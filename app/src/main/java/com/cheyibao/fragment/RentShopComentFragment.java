package com.cheyibao.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.cheyibao.adapter.RentShopCommentAdapter;
import com.cheyibao.model.RentShop;
import com.cheyibao.model.RentShopComment;
import com.cheyibao.model.ShopComment;
import com.cheyibao.util.RentCarUtils;
import com.common.BaseModel;
import com.common.EndLoadDataType;
import com.common.HandlerData;
import com.common.LoadDataType;
import com.common.MultipleStatusView;
import com.costans.PlatformContans;
import com.example.yunchebao.R;
import com.google.gson.reflect.TypeToken;
import com.http.HttpProxy;
import com.http.ICallBack;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class RentShopComentFragment extends Fragment {

    @BindView(R.id.multiple_status_view)
    MultipleStatusView multipleStatusView;
    @BindView(R.id.id_stickynavlayout_innerscrollview)
    RecyclerView listView;
    int page = 1;
    private RentShop rentShop;
    private Unbinder unbind;
    private RentShopCommentAdapter adapter;

    public RentShopComentFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_rent_coment, container, false);
        unbind = ButterKnife.bind(this, view);
        if (RentCarUtils.rentCarInfo != null) {
            rentShop = (RentShop) RentCarUtils.rentCarInfo.get(RentCarUtils.RENT_CAR_INFO_SHOP);
        }
        initView();
        return view;

    }

    private void initView() {
        adapter = new RentShopCommentAdapter(new ArrayList<>());
        listView.setLayoutManager(new LinearLayoutManager(getContext()));
        listView.setAdapter(adapter);
        adapter.setOnLoadMoreListener(()->loadDataType.loadMoreData(),listView);
        loadDataType.initData();
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
            Map<String, Object> params = initParam();
            multipleStatusView.showLoading();
            HttpProxy.obtain().get(PlatformContans.CarRent.getRentCarCommentDetailsList, params, new ICallBack() {
                @Override
                public void OnSuccess(String result) {
                    Type type = new TypeToken<BaseModel<List<RentShopComment>>>() {
                    }.getType();
                    HandlerData.handlerData(result, type, new EndLoadDataType<List<RentShopComment>>() {
                        @Override
                        public void onFailed() {
                            multipleStatusView.showError();

                        }

                        @Override
                        public void onSuccess(List<RentShopComment> rentShopComments) {
                            if (rentShopComments!=null && rentShopComments.size()>0){
                                multipleStatusView.showContent();
                                adapter.setNewData(rentShopComments);
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
            Map<String, Object> params = initParam();
            HttpProxy.obtain().get(PlatformContans.CarRent.getRentCarCommentDetailsList, params, new ICallBack() {
                @Override
                public void OnSuccess(String result) {
                    Type type = new TypeToken<BaseModel<List<RentShopComment>>>() {
                    }.getType();
                    HandlerData.handlerData(result, type, new EndLoadDataType<List<RentShopComment>>() {
                        @Override
                        public void onFailed() {
                            adapter.loadMoreComplete();
                        }

                        @Override
                        public void onSuccess(List<RentShopComment> rentShopComments) {
                            if (rentShopComments!=null && rentShopComments.size()>0){
                                multipleStatusView.showContent();
                                adapter.addData(rentShopComments);
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



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbind.unbind();
    }
}
