package com.baike;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.baike.adapter.CategoryTagAdapter;
import com.baike.model.ClassifyWiki;
import com.costans.PlatformContans;
import com.example.yunchebao.R;
import com.google.gson.Gson;
import com.http.HttpProxy;
import com.http.ICallBack;
import com.system.adapter.BaikeTypeAdapter;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagFlowLayout;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BaikeTagActivity extends AppCompatActivity {
    List<ClassifyWiki> mClassifyWikis1 ;
    CategoryTagAdapter mBaikeTypeAdapter;
    int type;
    int pos;
    @BindView(R.id.fl_content)
    TagFlowLayout mFlowLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_baike_tag);
        ButterKnife.bind(this);
        type=getIntent().getIntExtra("type",-1);
        pos=getIntent().getIntExtra("pos",-1);
        initView();
    }

    private void initView() {
        mClassifyWikis1=new ArrayList<>();
        mBaikeTypeAdapter=new CategoryTagAdapter(mClassifyWikis1);
        mFlowLayout.setAdapter(mBaikeTypeAdapter);
        mFlowLayout.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
            @Override
            public boolean onTagClick(View view, int position, FlowLayout parent) {
                Intent intent=new Intent();
                Bundle bundle=new Bundle();
                bundle.putInt("pos",position);
                intent.putExtras(bundle);
                setResult(1,intent);
                finish();
                return true;
            }
        });
        mFlowLayout.setOnSelectListener(new TagFlowLayout.OnSelectListener() {
            @Override
            public void onSelected(Set<Integer> selectPosSet) {

            }
        });
        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        getType(type);
    }
    ClassifyWiki mClassifyWiki;
    private void getType(final int type) {
        Map<String, Object> params = new HashMap<>();
        params.put("type", type);
        HttpProxy.obtain().get(PlatformContans.WiKi.getWikiClassifyByType, params, new ICallBack() {
            @Override
            public void OnSuccess(String result) {
                Log.e("getWikiClassifyByType", result);
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    JSONArray data = jsonObject.getJSONArray("data");
                    for (int i = 0; i < data.length(); i++) {
                        JSONObject item = data.getJSONObject(i);
                        ClassifyWiki classifyWiki = new Gson().fromJson(item.toString(), ClassifyWiki.class);
                        mClassifyWikis1.add(classifyWiki);
                        if(pos==i){
                            mClassifyWiki=classifyWiki;
                        }
                    }
                    mBaikeTypeAdapter.notifyDataChanged();
                    mBaikeTypeAdapter.setSelected(pos,mClassifyWiki);

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
