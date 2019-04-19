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
import android.widget.LinearLayout;
import android.widget.TextView;

import com.application.MyApplication;
import com.cheyibao.AddSchoolCommentActivity;
import com.costans.PlatformContans;
import com.example.yunchebao.R;
import com.fourshop.activity.AddFourCommentActivity;
import com.fourshop.activity.SeeCommentActivity;
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

public class WashOrderDetailActivity extends AppCompatActivity {
    CarOrder mCarOrder;

    @BindView(R.id.tv_name)
    TextView tv_name;
    @BindView(R.id.tv_washstate)
    TextView tv_washstate;
    @BindView(R.id.tv_washtype)
    TextView tv_washtype;
    @BindView(R.id.tv_content)
    TextView tv_content;
    @BindView(R.id.tv_date)
    TextView tv_date;
    @BindView(R.id.tv_price)
    TextView tv_price;
    @BindView(R.id.tv_phone)
    TextView tv_phone;
    @BindView(R.id.tv_time)
    TextView tv_time;
    @BindView(R.id.ll_state1)
    LinearLayout ll_state1;
    @BindView(R.id.tv_address)
    TextView tv_address;
    @BindView(R.id.tv_seecomment)
    TextView tv_seecomment;
    @BindView(R.id.tv_cancel)
    TextView tv_cancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wash_order_detail);
        ButterKnife.bind(this);
        mCarOrder= (CarOrder) getIntent().getSerializableExtra("data");
        initView();
    }

    private void initView() {
        tv_name.setText(mCarOrder.getShopName());
        tv_phone.setText(mCarOrder.getTelephone());
        tv_washtype.setText(mCarOrder.getServeTitle());
        tv_content.setText(mCarOrder.getServeCategory());
        tv_address.setText(mCarOrder.getAddress());
        tv_date.setText(mCarOrder.getCreateTime().substring(0,10));
        tv_time.setText(mCarOrder.getCreateTime().substring(0,10));
        tv_price.setText("总价：￥"+mCarOrder.getPrice());
        switch (mCarOrder.getState()){
            case 0:
                tv_washstate.setText("已取消");
                break;
            case 2:
                tv_washstate.setText("服务中");
                tv_cancel.setVisibility(View.VISIBLE);
                break;
            case 3:
                tv_washstate.setText("待评价");
                ll_state1.setVisibility(View.VISIBLE);
                break;
            case 4:
                tv_washstate.setText("已完成");
                tv_seecomment.setVisibility(View.VISIBLE);
                break;
        }
    }
    private void washCancel(String id) {
        Map<String, Object> params = new HashMap<>();
        params.put("id", id);
        HttpProxy.obtain().post(PlatformContans.CarWashRepairShop.cancelWashRepairOrder, MyApplication.token, params, new ICallBack() {
            @Override
            public void OnSuccess(String result) {
                Log.e("result", result);
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    int code = jsonObject.getInt("resultCode");
                    if (code == 0) {
                        ToastUtil.showToast(WashOrderDetailActivity.this, "取消成功");
                        finish();
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
    private void fourCancel(String id) {
        Map<String, Object> params = new HashMap<>();
        params.put("id", id);
        HttpProxy.obtain().post(PlatformContans.FourShop.cancelFourShopOrder, MyApplication.token, params, new ICallBack() {
            @Override
            public void OnSuccess(String result) {
                Log.e("result", result);
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    int code = jsonObject.getInt("resultCode");
                    if (code == 0) {
                        ToastUtil.showToast(WashOrderDetailActivity.this, "取消成功");
                        finish();
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
                if(mCarOrder.getFlag()==1){
                    washCancel(id);
                }else{
                    fourCancel(id);
                }

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
                callPhone(mCarOrder.getTelephone());
                break;
            case R.id.tv_seecomment:
                if(mCarOrder.getFlag()==4){
                    Intent intent3 = new Intent(WashOrderDetailActivity.this, SeeCommentActivity.class);
                    intent3.putExtra("id", mCarOrder.getId());
                    startActivity(intent3);
                }
                break;
            case R.id.tv_cancel:
                showCancelDialog(mCarOrder.getId());
                break;
            case R.id.tv_comment:
                if(mCarOrder.getFlag()==1){
                    Intent intent = new Intent(WashOrderDetailActivity.this, PubCommentActivity.class);
                    intent.putExtra("id", mCarOrder.getId());
                    intent.putExtra("flag", mCarOrder.getFlag());
                    startActivity(intent);
                }else{
                    Intent intent = new Intent(WashOrderDetailActivity.this, AddFourCommentActivity.class);
                    intent.putExtra("id", mCarOrder.getId());
                    startActivity(intent);
                }


                break;
        }
    }
    public void callPhone(String phoneNum) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        Uri data = Uri.parse("tel:" + phoneNum);
        intent.setData(data);
        startActivity(intent);
    }
}
