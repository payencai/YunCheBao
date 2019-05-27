package com.example.yunchebao.fourshop.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.costans.PlatformContans;
import com.example.yunchebao.R;
import com.example.yunchebao.fourshop.activity.FourShopDetailActivity;
import com.example.yunchebao.fourshop.adapter.FourShopCommentAdapter;
import com.example.yunchebao.fourshop.bean.FourShopComment;
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
//import cc.shinichi.library.ImagePreview;
//import cc.shinichi.library.bean.ImageInfo;

/**
 * A simple {@link Fragment} subclass.
 */
public class FourShopCommentFragment extends Fragment {

    @BindView(R.id.rv_order)
    RecyclerView rv_order;
    FourShopCommentAdapter mFourShopCommentAdapter;
    List<FourShopComment> mFourShopComments;
    int page = 1;
    boolean isLoadMore = false;
    String id;
    FourShopDetailActivity mActivity;

    public FourShopCommentFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_four_shop_comment, container, false);
        ButterKnife.bind(this, view);
        mActivity = (FourShopDetailActivity) getActivity();
        id = mActivity.getId();
        initView();
        return view;
    }

    private void initView() {
        mFourShopComments = new ArrayList<>();

        mFourShopCommentAdapter = new FourShopCommentAdapter(R.layout.item_shop_comment, mFourShopComments);
        mFourShopCommentAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                page++;
                isLoadMore = true;
                getData();
            }
        }, rv_order);

        rv_order.setLayoutManager(new LinearLayoutManager(getContext()));
        rv_order.setAdapter(mFourShopCommentAdapter);
        getData();
    }

    private void getData() {
        Map<String, Object> params = new HashMap<>();
        params.put("page", page);
        params.put("shopId", id);
        HttpProxy.obtain().get(PlatformContans.FourShop.getFourShopOrderCommentByShopId, params, new ICallBack() {
            @Override
            public void OnSuccess(String result) {
                Log.e("getDrivingSchool", result);
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    JSONArray data = jsonObject.getJSONArray("data");
                    List<FourShopComment> replaceDrives = new ArrayList<>();
                    for (int i = 0; i < data.length(); i++) {
                        JSONObject item = data.getJSONObject(i);
                        FourShopComment replaceDrive = new Gson().fromJson(item.toString(), FourShopComment.class);
                        mFourShopComments.add(replaceDrive);
                        replaceDrives.add(replaceDrive);
                    }
                    if (isLoadMore) {
                        isLoadMore = false;
                        if (data.length() == 0) {
                            mFourShopCommentAdapter.loadMoreEnd(true);
                        } else {
                            mFourShopCommentAdapter.addData(replaceDrives);
                            mFourShopCommentAdapter.loadMoreComplete();
                        }
                    } else {
                        mFourShopCommentAdapter.setNewData(mFourShopComments);

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
