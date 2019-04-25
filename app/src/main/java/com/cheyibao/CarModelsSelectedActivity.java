package com.cheyibao;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cheyibao.adapter.CarModelsFirstAndSubClassAdapter;
import com.cheyibao.model.CarModelsFirstLevel;
import com.cheyibao.model.SubCarModels;
import com.cheyibao.util.RentCarUtils;
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

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CarModelsSelectedActivity extends AppCompatActivity {

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
    @BindView(R.id.car_model_recycler_view)
    RecyclerView carModelRecyclerView;
    @BindView(R.id.multiple_status_view)
    MultipleStatusView multipleStatusView;

    private boolean isFirstLevel = true;
    private String categoryId;

    private CarModelsFirstAndSubClassAdapter adapter;
    private Stack<List<CarModelsFirstLevel>> stack = new Stack<>();
    private SparseArray<Object> sparseArray = new SparseArray<>();
    private int level = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_models_selected);
        ButterKnife.bind(this);
        RentCarUtils.sparseArray = sparseArray;
        title.setText("选择车型");
        carModelRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new CarModelsFirstAndSubClassAdapter(new ArrayList<>());
        adapter.bindToRecyclerView(carModelRecyclerView);
        loadDataType.initData();
        adapter.setOnItemClickListener((adapter, view, position) -> {
            CarModelsFirstLevel carModelsFirstLevel = (CarModelsFirstLevel) adapter.getItem(position);
            if (carModelsFirstLevel!=null){
                sparseArray.put(sparseArray.size(),carModelsFirstLevel);
                level = carModelsFirstLevel.getLevel()+1;
                isFirstLevel = false;
                categoryId = carModelsFirstLevel.getId();
                loadDataType.initData();
            }
        });
    }

    private LoadDataType loadDataType = new LoadDataType() {
        @Override
        public Map<String, Object> initParam() {
            Map<String,Object> map = new HashMap<>();
            if (isFirstLevel){
                map.put("level",level);
            }else {
                map.put("categoryId",categoryId);
            }
            return map;
        }

        @Override
        public void initData() {
            String url = isFirstLevel? PlatformContans.CarCategory.getFirstCategory:PlatformContans.CarCategory.getSubclass;
            Map<String,Object> map = initParam();
            multipleStatusView.showLoading();
            HttpProxy.obtain().get(url, map,"",new ICallBack() {
                @Override
                public void OnSuccess(String result) {
                    Type type = isFirstLevel? new TypeToken<BaseModel<List<CarModelsFirstLevel>>>(){}.getType() : new TypeToken<BaseModel<SubCarModels>>(){}.getType();
                    EndLoadDataType endLoadDataType = isFirstLevel ?
                            new EndLoadDataType<List<CarModelsFirstLevel>>() {
                                @Override
                                public void onFailed() {
                                    multipleStatusView.showError();
                                }

                                @Override
                                public void onSuccess(List<CarModelsFirstLevel> carModelsFirstLevels) {
                                    if (carModelsFirstLevels==null || carModelsFirstLevels.size()<=0){
                                        multipleStatusView.showEmpty();
                                    }else {
                                        multipleStatusView.showContent();
                                        adapter.setNewData(carModelsFirstLevels);
                                        stack.add(carModelsFirstLevels);
                                    }

                                }
                            } :
                            new EndLoadDataType<SubCarModels>() {
                                @Override
                                public void onFailed() {
                                    multipleStatusView.showError();
                                }

                                @Override
                                public void onSuccess(SubCarModels subCarModelsBaseModel) {
                                    if (subCarModelsBaseModel!=null){
                                        if (subCarModelsBaseModel.getParam()!=null && subCarModelsBaseModel.getParam().size()>0){
                                            sparseArray.put(sparseArray.size(),subCarModelsBaseModel.getParam().get(0));
                                            finish();
                                            return;
                                        }else {
                                            List<CarModelsFirstLevel> carModelsFirstLevelList = subCarModelsBaseModel.getCarCategory();
                                            if (carModelsFirstLevelList!=null && carModelsFirstLevelList.size()>0){
                                                multipleStatusView.showContent();
                                                adapter.setNewData(carModelsFirstLevelList);
                                                stack.add(carModelsFirstLevelList);
                                                return;
                                            }
                                        }
                                        multipleStatusView.showEmpty();
                                    }
                                }
                            };
                    HandlerData.handlerData(result, type,endLoadDataType);
                }

                @Override
                public void onFailure(String error) {
                    multipleStatusView.showError();
                }
            });
        }
    };

    @OnClick(R.id.back)
    public void onViewClicked() {
        onBackPressed();
    }

    @Override
    public void onBackPressed() {
        sparseArray.remove(sparseArray.size()-1);
        if (stack !=null && stack.size()>0){
            stack.pop();
            sparseArray.remove(sparseArray.size()-1);
            if (stack.size()>0){
                List<CarModelsFirstLevel> current = stack.peek();
                if (current!=null && current.size()>0){
                    multipleStatusView.showContent();
                    adapter.setNewData(current);
                }
            }
        }else {
            super.onBackPressed();
        }
    }
}
