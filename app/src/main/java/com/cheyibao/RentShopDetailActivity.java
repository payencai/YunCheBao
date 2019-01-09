package com.cheyibao;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.cheyibao.adapter.RentShopListAdapter;
import com.cheyibao.fragment.RentComentFragment;
import com.cheyibao.fragment.RentShopFragment;
import com.costans.PlatformContans;
import com.entity.PhoneGoodEntity;
import com.example.yunchebao.R;
import com.http.HttpProxy;
import com.http.ICallBack;
import com.nohttp.sample.NoHttpBaseActivity;
import com.tool.ActivityConstans;
import com.tool.UIControlUtils;
import com.tool.adapter.MyFragmentPagerAdapter;
import com.tool.listview.PersonalScrollView;
import com.tool.listview.PersonalViewPager;
import com.tool.view.GridViewForScrollView;
import com.youth.banner.BannerConfig;
import com.youth.banner.listener.OnBannerListener;

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

/**
 * Created by sdhcjhss on 2017/12/24.
 */

public class RentShopDetailActivity extends AppCompatActivity {
    private Context ctx;

    @BindView(R.id.tab_shop)
    TabLayout tab_shop;

    @BindView(R.id.vp_shop)
    PersonalViewPager vp_shop;
    @BindView(R.id.scollview)
    PersonalScrollView mScrollView;

    MyFragmentPagerAdapter mMyFragmentPagerAdapter;
    private List<String> mTabTitles=new ArrayList<>();
    private List<Fragment> mFragments=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rent_shop_detail);
        ButterKnife.bind(this);
        initView();

    }



    private void initView() {

        mTabTitles.add("租借车型");
        mTabTitles.add("查看评论");
        mFragments.add(new RentShopFragment());
        mFragments.add(new RentComentFragment());
        mMyFragmentPagerAdapter=new MyFragmentPagerAdapter(getSupportFragmentManager(),mFragments,mTabTitles);
        vp_shop.setAdapter(mMyFragmentPagerAdapter);
        vp_shop.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == 0) {
                    vp_shop.resetHeight(0);
                } else if (position == 1) {
                    vp_shop.resetHeight(1);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        tab_shop.setupWithViewPager(vp_shop);
    }

    @OnClick({R.id.back})
    public void Onclick(View v){
        switch (v.getId()){
            case R.id.back:
                onBackPressed();
                break;
        }
    }
    List<String> images=new ArrayList<>();

    private void getPhoto(){
        Map<String, Object> params = new HashMap<>();
        params.put("merchantId","");
        HttpProxy.obtain().get(PlatformContans.CarRent.getRentCarPhoto, params, new ICallBack() {
            @Override
            public void OnSuccess(String result) {
                Log.e("getBannerList", result);
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    JSONArray data = jsonObject.getJSONArray("data");
                    for (int i = 0; i < data.length(); i++) {
                        String url = data.getString(i);
                        images.add(url);
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
}
