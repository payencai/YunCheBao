package com.order;


import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.application.MyApplication;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.cheyibao.AddRentCommentActivity;
import com.cheyibao.AddSchoolCommentActivity;
import com.costans.PlatformContans;
import com.drive.activity.AddOrderCommentActivity;
import com.drive.activity.DriveOrderDetailActivity;
import com.drive.activity.DriverCommentActivity;
import com.example.yunchebao.R;
import com.example.yunchebao.fourshop.activity.AddFourCommentActivity;
import com.example.yunchebao.fourshop.activity.SeeCommentActivity;
import com.example.yunchebao.fourshop.bean.FourShopCar;
import com.google.gson.Gson;
import com.http.HttpProxy;
import com.http.ICallBack;
import com.payencai.library.util.ToastUtil;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.vipcenter.OrderCommentsActivity;
import com.vipcenter.PubCommentActivity;
import com.vipcenter.RentOrderDetailActivity;
import com.vipcenter.ShoolOrderDetailActivity;
import com.vipcenter.WashOrderDetailActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import go.error;

/**
 * A simple {@link Fragment} subclass.
 */
public class CarOrderFragment extends Fragment {
    int count = 0;
    int state = 0;
    int page = 1;
    boolean isLoadMore = false;
    @BindView(R.id.rv_order)
    RecyclerView rv_order;
    @BindView(R.id.refresh)
    SmartRefreshLayout refresh;
    List<CarOrder> mCarOrders;
    CarOrderAdapter mCarOrderAdapter;

    public static CarOrderFragment newInstance(int state) {
        CarOrderFragment orderFragment = new CarOrderFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("state", state);
        orderFragment.setArguments(bundle);
        return orderFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_car_order, container, false);
        ButterKnife.bind(this, view);
        initView();
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1||requestCode==2){
            refresh();
        }
    }

    private void initView() {
        state = getArguments().getInt("state");
        mCarOrders = new ArrayList<>();
        mCarOrderAdapter = new CarOrderAdapter(R.layout.item_service_order, mCarOrders);
        mCarOrderAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                page++;
                isLoadMore = true;
                getData();
            }
        }, rv_order);
        mCarOrderAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                CarOrder carOrder= (CarOrder) adapter.getItem(position);
                onChildClick(carOrder);
            }
        });
        rv_order.setLayoutManager(new LinearLayoutManager(getContext()));
        rv_order.setAdapter(mCarOrderAdapter);
        refresh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                refresh();
            }
        });
        getData();

    }
    private void onChildClick(CarOrder carOrder){
        Intent intent;
        if(carOrder.getType()==5){
            if(carOrder.getState()==2){
                //待评价
                intent=new Intent(getContext(),AddOrderCommentActivity.class);
                intent.putExtra("id",carOrder.getId());
                startActivity(intent);
            }else if(carOrder.getState()==3){
                //已完成
                intent=new Intent(getContext(),DriverCommentActivity.class);
                intent.putExtra("id",carOrder.getId());
                startActivity(intent);
            }
        }else{
            switch (carOrder.getType()){
                case 1://4s店
                    if(carOrder.getState()==3){
                        intent=new Intent(getContext(),AddFourCommentActivity.class);
                        intent.putExtra("id",carOrder.getId());
                        startActivity(intent);
                        //待评价
                    }else if(carOrder.getState()==4){
                        intent=new Intent(getContext(),SeeCommentActivity.class);
                        intent.putExtra("id",carOrder.getId());
                        startActivity(intent);
                        //已完成
                    }else if (carOrder.getState()==2){
                         showCancelDialog(carOrder);
                        //取消
                    }
                    break;
                case 2://洗修店
                    if(carOrder.getState()==3){

                        //待评价
                    }else if(carOrder.getState()==4){
                        //已完成
                    }else if (carOrder.getState()==2){
                        showCancelDialog(carOrder);
                        //取消
                    }
                    break;
                case 3://驾校
                    if(carOrder.getState()==3){

                        //待评价
                    }else if(carOrder.getState()==4){
                        //已完成
                    }else if (carOrder.getState()==2){

                        //取消
                    }
                    break;
                case 4://租车
                    if(carOrder.getState()==3){

                        //待评价
                    }else if(carOrder.getState()==4){
                        //已完成
                    }else if (carOrder.getState()==2){

                        //取消
                    }
                    break;
            }

        }
    }
    private void getData(){
        Map<String,Object> params=new HashMap<>();
        params.put("state",state);
        params.put("page",page);
        HttpProxy.obtain().get(PlatformContans.Commom.getMyOrderList, params,MyApplication.token, new ICallBack() {
            @Override
            public void OnSuccess(String result) {
                Log.e("getMyOrderList", result);
                refresh.finishRefresh();
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    JSONArray data = jsonObject.getJSONArray("data");
                    List<CarOrder> carOrderList=new ArrayList<>();
                    for (int i = 0; i < data.length(); i++) {
                        JSONObject item = data.getJSONObject(i);
                        CarOrder replaceDrive = new Gson().fromJson(item.toString(), CarOrder.class);
                        mCarOrders.add(replaceDrive);
                        carOrderList.add(replaceDrive);
                    }
                    if (isLoadMore) {
                        isLoadMore = false;
                        if (data.length() == 0) {
                            mCarOrderAdapter.loadMoreEnd(true);
                        } else {
                            mCarOrderAdapter.addData(carOrderList);
                            mCarOrderAdapter.loadMoreComplete();
                        }
                    } else {
                        mCarOrderAdapter.setNewData(mCarOrders);

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

    private void showCancelDialog(CarOrder carOrder) {
        Dialog dialog = new Dialog(getContext(), R.style.dialog);
        View view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_cancel_order, null);
        TextView tv_back = (TextView) view.findViewById(R.id.tv_back);
        TextView tv_confirm = (TextView) view.findViewById(R.id.tv_confirm);
        tv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        tv_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                switch (carOrder.getType()){
                    case 1:
                        fourCancel(carOrder.getId());
                        break;
                    case 2:
                        washCancel(carOrder.getId());
                        break;
                    case 3:
                        break;
                    case 4:
                        break;
                }
            }
        });
        dialog.setCanceledOnTouchOutside(false);
        dialog.setContentView(view);
        dialog.show();
        Window window = dialog.getWindow();
        WindowManager windowManager = getActivity().getWindowManager();
        WindowManager.LayoutParams layoutParams = window.getAttributes();
        Display display = windowManager.getDefaultDisplay();
        layoutParams.width = (int) (display.getWidth() * 0.8);
        layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        window.setAttributes(layoutParams);
    }

    private void refresh() {
        page = 1;
        mCarOrders.clear();
        mCarOrderAdapter.setNewData(mCarOrders);
        getData();
    }

    private void washCancel(String id){
        Map<String, Object> params = new HashMap<>();
        params.put("id", id);
        HttpProxy.obtain().post(PlatformContans.CarWashRepairShop.cancelWashRepairOrder, MyApplication.token, params, new ICallBack() {
            @Override
            public void OnSuccess(String result) {
                Log.e("result", result);
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    int code = jsonObject.getInt("resultCode");
                    if (code == 0) {
                        ToastUtil.showToast(getContext(), "取消成功");
                        page = 1;

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

    private void carCancel(String id) {
        Map<String, Object> params = new HashMap<>();
        params.put("orderId", id);
        HttpProxy.obtain().post(PlatformContans.CarOrder.cancelCarOrder, MyApplication.token, params, new ICallBack() {
            @Override
            public void OnSuccess(String result) {
                Log.e("result", result);
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    int code = jsonObject.getInt("resultCode");
                    if (code == 0) {
                        ToastUtil.showToast(getContext(), "取消成功");
                        page = 1;

                    } else {
                        String msg = jsonObject.getString("message");
                        ToastUtil.showToast(getContext(), msg);
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
    private void fourCancel(String id) {
        Map<String, Object> params = new HashMap<>();
        params.put("id", id);
        HttpProxy.obtain().post(PlatformContans.FourShop.cancelFourShopOrder, MyApplication.token, params, new ICallBack() {
            @Override
            public void OnSuccess(String result) {
                Log.e("result", result);
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    int code = jsonObject.getInt("resultCode");
                    if (code == 0) {
                        ToastUtil.showToast(getContext(), "取消成功");
                        page = 1;

                    } else {
                        String msg = jsonObject.getString("message");
                        ToastUtil.showToast(getContext(), msg);
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
