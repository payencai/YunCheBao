package com.rongcloud.activity.contact;


import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.application.MyApplication;
import com.costans.PlatformContans;
import com.example.yunchebao.R;
import com.google.gson.Gson;
import com.http.HttpProxy;
import com.http.ICallBack;
import com.rongcloud.adapter.ApplyFriendAdapter;
import com.rongcloud.model.ApplyFriend;
import com.rongcloud.sidebar.ContactModel;
import com.rongcloud.sidebar.ContactsAdapter;
import com.rongcloud.sidebar.PinnedHeaderDecoration;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.rong.imkit.RongIM;

/**
 * A simple {@link Fragment} subclass.
 */
public class NewFriendFragment extends Fragment {
    ApplyFriendAdapter mApplyFriendAdapter;
    List<ApplyFriend> mContactModels = new ArrayList<>();
    List<ApplyFriend> mShowModels = new ArrayList<>();
    @BindView(R.id.main_recycler)
    RecyclerView mRecyclerView;

    public NewFriendFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_new_friend, container, false);
        ButterKnife.bind(this, view);
        initView(view);
        return view;
    }


    private void showApplyDialog(final ApplyFriend applyFriend) {
        final Dialog dialog = new Dialog(getContext());
        View view = LayoutInflater.from(getContext()).inflate(R.layout.dialog, null);
        final EditText et_season = (EditText) view.findViewById(R.id.et_season);
        TextView tv_cancel = (TextView) view.findViewById(R.id.tv_cancel);
        TextView tv_name = (TextView) view.findViewById(R.id.tv_name);
        TextView tv_submit = (TextView) view.findViewById(R.id.tv_submit);
        tv_name.setText("好友申请");
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

    private void applyAndagree(final int state, ApplyFriend applyFriend, String reason, final Dialog dialog) {
        Map<String, Object> params = new HashMap<>();
        params.put("id", applyFriend.getId());
        params.put("state", state);
        if (state == 2) {
            params.put("rejectReason", reason);
        }
        com.vipcenter.model.UserInfo userinfo = MyApplication.getUserInfo();
        if (userinfo != null)
            HttpProxy.obtain().post(PlatformContans.Chat.updateFriendApply, MyApplication.token, params, new ICallBack() {
                @Override
                public void OnSuccess(String result) {
                    if(dialog!=null)
                       dialog.dismiss();
                    if (state == 2)
                        Toast.makeText(getContext(), "已拒绝", Toast.LENGTH_SHORT).show();
                    else {
                        Toast.makeText(getContext(), "添加好友成功", Toast.LENGTH_SHORT).show();
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

    private void initView(View view) {
        mApplyFriendAdapter = new ApplyFriendAdapter(mShowModels);
        mApplyFriendAdapter.setOnItemClickListener(new ApplyFriendAdapter.OnItemClickListener() {
            @Override
            public void onClick(int position, ApplyFriend contactModel) {
                if(contactModel.getState()==0){
                    Intent intent = new Intent(getContext(), ApplyDetailActivity.class);
                    intent.putExtra("apply", contactModel);
                    startActivityForResult(intent,1);
                }
            }

            @Override
            public void agree(ApplyFriend applyFriend) {
                //showApplyDialog(applyFriend);
                applyAndagree(1,applyFriend,"",null);
            }

            @Override
            public void refuse(ApplyFriend applyFriend) {
                showApplyDialog(applyFriend);
            }
        });
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
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
        com.vipcenter.model.UserInfo userinfo = MyApplication.getUserInfo();
        if (userinfo != null)
            HttpProxy.obtain().get(PlatformContans.Chat.getFriendApplyList, MyApplication.token, new ICallBack() {
                @Override
                public void OnSuccess(String result) {
                    Log.e("apply", result);
                    try {
                        JSONObject jsonObject = new JSONObject(result);
                        JSONArray data = jsonObject.getJSONArray("data");
                        for (int i = 0; i < data.length(); i++) {
                            JSONObject item = data.getJSONObject(i);
                            ApplyFriend applyFriend = new Gson().fromJson(item.toString(), ApplyFriend.class);
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
