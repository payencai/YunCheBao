package com.example.yunchebao.cheyibao.newcar;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.caryibao.NewCar;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.cheyibao.OldCarSelectActivity;
import com.cheyibao.SelectCarBrandActivity;
import com.cheyibao.adapter.CarCommomAdapter;
import com.cheyibao.adapter.NewcarListAdapter;
import com.cheyibao.model.NewCarMenu;
import com.cheyibao.view.PriceSelectWindow;
import com.cheyibao.view.TypeSelectWindow;
import com.costans.PlatformContans;
import com.example.yunchebao.R;
import com.google.gson.Gson;
import com.gyf.immersionbar.ImmersionBar;
import com.http.HttpProxy;
import com.http.ICallBack;
import com.nohttp.sample.NoHttpBaseActivity;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.tool.ActivityAnimationUtils;
import com.tool.ActivityConstans;
import com.tool.view.GridViewForScrollView;
import com.xihubao.model.CarBrand;

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
import co.lujun.androidtagview.TagContainerLayout;
import co.lujun.androidtagview.TagView;

/**
 * Created by sdhcjhss on 2017/12/28.
 */

public class NewCarListActivity extends NoHttpBaseActivity {
    private List<NewCar> mNewCars;
    NewcarListAdapter mAdapter;
    NewCarMenu mNewCarMenu;
    @BindView(R.id.rv_car)
    RecyclerView rv_car;
    @BindView(R.id.refresh)
    SmartRefreshLayout  mRefreshLayout;
    int page = 1;
    boolean isLoadMore=false;

    @BindView(R.id.cityName)
    TextView cityName;
    List<String> tagStrList;
    @BindView(R.id.tagcontainerLayout)
    TagContainerLayout mTagContainerLayout;
    @BindView(R.id.menu1)
    TextView menuText1;
    @BindView(R.id.ll_select)
    LinearLayout ll_select;
    int flag;
    CarBrand mCarBrand;
    String firstId = "";//品牌Id;
    String startprice = "";
    String endprice = "";
    String brand = "";
    String displacement = "";
    String fuel = "";
    String color = "";
    String variableBox = "";
    String seat = "";
    String country = "";
    String models = "";
    String money;
    int type = 0;//个人商家
    int orderByClause = 0;
    String sort;
    String price ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_car_list_layout);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            flag = bundle.getInt("flag");
            mNewCarMenu = (NewCarMenu) bundle.getSerializable("menu");
            mCarBrand = (CarBrand) bundle.getSerializable("brand");
        }
        ButterKnife.bind(this);
        ImmersionBar.with(this).autoDarkModeEnable(true).fitsSystemWindows(true).statusBarColor(R.color.white).init();
        initView();
    }

    private void getData() {
        //UserInfo userInfo= MyApplication.getUserInfo();
        Map<String, Object> params = new HashMap<>();
        params.put("page", page);
        if (!TextUtils.isEmpty(firstId)) {
            params.put("firstId", firstId);
        }

        if (!TextUtils.isEmpty(fuel)) {
            params.put("fuel", fuel);
        }
        if (!TextUtils.isEmpty(color)) {
            params.put("color", color);
        }
        if (!TextUtils.isEmpty(seat)) {
            params.put("seat", seat);
        }
        if (!TextUtils.isEmpty(models)) {
            params.put("models", models);
        }
        if (!TextUtils.isEmpty(displacement)) {
            params.put("displacement", displacement);
        }
        if (!TextUtils.isEmpty(variableBox)) {
            params.put("variableBox", variableBox);
        }
        if (!TextUtils.isEmpty(country)) {
            params.put("country", country);
        }

        if (!TextUtils.isEmpty(startprice)) {
            params.put("startprice", startprice);
        }
        if (!TextUtils.isEmpty(endprice)) {
            params.put("endprice", endprice);
        }
        if (orderByClause > 0) {
            params.put("orderByClause", orderByClause);
        }
        Log.e("params", params.toString());
        HttpProxy.obtain().get(PlatformContans.NewCar.getNewCarMerchantMessage, params, "", new ICallBack() {
            @Override
            public void OnSuccess(String result) {
                mRefreshLayout.finishRefresh();
                Log.e("getnewcar", result);
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    jsonObject = jsonObject.getJSONObject("data");
                    JSONArray data = jsonObject.getJSONArray("beanList");
                    List<NewCar>  newCars=new ArrayList<>();
                    for (int i = 0; i < data.length(); i++) {
                        JSONObject item = data.getJSONObject(i);
                        NewCar newCar = new Gson().fromJson(item.toString(), NewCar.class);
                        mNewCars.add(newCar);
                        newCars.add(newCar);
                    }
                    if(isLoadMore){
                        isLoadMore=false;
                        if(data.length()==0){
                            mAdapter.loadMoreEnd(true);
                        }else{
                            mAdapter.addData(newCars);
                            mAdapter.loadMoreComplete();
                        }
                    }else{
                        mAdapter.setNewData(mNewCars);
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

    private void initTag() {
        tagStrList = new ArrayList<>();
        switch (flag) {
            case 1:
                startprice = "0";
                endprice = "100000";
                money = "10万以下";
                break;
            case 2:
                startprice = "100000";
                endprice = "250000";
                money = "10-25万";

                break;
            case 3:
                startprice = "250000";
                endprice = "400000";
                money = "25-40万";
                //tagStrList.add("25-40万");
                break;
            case 4:
                startprice = "400000";
                endprice = "550000";
                money = "40-55万";
                // tagStrList.add("40-55万");
                break;
            case 5:
                startprice = "550000";
                endprice = "700000";
                money = "55-70万";
                // tagStrList.add("55-70万");
                break;
            case 6:
                startprice = "700000";
                endprice = "850000";
                money = "70-85万";
                //tagStrList.add("70-85万");
                break;
            case 7:
                startprice = "850000";
                endprice = "1000000";
                money = "85-100万";
                //tagStrList.add("85-100万");
                break;
            case 8:
                startprice = "1000000";
                endprice = "100000000";
                money = "100万以上";
                //tagStrList.add("100万以上");
                break;
            case 9:
                break;
            case 10:
                tagStrList.add(mCarBrand.getName());
                firstId = mCarBrand.getFistId();
                break;

        }
        if (!TextUtils.isEmpty(money)) {
            tagStrList.add(money);
        }
        mTagContainerLayout.setTags(tagStrList);
        mTagContainerLayout.setOnTagClickListener(new TagView.OnTagClickListener() {

            @Override
            public void onTagClick(int position, String text) {

            }

            @Override
            public void onTagLongClick(final int position, String text) {

            }



            @Override
            public void onTagCrossClick(int position) {
                String text = tagStrList.get(position);
                tagStrList.remove(text);
                mTagContainerLayout.removeTag(position);
                Log.e("text", text);
                if (text.equals(brand)) {
                    firstId = "";
                }
                if (text.equals("价格最低") || text.equals("价格最高") || text.equals("车龄最短") || text.equals("里程最少")) {
                    orderByClause = 0;

                }
                if(text.equals(money)){
                    startprice="";
                    endprice="";
                    money="";

                }
                if (text.equals(fuel)) {
                    fuel = "";
                }
                if (text.equals(displacement)) {
                    displacement = "";
                }
                if (text.equals(color)) {
                    color = "";
                }
                if (text.equals(seat)) {
                    seat = "";
                }
                if (text.equals(variableBox)) {
                    variableBox = "";
                }
                if (text.equals(country)) {
                    country = "";
                }
                if (text.equals(models)) {
                    models = "";
                }
                refreshAllData();

            }
        });
    }
    private void refreshAllData(){
        page=1;
        mNewCars.clear();
        mAdapter.setNewData(mNewCars);
        getData();
    }

    private void initRecycleview(){
        mNewCars = new ArrayList<>();
        mAdapter = new NewcarListAdapter(mNewCars);
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                NewCar newCar= (NewCar) adapter.getItem(position);
                Bundle bundle = new Bundle();
                bundle.putSerializable("data", newCar);
                ActivityAnimationUtils.commonTransition(NewCarListActivity.this, NewCarDetailActivity.class, ActivityConstans.Animation.FADE, bundle);
            }
        });
        mAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                page++;
                isLoadMore=true;
                getData();
            }
        },rv_car);
        rv_car.setLayoutManager(new LinearLayoutManager(this));
        rv_car.setAdapter(mAdapter);
    }
    private void initView() {
        //cityName.setText(MyApplication.getaMapLocation().getCity());
        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                refreshAllData();
            }
        });
        initTag();
        initRecycleview();
        getData();
    }





    private void showTypeWindow() {
        TypeSelectWindow typeSelectWindow = new TypeSelectWindow(this);
        typeSelectWindow.showPopupWindow(ll_select);
        View view = typeSelectWindow.getContentView();
        TextView tv_pricemin = (TextView) view.findViewById(R.id.item1);
        TextView tv_pricemax = (TextView) view.findViewById(R.id.item2);
        TextView tv_carage = (TextView) view.findViewById(R.id.item3);
        TextView tv_dis = (TextView) view.findViewById(R.id.item4);
        tv_pricemin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                orderByClause = 1;
                mTagContainerLayout.removeAllTags();
                typeSelectWindow.dismissPopup();
                if (tagStrList.size() > 0) {
                    if (!tagStrList.contains("价格最低") && !tagStrList.contains("价格最高") && !tagStrList.contains("车龄最短") && !tagStrList.contains("里程最少")) {
                        tagStrList.add("价格最低");
                    }
                    for (int i = 0; i < tagStrList.size(); i++) {
                        String tag = tagStrList.get(i);
                        if (tag.equals("价格最低") || tag.equals("价格最高") || tag.equals("车龄最短") || tag.equals("里程最少")) {
                            tagStrList.remove(tag);
                            tagStrList.add("价格最低");
                            break;
                        }
                    }
                } else {
                    tagStrList.add("价格最低");
                }
                mTagContainerLayout.setTags(tagStrList);
                refreshAllData();


            }
        });
        tv_pricemax.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                orderByClause = 2;
                mTagContainerLayout.removeAllTags();
                typeSelectWindow.dismissPopup();
                if (tagStrList.size() > 0) {
                    if (!tagStrList.contains("价格最低") && !tagStrList.contains("价格最高") && !tagStrList.contains("车龄最短") && !tagStrList.contains("里程最少")) {
                        tagStrList.add("价格最高");
                    }
                    for (int i = 0; i < tagStrList.size(); i++) {
                        String tag = tagStrList.get(i);
                        if (tag.equals("价格最低") || tag.equals("价格最高") || tag.equals("车龄最短") || tag.equals("里程最少")) {
                            tagStrList.remove(tag);
                            tagStrList.add("价格最高");
                            break;
                        }
                    }
                } else {
                    tagStrList.add("价格最高");
                }
                mTagContainerLayout.setTags(tagStrList);
                refreshAllData();
            }
        });
        tv_carage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                orderByClause = 3;
                mTagContainerLayout.removeAllTags();
                typeSelectWindow.dismissPopup();
                if (tagStrList.size() > 0) {
                    if (!tagStrList.contains("价格最低") && !tagStrList.contains("价格最高") && !tagStrList.contains("车龄最短") && !tagStrList.contains("里程最少")) {
                        tagStrList.add("车龄最短");
                    }
                    for (int i = 0; i < tagStrList.size(); i++) {
                        String tag = tagStrList.get(i);
                        if (tag.equals("价格最低") || tag.equals("价格最高") || tag.equals("车龄最短") || tag.equals("里程最少")) {
                            tagStrList.remove(tag);
                            tagStrList.add("车龄最短");
                            break;
                        }
                    }
                } else {
                    tagStrList.add("车龄最短");
                }
                mTagContainerLayout.setTags(tagStrList);
                refreshAllData();
            }
        });
        tv_dis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                orderByClause = 4;
                mTagContainerLayout.removeAllTags();
                typeSelectWindow.dismissPopup();
                if (tagStrList.size() > 0) {
                    if (!tagStrList.contains("价格最低") && !tagStrList.contains("价格最高") && !tagStrList.contains("车龄最短") && !tagStrList.contains("里程最少")) {
                        tagStrList.add("里程最少");
                    }
                    for (int i = 0; i < tagStrList.size(); i++) {
                        String tag = tagStrList.get(i);
                        if (tag.equals("价格最低") || tag.equals("价格最高") || tag.equals("车龄最短") || tag.equals("里程最少")) {
                            tagStrList.remove(tag);
                            tagStrList.add("里程最少");
                            break;
                        }
                    }
                } else {
                    tagStrList.add("里程最少");
                }
                mTagContainerLayout.setTags(tagStrList);
                refreshAllData();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && data != null) {
            mTagContainerLayout.removeAllTags();
            String name = data.getStringExtra("name");
            firstId = data.getStringExtra("id");
            if (TextUtils.isEmpty(brand)) {
                brand = name;
                tagStrList.add(brand);
            } else {
                tagStrList.remove(brand);
                brand = name;
                tagStrList.add(brand);
            }
            mTagContainerLayout.setTags(tagStrList);
            refreshAllData();
        }
        if (requestCode == 2 && data != null) {
            tagStrList.clear();
            mTagContainerLayout.removeAllTags();
            displacement = data.getStringExtra("displacement");
            fuel = data.getStringExtra("fuel");
            color = data.getStringExtra("color");
            variableBox = data.getStringExtra("variableBox");
            seat = data.getStringExtra("seat");
            country = data.getStringExtra("country");
            models = data.getStringExtra("models");
            if (!TextUtils.isEmpty(displacement)) {
                tagStrList.add(displacement);
            }
            if (!TextUtils.isEmpty(fuel)) {
                tagStrList.add(fuel);
            }
            if (!TextUtils.isEmpty(color)) {
                tagStrList.add(color);
            }
            if (!TextUtils.isEmpty(variableBox)) {
                tagStrList.add(variableBox);
            }
            if (!TextUtils.isEmpty(seat)) {
                tagStrList.add(seat);
            }
            if (!TextUtils.isEmpty(country)) {
                tagStrList.add(country);
            }
            if (!TextUtils.isEmpty(models)) {
                tagStrList.add(models);
            }
            if (!TextUtils.isEmpty(sort)) {
                tagStrList.add(sort);
            }
            if (!TextUtils.isEmpty(money)) {
                tagStrList.add(money);
            }

            if (!TextUtils.isEmpty(brand)) {
                tagStrList.add(brand);
            }
            mTagContainerLayout.setTags(tagStrList);
            refreshAllData();
        }
    }

    private void showPriceWindow() {
        price="不限";
        List<String> listDatas = new ArrayList<>();
        listDatas.add("不限");
        listDatas.add("10万以下");
        listDatas.add("10-25万");
        listDatas.add("25-40万");
        listDatas.add("40-60万");
        listDatas.add("60-100万");
        listDatas.add("100万以上");
        CarCommomAdapter carCommomAdapte = new CarCommomAdapter(this, listDatas);
        PriceSelectWindow typeSelectWindow = new PriceSelectWindow(this);
        typeSelectWindow.showPopupWindow(ll_select);
        View view = typeSelectWindow.getContentView();
        TextView tv_confirm = (TextView) view.findViewById(R.id.tv_confirm);
        tv_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                typeSelectWindow.dismissPopup();

                if (!TextUtils.isEmpty(money)) {
                    tagStrList.remove(money);
                    if (!TextUtils.equals(price, "不限")) {
                        tagStrList.add(price);
                        money=price;
                        switch (money) {
                            case "10万以下":
                                startprice = "0";
                                endprice = "100000";
                                break;
                            case "10-25万":
                                startprice = "100000";
                                endprice = "250000";
                                break;
                            case "25-40万":
                                endprice = "400000";
                                startprice = "250000";
                                break;
                            case "40-60万":
                                startprice = "400000";
                                endprice = "600000";
                                break;
                            case "60-100万":
                                endprice = "1000000";
                                startprice = "600000";
                                break;
                            case "100万以上":
                                startprice = "1000000";
                                endprice = "100000000";
                                break;
                        }

                    } else {
                        startprice = "";
                        endprice = "";
                        money="";
                    }
                    mTagContainerLayout.removeAllTags();
                    mTagContainerLayout.setTags(tagStrList);
                } else {
                    if (!TextUtils.equals(price, "不限")) {
                        tagStrList.add(price);
                        money=price;
                        mTagContainerLayout.addTag(price);
                        switch (price) {
                            case "10万以下":
                                startprice = "0";
                                endprice = "100000";
                                break;
                            case "10-25万":
                                startprice = "100000";
                                endprice = "250000";
                                break;
                            case "25-40万":
                                endprice = "400000";
                                startprice = "250000";
                                break;
                            case "40-60万":
                                startprice = "400000";
                                endprice = "600000";
                                break;
                            case "60-100万":
                                endprice = "1000000";
                                startprice = "600000";
                                break;
                            case "100万以上":
                                startprice = "1000000";
                                endprice = "100000000";
                                break;
                        }

                    }
                }
                refreshAllData();
            }
        });
        GridViewForScrollView gridViewForScrollView = (GridViewForScrollView) view.findViewById(R.id.sg_price);
        gridViewForScrollView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                carCommomAdapte.setPos(position);
                carCommomAdapte.notifyDataSetChanged();
                price = listDatas.get(position);
            }
        });
        gridViewForScrollView.setAdapter(carCommomAdapte);

    }


    @OnClick({R.id.back, R.id.menu1, R.id.menu2, R.id.menu3, R.id.menu4, R.id.rechargeBtn})
    public void OnClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                onBackPressed();
                break;

            case R.id.menu1:
                showTypeWindow();
                break;
            case R.id.menu2:
                startActivityForResult(new Intent(NewCarListActivity.this, SelectCarBrandActivity.class), 1);
                break;
            case R.id.menu3:
                showPriceWindow();
                break;
            case R.id.menu4:
                startActivityForResult(new Intent(NewCarListActivity.this, OldCarSelectActivity.class), 2);
                break;
            case R.id.rechargeBtn:
                tagStrList.clear();
                mTagContainerLayout.removeAllTags();
                orderByClause = 0;
                firstId = "";
                startprice = "";
                startprice = "";
                brand = "";
                displacement = "";
                fuel = "";
                color = "";
                variableBox = "";
                seat = "";
                country = "";
                models = "";
                refreshAllData();

                break;
        }
    }



}
