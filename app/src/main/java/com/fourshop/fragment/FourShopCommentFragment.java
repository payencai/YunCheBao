package com.fourshop.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.application.MyApplication;
import com.costans.PlatformContans;
import com.example.yunchebao.R;
import com.http.HttpProxy;
import com.http.ICallBack;

import java.util.HashMap;
import java.util.Map;

import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class FourShopCommentFragment extends Fragment {


    public FourShopCommentFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_four_shop_comment, container, false);
        ButterKnife.bind(this,view);
        getData();
        return view;
    }
    private void getData() {
        Map<String, Object> params = new HashMap<>();
        params.put("page", 1);
        params.put("state", 1);
        HttpProxy.obtain().get(PlatformContans.FourShop.getFourShopOrderCommentList, params, MyApplication.token, new ICallBack() {
            @Override
            public void OnSuccess(String result) {
                Log.e("comment",result);
            }

            @Override
            public void onFailure(String error) {

            }
        });
    }

}
