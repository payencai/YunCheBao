package com.example.yunchebao.drive.activity;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.yunchebao.MyApplication;
import com.bumptech.glide.Glide;
import com.costans.PlatformContans;
import com.example.yunchebao.R;
import com.example.yunchebao.myservice.model.DriverOrderDetail;
import com.google.gson.Gson;
import com.gyf.immersionbar.ImmersionBar;
import com.http.HttpProxy;
import com.http.ICallBack;
import com.order.CarOrder;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DriveOrderDetailActivity extends AppCompatActivity {
    @BindView(R.id.iv_car)
    ImageView iv_car;
    @BindView(R.id.tv_name)
    TextView tv_name;
    @BindView(R.id.tv_carstate)
    TextView tv_carstate;
    @BindView(R.id.tv_time)
    TextView tv_time;
    @BindView(R.id.tv_avgprice)
    TextView tv_avgprice;
    @BindView(R.id.tv_coash)
    TextView tv_coash;
    @BindView(R.id.tv_phone)
    TextView tv_phone;
    @BindView(R.id.tv_address)
    TextView tv_address;
    @BindView(R.id.tv_address2)
    TextView tv_address2;
    @BindView(R.id.ll_state1)
    LinearLayout ll_state1;
    @BindView(R.id.tv_seecomment)
    TextView tv_seecomment;
    CarOrder mCarOrder;
    DriverOrderDetail mDriverOrderDetail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drive_order_detail);
        ButterKnife.bind(this);
        ImmersionBar.with(this).autoDarkModeEnable(true).fitsSystemWindows(true).statusBarColor(R.color.white).init();
        mCarOrder= (CarOrder) getIntent().getSerializableExtra("data");
        initView();
    }
    private void getDetail(){
        Map<String,Object>params=new HashMap<>();
        params.put("orderId",mCarOrder.getId());
        HttpProxy.obtain().get(PlatformContans.MyService.getDriverOrderDetail, params, MyApplication.token, new ICallBack() {
            @Override
            public void OnSuccess(String result) {
                Log.e("getSchoolOrderDetail",result);
                try {
                    JSONObject jsonObject=new JSONObject(result);
                    JSONObject data=jsonObject.getJSONObject("data");
                    mDriverOrderDetail=new Gson().fromJson(data.toString(), DriverOrderDetail.class);
                    setData();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(String error) {

            }
        });
    }

    private void setData() {
        tv_address.setText(mDriverOrderDetail.getAddress());
        tv_address2.setText(mDriverOrderDetail.getEndAddress());
        tv_name.setText(mDriverOrderDetail.getShopName());
        tv_phone.setText(mDriverOrderDetail.getShopTelephone());
        tv_avgprice.setText("￥"+mDriverOrderDetail.getPrice());
        tv_time.setText(mDriverOrderDetail.getCreateTime().substring(0,10));
        tv_coash.setText(mDriverOrderDetail.getDriverName());
        Glide.with(this).load(mDriverOrderDetail.getLogo()).into(iv_car);
        switch (mCarOrder.getState()){
            case 2:
                tv_carstate.setText("待评价");
                ll_state1.setVisibility(View.VISIBLE);
                break;
            case 3:
                tv_carstate.setText("已完成");
                tv_seecomment.setVisibility(View.VISIBLE);
                break;
        }
    }


    private void initView() {
        getDetail();
    }

    public void callPhone(String phoneNum) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        Uri data = Uri.parse("tel:" + phoneNum);
        intent.setData(data);
        startActivity(intent);
    }
    @OnClick({R.id.back,R.id.tv_complain,R.id.tv_seecomment,R.id.tv_comment})
    void onClick(View view){
        switch (view.getId()){
            case R.id.back:
                finish();
                break;
            case R.id.tv_complain:
                callPhone(mDriverOrderDetail.getShopTelephone());
                break;
            case R.id.tv_seecomment:
                Intent intent =new Intent(DriveOrderDetailActivity.this,DriverCommentActivity.class);
                intent.putExtra("id",mCarOrder.getShopId());
                startActivityForResult(intent,1);
                break;
            case R.id.tv_comment:
                //去评价
                Intent intent2 =new Intent(DriveOrderDetailActivity.this,AddOrderCommentActivity.class);
                intent2.putExtra("id",mCarOrder.getId());
                startActivityForResult(intent2,2);
                break;
        }
    }
}
