package com.xihubao.fragment;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.costans.PlatformContans;
import com.entity.GoodsListBean;
import com.entity.PhoneShopEntity;
import com.entity.ServerType;
import com.eowise.recyclerview.stickyheaders.OnHeaderClickListener;
import com.eowise.recyclerview.stickyheaders.StickyHeadersBuilder;
import com.eowise.recyclerview.stickyheaders.StickyHeadersItemDecoration;
import com.example.yunchebao.R;
import com.google.gson.Gson;
import com.http.HttpProxy;
import com.http.ICallBack;
import com.nohttp.sample.BaseFragment;
import com.nohttp.tools.HttpJsonClient;
import com.tool.ActivityAnimationUtils;
import com.tool.ActivityConstans;
import com.tool.UIControlUtils;
import com.xihubao.OrderPayActivity;
import com.xihubao.WashPayActivity;
import com.xihubao.adapter.BigramHeaderAdapter;
import com.xihubao.adapter.PersonAdapter;
import com.xihubao.adapter.RecycleGoodsCategoryListAdapter;
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

    @BindView(R.id.goods_category_list)
    ListView mGoodsCateGoryList;
    @BindView(R.id.goods_recycleView)
    ListView goods_recycleView;
    ServerDetailAdapter mServerDetailAdapter;
    ServerCatogryAdapter mServerCatogryAdapter;
    String id;
    String type;
    //商品类别列表
    private List<ServerType> mServerTypes=new ArrayList<>();
    //商品列表
    private List<ServerType.ServeListBean> mServeListBeans=new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.shop_detail_goods, container, false);
        ButterKnife.bind(this,view);
        id=this.getArguments().getString("id");
        type=getArguments().getString("type");
        initView(view);
        return view;
    }
    private void initView(View view) {
        mServerCatogryAdapter=new ServerCatogryAdapter(getContext(),mServerTypes);
        mGoodsCateGoryList.setAdapter(mServerCatogryAdapter);
        mGoodsCateGoryList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mServeListBeans.clear();
                mServeListBeans.addAll(mServerTypes.get(position).getServeList());
                Log.e("res",mServerTypes.get(position).getServeList().size()+"");
                mServerDetailAdapter.notifyDataSetChanged();
                for (int i = 0; i < mServerCatogryAdapter.getCount(); i++) {
                    View view1=mGoodsCateGoryList.getChildAt(i);
                    if(i==position){
                        view1.setBackgroundColor(getContext().getResources().getColor(R.color.white));
                    }else{
                        view1.setBackgroundColor(getContext().getResources().getColor(R.color.gray_e2));
                    }

                }
            }
        });
        goods_recycleView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(getContext(), WashPayActivity.class);
                intent.putExtra("data",mServeListBeans.get(position));
                intent.putExtra("type",type);
                startActivity(intent);
            }
        });
        getData();
    }
    private void initDetaiListView(){
        mServeListBeans.addAll(mServerTypes.get(0).getServeList());
        mServerDetailAdapter=new ServerDetailAdapter(getContext(),mServeListBeans);
        goods_recycleView.setAdapter(mServerDetailAdapter);
    }
    private void getData(){
        Map<String,Object> params=new HashMap<>();
        params.put("shopId",id);
        HttpProxy.obtain().get(PlatformContans.CarWashRepairShop.getWashRepairServeResultByShopId, params, new ICallBack() {
            @Override
            public void OnSuccess(String result) {
                Log.e("data",result);
                try {
                    JSONObject jsonObject=new JSONObject(result);
                    JSONArray data=jsonObject.getJSONArray("data");
                    for (int i = 0; i <data.length() ; i++) {
                        JSONObject item=data.getJSONObject(i);
                        ServerType serverType=new Gson().fromJson(item.toString(),ServerType.class);
                        mServerTypes.add(serverType);
                    }
                    mServerCatogryAdapter.notifyDataSetChanged();
                    if(mServerTypes.size()>0){
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
    public static GoodsFragment newInstance(String id,String type){
        GoodsFragment goodsFragment=new GoodsFragment();
        Bundle bundle=new Bundle();
        bundle.putString("id",id);
        bundle.putString("type",type);
        goodsFragment.setArguments(bundle);
        return  goodsFragment;
    }
}
