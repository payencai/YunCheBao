package com.newversion;

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

import com.application.MyApplication;
import com.costans.PlatformContans;
import com.example.yunchebao.R;
import com.http.HttpProxy;
import com.http.ICallBack;
import com.payencai.library.util.ToastUtil;
import com.example.yunchebao.rongcloud.sidebar.ContactModel;
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_tags);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        mSelect = new ArrayList<>();
        mTagUserAdapter = new TagUserAdapter(this, mSelect);
        lv_user.setAdapter(mTagUserAdapter);
        ll_adduser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(CreateTagsActivity.this, ChooseUserActivity.class), 1);
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
                String name=et_name.getEditableText().toString();

                if(TextUtils.isEmpty(name)){
                    ToastUtil.showToast(CreateTagsActivity.this,"名字不能为空");
                    return;
                }
                if(TextUtils.isEmpty(ids)){
                    ToastUtil.showToast(CreateTagsActivity.this,"请添加成员");
                    return;
                }
                addTag();
            }
        });
    }
    private void addTag(){
        Map<String,Object> params=new HashMap<>();
        params.put("name",et_name.getEditableText().toString());
        params.put("ids",ids);
        HttpProxy.obtain().post(PlatformContans.Label.addLabel, MyApplication.token, params, new ICallBack() {
            @Override
            public void OnSuccess(String result) {
                Log.e("result",result);
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
            mSelect.clear();
            ArrayList<ContactModel> modelArrayList = (ArrayList<ContactModel>) data.getSerializableExtra("user");
            mSelect.addAll(modelArrayList);
            mTagUserAdapter.notifyDataSetChanged();
            ids="";

            ArrayList<String> idList = new ArrayList<>();
            for (int i = 0; i < mSelect.size(); i++) {
                idList.add(mSelect.get(i).getUserId());
            }

            ids = listToString(idList);
        }
    }

    /**List转String逗号分隔*/
    private String listToString(ArrayList<String> list) {

        StringBuilder stringBuilder = new StringBuilder();
        for(String str : list){
            stringBuilder.append(str);
            stringBuilder.append(",");
        }
        String strs = stringBuilder.toString();
        strs = strs.substring(0, strs.length() - 1);

        return strs;
    }
}
