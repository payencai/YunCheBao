package com.xihubao.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.example.yunchebao.MyApplication;
import com.costans.PlatformContans;
import com.entity.ServerType;

import com.example.yunchebao.R;
import com.google.gson.Gson;
import com.http.HttpProxy;
import com.http.ICallBack;
import com.example.yunchebao.maket.GoodsPayActivity;
import com.nohttp.sample.BaseFragment;
import com.tool.MyListView;

import com.xihubao.adapter.ServerCatogryAdapter;
import com.xihubao.adapter.ServerDetailAdapter;


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
 * 商品
 */
public class GoodsFragment extends BaseFragment {

    @BindView(R.id.lv_left)
    MyListView lv_category;
    @BindView(R.id.lv_right)
    MyListView goods_recycleView;
    ServerDetailAdapter mServerDetailAdapter;
    ServerCatogryAdapter mServerCatogryAdapter;
    String id;
    int type;
    String money;
    //商品类别列表
    private List<ServerType> mServerTypes = new ArrayList<>();
    //商品列表
    private List<ServerType.ServeListBean> mServeListBeans = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.shop_detail_goods, container, false);
        ButterKnife.bind(this, view);
        id = getArguments().getString("id");
        type = getArguments().getInt("type");
        initView(view);
        return view;
    }

    private void initView(View view) {
        mServerCatogryAdapter = new ServerCatogryAdapter(getContext(), mServerTypes);
        lv_category.setAdapter(mServerCatogryAdapter);
        lv_category.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mServeListBeans.clear();
                mServeListBeans.addAll(mServerTypes.get(position).getServeList());
                mServerCatogryAdapter.setPos(position);
                mServerCatogryAdapter.notifyDataSetChanged();
                Log.e("res", mServerTypes.get(position).getServeList().size() + "");
                mServerDetailAdapter.notifyDataSetChanged();
            }
        });
        goods_recycleView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                money = mServeListBeans.get(position).getPrice() + "";
                takeOrder(mServeListBeans.get(position).getId(), mServeListBeans.get(position).getFirstName());
            }
        });
        getData();
    }

    private void takeOrder(String id,String goods) {

        Map<String, Object> params = new HashMap<>();
        params.put("id", id);
        HttpProxy.obtain().post(PlatformContans.CarWashRepairShop.addWashRepairOrder, MyApplication.token, params, new ICallBack() {
            @Override
            public void OnSuccess(String result) {
                Log.e("result",result);
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    int code = jsonObject.getInt("resultCode");
                    if (code == 0) {
                        String orderid = jsonObject.getString("data");
                        Intent intent = new Intent(getContext(), GoodsPayActivity.class);
                        intent.putExtra("orderid", orderid);
                        intent.putExtra("money", money);
                        intent.putExtra("flag", "1");
                        intent.putExtra("shop", goods);
                        intent.putExtra("goods", goods);
                        startActivity(intent);
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


    private void initDetaiListView() {
        mServeListBeans.addAll(mServerTypes.get(0).getServeList());
        mServerDetailAdapter = new ServerDetailAdapter(getContext(), mServeListBeans);
        goods_recycleView.setAdapter(mServerDetailAdapter);
    }

    private void getData() {
        Map<String, Object> params = new HashMap<>();
        params.put("shopId", id);
        params.put("type",type);
        HttpProxy.obtain().get(PlatformContans.CarWashRepairShop.getWashRepairServeResultByShopId, params, new ICallBack() {
            @Override
            public void OnSuccess(String result) {
                Log.e("data", result);
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    JSONArray data = jsonObject.getJSONArray("data");
                    for (int i = 0; i < data.length(); i++) {
                        JSONObject item = data.getJSONObject(i);
                        ServerType serverType = new Gson().fromJson(item.toString(), ServerType.class);
                        mServerTypes.add(serverType);
                    }
                    mServerCatogryAdapter.notifyDataSetChanged();
                    if (mServerTypes.size() > 0) {
                        initDetaiListView();
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

    public static GoodsFragment newInstance(String id, int type) {
        GoodsFragment goodsFragment = new GoodsFragment();
        Bundle bundle = new Bundle();
        bundle.putString("id", id);
        bundle.putInt("type", type);
        goodsFragment.setArguments(bundle);
        return goodsFragment;
    }
}
