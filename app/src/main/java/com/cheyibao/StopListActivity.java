package com.cheyibao;

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
import com.cheyibao.adapter.RentCarAdapter;
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

public class StopListActivity extends AppCompatActivity {

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
        areaAdapter = new AreaAdapter(new ArrayList<>());
        areaListView.setLayoutManager(new LinearLayoutManager(this));
        areaAdapter.bindToRecyclerView(areaListView);
        areaAdapter.setOnItemClickListener((adapter, view, position) -> {
            Area area = areaAdapter.getItem(position);
            if (area != null) {
                areaAdapter.refreshItem(position);
                if (position == 0) {
                    getShop(1, "");
                    areaNameTextView.setText("附近门店");
                } else {
                    getShop(2, area.getName());
                    areaNameTextView.setText(area.getName());
                }
            }
        });
        getJsonData();

        rentShopAdapter = new RentShopAdapter(new ArrayList<>());
        shopListView.setLayoutManager(new LinearLayoutManager(this));
        rentShopAdapter.bindToRecyclerView(shopListView);
        rentShopAdapter.setOnItemClickListener((adapter, view, position) -> {

        });
        getShop(1, "");
        areaNameTextView.setText("附近门店");
    }


    private void getJsonData() {
        List<Area> areaList = new ArrayList<>();
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
        }

    }

    private void getShop(int type, String area) {

        String result = "{\"resultCode\":0,\"message\":null,\"data\":[{\"id\":\"752edc96-d8e0-437d-ac00-ad15816e6c85\",\"shopNo\":\"2683449481\",\"name\":\"小安租车店\",\"province\":\"云南省\",\"city\":\"昆明市\",\"area\":\"西山区\",\"address\":\"润城第二大道18层\",\"createTime\":\"2019-04-11 00:00:00\",\"saleTelephone\":\"12345678910\",\"logo\":\"https://yunchebao.oss-cn-shenzhen.aliyuncs.com/image/2019041018363446\",\"grade\":1,\"longitude\":\"102.706562\",\"latitude\":\"24.996527\",\"geoHash\":\"wk3n1nkx\",\"distance\":0.03177768045101879,\"score\":0.0,\"number\":0,\"orderNum\":0,\"banner\":\"http://yunchebao.oss-cn-shenzhen.aliyuncs.com/image/2019032109541078,http://yunchebao.oss-cn-shenzhen.aliyuncs.com/image/2019032109541098,http://yunchebao.oss-cn-shenzhen.aliyuncs.com/image/2019032109541048,http://yunchebao.oss-cn-shenzhen.aliyuncs.com/image/2019032109541070,http://yunchebao.oss-cn-shenzhen.aliyuncs.com/image/2019032109541036,http://yunchebao.oss-cn-shenzhen.aliyuncs.com/image/2019032109541182,http://yunchebao.oss-cn-shenzhen.aliyuncs.com/image/2019032109541129,http://yunchebao.oss-cn-shenzhen.aliyuncs.com/image/2019032109541143,http://yunchebao.oss-cn-shenzhen.aliyuncs.com/image/2019032109541155,http://yunchebao.oss-cn-shenzhen.aliyuncs.com/image/2019032109541188\",\"isOnlineServe\":1,\"agencyId\":\"6a6a7292-1936-486f-a27b-6c6de132dbbb\"}]}";
        BaseModel<List<RentShop>> baseModel = new Gson().fromJson(result, new TypeToken<BaseModel<List<RentShop>>>() {
        }.getType());
        if (baseModel != null) {
            List<RentShop> rentShopList = baseModel.getData();
            rentShopAdapter.setNewData(rentShopList);
        }

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
                try {
                    BaseModel<List<RentShop>> baseModel = new Gson().fromJson(result,new TypeToken<BaseModel<List<RentShop>>>() {}.getType());
                    if (baseModel!=null){
                        List<RentShop> rentShopList = baseModel.getData();
                        rentShopAdapter.setNewData(rentShopList);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(String error) {

            }
        });
    }
}
