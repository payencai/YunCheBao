package com.cheyibao;

import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cheyibao.adapter.RentCarModelAdapter;
import com.cheyibao.model.RentCarModel;
import com.cheyibao.model.RentShop;
import com.cheyibao.util.RentCarUtils;
import com.common.BaseModel;
import com.coorchice.library.SuperTextView;
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
import butterknife.OnClick;

public class CarModelsListActivity extends AppCompatActivity {

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
    @BindView(R.id.select_brand_view)
    FrameLayout selectBrandView;
    @BindView(R.id.enter_price_range_view)
    FrameLayout enterPriceRangeView;
    @BindView(R.id.rent_car_list_view)
    RecyclerView rentCarListView;


    private RentShop rentShop;
    private int page = 1;

    private RentCarModelAdapter rentCarModelAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_models_list);
        ButterKnife.bind(this);
        title.setText("车型查询");
        if (RentCarUtils.rentCarInfo!=null){
            rentShop = (RentShop) RentCarUtils.rentCarInfo.get(RentCarUtils.RENT_CAR_INFO_SHOP);
            if (rentShop!=null){
                textBtn.setText(rentShop.getCity());
                textBtn.setVisibility(View.VISIBLE);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    textBtn.setTextColor(getColor(R.color.black_33));
                }else {
                    textBtn.setTextColor(getResources().getColor(R.color.black_33));
                }
            }
        }

        rentCarModelAdapter = new RentCarModelAdapter(new ArrayList<>());
        rentCarListView.setLayoutManager(new LinearLayoutManager(this));
        rentCarModelAdapter.bindToRecyclerView(rentCarListView);
        getRentCar();
    }


    private void getRentCar() {
        Map<String, Object> params = new HashMap<>();
        params.put("page", page);
        params.put("shopId",rentShop==null? "" : rentShop.getId());
        params.put("brand", "");
        params.put("minPrice","");
        params.put("maxPrice", "");
        HttpProxy.obtain().get(PlatformContans.CarRent.getRentCarCarListByShopId, params, "", new ICallBack() {
            @Override
            public void OnSuccess(String result) {
                Log.e("getdata", result);
                BaseModel<List<RentCarModel>> baseModel = new Gson().fromJson(result, new TypeToken<BaseModel<List<RentCarModel>>>() {
                }.getType());
                List<RentCarModel> rentCarModelList;
                if (baseModel != null) {
                     rentCarModelList = baseModel.getData();
                    rentCarModelAdapter.setNewData(rentCarModelList);
                }
            }

            @Override
            public void onFailure(String error) {

            }
        });
    }

    @OnClick(R.id.back)
    public void onBackClicked() {
        onBackPressed();
    }

    @OnClick(R.id.select_brand_view)
    public void onSelectBrandViewClicked() {
    }

    @OnClick(R.id.enter_price_range_view)
    public void onEnterPriceRangeViewClicked() {
    }
}
