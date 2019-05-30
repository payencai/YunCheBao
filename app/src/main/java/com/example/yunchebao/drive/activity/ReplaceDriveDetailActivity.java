package com.example.yunchebao.drive.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.maps.model.LatLng;
import com.example.yunchebao.MyApplication;
import com.bumptech.glide.Glide;
import com.costans.PlatformContans;
import com.example.yunchebao.drive.adapter.DriveManAdapter;
import com.example.yunchebao.drive.adapter.SubstitubeAdapter;
import com.example.yunchebao.drive.model.DriveMan;
import com.example.yunchebao.drive.model.ReplaceDrive;
import com.example.yunchebao.drive.model.SubstitubeComment;
import com.example.yunchebao.R;
import com.google.gson.Gson;
import com.gyf.immersionbar.ImmersionBar;
import com.http.HttpProxy;
import com.http.ICallBack;
import com.iarcuschin.simpleratingbar.SimpleRatingBar;
import com.system.WebviewActivity;
import com.tool.MathUtil;
import com.vipcenter.RegisterActivity;
import com.xihubao.ShopInfoActivity;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.listener.OnBannerListener;

import org.json.JSONArray;
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

public class ReplaceDriveDetailActivity extends AppCompatActivity {
    @BindView(R.id.banner)
    Banner banner;
    @BindView(R.id.iv_play)
    ImageView iv_play;
    @BindView(R.id.tv_name)
    TextView tv_name;
    @BindView(R.id.tv_grade)
    TextView tv_grade;
    @BindView(R.id.sr_score)
    SimpleRatingBar sr_score;
    @BindView(R.id.tv_address)
    TextView tv_address;
    @BindView(R.id.tv_score)
    TextView tv_score;
    @BindView(R.id.collectIcon)
    ImageView iv_collect;
    @BindView(R.id.tv_dis)
    TextView tv_dis;
    @BindView(R.id.lv_drive)
    ListView lv_drive;
    @BindView(R.id.lv_comment)
    ListView lv_comment;
    List<DriveMan> mDriveMEN;
    List<SubstitubeComment> mSubstitubeComments;
    DriveManAdapter mDriveManAdapter;
    SubstitubeAdapter mSubstitubeAdapter;
    ReplaceDrive mReplaceDrive;
    String id;
    int page = 1;
    String video1="http://9890.vod.myqcloud.com/9890_4e292f9a3dd011e6b4078980237cc3d3.f20.mp4";
    String video2="http://www.w3school.com.cn/example/html5/mov_bbb.mp4";
    String video3="https://media.w3.org/2010/05/sintel/trailer.mp4";
     int videoCount=0;
    String img1="http://gss0.baidu.com/-vo3dSag_xI4khGko9WTAnF6hhy/zhidao/pic/item/fc1f4134970a304ec0ff5ed5d7c8a786c8175cc4.jpg";
    String img2="https://photo.16pic.com/00/51/84/16pic_5184604_b.jpg";
    String img3="http://up.bizhitupian.com/pic/9a/92/79/9a9279dd6336a045145ec611ed26418b.jpg";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_replace_drive_detail);
        ButterKnife.bind(this);
        ImmersionBar.with(this).autoDarkModeEnable(true).fitsSystemWindows(true).statusBarColor(R.color.white).init();
        id = getIntent().getStringExtra("id");
        initView();
    }
    List<String> images = new ArrayList<>();
    List<String> vimages = new ArrayList<>();
    List<String> videos = new ArrayList<>();
    List<String> banners = new ArrayList<>();
    @OnClick({R.id.ll_head, R.id.collectBtn, R.id.ll_league,R.id.menuBtn,R.id.more2,R.id.callBtn})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.callBtn:
                callPhone(mReplaceDrive.getSaleTelephone());
                break;
            case R.id.more2:
                Intent intent3 = new Intent(ReplaceDriveDetailActivity.this, CommentMoreActivity.class);
                intent3.putExtra("id", mReplaceDrive.getId());
                startActivity(intent3);
                break;
            case R.id.menuBtn:
                if (!MyApplication.isLogin) {
                    startActivity(new Intent(ReplaceDriveDetailActivity.this, RegisterActivity.class));
                    return;
                }
                Intent intent2 = new Intent(ReplaceDriveDetailActivity.this, ReplaceOrderActivity.class);
                intent2.putExtra("id", mReplaceDrive.getId());
                startActivity(intent2);
                break;
            case R.id.ll_league:
                showMapDialog(new LatLng(MyApplication.getaMapLocation().getLatitude(), MyApplication.getaMapLocation().getLongitude()));
                break;
            case R.id.ll_head:
                Intent intent = new Intent(ReplaceDriveDetailActivity.this, ShopInfoActivity.class);
                intent.putExtra("id", mReplaceDrive.getId());
                startActivity(intent);
                break;
            case R.id.collectBtn:
                if (!MyApplication.isLogin) {
                    startActivity(new Intent(ReplaceDriveDetailActivity.this, RegisterActivity.class));
                    return;
                }
                if (isCollect == 0) {
                    isCollect = 1;
                    iv_collect.setImageResource(R.mipmap.collect_yellow);
                    collect();
                } else if (isCollect == 1) {
                    isCollect = 0;
                    iv_collect.setImageResource(R.mipmap.collect_gray_hole);
                    delCollect();
                }
                break;
        }
    }

    /**
     * 拨打电话（跳转到拨号界面，用户手动点击拨打）
     *
     * @param phoneNum 电话号码
     */
    public void callPhone(String phoneNum) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        Uri data = Uri.parse("tel:" + phoneNum);
        intent.setData(data);
        startActivity(intent);
    }

    private void initView() {
        mDriveMEN = new ArrayList<>();
        mSubstitubeComments = new ArrayList<>();
        mDriveManAdapter = new DriveManAdapter(this, mDriveMEN);
        mSubstitubeAdapter = new SubstitubeAdapter(this, mSubstitubeComments);
        lv_comment.setAdapter(mSubstitubeAdapter);
        lv_drive.setAdapter(mDriveManAdapter);
        lv_drive.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(ReplaceDriveDetailActivity.this,DriverDetailActivity.class);
                intent.putExtra("id",mDriveMEN.get(position).getId());
                startActivity(intent);
            }
        });
        mDriveManAdapter.setOnSelectListener(new DriveManAdapter.OnSelectListener() {
            @Override
            public void onSelect(int position) {
                DriveMan driveMan = mDriveMEN.get(position);
                Intent intent = new Intent(ReplaceDriveDetailActivity.this, AddSubstitubeActivity.class);
                intent.putExtra("data", driveMan);
                intent.putExtra("id",mReplaceDrive.getId());
                startActivity(intent);
            }
        });

        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        getDetail();

    }

    private void getComment() {
        Map<String, Object> params = new HashMap<>();
        params.put("shopId", id);
        params.put("page", page);
        HttpProxy.obtain().get(PlatformContans.SubstituteDriving.getSubstituteDrivingCommentListByShopId, params, new ICallBack() {
            @Override
            public void OnSuccess(String result) {
                Log.e("detail", result);
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    JSONArray data = jsonObject.getJSONArray("data");
                    for (int i = 0; i < data.length(); i++) {
                        JSONObject item = data.getJSONObject(i);
                        SubstitubeComment driveMan = new Gson().fromJson(item.toString(), SubstitubeComment.class);
                        mSubstitubeComments.add(driveMan);
                    }
                    mSubstitubeAdapter.notifyDataSetChanged();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(String error) {

            }
        });
    }

    private void getDriveMan() {
        Map<String, Object> params = new HashMap<>();
        params.put("shopId", id);
        params.put("page", page);
        HttpProxy.obtain().get(PlatformContans.SubstituteDriving.getSubstituteDriverListForApp, params, new ICallBack() {
            @Override
            public void OnSuccess(String result) {
                Log.e("detail", result);
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    JSONArray data = jsonObject.getJSONArray("data");
                    for (int i = 0; i < data.length(); i++) {
                        JSONObject item = data.getJSONObject(i);
                        DriveMan driveMan = new Gson().fromJson(item.toString(), DriveMan.class);
                        mDriveMEN.add(driveMan);
                    }
                    mDriveManAdapter.notifyDataSetChanged();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(String error) {

            }
        });
    }

    private void initData() {

        tv_name.setText(mReplaceDrive.getShopName());
        tv_address.setText(mReplaceDrive.getProvince() + mReplaceDrive.getCity() + mReplaceDrive.getArea() + mReplaceDrive.getAddress());
        tv_grade.setText(mReplaceDrive.getGrade() + "");
        tv_score.setText((int)mReplaceDrive.getScore() + "");
        sr_score.setRating(mReplaceDrive.getScore());
        tv_dis.setText(MathUtil.getDoubleTwo(mReplaceDrive.getDistance()) + "km");
        String banner = mReplaceDrive.getBanner();
        String vimgs=mReplaceDrive.getVimgs();
        String video=mReplaceDrive.getVideos();
        if (!TextUtils.isEmpty(vimgs)) {
            vimages = Arrays.asList(vimgs.split(","));
            videoCount=vimages.size();
        }
        if (!TextUtils.isEmpty(video)) {
            videos = Arrays.asList(video.split(","));
        }
        if (!TextUtils.isEmpty(banner)) {
            banners = Arrays.asList(banner.split(","));
        }
        images.addAll(vimages);
        images.addAll(banners);
        initBanner(images);
        isCollect();
        getDriveMan();
        getComment();
    }

    int isCollect = -1;

    public void isCollect() {
        Map<String, Object> params = new HashMap<>();
        params.put("shopId", mReplaceDrive.getId());
        HttpProxy.obtain().get(PlatformContans.SubstituteDriving.isCollectionByShopId, params, MyApplication.token, new ICallBack() {
            @Override
            public void OnSuccess(String result) {
                Log.e("result", result);
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    int data = jsonObject.getInt("data");
                    if (data == 0) {
                        isCollect = 0;
                        iv_collect.setImageResource(R.mipmap.collect_gray_hole);
                    } else if (data == 1) {
                        isCollect = 1;
                        iv_collect.setImageResource(R.mipmap.collect_yellow);
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

    public void delCollect() {
        Map<String, Object> params = new HashMap<>();
        params.put("shopId", mReplaceDrive.getId());
        HttpProxy.obtain().post(PlatformContans.SubstituteDriving.deleteShopCollection, MyApplication.token, params, new ICallBack() {
            @Override
            public void OnSuccess(String result) {
                Log.e("result", result);
                try {
                    JSONObject jsonObject = new JSONObject(result);


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(String error) {

            }
        });
    }

    public void collect() {
        Map<String, Object> params = new HashMap<>();
        params.put("shopId", mReplaceDrive.getId());
        HttpProxy.obtain().post(PlatformContans.SubstituteDriving.addShopCollection, MyApplication.token, params, new ICallBack() {
            @Override
            public void OnSuccess(String result) {
                Log.e("result", result);
                try {
                    JSONObject jsonObject = new JSONObject(result);


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(String error) {

            }
        });
    }

    private void getDetail() {
        Map<String, Object> params = new HashMap<>();
        params.put("id", id);
        params.put("longitude", MyApplication.getaMapLocation().getLongitude());
        params.put("latitude", MyApplication.getaMapLocation().getLatitude());
        HttpProxy.obtain().get(PlatformContans.SubstituteDriving.getSubstituteDrivingShopResultById, params, new ICallBack() {
            @Override
            public void OnSuccess(String result) {
                Log.e("detail", result);
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    JSONObject data = jsonObject.getJSONObject("data");
                    mReplaceDrive = new Gson().fromJson(data.toString(), ReplaceDrive.class);
//                    String imgs=img1+","+img2+","+img3;
//                    String videos=video1+","+video2+","+video3;
//                    mReplaceDrive.setVideos(videos);
//                    mReplaceDrive.setVimgs(imgs);
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

    private void initBanner(List<String> images) {
        banner.setImageLoader(new com.youth.banner.loader.ImageLoader() {
            @Override
            public void displayImage(Context context, Object path, ImageView imageView) {
                //此处可以自行选择，我直接用的Picasso
                imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                Glide.with(ReplaceDriveDetailActivity.this).load((String) path).into(imageView);
            }
        });
        banner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
                if(videoCount>position){
                    Intent intent = new Intent(ReplaceDriveDetailActivity.this, SimplePlayerActivity.class);
                    intent.putExtra("img", images.get(position));
                    intent.putExtra("video", videos.get(position));
                    startActivity(intent);
                }else{
                    //Log.e("url", mBanners.get(position).getPicture() + "-" + mBanners.get(position).getSkipUrl());
                    Intent intent = new Intent(ReplaceDriveDetailActivity.this, WebviewActivity.class);
                    String url = "";
                    if (!url.contains("http") && !url.contains("https")) {
                        url = "http://" + url;
                    }
                    intent.putExtra("url", url);
                    startActivity(intent);
                }


            }
        });
        banner.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (videoCount>position) {
                    iv_play.setVisibility(View.VISIBLE);
                } else {
                    iv_play.setVisibility(View.GONE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);//设置圆形指示器与标题
        banner.setIndicatorGravity(BannerConfig.CENTER);//设置指示器位置
        banner.setDelayTime(3000);//设置轮播时间
        banner.setImages(images);//设置图片源
        banner.start();
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
}
