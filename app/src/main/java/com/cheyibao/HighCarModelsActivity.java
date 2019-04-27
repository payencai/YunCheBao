package com.cheyibao;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.cheyibao.adapter.RentCarModelAdapter;
import com.cheyibao.model.HighCarCategory;
import com.cheyibao.model.RentCar;
import com.cheyibao.model.RentCarModel;
import com.common.BaseModel;
import com.common.EndLoadDataType;
import com.common.HandlerData;
import com.common.LoadDataType;
import com.common.MultipleStatusView;
import com.coorchice.library.SuperTextView;
import com.costans.PlatformContans;
import com.example.yunchebao.R;
import com.google.gson.reflect.TypeToken;
import com.http.HttpProxy;
import com.http.ICallBack;
import com.payencai.library.util.ToastUtil;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class HighCarModelsActivity extends AppCompatActivity {

    public static void startActivity(HighCarCategory highCarCategory, Activity activity){
        Intent intent = new Intent(activity,HighCarModelsActivity.class);
        intent.putExtra("high_car_category",highCarCategory);
        activity.startActivity(intent);
    }

    public static void startActivity(HighCarCategory highCarCategory, Fragment fragment){
        Intent intent = new Intent(fragment.getContext(),HighCarModelsActivity.class);
        intent.putExtra("high_car_category",highCarCategory);
        fragment.startActivity(intent);
    }


    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.searchLay)
    LinearLayout searchLay;
    @BindView(R.id.superText)
    SuperTextView superText;
    @BindView(R.id.textBtn)
    TextView textBtn;
    @BindView(R.id.shareBtn)
    ImageView shareBtn;
    @BindView(R.id.shopCartBtn)
    ImageView shopCartBtn;
    @BindView(R.id.menuBtn)
    ImageView menuBtn;
    @BindView(R.id.userBtn)
    ImageView userBtn;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.multiple_status_view)
    MultipleStatusView multipleStatusView;
    @BindView(R.id.refresh_layout)
    SmartRefreshLayout refreshLayout;

    private HighCarCategory highCarCategory;

    private RentCarModelAdapter adapter;

    private int page;

    private Activity context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_high_car_models);
        ButterKnife.bind(this);
        context  = this;
        highCarCategory = getIntent().getParcelableExtra("high_car_category");
        if (highCarCategory!=null){
            title.setText(highCarCategory.getFirstName());
        }
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new RentCarModelAdapter(new ArrayList<>());
        adapter.bindToRecyclerView(recyclerView);
        adapter.setOnItemClickListener((a, view, position) -> {
            RentCarModel rentCarModel = adapter.getItem(position);
            HighCarModelsDetailActivity.startActivity(rentCarModel,context);
        });

        loadDataType.initData();

        refreshLayout.setOnRefreshListener(refreshLayout -> loadDataType.refreshData());
        refreshLayout.setOnLoadMoreListener(refreshLayout -> loadDataType.loadMoreData());
    }


    private LoadDataType loadDataType = new LoadDataType() {
        private Type type = new TypeToken<BaseModel<List<RentCarModel>>>(){}.getType();
        @Override
        public Map<String, Object> initParam() {
            Map<String,Object> map = new HashMap<>();
            map.put("page",page);
            map.put("brand",highCarCategory.getFirstName());
            map.put("carTategory",highCarCategory.getSecondName());
            return map;
        }

        @Override
        public void initData() {
            page = 1;
            HttpProxy.obtain().get(PlatformContans.CarRent.getRentCarCarListForHighCar, initParam(), new ICallBack() {
                @Override
                public void OnSuccess(String result) {
                    HandlerData.handlerData(result, type, new EndLoadDataType<List<RentCarModel>>() {
                        @Override
                        public void onFailed() {
                            multipleStatusView.showError();
                        }

                        @Override
                        public void onSuccess(List<RentCarModel> rentCarModels) {
                            if (rentCarModels!=null && rentCarModels.size()>0){
                                adapter.setNewData(rentCarModels);
                                multipleStatusView.showContent();
                            }else {
                                multipleStatusView.showEmpty();
                            }
                        }

                        @Override
                        public void onSuccessBaseModel(BaseModel baseModel) {
                            if (baseModel!=null && baseModel.getResultCode()!=0){
                                ToastUtil.showToast(context,baseModel.getMessage());
                                multipleStatusView.showError();
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
            HttpProxy.obtain().get(PlatformContans.CarRent.getRentCarCarListForHighCar, initParam(), new ICallBack() {
                @Override
                public void OnSuccess(String result) {
                    HandlerData.handlerData(result, type, new EndLoadDataType<List<RentCarModel>>() {
                        @Override
                        public void onFailed() {
                            refreshLayout.finishLoadMore(false);
                        }

                        @Override
                        public void onSuccess(List<RentCarModel> rentCarModels) {
                            refreshLayout.finishLoadMore(true);
                            if (rentCarModels!=null && rentCarModels.size()>0){
                                adapter.setNewData(rentCarModels);
                                multipleStatusView.showContent();
                            }
                        }

                        @Override
                        public void onSuccessBaseModel(BaseModel baseModel) {
                            if (baseModel!=null && baseModel.getResultCode()!=0){
                                ToastUtil.showToast(context,baseModel.getMessage());
                                refreshLayout.finishLoadMore(false);
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
            HttpProxy.obtain().get(PlatformContans.CarRent.getRentCarCarListForHighCar, initParam(), new ICallBack() {
                @Override
                public void OnSuccess(String result) {
                    HandlerData.handlerData(result, type, new EndLoadDataType<List<RentCarModel>>() {
                        @Override
                        public void onFailed() {
                            refreshLayout.finishRefresh(false);
                        }

                        @Override
                        public void onSuccess(List<RentCarModel> rentCarModels) {
                            refreshLayout.finishRefresh(true);
                            if (rentCarModels!=null && rentCarModels.size()>0){
                                adapter.setNewData(rentCarModels);
                                multipleStatusView.showContent();
                            }
                        }

                        @Override
                        public void onSuccessBaseModel(BaseModel baseModel) {
                            if (baseModel!=null && baseModel.getResultCode()!=0){
                                ToastUtil.showToast(context,baseModel.getMessage());
                                refreshLayout.finishRefresh(false);
                            }
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

    @OnClick(R.id.back)
    public void onViewClicked() {
        onBackPressed();
    }
}
