package com.cheyibao.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.application.MyApplication;
import com.baidu.platform.comapi.map.A;
import com.cheyibao.CarSecondBrandActivity;
import com.cheyibao.NewCarActivity;
import com.cheyibao.NewCarListActivity;
import com.cheyibao.adapter.NewCarAdapter;
import com.cheyibao.adapter.NewCarMenuAdapter;
import com.cheyibao.model.NewCarMenu;
import com.costans.PlatformContans;
import com.entity.Banner;
import com.example.yunchebao.R;
import com.google.gson.Gson;
import com.http.HttpProxy;
import com.http.ICallBack;
import com.maket.model.GoodMenu;
import com.nanchen.wavesidebar.WaveSideBarView;
import com.nohttp.sample.BaseFragment;
import com.rongcloud.sidebar.PinnedHeaderDecoration;
import com.tool.ActivityAnimationUtils;
import com.tool.ActivityConstans;
import com.tool.indexbar.CarBean;
import com.tool.indexbar.RecCarAdapter;
import com.tool.indexbar.Utils;
import com.tool.listview.PersonalListView;
import com.tool.slideshowview.SlideShowView;
import com.tool.view.GridViewForScrollView;
import com.tool.view.ListViewForScrollView;
import com.vipcenter.RegisterActivity;
import com.xihubao.CarBrandSelectActivity;
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
import qdx.indexbarlayout.IndexBar;
import qdx.indexbarlayout.IndexLayout;
import qdx.stickyheaderdecoration.NormalDecoration;

/**
 * Created by sdhcjhss on 2017/12/9.
 */

public class NewCarFragment extends BaseFragment {

    @BindView(R.id.listview)
    ListViewForScrollView mListView;
    NewCarAdapter adapter;
    List<CarBrand> mCarBrands = new ArrayList<>();
    //轮播图片
    private List<Map<String, String>> imageList = new ArrayList<>();
    @BindView(R.id.slideshowView)
    SlideShowView slideShowView;
    @BindView(R.id.index_layout)
    WaveSideBarView mWaveSideBarView;
    @BindView(R.id.gv_newcar)
    GridViewForScrollView gv_newcar;
    NewCarMenuAdapter mNewCarMenuAdapter;
    List<NewCarMenu> mNewCarMenus=new ArrayList<>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_xinche, container, false);
        ButterKnife.bind(this, rootView);
        initView();
        mNewCarMenus.clear();
        getMenu();
        return rootView;
    }
    private void getMenu() {
        Map<String, Object> params = new HashMap<>();
        params.put("type", 1);
        HttpProxy.obtain().get(PlatformContans.CarCategory.getNewOldIndex, params, new ICallBack() {
            @Override
            public void OnSuccess(String result) {

                try {
                    JSONObject jsonObject = new JSONObject(result);
                    JSONArray data = jsonObject.getJSONArray("data");
                    for (int i = 0; i < data.length(); i++) {
                        JSONObject item = data.getJSONObject(i);
                        NewCarMenu baikeItem = new Gson().fromJson(item.toString(), NewCarMenu.class);
                        mNewCarMenus.add(baikeItem);
                    }
                    mNewCarMenuAdapter.notifyDataSetChanged();
                    //Log.e("getdata", result);
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
    private void getData(int level) {
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
                    }
                    adapter.notifyDataSetChanged();
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

    private void initView() {
        mCarBrands.clear();
        adapter = new NewCarAdapter(getContext(), mCarBrands);
        mNewCarMenuAdapter=new NewCarMenuAdapter(getContext(),mNewCarMenus);
        gv_newcar.setAdapter(mNewCarMenuAdapter);
        mListView.setAdapter(adapter);
        mListView.setDividerHeight(0);
        mListView.setDivider(null);
        mListView.setFocusable(false);
        mListView.setFocusableInTouchMode(false);
        mListView.requestFocus();
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(getContext(),NewCarListActivity.class);
                Bundle bundle=new Bundle();
                bundle.putInt("flag",6);
                bundle.putSerializable("brand",mCarBrands.get(position));
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        gv_newcar.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                NewCarMenu newCarMenu=mNewCarMenus.get(position);
                Intent intent=new Intent(getContext(),NewCarListActivity.class);
                Bundle bundle=new Bundle();
                bundle.putInt("flag",5);
                bundle.putSerializable("menu",newCarMenu);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        //网络地址获取轮播图
        imageList.clear();
        getBaner();
        getData(1);

    }


    @OnClick({R.id.selectMenu1, R.id.selectMenu2, R.id.selectMenu3, R.id.selectMenu4, R.id.menuLay1, R.id.menuLay2, R.id.menuLay3, R.id.menuLay4, R.id.menuLay5, R.id.menuLay6, R.id.menuLay7, R.id.menuLay8})
    public void OnClick(View v) {
        Bundle bundle = new Bundle();

        switch (v.getId()) {
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
            case R.id.menuLay1:
            case R.id.menuLay2:
            case R.id.menuLay3:
            case R.id.menuLay4:
            case R.id.menuLay5:
            case R.id.menuLay6:
                // if(MyApplication.isLogin)
                ActivityAnimationUtils.commonTransition(getActivity(), NewCarListActivity.class, ActivityConstans.Animation.FADE);
//                else{
//                    startActivity(new Intent(getContext(), RegisterActivity.class));
//                }
                break;
            case R.id.menuLay7:
                //   if(MyApplication.isLogin)
                ActivityAnimationUtils.commonTransition(getActivity(), NewCarListActivity.class, ActivityConstans.Animation.FADE);
//                else{
//                    startActivity(new Intent(getContext(), RegisterActivity.class));
//                }
                break;
            case R.id.menuLay8:
                //if(MyApplication.isLogin)
                ActivityAnimationUtils.commonTransition(getActivity(), NewCarListActivity.class, ActivityConstans.Animation.FADE);
//                else{
//                    startActivity(new Intent(getContext(), RegisterActivity.class));
//                }
                break;
        }
    }

}
