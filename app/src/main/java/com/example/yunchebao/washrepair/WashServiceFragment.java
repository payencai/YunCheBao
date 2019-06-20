package com.example.yunchebao.washrepair;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import com.example.yunchebao.MyApplication;
import com.costans.PlatformContans;
import com.entity.ServerType;
import com.example.yunchebao.R;
import com.google.gson.Gson;
import com.http.HttpProxy;
import com.http.ICallBack;
import com.example.yunchebao.maket.GoodsPayActivity;
import com.tool.MyListView;
import com.vipcenter.RegisterActivity;
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
 * A simple {@link Fragment} subclass.
 */
public class WashServiceFragment extends Fragment {
    ServerCatogryAdapter mServerCatogryAdapter;
    ServerDetailAdapter mServerDetailAdapter;
    @BindView(R.id.lv_left)
    MyListView lv_left;
    @BindView(R.id.lv_right)
    MyListView lv_right;
    @BindView(R.id.tv_type)
    TextView tv_type;

    List<ServerType> mServiceContents;
     List<ServerType.ServeListBean> mServeListBeans ;
    String id;
    int type;
    String shopname;
    public WashServiceFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_wash_detail, container, false);
        ButterKnife.bind(this,view);
        id=getArguments().getString("id");
        shopname=getArguments().getString("shop");
        type=getArguments().getInt("type");
        initView();
        return view;
    }
    public static WashServiceFragment newInstance(String id,int type,String shopname){
        WashServiceFragment washServiceFragment=new WashServiceFragment();
        Bundle bundle=new Bundle();
        bundle.putString("id",id);
        bundle.putString("shop",shopname);
        bundle.putInt("type",type);
        washServiceFragment.setArguments(bundle);
        return washServiceFragment;
    }
    private void initView() {
        mServeListBeans=new ArrayList<>();
        mServiceContents=new ArrayList<>();
        mServerCatogryAdapter=new ServerCatogryAdapter(getContext(),mServiceContents);
        lv_left.setAdapter(mServerCatogryAdapter);
        lv_left.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                tv_type.setText(mServiceContents.get(position).getCategoryName());
                mServeListBeans.clear();
                mServeListBeans.addAll(mServiceContents.get(position).getServeList());
                mServerCatogryAdapter.setPos(position);
                mServerCatogryAdapter.notifyDataSetChanged();
                mServerDetailAdapter.notifyDataSetChanged();
            }
        });
        lv_left.setNestedScrollingEnabled(false);
        lv_right.setNestedScrollingEnabled(false);
        getData();
    }
    private void getData(){
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
                        mServiceContents.add(serverType);
                    }

                    mServerCatogryAdapter.notifyDataSetChanged();
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
        mServerDetailAdapter = new ServerDetailAdapter(getContext(), mServeListBeans);
        lv_right.setAdapter(mServerDetailAdapter);
        lv_right.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(MyApplication.isLogin){
                    ServerType.ServeListBean serveListBean= mServeListBeans.get(position);
                    takeOrder(serveListBean.getId(),serveListBean.getPrice(),serveListBean.getSecondName());
                }else{
                    startActivity(new Intent(getContext(), RegisterActivity.class));
                }

            }
        });
    }


    private void takeOrder(String id,double money,String name) {

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
                        intent.putExtra("money", money+"");
                        intent.putExtra("flag", "1");
                        intent.putExtra("shop", shopname);
                        intent.putExtra("goods", name);
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

}
