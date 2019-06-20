package com.example.yunchebao.maket;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.maps.model.LatLng;
import com.coorchice.library.SuperTextView;
import com.example.yunchebao.MyApplication;
import com.bumptech.glide.Glide;
import com.costans.PlatformContans;
import com.example.yunchebao.R;
import com.flyco.tablayout.SlidingTabLayout;
import com.google.gson.Gson;
import com.http.HttpProxy;
import com.http.ICallBack;
import com.example.yunchebao.maket.fragment.GoodsTypeFragment;
import com.example.yunchebao.maket.model.GoodsShop;
import com.nohttp.sample.NoHttpFragmentBaseActivity;
import com.payencai.library.util.ToastUtil;
import com.tool.ActivityConstans;
import com.tool.UIControlUtils;
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
import io.rong.imkit.RongIM;

/**
 * Created by sdhcjhss on 2018/1/4.
 * 店铺主页
 */

public class ShopMainListActivity extends NoHttpFragmentBaseActivity {
    @BindView(R.id.viewpager)
    ViewPager viewPager;
    @BindView(R.id.tab_layout)
    SlidingTabLayout tab_layout;
    @BindView(R.id.shopIcon)
    ImageView shopIcon;
    @BindView(R.id.tv_add)
    SuperTextView tv_add;
    @BindView(R.id.shopName)
    TextView shopName;
    @BindView(R.id.tv_grade)
    TextView tv_grade;
    @BindView(R.id.iv_heart)
    ImageView iv_heart;
    @BindView(R.id.tv_address)
    TextView tv_address;
    @BindView(R.id.tv_dis)
    TextView tv_dis;
    String[] mTitles = {"全部", "热销", "新品"};
    ArrayList<Fragment> mFragments = new ArrayList<>();
    public static String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shop_home_indicator_vp_list);
        ButterKnife.bind(this);
        id = getIntent().getExtras().getString("id");
        initView();
    }

    private void initView() {
        UIControlUtils.UITextControlsUtils.setUIText(findViewById(R.id.title), ActivityConstans.UITag.TEXT_VIEW, "店铺");
        findViewById(R.id.topPanel).setVisibility(View.VISIBLE);
        findViewById(R.id.shopCartBtn).setVisibility(View.GONE);
        for (int i = 0; i < 3; i++) {
            //int j=i++;
            mFragments.add(GoodsTypeFragment.newInstance(i));
        }
        getShop();
        isCollect();
        tab_layout.setViewPager(viewPager, mTitles, this, mFragments);

    }
    private void showApplyDialog() {
        final Dialog dialog = new Dialog(this);
        View view = LayoutInflater.from(this).inflate(R.layout.dialog, null);
        final EditText et_season = (EditText) view.findViewById(R.id.et_season);
        TextView tv_cancel = (TextView) view.findViewById(R.id.tv_cancel);
        TextView tv_name = (TextView) view.findViewById(R.id.tv_name);
        TextView tv_submit = (TextView) view.findViewById(R.id.tv_submit);
        tv_name.setText("好友申请");
        tv_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String reson = et_season.getEditableText().toString();
                apply(reson, dialog);

            }
        });
        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.setContentView(view);
        dialog.show();
        Window window = dialog.getWindow();
        WindowManager.LayoutParams layoutParams = window.getAttributes();
        layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        window.setAttributes(layoutParams);

    }

    private void apply(String reason, final Dialog dialog) {
        Map<String, Object> params = new HashMap<>();
        params.put("shopId", mGoodsShop.getId());

        HttpProxy.obtain().post(PlatformContans.Chat.addFriendByShopId, MyApplication.token, params, new ICallBack() {
            @Override
            public void OnSuccess(String result) {
                Log.e("friend", result);
                dialog.dismiss();
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    int code = jsonObject.getInt("resultCode");
                    String msg = jsonObject.getString("message");
                    if (code == 0) {
                        dialog.dismiss();
                        Toast.makeText(ShopMainListActivity.this, "已添加对方为好友！", Toast.LENGTH_SHORT).show();
                    } else {
                        ToastUtil.showToast(ShopMainListActivity.this, msg);
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
    GoodsShop mGoodsShop;

    private void getShop() {
        Map<String, Object> params = new HashMap<>();
        params.put("shopId", id);
        HttpProxy.obtain().get(PlatformContans.GoodInfo.getBabyMerchantShopResultById, params, new ICallBack() {
            @Override
            public void OnSuccess(String result) {
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    JSONObject data = jsonObject.getJSONObject("data");
                    mGoodsShop = new Gson().fromJson(data.toString(), GoodsShop.class);
                    shopName.setText(mGoodsShop.getShopName());
                    tv_grade.setText("" + mGoodsShop.getGrade());
                    tv_address.setText(mGoodsShop.getProvince()+mGoodsShop.getCity()+mGoodsShop.getArea()+mGoodsShop.getAddress());
                    tv_dis.setText(mGoodsShop.getDistance()+"km");
                    Glide.with(ShopMainListActivity.this).load(mGoodsShop.getLogo()).into(shopIcon);
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

    int collect = 0;

    private void isCollect() {
        Map<String, Object> params = new HashMap<>();
        params.put("userId", MyApplication.getUserInfo().getId());
        params.put("shopId", id);
        HttpProxy.obtain().get(PlatformContans.GoodCollect.getBabyMerchantCollection, params, new ICallBack() {
            @Override
            public void OnSuccess(String result) {
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    collect = jsonObject.getInt("data");
                    if (collect == 1) {
                        iv_heart.setImageResource(R.mipmap.orange_heart_icon);
                    }
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

    private void addCollect() {
        Map<String, Object> params = new HashMap<>();
        params.put("shopId", id);
        HttpProxy.obtain().post(PlatformContans.GoodCollect.addBabyMerchantCollection, MyApplication.token, params, new ICallBack() {
            @Override
            public void OnSuccess(String result) {
                collect = 1;
                iv_heart.setImageResource(R.mipmap.orange_heart_icon);
            }

            @Override
            public void onFailure(String error) {

            }
        });
    }

    private void delCollect() {
        Map<String, Object> params = new HashMap<>();
        params.put("shopId", id);
        HttpProxy.obtain().post(PlatformContans.GoodCollect.deleteCommodityCollection, MyApplication.token, params, new ICallBack() {
            @Override
            public void OnSuccess(String result) {


                collect = 0;
                iv_heart.setImageResource(R.mipmap.collect_66);
                // tv_phone.setText(mRentCar.getServiceTelephone());

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
    @OnClick({R.id.back, R.id.customerServiceBtn, R.id.collectBtn, R.id.ll_head,R.id.ll_map,R.id.tv_add})
    public void OnClick(View v) {
        switch (v.getId()) {
            case R.id.tv_add:
                showApplyDialog();
                break;
            case R.id.ll_map:
                showMapDialog(new LatLng(Double.parseDouble(mGoodsShop.getLatitude()),Double.parseDouble(mGoodsShop.getLongitude())));
                break;
            case R.id.ll_head:
                Intent intent = new Intent(ShopMainListActivity.this, ShopInfoActivity.class);
                intent.putExtra("id", mGoodsShop.getId());
                startActivity(intent);
                break;
            case R.id.back:
                onBackPressed();
                break;
            case R.id.collectBtn:
                if (collect == 1) {
                    delCollect();
                } else {
                    addCollect();
                }
                break;
            case R.id.customerServiceBtn:
                RongIM.getInstance().startPrivateChat(ShopMainListActivity.this, mGoodsShop.getId(), mGoodsShop.getShopName());
                //ActivityAnimationUtils.commonTransition(ShopMainListActivity.this, OrderChatDetailActivity.class, ActivityConstans.Animation.FADE);
                break;
        }
    }
}
