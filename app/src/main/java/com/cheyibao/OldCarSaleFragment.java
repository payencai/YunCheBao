package com.cheyibao;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.cheyibao.adapter.CarRecommendListAdapter;
import com.cheyibao.model.OldCar;
import com.costans.PlatformContans;
import com.example.yunchebao.R;
import com.example.yunchebao.cheyibao.oldcar.OldCarDetailActivity;
import com.google.gson.Gson;
import com.http.HttpProxy;
import com.http.ICallBack;
import com.example.yunchebao.maket.model.LoadMoreListView;
import com.tool.ActivityAnimationUtils;
import com.tool.ActivityConstans;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;


public class OldCarSaleFragment extends Fragment {
    int page = 1;
    List<OldCar> mOldCars;
    CarRecommendListAdapter adapter;
    boolean isLoadMore = false;
    @BindView(R.id.lv_car)
    LoadMoreListView lv_car;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_old_car_sale, container, false);
        ButterKnife.bind(this, view);
        mOldCars = new ArrayList<>();
        adapter = new CarRecommendListAdapter(getActivity(), mOldCars);
        lv_car.setAdapter(adapter);
        lv_car.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Bundle bundle = new Bundle();
                bundle.putSerializable("data", mOldCars.get(position));
                ActivityAnimationUtils.commonTransition(getActivity(), OldCarDetailActivity.class, ActivityConstans.Animation.FADE, bundle);

            }
        });
        lv_car.setOnLoadMoreListener(new LoadMoreListView.OnLoadMoreListener() {
            @Override
            public void onloadMore() {
                isLoadMore = true;
                page++;
                getData();
            }
        });
        getData();
        return view;
    }

    private void getData() {
        Map<String, Object> params = new HashMap<>();
        OldCarShopActivity activity = (OldCarShopActivity) getActivity();
        params.put("page", page);
        params.put("merchantId", activity.getId());
        Log.e("params", params.toString());
        HttpProxy.obtain().get(PlatformContans.OldCar.getOldCarMerchantCarByApp, params, new ICallBack() {
            @Override
            public void OnSuccess(String result) {

                try {
                    JSONObject jsonObject = new JSONObject(result);
                    jsonObject = jsonObject.getJSONObject("data");
                    JSONArray data = jsonObject.getJSONArray("beanList");
                    for (int i = 0; i < data.length(); i++) {
                        JSONObject item = data.getJSONObject(i);
                        OldCar baikeItem = new Gson().fromJson(item.toString(), OldCar.class);
                        mOldCars.add(baikeItem);
                    }
                    Log.e("getdata", result);
                    adapter.notifyDataSetChanged();
                    if (isLoadMore) {
                        isLoadMore = false;
                        lv_car.setLoadCompleted();
                    }
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

}
