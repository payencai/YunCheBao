package com.cheyibao;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.application.MyApplication;
import com.bumptech.glide.Glide;
import com.cheyibao.adapter.LongRentShopAdapter;
import com.cheyibao.model.LongRentShop;
import com.cheyibao.model.RentCarModel;
import com.cheyibao.view.RangerPricePopwindow;
import com.common.BaseModel;
import com.common.EndLoadDataType;
import com.common.HandlerData;
import com.common.LoadDataType;
import com.common.MultipleStatusView;
import com.costans.PlatformContans;
import com.example.yunchebao.R;
import com.google.gson.reflect.TypeToken;
import com.http.HttpProxy;
import com.http.ICallBack;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CarModelsDetailActivity extends AppCompatActivity {

    @BindView(R.id.car_banner_view)
    ImageView carBannerView;
    @BindView(R.id.car_brand_view)
    TextView carBrandView;
    @BindView(R.id.car_models_view)
    TextView carModelsView;
    @BindView(R.id.seat_view)
    TextView seatView;
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.rl_top)
    RelativeLayout rlTop;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.is_distance_view)
    CheckBox isDistanceView;
    @BindView(R.id.distance_view)
    LinearLayout distanceView;
    @BindView(R.id.is_price_view)
    CheckBox isPriceView;
    @BindView(R.id.price_view)
    LinearLayout priceView;
    @BindView(R.id.is_to_home_service_view)
    CheckBox isToHomeServiceView;
    @BindView(R.id.app_barlayout)
    AppBarLayout appBarlayout;
    @BindView(R.id.shop_list_view)
    RecyclerView shopListView;
    @BindView(R.id.multiple_status_view)
    MultipleStatusView multipleStatusView;
    @BindView(R.id.refresh_layout)
    SmartRefreshLayout refreshLayout;
    private RentCarModel rentCarModel;
    private double longitude;
    private double latitude;

    private int page = 1;

    private LongRentShopAdapter adapter;
    private RangerPricePopwindow rangerPricePopwindow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_models_detail);
        ButterKnife.bind(this);
        init();
        initView();
    }

    private void initView(){
        Glide.with(this).load(rentCarModel.getImage()).into(carBannerView);
        carBrandView.setText(rentCarModel.getBrand());
        carModelsView.setText(rentCarModel.getCarTategory());
        seatView.setText(String.format("%s/%s",rentCarModel.getVariableBox(),rentCarModel.getSeat().contains("座")?rentCarModel.getSeat():String.format("%s座",rentCarModel.getSeat())));

        isToHomeServiceView.setOnCheckedChangeListener((buttonView, isChecked) -> loadDataType.initData());
    }

    private void init(){
        rentCarModel = getIntent().getParcelableExtra("car_model");
        longitude = MyApplication.getaMapLocation().getLongitude() == 0?102.222225:MyApplication.getaMapLocation().getLongitude();
        latitude = MyApplication.getaMapLocation().getLatitude() == 0?24.00123250:MyApplication.getaMapLocation().getLatitude();

        shopListView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new LongRentShopAdapter(new ArrayList<>());
        adapter.bindToRecyclerView(shopListView);

        loadDataType.initData();
        refreshLayout.setEnableRefresh(false);
        refreshLayout.setOnLoadMoreListener(refreshLayout -> loadDataType.loadMoreData());
    }

    private LoadDataType loadDataType = new LoadDataType() {
        @Override
        public Map<String, Object> initParam() {
            Map<String, Object> map = new HashMap<>();
            map.put("page", page);
            map.put("brand", rentCarModel.getBrand());
            map.put("carTategory", rentCarModel.getCarTategory());
            map.put("longitude", longitude);
            map.put("latitude", latitude);
//            map.put("maxDistance", 0);
//            map.put("minPrice", 0);
//            map.put("maxPrice", 0);
            map.put("isOnlineServe", isToHomeServiceView.isChecked() ? 1 : 2);
            return null;
        }

        @Override
        public void initData() {
            page = 1;
            Map<String, Object> map = initParam();
            multipleStatusView.showLoading();
            HttpProxy.obtain().get(PlatformContans.CarRent.getRentCarShopByCondition, map, new ICallBack() {
                @Override
                public void OnSuccess(String result) {
                    Type type = new TypeToken<BaseModel<List<LongRentShop>>>() {
                    }.getType();
                    HandlerData.handlerData(result, type, new EndLoadDataType<List<LongRentShop>>() {
                        @Override
                        public void onFailed() {
                            multipleStatusView.showError();
                        }

                        @Override
                        public void onSuccess(List<LongRentShop> longRentShops) {
                            if (longRentShops == null || longRentShops.size() <= 0) {
                                multipleStatusView.showEmpty();
                            } else {
                                multipleStatusView.showContent();
                                adapter.setNewData(longRentShops);
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
            Map<String, Object> map = initParam();
            HttpProxy.obtain().get(PlatformContans.CarRent.getRentCarShopByCondition, map, new ICallBack() {
                @Override
                public void OnSuccess(String result) {
                    Type type = new TypeToken<BaseModel<List<LongRentShop>>>() {
                    }.getType();
                    HandlerData.handlerData(result, type, new EndLoadDataType<List<LongRentShop>>() {
                        @Override
                        public void onFailed() {

                        }

                        @Override
                        public void onSuccess(List<LongRentShop> longRentShops) {
                            if (longRentShops != null && longRentShops.size() > 0) {
                                multipleStatusView.showContent();
                                adapter.setNewData(longRentShops);
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
    };

    @OnClick(R.id.price_view)
    public void onPriceClicked() {
        if (rangerPricePopwindow==null){
            rangerPricePopwindow = new RangerPricePopwindow(this);
        }
        rangerPricePopwindow.showAsDropDown(priceView);
    }

    @OnClick(R.id.back)
    public void onBackClicked() {
        onBackPressed();
    }
}
