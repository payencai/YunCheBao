package com.cheyibao;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.application.MyApplication;
import com.cheyibao.adapter.AreaAdapter;
import com.cheyibao.adapter.RentShopAdapter;
import com.cheyibao.model.Area;
import com.cheyibao.model.RentShop;
import com.common.BaseModel;
import com.coorchice.library.SuperTextView;
import com.costans.PlatformContans;
import com.example.yunchebao.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.http.HttpProxy;
import com.http.ICallBack;
import com.tool.JsonUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ShopListActivity extends AppCompatActivity {

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
    @BindView(R.id.area_list_view)
    RecyclerView areaListView;
    @BindView(R.id.shop_list_view)
    RecyclerView shopListView;
    @BindView(R.id.area_name_text_view)
    TextView areaNameTextView;
    private String cityCode;
    private AreaAdapter areaAdapter;

    private RentShopAdapter rentShopAdapter;

    private int page = 1;


    List<Area> areaList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stop_list);
        ButterKnife.bind(this);
        title.setText("选择门店");
        String adcode = MyApplication.getaMapLocation().getAdCode();
        if (!TextUtils.isEmpty(adcode)) {
            cityCode = MyApplication.getaMapLocation().getAdCode().substring(0, 4) + "00";
        }

        init();

        getJsonData();

        getShop(1, "");
        areaNameTextView.setText("附近门店");
    }

    private void init(){
        areaAdapter = new AreaAdapter(new ArrayList<>());
        areaListView.setLayoutManager(new LinearLayoutManager(this));
        areaAdapter.bindToRecyclerView(areaListView);
        areaAdapter.setOnItemClickListener((adapter, view, position) -> {
            Area area = areaAdapter.getItem(position);
            areaAdapter.refreshItem(position);
            if (area != null) {
                if (position == 0) {
                    getShop(1, "");
                    areaNameTextView.setText("附近门店");
                } else {
                    getShop(2, area.getName());
                    areaNameTextView.setText(area.getName());
                }
            }
        });

        rentShopAdapter = new RentShopAdapter(new ArrayList<>());
        shopListView.setLayoutManager(new LinearLayoutManager(this));
        rentShopAdapter.bindToRecyclerView(shopListView);
        rentShopAdapter.setOnItemClickListener((adapter, view, position) -> {
            RentShop rentShop = rentShopAdapter.getItem(position);
            Intent intent = new Intent();
            intent.putExtra("rent_shop",rentShop);
            setResult(RESULT_OK,intent);
            finish();
        });
    }


    private void getJsonData() {
        Area area1 = new Area();
        area1.setName("附近门店");
        area1.setSelecting(true);
        areaList.add(area1);
        String json = JsonUtil.getJson(this, "area.json");
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray areas = jsonObject.getJSONArray(cityCode);
            Log.e("areas", areas.length() + "");
            Log.e("areas", areas.toString());
            for (int i = 0; i < areas.length(); i++) {
                JSONObject item = areas.getJSONObject(i);
                Log.e("areas", item.toString());
                Area area = new Gson().fromJson(item.toString(), Area.class);
                areaList.add(area);
            }
            areaAdapter.setNewData(areaList);
        } catch (JSONException e) {
            e.printStackTrace();

            Area area3 = new Area();
            area3.setName("五华区");
            areaList.add(area3);

            Area area2 = new Area();
            area2.setName("西山区");
            areaList.add(area2);

            Area area4 = new Area();
            area4.setName("高新区");
            areaList.add(area4);

            Area area5 = new Area();
            area5.setName("官渡区");
            areaList.add(area5);

            areaAdapter.setNewData(areaList);
        }

    }

    private void getShop(int type, String area) {

        Map<String, Object> params = new HashMap<>();
        params.put("type", type);
        params.put("page", page);

        params.put("longitude", MyApplication.getaMapLocation().getLongitude() + "");
        params.put("latitude", MyApplication.getaMapLocation().getLatitude() + "");
        params.put("area", area + "");
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
