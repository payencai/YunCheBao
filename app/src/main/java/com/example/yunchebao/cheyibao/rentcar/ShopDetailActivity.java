package com.example.yunchebao.cheyibao.rentcar;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.maps.model.LatLng;
import com.cheyibao.fragment.RentCarModelsFragment;
import com.cheyibao.fragment.RentShopComentFragment;
import com.cheyibao.model.RentShop;
import com.cheyibao.util.RentCarUtils;
import com.common.ConfirmDialog;
import com.common.DialPhoneUtils;
import com.example.yunchebao.MyApplication;
import com.example.yunchebao.R;
import com.example.yunchebao.net.Api;
import com.example.yunchebao.net.NetUtils;
import com.example.yunchebao.net.OnMessageReceived;
import com.flyco.tablayout.SlidingTabLayout;
import com.google.gson.Gson;
import com.iarcuschin.simpleratingbar.SimpleRatingBar;
import com.lzy.okgo.model.HttpParams;
import com.example.yunchebao.maket.adapter.GoodsOrderImageAdapter;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.tool.view.HorizontalListView;
import com.vipcenter.RegisterActivity;
import com.xihubao.ShopInfoActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.rong.imkit.RongIM;


public class ShopDetailActivity extends AppCompatActivity {

    @BindView(R.id.shop_name_view)
    TextView shopNameView;
    @BindView(R.id.grade_view)
    TextView gradeView;
    @BindView(R.id.business_hours_view)
    TextView businessHoursView;
    @BindView(R.id.sb_score)
    SimpleRatingBar sbScore;
    @BindView(R.id.score_view)
    TextView scoreView;
    @BindView(R.id.address_view)
    TextView addressView;
    @BindView(R.id.phone_view)
    TextView phoneView;
    @BindView(R.id.head_view)
    ImageView headView;
    @BindView(R.id.photo_list_view)
    HorizontalListView photoListView;
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.rl_top)
    RelativeLayout rlTop;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tab_layout)
    SlidingTabLayout tabLayout;
    @BindView(R.id.app_barlayout)
    AppBarLayout appBarlayout;
    @BindView(R.id.viewpager)
    ViewPager viewpager;
    @BindView(R.id.call_view)
    TextView callView;
    @BindView(R.id.online_chat_view)
    TextView onlineChatView;
    @BindView(R.id.ll_bottom)
    LinearLayout llBottom;

    private RentShop rentShop;

    private GoodsOrderImageAdapter adapter;

    ArrayList<Fragment> mFragments = new ArrayList<>();

    String[] titles = {"租借车型", "查看评论"};


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_detail);
        ButterKnife.bind(this);
        rentShop = (RentShop) RentCarUtils.rentCarInfo.get(RentCarUtils.RENT_CAR_INFO_SHOP);
        initView();
    }
    RentCarDetail rentCarDetail;
    private void getDetail(){
        HttpParams httpParams=new HttpParams();
        httpParams.put("shopId",rentShop.getId());
        NetUtils.getInstance().get( MyApplication.token,Api.CarRent.getRentCarShopById,httpParams , new OnMessageReceived() {
            @Override
            public void onSuccess(String response) {
                try {
                    JSONObject jsonObject=new JSONObject(response);
                    int code=jsonObject.getInt("resultCode");
                    if(code==0){
                        JSONObject data=jsonObject.getJSONObject("data");
                        rentCarDetail=new Gson().fromJson(data.toString(),RentCarDetail.class);
                        setUI();
                    }else{

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(String error) {

            }
        });
    }
    private void setUI(){
        shopNameView.setText(rentCarDetail.getName());
        gradeView.setText(String.format("%s", rentCarDetail.getGrade()));
        sbScore.setRating((float) rentCarDetail.getScore());
        String time="营业时间:"+rentCarDetail.getAmStart()+"-"+rentCarDetail.getPmStop();
        businessHoursView.setText(time.replace("null",""));
        if(TextUtils.isEmpty(rentCarDetail.getAmStart())){
            businessHoursView.setText("营业时间:8:00-18:30");
        }
       // ToastUtils.showLongToast(this,rentCarDetail.getId());
        scoreView.setText(String.format("%s分", (int)(rentCarDetail.getScore())));
        addressView.setText(String.format("%s%s%s%s", rentCarDetail.getProvince(), rentCarDetail.getCity(), rentCarDetail.getArea(), rentCarDetail.getAddress()));
        phoneView.setText(rentCarDetail.getSaleTelephone());
        List<String> images = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            images.add("http://img02.tooopen.com/images/20150820/tooopen_sy_139083876216.jpg");
        }
        if (images.size() == 0) {
            photoListView.setVisibility(View.GONE);
        }
        adapter = new GoodsOrderImageAdapter(this, images);
        photoListView.setAdapter(adapter);
    }
    private void initView() {
        getDetail();
        mFragments.add(new RentCarModelsFragment());
        mFragments.add(new RentShopComentFragment());
        tabLayout.setViewPager(viewpager, titles, this, mFragments);
    }

    @OnClick({R.id.back,R.id.online_chat_view,R.id.head_view,R.id.address_view})
     void onClicked(View view) {
        switch (view.getId()){
            case R.id.address_view:
                showMapDialog(new LatLng(Double.parseDouble(rentShop.getLatitude()), Double.parseDouble(rentShop.getLongitude())));
                break;
            case R.id.head_view:
                Intent intent = new Intent(ShopDetailActivity.this, ShopInfoActivity.class);
                intent.putExtra("id", rentShop.getId());
                startActivity(intent);
                break;
            case R.id.online_chat_view:
                if(MyApplication.isIsLogin())
                   RongIM.getInstance().startPrivateChat(ShopDetailActivity.this, rentShop.getId(), rentShop.getName());
                else {
                    startActivity(new Intent(ShopDetailActivity.this, RegisterActivity.class));
                }
                break;
            case R.id.back:
                finish();
                break;
        }

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

    private void showMapDialog(final LatLng latLng) {
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
    @SuppressLint("CheckResult")
    @OnClick(R.id.call_view)
    public void onCallViewClicked() {
        RxPermissions rxPermissions = new RxPermissions(this);
        rxPermissions.request(Manifest.permission.CALL_PHONE).subscribe(aBoolean -> {
            if (aBoolean){
                ConfirmDialog confirmDialog = new ConfirmDialog(ShopDetailActivity.this);
                confirmDialog .setTitle("拨号提示")
                        .setMessageText("确定拨打电话吗？")
                        .setCancelable(true)
                        .setConfirmClickListener(v -> {
                            DialPhoneUtils.startDialNumber(ShopDetailActivity.this,rentShop.getSaleTelephone());
                            confirmDialog.dismiss();
                        })
                        .setCancelClickListener(v -> confirmDialog.dismiss());
                confirmDialog.show();
            }
        });
    }

    @OnClick(R.id.online_chat_view)
    public void onOnlineChatViewClicked() {
    }
}
