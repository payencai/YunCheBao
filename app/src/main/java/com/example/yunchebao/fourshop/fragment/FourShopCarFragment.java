package com.example.yunchebao.fourshop.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.costans.PlatformContans;
import com.example.yunchebao.R;
import com.example.yunchebao.fourshop.activity.CarDetailActivity;
import com.example.yunchebao.fourshop.activity.FourShopDetailActivity;
import com.example.yunchebao.fourshop.adapter.FourShopCarAdapter;
import com.example.yunchebao.fourshop.bean.FourShopCar;
import com.google.gson.Gson;
import com.http.HttpProxy;
import com.http.ICallBack;

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
public class FourShopCarFragment extends Fragment {
    @BindView(R.id.rv_order)
    RecyclerView rv_order;
    FourShopCarAdapter mFourShopCarAdapter;
    List<FourShopCar> mFourShopCars;
    int page=1;
    boolean isLoadMore=false;
    String id;
    FourShopDetailActivity mActivity;
    public FourShopCarFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_four_shop_car, container, false);
        ButterKnife.bind(this,view);
        mActivity= (FourShopDetailActivity) getActivity();
        id=mActivity.getId();
        initView();
        return view;
    }

    private void initView() {
        mFourShopCars=new ArrayList<>();
        mFourShopCarAdapter=new FourShopCarAdapter(R.layout.item_4s_car,mFourShopCars);
        mFourShopCarAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                page++;
                isLoadMore=true;
                getData();
            }
        },rv_order);
        mFourShopCarAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                FourShopCar fourShopCar= (FourShopCar) adapter.getItem(position);
                Intent intent=new Intent(getContext(), CarDetailActivity.class);
                intent.putExtra("id",fourShopCar.getId());
                startActivity(intent);
            }
        });
        rv_order.setLayoutManager(new LinearLayoutManager(getContext()));
        rv_order.setAdapter(mFourShopCarAdapter);
        getData();
    }
    private void getData(){
        Map<String, Object> params = new HashMap<>();
        params.put("page", page);
        params.put("shopId", id);
        HttpProxy.obtain().get(PlatformContans.FourShop.getFourCarListForApp, params, new ICallBack() {
            @Override
            public void OnSuccess(String result) {
                Log.e("getDrivingSchool", result);
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    JSONArray data = jsonObject.getJSONArray("data");
                    List<FourShopCar> replaceDrives=new ArrayList<>();
                    for (int i = 0; i < data.length(); i++) {
                        JSONObject item = data.getJSONObject(i);
                        FourShopCar replaceDrive = new Gson().fromJson(item.toString(), FourShopCar.class);
                        mFourShopCars.add(replaceDrive);
                        replaceDrives.add(replaceDrive);
                    }
                    if (isLoadMore) {
                        isLoadMore = false;
                        if (data.length() == 0) {
                            mFourShopCarAdapter.loadMoreEnd(true);
                        } else {
                            mFourShopCarAdapter.addData(replaceDrives);
                            mFourShopCarAdapter.loadMoreComplete();
                        }
                    } else {
                        mFourShopCarAdapter.setNewData(mFourShopCars);

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
