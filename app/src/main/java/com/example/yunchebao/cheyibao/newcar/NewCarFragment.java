package com.example.yunchebao.cheyibao.newcar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;

import com.baike.adapter.CarListAdapter;
import com.caryibao.NewCar;
import com.cheyibao.adapter.NewCarMenuAdapter;
import com.costans.PlatformContans;
import com.entity.Banner;
import com.example.yunchebao.R;
import com.google.gson.Gson;
import com.http.HttpProxy;
import com.http.ICallBack;
import com.nohttp.sample.BaseFragment;
import com.tool.ActivityAnimationUtils;
import com.tool.ActivityConstans;
import com.tool.listview.PersonalListView;
import com.tool.listview.PersonalScrollView;
import com.tool.slideshowview.SlideShowView;
import com.tool.view.GridViewForScrollView;
import com.xihubao.model.CarBrand;

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

public class NewCarFragment extends BaseFragment {

    @BindView(R.id.listview)
    PersonalListView mListView;
    @BindView(R.id.sc_new)
    PersonalScrollView sc_new;
    List<CarBrand> mCarBrands = new ArrayList<>();
    //轮播图片
    private List<Map<String, String>> imageList = new ArrayList<>();
    @BindView(R.id.slideshowView)
    SlideShowView slideShowView;

    @BindView(R.id.gv_newcar)
    GridViewForScrollView gv_newcar;
    NewCarMenuAdapter mNewCarMenuAdapter;
    List<CarBrand> mNewCarMenus=new ArrayList<>();
    int page=1;
    private List<NewCar> mNewCars;
    private CarListAdapter adapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_xinche, container, false);
        ButterKnife.bind(this, rootView);
        initView();
        mNewCarMenus.clear();
        return rootView;
    }


    private void getNewCar() {
        Map<String, Object> params = new HashMap<>();
        params.put("page", page);
        HttpProxy.obtain().get(PlatformContans.NewCar.getNewCarListByApp, params,  new ICallBack() {
            @Override
            public void OnSuccess(String result) {
                Log.e("newcar",result);
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    JSONArray data = jsonObject.getJSONArray("data");
                    for (int i = 0; i < data.length(); i++) {
                        JSONObject item = data.getJSONObject(i);
                        NewCar baikeItem = new Gson().fromJson(item.toString(), NewCar.class);
                        mNewCars.add(baikeItem);
                    }
                    adapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(String error) {

            }
        });
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
                        mCarBrands.add(baikeItem);
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

    private void getBaner() {
        imageList.clear();
        Map<String, Object> params = new HashMap<>();
        params.put("type", 2);
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
    private void initView() {
        mCarBrands.clear();
        mNewCars=new ArrayList<>();
        mNewCarMenuAdapter=new NewCarMenuAdapter(getContext(),mNewCarMenus);
        gv_newcar.setAdapter(mNewCarMenuAdapter);
        adapter = new CarListAdapter(getContext(),mNewCars );
        mListView.setAdapter(adapter);
        mListView.setDividerHeight(0);
        mListView.setDivider(null);
        mListView.setFocusable(false);
        mListView.setFocusableInTouchMode(false);
        mListView.requestFocus();
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bundle bundle=new Bundle();
                bundle.putSerializable("data",mNewCars.get(position));
                ActivityAnimationUtils.commonTransition(getActivity(), NewCarDetailActivity.class, ActivityConstans.Animation.FADE,bundle);
            }
        });
        sc_new.setOnScrollBottomListener(new PersonalScrollView.OnScrollBottomListener() {
            @Override
            public void onScrollBottom() {
                page++;
                getNewCar();
            }
        });
        gv_newcar.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(getContext(),NewCarListActivity.class);
                Bundle bundle=new Bundle();
                bundle.putInt("flag",10);
                bundle.putSerializable("brand",mCarBrands.get(position));
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        //网络地址获取轮播图
        imageList.clear();
        getBaner();
        getBrand(1);
        getNewCar();

    }


    @OnClick({R.id.toAllCars,R.id.selectMenu1, R.id.selectMenu2, R.id.selectMenu3, R.id.selectMenu4,R.id.tv_p55,R.id.tv_p70,R.id.tv_p85,R.id.tv_p100})
    public void OnClick(View v) {
        Bundle bundle = new Bundle();

        switch (v.getId()) {
            case R.id.tv_p55:
                bundle.putInt("flag", 5);
                ActivityAnimationUtils.commonTransition(getActivity(), NewCarListActivity.class, ActivityConstans.Animation.FADE, bundle);
                break;
            case R.id.tv_p70:
                bundle.putInt("flag", 6);
                ActivityAnimationUtils.commonTransition(getActivity(), NewCarListActivity.class, ActivityConstans.Animation.FADE, bundle);
                break;
            case R.id.tv_p85:
                bundle.putInt("flag", 7);
                ActivityAnimationUtils.commonTransition(getActivity(), NewCarListActivity.class, ActivityConstans.Animation.FADE, bundle);
                break;
            case R.id.tv_p100:
                bundle.putInt("flag", 8);
                ActivityAnimationUtils.commonTransition(getActivity(), NewCarListActivity.class, ActivityConstans.Animation.FADE, bundle);
                break;
            case R.id.toAllCars:
                bundle.putInt("flag", 9);
                ActivityAnimationUtils.commonTransition(getActivity(), NewCarListActivity.class, ActivityConstans.Animation.FADE, bundle);
                break;
            case R.id.selectMenu1:
                bundle.putInt("flag", 1);
                ActivityAnimationUtils.commonTransition(getActivity(), NewCarListActivity.class, ActivityConstans.Animation.FADE, bundle);
                break;
            case R.id.selectMenu2:
                bundle.putInt("flag", 2);
                ActivityAnimationUtils.commonTransition(getActivity(), NewCarListActivity.class, ActivityConstans.Animation.FADE, bundle);
                break;
            case R.id.selectMenu3:

                bundle.putInt("flag", 3);
                ActivityAnimationUtils.commonTransition(getActivity(), NewCarListActivity.class, ActivityConstans.Animation.FADE, bundle);
                break;
            case R.id.selectMenu4:
                bundle.putInt("flag", 4);
                ActivityAnimationUtils.commonTransition(getActivity(), NewCarListActivity.class, ActivityConstans.Animation.FADE, bundle);

                break;


        }
    }

}