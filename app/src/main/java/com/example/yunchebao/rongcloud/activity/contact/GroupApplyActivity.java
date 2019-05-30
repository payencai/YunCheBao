package com.example.yunchebao.rongcloud.activity.contact;

import android.app.Dialog;
import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yunchebao.MyApplication;
import com.costans.PlatformContans;
import com.example.yunchebao.R;
import com.google.gson.Gson;
import com.gyf.immersionbar.ImmersionBar;
import com.http.HttpProxy;
import com.http.ICallBack;
import com.example.yunchebao.rongcloud.adapter.ApplyGroupAdapter;
import com.example.yunchebao.rongcloud.model.ApplyGroup;
import com.example.yunchebao.rongcloud.sidebar.PinnedHeaderDecoration;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GroupApplyActivity extends AppCompatActivity {
    ApplyGroupAdapter mApplyFriendAdapter;
    List<ApplyGroup> mContactModels = new ArrayList<>();
    List<ApplyGroup> mShowModels = new ArrayList<>();
    @BindView(R.id.main_recycler)
    RecyclerView mRecyclerView;
    @BindView(R.id.sr_refresh)
    SwipeRefreshLayout sr_refresh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_apply);
        ImmersionBar.with(this).autoDarkModeEnable(true).fitsSystemWindows(true).statusBarColor(R.color.white).init();
        ButterKnife.bind(this);
        initView();
    }

    private void showApplyDialog(final ApplyGroup applyFriend) {
        final Dialog dialog = new Dialog(this);
        View view = LayoutInflater.from(this).inflate(R.layout.dialog, null);
        final EditText et_season = (EditText) view.findViewById(R.id.et_season);
        TextView tv_cancel = (TextView) view.findViewById(R.id.tv_cancel);
        TextView tv_name = (TextView) view.findViewById(R.id.tv_name);
        TextView tv_submit = (TextView) view.findViewById(R.id.tv_submit);
        tv_name.setText("入群申请");
        tv_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String reson = et_season.getEditableText().toString();
                applyAndagree(2, applyFriend, reson, dialog);

            }
        });
        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.setContentView(view);
        dialog.show();
        Window window = dialog.getWindow();
        WindowManager.LayoutParams layoutParams = window.getAttributes();
        layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        window.setAttributes(layoutParams);

    }

    private void applyAndagree(final int state, ApplyGroup applyFriend, String reason, final Dialog dialog) {
        Map<String, Object> params = new HashMap<>();
        params.put("id", applyFriend.getId());
        params.put("state", state);
        if (state == 2) {
            params.put("rejectReason", reason);
        }

        HttpProxy.obtain().post(PlatformContans.Chat.updateCrowdApply, MyApplication.token, params, new ICallBack() {
            @Override
            public void OnSuccess(String result) {
                if (dialog != null)
                    dialog.dismiss();
                if (state == 2)
                    Toast.makeText(GroupApplyActivity.this, "已拒绝", Toast.LENGTH_SHORT).show();
                else {
                    Toast.makeText(GroupApplyActivity.this, "已入群", Toast.LENGTH_SHORT).show();
                }
                mContactModels.clear();
                mShowModels.clear();
                getData();
            }

            @Override
            public void onFailure(String error) {

            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mContactModels.clear();
        mShowModels.clear();
        getData();
    }

    private void initView() {
        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        sr_refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mShowModels.clear();
                mContactModels.clear();
                getData();
            }
        });
        mApplyFriendAdapter = new ApplyGroupAdapter(mShowModels);
        mApplyFriendAdapter.setOnItemClickListener(new ApplyGroupAdapter.OnItemClickListener() {
            @Override
            public void onClick(int position, ApplyGroup contactModel) {
                if (contactModel.getState() == 0) {
//                    Intent intent = new Intent(GroupApplyActivity.this, ApplyDetailActivity.class);
//                    intent.putExtra("apply", contactModel);
//                    startActivityForResult(intent,1);
                }
            }

            @Override
            public void agree(ApplyGroup applyFriend) {
                //showApplyDialog(applyFriend);
                applyAndagree(1, applyFriend, "", null);
            }

            @Override
            public void refuse(ApplyGroup applyFriend) {
                showApplyDialog(applyFriend);
            }
        });
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        final PinnedHeaderDecoration decoration = new PinnedHeaderDecoration();
        decoration.registerTypePinnedHeader(1, new PinnedHeaderDecoration.PinnedHeaderCreator() {
            @Override
            public boolean create(RecyclerView parent, int adapterPosition) {
                return true;
            }
        });
        mRecyclerView.addItemDecoration(decoration);
        mRecyclerView.setAdapter(mApplyFriendAdapter);
        getData();
    }

    private void getData() {

        HttpProxy.obtain().get(PlatformContans.Chat.getCrowdApplyList, MyApplication.token, new ICallBack() {
            @Override
            public void OnSuccess(String result) {
                if (sr_refresh.isRefreshing()) {
                    sr_refresh.setRefreshing(false);
                }
                Log.e("groupapply", result);
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    JSONArray data = jsonObject.getJSONArray("data");
                    for (int i = 0; i < data.length(); i++) {
                        JSONObject item = data.getJSONObject(i);
                        ApplyGroup applyFriend = new Gson().fromJson(item.toString(), ApplyGroup.class);
                        mContactModels.add(applyFriend);
                    }
                    mShowModels.addAll(mContactModels);
                    mApplyFriendAdapter.notifyDataSetChanged();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(String error) {
            }
        });
    }
}
