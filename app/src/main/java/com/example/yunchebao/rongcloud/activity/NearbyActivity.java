package com.example.yunchebao.rongcloud.activity;

import android.app.Dialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.application.MyApplication;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.costans.PlatformContans;
import com.example.yunchebao.R;
import com.example.yunchebao.rongcloud.activity.contact.FriendDetailActivity;
import com.google.gson.Gson;
import com.gyf.immersionbar.ImmersionBar;
import com.http.HttpProxy;
import com.http.ICallBack;
import com.example.yunchebao.rongcloud.adapter.NearByAdapter;
import com.example.yunchebao.rongcloud.model.Nearby;
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
import butterknife.OnClick;
import go.error;

public class NearbyActivity extends AppCompatActivity {
    int page = 1;
    boolean isLoadMore = false;
    @BindView(R.id.rv_nearby)
    RecyclerView rv_nearby;
    @BindView(R.id.refresh)
    SmartRefreshLayout refersh;
    @BindView(R.id.iv_logo)
    ImageView iv_logo;
    NearByAdapter mNearByAdapter;
    List<Nearby> mNearbies;
    String sex = "不限";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nearby);
        ImmersionBar.with(this).autoDarkModeEnable(true).fitsSystemWindows(true).statusBarColor(R.color.white).init();
        ButterKnife.bind(this);

        initView();
    }

    private void initView() {
        mNearbies = new ArrayList<>();
        mNearByAdapter = new NearByAdapter(R.layout.item_nearby, mNearbies);
        mNearByAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                page++;
                isLoadMore = true;
                getData();
            }
        }, rv_nearby);
        mNearByAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Nearby nearby = (Nearby) adapter.getItem(position);
                getIsFriend(nearby.getId());
            }
        });
        refersh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                refersh();
            }
        });
        rv_nearby.setLayoutManager(new LinearLayoutManager(this));
        rv_nearby.setAdapter(mNearByAdapter);
        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        getData();
    }

    private void getIsFriend(String userId) {
        Map<String, Object> params = new HashMap<>();
        params.put("userId", userId);
        HttpProxy.obtain().get(PlatformContans.User.getUserResultById, params, MyApplication.token, new ICallBack() {
            @Override
            public void OnSuccess(String result) {
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    JSONObject data = jsonObject.getJSONObject("data");
                    int code = jsonObject.getInt("resultCode");
                    if (code == 0) {
                        String isFriend = data.getString("isFriend");
                        if(TextUtils.equals("1",isFriend)){
                            Intent intent = new Intent(NearbyActivity.this, FriendDetailActivity.class);
                            intent.putExtra("id", userId);
                            startActivity(intent);
                        }else{
                            Intent intent = new Intent(NearbyActivity.this, StrangerDelActivity.class);
                            intent.putExtra("id", userId);
                            startActivity(intent);
                        }
                        Log.e("getFourShopListByApp", isFriend);
                    } else {

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

    private void getData() {
        Map<String, Object> params = new HashMap<>();
        params.put("page", page);
        params.put("sex", sex);
        params.put("longitude", MyApplication.getaMapLocation().getLongitude() + "");
        params.put("latitude", MyApplication.getaMapLocation().getLatitude() + "");
        Log.e("params", params.toString());
        HttpProxy.obtain().get(PlatformContans.User.searchNearbyUser, params, MyApplication.token, new ICallBack() {
            @Override
            public void OnSuccess(String result) {
                refersh.finishRefresh();
                Log.e("getFourShopListByApp", result);
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    JSONArray data = jsonObject.getJSONArray("data");
                    List<Nearby> replaceDrives = new ArrayList<>();
                    for (int i = 0; i < data.length(); i++) {
                        JSONObject item = data.getJSONObject(i);
                        Nearby replaceDrive = new Gson().fromJson(item.toString(), Nearby.class);
                        mNearbies.add(replaceDrive);
                        replaceDrives.add(replaceDrive);
                    }
                    if (isLoadMore) {
                        isLoadMore = false;
                        if (data.length() == 0) {
                            mNearByAdapter.loadMoreEnd(true);
                        } else {
                            mNearByAdapter.addData(replaceDrives);
                            mNearByAdapter.loadMoreComplete();
                        }
                    } else {
                        mNearByAdapter.setNewData(mNearbies);

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

    @OnClick({R.id.rl_more, R.id.back})
    void OnClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                break;
            case R.id.rl_more:
                showNearbyDialog();
                break;
        }
    }

    private void clearLocation() {
        HttpProxy.obtain().post(PlatformContans.User.clearNearbyUser, null, MyApplication.token, new ICallBack() {
            @Override
            public void OnSuccess(String result) {
                finish();
            }

            @Override
            public void onFailure(String error) {

            }
        });
    }

    private void showNearbyDialog() {
        final Dialog dialog = new Dialog(this, R.style.dialog);
        View dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_nearby_man, null);
        TextView tv_item1 = dialogView.findViewById(R.id.tv_item1);
        TextView tv_item2 = dialogView.findViewById(R.id.tv_item2);
        TextView tv_item3 = dialogView.findViewById(R.id.tv_item3);
        TextView tv_item4 = dialogView.findViewById(R.id.tv_item4);
        TextView tv_item5 = dialogView.findViewById(R.id.tv_item5);
        tv_item1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iv_logo.setImageResource(R.mipmap.ic_man);
                iv_logo.setVisibility(View.VISIBLE);
                sex = "男";
                dialog.dismiss();
                refersh();
            }
        });
        tv_item2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iv_logo.setImageResource(R.mipmap.ic_women);
                iv_logo.setVisibility(View.VISIBLE);
                sex = "女";
                dialog.dismiss();
                refersh();
            }
        });
        tv_item3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iv_logo.setVisibility(View.GONE);
                sex = "不限";
                dialog.dismiss();
                refersh();
            }
        });
        tv_item4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                clearLocation();

            }
        });
        tv_item5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        //获得dialog的window窗口
        Window window = dialog.getWindow();
        //设置dialog在屏幕底部
        window.setGravity(Gravity.BOTTOM);
        //设置dialog弹出时的动画效果，从屏幕底部向上弹出
        window.setWindowAnimations(R.style.mypopwindow_anim_style);
        window.getDecorView().setPadding(0, 0, 0, 0);
        //获得window窗口的属性
        android.view.WindowManager.LayoutParams lp = window.getAttributes();
        //设置窗口宽度为充满全屏
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        //设置窗口高度为包裹内容
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        //将设置好的属性set回去
        window.setAttributes(lp);
        //将自定义布局加载到dialog上
        dialog.setContentView(dialogView);
        dialog.show();
    }

    private void refersh() {
        page = 1;
        mNearbies.clear();
        mNearByAdapter.setNewData(mNearbies);
        getData();
    }
}
