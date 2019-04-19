package com.fourshop.activity;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.application.MyApplication;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.costans.PlatformContans;
import com.drive.adapter.DriverCommentAdapter;
import com.drive.model.SubstitubeComment;
import com.example.yunchebao.R;
import com.fourshop.adapter.FourShopCommentAdapter;
import com.fourshop.bean.FourShopComment;
import com.google.gson.Gson;
import com.http.HttpProxy;
import com.http.ICallBack;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import go.error;

public class SeeCommentActivity extends AppCompatActivity {


    @BindView(R.id.rv_order)
    RecyclerView rv_comment;
    @BindView(R.id.refresh)
    SmartRefreshLayout refresh;
    List<FourShopComment> mSubstitubeComments;
    FourShopCommentAdapter mDriverCommentAdapter;
    String id;
    int page=1;
    boolean isLoadMore=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_see_comment);
        ButterKnife.bind(this);
        id=getIntent().getStringExtra("id");
        initView();
    }

    private void initView() {
        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        refresh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                page=1;
                mSubstitubeComments.clear();
                mDriverCommentAdapter.setNewData(mSubstitubeComments);
                getData();
            }
        });
        mSubstitubeComments=new ArrayList<>();
        mDriverCommentAdapter=new FourShopCommentAdapter(R.layout.item_shop_comment,mSubstitubeComments);
        rv_comment.setLayoutManager(new LinearLayoutManager(this));
        rv_comment.setAdapter(mDriverCommentAdapter);
        getData();
    }
    private void getData(){
        Map<String, Object> params = new HashMap<>();
        params.put("orderId", id);
        HttpProxy.obtain().get(PlatformContans.FourShop.getFourShopOrderCommentByOrderId, params, MyApplication.token,new ICallBack() {
            @Override
            public void OnSuccess(String result) {
                refresh.finishRefresh();
                Log.e("detail", result);
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    JSONObject data = jsonObject.getJSONObject("data");
                    FourShopComment driveMan = new Gson().fromJson(data.toString(), FourShopComment.class);
                    mSubstitubeComments.add(driveMan);
                    mDriverCommentAdapter.setNewData(mSubstitubeComments);
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
