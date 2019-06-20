package com.example.yunchebao.maket.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.costans.PlatformContans;
import com.example.yunchebao.R;
import com.google.gson.Gson;
import com.http.HttpProxy;
import com.http.ICallBack;
import com.example.yunchebao.maket.GoodDetailActivity;
import com.example.yunchebao.maket.adapter.GoodsCommentAdapter;
import com.example.yunchebao.maket.model.GoodsComment;
import com.nohttp.sample.BaseFragment;

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
 * Created by sdhcjhss on 2017/12/9.
 */

public class GoodCommentFragment extends BaseFragment {

    GoodsCommentAdapter mGoodsCommentAdapter;
    List<GoodsComment> mGoodsComments;
    @BindView(R.id.lv_comment)
    com.example.yunchebao.maket.model.LoadMoreListView lv_comment;
    @BindView(R.id.comment1)
    TextView comment1;
    @BindView(R.id.comment2)
    TextView comment2;
    int type = 1;
    int page = 1;
    boolean isLoadMore = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.good_comment_fragment, container, false);
        ButterKnife.bind(this, rootView);
        initView();
        return rootView;
    }

    private void initView() {
        mGoodsComments = new ArrayList<>();
        mGoodsCommentAdapter = new GoodsCommentAdapter(getContext(), mGoodsComments);
        lv_comment.setAdapter(mGoodsCommentAdapter);
        lv_comment.setOnLoadMoreListener(new com.example.yunchebao.maket.model.LoadMoreListView.OnLoadMoreListener() {
            @Override
            public void onloadMore() {
                isLoadMore = true;
                page++;
                getComment(type);
            }
        });

        comment1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                type = 1;
                page=1;
                comment1.setTextColor(getResources().getColor(R.color.yellow_02));
                comment2.setTextColor(getResources().getColor(R.color.gray_99));
                mGoodsComments.clear();
                getComment(type);
            }
        });
        comment2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                type = 2;
                page=1;
                comment2.setTextColor(getResources().getColor(R.color.yellow_02));
                comment1.setTextColor(getResources().getColor(R.color.gray_99));
                mGoodsComments.clear();

                getComment(type);
            }
        });
        getComment(type);
    }

    private void getComment(int type) {

        GoodDetailActivity goodDetailActivity = (GoodDetailActivity) getActivity();
        Map<String, Object> params = new HashMap<>();
        params.put("commodityId", goodDetailActivity.getGoodList().getId());
        params.put("page", page);
        params.put("type", type);
        HttpProxy.obtain().get(PlatformContans.GoodsOrder.getGoodsComment, params, new ICallBack() {
            @Override
            public void OnSuccess(String result) {

                Log.e("getGoodsComment", result);
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    JSONArray data = jsonObject.getJSONArray("data");

                    for (int i = 0; i < data.length(); i++) {
                        JSONObject item = data.getJSONObject(i);
                        GoodsComment goodsComment = new Gson().fromJson(item.toString(), GoodsComment.class);
                        mGoodsComments.add(goodsComment);
                    }
                    mGoodsCommentAdapter.notifyDataSetChanged();
                    if (isLoadMore) {
                        isLoadMore = false;
                        lv_comment.setLoadCompleted();
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
