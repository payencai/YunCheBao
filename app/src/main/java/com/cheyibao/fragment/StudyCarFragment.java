package com.cheyibao.fragment;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.application.MyApplication;
import com.bbcircle.data.CarShow;
import com.cheyibao.DrivingSchoolActivity;
import com.cheyibao.adapter.StudyItemAdapter;
import com.cheyibao.model.DrvingSchool;
import com.costans.PlatformContans;
import com.entity.Banner;
import com.entity.PhoneCommBaseType;
import com.example.yunchebao.R;
import com.google.gson.Gson;
import com.http.HttpProxy;
import com.http.ICallBack;
import com.nohttp.sample.BaseFragment;
import com.tool.ActivityConstans;
import com.tool.UIControlUtils;
import com.tool.listview.PersonalListView;
import com.tool.listview.PersonalScrollView;
import com.tool.slideshowview.SlideShowView;
import com.tool.viewpager.IndicatorViewPager;
import com.tool.viewpager.OnTransitionTextListener;
import com.tool.viewpager.ScrollIndicatorView;
import com.vipcenter.RegisterActivity;
import com.xihubao.Shop4SListActivity;
import com.xihubao.fragment.Shop4SListFragment;

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
    SlideShowView slideShowView;
    @BindView(R.id.scollview)
    PersonalScrollView mPersonalScrollView;
    StudyItemAdapter mStudyItemAdapter;
    List<DrvingSchool> mDrvingSchools = new ArrayList<>();
    int type = 1;
    //轮播图片
    private List<Map<String, String>> imageList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.indicator_vp_list, container, false);
        ButterKnife.bind(this, rootView);
        initView();
        getBaner();
        return rootView;
    }

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
                        String url = item.getString("picture");
                        Map<String, String> image_uri = new HashMap<String, String>();
                        image_uri.put("imageUrls", url);
                        imageList.add(image_uri);
                    }
                    slideShowView.setImageUrls(imageList);
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
                //mStudyItemAdapter.notifyDataSetChanged();
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
                //mStudyItemAdapter.notifyDataSetChanged();
                getData();
            }
        });
        mDrvingSchools.clear();
        mStudyItemAdapter = new StudyItemAdapter(getContext(), mDrvingSchools);
        lv_study.setAdapter(mStudyItemAdapter);
        lv_study.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (MyApplication.isLogin) {
                    Intent intent = new Intent(getContext(), DrivingSchoolActivity.class);
                    intent.putExtra("data", mDrvingSchools.get(position));
                    startActivity(intent);
                } else {
                    startActivity(new Intent(getContext(), RegisterActivity.class));
                }
            }
        });

        getData();
    }

    private void getData() {
        Map<String, Object> params = new HashMap<>();
        params.put("type", type);
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
