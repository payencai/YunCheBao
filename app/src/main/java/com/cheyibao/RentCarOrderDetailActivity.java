package com.cheyibao;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.yunchebao.MyApplication;
import com.bumptech.glide.Glide;
import com.cheyibao.model.RentOrder;
import com.cheyibao.model.RentShop;
import com.common.AvoidOnResult;
import com.common.BaseModel;
import com.common.ConfirmDialog;
import com.common.DialPhoneUtils;
import com.common.EndLoadDataType;
import com.common.HandlerData;
import com.coorchice.library.SuperTextView;
import com.costans.PlatformContans;
import com.example.yunchebao.R;
import com.google.gson.reflect.TypeToken;
import com.http.HttpProxy;
import com.http.ICallBack;
import com.payencai.library.util.ToastUtil;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RentCarOrderDetailActivity extends AppCompatActivity {

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
    @BindView(R.id.car_banner_view)
    ImageView carBannerView;
    @BindView(R.id.car_brand_view)
    TextView carBrandView;
    @BindView(R.id.status_view)
    TextView statusView;
    @BindView(R.id.car_models_view)
    TextView carModelsView;
    @BindView(R.id.seat_view)
    TextView seatView;
    @BindView(R.id.label)
    TextView label;
    @BindView(R.id.day_price_view)
    TextView dayPriceView;
    @BindView(R.id.x3)
    TextView x3;
    @BindView(R.id.price_parent_view)
    RelativeLayout priceParentView;
    @BindView(R.id.shop_name_view)
    TextView shopNameView;
    @BindView(R.id.shop_phone_view)
    TextView shopPhoneView;
    @BindView(R.id.shop_address_view)
    TextView shopAddressView;
    @BindView(R.id.cancel_order_click)
    TextView cancelOrderClick;
    @BindView(R.id.shop_complaint_click)
    TextView shopComplaintClick;
    @BindView(R.id.comment_click)
    TextView commentClick;

    private RentOrder rentOrder;

    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        setContentView(R.layout.activity_rent_car_order_detail);
        ButterKnife.bind(this);
        title.setText("详情");
        rentOrder = getIntent().getParcelableExtra("rent_order");
        if (rentOrder!=null){
            bindData();
        }
    }

    private void bindData(){
        Glide.with(carBannerView).load(rentOrder.getImage()).into(carBannerView);
        carBrandView.setText(rentOrder.getBrand());
        carModelsView.setText(rentOrder.getCarTategory());
        String seat = TextUtils.isEmpty(rentOrder.getSeat())?"":rentOrder.getSeat().replace("座", "");
        seatView.setText(String.format("%s/%s座", rentOrder.getVariableBox(), seat));
        dayPriceView.setText(String.format("￥%s", rentOrder.getDayPrice()));
        String text;
        switch (rentOrder.getState()){
            case 2:
                text = "服务中";
                break;
            case 3:
                text = "待评论";
                break;
            case 4:
                text = ".已完成";
                break;
            default:
                text = "未知状态";
        }
        statusView.setText(text);
        if (rentOrder.getState() == 2){
            cancelOrderClick.setVisibility(View.VISIBLE);
            ((View)commentClick.getParent()).setVisibility(View.GONE);
        }else if (rentOrder.getState()==3){
            cancelOrderClick.setVisibility(View.GONE);
            ((View)commentClick.getParent()).setVisibility(View.VISIBLE);
        }else {
            cancelOrderClick.setVisibility(View.GONE);
            ((View)commentClick.getParent()).setVisibility(View.GONE);
        }
        x3.setText(String.format("x%s",rentOrder.getRentDay()));

        Map<String,Object> map = new HashMap<>();
        map.put("shopId",rentOrder.getShopId());
       HttpProxy.obtain().get(PlatformContans.CarRent.getRentCarShopById, map, new ICallBack() {
           @Override
           public void OnSuccess(String result) {
               HandlerData.handlerData(result, new TypeToken<BaseModel<RentShop>>() {
               }.getType(), new EndLoadDataType<RentShop>() {
                   @Override
                   public void onFailed() {

                   }

                   @Override
                   public void onSuccess(RentShop rentShop) {
                        if (rentShop!=null){
                            shopNameView.setText(rentShop.getName());
                            shopPhoneView.setText(rentShop.getSaleTelephone());
                            shopAddressView.setText(rentShop.getAddress());
                        }
                   }
               });
           }

           @Override
           public void onFailure(String error) {

           }
       });
    }

    @OnClick(R.id.back)
    public void onBackClicked() {
        onBackPressed();
    }

    @OnClick(R.id.cancel_order_click)
    public void onCancelOrderClickClicked() {
        cancelOrder();
    }

    @SuppressLint("CheckResult")
    @OnClick(R.id.shop_complaint_click)
    public void onShopComplaintClickClicked() {
        RxPermissions rxPermissions = new RxPermissions(this);
        rxPermissions.request(Manifest.permission.CALL_PHONE).subscribe(aBoolean -> {
            if (aBoolean){
                ConfirmDialog confirmDialog = new ConfirmDialog(context);
                confirmDialog .setTitle("拨号提示")
                        .setMessageText("确定拨打电话吗？")
                        .setCancelable(true)
                        .setConfirmClickListener(v -> {
                            DialPhoneUtils.startDialNumber(context,"0871-68106404");
                            confirmDialog.dismiss();
                        })
                        .setCancelClickListener(v -> confirmDialog.dismiss());
                confirmDialog.show();
            }
        });
    }

    @OnClick(R.id.comment_click)
    public void onCommentClickClicked() {
        AvoidOnResult avoidOnResult = new AvoidOnResult(this);
        Intent intent = new Intent(context, RentCarOrderCommentActivity.class);
        intent.putExtra("rent_car_order_id",rentOrder==null?"":rentOrder.getId());
        avoidOnResult.startForResult(intent, 1, (requestCode, resultCode, data) -> {
            statusView.setText("已完成");
            rentOrder.setState(4);
        });
    }

    private void cancelOrder(){
        if ( rentOrder!=null && ( rentOrder.getState()==1 || rentOrder.getState()==2)){
            ConfirmDialog dialog = new ConfirmDialog(this);
            dialog .setTitle("取消订单提示")
                    .setMessageText("确定取消订单吗？")
                    .setCancelable(true)
                    .setConfirmClickListener(v -> {
                        Map<String,Object> map = new HashMap<>();

                        map.put("id",rentOrder.getId());
                        HttpProxy.obtain().post(PlatformContans.CarRent.cancelRentCarOrder, MyApplication.token, map, new ICallBack() {
                            @Override
                            public void OnSuccess(String result) {
                                Type type = new TypeToken<BaseModel<String>>(){}.getType();
                                HandlerData.handlerData(result, type, new EndLoadDataType<String>() {
                                    @Override
                                    public void onFailed() {
                                        ToastUtil.showToast(context,"订单删除失败");
                                    }

                                    @Override
                                    public void onSuccess(String s) {
                                        finish();
                                    }

                                    @Override
                                    public void onSuccessBaseModel(BaseModel baseModel) {
                                        if (baseModel!=null){
                                            ToastUtil.showToast(context,baseModel.getMessage());
                                        }
                                    }
                                });
                            }

                            @Override
                            public void onFailure(String error) {
                                ToastUtil.showToast(context,"订单删除失败");
                            }
                        });
                        dialog.dismiss();
                    })
                    .setCancelClickListener(v -> dialog.dismiss());
            dialog.show();
        }else {
            if (rentOrder==null){
                ToastUtil.showToast(context,"订单不存在");
            }else {
                ToastUtil.showToast(context,"当前状态不能取消订单");
            }
        }
    }
}
