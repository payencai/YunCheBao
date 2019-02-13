package com.xihubao;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.LinearInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.application.MyApplication;
import com.costans.PlatformContans;
import com.entity.Banner;
import com.entity.GoodsListBean;
import com.entity.PhoneShopEntity;
import com.example.yunchebao.R;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.SlidingTabLayout;
import com.google.gson.Gson;
import com.http.HttpProxy;
import com.http.ICallBack;
import com.nohttp.rest.Request;
import com.nohttp.sample.NoHttpFragmentBaseActivity;
import com.rongcloud.model.CarShop;
import com.tool.ActivityAnimationUtils;
import com.tool.ActivityConstans;
import com.tool.FitStateUI;
import com.tool.UIControlUtils;
import com.tool.slideshowview.SlideShowView;
import com.vipcenter.RegisterActivity;
import com.xihubao.adapter.TabFragmentAdapter;
import com.xihubao.fragment.EvaluateFragment;
import com.xihubao.fragment.GoodsFragment;

import org.json.JSONException;
import org.json.JSONObject;

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
    String type;

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
    @BindView(R.id.collectIcon)
    RelativeLayout collectIcon;
    @BindView(R.id.iv_heart)
    ImageView iv_heart;
    int isCollect;
    String [] titles={"服务","评价"
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.washcar_shop_detail);
        ButterKnife.bind(this);
        mCarShop= (CarShop) getIntent().getExtras().getSerializable("id");
        type=mCarShop.getBanner();
        initView();
        initViewPager();
    }





    private void initView() {

        slidingTabLayout = (SlidingTabLayout) findViewById(R.id.slidinglayout);
        viewPager = (ViewPager) findViewById(R.id.vp);
        List<String> result = Arrays.asList(mCarShop.getBanner().split(","));
        for (int i = 0; i < result.size(); i++) {
            String url = result.get(i);
            Map<String, String> image_uri = new HashMap<String, String>();
            image_uri.put("imageUrls", url);
            imageList.add(image_uri);
        }
        bgImg.setImageUrls(imageList);
        //bgImg.setImageURI(Uri.parse(mCarShop.getBanner()));
        shopname.setText(mCarShop.getShopName());
        score.setText(mCarShop.getGrade()+"");
        dis.setText(mCarShop.getDistance()+"km");
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
    }
    private void collect(){
        String token="";
        if(MyApplication.isLogin){
            token=MyApplication.getUserInfo().getToken();
        }
        Map<String,Object> params=new HashMap<>();
        params.put("shopId",mCarShop.getId());
        params.put("type",1);
        HttpProxy.obtain().post(PlatformContans.Collect.addCarCollection, token,params , new ICallBack() {
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
            token=MyApplication.getUserInfo().getToken();
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
            token=MyApplication.getUserInfo().getToken();
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
    @OnClick({R.id.back})
    public void OnClick(View v){
        switch (v.getId()){
            case R.id.back:
                onBackPressed();
                break;
        }
    }
}
