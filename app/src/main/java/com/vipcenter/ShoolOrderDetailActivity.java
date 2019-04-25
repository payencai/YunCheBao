package com.vipcenter;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.application.MyApplication;
import com.bumptech.glide.Glide;
import com.cheyibao.AddSchoolCommentActivity;
import com.cheyibao.model.Merchant;
import com.costans.PlatformContans;
import com.example.yunchebao.R;
import com.google.gson.Gson;
import com.http.HttpProxy;
import com.http.ICallBack;
import com.order.CarOrder;
import com.payencai.library.util.ToastUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.costans.PlatformContans.GoodsOrder.cancelOrder;

public class ShoolOrderDetailActivity extends AppCompatActivity {
    CarOrder mCarOrder;

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
    @BindView(R.id.tv_class)
    TextView tv_class;
    @BindView(R.id.tv_coash)
    TextView tv_coash;
    @BindView(R.id.tv_phone)
    TextView tv_phone;
    @BindView(R.id.tv_address)
    TextView tv_address;
    @BindView(R.id.ll_state1)
    LinearLayout ll_state1;
    @BindView(R.id.tv_seecomment)
    TextView tv_seecomment;
    @BindView(R.id.tv_cancel)
    TextView tv_cancel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shool_order_detail);
        ButterKnife.bind(this);
        mCarOrder= (CarOrder) getIntent().getSerializableExtra("data");
        initView();
    }
    private void carCancel(String id) {
        Map<String, Object> params = new HashMap<>();
        params.put("orderId", id);
        HttpProxy.obtain().post(PlatformContans.CarOrder.cancelCarOrder, MyApplication.token, params, new ICallBack() {
            @Override
            public void OnSuccess(String result) {
                Log.e("result", result);
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    int code = jsonObject.getInt("resultCode");
                    if (code == 0) {
                        ToastUtil.showToast(ShoolOrderDetailActivity.this, "取消成功");
                        finish();
                    } else {
                        String msg = jsonObject.getString("message");
                        ToastUtil.showToast(ShoolOrderDetailActivity.this, msg);
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
    private void showCancelDialog(String id) {
        Dialog dialog = new Dialog(this, R.style.dialog);
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_cancel_order, null);
        TextView tv_back = (TextView) view.findViewById(R.id.tv_back);
        TextView tv_confirm = (TextView) view.findViewById(R.id.tv_confirm);
        tv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        tv_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                carCancel(id);
            }
        });
        dialog.setCanceledOnTouchOutside(false);
        dialog.setContentView(view);
        dialog.show();
        Window window = dialog.getWindow();
        WindowManager windowManager = getWindowManager();
        WindowManager.LayoutParams layoutParams = window.getAttributes();
        Display display = windowManager.getDefaultDisplay();
        layoutParams.width = (int) (display.getWidth() * 0.8);
        layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        window.setAttributes(layoutParams);
    }
    @OnClick({R.id.back,R.id.tv_complain,R.id.tv_seecomment,R.id.tv_cancel,R.id.tv_comment})
    void onClick(View view){
        switch (view.getId()){
            case R.id.back:
                finish();
                break;
            case R.id.tv_complain:
                callPhone(merchant.getServiceTelephone());
                break;
            case R.id.tv_seecomment:
                break;
            case R.id.tv_cancel:
                showCancelDialog(mCarOrder.getId());
                break;
            case R.id.tv_comment:
                Intent intent = new Intent(ShoolOrderDetailActivity.this, AddSchoolCommentActivity.class);
                intent.putExtra("item", mCarOrder);
                startActivity(intent);
                break;
        }
    }
    public void callPhone(String phoneNum) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        Uri data = Uri.parse("tel:" + phoneNum);
        intent.setData(data);
        startActivity(intent);
    }
    Merchant merchant;

    private void getMerchat() {
        Map<String, Object> params = new HashMap<>();
        params.put("merchantId", mCarOrder.getShopId());
        HttpProxy.obtain().get(PlatformContans.Shop.getMerchantById, params, new ICallBack() {
            @Override
            public void OnSuccess(String result) {
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    JSONObject data = jsonObject.getJSONObject("data");
                    merchant = new Gson().fromJson(data.toString(), Merchant.class);
                    tv_phone.setText(merchant.getServiceTelephone());
                    tv_address.setText(merchant.getAddress());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(String error) {

            }
        });
    }

    private void initView() {
//        tv_address.setText(mCarOrder.getAddress());
//        tv_name.setText(mCarOrder.getShopName());
//        tv_phone.setText(mCarOrder.getTelephone());
//        tv_avgprice.setText("￥"+mCarOrder.getPrice());
//        tv_class.setText(mCarOrder.getClassName());
//        tv_time.setText(mCarOrder.getCreateTime().substring(0,10));
//        tv_coash.setText(mCarOrder.getCoachName());
//        Glide.with(this).load(mCarOrder.getImage()).into(iv_car);
        switch (mCarOrder.getState()){
            case 0:
                tv_carstate.setText("已取消");
                break;
            case 2:
                tv_carstate.setText("服务中");
                tv_cancel.setVisibility(View.VISIBLE);
                break;
            case 3:
                tv_carstate.setText("待评价");
                ll_state1.setVisibility(View.VISIBLE);
                break;
            case 4:
                tv_carstate.setText("已完成");
                tv_seecomment.setVisibility(View.VISIBLE);
                break;
        }
        getMerchat();
    }

}
