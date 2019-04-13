package com.cheyibao.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.cheyibao.adapter.RentCarModelAdapter;
import com.cheyibao.model.RentCarModel;
import com.cheyibao.model.RentCarType;
import com.cheyibao.model.RentShop;
import com.cheyibao.util.Const;
import com.common.BaseModel;
import com.costans.PlatformContans;
import com.example.yunchebao.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.http.HttpProxy;
import com.http.ICallBack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class RentCarModelsFragment extends Fragment {

    private RentCarModelAdapter adapter;
    @BindView(R.id.id_stickynavlayout_innerscrollview)
    RecyclerView lv_rentcar;
    int page = 1;
    String id;
    boolean isLoadMore = false;

    public RentCarModelsFragment() {
    }

    RentShop rentShop;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_rent_shop, container, false);
        ButterKnife.bind(this, view);
        if (Const.rentCarInfo!=null){
            rentShop = (RentShop) Const.rentCarInfo.get("shop");
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

        });
        adapter.setOnLoadMoreListener(() -> {
            isLoadMore = true;
            page++;
            getData();
        }, lv_rentcar);
        getData();

    }

    public void getData() {
        Map<String, Object> params = new HashMap<>();
        params.put("page", page);
        params.put("shopId", rentShop.getId());
        HttpProxy.obtain().get(PlatformContans.CarRent.getRentCarCarListByShopId, params, new ICallBack() {
            @Override
            public void OnSuccess(String result) {
                Log.e("getRentCarList", result);
                BaseModel<List<RentCarModel>> baseModel = new Gson().fromJson(result,new TypeToken<BaseModel<List<RentCarModel>>>(){}.getType());
                if (baseModel!=null){
                    List<RentCarModel> rentCarModelList = baseModel.getData();
                    if (rentCarModelList!=null && rentCarModelList.size()>0){
                        if (page==1){
                            adapter.setNewData(rentCarModelList);
                        }else {
                            adapter.addData(rentCarModelList);
                        }
                    }
                }
                if (isLoadMore) {
                    adapter.loadMoreEnd(true);
                    isLoadMore = false;
                }
            }

            @Override
            public void onFailure(String error) {

            }
        });
    }
}