package com.cheyibao.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cheyibao.adapter.RentShopCommentAdapter;
import com.cheyibao.adapter.RvCommentAdapter;
import com.cheyibao.model.RentShop;
import com.cheyibao.model.RentShopComment;
import com.cheyibao.model.ShopComment;
import com.cheyibao.util.Const;
import com.common.BaseModel;
import com.costans.PlatformContans;
import com.example.yunchebao.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.http.HttpProxy;
import com.http.ICallBack;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class RentShopComentFragment extends Fragment {

    private List<ShopComment> list;
    private RentShopCommentAdapter adapter;
    @BindView(R.id.id_stickynavlayout_innerscrollview)
    RecyclerView listView;
    int page = 1;
    boolean isLoadMore=false;
    private RentShop rentShop;
    public RentShopComentFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_rent_coment, container, false);
        ButterKnife.bind(this, view);
        if (Const.rentCarInfo!=null){
            rentShop = (RentShop) Const.rentCarInfo.get("shop");
        }
        initView();
        return view;

    }

    private void initView() {
        adapter = new RentShopCommentAdapter(new ArrayList<>());
        listView.setLayoutManager(new LinearLayoutManager(getContext()));
        listView.setAdapter(adapter);
        getData();

    }

    public void getData() {

        Map<String, Object> params = new HashMap<>();
        params.put("page", page);
        params.put("shopId", rentShop.getId());
        HttpProxy.obtain().get(PlatformContans.CarRent.getRentCarCommentDetailsList, params, new ICallBack() {
            @Override
            public void OnSuccess(String result) {
                Log.e("getUserComment", result);
                BaseModel<List<RentShopComment>> baseModel = new Gson().fromJson(result,new TypeToken<BaseModel<List<RentShopComment>>>(){}.getType());
                if (baseModel!=null){
                    List<RentShopComment> rentShopCommentList = baseModel.getData();
                    if (rentShopCommentList!=null && rentShopCommentList.size()>9){
                        if (page==1){
                            adapter.setNewData(rentShopCommentList);
                        }else {
                            adapter.addData(rentShopCommentList);
                        }
                        if(isLoadMore){
                            isLoadMore=false;
                        }
                    }
                }
            }

            @Override
            public void onFailure(String error) {

            }
        });
    }
}
