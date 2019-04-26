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
import com.cheyibao.adapter.RentCarModelAdapter;
import com.cheyibao.model.RentCarModel;
import com.cheyibao.model.RentShop;
import com.cheyibao.util.RentCarUtils;
import com.common.BaseModel;
import com.common.EndLoadDataType;
import com.common.HandlerData;
import com.common.LoadDataType;
import com.common.MultipleStatusView;
import com.costans.PlatformContans;
import com.example.yunchebao.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.http.HttpProxy;
import com.http.ICallBack;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class RentCarModelsFragment extends Fragment {

    @BindView(R.id.multiple_status_view)
    MultipleStatusView multipleStatusView;
    @BindView(R.id.id_stickynavlayout_innerscrollview)
    RecyclerView lv_rentcar;
    private Unbinder unbinder;


    private RentCarModelAdapter adapter;

    int page = 1;
    String id;

    public RentCarModelsFragment() {
    }

    RentShop rentShop;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_rent_shop, container, false);
        unbinder = ButterKnife.bind(this, view);
        if (RentCarUtils.rentCarInfo != null) {
            rentShop = (RentShop) RentCarUtils.rentCarInfo.get(RentCarUtils.RENT_CAR_INFO_SHOP);
        }
        initView();
        return view;

    }


    private void initView() {
        adapter = new RentCarModelAdapter(new ArrayList<>());
        lv_rentcar.setLayoutManager(new LinearLayoutManager(getContext()));
        lv_rentcar.setAdapter(adapter);
        adapter.bindToRecyclerView(lv_rentcar);
        adapter.setOnItemClickListener((adapter, view, position) -> {
            RentCarUtils.rentCarInfo.put(RentCarUtils.RENT_CAR_INFO_CAR_MODEL, adapter.getItem(position));
            showDialog((RentCarModel) Objects.requireNonNull(adapter.getItem(position)));
        });
        adapter.setOnLoadMoreListener(() -> loadDataType.loadMoreData(),lv_rentcar);
        loadDataType.initData();
    }

    private void showDialog(RentCarModel rentCarModel) {
        final Dialog dialog = new Dialog(getContext(), R.style.dialog);
        View dialogView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_rentcar, null);
        TextView tv_name = dialogView.findViewById(R.id.tv_name);
        TextView tv_auto = dialogView.findViewById(R.id.tv_auto);
        TextView tv_model = dialogView.findViewById(R.id.tv_model);
        TextView tv_price = dialogView.findViewById(R.id.tv_price);
        TextView tv_submit = dialogView.findViewById(R.id.tv_submit);
        //获得dialog的window窗口
        Window window = dialog.getWindow();
        //设置dialog在屏幕底部
        window.setGravity(Gravity.BOTTOM);
        //设置dialog弹出时的动画效果，从屏幕底部向上弹出
        window.setWindowAnimations(R.style.mypopwindow_anim_style);
        window.getDecorView().setPadding(0, 0, 0, 0);
        //获得window窗口的属性
        WindowManager.LayoutParams lp = window.getAttributes();
        //设置窗口宽度为充满全屏
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        //设置窗口高度为包裹内容
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        //将设置好的属性set回去
        window.setAttributes(lp);
        //将自定义布局加载到dialog上
        dialog.setContentView(dialogView);
        dialog.show();
        tv_name.setText(rentCarModel.getBrand());
        tv_model.setText(rentCarModel.getCarTategory());
        tv_auto.setText(String.format("%s/%s座", rentCarModel.getVariableBox(), rentCarModel.getSeat()));
        tv_price.setText(String.format("￥%s", rentCarModel.getDayPrice()));
        tv_submit.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), RentCarOrderActivity.class);
            startActivity(intent);
            dialog.dismiss();
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    private LoadDataType loadDataType = new LoadDataType() {
        @Override
        public Map<String, Object> initParam() {
            Map<String, Object> params = new HashMap<>();
            params.put("page", page);
            params.put("shopId", rentShop.getId());
            return params;
        }

        @Override
        public void initData() {
            page = 1;
            Map<String,Object> params = initParam();
            multipleStatusView.showLoading();
            HttpProxy.obtain().get(PlatformContans.CarRent.getRentCarCarListByShopId, params, new ICallBack() {
                @Override
                public void OnSuccess(String result) {
                    Type type = new TypeToken<BaseModel<List<RentCarModel>>>() {
                    }.getType();
                    HandlerData.handlerData(result, type, new EndLoadDataType<List<RentCarModel>>() {
                        @Override
                        public void onFailed() {
                            multipleStatusView.showError();
                        }

                        @Override
                        public void onSuccess(List<RentCarModel> rentCarModels) {
                            if (rentCarModels!=null && rentCarModels.size()>0){
                                multipleStatusView.showContent();
                                adapter.setNewData(rentCarModels);
                            }else {
                                multipleStatusView.showEmpty();
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
            Map<String,Object> params = initParam();
            HttpProxy.obtain().get(PlatformContans.CarRent.getRentCarCarListByShopId, params, new ICallBack() {
                @Override
                public void OnSuccess(String result) {
                    Type type = new TypeToken<BaseModel<List<RentCarModel>>>() {
                    }.getType();
                    HandlerData.handlerData(result, type, new EndLoadDataType<List<RentCarModel>>() {
                        @Override
                        public void onFailed() {
                            adapter.loadMoreComplete();
                        }

                        @Override
                        public void onSuccess(List<RentCarModel> rentCarModels) {
                            if (rentCarModels!=null && rentCarModels.size()>0){
                                multipleStatusView.showContent();
                                adapter.addData(rentCarModels);
                                adapter.loadMoreComplete();
                            }else {
                                adapter.loadMoreEnd();
                            }
                        }
                    });
                }

                @Override
                public void onFailure(String error) {
                    adapter.loadMoreComplete();
                }
            });
        }
    };

}