package com.xihubao.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.costans.PlatformContans;

import com.example.yunchebao.R;
import com.google.gson.Gson;
import com.http.HttpProxy;
import com.http.ICallBack;
import com.example.yunchebao.maket.model.LoadMoreListView;
import com.nohttp.sample.BaseFragment;
import com.xihubao.adapter.WashCommentAdapter;
import com.xihubao.model.WashComment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;


public class EvaluateFragment extends BaseFragment {



    private List<WashComment> list = new ArrayList<>();
    WashCommentAdapter mWashCommentAdapter;
    private View view;
    String shopid;
    boolean isLoadMore=false;
    int page=1;
    @BindView(R.id.lv_comment)
    LoadMoreListView lv_comment;

    public List<WashComment> getList() {
        return list;
    }

    public void setList(List<WashComment> list) {
        this.list = list;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_washcomment, null);
        shopid=getArguments().getString("shopId");
        ButterKnife.bind(this,view);
        mWashCommentAdapter=new WashCommentAdapter(getContext(),list);
        lv_comment.setAdapter(mWashCommentAdapter);
        lv_comment.setOnLoadMoreListener(new LoadMoreListView.OnLoadMoreListener() {
            @Override
            public void onloadMore() {
                 page++;
                 isLoadMore=true;
                 lv_comment.setLoadCompleted();
                 getData();
            }
        });
        getData();
        return view;
    }


    public static EvaluateFragment newInstance(String shopId) {
        EvaluateFragment tabFragment = new EvaluateFragment();
        Bundle bundle=new Bundle();
        bundle.putString("shopId",shopId);
        tabFragment.setArguments(bundle);
        return tabFragment;
    }
    public void getData() {

        Map<String, Object> params = new HashMap<>();
        params.put("page", page);
        params.put("shopId", shopid);
        HttpProxy.obtain().get(PlatformContans.CarWashRepairShop.getWashRepairCommentDetailsList, params, new ICallBack() {
            @Override
            public void OnSuccess(String result) {
                Log.e("getUserComment", result);
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    JSONArray data = jsonObject.getJSONArray("data");
                    for (int i = 0; i < data.length(); i++) {
                        JSONObject item = data.getJSONObject(i);
                        WashComment baikeItem = new Gson().fromJson(item.toString(), WashComment.class);
                        list.add(baikeItem);
                    }

                    mWashCommentAdapter.notifyDataSetChanged();

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

