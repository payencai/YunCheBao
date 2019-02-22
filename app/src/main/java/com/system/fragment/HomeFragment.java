package com.system.fragment;


import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.model.LatLng;
import com.application.MyApplication;

import com.baike.MagzineCoverActivity;
import com.bbcircle.CarShowDetailActivity;
import com.bbcircle.CommentsActivity;
import com.bbcircle.NewDrvingActivity;
import com.bumptech.glide.Glide;

import com.cityselect.CityListActivity;
import com.costans.PlatformContans;
import com.entity.Banner;
import com.entity.FourShop;
import com.entity.PhoneGoodEntity;
import com.entity.UrlBean;
import com.example.yunchebao.R;
import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.http.HttpProxy;
import com.http.ICallBack;
import com.maket.GoodDetailActivity;
import com.maket.model.GoodList;
import com.maket.model.KnowYou;
import com.nohttp.rest.Request;
import com.nohttp.rest.Response;
import com.nohttp.sample.BaseFragment;
import com.nohttp.sample.HttpListener;
import com.nohttp.tools.HttpJsonClient;
import com.rongcloud.activity.ChatActivity;
import com.rongcloud.activity.contact.FriendDetailActivity;
import com.rongcloud.adapter.ListDataSave;
import com.rongcloud.model.Friend;
import com.rongcloud.model.MyFriend;
import com.rongcloud.model.MyGroup;
import com.rongcloud.sidebar.ContactModel;
import com.rongcloud.sidebar.ContactsAdapter;
import com.system.WebviewActivity;
import com.system.adapter.HomeListAdapter;
import com.system.model.HomeImage;
import com.system.model.Weather;
import com.tool.view.ListViewForScrollView;
import com.vipcenter.RegisterActivity;
import com.vipcenter.UserCenterActivity;
import com.system.adapter.HomeMenuListAdapter;
import com.tool.ActivityAnimationUtils;
import com.tool.ActivityConstans;
import com.tool.slideshowview.SlideShowView;
import com.tool.view.GridViewForScrollView;
import com.tool.view.HorizontalListView;
import com.vipcenter.model.UserInfo;
import com.xihubao.BrandGoodsListActivity;
import com.xihubao.MapShopActivity;
import com.xihubao.NewGoodsListActivity;
import com.xihubao.RepairListActivity;
import com.xihubao.RoadAssistanceListActivity;
import com.xihubao.Shop4SInfoActivity;
import com.xihubao.WashCarListActivity;
import com.youth.banner.BannerConfig;
import com.youth.banner.listener.OnBannerListener;
import com.yuedan.YuedanHomeActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.litepal.LitePal;


import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import go.error;
import io.rong.imkit.RongIM;
import io.rong.imkit.model.GroupUserInfo;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Group;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends BaseFragment {

    private static final String TAG = "MainActivity";
    private Context ctx;
    //轮播图片
    private List<Map<String, String>> imageList = new ArrayList<>();
    @BindView(R.id.tv_wendu)
    TextView tv_wendu;
    @BindView(R.id.tv_city)
    TextView tv_city;
    @BindView(R.id.tv_wash)
    TextView tv_wash;
    @BindView(R.id.tv_go)
    TextView tv_go;
    @BindView(R.id.tv_locate)
    TextView tv_locate;
    @BindView(R.id.tv_weather)
    TextView tv_weather;
    @BindView(R.id.slideshowView)
    com.youth.banner.Banner banner;
    @BindView(R.id.lv_home)
    ListViewForScrollView lv_home;
    @BindView(R.id.menuLay5)
    LinearLayout menuLay5;
    @BindView(R.id.menuLay7)
    LinearLayout menuLay7;
    @BindView(R.id.rl_weather)
    RelativeLayout rl_weather;
    @BindView(R.id.ll_item1)
    LinearLayout ll_item1;
    @BindView(R.id.ll_item2)
    LinearLayout ll_item2;
    @BindView(R.id.ll_item3)
    LinearLayout ll_item3;
    List<HomeImage> mHomeImages;
    HomeListAdapter mHomeListAdapter;




    List<Banner> mBanners = new ArrayList<>();

    private void google(double mLatitude, double mLongitude) {
        if (isAvilible(getContext(), "com.google.android.apps.maps")) {
            Uri gmmIntentUri = Uri.parse("google.navigation:q="
                    + mLatitude + "," + mLongitude
                    + ", + Sydney +Australia");
            Intent mapIntent = new Intent(Intent.ACTION_VIEW,
                    gmmIntentUri);
            mapIntent.setPackage("com.google.android.apps.maps");
            startActivity(mapIntent);
        } else {
            Toast.makeText(getContext(), "您尚未安装谷歌地图", Toast.LENGTH_LONG)
                    .show();
            Uri uri = Uri
                    .parse("market://details?id=com.google.android.apps.maps");
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
        }
    }

    private void showDialog(final LatLng latLng) {
        final Dialog dialog = new Dialog(getContext(), R.style.dialog);
        View dialogView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_map, null);
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
        if (isAvilible(getContext(), "com.baidu.BaiduMap")) {// 传入指定应用包名

            try {
                Intent intent = Intent.getIntent("intent://map/direction?destination=latlng:"
                        + poit.latitude + ","
                        + poit.longitude + "|name:" + // 终点
                        "&mode=driving&" + // 导航路线方式
                        "region=广东" + //
                        "&src=广州番禺#Intent;scheme=bdapp;package=com.baidu.BaiduMap;end");
                startActivity(intent); // 启动调用
            } catch (URISyntaxException e) {
                Log.e("intent", e.getMessage());
            }
        } else {// 未安装
            Toast.makeText(getContext(), "您尚未安装百度地图", Toast.LENGTH_LONG)
                    .show();
            Uri uri = Uri
                    .parse("market://details?id=com.baidu.BaiduMap");
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
        }
    }

    private void gaode(double mLatitude, double mLongitude) {
        if (isAvilible(getContext(), "com.autonavi.minimap")) {
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
            Toast.makeText(getContext(), "您尚未安装高德地图", Toast.LENGTH_LONG)
                    .show();
            Uri uri = Uri
                    .parse("market://details?id=com.autonavi.minimap");
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, rootView);
        MyApplication.setDataSave(new ListDataSave(MyApplication.getContext(), "data"));
        if(Build.VERSION.SDK_INT>=28)
            initGPS(getActivity());
        initLocation();
        getImageUrl();
        init();
        return rootView;
    }
    List<UrlBean> mUrlBeans = new ArrayList<>();
    List<String> images = new ArrayList<>();
    public AMapLocationClient mLocationClient = null;
    //声明AMapLocationClientOption对象
    public AMapLocationClientOption mLocationOption = null;
    //声明定位回调监听器
    public AMapLocationListener mLocationListener = new AMapLocationListener() {
        @Override
        public void onLocationChanged(AMapLocation aMapLocation) {
            Log.e("locate", aMapLocation.getAddress());
            if (null != aMapLocation) {
                getWeather(aMapLocation.getCity());
                tv_locate.setText(aMapLocation.getCity());
                MyApplication.setaMapLocation(aMapLocation);
            } else {
                tv_city.setText("定位失败");
                tv_city.setVisibility(View.VISIBLE);
                Log.e("定位失败", "aMapLocation is null");
            }

        }
    };
    LocationManager locationManager;
    public  void initGPS(final Activity activity) {

        locationManager = (LocationManager) activity.getSystemService(Context.LOCATION_SERVICE);

        // 判断GPS模块是否开启，如果没有则开启
        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {

            AlertDialog.Builder dialog = new AlertDialog.Builder(activity);
            dialog.setMessage("安卓9.0及以上系统要使用定位功能，必须开启GPS");
            dialog.setPositiveButton("设置", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialogInterface, int position) {
                    Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    activity.startActivityForResult(intent, 1); // 设置完成后返回到原来的界面
                }
            });

            dialog.setNeutralButton("取消", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int position) {
                    Log.d(TAG, "---> 取消");
                    dialogInterface.dismiss();
                    activity.finish();
                }
            });

            AlertDialog alertDialog = dialog.create();

            //点击外面不消失
            alertDialog.setCanceledOnTouchOutside(false);
            alertDialog.setCancelable(false);

            alertDialog.show();

        } else {
            Log.d(TAG, "---> GPS模块已开启");
            //Toast.makeText(activity, "GPS模块已开启", Toast.LENGTH_SHORT).show();
        }
    }



    /**
     * 默认的定位参数
     *
     * @author
     * @since 2.8.0
     */
    private AMapLocationClientOption getDefaultOption() {
        AMapLocationClientOption mOption = new AMapLocationClientOption();
        mOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);//可选，设置定位模式，可选的模式有高精度、仅设备、仅网络。默认为高精度模式
        mOption.setGpsFirst(false);//可选，设置是否gps优先，只在高精度模式下有效。默认关闭
        //mOption.setHttpTimeOut(30000);//可选，设置网络请求超时时间。默认为30秒。在仅设备模式下无效
        //   mOption.setInterval(2000);//可选，设置定位间隔。默认为2秒
        mOption.setNeedAddress(true);//可选，设置是否返回逆地理地址信息。默认是true
        //   mOption.setOnceLocation(false);//可选，设置是否单次定位。默认是false
        mOption.setOnceLocation(true);  //获取一次定位结果：
        mOption.setOnceLocationLatest(false);//可选，设置是否等待wifi刷新，默认为false.如果设置为true,会自动变为单次定位，持续定位时不要使用
        AMapLocationClientOption.setLocationProtocol(AMapLocationClientOption.AMapLocationProtocol.HTTP);//可选， 设置网络请求的协议。可选HTTP或者HTTPS。默认为HTTP
        mOption.setSensorEnable(false);//可选，设置是否使用传感器。默认是false
        mOption.setWifiScan(true); //可选，设置是否开启wifi扫描。默认为true，如果设置为false会同时停止主动刷新，停止以后完全依赖于系统刷新，定位位置可能存在误差
        mOption.setLocationCacheEnable(true); //可选，设置是否使用缓存定位，默认为true
        return mOption;
    }





    @Override
    public void onDestroy() {
        super.onDestroy();
        destroyLocation();
    }
    /**
     * 停止定位
     *
     * @author
     * @since 2.8.0
     */
    private void stopLocation() {
        // 停止定位
        mLocationClient.stopLocation();
    }

    private void destroyLocation() {
        if (null != mLocationClient) {
            mLocationClient.onDestroy();
            mLocationClient = null;
            mLocationOption = null;


        }
    }
    public  void startLocate(){
        mLocationClient.startLocation();
    }
    private void initLocation() {
        //初始化定位
        mLocationClient = new AMapLocationClient(getContext());
//设置定位回调监听
        mLocationOption=getDefaultOption();
        mLocationClient.setLocationOption(mLocationOption);
        mLocationClient.setLocationListener(mLocationListener);
        mLocationClient.startLocation();

    }


    private void initBanner() {
        banner.setImageLoader(new com.youth.banner.loader.ImageLoader() {
            @Override
            public void displayImage(Context context, Object path, ImageView imageView) {
                //此处可以自行选择，我直接用的Picasso
                imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                Glide.with(getContext()).load((String) path).into(imageView);
            }
        });
        banner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
                Log.e("url", mBanners.get(position).getPicture() + "-" + mBanners.get(position).getSkipUrl());
                Intent intent = new Intent(getContext(), WebviewActivity.class);
                String url = mBanners.get(position).getSkipUrl();
                if (!url.contains("http") && !url.contains("https")) {
                    url = "http://" + url;
                }
                intent.putExtra("url", url);
                startActivity(intent);
            }
        });
        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);//设置圆形指示器与标题
        banner.setIndicatorGravity(BannerConfig.CENTER);//设置指示器位置
        banner.setDelayTime(2000);//设置轮播时间
        banner.setImages(images);//设置图片源
        banner.start();
    }

    private void getWeather(String city) {
        Map<String, Object> params = new HashMap<>();
        params.put("city", city);
        HttpProxy.obtain().get(PlatformContans.Commom.getTodayTemperatureByCity, params,"", new ICallBack() {
            @Override
            public void OnSuccess(String result) {
                Log.e("getToday",result);
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    jsonObject = jsonObject.getJSONObject("data");
                    JSONObject today = jsonObject.getJSONObject("today");
                    Weather weather = new Gson().fromJson(today.toString(), Weather.class);
                    tv_city.setText(weather.getCity());
                    tv_go.setText(weather.getTravelIndex());
                    tv_wash.setText(weather.getWashIndex());
                    tv_wendu.setText(weather.getTemp());
                    tv_weather.setText(weather.getWeather()+"/"+weather.getWind()+" "+weather.getTemperature());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(String error) {
                Log.e("getToday",error);
            }
        });
    }

    private void getHomeImage(int superId) {
        Map<String, Object> params = new HashMap<>();
        params.put("superId", superId);
        HttpProxy.obtain().get(PlatformContans.Commom.getSkipUrlResult, params, new ICallBack() {
            @Override
            public void OnSuccess(String result) {

                try {
                    JSONObject jsonObject = new JSONObject(result);
                    jsonObject = jsonObject.getJSONObject("data");
                    JSONArray data = jsonObject.getJSONArray("list");
                    for (int i = 0; i < data.length(); i++) {
                        JSONObject item = data.getJSONObject(i);
                        HomeImage urlBean = new Gson().fromJson(item.toString(), HomeImage.class);
                        mHomeImages.add(urlBean);
                    }
                    Log.e("getSkipUrlResult", result);
                    mHomeListAdapter.notifyDataSetChanged();
                    lv_home.setFocusable(true);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(String error) {

            }
        });
    }


    private void getImageUrl() {
        HttpProxy.obtain().get(PlatformContans.Commom.getSkipUrl, "", new ICallBack() {
            @Override
            public void OnSuccess(String result) {
                Log.e("result", result);
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    int code = jsonObject.getInt("resultCode");
                    if (code == 0) {
                        JSONArray data = jsonObject.getJSONArray("data");
                        for (int i = 0; i < data.length(); i++) {
                            JSONObject item = data.getJSONObject(i);
                            UrlBean urlBean = new Gson().fromJson(item.toString(), UrlBean.class);
                            mUrlBeans.add(urlBean);
                        }

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

    private void getBaner() {
        Map<String, Object> params = new HashMap<>();
        params.put("type", 1);
        HttpProxy.obtain().get(PlatformContans.Commom.getBannerList, params, new ICallBack() {
            @Override
            public void OnSuccess(String result) {
                Log.e("data", result);
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    JSONArray data = jsonObject.getJSONArray("data");
                    for (int i = 0; i < data.length(); i++) {
                        JSONObject item = data.getJSONObject(i);
                        Banner banner = new Gson().fromJson(item.toString(), Banner.class);
                        mBanners.add(banner);
                        String url = item.getString("picture");
                        images.add(url);
                    }
                    initBanner();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(String error) {

            }
        });


    }

    private void init() {
        ctx = getActivity();

        getBaner();

        ll_item1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mHomeImages.clear();
                getHomeImage(3);
            }
        });
        rl_weather.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent3 = new Intent(getContext(), WebviewActivity.class);
                intent3.putExtra("url", "http://www.yunchebao.com:8080/weather/?city="+MyApplication.getaMapLocation().getCity());
                startActivity(intent3);
            }
        });
        ll_item2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mHomeImages.clear();
                getHomeImage(4);
            }
        });
        ll_item3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mHomeImages.clear();
                getHomeImage(5);
            }
        });
        mHomeImages = new ArrayList<>();

        mHomeListAdapter = new HomeListAdapter(getContext(), mHomeImages);
        lv_home.setAdapter(mHomeListAdapter);
        lv_home.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent3 = new Intent(getContext(), WebviewActivity.class);
                intent3.putExtra("url", mHomeImages.get(position).getUrl());
                startActivity(intent3);
            }
        });
        lv_home.setFocusable(false);

        getHomeImage(3);
    }


    @OnClick({R.id.messenger_icon, R.id.menuLay1, R.id.menuLay2, R.id.menuLay3, R.id.menuLay4, R.id.menuLay7, R.id.menuLay5, R.id.menuLay6, R.id.menuLay8, R.id.menuLay9, R.id.menuLay10, R.id.user_center_icon})
    public void OnClick(View v) {
        switch (v.getId()) {
            case R.id.messenger_icon:
                if (MyApplication.isLogin) {
                    startActivity(new Intent(getContext(), ChatActivity.class));
                    //connect(MyApplication.getUserInfo().getHxPassword());
                } else {
                    startActivity(new Intent(getContext(), RegisterActivity.class));
                }
                //ActivityAnimationUtils.commonTransition(getActivity(), MessageMainActivity.class, ActivityConstans.Animation.FADE);
                break;
            case R.id.menuLay1://洗车店
                Bundle bundle=new Bundle();
                bundle.putInt("type",1);
                ActivityAnimationUtils.commonTransition(getActivity(), WashCarListActivity.class, ActivityConstans.Animation.FADE,bundle);
                break;
            case R.id.menuLay2://修理店
                Bundle bundle2=new Bundle();
                bundle2.putInt("type",2);
                ActivityAnimationUtils.commonTransition(getActivity(), WashCarListActivity.class, ActivityConstans.Animation.FADE,bundle2);
                break;
            case R.id.menuLay3://4S店
                ActivityAnimationUtils.commonTransition(getActivity(), Shop4SInfoActivity.class, ActivityConstans.Animation.FADE);
                break;
            case R.id.menuLay4://驾校汇
                startActivity(new Intent(getContext(), NewDrvingActivity.class));
                //ActivityAnimationUtils.commonTransition(getActivity(), Shop4SInfoActivity.class, ActivityConstans.Animation.FADE);
                break;
            case R.id.menuLay5://
                ActivityAnimationUtils.commonTransition(getActivity(), YuedanHomeActivity.class, ActivityConstans.Animation.FADE);

                //ActivityAnimationUtils.commonTransition(getActivity(), Shop4SInfoActivity.class, ActivityConstans.Animation.FADE);
                break;
            case R.id.menuLay6://加油站
                // ActivityAnimationUtils.commonTransition(getActivity(), RoadAssistanceListActivity.class, ActivityConstans.Animation.FADE);
                break;
            case R.id.menuLay7://违章
                ActivityAnimationUtils.commonTransition(getActivity(), RoadAssistanceListActivity.class, ActivityConstans.Animation.FADE);
                //ActivityAnimationUtils.commonTransition(getActivity(), RoadAssistanceListActivity.class, ActivityConstans.Animation.FADE);
                break;
            case R.id.menuLay8://约单
                Intent intent = new Intent(getContext(), WebviewActivity.class);
                intent.putExtra("url", mUrlBeans.get(0).getUrl());
                startActivity(intent);
                break;
            case R.id.menuLay9://地图导航
                showDialog(new LatLng(MyApplication.getaMapLocation().getLatitude(), MyApplication.getaMapLocation().getLongitude()));
                //ActivityAnimationUtils.commonTransition(getActivity(), YuedanHomeActivity.class, ActivityConstans.Animation.FADE);
                break;
            case R.id.menuLay10://滴滴
                Intent intent3 = new Intent(getContext(), WebviewActivity.class);
                intent3.putExtra("url", mUrlBeans.get(1).getUrl());
                startActivity(intent3);
                //showDialog(new LatLng(MyApplication.getaMapLocation().getLatitude(),MyApplication.getaMapLocation().getLongitude()));
                //ActivityAnimationUtils.commonTransition(getActivity(), YuedanHomeActivity.class, ActivityConstans.Animation.FADE);
                break;
//            case R.id.newGoodsLay://新品尝鲜
//                ActivityAnimationUtils.commonTransition(getActivity(), NewGoodsListActivity.class, ActivityConstans.Animation.FADE);
//                break;
//            case R.id.recommendLay://为您推荐
//                ActivityAnimationUtils.commonTransition(getActivity(), BrandGoodsListActivity.class, ActivityConstans.Animation.FADE);
//                break;
//            case R.id.cityLay://城市选择列表
//                ActivityAnimationUtils.commonTransition(getActivity(), CityListActivity.class, ActivityConstans.Animation.FADE);
//                break;
            case R.id.user_center_icon://个人中心
                if (MyApplication.isLogin)
                    ActivityAnimationUtils.commonTransition(getActivity(), UserCenterActivity.class, ActivityConstans.Animation.FADE);
                else {
                    startActivity(new Intent(getContext(), RegisterActivity.class));
                }
                break;
//            case R.id.magList:
//                ActivityAnimationUtils.commonTransition(getActivity(), MagzineCoverActivity.class, ActivityConstans.Animation.FADE);
//                break;
        }
    }
}
