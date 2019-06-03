package com.xihubao;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.maps.model.LatLng;
import com.example.yunchebao.MyApplication;
import com.costans.PlatformContans;
import com.example.yunchebao.R;
import com.flyco.tablayout.SlidingTabLayout;
import com.google.gson.Gson;
import com.gyf.immersionbar.ImmersionBar;
import com.http.HttpProxy;
import com.http.ICallBack;
import com.example.yunchebao.rongcloud.model.CarShop;
import com.tool.MathUtil;
import com.tool.slideshowview.SlideShowView;
import com.vipcenter.RegisterActivity;
import com.xihubao.adapter.TabFragmentAdapter;
import com.xihubao.fragment.EvaluateFragment;
import com.xihubao.fragment.GoodsFragment;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class WashCarDetailActivity extends FragmentActivity {

    private SlidingTabLayout slidingTabLayout;
    private List<Map<String, String>> imageList = new ArrayList<>();
    //fragment列表
    private ArrayList<Fragment> mFragments=new ArrayList<>();
    //tab名的列表
    private List<String> mTitles=new ArrayList<>();
    private ViewPager viewPager;
    private TabFragmentAdapter adapter;
    String id;
    int type=0;

    CarShop mCarShop;
    @BindView(R.id.bg_img)
    SlideShowView bgImg;
    @BindView(R.id.shopname)
    TextView shopname;
    @BindView(R.id.score)
    TextView score;
    @BindView(R.id.dis)
    TextView dis;
    @BindView(R.id.address)
    TextView address;
    @BindView(R.id.tv_grade)
    TextView tv_grade;
    @BindView(R.id.collectIcon)
    RelativeLayout collectIcon;
    @BindView(R.id.iv_heart)
    ImageView iv_heart;
    int isCollect;
    String [] titles={"服务","评价"

    };

    public CarShop getCarShop() {
        return mCarShop;
    }

    public void setCarShop(CarShop carShop) {
        mCarShop = carShop;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.washcar_shop_detail);
        ButterKnife.bind(this);

        mCarShop= (CarShop) getIntent().getExtras().getSerializable("data");
        id=getIntent().getStringExtra("id");
        type=getIntent().getExtras().getInt("flag");
        slidingTabLayout = (SlidingTabLayout) findViewById(R.id.slidinglayout);
        viewPager = (ViewPager) findViewById(R.id.vp);
        initView();

    }

    private void initData(){
        if(!TextUtils.isEmpty(mCarShop.getBanner())){
            List<String> result = Arrays.asList(mCarShop.getBanner().split(","));
            for (int i = 0; i < result.size(); i++) {
                String url = result.get(i);
                Map<String, String> image_uri = new HashMap<String, String>();
                image_uri.put("imageUrls", url);
                imageList.add(image_uri);
            }
            bgImg.setImageUrls(imageList);
        }

        //bgImg.setImageURI(Uri.parse(mCarShop.getBanner()));
        shopname.setText(mCarShop.getShopName());
        score.setText(mCarShop.getScore()+"");
        dis.setText(MathUtil.getDoubleTwo(mCarShop.getDistance())+"km");
        tv_grade.setText(mCarShop.getGrade()+"");
        address.setText(mCarShop.getAddress());
        collectIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(MyApplication.isLogin){
                    if(isCollect==0)
                        collect();
                    else
                        deleteCollect();
                }else{
                    startActivity(new Intent(WashCarDetailActivity.this, RegisterActivity.class));
                }
            }
        });
        getIsCollect();
        initViewPager();
    }
    private void getDetail(){
        Map<String, Object> params = new HashMap<>();
        params.put("id", id);
        params.put("longitude", MyApplication.getaMapLocation().getLongitude());
        params.put("latitude", MyApplication.getaMapLocation().getLatitude());
        HttpProxy.obtain().get(PlatformContans.CarWashRepairShop.getWashRepairShopById, params, new ICallBack() {
            @Override
            public void OnSuccess(String result) {
                Log.e("params", result);
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    JSONObject data = jsonObject.getJSONObject("data");
                    mCarShop=new Gson().fromJson(data.toString(),CarShop.class);
                    initData();

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

        if(mCarShop==null){
            getDetail();
        }else{
            initData();
        }
    }
    private void collect(){
        String token="";
        if(MyApplication.isLogin){
            token=MyApplication.token;
        }
        Map<String,Object> params=new HashMap<>();
        params.put("shopId",mCarShop.getId());
        params.put("type",1);
        HttpProxy.obtain().post(PlatformContans.Collect.addWashCollection, token,params , new ICallBack() {
            @Override
            public void OnSuccess(String result) {
                Log.e("heart",result);
                iv_heart.setImageResource(R.mipmap.orange_heart_icon);
                getIsCollect();
                Toast.makeText(WashCarDetailActivity.this,"收藏成功",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(String error) {

            }
        });
    }
    private void getIsCollect(){
        String token="";
        if(MyApplication.isLogin){
            token=MyApplication.token;
        }
        Map<String,Object> params=new HashMap<>();
        params.put("shopId",mCarShop.getId());
        params.put("type",1);
        HttpProxy.obtain().get(PlatformContans.Collect.isCollectionByShopId, params, token, new ICallBack() {
            @Override
            public void OnSuccess(String result) {
                Log.e("heart",result);
                try {
                    JSONObject jsonObject=new JSONObject(result);
                    String data=jsonObject.getString("data");
                    if(data.equals("1")){
                        iv_heart.setImageResource(R.mipmap.orange_heart_icon);
                        isCollect=1;
                    }else{
                        isCollect=0;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
               // iv_heart.setImageResource(R.mipmap.white_heart_icon);
               // Toast.makeText(WashCarDetailActivity.this,"取消成功",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(String error) {

            }
        });
    }
    private void deleteCollect(){
        String token="";
        if(MyApplication.isLogin){
            token=MyApplication.token;
        }
        Map<String,Object> params=new HashMap<>();
        params.put("shopId",mCarShop.getId());
        params.put("type",1);
        HttpProxy.obtain().post(PlatformContans.Collect.deleteWashCollection,  token,params, new ICallBack() {
            @Override
            public void OnSuccess(String result) {
                Log.e("heart",result);

                iv_heart.setImageResource(R.mipmap.white_heart_icon);
                getIsCollect();
                Toast.makeText(WashCarDetailActivity.this,"取消成功",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(String error) {

            }
        });
    }
    private void initViewPager() {
        GoodsFragment goodsFragment=GoodsFragment.newInstance(mCarShop.getId(),type);
        EvaluateFragment evaluateFragment=EvaluateFragment.newInstance(mCarShop.getId());
        mFragments.add(goodsFragment);
        mFragments.add(evaluateFragment);
        mTitles.add("服务");
        mTitles.add("评价");
        MyPagerAdapter adapter=new MyPagerAdapter(getSupportFragmentManager());
       // adapter=new TabFragmentAdapter(getSupportFragmentManager(),mFragments,mTitles);
        viewPager.setAdapter(adapter);
        slidingTabLayout.setViewPager(viewPager);
//        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
//            @Override
//            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//
//            }
//
//            @Override
//            public void onPageSelected(int position) {
//                switch (position){
//                    case 0:
//                        break;
//                    case 1:
//                        break;
//                    case 2:
//                        break;
//                }
//            }
//
//            @Override
//            public void onPageScrollStateChanged(int state) {
//
//            }
//        });
    }

    private class MyPagerAdapter extends FragmentPagerAdapter {
        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titles[position];
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }
    }
    @OnClick({R.id.back,R.id.league,R.id.ll_shop,R.id.phoneIcon})
    public void OnClick(View v){
        switch (v.getId()){
            case R.id.phoneIcon:
                callPhone(mCarShop.getSaleTelephone());
                break;
            case R.id.ll_shop:
                 Intent intent=new Intent(WashCarDetailActivity.this,ShopInfoActivity.class);
                 intent.putExtra("id",mCarShop.getId());
                 startActivity(intent);
                break;
            case R.id.back:
                onBackPressed();
                break;
            case R.id.league:
                LatLng latLng=new LatLng(Double.parseDouble(mCarShop.getLatitude()),Double.parseDouble(mCarShop.getLongitude()));
                showDialog(latLng);
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
