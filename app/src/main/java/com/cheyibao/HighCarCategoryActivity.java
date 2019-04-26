package com.cheyibao;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.cheyibao.adapter.HighCarCategoryAdapter;
import com.cheyibao.model.HighCarCategory;
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
import com.lljjcoder.style.citythreelist.AreaActivity;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.vipcenter.adapter.HistoryListAdapter;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class HighCarCategoryActivity extends AppCompatActivity {

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

    private HighCarCategoryAdapter adapter ;
    private int page = 1;

    private Activity context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_high_car_category);
        context = this;
        ButterKnife.bind(this);
        title.setText("高端自驾");

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new HighCarCategoryAdapter(new ArrayList<>());
        adapter.bindToRecyclerView(recyclerView);
        loadDataType.initData();

        refreshLayout.setOnLoadMoreListener(refreshLayout -> loadDataType.loadMoreData());

        refreshLayout.setOnRefreshListener(refreshLayout -> loadDataType.refreshData());

        adapter.setOnItemClickListener((adapter, view, position) -> {
            HighCarCategory highCarCategory = (HighCarCategory) adapter.getItem(position);
            if (highCarCategory!=null){
                HighCarModelsActivity.startActivity(highCarCategory,context);
            }
        });
    }

    private LoadDataType loadDataType = new LoadDataType() {
        @Override
        public Map<String, Object> initParam() {
            Map<String,Object> map = new HashMap<>();
            map.put("page",page);
            return map;
        }

        @Override
        public void initData() {
            page = 1 ;
            HttpProxy.obtain().get(PlatformContans.CarRent.getHighCarCategoryListForApp, initParam(), new ICallBack() {
                @Override
                public void OnSuccess(String result) {
                    Type type = new TypeToken<BaseModel<List<HighCarCategory>>>(){}.getType();
                    HandlerData.handlerData(result, type, new EndLoadDataType<List<HighCarCategory>>() {
                        @Override
                        public void onFailed() {
                            multipleStatusView.showError();
                        }

                        @Override
                        public void onSuccess(List<HighCarCategory> highCarCategories) {
                            if (highCarCategories==null || highCarCategories.size()<1){
                                multipleStatusView.showEmpty();
                            }else {
                                multipleStatusView.showContent();
                                adapter.setNewData(highCarCategories);
                            }
                        }

                        @Override
                        public void onSuccessBaseModel(BaseModel baseModel) {
                            super.onSuccessBaseModel(baseModel);
                            if (baseModel.getResultCode()!=0){
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
            HttpProxy.obtain().get(PlatformContans.CarRent.getHighCarCategoryListForApp, initParam(), new ICallBack() {
                @Override
                public void OnSuccess(String result) {
                    Type type = new TypeToken<BaseModel<List<HighCarCategory>>>(){}.getType();
                    HandlerData.handlerData(result, type, new EndLoadDataType<List<HighCarCategory>>() {
                        @Override
                        public void onFailed() {
                            refreshLayout.finishLoadMore(false);
                        }

                        @Override
                        public void onSuccess(List<HighCarCategory> highCarCategories) {
                            refreshLayout.finishLoadMore(true);
                            if (highCarCategories!=null && highCarCategories.size()>0){
                                multipleStatusView.showContent();
                                adapter.addData(highCarCategories);
                            }
                        }

                        @Override
                        public void onSuccessBaseModel(BaseModel baseModel) {
                            super.onSuccessBaseModel(baseModel);
                            if (baseModel.getResultCode()!=0){
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
            HttpProxy.obtain().get(PlatformContans.CarRent.getHighCarCategoryListForApp, initParam(), new ICallBack() {
                @Override
                public void OnSuccess(String result) {
                    Type type = new TypeToken<BaseModel<List<HighCarCategory>>>(){}.getType();
                    HandlerData.handlerData(result, type, new EndLoadDataType<List<HighCarCategory>>() {
                        @Override
                        public void onFailed() {
                            refreshLayout.finishRefresh(false);
                        }

                        @Override
                        public void onSuccess(List<HighCarCategory> highCarCategories) {
                            refreshLayout.finishRefresh(true);
                            if (highCarCategories!=null && highCarCategories.size()>0){
                                multipleStatusView.showContent();
                                adapter.setNewData(highCarCategories);
                            }
                        }

                        @Override
                        public void onSuccessBaseModel(BaseModel baseModel) {
                            super.onSuccessBaseModel(baseModel);
                            if (baseModel.getResultCode()!=0){
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
