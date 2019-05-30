package com.example.yunchebao.gasstation;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.example.yunchebao.MyApplication;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.cheyibao.model.Area;
import com.costans.PlatformContans;
import com.example.yunchebao.R;
import com.example.yunchebao.fourshop.bean.DropdownItemObject;
import com.example.yunchebao.fourshop.view.DropdownButton;
import com.example.yunchebao.fourshop.view.DropdownListView;
import com.example.yunchebao.gasstation.adapter.GasStationAdapter;
import com.example.yunchebao.gasstation.model.GasStation;
import com.google.gson.Gson;
import com.gyf.immersionbar.ImmersionBar;
import com.http.HttpProxy;
import com.http.ICallBack;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.tool.JsonUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GasStationActivity extends AppCompatActivity implements DropdownListView.Container {

    DropdownListView currentDropdownList;
    Animation dropdown_in, dropdown_out, dropdown_mask_out;
    private List<DropdownItemObject> chooseTypeData = new ArrayList<>();//选择类型
    private List<DropdownItemObject> chooseLanguageData = new ArrayList<>();//选择语言
    @BindView(R.id.rv_order)
    RecyclerView mRecyclerView;
    @BindView(R.id.chooseType)
    DropdownButton chooseType;
    @BindView(R.id.chooseLanguage)
    DropdownButton chooseLanguage;
    @BindView(R.id.dropdownType)
    DropdownListView dropdownType;
    @BindView(R.id.dropdownLanguage)
    DropdownListView dropdownLanguage;
    @BindView(R.id.refresh)
    SmartRefreshLayout refersh;
    String city;
    String province;
    String area;
    int type = 1;
    int page = 1;
    boolean isLoadMore = false;
    @BindView(R.id.mask)
    View mask;
    List<GasStation> mShopDataList;
    GasStationAdapter mAdapter;
    List<Area> mAreas = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gas_station);
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
        initDropDownMenu();
        initAdapter();
        getData();
    }


    @Override
    public void show(DropdownListView view) {
        if (currentDropdownList != null) {
            currentDropdownList.clearAnimation();
            currentDropdownList.startAnimation(dropdown_out);
            currentDropdownList.setVisibility(View.GONE);
            currentDropdownList.button.setChecked(false);
        }
        currentDropdownList = view;
        mask.clearAnimation();
        mask.setVisibility(View.VISIBLE);
        currentDropdownList.clearAnimation();
        currentDropdownList.startAnimation(dropdown_in);
        currentDropdownList.setVisibility(View.VISIBLE);
        currentDropdownList.button.setChecked(true);
    }

    @Override
    public void hide() {
        if (currentDropdownList != null) {
            currentDropdownList.clearAnimation();
            currentDropdownList.startAnimation(dropdown_out);
            currentDropdownList.button.setChecked(false);
            mask.clearAnimation();
            mask.startAnimation(dropdown_mask_out);
        }
        currentDropdownList = null;
    }

    @Override
    public void onSelectionChanged(DropdownListView dropdownListView) {
        page = 1;
        if (dropdownListView == dropdownType) {
            if (dropdownType.current.id == 0) {
                area = "";
                province = "";
                city = "";
            } else {
                area = dropdownType.current.value;
                province = MyApplication.getaMapLocation().getProvince();
                city = MyApplication.getaMapLocation().getCity();
            }
            mShopDataList.clear();
            mAdapter.setNewData(mShopDataList);
            getData();
        } else {
            int id = dropdownLanguage.current.id;
            switch (id) {
                case 0:
                    type = 1;
                    break;
                case 1:
                    type = 2;
                    break;
                case 2:
                    type = 3;
                    break;
                case 3:
                    type = 4;
                    break;
            }
            mShopDataList.clear();
            mAdapter.setNewData(mShopDataList);
            getData();
        }
    }

    private void getData() {
        Map<String, Object> params = new HashMap<>();
        params.put("page", page);
        params.put("sort", type);
        if (!TextUtils.isEmpty(province))
            params.put("province", province);
        if (!TextUtils.isEmpty(city))
            params.put("city", city);
        if (!TextUtils.isEmpty(area))
            params.put("area", area);
        params.put("longitude", MyApplication.getaMapLocation().getLongitude());
        params.put("latitude", MyApplication.getaMapLocation().getLatitude());
        Log.e("params",params.toString());
        HttpProxy.obtain().get(PlatformContans.Station.getGasStationShopListByApp, params, "", new ICallBack() {
            @Override
            public void OnSuccess(String result) {
                refersh.finishRefresh();
                Log.e("getFourShopListByApp", result);
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    JSONArray data = jsonObject.getJSONArray("data");
                    List<GasStation> replaceDrives = new ArrayList<>();
                    for (int i = 0; i < data.length(); i++) {
                        JSONObject item = data.getJSONObject(i);
                        GasStation replaceDrive = new Gson().fromJson(item.toString(), GasStation.class);
                        mShopDataList.add(replaceDrive);
                        replaceDrives.add(replaceDrive);
                    }
                    if (isLoadMore) {
                        isLoadMore = false;
                        if (data.length() == 0) {
                            mAdapter.loadMoreEnd(true);
                        } else {
                            mAdapter.addData(replaceDrives);
                            mAdapter.loadMoreComplete();
                        }
                    } else {
                        mAdapter.setNewData(mShopDataList);

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

    void reset() {
        chooseType.setChecked(false);
        chooseLanguage.setChecked(false);
        dropdownType.setVisibility(View.GONE);
        dropdownLanguage.setVisibility(View.GONE);
        mask.setVisibility(View.GONE);
        dropdownType.clearAnimation();
        dropdownLanguage.clearAnimation();
        mask.clearAnimation();
    }

    private void bindCity() {
        String cityCode = "440110";
        if (MyApplication.getaMapLocation() != null)
            if (!TextUtils.isEmpty(MyApplication.getaMapLocation().getAdCode()) && MyApplication.getaMapLocation().getAdCode().length() > 4)
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
                int j = i + 1;
                chooseTypeData.add(new DropdownItemObject(area.getName(), j, area.getName()));
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private void bindSelectType() {
        chooseLanguageData.add(new DropdownItemObject("距离最近", 0, "距离最近"));
        chooseLanguageData.add(new DropdownItemObject("评分最高", 1, "评分最高"));

        dropdownLanguage.bind(chooseLanguageData, chooseLanguage, this, 0);
    }

    void initDropDownMenu() {
        dropdown_in = AnimationUtils.loadAnimation(this, R.anim.dropdown_in);
        dropdown_out = AnimationUtils.loadAnimation(this, R.anim.dropdown_out);
        dropdown_mask_out = AnimationUtils.loadAnimation(this, R.anim.dropdown_mask_out);
        reset();
        bindSelectType();
        chooseTypeData.add(new DropdownItemObject("全部", 0, "全部"));
        bindCity();
        dropdownType.bind(chooseTypeData, chooseType, this, 0);
        mask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hide();
            }
        });
        dropdown_mask_out.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                if (currentDropdownList == null) {
                    reset();
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

    }

    private void initAdapter() {
        mShopDataList = new ArrayList<>();
        mAdapter = new GasStationAdapter(R.layout.item_search_wash, mShopDataList);
        mAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                page++;
                isLoadMore = true;
                getData();
            }
        }, mRecyclerView);
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                GasStation fourShopData= (GasStation) adapter.getItem(position);
                Intent intent=new Intent(GasStationActivity.this, GasStationDetailActivity.class);
                intent.putExtra("id",fourShopData.getId());
                startActivity(intent);
            }
        });
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mAdapter);//设置adapter
        refersh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                page = 1;
                mShopDataList.clear();
                mAdapter.setNewData(mShopDataList);
                getData();
            }
        });

    }
}
