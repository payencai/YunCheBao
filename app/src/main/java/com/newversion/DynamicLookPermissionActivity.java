package com.newversion;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.yunchebao.MyApplication;
import com.costans.PlatformContans;
import com.example.yunchebao.R;
import com.google.gson.Gson;
import com.http.HttpProxy;
import com.http.ICallBack;
import com.example.yunchebao.rongcloud.sidebar.ContactModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DynamicLookPermissionActivity extends AppCompatActivity {
    /**
     * 查看权限（1.公开，2私密，3.部分可见，4.不给谁看）
     */
    private int kind = 1;

    @BindView(R.id.tv_cancel)
    TextView tvCancel;
    @BindView(R.id.tv_confirm)
    TextView tvConfirm;
    @BindView(R.id.iv_select_all_lock)
    ImageView ivSelectAllLock;
    @BindView(R.id.ll_all_look)
    LinearLayout llAllLook;
    @BindView(R.id.iv_select_none_lock)
    ImageView ivSelectNoneLock;
    @BindView(R.id.ll_none_look)
    LinearLayout llNoneLook;
    @BindView(R.id.iv_select_part_lock)
    ImageView ivSelectPartLock;
    @BindView(R.id.iv_export_look)
    ImageView ivExportLook;
    @BindView(R.id.iv_export_unlook)
    ImageView ivExportUnlook;
    @BindView(R.id.ll_part_look)
    LinearLayout llPartLook;
    @BindView(R.id.iv_select_par_unlock)
    ImageView ivSelectParUnlock;
    @BindView(R.id.ll_part_unlook)
    LinearLayout llPartUnlook;
    @BindView(R.id.ll_look_select)
    LinearLayout ll_look_select;
    @BindView(R.id.ll_unlook_select)
    LinearLayout ll_unlook_select;
    @BindView(R.id.rv_select_look)
    RecyclerView rv_select_look;
    @BindView(R.id.rv_select_unlook)
    RecyclerView rv_select_unlook;
    @BindView(R.id.tv_look_users)
    TextView tv_look_users;
    @BindView(R.id.tv_unlook_users)
    TextView tv_unlook_users;

    private ArrayList<ContactModel> mSelect = new ArrayList<>();
    private String ids;
    private String users;

    private MyTagAdapter mMyTagAdapter;
    private List<NewTag> mNewTags = new ArrayList<>();

    private RecyclerView rv_tags;

    private HashSet<String> idList = new HashSet<>();
    private ArrayList<String> userList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dynamic_look_permission);
        ButterKnife.bind(this);

        initView();
    }

    private void initView() {
        getFriendsTagList();

        mMyTagAdapter = new MyTagAdapter(R.layout.item_select_tag, mNewTags, false);
    }

    /**
     * 获取标签列表
     */
    private void getFriendsTagList() {

        HttpProxy.obtain().get(PlatformContans.Label.getLabelList, MyApplication.token, new ICallBack() {
            @Override
            public void OnSuccess(String result) {
                Log.e("getLabelList", result);
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    JSONArray data = jsonObject.getJSONArray("data");
                    for (int i = 0; i < data.length(); i++) {
                        JSONObject item = data.getJSONObject(i);
                        NewTag baikeItem = new Gson().fromJson(item.toString(), NewTag.class);
                        mNewTags.add(baikeItem);
                    }
                    mMyTagAdapter.setNewData(mNewTags);
                    mMyTagAdapter.loadMoreEnd(true);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(String error) {

            }
        });

    }

    @OnClick({R.id.tv_cancel, R.id.tv_confirm, R.id.ll_all_look, R.id.ll_none_look, R.id.ll_part_look, R.id.ll_part_unlook, R.id.tv_select_from_contacts_look, R.id.tv_select_from_contacts_unlook})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_cancel:

                onBackPressed();
                break;
            case R.id.tv_confirm:

                confirm();
                break;
            case R.id.ll_all_look:

                allLook();
                break;
            case R.id.ll_none_look:

                noneLook();
                break;
            case R.id.ll_part_look:

                partLook();
                break;
            case R.id.ll_part_unlook:

                partUnLook();
                break;
            case R.id.tv_select_from_contacts_look://从通讯录选择

                startActivityForResult(new Intent(DynamicLookPermissionActivity.this, ChooseUserActivity.class), 1);
                kind = 3;
                break;
            case R.id.tv_select_from_contacts_unlook://从通讯录选择

                startActivityForResult(new Intent(DynamicLookPermissionActivity.this, ChooseUserActivity.class), 1);
                kind = 4;
                break;


        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && data != null) {
            mSelect.clear();
            ArrayList<ContactModel> modelArrayList = (ArrayList<ContactModel>) data.getSerializableExtra("user");
            mSelect.addAll(modelArrayList);

            idList.clear();
            users = "";

            for (int i = 0; i < mSelect.size(); i++) {
                idList.add(mSelect.get(i).getUserId());
                userList.add(mSelect.get(i).getName());
            }

            users = listToString(userList);

            if (kind == 3) {
                tv_look_users.setVisibility(View.VISIBLE);
                tv_look_users.setText(users);
                tv_unlook_users.setText("");
                tv_unlook_users.setVisibility(View.GONE);
            } else {
                tv_unlook_users.setVisibility(View.VISIBLE);
                tv_unlook_users.setText(users);
                tv_look_users.setText("");
                tv_look_users.setVisibility(View.GONE);
            }
        }

    }

    /**
     * List转String逗号分隔
     */
    private String listToString(ArrayList<String> list) {

        StringBuilder stringBuilder = new StringBuilder();
        for (String str : list) {
            stringBuilder.append(str);
            stringBuilder.append(",");
        }
        String strs = stringBuilder.toString();
        strs = strs.substring(0, strs.length() - 1);

        return strs;
    }

    private String setToString(HashSet<String> list) {
        StringBuilder stringBuilder = new StringBuilder();
        for (String str : list) {
            stringBuilder.append(str);
            stringBuilder.append(",");
        }
        String strs = stringBuilder.toString();
        strs = strs.substring(0, strs.length() - 1);

        return strs;
    }


    private void confirm() {
        Intent intent = new Intent();
        intent.putExtra("kind", kind);
        if (mNewTags.size() > 0) {
            for (int i = 0, length = mNewTags.size(); i < length; i++) {
                NewTag newTag = mNewTags.get(i);

                if (newTag.isChecked()) {
                    List<NewTag.ListBean> list = newTag.getList();
                    userList.add(newTag.getName());

                    for (int j = 0, size = list.size(); j < size; j++) {
                        idList.add(list.get(j).getUserId());
                    }
                }

            }
        }
        if(!idList.isEmpty())
          ids = setToString(idList);
        else{
            ids="";
        }
        if(userList.size()>0)
           users = listToString(userList);
        else{
            users="";
        }

        if (kind == 3) {
            intent.putExtra("ids", ids);
            intent.putExtra("users", users);
        }

        if (kind == 4) {
            intent.putExtra("ids", ids);
            intent.putExtra("users", users);
        }


        setResult(RESULT_OK, intent);
        finish();
    }

    private void allLook() {
        kind = 1;

        ivSelectAllLock.setVisibility(View.VISIBLE);

        ivSelectNoneLock.setVisibility(View.INVISIBLE);
        ivSelectPartLock.setVisibility(View.INVISIBLE);
        ivSelectParUnlock.setVisibility(View.INVISIBLE);
    }

    private void noneLook() {
        kind = 2;

        ivSelectAllLock.setVisibility(View.INVISIBLE);

        ivSelectNoneLock.setVisibility(View.VISIBLE);

        ivSelectPartLock.setVisibility(View.INVISIBLE);
        ivSelectParUnlock.setVisibility(View.INVISIBLE);

    }

    boolean exportLook = false;

    private void partLook() {
        rv_tags = rv_select_look;
        rv_select_look.setVisibility(View.VISIBLE);

        rv_tags.setLayoutManager(new LinearLayoutManager(this));
        rv_tags.setAdapter(mMyTagAdapter);

        kind = 3;

        exportLook = !exportLook;

        ivSelectAllLock.setVisibility(View.INVISIBLE);
        ivSelectNoneLock.setVisibility(View.INVISIBLE);

        ivSelectPartLock.setVisibility(View.VISIBLE);

        ivSelectParUnlock.setVisibility(View.INVISIBLE);

        ivExportUnlook.setImageResource(R.mipmap.arrow_bottom);

        if (exportLook) {
            ivExportLook.setImageResource(R.mipmap.arrow_top);
            ll_look_select.setVisibility(View.VISIBLE);
        } else {
            ivExportLook.setImageResource(R.mipmap.arrow_bottom);
            ll_look_select.setVisibility(View.GONE);
        }

        ll_unlook_select.setVisibility(View.GONE);
        exportUnlook = false;

    }

    boolean exportUnlook = false;

    private void partUnLook() {
        rv_tags = rv_select_unlook;
        rv_select_unlook.setVisibility(View.VISIBLE);

        rv_tags.setLayoutManager(new LinearLayoutManager(this));
        rv_tags.setAdapter(mMyTagAdapter);

        kind = 4;
        exportUnlook = !exportUnlook;

        ivSelectAllLock.setVisibility(View.INVISIBLE);
        ivSelectNoneLock.setVisibility(View.INVISIBLE);
        ivSelectPartLock.setVisibility(View.INVISIBLE);

        ivSelectParUnlock.setVisibility(View.VISIBLE);

        ivExportLook.setImageResource(R.mipmap.arrow_bottom);

        if (exportUnlook) {
            ivExportUnlook.setImageResource(R.mipmap.arrow_top);
            ll_unlook_select.setVisibility(View.VISIBLE);
        } else {
            ivExportUnlook.setImageResource(R.mipmap.arrow_bottom);
            ll_unlook_select.setVisibility(View.GONE);
        }

        ll_look_select.setVisibility(View.GONE);
        exportLook = false;

    }

}
