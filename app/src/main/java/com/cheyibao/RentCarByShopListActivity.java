package com.cheyibao;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.yunchebao.MyApplication;
import com.cheyibao.adapter.AreaAdapter;
import com.cheyibao.adapter.RentShopAdapter;
import com.cheyibao.model.Area;
import com.cheyibao.model.RentShop;
import com.cheyibao.util.RentCarUtils;
import com.common.BaseModel;
import com.common.EndLoadDataType;
import com.common.HandlerData;
import com.common.LoadDataType;
import com.common.MultipleStatusView;
import com.coorchice.library.SuperTextView;
import com.costans.PlatformContans;
import com.example.yunchebao.R;
import com.example.yunchebao.cheyibao.rentcar.ShopDetailActivity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.http.HttpProxy;
import com.http.ICallBack;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.tool.JsonUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RentCarByShopListActivity extends AppCompatActivity {

    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.searchLay)
    LinearLayout searchLay;
    @BindView(R.id.superText)
    SuperTextView superText;
    @BindView(R.id.textBtn)
    TextView textBtn;
    @BindView(R.id.shareBtn)
    ImageView shareBtn;
    @BindView(R.id.shopCartBtn)
    ImageView shopCartBtn;
    @BindView(R.id.menuBtn)
    ImageView menuBtn;
    @BindView(R.id.userBtn)
    ImageView userBtn;
    @BindView(R.id.area_list_view)
    RecyclerView areaListView;
    @BindView(R.id.shop_list_view)
    RecyclerView shopListView;
    @BindView(R.id.area_name_text_view)
    TextView areaNameTextView;
    @BindView(R.id.multiple_status_view)
    MultipleStatusView multipleStatusView;
    @BindView(R.id.refresh_layout)
    SmartRefreshLayout refreshLayout;
    private String cityCode;
    private AreaAdapter areaAdapter;

    private RentShopAdapter rentShopAdapter;

    private int page = 1;
    private int type = 1;
    private String area = "";



    List<Area> areaList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stop_list);
        ButterKnife.bind(this);
        title.setText("选择门店");
        String adcode = MyApplication.getaMapLocation().getAdCode();
        if (!TextUtils.isEmpty(adcode)) {
            cityCode = MyApplication.getaMapLocation().getAdCode().substring(0, 4) + "00";
        }
        init();
        getJsonData();
        loadDataType.initData();
        areaNameTextView.setText("附近门店");
    }

    private void init() {
        areaAdapter = new AreaAdapter(new ArrayList<>());
        areaListView.setLayoutManager(new LinearLayoutManager(this));
        areaAdapter.bindToRecyclerView(areaListView);
        areaAdapter.setOnItemClickListener((adapter, view, position) -> {
            Area area = areaAdapter.getItem(position);
            areaAdapter.refreshItem(position);
            if (area != null) {
                if (position == 0) {
                    type = 1;
                    this.area = "";
                    areaNameTextView.setText("附近门店");
                } else {
                    type = 2;
                    this.area = area.getName();
                    areaNameTextView.setText(area.getName());
                }
                loadDataType.initData();
            }
        });

        rentShopAdapter = new RentShopAdapter(new ArrayList<>());
        shopListView.setLayoutManager(new LinearLayoutManager(this));
        rentShopAdapter.bindToRecyclerView(shopListView);
        rentShopAdapter.setOnItemClickListener((adapter, view, position) -> {
            if (RentCarUtils.rentCarInfo==null){
                RentCarUtils.rentCarInfo = new HashMap<>();
            }
            RentCarUtils.rentCarInfo.clear();
            RentCarUtils.rentCarInfo.put(RentCarUtils.RENT_CAR_INFO_SHOP, rentShopAdapter.getItem(position));
            RentCarUtils.rentCarInfo.put(RentCarUtils.IS_EDIT_ORDER_TIME, true);

            Intent intent = new Intent(RentCarByShopListActivity.this, ShopDetailActivity.class);
            startActivity(intent);
        });
        refreshLayout.setOnLoadMoreListener(refreshLayout -> loadDataType.loadMoreData());
        refreshLayout.setOnRefreshListener(refreshLayout -> loadDataType.refreshData());
    }


    private void getJsonData() {
        Area area1 = new Area();
        area1.setName("附近门店");
        area1.setSelecting(true);
        areaList.add(area1);
        String json = JsonUtil.getJson(this, "area.json");
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray areas = jsonObject.getJSONArray(cityCode);
            Log.e("areas", areas.length() + "");
            Log.e("areas", areas.toString());
            for (int i = 0; i < areas.length(); i++) {
                JSONObject item = areas.getJSONObject(i);
                Log.e("areas", item.toString());
                Area area = new Gson().fromJson(item.toString(), Area.class);
                areaList.add(area);
            }
            areaAdapter.setNewData(areaList);
        } catch (JSONException e) {
            e.printStackTrace();
            areaAdapter.setNewData(areaList);
        }

    }


    @OnClick(R.id.back)
    public void onViewClicked() {
        onBackPressed();
    }

    private LoadDataType loadDataType = new LoadDataType() {
        @Override
        public Map<String, Object> initParam() {
            Map<String, Object> params = new HashMap<>();
            params.put("type", type);
            params.put("page", page);

            double longitude = MyApplication.getaMapLocation().getLongitude()==0?102.4584665:MyApplication.getaMapLocation().getLongitude();
            double latitude = MyApplication.getaMapLocation().getLatitude()==0?24.52845:MyApplication.getaMapLocation().getLatitude();
            params.put("longitude", longitude);
            params.put("latitude", latitude);
            if (!TextUtils.isEmpty(area)){
                params.put("area", area);
            }
            return params;
        }

        @Override
        public void initData() {
            Map<String, Object> params = initParam();
            multipleStatusView.showLoading();
            HttpProxy.obtain().get(PlatformContans.CarRent.getRentCarShop, params, "", new ICallBack() {
                @Override
                public void OnSuccess(String result) {
                    Type type = new TypeToken<BaseModel<List<RentShop>>>() {}.getType();
                    HandlerData.handlerData(result, type, new EndLoadDataType<List<RentShop>>() {
                        @Override
                        public void onFailed() {
                            multipleStatusView.showError();
                        }

                        @Override
                        public void onSuccess(List<RentShop> rentShopList) {
                            if (rentShopList!=null && rentShopList.size()>0){
                                rentShopAdapter.setNewData(rentShopList);
                                multipleStatusView.showContent();
                            }else {
                                multipleStatusView.showEmpty();
                            }
                        }
                    });
                }

                @Override
                public void onFailure(String error) {
                    multipleStatusView.showError();
                }
            });
        }

        @Override
        public void loadMoreData() {
            page++;
            Map<String, Object> params = initParam();
            HttpProxy.obtain().get(PlatformContans.CarRent.getRentCarShop, params, "", new ICallBack() {
                @Override
                public void OnSuccess(String result) {
                    Type type = new TypeToken<BaseModel<List<RentShop>>>() {}.getType();
                    HandlerData.handlerData(result, type, new EndLoadDataType<List<RentShop>>() {
                        @Override
                        public void onFailed() {
                            refreshLayout.finishLoadMore(false);
                        }

                        @Override
                        public void onSuccess(List<RentShop> rentShopList) {
                            refreshLayout.finishLoadMore(true);
                            if (rentShopList!=null && rentShopList.size()>0){
                                rentShopAdapter.addData(rentShopList);
                            }
                        }
                    });
                }

                @Override
                public void onFailure(String error) {
                    refreshLayout.finishLoadMore(false);
                }
            });
        }

        @Override
        public void refreshData() {
            page = 1;
            Map<String, Object> params = initParam();
            HttpProxy.obtain().get(PlatformContans.CarRent.getRentCarShop, params, "", new ICallBack() {
                @Override
                public void OnSuccess(String result) {
                    Type type = new TypeToken<BaseModel<List<RentShop>>>() {}.getType();
                    HandlerData.handlerData(result, type, new EndLoadDataType<List<RentShop>>() {
                        @Override
                        public void onFailed() {
                            multipleStatusView.showError();
                            refreshLayout.finishRefresh(false);
                        }

                        @Override
                        public void onSuccess(List<RentShop> rentShopList) {
                            if (rentShopList == null || rentShopList.size()<=0){
                                multipleStatusView.showEmpty();
                            }else {
                                multipleStatusView.showContent();
                                rentShopAdapter.setNewData(rentShopList);
                            }
                            refreshLayout.finishRefresh(true);
                        }
                    });
                }

                @Override
                public void onFailure(String error) {
                    refreshLayout.finishRefresh(false);
                }
            });
        }
    };

}
