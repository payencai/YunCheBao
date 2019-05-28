package com.vipcenter.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.application.MyApplication;
import com.bbcircle.CarShowDetailActivity;
import com.example.yunchebao.babycircle.carfriend.DriverFriendsDetailActivity;
import com.example.yunchebao.babycircle.selfdrive.DrivingSelfDetailActivity;
import com.bbcircle.RaceDetailActivity;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.costans.PlatformContans;
import com.entity.PhoneArticleEntity;
import com.example.yunchebao.R;
import com.google.gson.Gson;
import com.http.HttpProxy;
import com.http.ICallBack;
import com.nohttp.sample.BaseFragment;
import com.tool.ActivityAnimationUtils;
import com.tool.ActivityConstans;
import com.vipcenter.adapter.ArticleListAdapter;

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
 * Created by sdhcjhss on 2018/1/6.
 */

public class ArticleFragment extends BaseFragment {
    @BindView(R.id.rv_collect)
    RecyclerView rv_collect;
    List<PhoneArticleEntity> mWashCollects ;
    ArticleListAdapter mWashCollectAdapter;
    int page=1;
    boolean isLoadMore=false;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_collect_commom, container, false);
        ButterKnife.bind(this, rootView);
        init();
        return rootView;
    }

    private void init() {
        mWashCollects=new ArrayList<>();
        mWashCollectAdapter=new ArticleListAdapter(R.layout.item_article,mWashCollects);
        mWashCollectAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                page++;
                isLoadMore=true;
                getData();
            }
        });
        mWashCollectAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                PhoneArticleEntity item= (PhoneArticleEntity) adapter.getItem(position);
                Bundle bundle=new Bundle();
                bundle.putInt("id",item.getCircleId());
                bundle.putInt("type",item.getType());
                switch (item.getType()){
                    case 1:
                        ActivityAnimationUtils.commonTransition(getActivity(), DrivingSelfDetailActivity.class, ActivityConstans.Animation.FADE,bundle);
                        break;
                    case 2:
                        ActivityAnimationUtils.commonTransition(getActivity(), DriverFriendsDetailActivity.class, ActivityConstans.Animation.FADE,bundle);
                        break;
                    case 3:
                        ActivityAnimationUtils.commonTransition(getActivity(), CarShowDetailActivity.class, ActivityConstans.Animation.FADE,bundle);
                        break;
                    case 4:
                        ActivityAnimationUtils.commonTransition(getActivity(), RaceDetailActivity.class, ActivityConstans.Animation.FADE,bundle);
                        break;
                }
            }


        });
        rv_collect.setLayoutManager(new LinearLayoutManager(getContext()));
        rv_collect.setAdapter(mWashCollectAdapter);
        getData();

    }
    private void getData(){

        Map<String,Object> params=new HashMap<>();
        params.put("page",page);
        HttpProxy.obtain().get(PlatformContans.Collect.getBabyCollection, params, MyApplication.token,new ICallBack() {
            @Override
            public void OnSuccess(String result) {
                Log.e("getNewCarCollectionList", result);
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    jsonObject=jsonObject.getJSONObject("data");
                    JSONArray data = jsonObject.getJSONArray("beanList");
                    List<PhoneArticleEntity>washCollects=new ArrayList<>();
                    for (int i = 0; i < data.length(); i++) {
                        JSONObject item = data.getJSONObject(i);
                        PhoneArticleEntity baikeItem = new Gson().fromJson(item.toString(), PhoneArticleEntity.class);
                        washCollects.add(baikeItem);
                        mWashCollects.add(baikeItem);
                    }
                    if(isLoadMore){
                        isLoadMore=false;
                        if(data.length()>0){
                            mWashCollectAdapter.addData(washCollects);
                            mWashCollectAdapter.loadMoreComplete();
                        }else{
                            mWashCollectAdapter.loadMoreEnd(true);
                        }

                    }else{
                        mWashCollectAdapter.setNewData(mWashCollects);
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
