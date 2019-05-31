package com.cheyibao;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.yunchebao.MyApplication;
import com.cheyibao.adapter.RentCarModelAdapter;
import com.cheyibao.model.RentCarModel;
import com.cheyibao.util.RentCarUtils;
import com.cheyibao.view.RangerPricePopwindow;
import com.common.AvoidOnResult;
import com.common.BaseModel;
import com.common.EndLoadDataType;
import com.common.HandlerData;
import com.common.LoadDataType;
import com.common.MultipleStatusView;
import com.coorchice.library.SuperTextView;
import com.costans.PlatformContans;
import com.example.yunchebao.R;
import com.example.yunchebao.cheyibao.rentcar.ShopListNoAreaActivity;
import com.google.gson.reflect.TypeToken;
import com.http.HttpProxy;
import com.http.ICallBack;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RentCarByCarModelActivity extends AppCompatActivity {


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
    @BindView(R.id.brand_view)
    LinearLayout brandView;
    @BindView(R.id.price_view)
    LinearLayout priceView;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.multiple_status_view)
    MultipleStatusView multipleStatusView;
    @BindView(R.id.refresh_layout)
    SmartRefreshLayout refreshLayout;

    private RangerPricePopwindow rangerPricePopwindow;
    private RentCarModelAdapter adapter;

    String brand;

    private Activity context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rent_car_by_car_model);
        ButterKnife.bind(this);
        context = this;
        title.setText("车型查询");
        textBtn.setText(TextUtils.isEmpty(MyApplication.getaMapLocation().getCity()) ? "昆明" : MyApplication.getaMapLocation().getCity());
        textBtn.setVisibility(View.VISIBLE);

        rangerPricePopwindow = new RangerPricePopwindow(multipleStatusView);
        rangerPricePopwindow.setConfirmListener(v -> {
            rangerPricePopwindow.dismiss();
            loadDataType.initData();
        });

        adapter = new RentCarModelAdapter(new ArrayList<>());
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter.bindToRecyclerView(recyclerView);
        adapter.setOnItemClickListener((adapter, view, position) -> {
            if (RentCarUtils.rentCarInfo==null){
                RentCarUtils.rentCarInfo = new HashMap<>();
            }
            RentCarUtils.rentCarInfo.clear();
            RentCarUtils.rentCarInfo.put(RentCarUtils.RENT_CAR_INFO_CAR_MODEL,adapter.getItem(position));
            RentCarUtils.rentCarInfo.put(RentCarUtils.IS_EDIT_ORDER_TIME,true);
            RentCarUtils.rentCarInfo.put(RentCarUtils.IS_EDIT_ORDER_ADDRESS,true);
            RentCarUtils.rentCarInfo.put(RentCarUtils.IS_EDIT_ORDER_ONLY_ADDRESS,true);
            Intent intent = new Intent(context, ShopListNoAreaActivity.class);
            intent.putExtra("confirm_order","confirm_order");
            startActivity(intent);
        });
        refreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                loadDataType.loadMoreData();
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                loadDataType.refreshData();
            }
        });
        loadDataType.initData();
    }

    private LoadDataType loadDataType = new LoadDataType() {

        int page ;
        private Type type = new TypeToken<BaseModel<List<RentCarModel>>>(){}.getType();

        @Override
        public Map<String, Object> initParam() {
            Map<String,Object> map = new HashMap<>();
            map.put("page",page);
            map.put("city",TextUtils.isEmpty(MyApplication.getaMapLocation().getCity())?"昆明市":MyApplication.getaMapLocation().getCity());
            if (!TextUtils.isEmpty(brand) && !brand.equals("全部")){
                map.put("brand",brand);
            }
            if (rangerPricePopwindow.getMinPrice()>0){
                map.put("minPrice",rangerPricePopwindow.getMinPrice());
            }

            if (rangerPricePopwindow.getMaxPrice()>0){
                map.put("maxPrice",rangerPricePopwindow.getMaxPrice());
            }
            return map;
        }

        @Override
        public void initData() {
            page = 1;
            HttpProxy.obtain().get(PlatformContans.CarRent.getRentCarCarListForApp, initParam(), new ICallBack() {
                @Override
                public void OnSuccess(String result) {
                    HandlerData.handlerData(result, type, new EndLoadDataType<List<RentCarModel>>() {
                        @Override
                        public void onFailed() {
                            multipleStatusView.showError();
                        }

                        @Override
                        public void onSuccess(List<RentCarModel> rentCarModels) {
                            if (rentCarModels!=null && rentCarModels.size()>0){
                                multipleStatusView.showContent();
                                adapter.setNewData(rentCarModels);
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
            HttpProxy.obtain().get(PlatformContans.CarRent.getRentCarCarListForApp, initParam(), new ICallBack() {
                @Override
                public void OnSuccess(String result) {
                    HandlerData.handlerData(result, type, new EndLoadDataType<List<RentCarModel>>() {
                        @Override
                        public void onFailed() {
                           refreshLayout.finishLoadMore(false);
                            multipleStatusView.showContent();
                        }

                        @Override
                        public void onSuccess(List<RentCarModel> rentCarModels) {
                            refreshLayout.finishLoadMore(true);
                            multipleStatusView.showContent();
                            if (rentCarModels!=null && rentCarModels.size()>0){
                                adapter.addData(rentCarModels);
                            }else {
                                refreshLayout.finishLoadMoreWithNoMoreData();
                            }
                        }
                    });
                }

                @Override
                public void onFailure(String error) {
                    refreshLayout.finishLoadMore(false);
                    multipleStatusView.showContent();
                }
            });
        }

        @Override
        public void refreshData() {
            page = 1;
            HttpProxy.obtain().get(PlatformContans.CarRent.getRentCarCarListForApp, initParam(), new ICallBack() {
                @Override
                public void OnSuccess(String result) {
                    HandlerData.handlerData(result, type, new EndLoadDataType<List<RentCarModel>>() {
                        @Override
                        public void onFailed() {
                            refreshLayout.finishRefresh(false);
                            multipleStatusView.showContent();
                        }

                        @Override
                        public void onSuccess(List<RentCarModel> rentCarModels) {
                            refreshLayout.finishRefresh(true);
                            multipleStatusView.showContent();
                            if (rentCarModels!=null && rentCarModels.size()>0){
                                adapter.setNewData(rentCarModels);
                            }
                        }
                    });
                }

                @Override
                public void onFailure(String error) {
                    refreshLayout.finishRefresh(false);
                    multipleStatusView.showContent();
                }
            });
        }
    };

    @OnClick(R.id.back)
    public void onViewClicked() {
        onBackPressed();
    }

    @OnClick(R.id.brand_view)
    public void onBrandViewClicked() {
        AvoidOnResult avoidOnResult = new AvoidOnResult(this);
        avoidOnResult.startForResult(BrandSelectedActivity.class, 1, (requestCode, resultCode, data) -> {
            if (requestCode==1 && data!=null){
                brand = data.getStringExtra("brand");
                loadDataType.initData();
            }
        });
    }

    @OnClick(R.id.price_view)
    public void onPriceViewClicked() {
        rangerPricePopwindow.showAsDropDown(priceView);
    }

}
