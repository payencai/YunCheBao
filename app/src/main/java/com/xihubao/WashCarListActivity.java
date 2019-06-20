package com.xihubao;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.yunchebao.MyApplication;


import com.cheyibao.model.Area;
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
import com.tool.JsonUtil;
import com.tool.UIControlUtils;
import com.vipcenter.RegisterActivity;
import com.vipcenter.adapter.CityAdapter;
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

public class WashCarListActivity extends NoHttpBaseActivity {
    @BindView(R.id.tv_type)
    TextView tv_type;
    @BindView(R.id.rl_left)
    LinearLayout rl_left;
    @BindView(R.id.rl_right)
    LinearLayout rl_right;
    @BindView(R.id.lv_car)
    LoadMoreListView lv_car;
    @BindView(R.id.tv_city)
    TextView tv_city;
    @BindView(R.id.view)
     View line;
    List<CarShop> list;
    WashCarListAdapter adapter;
    int page = 1;
    int grade = 1;
    int type=1;
    List<Area> mAreas = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.washcar_shoplist);
        type=getIntent().getExtras().getInt("type");
        ButterKnife.bind(this);
        initView();

    }

    private void getJsonData() {
        String cityCode = "440110";
        if (MyApplication.getaMapLocation() != null)
            if(!TextUtils.isEmpty(MyApplication.getaMapLocation().getAdCode())&&MyApplication.getaMapLocation().getAdCode().length()>4)
               cityCode = MyApplication.getaMapLocation().getAdCode().substring(0, 4) + "00";
        String json = JsonUtil.getJson(this, "area.json");
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray areas = jsonObject.getJSONArray(cityCode);
            Log.e("areas", areas.length() + "");
            for (int i = 0; i < areas.length(); i++) {
                JSONObject item = areas.getJSONObject(i);
                Area area = new Gson().fromJson(item.toString(), Area.class);
                mAreas.add(area);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    PopupWindow popupWindowcity;

    private void showCityPopupWindow() {
        View view = LayoutInflater.from(this).inflate(R.layout.popup_city, null);
        ListView lv_city = (ListView) view.findViewById(R.id.lv_city);
        CityAdapter mcityAdapter = new CityAdapter(this, mAreas);
        mcityAdapter.setPos(pos);
        lv_city.setAdapter(mcityAdapter);
        lv_city.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                pos = position;
                mcityAdapter.setPos(position);
                mcityAdapter.notifyDataSetChanged();
                String city = mAreas.get(position).getName();
                tv_city.setText(city);
                popupWindowcity.dismiss();
                list.clear();
                getData();
            }
        });
        popupWindowcity = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindowcity.setAnimationStyle(R.style.PopDown);
        popupWindowcity.showAsDropDown(line);


    }

    PopupWindow popupWindow;

    private void showSortPopupWindow() {
        View view = LayoutInflater.from(this).inflate(R.layout.popup_dengji, null);
        TextView item1 = (TextView) view.findViewById(R.id.item1);
        TextView item2 = (TextView) view.findViewById(R.id.item2);
        TextView item3 = (TextView) view.findViewById(R.id.item3);
        TextView item4 = (TextView) view.findViewById(R.id.item4);
        item1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv_type.setText("距离最近");
                grade = 1;
                list.clear();
                adapter.notifyDataSetChanged();
                getData();
                popupWindow.dismiss();
            }
        });
        item2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
                tv_type.setText("评分优先");
                grade = 2;
                list.clear();
                adapter.notifyDataSetChanged();
                getData();
            }
        });
        item3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
                tv_type.setText("等级优先");
                grade = 3;
                list.clear();
                adapter.notifyDataSetChanged();
                getData();
            }
        });
        item4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
                grade = 4;
                tv_type.setText("订单优先");
                list.clear();
                adapter.notifyDataSetChanged();
                getData();
            }
        });
        popupWindow = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.showAsDropDown(line);
        popupWindow.setAnimationStyle(R.style.PopDown);
        popupWindow.setOutsideTouchable(true);
    }

    int pos = 0;

    private void initView() {
        String name="";
        if(type==1){
            name="洗车店";
        }else{
            name="修理店";
        }
        UIControlUtils.UITextControlsUtils.setUIText(findViewById(R.id.title), ActivityConstans.UITag.TEXT_VIEW, name);
        list = new ArrayList<>();
        adapter = new WashCarListAdapter(this, list);
        lv_car.setAdapter(adapter);
//
        if(MyApplication.getaMapLocation()!=null)
           tv_city.setText(MyApplication.getaMapLocation().getDistrict());
        lv_car.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bundle bundle = new Bundle();
                Log.e("pos", position + "");
                bundle.putSerializable("data", list.get(position));
                bundle.putString("type", "洗车店");
                bundle.putInt("flag",1);
                if (MyApplication.isLogin)
                    ActivityAnimationUtils.commonTransition(WashCarListActivity.this, WashCarDetailActivity.class, ActivityConstans.Animation.FADE, bundle);
                else {
                    startActivity(new Intent(WashCarListActivity.this, RegisterActivity.class));
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
        rl_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (popupWindow != null) {
                    if (popupWindow.isShowing()) {
                        popupWindow.dismiss();
                    }
                }
                if (popupWindowcity == null)
                    showCityPopupWindow();
                else {
                    if (popupWindowcity.isShowing()) {
                        popupWindowcity.dismiss();
                    } else {
                        showCityPopupWindow();
                    }
                }
            }
        });
        rl_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (popupWindowcity != null) {
                    if (popupWindowcity.isShowing()) {
                        popupWindowcity.dismiss();
                    }
                }
                if (popupWindow == null)
                    showSortPopupWindow();
                else {
                    if (popupWindow.isShowing()) {
                        popupWindow.dismiss();
                    } else {
                        showSortPopupWindow();
                    }
                }
            }
        });
        getJsonData();
        getData();
    }


    private void getData() {

        Request<String> request = null;
        request = NoHttp.createStringRequest(PlatformContans.CarWashRepairShop
                .getCarWashRepairShopListByApp, RequestMethod.GET);
        request.add("page", page);
        request.add("type", type);
        request.add("orderByClause", grade);
        request.add("longitude", MyApplication.getaMapLocation().getLongitude());
        request.add("latitude", MyApplication.getaMapLocation().getLatitude());
        request.add("province",MyApplication.getaMapLocation().getProvince());
        request.add("city", MyApplication.getaMapLocation().getCity());
        request.add("area",tv_city.getText().toString());
        Log.e("params",type+"");
        request(0, request, httpListener, true, true);
    }

    private HttpListener<String> httpListener = new HttpListener<String>() {
        @Override
        public void onSucceed(int what, Response response) throws JSONException {

            String res = response.get().toString();
            Log.e("res", res);
            try {
                JSONObject jsonObject = new JSONObject(res);
                JSONArray data = jsonObject.getJSONArray("data");
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
