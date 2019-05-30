package com.vipcenter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.example.yunchebao.MyApplication;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.costans.PlatformContans;
import com.example.yunchebao.R;
import com.google.gson.Gson;
import com.gyf.immersionbar.ImmersionBar;
import com.http.HttpProxy;
import com.http.ICallBack;
import com.vipcenter.adapter.MyPayAdapter;
import com.vipcenter.model.Mypay;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyPayActivity extends AppCompatActivity {
    int page=1;
    boolean isLoadMore=false;
    @BindView(R.id.rv_pay)
    RecyclerView rv_pay;
    MyPayAdapter mMyPayAdapter;
    List<Mypay>mMypays;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_pay);
        ButterKnife.bind(this);
        ImmersionBar.with(this).autoDarkModeEnable(true).fitsSystemWindows(true).statusBarColor(R.color.white).init();
        initView();

    }

    private void initView() {
        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mMypays=new ArrayList<>();
        mMyPayAdapter=new MyPayAdapter(R.layout.item_mypay,mMypays);
        mMyPayAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                page++;
                isLoadMore=true;
                getData();
            }
        },rv_pay);
        rv_pay.setLayoutManager(new LinearLayoutManager(this));
        rv_pay.setAdapter(mMyPayAdapter);
        getData();
    }
    private void getData() {
        Map<String,Object> params=new HashMap<>();
        params.put("page",page);
        HttpProxy.obtain().get(PlatformContans.User.getSpendRecord, params,MyApplication.token, new ICallBack() {
            @Override
            public void OnSuccess(String result) {
                Log.e("result", result);
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    int code = jsonObject.getInt("resultCode");
                    if (code == 0) {
                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                        List<Mypay> orders=new ArrayList<>();
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject item = jsonArray.getJSONObject(i);
                            Mypay homeCategory = new Gson().fromJson(item.toString(), Mypay.class);
                            orders.add(homeCategory);
                            mMypays.add(homeCategory);
                        }
                        if (isLoadMore) {
                            isLoadMore = false;
                            mMyPayAdapter.addData(orders);
                            if (jsonArray.length() > 0)
                                mMyPayAdapter.loadMoreComplete();
                            else {
                                mMyPayAdapter.loadMoreEnd(true);
                            }
                        } else {
                            mMyPayAdapter.setNewData(mMypays);
                            // orderAdapter.loadMoreEnd(true);
                        }
                    }
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
