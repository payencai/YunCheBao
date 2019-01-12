package com.maket.fragment;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;

import com.costans.PlatformContans;
import com.example.yunchebao.R;
import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.http.HttpProxy;
import com.http.ICallBack;
import com.maket.GoodDetailActivity;
import com.maket.adapter.ParamsInfoAdapter;
import com.maket.model.GoodInfoParams;
import com.maket.model.GoodList;
import com.nohttp.sample.BaseFragment;
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
import butterknife.OnClick;

/**
 * Created by sdhcjhss on 2018/1/9.
 */

public class GoodDetailInfoFragment extends BaseFragment {
    private Context ctx;
    ParamsInfoAdapter mParamsInfoAdapter;
    List<GoodInfoParams> mGoodInfoParams=new ArrayList<>();
    @BindView(R.id.lv_params)
    ListView lv_params;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.market_good_detail_info, container, false);
        ButterKnife.bind(this, rootView);
        init();
        return rootView;
    }

    private void init() {
        ctx = getActivity();
        mParamsInfoAdapter=new ParamsInfoAdapter(getContext(),mGoodInfoParams);
        lv_params.setAdapter(mParamsInfoAdapter);
        getParams();
    }
    private void getParams(){
        GoodDetailActivity activity= (GoodDetailActivity) getActivity();
        Map<String,Object> params=new HashMap<>();
        params.put("babyMerchantCommodityId",activity.getGoodList().getBabyMerchantId());
        HttpProxy.obtain().get(PlatformContans.GoodInfo.getGoodParams, params, new ICallBack() {
            @Override
            public void OnSuccess(String result) {
                Log.e("getGoodParams", result);
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    JSONArray data = jsonObject.getJSONArray("data");
                    for (int i = 0; i < data.length(); i++) {
                        JSONObject item = data.getJSONObject(i);
                        GoodInfoParams baikeItem = new Gson().fromJson(item.toString(), GoodInfoParams.class);
                        mGoodInfoParams.add(baikeItem);
                    }
                    mParamsInfoAdapter.notifyDataSetChanged();
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
