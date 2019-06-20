package com.cheyibao;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.maps.model.LatLng;
import com.cheyibao.fragment.RentComentFragment;
import com.cheyibao.fragment.RentShopFragment;
import com.cheyibao.model.Merchant;
import com.cheyibao.model.RentCar;
import com.costans.PlatformContans;
import com.example.yunchebao.R;
import com.flyco.tablayout.SlidingTabLayout;
import com.google.gson.Gson;
import com.http.HttpProxy;
import com.http.ICallBack;
import com.iarcuschin.simpleratingbar.SimpleRatingBar;
import com.example.yunchebao.maket.adapter.GoodsOrderImageAdapter;
import com.tool.MathUtil;
import com.tool.view.HorizontalListView;
import com.xihubao.ShopInfoActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by sdhcjhss on 2017/12/24.
 */

public class RentShopDetailActivity extends AppCompatActivity {
    private Context ctx;
    @BindView(R.id.tab_layout)
    SlidingTabLayout tab_shop;
    @BindView(R.id.viewpager)
    ViewPager vp_shop;
    @BindView(R.id.tv_shopname)
    TextView tv_shopname;
    @BindView(R.id.tv_address)
    TextView tv_address;
    @BindView(R.id.tv_phone)
    TextView tv_phone;
    @BindView(R.id.hlv_photo)
    HorizontalListView hlv_photo;
    @BindView(R.id.sb_score)
    SimpleRatingBar sb_score;
    @BindView(R.id.tv_score)
    TextView tv_score;
    @BindView(R.id.tv_grade)
    TextView tv_grade;
    ArrayList<Fragment> mFragments=new ArrayList<>();
    GoodsOrderImageAdapter mRentCarImageAdapter;
    List<String> images=new ArrayList<>();
    RentCar mRentCar;
    Merchant merchant;
    String [] titles={"租借车型","查看评论"};
    public RentCar getRentCar() {
        return mRentCar;
    }

    public void setRentCar(RentCar rentCar) {
        mRentCar = rentCar;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rent_shop_detail);
        mRentCar= (RentCar) getIntent().getSerializableExtra("data");
        ButterKnife.bind(this);
        initView();
        setUI();
    }
    private void setUI(){
        tv_shopname.setText(mRentCar.getName());
        tv_address.setText(mRentCar.getProvince()+mRentCar.getCity()+mRentCar.getDistrict()+mRentCar.getAddress());
        tv_phone.setText(mRentCar.getServiceTelephone());
        tv_score.setText(MathUtil.getOne(mRentCar.getScore())+"分");
        sb_score.setRating(mRentCar.getScore());
        tv_grade.setText(mRentCar.getGrade()+"");
        tv_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LatLng latLng=new LatLng(Double.parseDouble(mRentCar.getLatitude()),Double.parseDouble(mRentCar.getLongitude()));
                showDialog(latLng);
            }
        });
        if(!TextUtils.isEmpty(mRentCar.getBanner())){
            mRentCarImageAdapter=new GoodsOrderImageAdapter(RentShopDetailActivity.this,mRentCar.getImages());
            hlv_photo.setAdapter(mRentCarImageAdapter);
        }
        getMerchat();
    }
    private void getMerchat() {
        Map<String, Object> params = new HashMap<>();
        params.put("merchantId", mRentCar.getId());
        HttpProxy.obtain().get(PlatformContans.Shop.getMerchantById, params, new ICallBack() {
            @Override
            public void OnSuccess(String result) {
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    JSONObject data = jsonObject.getJSONObject("data");
                    merchant = new Gson().fromJson(data.toString(), Merchant.class);
                   // tv_phone.setText(mRentCar.getServiceTelephone());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(String error) {

            }
        });
    }
    private void google(double mLatitude, double mLongitude) {
        if (isAvilible(this, "com.google.android.apps.maps")) {
            Uri gmmIntentUri = Uri.parse("google.navigation:q="
                    + mLatitude + "," + mLongitude
                    + ", + Sydney +Australia");
            Intent mapIntent = new Intent(Intent.ACTION_VIEW,
                    gmmIntentUri);
            mapIntent.setPackage("com.google.android.apps.maps");
            startActivity(mapIntent);
        } else {
            Toast.makeText(this, "您尚未安装谷歌地图", Toast.LENGTH_LONG)
                    .show();
            Uri uri = Uri
                    .parse("market://details?id=com.google.android.apps.maps");
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
        }
    }

    private void showDialog(final LatLng latLng) {
        final Dialog dialog = new Dialog(this, R.style.dialog);
        View dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_map, null);
        //获得dialog的window窗口
        Window window = dialog.getWindow();
        //设置dialog在屏幕底部
        window.setGravity(Gravity.BOTTOM);
        //设置dialog弹出时的动画效果，从屏幕底部向上弹出
        window.setWindowAnimations(R.style.mypopwindow_anim_style);
        window.getDecorView().setPadding(0, 0, 0, 0);
        //获得window窗口的属性
        android.view.WindowManager.LayoutParams lp = window.getAttributes();
        //设置窗口宽度为充满全屏
        lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
        //设置窗口高度为包裹内容
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        //将设置好的属性set回去
        window.setAttributes(lp);
        //将自定义布局加载到dialog上
        dialog.setContentView(dialogView);
        dialog.findViewById(R.id.tv_baidu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                baidu(latLng.latitude, latLng.longitude);
            }
        });
        dialog.findViewById(R.id.tv_google).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                google(latLng.latitude, latLng.longitude);

            }
        });
        dialog.findViewById(R.id.tv_gaode).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                gaode(latLng.latitude, latLng.longitude);

            }
        });
        dialog.findViewById(R.id.tv_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();

            }
        });
        dialog.show();
    }

    private void baidu(double mLatitude, double mLongitude) {
        LatLng poit = new LatLng(mLatitude, mLongitude);
        if (isAvilible(this, "com.baidu.BaiduMap")) {// 传入指定应用包名

            try {
                Intent intent = Intent.getIntent("intent://map/direction?destination=latlng:"
                        + poit.latitude + ","
                        + poit.longitude + "|name:" + // 终点
                        "&mode=driving&" + // 导航路线方式
                        "region=" + //
                        "&src=广州番禺#Intent;scheme=bdapp;package=com.baidu.BaiduMap;end");
                startActivity(intent); // 启动调用
            } catch (URISyntaxException e) {
                Log.e("intent", e.getMessage());
            }
        } else {// 未安装
            Toast.makeText(this, "您尚未安装百度地图", Toast.LENGTH_LONG)
                    .show();
            Uri uri = Uri
                    .parse("market://details?id=com.baidu.BaiduMap");
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
        }
    }
    public static boolean isAvilible(Context context, String packageName) {
        // 获取packagemanager
        final PackageManager packageManager = context.getPackageManager();
        // 获取所有已安装程序的包信息
        List<PackageInfo> packageInfos = packageManager.getInstalledPackages(0);
        // 用于存储所有已安装程序的包名
        List<String> packageNames = new ArrayList<String>();
        // 从pinfo中将包名字逐一取出，压入pName list中
        if (packageInfos != null) {
            for (int i = 0; i < packageInfos.size(); i++) {
                String packName = packageInfos.get(i).packageName;
                packageNames.add(packName);
            }
        }
        // 判断packageNames中是否有目标程序的包名，有TRUE，没有FALSE
        return packageNames.contains(packageName);
    }
    private void gaode(double mLatitude, double mLongitude) {
        if (isAvilible(this, "com.autonavi.minimap")) {
            try {
                Intent intent = Intent.getIntent("androidamap://navi?sourceApplication=新疆和田&poiname=" + "广州" + "&lat="
                        + mLatitude
                        + "&lon="
                        + mLongitude + "&dev=0");
                startActivity(intent);
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(this, "您尚未安装高德地图", Toast.LENGTH_LONG)
                    .show();
            Uri uri = Uri
                    .parse("market://details?id=com.autonavi.minimap");
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
        }
    }

    private void initView() {

        mFragments.add(new RentShopFragment());
        mFragments.add(new RentComentFragment());
        tab_shop.setViewPager(vp_shop,titles,this,mFragments);

    }

    @OnClick({R.id.back,R.id.iv_head,R.id.tv_call})
    public void Onclick(View v){
        switch (v.getId()){
            case R.id.tv_call:
                callToPhoneSweetAlert(mRentCar.getServiceTelephone());
                break;
            case R.id.iv_head:
                Intent intent = new Intent(RentShopDetailActivity.this, ShopInfoActivity.class);
                intent.putExtra("id", mRentCar.getId());
                startActivity(intent);
               // callToPhoneSweetAlert(mRentCar.getServiceTelephone());
                break;
            case R.id.back:
                onBackPressed();
                break;
        }
    }

    public void callToPhone(String phone) {
        /**
         *  借助ContextCompat.checkSelfPermission()方法判断是否授予权限，接收两个参数，Context和具体权限名，方法的返回值与
         *  PackageManager.PERMISSION_GRANTED做比较，相等说明已经授权
         */
        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            /**
             * 同样借助ContextCompat.requestPermissions()方法弹出权限申请的对话框
             * 参数为Context,具体权限名，作为返回结果的识别码（自定义）
             */
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE}, 1);
        } else {
            //已授权拨打电话
            try {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + phone));
                startActivity(intent);
            } catch (SecurityException e) {
                e.printStackTrace();
            }
        }
    }
    public void callToPhoneSweetAlert(final String phone) {
        new SweetAlertDialog(this, SweetAlertDialog.NORMAL_TYPE)
                .setTitleText("确定要电话联系商家吗？")
                .setConfirmText("拨打")
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        sweetAlertDialog.dismiss();
                        callToPhone(phone);
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


}
