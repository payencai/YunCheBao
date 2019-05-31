package com.example.yunchebao.cheyibao.rentcar;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cheyibao.RentCarOrderActivity;
import com.example.yunchebao.MyApplication;
import com.cheyibao.adapter.RentShopAdapter;
import com.cheyibao.model.RentCarModel;
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
import com.google.gson.reflect.TypeToken;
import com.http.HttpProxy;
import com.http.ICallBack;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.system.model.AddressBean;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ShopListNoAreaActivity extends AppCompatActivity {

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
    @BindView(R.id.shop_list_view)
    RecyclerView shopListView;
    @BindView(R.id.multiple_status_view)
    MultipleStatusView multipleStatusView;
    @BindView(R.id.refresh_layout)
    SmartRefreshLayout refreshLayout;

    private RentShopAdapter rentShopAdapter;
    private int page = 1;
    private AddressBean addressBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_list_no_area);
        ButterKnife.bind(this);
        title.setText("选择门店");
        rentShopAdapter = new RentShopAdapter(new ArrayList<>());
        shopListView.setLayoutManager(new LinearLayoutManager(this));
        rentShopAdapter.bindToRecyclerView(shopListView);
        final String confirmOrder = getIntent().getStringExtra("confirm_order");
        rentShopAdapter.setOnItemClickListener((adapter, view, position) -> {
            if ("confirm_order".equals(confirmOrder)){
                RentCarUtils.rentCarInfo.put(RentCarUtils.RENT_CAR_INFO_SHOP, rentShopAdapter.getItem(position));
                RentCarModel rentCarModel =RentCarUtils.rentCarInfo.get(RentCarUtils.RENT_CAR_INFO_CAR_MODEL)==null?null: (RentCarModel) RentCarUtils.rentCarInfo.get(RentCarUtils.RENT_CAR_INFO_CAR_MODEL);
                if (rentCarModel!=null){
                    showDialog(rentCarModel);
                }
            }else {
                RentCarUtils.rentCarInfo.put(RentCarUtils.RENT_CAR_INFO_SHOP, rentShopAdapter.getItem(position));
                Intent intent = new Intent(ShopListNoAreaActivity.this, ShopDetailActivity.class);
                startActivity(intent);
            }
        });
        addressBean = RentCarUtils.rentCarInfo.get(RentCarUtils.RENT_CAR_INFO_AREA_1)==null?null: (AddressBean) RentCarUtils.rentCarInfo.get(RentCarUtils.RENT_CAR_INFO_AREA_1);
        loadDataType.initData();
        refreshLayout.setOnRefreshListener(refreshLayout -> loadDataType.refreshData());
        refreshLayout.setOnLoadMoreListener(refreshLayout -> loadDataType.loadMoreData());
    }

    private void showDialog(RentCarModel rentCarModel) {
        final Dialog dialog = new Dialog(this, R.style.dialog);
        View dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_rentcar, null);
        TextView tv_name = dialogView.findViewById(R.id.tv_name);
        TextView tv_auto = dialogView.findViewById(R.id.tv_auto);
        TextView tv_model = dialogView.findViewById(R.id.tv_model);
        TextView tv_price = dialogView.findViewById(R.id.tv_price);
        TextView tv_submit = dialogView.findViewById(R.id.tv_submit);
        //获得dialog的window窗口
        Window window = dialog.getWindow();
        //设置dialog在屏幕底部
        window.setGravity(Gravity.BOTTOM);
        //设置dialog弹出时的动画效果，从屏幕底部向上弹出
        window.setWindowAnimations(R.style.mypopwindow_anim_style);
        window.getDecorView().setPadding(0, 0, 0, 0);
        //获得window窗口的属性
        WindowManager.LayoutParams lp = window.getAttributes();
        //设置窗口宽度为充满全屏
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        //设置窗口高度为包裹内容
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        //将设置好的属性set回去
        window.setAttributes(lp);
        //将自定义布局加载到dialog上
        dialog.setContentView(dialogView);
        dialog.show();
        tv_name.setText(rentCarModel.getBrand());
        tv_model.setText(rentCarModel.getCarTategory());
        tv_auto.setText(String.format("%s/%s座", rentCarModel.getVariableBox(), rentCarModel.getSeat()));
        tv_price.setText(String.format("￥%s", rentCarModel.getDayPrice()));
        tv_submit.setOnClickListener(v -> {
            Intent intent = new Intent(this, RentCarOrderActivity.class);
            startActivity(intent);
            dialog.dismiss();
        });
    }


    @OnClick(R.id.back)
    public void onViewClicked() {
        onBackPressed();
    }

    private LoadDataType loadDataType = new LoadDataType() {
        @Override
        public Map<String, Object> initParam() {
            Map<String, Object> params = new HashMap<>();
            params.put("page", page);
            double longitude = addressBean==null?(
                    MyApplication.getaMapLocation().getLongitude()==0?102.45688:MyApplication.getaMapLocation().getLongitude()
                    ):addressBean.getLatlng().getLng();
            params.put("longitude", longitude);
            double latitude = addressBean==null?(
                    MyApplication.getaMapLocation().getLatitude()==0?24.5622144:MyApplication.getaMapLocation().getLatitude()
            ):addressBean.getLatlng().getLat();
            params.put("latitude", latitude);
            if (RentCarUtils.rentCarInfo.get(RentCarUtils.RENT_CAR_INFO_IS_TO_HOME_SERVICE)!=null){
                boolean b = (boolean) RentCarUtils.rentCarInfo.get(RentCarUtils.RENT_CAR_INFO_IS_TO_HOME_SERVICE);
                params.put("isOnlineServe",b? RentCarUtils.ONLINESERVE:RentCarUtils.OFFLINESERVE);
            }
            String city = addressBean==null?(
                    TextUtils.isEmpty(MyApplication.getaMapLocation().getCity())?"昆明市":MyApplication.getaMapLocation().getCity()
                    ):addressBean.getCityname();
            params.put("city", city);
            return params;
        }

        @Override
        public void initData() {
            page=1;
            Map<String, Object> params = initParam();
            multipleStatusView.showLoading();
            HttpProxy.obtain().get(PlatformContans.CarRent.getRentCarShop, params, "", new ICallBack() {
                @Override
                public void OnSuccess(String result) {
                    Type type = new TypeToken<BaseModel<List<RentShop>>>() {
                    }.getType();
                    HandlerData.handlerData(result,type,new EndLoadDataType<List<RentShop>>(){
                        @Override
                        public void onSuccess(List<RentShop> rentShopList) {
                            if (rentShopList==null){
                                multipleStatusView.showEmpty();
                            }else {
                                multipleStatusView.showContent();
                                rentShopAdapter.setNewData(rentShopList);
                            }
                        }

                        @Override
                        public void onFailed() {
                            multipleStatusView.showError();
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
                    refreshLayout.finishLoadMore(true);
                    Type type = new TypeToken<BaseModel<List<RentShop>>>() {
                    }.getType();
                    HandlerData.handlerData(result,type,new EndLoadDataType<List<RentShop>>(){

                        @Override
                        public void onFailed() {
                            refreshLayout.finishLoadMore(false);
                        }

                        @Override
                        public void onSuccess(List<RentShop> rentShopList) {
                            if (rentShopList!=null){
                                rentShopAdapter.setNewData(rentShopList);
                            }
                            refreshLayout.finishLoadMore(true);
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
                    Type type = new TypeToken<BaseModel<List<RentShop>>>() {
                    }.getType();
                    HandlerData.handlerData(result,type,new EndLoadDataType<List<RentShop>>(){
                        @Override
                        public void onSuccess(List<RentShop> rentShopList) {
                            refreshLayout.finishRefresh(true);
                            if (rentShopList==null){
                                multipleStatusView.showEmpty();
                            }else {
                                rentShopAdapter.setNewData(rentShopList);
                            }
                        }

                        @Override
                        public void onFailed() {
                            refreshLayout.finishRefresh(false);
                            multipleStatusView.showError();
                        }
                    });
                }

                @Override
                public void onFailure(String error) {
                    refreshLayout.finishRefresh(false);
                    multipleStatusView.showError();
                }
            });
        }
    };


}
