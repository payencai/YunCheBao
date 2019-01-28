package com.vipcenter;

import android.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.application.MyApplication;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.costans.PlatformContans;
import com.example.yunchebao.R;
import com.google.gson.Gson;
import com.http.HttpProxy;
import com.http.ICallBack;
import com.order.NewPublish;
import com.tool.ActivityConstans;
import com.tool.UIControlUtils;
import com.vipcenter.adapter.FocusAdapter;
import com.vipcenter.model.MyFocus;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MyFocusActivity extends AppCompatActivity {
    List<MyFocus> mMyFocusList;
    FocusAdapter mFocusAdapter;
    @BindView(R.id.rv_focus)
    RecyclerView rv_focus;
    int page=1;
    boolean isLoadMore=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_focus);
        UIControlUtils.UITextControlsUtils.setUIText(findViewById(R.id.title), ActivityConstans.UITag.TEXT_VIEW, "粉丝");
        ButterKnife.bind(this);
        initView();
    }
    @OnClick({R.id.back})
    public void OnClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                onBackPressed();
                break;
        }
    }
    private void initView() {
        mMyFocusList=new ArrayList<>();
        mFocusAdapter=new FocusAdapter(R.layout.item_focus,mMyFocusList);
        mFocusAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                page++;
                isLoadMore=true;
                getData();
            }
        },rv_focus);
        rv_focus.setLayoutManager(new LinearLayoutManager(this));
        rv_focus.setAdapter(mFocusAdapter);
        getData();
    }
    private void getData(){
        Map<String,Object> params=new HashMap<>();
        params.put("page",page);
        HttpProxy.obtain().get(PlatformContans.User.getOtherFocusList, params, MyApplication.getUserInfo().getToken(), new ICallBack() {
            @Override
            public void OnSuccess(String result) {
                Log.e("result",result);
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    int code = jsonObject.getInt("resultCode");
                    if (code == 0) {

                        JSONArray data = jsonObject.getJSONArray("data");
                        List<MyFocus> mCarOrder = new ArrayList<>();
                        for (int i = 0; i < data.length(); i++) {
                            JSONObject item = data.getJSONObject(i);
                            MyFocus carOrder = new Gson().fromJson(item.toString(), MyFocus.class);
                            mCarOrder.add(carOrder);
                        }
                        if (isLoadMore) {
                            isLoadMore = false;
                            mFocusAdapter.addData(mCarOrder);
                            if (data.length() > 0)
                                mFocusAdapter.loadMoreComplete();
                            else {
                                mFocusAdapter.loadMoreEnd(true);
                            }
                        } else {
                            mFocusAdapter.setNewData(mCarOrder);
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
