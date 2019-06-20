package com.cheyibao;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.baike.adapter.CarListAdapter;
import com.caryibao.NewCar;
import com.costans.PlatformContans;
import com.example.yunchebao.R;
import com.example.yunchebao.cheyibao.newcar.NewCarDetailActivity;
import com.example.yunchebao.cheyibao.newcar.NewCarShopActivity;
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

/**
 * A simple {@link Fragment} subclass.
 */
public class NewCarNearbyFragment extends Fragment {

    private List<NewCar> list;
    private CarListAdapter adapter;

    public NewCarNearbyFragment() {
        // Required empty public constructor
    }
    boolean isLoadMore=false;
    int page = 1;
    @BindView(R.id.lv_car)
    LoadMoreListView lv_car;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_new_car_nearby, container, false);
        ButterKnife.bind(this, view);
        list = new ArrayList<>();
        adapter = new CarListAdapter(getContext(), list);
        lv_car.setAdapter(adapter);
        lv_car.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("data", list.get(position));
                ActivityAnimationUtils.commonTransition(getActivity(), NewCarDetailActivity.class, ActivityConstans.Animation.FADE, bundle);
            }
        });
        lv_car.setOnLoadMoreListener(new LoadMoreListView.OnLoadMoreListener() {
            @Override
            public void onloadMore() {
                isLoadMore=true;
                page++;
                getData();
            }
        });
        getData();
        return view;
    }

    private void getData() {
        //UserInfo userInfo= MyApplication.getUserInfo();
        Map<String, Object> params = new HashMap<>();
        params.put("page", page);
        NewCarShopActivity activity = (NewCarShopActivity) getActivity();
        params.put("merchantId", activity.getShop().getId());
        Log.e("params", params.toString());
        HttpProxy.obtain().get(PlatformContans.NewCar.getNewCarMerchantMessage, params, new ICallBack() {
            @Override
            public void OnSuccess(String result) {
                Log.e("getnewcar", result);
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    jsonObject = jsonObject.getJSONObject("data");
                    JSONArray data = jsonObject.getJSONArray("beanList");
                    for (int i = 0; i < data.length(); i++) {
                        JSONObject item = data.getJSONObject(i);
                        NewCar baikeItem = new Gson().fromJson(item.toString(), NewCar.class);
                        list.add(baikeItem);
                    }
                    adapter.notifyDataSetChanged();
                    if(isLoadMore){
                        isLoadMore=false;
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
