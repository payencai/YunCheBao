package com.cheyibao.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.yunchebao.MyApplication;
import com.cheyibao.RentCarOrderCommentActivity;
import com.cheyibao.adapter.RentOrderAdapter;
import com.cheyibao.model.RentOrder;
import com.common.AvoidOnResult;
import com.common.BaseModel;
import com.common.ConfirmDialog;
import com.common.EndLoadDataType;
import com.common.HandlerData;
import com.common.LoadDataType;
import com.common.MultipleStatusView;
import com.costans.PlatformContans;
import com.example.yunchebao.R;
import com.example.yunchebao.myservice.SeeRentCommentActivity;
import com.google.gson.reflect.TypeToken;
import com.http.HttpProxy;
import com.http.ICallBack;
import com.nohttp.sample.BaseFragment;
import com.payencai.library.util.ToastUtil;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.vipcenter.RentOrderDetailActivity;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class SelfDriverOrderFragment extends BaseFragment {
    private static final String STATUS = "status";
    @BindView(R.id.order_list_view)
    RecyclerView orderListView;
    @BindView(R.id.multiple_status_view)
    MultipleStatusView multipleStatusView;
    @BindView(R.id.refresh_layout)
    SmartRefreshLayout refreshLayout;
    Unbinder unbinder;


    private RentOrderAdapter adapter;

    private int page;


    private int status;


    public SelfDriverOrderFragment() {
        // Required empty public constructor
    }

    public static SelfDriverOrderFragment newInstance(int status) {
        SelfDriverOrderFragment fragment = new SelfDriverOrderFragment();
        Bundle args = new Bundle();
        args.putInt(STATUS, status);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            status = getArguments().getInt(STATUS);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_self_driver_order, container, false);
        unbinder = ButterKnife.bind(this, view);
        orderListView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new RentOrderAdapter(new ArrayList<>());
        adapter.bindToRecyclerView(orderListView);

        adapter.setOnItemClickListener((adapter, view12, position) -> {
            RentOrder rentOrder = (RentOrder) adapter.getItem(position);
            Intent intent = new Intent(getContext(), RentOrderDetailActivity.class);
            intent.putExtra("id",rentOrder.getId());
            AvoidOnResult avoidOnResult = new AvoidOnResult(getActivity());
            avoidOnResult.startForResult(intent, 3, (requestCode, resultCode, data) -> loadDataType.refreshData());

        });

        adapter.setOnItemChildClickListener((adapter, view1, position) -> {
            RentOrder rentOrder = (RentOrder) adapter.getItem(position);
            switch (view1.getId()){
                case R.id.tv_job:
                    if(rentOrder.getState()==2){
                        cancelOrder(rentOrder);
                    }else if(rentOrder.getState()==3){
                        AvoidOnResult avoidOnResult = new AvoidOnResult(getActivity());
                        Intent intent = new Intent(getContext(), RentCarOrderCommentActivity.class);
                        intent.putExtra("rent_car_order_id",rentOrder==null?"":rentOrder.getId());
                        avoidOnResult.startForResult(intent, 2, (requestCode, resultCode, data) -> loadDataType.refreshData());
                    }else if(rentOrder.getState()==4){
                        AvoidOnResult avoidOnResult = new AvoidOnResult(getActivity());
                        Intent intent = new Intent(getContext(), SeeRentCommentActivity.class);
                        intent.putExtra("id",rentOrder.getId());
                        avoidOnResult.startForResult(intent, 1, (requestCode, resultCode, data) -> loadDataType.refreshData());
                    }
                    break;

            }
        });
        loadDataType.initData();

        refreshLayout.setOnRefreshListener(refreshLayout -> loadDataType.refreshData());

        refreshLayout.setOnLoadMoreListener(refreshLayout -> loadDataType.loadMoreData());

        return view;
    }

    private void cancelOrder(RentOrder rentOrder){
        if ( rentOrder!=null && ( rentOrder.getState()==1 || rentOrder.getState()==2)){
            ConfirmDialog dialog = new ConfirmDialog(getContext());
            dialog .setTitle("取消订单提示")
                    .setMessageText("确定取消订单吗？")
                    .setCancelable(true)
                    .setConfirmClickListener(v -> {
                        Map<String,Object> map = new HashMap<>();

                        map.put("id",rentOrder.getId());
                        HttpProxy.obtain().post(PlatformContans.CarRent.cancelRentCarOrder, MyApplication.token, map, new ICallBack() {
                            @Override
                            public void OnSuccess(String result) {
                                Type type = new TypeToken<BaseModel<String>>(){}.getType();
                                HandlerData.handlerData(result, type, new EndLoadDataType<String>() {
                                    @Override
                                    public void onFailed() {
                                        ToastUtil.showToast(getContext(),"订单删除失败");
                                    }

                                    @Override
                                    public void onSuccess(String s) {
                                        ToastUtil.showToast(getContext(),s);
                                        loadDataType.refreshData();
                                    }

                                    @Override
                                    public void onSuccessBaseModel(BaseModel baseModel) {
                                        if (baseModel!=null){
                                            ToastUtil.showToast(getContext(),baseModel.getMessage());
                                        }
                                    }
                                });
                            }

                            @Override
                            public void onFailure(String error) {
                                ToastUtil.showToast(getContext(),"订单删除失败");
                            }
                        });
                        dialog.dismiss();
                    })
                    .setCancelClickListener(v -> dialog.dismiss());
            dialog.show();
        }else {
            if (rentOrder==null){
                ToastUtil.showToast(getContext(),"订单不存在");
            }else {
                ToastUtil.showToast(getContext(),"当前状态不能取消订单");
            }
        }
    }

    private LoadDataType loadDataType = new LoadDataType() {
        @Override
        public Map<String, Object> initParam() {
            Map<String,Object> map = new HashMap<>();
            map.put("page",page);
            if (status!=-1){
                map.put("state",status);
            }
            return map;
        }

        @Override
        public void initData() {
            page = 1;
            HttpProxy.obtain().get(PlatformContans.CarRent.getUserRentCarOrderList, initParam(), MyApplication.token, new ICallBack() {
                @Override
                public void OnSuccess(String result) {
                    Type type = new TypeToken<BaseModel<List<RentOrder>>>(){}.getType();
                    HandlerData.handlerData(result, type, new EndLoadDataType<List<RentOrder>>() {
                        @Override
                        public void onFailed() {
                            multipleStatusView.showError();
                        }

                        @Override
                        public void onSuccess(List<RentOrder> rentOrderList) {
                            if (rentOrderList==null || rentOrderList.size()<1){
                                multipleStatusView.showEmpty();
                            }else {
                                multipleStatusView.showContent();
                                adapter.setNewData(rentOrderList);
                            }
                        }
                    });
                }

                @Override
                public void onFailure(String error) {
                    multipleStatusView.showError();
                }
            });
        }

        @Override
        public void loadMoreData() {
            page++;
            HttpProxy.obtain().get(PlatformContans.CarRent.getUserRentCarOrderList, initParam(), MyApplication.token, new ICallBack() {
                @Override
                public void OnSuccess(String result) {
                    Type type = new TypeToken<BaseModel<List<RentOrder>>>(){}.getType();
                    HandlerData.handlerData(result, type, new EndLoadDataType<List<RentOrder>>() {
                        @Override
                        public void onFailed() {
                            refreshLayout.finishLoadMore(false);
                        }

                        @Override
                        public void onSuccess(List<RentOrder> rentOrderList) {
                            if (rentOrderList==null || rentOrderList.size()<1){
                                refreshLayout.finishLoadMoreWithNoMoreData();
                            }else {
                                refreshLayout.finishLoadMore(true);
                                adapter.addData(rentOrderList);
                                multipleStatusView.showContent();
                            }
                        }
                    });
                }

                @Override
                public void onFailure(String error) {
                    refreshLayout.finishLoadMore(false);
                }
            });
        }

        @Override
        public void refreshData() {
            page = 1;
            HttpProxy.obtain().get(PlatformContans.CarRent.getUserRentCarOrderList, initParam(), MyApplication.token, new ICallBack() {
                @Override
                public void OnSuccess(String result) {
                    Type type = new TypeToken<BaseModel<List<RentOrder>>>(){}.getType();
                    HandlerData.handlerData(result, type, new EndLoadDataType<List<RentOrder>>() {
                        @Override
                        public void onFailed() {
                            refreshLayout.finishRefresh(false);
                        }

                        @Override
                        public void onSuccess(List<RentOrder> rentOrderList) {
                            refreshLayout.finishRefresh(true);
                            adapter.setNewData(rentOrderList);
                            multipleStatusView.showContent();
                        }
                    });
                }

                @Override
                public void onFailure(String error) {
                    refreshLayout.finishRefresh(false);
                }
            });
        }
    };

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
