package com.xihubao;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.yunchebao.MyApplication;


import com.costans.PlatformContans;

import com.example.yunchebao.R;
import com.google.gson.Gson;

import com.example.yunchebao.maket.model.LoadMoreListView;
import com.nohttp.NoHttp;
import com.nohttp.RequestMethod;
import com.nohttp.rest.Request;
import com.nohttp.rest.Response;
import com.nohttp.sample.HttpListener;
import com.nohttp.sample.NoHttpBaseActivity;
import com.example.yunchebao.rongcloud.model.CarShop;
import com.tool.ActivityAnimationUtils;
import com.tool.ActivityConstans;
import com.tool.UIControlUtils;
import com.vipcenter.RegisterActivity;
import com.xihubao.adapter.WashCarListAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by sdhcjhss on 2017/12/7.
 * 洗车店列表页
 */

public class RepairListActivity extends NoHttpBaseActivity  {
    @BindView(R.id.rl_left)
    LinearLayout rl_left;
    @BindView(R.id.rl_right)
    LinearLayout rl_right;
    @BindView(R.id.lv_car)
    LoadMoreListView lv_car;
    @BindView(R.id.tv_type)
    TextView tv_type;
    List<CarShop> list;

    WashCarListAdapter adapter;
    int page = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.washcar_shoplist);
        ButterKnife.bind(this);
        initView();
    }
    PopupWindow popupWindow;
    private void showSortPopupWindow() {
        View view = LayoutInflater.from(this).inflate(R.layout.popup_dengji, null);
        popupWindow = new PopupWindow(view, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.showAsDropDown(rl_right);
        popupWindow.setOutsideTouchable(false);
    }
    private void initView() {
        UIControlUtils.UITextControlsUtils.setUIText(findViewById(R.id.title), ActivityConstans.UITag.TEXT_VIEW, "修车店");

        list = new ArrayList<>();
        adapter=new WashCarListAdapter(this,list);
        lv_car.setAdapter(adapter);
//
        lv_car.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bundle bundle = new Bundle();
                Log.e("pos", position + "");
                bundle.putSerializable("data", list.get(position));
                bundle.putString("type", "修车店");
                bundle.putInt("flag", 2);
                if (MyApplication.isLogin)
                    ActivityAnimationUtils.commonTransition(RepairListActivity.this, WashCarDetailActivity.class, ActivityConstans.Animation.FADE, bundle);
                else {
                    startActivity(new Intent(RepairListActivity.this, RegisterActivity.class));
                }
            }
        });
        lv_car.setOnLoadMoreListener(new LoadMoreListView.OnLoadMoreListener() {
            @Override
            public void onloadMore() {
                page++;
                getData();
                lv_car.setLoadCompleted();
            }
        });
        rl_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (popupWindow == null)
                    showSortPopupWindow();
                else {
                    if (popupWindow.isShowing()) {
                        popupWindow.dismiss();
                    }else{
                        showSortPopupWindow();
                    }
                }
            }
        });
        getData();
    }

    private void getData() {

        Request<String> request = null;
        request = NoHttp.createStringRequest(PlatformContans.CarWashRepairShop
                .getCarWashRepairShopListByApp, RequestMethod.GET);
        request.add("page", page);
        request.add("type", 2);
        request.add("grade", 1);
        request.add("longitude", MyApplication.getaMapLocation().getLongitude());
        request.add("latitude", MyApplication.getaMapLocation().getLatitude());
        request.add("city", MyApplication.getaMapLocation().getCity());
        request(0, request, httpListener, true, true);
    }

    private HttpListener<String> httpListener = new HttpListener<String>() {
        @Override
        public void onSucceed(int what, Response response) throws JSONException {

            String res = response.get().toString();
            Log.e("res", res);
            try {
                JSONObject jsonObject = new JSONObject(res);
                jsonObject = jsonObject.getJSONObject("data");
                JSONArray data = jsonObject.getJSONArray("beanList");
                for (int i = 0; i < data.length(); i++) {
                    JSONObject item = data.getJSONObject(i);
                    CarShop carShop = new Gson().fromJson(item.toString(), CarShop.class);
                    list.add(carShop);
                }
                adapter.notifyDataSetChanged();

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

        @Override
        public void onFailed(int what, Response<String> response) {

        }
    };


    @OnClick({R.id.back})
    public void OnClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                onBackPressed();
                break;
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
}
