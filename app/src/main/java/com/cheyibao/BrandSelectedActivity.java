package com.cheyibao;

import android.content.Intent;
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
import com.cheyibao.adapter.CarModelsFirstAndSubClassAdapter;
import com.cheyibao.model.CarModelsFirstLevel;
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
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class BrandSelectedActivity extends AppCompatActivity {

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

    private CarModelsFirstAndSubClassAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_brand_selected);
        ButterKnife.bind(this);
        title.setText("选择品牌");

        adapter = new CarModelsFirstAndSubClassAdapter(new ArrayList<>());
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter.bindToRecyclerView(recyclerView);
        adapter.setOnItemClickListener((a, view, position) -> {
            CarModelsFirstLevel carModelsFirstLevel = adapter.getItem(position);
            if (carModelsFirstLevel!=null){
                Intent intent = new Intent();
                intent.putExtra("brand",carModelsFirstLevel.getName());
                setResult(RESULT_OK,intent);
                finish();
            }
        });
        refreshLayout.setOnRefreshListener(refreshLayout -> loadDataType.refreshData());
        loadDataType.initData();
    }

    private LoadDataType loadDataType = new LoadDataType() {
        private Type type = new TypeToken<BaseModel<List<CarModelsFirstLevel>>>(){}.getType();
        @Override
        public Map<String, Object> initParam() {
            Map<String,Object> map = new HashMap<>();
            map.put("level",1);
            return map;
        }

        @Override
        public void initData() {
            HttpProxy.obtain().get(PlatformContans.CarCategory.getFirstCategory, initParam(), new ICallBack() {
                @Override
                public void OnSuccess(String result) {
                    HandlerData.handlerData(result, type, new EndLoadDataType<List<CarModelsFirstLevel>>() {
                        @Override
                        public void onFailed() {
                            multipleStatusView.showError();
                        }

                        @Override
                        public void onSuccess(List<CarModelsFirstLevel> carModelsFirstLevels) {
                            if (carModelsFirstLevels!=null && carModelsFirstLevels.size()>0){
                                multipleStatusView.showContent();
                                CarModelsFirstLevel carModelsFirstLevel = new CarModelsFirstLevel();
                                carModelsFirstLevel.setName("全部");
                                carModelsFirstLevels.add(0,carModelsFirstLevel);
                                adapter.setNewData(carModelsFirstLevels);
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
        public void refreshData() {
            HttpProxy.obtain().get(PlatformContans.CarCategory.getFirstCategory, initParam(), new ICallBack() {
                @Override
                public void OnSuccess(String result) {
                    HandlerData.handlerData(result, type, new EndLoadDataType<List<CarModelsFirstLevel>>() {
                        @Override
                        public void onFailed() {
                            refreshLayout.finishRefresh(false);
                            multipleStatusView.showContent();
                        }

                        @Override
                        public void onSuccess(List<CarModelsFirstLevel> carModelsFirstLevels) {
                            refreshLayout.finishRefresh(true);
                            multipleStatusView.showContent();
                            if (carModelsFirstLevels!=null && carModelsFirstLevels.size()>0){
                                CarModelsFirstLevel carModelsFirstLevel  = new CarModelsFirstLevel();
                                carModelsFirstLevel.setName("全部");
                                carModelsFirstLevels.add(0,carModelsFirstLevel);
                                adapter.setNewData(carModelsFirstLevels);
                            }
                        }
                    });
                }

                @Override
                public void onFailure(String error) {
                    refreshLayout.finishRefresh(false);
                    multipleStatusView.showContent();
                }
            });
        }
    };

    @OnClick(R.id.back)
    public void onViewClicked() {
        onBackPressed();
    }
}
