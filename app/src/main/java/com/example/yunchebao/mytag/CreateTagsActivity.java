package com.example.yunchebao.mytag;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.yunchebao.MyApplication;
import com.costans.PlatformContans;
import com.example.yunchebao.R;
import com.http.HttpProxy;
import com.http.ICallBack;
import com.newversion.NewTag;
import com.newversion.TagUserAdapter;
import com.payencai.library.util.ToastUtil;
import com.example.yunchebao.rongcloud.sidebar.ContactModel;
import com.tool.StringUtils;
import com.tool.listview.PersonalListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CreateTagsActivity extends AppCompatActivity {
    @BindView(R.id.lv_user)
    PersonalListView lv_user;
    @BindView(R.id.ll_adduser)
    LinearLayout ll_adduser;
    TagUserAdapter mTagUserAdapter;
    ArrayList<ContactModel> mSelect;
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.tv_finish)
    TextView tv_finish;
    @BindView(R.id.et_name)
    EditText et_name;
    String ids;
    NewTag newTag;
    ArrayList<String> idsList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_tags);
        ButterKnife.bind(this);
        newTag = (NewTag) getIntent().getSerializableExtra("data");
        initView();
    }

    private void initView() {
        idsList=new ArrayList<>();
        mSelect = new ArrayList<>();
        mTagUserAdapter = new TagUserAdapter(this, mSelect);
        mTagUserAdapter.setOnDelClickListener(new TagUserAdapter.OnDelClickListener() {
            @Override
            public void onClick(int position) {
                ContactModel contactModel = mSelect.get(position);
                mSelect.remove(position);
                mTagUserAdapter.notifyDataSetChanged();
                idsList.remove(contactModel.getUserId());
                if (newTag != null) {
                    for (int i = 0; i < newTag.getList().size(); i++) {
                        if (contactModel.getUserId().equals(newTag.getList().get(i).getUserId())) {
                            newTag.getList().remove(i);
                        }
                    }
                }


            }
        });
        lv_user.setAdapter(mTagUserAdapter);
        ll_adduser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(CreateTagsActivity.this, ChooseTagUserActivity.class);
                if (newTag != null) {
                    intent.putExtra("data", newTag);
                }
                startActivityForResult(intent, 1);
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        tv_finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = et_name.getEditableText().toString().trim();
                if (TextUtils.isEmpty(name)) {
                    ToastUtil.showToast(CreateTagsActivity.this, "名字不能为空");
                    return;
                }
                if (idsList.size()==0) {
                    ToastUtil.showToast(CreateTagsActivity.this, "请添加成员");
                    return;
                }
                if(newTag!=null){
                    updateTag();
                }else{
                    addTag();
                }

            }
        });
        if (newTag != null) {
            mSelect.clear();
            et_name.setText(newTag.getName());
            for (int i = 0; i < newTag.getList().size(); i++) {
                idsList.add(newTag.getList().get(i).getUserId());
                NewTag.ListBean listBean = newTag.getList().get(i);
                ContactModel contactModel = new ContactModel(listBean.getName());
                contactModel.setHeadPortrait(listBean.getHeadPortrait());
                contactModel.setUserId(listBean.getUserId());
                contactModel.setName(listBean.getName());
                mSelect.add(contactModel);
            }
            mTagUserAdapter.notifyDataSetChanged();

        }
    }

    private void addTag() {
        Map<String, Object> params = new HashMap<>();
        params.put("name", et_name.getEditableText().toString());
        params.put("ids", listToString(idsList));
        HttpProxy.obtain().post(PlatformContans.Label.addLabel, MyApplication.token, params, new ICallBack() {
            @Override
            public void OnSuccess(String result) {
                Log.e("result", result);
                finish();
            }

            @Override
            public void onFailure(String error) {

            }
        });
    }
    private void updateTag() {
        idsList.clear();
        for (int i = 0; i < mSelect.size(); i++) {
            idsList.add(mSelect.get(i).getUserId());
        }
        Map<String, Object> params = new HashMap<>();
        String name=et_name.getEditableText().toString();
        if(!TextUtils.isEmpty(name)){
            params.put("name", name);
        }
        params.put("id",newTag.getId());

        params.put("ids", listToString(idsList));
        HttpProxy.obtain().post(PlatformContans.Label.update, MyApplication.token, params, new ICallBack() {
            @Override
            public void OnSuccess(String result) {
                Log.e("updateTag", result);
                finish();
            }

            @Override
            public void onFailure(String error) {

            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && data != null) {
            String userList=data.getStringExtra("userList");
            ids=userList;
            String headList=data.getStringExtra("headList");
            String nameList=data.getStringExtra("nameList");
            idsList= StringUtils.StringToArrayList(userList,",");
            for (int i = 0; i < idsList.size(); i++) {
                ContactModel contactModel=new ContactModel(StringUtils.StringToArrayList(nameList,",").get(i));
                contactModel.setUserId(idsList.get(i));
                contactModel.setHeadPortrait(StringUtils.StringToArrayList(headList,",").get(i));
                mSelect.add(contactModel);
            }
            mTagUserAdapter.notifyDataSetChanged();

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
}
