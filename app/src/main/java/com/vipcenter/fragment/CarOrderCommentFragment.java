package com.vipcenter.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.yunchebao.MyApplication;
import com.costans.PlatformContans;
import com.example.yunchebao.R;
import com.google.gson.Gson;
import com.http.HttpProxy;
import com.http.ICallBack;
import com.vipcenter.adapter.CarCommentAdapter;
import com.vipcenter.model.CarOrderComment;

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
public class CarOrderCommentFragment extends Fragment {

    @BindView(R.id.rv_comment)
    RecyclerView rv_comment;
    List<CarOrderComment> mWashOrderComments;
    CarCommentAdapter mWashCommentAdapter;
    String orderId;

    public CarOrderCommentFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_wash_order_comment, container, false);
        ButterKnife.bind(this, view);
        orderId = getArguments().getString("id");
        initView();
        return view;
    }

    public static CarOrderCommentFragment newInstance(String id) {
        CarOrderCommentFragment washOrderCommentFragment = new CarOrderCommentFragment();
        Bundle bundle = new Bundle();
        bundle.putString("id", id);
        washOrderCommentFragment.setArguments(bundle);
        return washOrderCommentFragment;
    }

    private void initView() {
        mWashOrderComments = new ArrayList<>();
        mWashCommentAdapter = new CarCommentAdapter(R.layout.item_shop_comment, mWashOrderComments);
        rv_comment.setLayoutManager(new LinearLayoutManager(getContext()));
        rv_comment.setAdapter(mWashCommentAdapter);
        getData();
    }

    private void getData() {
        Map<String, Object> params = new HashMap<>();
        params.put("orderId", orderId);
        HttpProxy.obtain().get(PlatformContans.Order.getEvaluationByOrderId, params, MyApplication.token, new ICallBack() {
            @Override
            public void OnSuccess(String result) {
                Log.e("getUserComment", result);
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    JSONObject data = jsonObject.getJSONObject("data");
                    CarOrderComment baikeItem = new Gson().fromJson(data.toString(), CarOrderComment.class);
                    mWashOrderComments.add(baikeItem);
                    mWashCommentAdapter.setNewData(mWashOrderComments);

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
