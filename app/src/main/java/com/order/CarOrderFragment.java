package com.order;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.application.MyApplication;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.costans.PlatformContans;
import com.example.yunchebao.R;
import com.google.gson.Gson;
import com.http.HttpProxy;
import com.http.ICallBack;
import com.payencai.library.util.ToastUtil;
import com.vipcenter.PubCommentActivity;

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

    private void initView() {
        state = getArguments().getInt("state");
        mCarOrders = new ArrayList<>();
        mCarOrderAdapter = new CarOrderAdapter(R.layout.item_car_order, mCarOrders);
        mCarOrderAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                page++;
                isLoadMore = true;
                getWashOrder();
            }
        }, rv_order);
        mCarOrderAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                CarOrder carOrder = (CarOrder) adapter.getItem(position);
                if (carOrder.getFlag() == 1) {//洗护宝订单
                    if (carOrder.getState() == 2) {
                        washCancel(carOrder.getId());
                    } else if (carOrder.getState() == 3) {
                        Intent intent = new Intent(getContext(), PubCommentActivity.class);
                        intent.putExtra("id", carOrder.getId());
                        startActivity(intent);
                    }
                } else if (carOrder.getFlag() == 2) {//车易宝订单
                    if (carOrder.getState() == 2) {
                        carCancel(carOrder.getId());
                    } else if (carOrder.getState() == 3) {

                    }
                }
            }
        });
        rv_order.setLayoutManager(new LinearLayoutManager(getContext()));
        rv_order.setAdapter(mCarOrderAdapter);
        getWashOrder();

    }

    private void washCancel(String id) {
        Map<String, Object> params = new HashMap<>();
        params.put("id", id);
        HttpProxy.obtain().post(PlatformContans.CarWashRepairShop.cancelWashRepairOrder, MyApplication.getUserInfo().getToken(), params, new ICallBack() {
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
        HttpProxy.obtain().post(PlatformContans.CarOrder.cancelCarOrder, MyApplication.getUserInfo().getToken(), params, new ICallBack() {
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

    private void getWashOrder() {
        Map<String, Object> params = new HashMap<>();
        params.put("state", state);
        params.put("page", page);
        HttpProxy.obtain().get(PlatformContans.Order.getUserOrderList, params, MyApplication.getUserInfo().getToken(), new ICallBack() {
            @Override
            public void OnSuccess(String result) {
                Log.e("result", result);
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    int code = jsonObject.getInt("resultCode");
                    if (code == 0) {
                        JSONArray data = jsonObject.getJSONArray("data");
                        List<CarOrder> mCarOrder = new ArrayList<>();
                        for (int i = 0; i < data.length(); i++) {
                            JSONObject item = data.getJSONObject(i);
                            CarOrder carOrder = new Gson().fromJson(item.toString(), CarOrder.class);
                            carOrder.setFlag(1);
                            mCarOrder.add(carOrder);
                        }
                        getCarOrder(mCarOrder);
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

    private void getCarOrder(List<CarOrder> carOrderList) {
        Map<String, Object> params = new HashMap<>();
        params.put("state", state);
        params.put("page", page);
        HttpProxy.obtain().get(PlatformContans.Order.getUserCarOrder, params, MyApplication.getUserInfo().getToken(), new ICallBack() {
            @Override
            public void OnSuccess(String result) {
                Log.e("result", result);
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    int code = jsonObject.getInt("resultCode");
                    if (code == 0) {
                        JSONArray data = jsonObject.getJSONArray("data");
                        List<CarOrder> mCarOrder = new ArrayList<>();
                        for (int i = 0; i < data.length(); i++) {
                            JSONObject item = data.getJSONObject(i);
                            CarOrder carOrder = new Gson().fromJson(item.toString(), CarOrder.class);
                            carOrder.setFlag(2);
                            if (isLoadMore) {
                                mCarOrder.add(carOrder);
                            } else {
                                carOrderList.add(carOrder);
                            }
                        }
                        if (isLoadMore) {
                            isLoadMore = false;
                            mCarOrderAdapter.addData(0, carOrderList);
                            mCarOrderAdapter.addData(mCarOrder);
                            if (data.length() > 0)
                                mCarOrderAdapter.loadMoreComplete();
                            else {
                                mCarOrderAdapter.loadMoreEnd(true);
                            }
                        } else {
                            mCarOrderAdapter.setNewData(carOrderList);
                            // orderAdapter.loadMoreEnd(true);
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
}
