package com.newversion;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.yunchebao.MyApplication;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.costans.PlatformContans;
import com.example.yunchebao.R;
import com.example.yunchebao.mytag.CreateTagsActivity;
import com.google.gson.Gson;
import com.http.HttpProxy;
import com.http.ICallBack;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyTagsActivity extends AppCompatActivity {
    @BindView(R.id.rv_tags)
    RecyclerView rv_tags;
    @BindView(R.id.ll_newtag)
    LinearLayout ll_newtag;
    MyTagAdapter mMyTagAdapter;
    List<NewTag> mNewTags;
    @BindView(R.id.back)
    ImageView back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_tags);
        ButterKnife.bind(this);
        initView();
        getData();
    }

    private void deleteTag(int  id){
        Map<String,Object> params=new HashMap<>();
        params.put("id",id);
        HttpProxy.obtain().post(PlatformContans.Label.deleteLabel, MyApplication.token, params, new ICallBack() {
            @Override
            public void OnSuccess(String result) {
                Log.e("result",result);
                getData();
            }

            @Override
            public void onFailure(String error) {

            }
        });
    }

    public void getData() {

        HttpProxy.obtain().get(PlatformContans.Label.getLabelList, MyApplication.token, new ICallBack() {
            @Override
            public void OnSuccess(String result) {
                Log.e("getLabelList", result);
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    JSONArray data = jsonObject.getJSONArray("data");
                    List<NewTag> newTags=new ArrayList<>();
                    for (int i = 0; i < data.length(); i++) {
                        JSONObject item = data.getJSONObject(i);
                        NewTag baikeItem = new Gson().fromJson(item.toString(), NewTag.class);
                        baikeItem.setBack(true);
                        newTags.add(baikeItem);
                    }
                    mMyTagAdapter.setNewData(newTags);
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
    private void initView() {
        mNewTags=new ArrayList<>();
        mMyTagAdapter=new MyTagAdapter(R.layout.item_select_tag,mNewTags,true);
        mMyTagAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                NewTag newTag= (NewTag) adapter.getItem(position);
                if(view.getId()==R.id.btnDelete){
                    deleteTag(newTag.getId());
                }else if(view.getId()==R.id.ll_all_look){

                }

            }
        });

        rv_tags.setLayoutManager(new LinearLayoutManager(this));
        rv_tags.setAdapter(mMyTagAdapter);
        ll_newtag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivityForResult(new Intent(MyTagsActivity.this, CreateTagsActivity.class),1);
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1||requestCode==2){
            mNewTags.clear();
            mMyTagAdapter.setNewData(mNewTags);
            getData();
        }
    }
}
