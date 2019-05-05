package com.xihubao;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.costans.PlatformContans;
import com.example.yunchebao.R;
import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;
import com.http.HttpProxy;
import com.http.ICallBack;
import com.nohttp.sample.NoHttpBaseActivity;
import com.example.yunchebao.road.RoadOrderActivity;
import com.xihubao.adapter.RoadItemAdapter;
import com.xihubao.model.Road;
import com.xihubao.model.RoadService;
import com.example.yunchebao.road.PubRoadActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by sdhcjhss on 2017/12/11.
 */

public class AssistanceDetailActivity extends NoHttpBaseActivity {
    Context ctx;
    Road mPhoneShopEntity;
    @BindView(R.id.address)
    TextView address;
    @BindView(R.id.shopname)
    TextView shopname;
    @BindView(R.id.phone)
    TextView phone;
    @BindView(R.id.img)
    SimpleDraweeView mSimpleDraweeView;
    @BindView(R.id.listview)
    ListView listView;
    private RoadItemAdapter adapter;
    private List<RoadService> list=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.assistance_detail_layout);
        mPhoneShopEntity= (Road) getIntent().getSerializableExtra("entity");
        ButterKnife.bind(this);
        initView();
        getService();
    }
    private void getService(){
        Map<String, Object> params = new HashMap<>();
        params.put("shopId", mPhoneShopEntity.getId());
        HttpProxy.obtain().get(PlatformContans.RoadRescue.getRoadRescueServeListForApp, params, new ICallBack() {
            @Override
            public void OnSuccess(String result) {
                Log.e("road", result);
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    JSONArray data = jsonObject.getJSONArray("data");
                    for (int i = 0; i < data.length(); i++) {
                        JSONObject item = data.getJSONObject(i);
                        RoadService road = new Gson().fromJson(item.toString(), RoadService.class);
                        list.add(road);
                    }
                    adapter.notifyDataSetChanged();
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


        ctx = this;
        if(mPhoneShopEntity!=null){
            address.setText("商家地址 : "+mPhoneShopEntity.getAddress());
            phone.setText("商家电话 : "+mPhoneShopEntity.getSaleTelephone());
            shopname.setText(mPhoneShopEntity.getShopName());
            mSimpleDraweeView.setImageURI(Uri.parse(mPhoneShopEntity.getLogo()));
        }
        adapter = new RoadItemAdapter(ctx, list);
        listView.setAdapter(adapter);
    }

    public void callToPhoneSweetAlert() {
        new SweetAlertDialog(ctx, SweetAlertDialog.NORMAL_TYPE)
                .setTitleText("确定要电话联系商家吗？")
                .setConfirmText("拨打")
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        sweetAlertDialog.dismiss();
                        callToPhone();
                    }
                })
                .setCancelText("算了")
                .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        sweetAlertDialog.dismiss();
                    }
                })
                .show();
    }

    private void callToPhone() {
        /**
         *  借助ContextCompat.checkSelfPermission()方法判断是否授予权限，接收两个参数，Context和具体权限名，方法的返回值与
         *  PackageManager.PERMISSION_GRANTED做比较，相等说明已经授权
         */
        if(ContextCompat.checkSelfPermission(ctx, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED){
            /**
             * 同样借助ContextCompat.requestPermissions()方法弹出权限申请的对话框
             * 参数为Context,具体权限名，作为返回结果的识别码（自定义）
             */
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.CALL_PHONE},1);
        }else{
            //已授权拨打电话
            try{
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:"+ "10010"));
                startActivity(intent);
            } catch (SecurityException e){
                e.printStackTrace();
            }
        }
    }

    @OnClick({R.id.back,R.id.callBtn,R.id.tv_pub,R.id.menuBtn})
    public void OnClick(View v){
        switch (v.getId()){
            case R.id.menuBtn:
                startActivity(new Intent(this, RoadOrderActivity.class).putExtra("id",mPhoneShopEntity.getId()));
                break;
            case R.id.tv_pub:
                Intent intent=new Intent(this, PubRoadActivity.class);
                intent.putExtra("id",mPhoneShopEntity.getId());
                startActivity(intent);
                break;
            case R.id.back:
                onBackPressed();
                break;
            case R.id.callBtn:
                callToPhoneSweetAlert();
                break;
        }
    }

}
