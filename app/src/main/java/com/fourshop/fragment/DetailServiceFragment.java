package com.fourshop.fragment;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.costans.PlatformContans;
import com.entity.ServerType;
import com.example.yunchebao.R;
import com.fourshop.activity.FourShopDetailActivity;
import com.fourshop.adapter.ServiceContentAdapter;
import com.fourshop.adapter.ServiceTypeAdapter;
import com.fourshop.bean.ServiceContent;
import com.google.gson.Gson;
import com.http.HttpProxy;
import com.http.ICallBack;
import com.tool.MyListView;
import com.tool.listview.PersonalListView;
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
 * A simple {@link Fragment} subclass.
 */
public class DetailServiceFragment extends Fragment {
    ServiceContentAdapter mServiceContentAdapter;
    ServiceTypeAdapter mServiceTypeAdapter;
    @BindView(R.id.lv_left)
    MyListView lv_left;
    @BindView(R.id.lv_right)
    MyListView lv_right;
    @BindView(R.id.tv_type)
    TextView tv_type;
    FourShopDetailActivity mActivity;
    List<ServiceContent> mServiceContents;
     List<ServiceContent.ServeListBean> mServeListBeans ;
    String id;
    public DetailServiceFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_detail, container, false);
        ButterKnife.bind(this,view);
        mActivity= (FourShopDetailActivity) getActivity();
        id=mActivity.getId();
        initView();
        return view;
    }

    private void initView() {
        mServeListBeans=new ArrayList<>();
        mServiceContents=new ArrayList<>();
        mServiceTypeAdapter=new ServiceTypeAdapter(getContext(),mServiceContents);
        lv_left.setAdapter(mServiceTypeAdapter);
        lv_left.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                tv_type.setText(mServiceContents.get(position).getCategoryName());
                mServeListBeans.clear();
                mServeListBeans.addAll(mServiceContents.get(position).getServeList());
                mServiceTypeAdapter.setPos(position);
                mServiceTypeAdapter.notifyDataSetChanged();
                mServiceContentAdapter.notifyDataSetChanged();
            }
        });
        lv_left.setNestedScrollingEnabled(false);
        lv_right.setNestedScrollingEnabled(false);
        getData();
    }
    private void getData(){
        Map<String, Object> params = new HashMap<>();
        params.put("shopId", id);
        HttpProxy.obtain().get(PlatformContans.FourShop.getFourServeResultList, params, new ICallBack() {
            @Override
            public void OnSuccess(String result) {
                Log.e("data", result);
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    JSONArray data = jsonObject.getJSONArray("data");
                    for (int i = 0; i < data.length(); i++) {
                        JSONObject item = data.getJSONObject(i);
                        ServiceContent serverType = new Gson().fromJson(item.toString(), ServiceContent.class);
                        mServiceContents.add(serverType);
                    }

                    mServiceTypeAdapter.notifyDataSetChanged();
                    if (mServiceContents.size() > 0) {
                        tv_type.setText(mServiceContents.get(0).getCategoryName());
                        initDetaiListView();
                    }else{
                        tv_type.setVisibility(View.GONE);
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
        mServeListBeans.addAll(mServiceContents.get(0).getServeList());
        mServiceContentAdapter = new ServiceContentAdapter(getContext(), mServeListBeans);
        lv_right.setAdapter(mServiceContentAdapter);
        lv_right.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
    }

}
