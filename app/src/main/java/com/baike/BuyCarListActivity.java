package com.baike;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.WindowManager;

import com.baike.fragment.BuyCarMenuFragment;
import com.entity.PhoneShopEntity;
import com.example.yunchebao.R;
import com.nohttp.sample.NoHttpFragmentBaseActivity;
import com.tool.ActivityConstans;
import com.tool.FitStateUI;
import com.tool.UIControlUtils;
import com.tool.slideshowview.SlideShowView;
import com.xihubao.adapter.TabFragmentAdapter;
import com.xihubao.fragment.GoodsFragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class BuyCarListActivity extends NoHttpFragmentBaseActivity {
    @BindView(R.id.collapsing)
    CollapsingToolbarLayout collapsingToolbarLayout;
    private AppBarLayout appBarLayout;
    private TabLayout slidingTabLayout;
    //fragment列表
    private List<Fragment> mFragments=new ArrayList<>();
    //tab名的列表
    private List<String> mTitles=new ArrayList<>();
    private ViewPager viewPager;
    private TabFragmentAdapter adapter;

    //轮播图片
    private List<Map<String, String>> imageList = new ArrayList<>();
    @BindView(R.id.slideshowView)
    SlideShowView slideShowView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.maintain_list_layout);
        FitStateUI.setImmersionStateMode(this);
        ButterKnife.bind(this);
        setStatusBar();
        setCollsapsing();
        initView();
        requestMethod(0);
    }

    private void requestMethod(int type) {
        setFrom(new PhoneShopEntity());
//        Request<String> request = null;
//        if (PlatformContans.OBJECT_MAP.get(PlatformContans.LoginContacts.SHOP_ID) != null && !PlatformContans.OBJECT_MAP.get(PlatformContans.LoginContacts.SHOP_ID).equals("")){
//            switch (type){
//                case 0:
//                    request = NoHttp.createStringRequest(PlatformContans.rootUrl+PlatformContans.Urls.ShopUrls.getShop +"/"+ PlatformContans.OBJECT_MAP.get(PlatformContans.LoginContacts.SHOP_ID) , RequestMethod.GET);
//                    request(0,request,httpListener,true,true);
//                    break;
//                case 1:
//                    request = NoHttp.createStringRequest(PlatformContans.rootUrl+PlatformContans.Urls.ShopUrls.getShopShare , RequestMethod.GET);
//                    request.add("shop_id",PlatformContans.OBJECT_MAP.get(PlatformContans.LoginContacts.SHOP_ID).toString());
//                    request(1,request,httpListener,true,true);
//                    break;
//            }
//        }
    }

    private void initView() {
        UIControlUtils.UITextControlsUtils.setUIText(findViewById(R.id.title), ActivityConstans.UITag.TEXT_VIEW,"购车百科");
        //网络地址获取轮播图
        imageList.clear();
        for (int i = 0; i < 3; i++) {
            Map<String, String> image_uri = new HashMap<String, String>();
            image_uri.put("imageUrls", "https://you.autoimg.cn/_autohomecar__zhouyouji/657C2F909017074F9C59CA0B88DA0F0BDDC9.jpg?imageMogr2/format/jpg/thumbnail/790|watermark/2/text/5rG96L2m5LmL5a62/font/5b6u6L2v6ZuF6buR/fontsize/270/fill/I0ZGRkZGRg==");
//            image_uri.put("imageUris", adList.get(i).getCid());
            imageList.add(image_uri);
        }
        slideShowView.setImageUrls(imageList);
        appBarLayout = (AppBarLayout) findViewById(R.id.appbar);
        slidingTabLayout = (TabLayout) findViewById(R.id.slidinglayout);
        viewPager = (ViewPager) findViewById(R.id.vp);
    }

    private void setViewPager() {
        BuyCarMenuFragment goodsFragment1=new BuyCarMenuFragment();

        mFragments.add(goodsFragment1);

        mTitles.add("看车选车");

        adapter=new TabFragmentAdapter(getSupportFragmentManager(),mFragments,mTitles);
        viewPager.setAdapter(adapter);
        slidingTabLayout.setupWithViewPager(viewPager);
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position){
                    case 0:
                        break;
                    case 1:
                        break;
                    case 2:
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }


    private void setCollsapsing() {
        collapsingToolbarLayout.setStatusBarScrimColor(getResources().getColor(R.color.float_transparent));
        collapsingToolbarLayout.setContentScrim(getResources().getDrawable(R.color.float_transparent));
    }

    private void setStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        } else {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }






    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    private void setFrom(PhoneShopEntity rShop) {
        setViewPager();
//        if (rShop.getShop_pic() != null && !rShop.getShop_pic().equals("")){
//            Uri uri = Uri.parse(PlatformContans.rootUrl + rShop.getShop_pic());
//            DraweeController controller = Fresco.newDraweeControllerBuilder().setUri(uri).setAutoPlayAnimations(true).build();
//            bgImg.setController(controller);
//        }
//        UIControlUtils.UITextControlsUtils.setUIText(findViewById(R.id.title), ActivityConstans.UITag.TEXT_VIEW,rShop.getShop_name());
//        UIControlUtils.UITextControlsUtils.setUIText(findViewById(R.id.notice), ActivityConstans.UITag.TEXT_VIEW,rShop.getNotice());
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
