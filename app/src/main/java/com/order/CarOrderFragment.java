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
import com.drive.activity.PayDetailActivity;
import com.drive.model.ReplaceOrder;
import com.example.yunchebao.R;
import com.fourshop.activity.AddFourCommentActivity;
import com.fourshop.activity.SeeCommentActivity;
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
import io.rong.imageloader.utils.L;

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
    List<CarOrder> mWashOrders ;
    List<CarOrder> mFourOrders ;
    List<CarOrder> mDriversOrders;
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
        mWashOrders=new ArrayList<>();
        mFourOrders=new ArrayList<>();
        mDriversOrders=new ArrayList<>();
        mCarOrderAdapter = new CarOrderAdapter(R.layout.item_car_order, mCarOrders);
        mCarOrderAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                page++;
                isLoadMore = true;
                getWashOrder();
            }
        }, rv_order);
        mCarOrderAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                CarOrder carOrder = (CarOrder) adapter.getItem(position);
                Intent intent;
                if (carOrder.getFlag() == 1) {
                    intent = new Intent(getContext(), WashOrderDetailActivity.class);
                    intent.putExtra("data", carOrder);
                    startActivity(intent);
                } else if(carOrder.getFlag()==2){
                    if (carOrder.getType() == 3) {
                        intent = new Intent(getContext(), RentOrderDetailActivity.class);
                        intent.putExtra("data", carOrder);
                        startActivity(intent);
                    } else if (carOrder.getType() == 4) {
                        intent = new Intent(getContext(), ShoolOrderDetailActivity.class);
                        intent.putExtra("data", carOrder);
                        startActivity(intent);
                    }
                }else if(carOrder.getFlag()==3){
                        Intent intent2 =new Intent(getContext(),DriveOrderDetailActivity.class);
                        intent2.putExtra("data",carOrder);
                        startActivityForResult(intent2,3);
                }else if(carOrder.getFlag()==4){
                    intent = new Intent(getContext(), WashOrderDetailActivity.class);
                    intent.putExtra("data", carOrder);
                    startActivity(intent);
                }
            }
        });
        mCarOrderAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                CarOrder carOrder = (CarOrder) adapter.getItem(position);
                Intent intent;
                switch (view.getId()){
                    case R.id.btn_comment4:
                        switch (carOrder.getState()){
                            case 2:
                                showCancelDialog(carOrder);
                                break;
                            case 3:
                                Intent intent2 = new Intent(getContext(), AddFourCommentActivity.class);
                                intent2.putExtra("id", carOrder.getId());
                                startActivity(intent2);
                                break;
                            case 4:
                                Intent intent3 = new Intent(getContext(), SeeCommentActivity.class);
                                intent3.putExtra("id", carOrder.getId());
                                startActivity(intent3);
                                break;
                        }
                        break;
                    case R.id.tv_pjia:
                        if (carOrder.getState() == 3) {
                            Intent intent2 =new Intent(getContext(),DriverCommentActivity.class);
                            intent2.putExtra("id",carOrder.getShopId());
                            startActivityForResult(intent2,1);
                        } else {
                            //去评价
                            Intent intent2 =new Intent(getContext(),AddOrderCommentActivity.class);
                            intent2.putExtra("id",carOrder.getId());
                            startActivityForResult(intent2,2);
                        }
                        break;
                    case R.id.btn_comment:
                        if(carOrder.getState()==2){
                            showCancelDialog(carOrder);
                        }else if(carOrder.getState()==3){
                            intent = new Intent(getContext(), PubCommentActivity.class);
                            intent.putExtra("id", carOrder.getId());
                            startActivity(intent);
                        }
                        else if(carOrder.getState()==4){
                            intent = new Intent(getContext(), OrderCommentsActivity.class);
                            intent.putExtra("id", carOrder.getId());
                            intent.putExtra("type", 1);
                            startActivity(intent);
                        }
                        break;
                    case R.id.tv_cancel:
                        if(carOrder.getState()==2){
                            showCancelDialog(carOrder);
                        }else if(carOrder.getState()==3){
                            if (carOrder.getType() == 4) {
                                intent = new Intent(getContext(), AddSchoolCommentActivity.class);
                                intent.putExtra("item", carOrder);
                                startActivity(intent);
                            } else {
                                intent = new Intent(getContext(), AddRentCommentActivity.class);
                                intent.putExtra("item", carOrder);
                                startActivity(intent);
                            }
                        }
                        else if(carOrder.getState()==4){
                            intent = new Intent(getContext(), OrderCommentsActivity.class);
                            intent.putExtra("id", carOrder.getId());
                            intent.putExtra("type", 2);
                            startActivity(intent);
                        }
                         break;
                }
            }
        });
        rv_order.setLayoutManager(new LinearLayoutManager(getContext()));
        rv_order.setAdapter(mCarOrderAdapter);
        refresh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                refresh();
                refreshLayout.finishRefresh(1000);
            }
        });
        getWashOrder();

    }

    private void getFourOrder() {
        Map<String, Object> params = new HashMap<>();
        params.put("state", state);
        params.put("page", page);
        HttpProxy.obtain().get(PlatformContans.FourShop.getUserOrderList, params, MyApplication.token, new ICallBack() {
            @Override
            public void OnSuccess(String result) {
                Log.e("result", result);
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    int code = jsonObject.getInt("resultCode");
                    if (code == 0) {
                        JSONArray data = jsonObject.getJSONArray("data");
                        for (int i = 0; i < data.length(); i++) {
                            JSONObject item = data.getJSONObject(i);
                            CarOrder carOrder = new Gson().fromJson(item.toString(), CarOrder.class);
                            carOrder.setFlag(4);
                            mFourOrders.add(carOrder);
                        }
                        getCarOrder();
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
    private void getWashOrder() {
        Map<String, Object> params = new HashMap<>();
        params.put("state", state);
        params.put("page", page);
        HttpProxy.obtain().get(PlatformContans.Order.getUserOrderList, params, MyApplication.token, new ICallBack() {
            @Override
            public void OnSuccess(String result) {
                Log.e("result", result);
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    int code = jsonObject.getInt("resultCode");
                    if (code == 0) {
                        JSONArray data = jsonObject.getJSONArray("data");

                        for (int i = 0; i < data.length(); i++) {
                            JSONObject item = data.getJSONObject(i);
                            CarOrder carOrder = new Gson().fromJson(item.toString(), CarOrder.class);
                            carOrder.setFlag(1);
                            mWashOrders.add(carOrder);
                        }
                        getFourOrder();
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
    private void getDriverOrder() {
        int type=-1;
        Map<String, Object> params = new HashMap<>();
        params.put("page", page);
        if(state==3){
            params.put("isComment", 0);
        }
        params.put("state", 2);
        HttpProxy.obtain().get(PlatformContans.SubstituteDriving.getSubstituteDrivingOrderListByUser, params, MyApplication.token, new ICallBack() {
            @Override
            public void OnSuccess(String result) {
                Log.e("driver", result);
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    JSONArray data = jsonObject.getJSONArray("data");
                    List<CarOrder> carOrders=new ArrayList<>();
                    for (int i = 0; i < data.length(); i++) {
                        JSONObject item = data.getJSONObject(i);
                        CarOrder carOrder = new Gson().fromJson(item.toString(), CarOrder.class);
                        carOrder.setFlag(3);
                        mDriversOrders.add(carOrder);
                    }
                    carOrders.addAll(mWashOrders);
                    carOrders.addAll(mFourOrders);
                    carOrders.addAll(mCarOrders);
                    carOrders.addAll(mDriversOrders);
                    if (isLoadMore) {
                        isLoadMore = false;
                        mCarOrderAdapter.setNewData(carOrders);
                        if(data.length()==0){
                            mCarOrderAdapter.loadMoreEnd(true);
                        }else{
                            mCarOrderAdapter.loadMoreComplete();
                        }
                    } else {
                        mCarOrderAdapter.setNewData(carOrders);
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

    private void getCarOrder() {
        Map<String, Object> params = new HashMap<>();
        params.put("state", state);
        params.put("page", page);
        HttpProxy.obtain().get(PlatformContans.Order.getUserCarOrder, params, MyApplication.token, new ICallBack() {
            @Override
            public void OnSuccess(String result) {
                Log.e("result", result);
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    int code = jsonObject.getInt("resultCode");
                    if (code == 0) {
                        JSONArray data = jsonObject.getJSONArray("data");
                        List<CarOrder> carOrders=new ArrayList<>();
                        for (int i = 0; i < data.length(); i++) {
                            JSONObject item = data.getJSONObject(i);
                            CarOrder carOrder = new Gson().fromJson(item.toString(), CarOrder.class);
                            carOrder.setFlag(2);
                            mCarOrders.add(carOrder);
                        }
                        if(state!=2)
                            getDriverOrder();
                        else{
                            carOrders.addAll(mWashOrders);
                            carOrders.addAll(mFourOrders);
                            carOrders.addAll(mCarOrders);
                            if (isLoadMore) {
                                isLoadMore = false;
                                mCarOrderAdapter.setNewData(carOrders);
                                if(data.length()==0){
                                    mCarOrderAdapter.loadMoreEnd(true);
                                }else{
                                    mCarOrderAdapter.loadMoreComplete();
                                }
                            } else {
                                mCarOrderAdapter.setNewData(carOrders);
                            }
                        }
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
                if(carOrder.getFlag()==1)
                    washCancel(carOrder.getId());
                else if(carOrder.getFlag()==2){
                    carCancel(carOrder.getId());
                }else if(carOrder.getFlag()==4){
                    fourCancel(carOrder.getId());
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
        mWashOrders.clear();
        mFourOrders.clear();
        mDriversOrders.clear();
        mCarOrderAdapter.setNewData(mCarOrders);
        getWashOrder();
    }

    private void washCancel(String id) {
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
                        getWashOrder();
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
                        getWashOrder();
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
                        getWashOrder();
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
