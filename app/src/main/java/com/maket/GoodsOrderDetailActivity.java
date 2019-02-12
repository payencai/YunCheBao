package com.maket;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.application.MyApplication;
import com.coorchice.library.SuperTextView;
import com.costans.PlatformContans;
import com.entity.PhoneOrderEntity;
import com.example.yunchebao.R;
import com.http.HttpProxy;
import com.http.ICallBack;
import com.maket.adapter.GoodsOrderChildAdapter;
import com.maket.adapter.GoodsOrderDetailAdapter;
import com.payencai.library.util.ToastUtil;
import com.tool.BottomMenuDialog;
import com.tool.listview.PersonalListView;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.rong.imkit.RongIM;

public class GoodsOrderDetailActivity extends AppCompatActivity {
    PhoneOrderEntity mPhoneOrderEntity;
    @BindView(R.id.lv_goods)
    PersonalListView lv_goods;
    @BindView(R.id.tv_ordernum)
    TextView tv_ordernum;
    @BindView(R.id.tv_status)
    TextView tv_status;
    @BindView(R.id.shopName)
    TextView shopName;
    @BindView(R.id.tv_goodsnum)
    TextView tv_goodsnum;
    @BindView(R.id.total1)
    TextView total1;
    @BindView(R.id.total2)
    TextView total2;
    @BindView(R.id.total3)
    TextView total3;
    @BindView(R.id.total4)
    TextView total4;
    @BindView(R.id.tv_pay)
    SuperTextView tv_pay;
    @BindView(R.id.tv_cancel)
    TextView tv_cancel;
    @BindView(R.id.rl_contacts)
    RelativeLayout rl_contacts;
    @BindView(R.id.rl_phone)
    RelativeLayout rl_phone;
    @BindView(R.id.back)
    ImageView back;
    GoodsOrderChildAdapter mGoodsOrderChildAdapter;
    private String[] items = new String[]{"我不想买了", "信息填写错误，重新拍", "卖家缺货", "同城见面交易", "其他原因"};
    private BottomMenuDialog bottomDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);
        mPhoneOrderEntity= (PhoneOrderEntity) getIntent().getSerializableExtra("data");
        ButterKnife.bind(this);
        initView();
    }
    private void initView(){
        mGoodsOrderChildAdapter=new GoodsOrderChildAdapter(this,mPhoneOrderEntity.getItemList());
        lv_goods.setAdapter(mGoodsOrderChildAdapter);
        tv_ordernum.setText("订单号: "+mPhoneOrderEntity.getOrderNo());
        tv_goodsnum.setText("共"+mPhoneOrderEntity.getNumber()+"件商品");
        total1.setText("￥"+mPhoneOrderEntity.getTotal());
        total2.setText("￥"+mPhoneOrderEntity.getTotal());
        total3.setText("￥"+mPhoneOrderEntity.getTotal());
        total4.setText("￥"+mPhoneOrderEntity.getTotal());
        switch(mPhoneOrderEntity.getState()){
            case 1:
                break;
            case 2:
                break;
            case 3:
                break;
            case 4:
                break;
        }
        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertCancelPanel();
            }
        });
        tv_pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(GoodsOrderDetailActivity.this,GoodsPayActivity.class);
                intent.putExtra("orderid",mPhoneOrderEntity.getOrderNo());
                intent.putExtra("money",mPhoneOrderEntity.getTotal()+"");
                startActivity(intent);
            }
        });
        rl_contacts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RongIM.getInstance().startPrivateChat(GoodsOrderDetailActivity.this,mPhoneOrderEntity.getUserId(), mPhoneOrderEntity.getShopName());
            }
        });
        rl_phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callPhone(mPhoneOrderEntity.getMerchTelephone());
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    public void callPhone(String phoneNum) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        Uri data = Uri.parse("tel:" + phoneNum);
        intent.setData(data);
        startActivity(intent);
    }

    private void alertCancelPanel() {
        BottomMenuDialog.Builder builder = new BottomMenuDialog.Builder(this);
        builder.setTitle("请选择取消订单的理由");
        for (int i = 0; i < items.length; i++) {
            builder.addMenu(items[i], new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    bottomDialog.dismiss();
                    cancelOrder();
                }
            });
        }
        bottomDialog = builder.create();
        bottomDialog.show();
    }
    private void cancelOrder(){
        Map<String,Object> params=new HashMap<>();
        params.put("orderId",mPhoneOrderEntity.getId());
        HttpProxy.obtain().post(PlatformContans.GoodsOrder.cancelOrder, MyApplication.getUserInfo().getToken(), params, new ICallBack() {
            @Override
            public void OnSuccess(String result) {
                Log.e("reuslt",result);
                ToastUtil.showToast(GoodsOrderDetailActivity.this,"取消成功");
                finish();
            }

            @Override
            public void onFailure(String error) {

            }
        });
    }

}
