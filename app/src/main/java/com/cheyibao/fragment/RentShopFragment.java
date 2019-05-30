package com.cheyibao.fragment;


import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.cheyibao.RentCarOrderActivity;
import com.cheyibao.RentShopDetailActivity;
import com.cheyibao.adapter.RvRentcarAdapter;
import com.cheyibao.model.RentCar;
import com.cheyibao.model.RentCarType;
import com.costans.PlatformContans;
import com.example.yunchebao.R;
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

/**
 * A simple {@link Fragment} subclass.
 */
public class RentShopFragment extends Fragment {

    private List<RentCarType> list;
    private RvRentcarAdapter adapter;
    @BindView(R.id.id_stickynavlayout_innerscrollview)
    RecyclerView lv_rentcar;
    int page = 1;
    String id;
    boolean isLoadMore = false;

    public RentShopFragment() {
        // Required empty public constructor
    }

    RentCar mRentCar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_rent_shop, container, false);
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_rent_shop, container, false);
        ButterKnife.bind(this, view);
        RentShopDetailActivity activity = (RentShopDetailActivity) getActivity();
        mRentCar = activity.getRentCar();
        initView();
        return view;

    }

    private void showDialog(RentCarType rentCarType) {
        final Dialog dialog = new Dialog(getContext(), R.style.dialog);
        View dialogView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_rentcar, null);
        TextView tv_name = (TextView) dialogView.findViewById(R.id.tv_name);
        TextView tv_auto = (TextView) dialogView.findViewById(R.id.tv_auto);
        TextView tv_model = (TextView) dialogView.findViewById(R.id.tv_model);
        TextView tv_price = (TextView) dialogView.findViewById(R.id.tv_price);
        TextView tv_submit = (TextView) dialogView.findViewById(R.id.tv_submit);
        //获得dialog的window窗口
        Window window = dialog.getWindow();
        //设置dialog在屏幕底部
        window.setGravity(Gravity.BOTTOM);
        //设置dialog弹出时的动画效果，从屏幕底部向上弹出
        window.setWindowAnimations(R.style.mypopwindow_anim_style);
        window.getDecorView().setPadding(0, 0, 0, 0);
        //获得window窗口的属性
        android.view.WindowManager.LayoutParams lp = window.getAttributes();
        //设置窗口宽度为充满全屏
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        //设置窗口高度为包裹内容
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        //将设置好的属性set回去
        window.setAttributes(lp);
        //将自定义布局加载到dialog上
        dialog.setContentView(dialogView);
        dialog.show();
        tv_name.setText(rentCarType.getBrand());
        tv_model.setText(rentCarType.getModel());
        tv_auto.setText(rentCarType.getManualAutomatic() + "/" + rentCarType.getSeat() + "座");
        tv_price.setText("￥" + rentCarType.getDayPrice());
        tv_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), RentCarOrderActivity.class);
//                intent.putExtra("data", rentCarType);
//                intent.putExtra("rent", mRentCar);
                startActivity(intent);
                dialog.dismiss();
            }
        });
    }

    private void initView() {
        list = new ArrayList<>();
        adapter = new RvRentcarAdapter(R.layout.item_rent_type, list);
        lv_rentcar.setLayoutManager(new LinearLayoutManager(getContext()));
        lv_rentcar.setAdapter(adapter);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                showDialog(list.get(position));
            }
        });
        adapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                isLoadMore = true;
                page++;
                getData();
            }
        }, lv_rentcar);
        getData();

    }

    public void getData() {
        RentShopDetailActivity activity = (RentShopDetailActivity) getActivity();
        Map<String, Object> params = new HashMap<>();
        params.put("page", page);
        params.put("state", 1);
        params.put("merchantId", activity.getRentCar().getId());
        Log.e("params", params.toString());
        HttpProxy.obtain().get(PlatformContans.CarRent.getRentCarCarList, params, new ICallBack() {
            @Override
            public void OnSuccess(String result) {
                Log.e("getRentCarCarList", result);
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    jsonObject = jsonObject.getJSONObject("data");
                    JSONArray data = jsonObject.getJSONArray("beanList");
                    for (int i = 0; i < data.length(); i++) {
                        JSONObject item = data.getJSONObject(i);
                        RentCarType baikeItem = new Gson().fromJson(item.toString(), RentCarType.class);
                        list.add(baikeItem);
                    }
                    adapter.notifyDataSetChanged();
                    if (isLoadMore) {
                        adapter.loadMoreEnd(true);
                        isLoadMore = false;
                    }
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