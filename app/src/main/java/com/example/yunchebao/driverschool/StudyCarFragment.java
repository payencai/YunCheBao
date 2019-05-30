package com.example.yunchebao.driverschool;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.yunchebao.MyApplication;
import com.bumptech.glide.Glide;
import com.cheyibao.adapter.StudyItemAdapter;
import com.cheyibao.model.DrvingSchool;
import com.costans.PlatformContans;
import com.entity.Banner;
import com.example.yunchebao.R;
import com.google.gson.Gson;
import com.http.HttpProxy;
import com.http.ICallBack;
import com.nohttp.sample.BaseFragment;
import com.system.WebviewActivity;
import com.tool.listview.PersonalListView;
import com.tool.listview.PersonalScrollView;
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

/**
 * Created by sdhcjhss on 2017/12/9.
 */

public class StudyCarFragment extends BaseFragment {
    @BindView(R.id.lv_study)
    PersonalListView lv_study;
    @BindView(R.id.tv_dis)
    TextView tv_dis;

    @BindView(R.id.tv_score)
    TextView tv_score;
    @BindView(R.id.slideshowView)
    com.youth.banner.Banner banner;
    @BindView(R.id.scollview)
    PersonalScrollView mPersonalScrollView;
    StudyItemAdapter mStudyItemAdapter;
    List<DrvingSchool> mDrvingSchools = new ArrayList<>();
    int type = 1;
    int page=1;
    //轮播图片
    private List<Map<String, String>> imageList = new ArrayList<>();
    List<Banner> mBanners = new ArrayList<>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.indicator_vp_list, container, false);
        ButterKnife.bind(this, rootView);
        initView();
        getBaner();
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
    List<String> images = new ArrayList<>();
    private void getBaner() {
        imageList.clear();
        Map<String, Object> params = new HashMap<>();
        params.put("type", 5);
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

    private void initView() {
        tv_dis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                type = 1;
                tv_dis.setTextColor(getResources().getColor(R.color.black_33));
                tv_score.setTextColor(getResources().getColor(R.color.gray_99));
                mDrvingSchools.clear();
                mStudyItemAdapter.notifyDataSetChanged();
                getData();
            }
        });

        tv_score.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                type = 2;
                tv_dis.setTextColor(getResources().getColor(R.color.gray_99));
                tv_score.setTextColor(getResources().getColor(R.color.black_33));
                mDrvingSchools.clear();
                mStudyItemAdapter.notifyDataSetChanged();
                getData();
            }
        });
        mDrvingSchools.clear();
        mStudyItemAdapter = new StudyItemAdapter(getContext(), mDrvingSchools);
        lv_study.setAdapter(mStudyItemAdapter);
        lv_study.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    Intent intent = new Intent(getContext(), DrivingSchoolActivity.class);
                    intent.putExtra("data", mDrvingSchools.get(position));
                    startActivity(intent);

            }
        });

        getData();
    }

    private void getData() {
        Map<String, Object> params = new HashMap<>();
        params.put("type", type);
        params.put("page", page);
        params.put("longitude", MyApplication.getaMapLocation().getLongitude() + "");
        params.put("latitude", MyApplication.getaMapLocation().getLatitude() + "");
        Log.e("params", params.toString());
        HttpProxy.obtain().get(PlatformContans.DrivingSchool.getDrivingSchool, params, new ICallBack() {
            @Override
            public void OnSuccess(String result) {
                Log.e("getDrivingSchool", result);
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    JSONArray data = jsonObject.getJSONArray("data");
                    for (int i = 0; i < data.length(); i++) {
                        JSONObject item = data.getJSONObject(i);
                        DrvingSchool baikeItem = new Gson().fromJson(item.toString(), DrvingSchool.class);
                        mDrvingSchools.add(baikeItem);
                    }
                    mStudyItemAdapter.notifyDataSetChanged();
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
