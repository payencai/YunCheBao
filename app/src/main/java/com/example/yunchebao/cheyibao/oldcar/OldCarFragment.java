package com.example.yunchebao.cheyibao.oldcar;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.cheyibao.OldCarSelectActivity;
import com.cheyibao.adapter.NewCarMenuAdapter;
import com.cheyibao.model.OldCar;
import com.costans.PlatformContans;
import com.entity.Banner;
import com.example.yunchebao.MyApplication;
import com.example.yunchebao.R;
import com.google.gson.Gson;
import com.http.HttpProxy;
import com.http.ICallBack;
import com.nohttp.sample.BaseFragment;
import com.cheyibao.adapter.CarRecommendListAdapter;
import com.system.WebviewActivity;
import com.tool.ActivityAnimationUtils;
import com.tool.ActivityConstans;
import com.tool.listview.PersonalScrollView;
import com.tool.view.GridViewForScrollView;
import com.tool.view.ListViewForScrollView;
import com.vipcenter.RegisterActivity;
import com.xihubao.model.CarBrand;
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
 * Created by sdhcjhss on 2017/12/9.
 */

public class OldCarFragment extends BaseFragment {

    private List<OldCar> list;
    private CarRecommendListAdapter adapter;
    private Context ctx;

    @BindView(R.id.listview)
    ListViewForScrollView listView;
    int page=1;
    boolean isLoadMore;
    //轮播图片
    private List<String> imageList = new ArrayList<>();
    @BindView(R.id.banner)
    com.youth.banner.Banner banner;
    @BindView(R.id.scollview)
    PersonalScrollView scollview;
    @BindView(R.id.gv_newcar)
    GridViewForScrollView gv_newcar;
    NewCarMenuAdapter mNewCarMenuAdapter;
    List<Banner> bannerList;
    List<CarBrand> mNewCarMenus=new ArrayList<>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_oldcar, container, false);
        ButterKnife.bind(this, rootView);
        getBaner();
        initView();
        mNewCarMenus.clear();
       // getMenu();
//        requestMethod(0);
        return rootView;
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
                Log.e("url", bannerList.get(position).getPicture() + "-" + bannerList.get(position).getSkipUrl());
                Intent intent = new Intent(getContext(), WebviewActivity.class);
                String url = bannerList.get(position).getSkipUrl();
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
        banner.setImages(imageList);//设置图片源
        banner.start();
    }
    private void getBrand(int level) {
        Map<String, Object> params = new HashMap<>();
        params.put("level", level);
        HttpProxy.obtain().get(PlatformContans.CarCategory.getFirstCategory, params, "", new ICallBack() {
            @Override
            public void OnSuccess(String result) {

                try {
                    JSONObject jsonObject = new JSONObject(result);
                    JSONArray data = jsonObject.getJSONArray("data");
                    for (int i = 0; i < data.length(); i++) {
                        JSONObject item = data.getJSONObject(i);
                        CarBrand baikeItem = new Gson().fromJson(item.toString(), CarBrand.class);
                        mNewCarMenus.add(baikeItem);
                    }
                    mNewCarMenuAdapter.notifyDataSetChanged();
                    updateGridView(5);
                    Log.e("getdata", result);
                    //adapter.notifyDataSetChanged();
                    //updateData();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(String error) {

            }
        });
    }
    private void updateGridView(int NUM){
        int count = mNewCarMenuAdapter.getCount();
        int columns = (count % 2 == 0) ? count / 2 : count / 2 + 1;
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(columns * getResources().getDisplayMetrics().widthPixels / NUM,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        gv_newcar.setLayoutParams(params);
        gv_newcar.setColumnWidth(getResources().getDisplayMetrics().widthPixels / NUM);
        gv_newcar.setStretchMode(GridView.NO_STRETCH);
        if (count <= 5) {
            gv_newcar.setNumColumns(count);
        } else {
            gv_newcar.setNumColumns(columns);
        }

    }

    private void getBaner(){
        imageList.clear();
        Map<String,Object> params=new HashMap<>();
        params.put("type",3);
        HttpProxy.obtain().get(PlatformContans.Commom.getBannerList, params, new ICallBack() {
            @Override
            public void OnSuccess(String result) {
                Log.e("data",result);
                try {
                    JSONObject jsonObject=new JSONObject(result);
                    JSONArray data=jsonObject.getJSONArray("data");
                    for (int i = 0; i <data.length() ; i++) {
                        JSONObject item=data.getJSONObject(i);
                        Banner banner=new Gson().fromJson(item.toString(),Banner.class);
                        bannerList.add(banner);
                        String url=item.getString("picture");
                        Map<String, String> image_uri = new HashMap<String, String>();
                        image_uri.put("imageUrls",url);
                        imageList.add(banner.getPicture());
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
    private void initView() {

        bannerList=new ArrayList<>();
        list = new ArrayList<>();
        ctx = getActivity();
        adapter = new CarRecommendListAdapter(ctx, list);
        mNewCarMenuAdapter=new NewCarMenuAdapter(getContext(),mNewCarMenus);
        gv_newcar.setAdapter(mNewCarMenuAdapter);
        listView.setAdapter(adapter);
        listView.setFocusable(false);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bundle bundle=new Bundle();
                bundle.putSerializable("data",list.get(position));
                ActivityAnimationUtils.commonTransition(getActivity(), OldCarDetailActivity.class, ActivityConstans.Animation.FADE,bundle);
            }
        });
        scollview.setOnScrollBottomListener(new PersonalScrollView.OnScrollBottomListener() {
            @Override
            public void onScrollBottom() {
                page++;
                getData();
               // ToastUtil.showToast(getContext(),"到达底部");
            }
        });
        gv_newcar.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(getContext(),OldCarListActivity.class);
                Bundle bundle=new Bundle();
                bundle.putSerializable("menu",mNewCarMenus.get(position));
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        getData();
        getBrand(1);
    }
    private void getData(){
        Map<String,Object> params=new HashMap<>();
        params.put("page",page);
        params.put("orderByClause",3);
        HttpProxy.obtain().get(PlatformContans.OldCar.getOldCarMerchantCarByApp, params,"", new ICallBack() {
            @Override
            public void OnSuccess(String result) {
                Log.e("getdata", result);
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    jsonObject=jsonObject.getJSONObject("data");
                    JSONArray data = jsonObject.getJSONArray("beanList");
                    for (int i = 0; i < data.length(); i++) {
                        JSONObject item = data.getJSONObject(i);
                        OldCar baikeItem = new Gson().fromJson(item.toString(), OldCar.class);
                        list.add(baikeItem);
                    }
                    adapter.notifyDataSetChanged();
                    //updateData();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(String error) {

            }
        });
    }

    @OnClick({R.id.sellCarBtn, R.id.selectBtn, R.id.toAllCars, R.id.middleMenu1, R.id.middleMenu2, R.id.middleMenu3, R.id.middleMenu4, R.id.middleMenu5, R.id.middleMenu6, R.id.middleMenu7, R.id.middleMenu8})
    public void OnClick(View v) {
        Bundle bundle=new Bundle();
        switch (v.getId()) {

            case R.id.sellCarBtn:
                if(MyApplication.isIsLogin()){
                    ActivityAnimationUtils.commonTransition(getActivity(), SellCarActivity.class, ActivityConstans.Animation.FADE);
                }else{
                    startActivity(new Intent(getContext(), RegisterActivity.class));
                }

                break;
            case R.id.selectBtn:
                ActivityAnimationUtils.commonTransition(getActivity(), OldCarSelectActivity.class, ActivityConstans.Animation.FADE);
                break;
            case R.id.middleMenu1:
                bundle.putInt("price",-1);
                ActivityAnimationUtils.commonTransition(getActivity(), OldCarListActivity.class, ActivityConstans.Animation.FADE,bundle);
                break;
            case R.id.middleMenu2:
                bundle.putInt("price",5);
                ActivityAnimationUtils.commonTransition(getActivity(), OldCarListActivity.class, ActivityConstans.Animation.FADE,bundle);
                break;
            case R.id.middleMenu3:
                bundle.putInt("price",10);
                ActivityAnimationUtils.commonTransition(getActivity(), OldCarListActivity.class, ActivityConstans.Animation.FADE,bundle);
                break;
            case R.id.middleMenu4:
                bundle.putInt("price",15);
                ActivityAnimationUtils.commonTransition(getActivity(), OldCarListActivity.class, ActivityConstans.Animation.FADE,bundle);
                break;
            case R.id.middleMenu5:
                bundle.putInt("price",30);
                ActivityAnimationUtils.commonTransition(getActivity(), OldCarListActivity.class, ActivityConstans.Animation.FADE,bundle);
                break;
            case R.id.middleMenu6:
                bundle.putInt("price",1);
                ActivityAnimationUtils.commonTransition(getActivity(), OldCarListActivity.class, ActivityConstans.Animation.FADE,bundle);
                break;
            case R.id.middleMenu7:
                bundle.putInt("price",2);
                ActivityAnimationUtils.commonTransition(getActivity(), OldCarListActivity.class, ActivityConstans.Animation.FADE,bundle);
                break;
            case R.id.middleMenu8:
                bundle.putInt("price",3);
                ActivityAnimationUtils.commonTransition(getActivity(), OldCarListActivity.class, ActivityConstans.Animation.FADE,bundle);
                break;
            case R.id.toAllCars:
                bundle.putInt("price",4);
                ActivityAnimationUtils.commonTransition(getActivity(), OldCarListActivity.class, ActivityConstans.Animation.FADE,bundle);
                break;
        }
    }

}
