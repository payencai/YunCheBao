package com.vipcenter;

import android.content.Intent;
import android.support.annotation.NonNull;
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
import com.http.HttpProxy;
import com.http.ICallBack;
import com.payencai.library.util.ToastUtil;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.vipcenter.adapter.MyCarAdapter;
import com.vipcenter.model.MyCar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyCarListActivity extends AppCompatActivity {
    MyCarAdapter mMyCarAdapter;
    List<MyCar> mMyCars;
    @BindView(R.id.rv_order)
    RecyclerView rv_car;
    @BindView(R.id.refresh)
    SmartRefreshLayout refresh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_car_list);
        ButterKnife.bind(this);
        initView();
    }

    private void refresh() {
        mMyCars.clear();
        getData();
    }

    private void changeToDefault(String id, int isdefault) {
        Map<String, Object> params = new HashMap<>();
        params.put("id", id);
        params.put("isDefault", isdefault);
        HttpProxy.obtain().post(PlatformContans.DrivingLicense.updateCarToIsDefault, MyApplication.token, params, new ICallBack() {
            @Override
            public void OnSuccess(String result) {
                refresh();
            }

            @Override
            public void onFailure(String error) {

            }
        });
    }

    private void delete(String id) {
        Map<String, Object> params = new HashMap<>();
        params.put("id", id);
        HttpProxy.obtain().post(PlatformContans.DrivingLicense.deleteMyCar, MyApplication.token, params, new ICallBack() {
            @Override
            public void OnSuccess(String result) {
                Log.e("result", result);
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    int code = jsonObject.getInt("resultCode");
                    if (code == 0) {
                        refresh();
                    } else {
                        String message = jsonObject.getString("message");
                        ToastUtil.showToast(MyCarListActivity.this, message);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                refresh();
            }

            @Override
            public void onFailure(String error) {

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        refresh();
    }

    private void initView() {
        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        findViewById(R.id.tv_add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(MyCarListActivity.this, MycarActivity.class), 1);
            }
        });
        mMyCars = new ArrayList<>();
        mMyCarAdapter = new MyCarAdapter(R.layout.item_my_car, mMyCars);
        mMyCarAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                MyCar myCar = (MyCar) adapter.getItem(position);
                switch (view.getId()) {
                    case R.id.btnDelete:
                        delete(myCar.getId());
                        break;
                    case R.id.ll_check:
                        if (myCar.getIsDefault() == 1)
                            changeToDefault(myCar.getId(), 0);
                        else {
                            changeToDefault(myCar.getId(), 1);
                        }
                        break;
                    case R.id.ll_item:
                        if (myCar.getIsDefault() == 1) {
                            Intent intent = new Intent(MyCarListActivity.this, MycarActivity.class);
                            intent.putExtra("data", myCar);
                            startActivity(intent);
                        }
                        break;
                }
            }
        });
        rv_car.setLayoutManager(new LinearLayoutManager(this));
        rv_car.setAdapter(mMyCarAdapter);
        refresh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                refresh();
                refreshLayout.finishRefresh(1000);
            }
        });
        getData();
    }

    private void getData() {
        HttpProxy.obtain().post(PlatformContans.DrivingLicense.getApplicationByUserId, MyApplication.token, "", new ICallBack() {
            @Override
            public void OnSuccess(String result) {
                Log.e("mycar", result);
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    JSONArray data = jsonObject.getJSONArray("data");
                    for (int i = 0; i < data.length(); i++) {
                        JSONObject item = data.getJSONObject(i);
                        MyCar baikeItem = new Gson().fromJson(item.toString(), MyCar.class);
                        mMyCars.add(baikeItem);
                    }
                    mMyCarAdapter.setNewData(mMyCars);
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
