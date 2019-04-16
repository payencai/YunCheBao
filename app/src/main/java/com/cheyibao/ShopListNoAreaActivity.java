package com.cheyibao;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cheyibao.adapter.RentShopAdapter;
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
import com.system.model.AddressBean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ShopListNoAreaActivity extends AppCompatActivity {

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
    @BindView(R.id.shop_list_view)
    RecyclerView shopListView;

    private RentShopAdapter rentShopAdapter;
    private int page = 1;
    private AddressBean addressBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_list_no_area);
        ButterKnife.bind(this);
        title.setText("选择门店");
        rentShopAdapter = new RentShopAdapter(new ArrayList<>());
        shopListView.setLayoutManager(new LinearLayoutManager(this));
        rentShopAdapter.bindToRecyclerView(shopListView);
        rentShopAdapter.setOnItemClickListener((adapter, view, position) -> {
            RentCarUtils.rentCarInfo.put(RentCarUtils.RENT_CAR_INFO_SHOP, rentShopAdapter.getItem(position));
            Intent intent = new Intent(ShopListNoAreaActivity.this, ShopDetailActivity.class);
            startActivity(intent);
        });
        addressBean = (AddressBean) RentCarUtils.rentCarInfo.get(RentCarUtils.RENT_CAR_INFO_AREA_1);
        getShop();
    }

    private void getShop() {
        Map<String, Object> params = new HashMap<>();
        params.put("page", page);
        params.put("longitude", addressBean.getLatlng().getLng() + "");
        params.put("latitude", addressBean.getLatlng().getLat() + "");
        params.put("isOnlineServe", RentCarUtils.ONLINESERVE);
        params.put("city", addressBean.getCityname());
        HttpProxy.obtain().get(PlatformContans.CarRent.getRentCarShop, params, "", new ICallBack() {
            @Override
            public void OnSuccess(String result) {
                Log.e("getdata", result);
                BaseModel<List<RentShop>> baseModel = new Gson().fromJson(result, new TypeToken<BaseModel<List<RentShop>>>() {
                }.getType());
                if (baseModel != null) {
                    List<RentShop> rentShopList = baseModel.getData();
                    rentShopAdapter.setNewData(rentShopList);
                }
            }

            @Override
            public void onFailure(String error) {

            }
        });
    }

    @OnClick(R.id.back)
    public void onViewClicked() {
        onBackPressed();
    }
}
