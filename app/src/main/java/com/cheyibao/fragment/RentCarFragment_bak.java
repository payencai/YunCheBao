package com.cheyibao.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.yunchebao.MyApplication;
import com.cheyibao.RentShopDetailActivity;
import com.cheyibao.adapter.AreaItemAdapter;
import com.cheyibao.adapter.RentCarItemAdapter;
import com.cheyibao.model.Area;
import com.cheyibao.model.RentCar;
import com.costans.PlatformContans;
import com.entity.Banner;

import com.example.yunchebao.R;
import com.google.gson.Gson;
import com.http.HttpProxy;
import com.http.ICallBack;
import com.nohttp.sample.BaseFragment;
import com.tool.JsonUtil;
import com.tool.slideshowview.SlideShowView;

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

public class RentCarFragment_bak extends BaseFragment   {
    @BindView(R.id.lv_left)
    ListView lv_left;
    @BindView(R.id.lv_right)
    ListView lv_right;
    @BindView(R.id.tv_tag)
    TextView tv_tag;
    AreaItemAdapter mAreaItemAdapter;
    RentCarItemAdapter mRentCarItemAdapter;
    String cityCode;
    String json;
    List<Area> mAreas = new ArrayList<>();
    List<RentCar> mRentCars = new ArrayList<>();
    //轮播图片
    private List<Map<String, String>> imageList = new ArrayList<>();
    @BindView(R.id.slideshowView)
    SlideShowView slideShowView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.rent_home_layout_bak, container, false);
        ButterKnife.bind(this, rootView);
        String adcode = MyApplication.getaMapLocation().getAdCode();
        if (!TextUtils.isEmpty(adcode))
            cityCode = MyApplication.getaMapLocation().getAdCode().substring(0, 4) + "00";
//        else{
//            cityCode="440100";
//        }
        getBaner();
        mAreas.clear();

        getJsonData();
        initView();
        mRentCars.clear();
        getShop(1, "");
        return rootView;
    }
    int page=1;
    private void getShop(int type, String area) {
        Map<String, Object> params = new HashMap<>();
        params.put("type", type);
        params.put("page",page);
        if (!TextUtils.isEmpty(area))
            params.put("region", area);
        params.put("longitude", MyApplication.getaMapLocation().getLongitude() + "");
        params.put("latitude", MyApplication.getaMapLocation().getLatitude() + "");
        HttpProxy.obtain().get(PlatformContans.CarRent.getRentCar, params, "", new ICallBack() {
            @Override
            public void OnSuccess(String result) {
                Log.e("getdata", result);
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    JSONArray data = jsonObject.getJSONArray("data");
                    for (int i = 0; i < data.length(); i++) {
                        JSONObject item = data.getJSONObject(i);
                        RentCar baikeItem = new Gson().fromJson(item.toString(), RentCar.class);
                        mRentCars.add(baikeItem);
                    }
                    mRentCarItemAdapter.notifyDataSetChanged();
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

    private void getJsonData() {
        Area area1 = new Area();
        area1.setName("附近门店");
        mAreas.add(area1);
        json = JsonUtil.getJson(getContext(), "area.json");
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray areas = jsonObject.getJSONArray(cityCode);
            Log.e("areas", areas.length() + "");
            for (int i = 0; i < areas.length(); i++) {
                JSONObject item = areas.getJSONObject(i);
                Area area = new Gson().fromJson(item.toString(), Area.class);
                mAreas.add(area);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private void getBaner() {
        imageList.clear();
        Map<String, Object> params = new HashMap<>();
        params.put("type", 4);
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
//        //网络地址获取轮播图
//        imageList.clear();
//        for (int i = 0; i < 3; i++) {
//            Map<String, String> image_uri = new HashMap<String, String>();
//            image_uri.put("imageUrls", "https://you.autoimg.cn/_autohomecar__zhouyouji/657C2F909017074F9C59CA0B88DA0F0BDDC9.jpg?imageMogr2/format/jpg/thumbnail/790|watermark/2/text/5rG96L2m5LmL5a62/font/5b6u6L2v6ZuF6buR/fontsize/270/fill/I0ZGRkZGRg==");
//            imageList.add(image_uri);
//        }

    }

    private void initView() {
        mAreaItemAdapter = new AreaItemAdapter(getContext(), mAreas);
        mRentCarItemAdapter = new RentCarItemAdapter(getContext(), mRentCars);
        lv_left.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                tv_tag.setText(mAreas.get(position).getName());
                mAreaItemAdapter.setSelectedPosition(position);
                mAreaItemAdapter.notifyDataSetChanged();
                Area area=mAreas.get(position);
                mRentCars.clear();
                if(position==0){
                    getShop(1,"");
                }else{
                    getShop(2,area.getName());
                }

            }
        });
        lv_right.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(getContext(), RentShopDetailActivity.class);
                RentCar rentCar=mRentCars.get(position);
                intent.putExtra("data",rentCar);
                startActivity(intent);
            }
        });
        lv_left.setAdapter(mAreaItemAdapter);
        lv_right.setAdapter(mRentCarItemAdapter);



}


}
