package com.xihubao;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.cityselect.CityListActivity;
import com.costans.PlatformContans;
import com.example.yunchebao.R;
import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.http.HttpProxy;
import com.http.ICallBack;
import com.nohttp.sample.NoHttpBaseActivity;
import com.tool.ActivityConstans;
import com.tool.UIControlUtils;
import com.xihubao.adapter.AssistanceListAdapter;
import com.xihubao.model.RoadItem;

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

/**
 * Created by sdhcjhss on 2017/12/11.
 * 道路救援列表
 */

public class RoadAssistanceListActivity extends NoHttpBaseActivity {
    public AMapLocationClient mLocationClient = null;
    //声明AMapLocationClientOption对象
    public AMapLocationClientOption mLocationOption = null;
    //声明定位回调监听器
    public AMapLocationListener mLocationListener = new AMapLocationListener() {
        @Override
        public void onLocationChanged(AMapLocation aMapLocation) {
            Log.e("locate", aMapLocation.getAddress());
            lon = aMapLocation.getLongitude();
            lat = aMapLocation.getLatitude();
            city = aMapLocation.getCity();
            rightBtn.setText(city);
            getData();
        }
    };
    @BindView(R.id.listview)
    PullToRefreshListView refreshListView;
    ListView listView;
    private AssistanceListAdapter adapter;
    private List<RoadItem> list;
    private Context ctx;
    @BindView(R.id.textBtn)
    TextView rightBtn;
    int page = 1;
    double lat;
    double lon;
    String city;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listview_newonly);
        initView();
        initLocation();
    }

    private void initLocation() {
        //初始化定位
        mLocationClient = new AMapLocationClient(getApplicationContext());
//设置定位回调监听
        mLocationClient.setLocationListener(mLocationListener);
        mLocationOption = new AMapLocationClientOption();
        mLocationOption.setOnceLocation(true);

        if (null != mLocationOption) {
            mLocationClient.setLocationOption(mLocationOption);
            mLocationClient.stopLocation();
            mLocationClient.startLocation();
        }
    }

    private void initView() {
        UIControlUtils.UITextControlsUtils.setUIText(findViewById(R.id.title), ActivityConstans.UITag.TEXT_VIEW, "道路救援");
        ButterKnife.bind(this);
        ctx = this;
        findViewById(R.id.topPanel).setVisibility(View.VISIBLE);
        rightBtn.setVisibility(View.VISIBLE);
        rightBtn.setTextColor(ContextCompat.getColor(ctx, R.color.black_33));
        rightBtn.setText("昆明");
        rightBtn.setTextSize(14);
        Drawable drawable = getResources().getDrawable(R.mipmap.arrow_down);
        drawable.setBounds(0, 0, 22, 22);
        rightBtn.setCompoundDrawablePadding(10);
        rightBtn.setCompoundDrawables(null, null, drawable, null);
        listView = refreshListView.getRefreshableView();
        listView.setDivider(getResources().getDrawable(R.color.gray_cc));
        listView.setDividerHeight(1);
        list = new ArrayList<>();
        adapter = new AssistanceListAdapter(ctx, list);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Context context=view.getContext();
                int pos=position-1;
                RoadItem phoneShopEntity= (RoadItem) adapter.getItem(pos);
                Intent intent =new Intent(context,AssistanceDetailActivity.class);
                intent.putExtra("entity",phoneShopEntity);
                context.startActivity(intent);
                //bundle.putSerializable("bean",phoneShopEntity);
                //ActivityAnimationUtils.commonTransition(RoadAssistanceListActivity.this, AssistanceDetailActivity.class, ActivityConstans.Animation.FADE,bundle);
            }
        });//加载更多
        refreshListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
            @Override
            public void onRefresh(final PullToRefreshBase<ListView> refreshView) {
                Log.e("data", "data");
                page = 1;
                refreshView.setRefreshing();
                list.clear();
                getData();
                //refreshView.setRefreshing();
            }
        });
        refreshListView.setOnLastItemVisibleListener(new PullToRefreshBase.OnLastItemVisibleListener() {
            @Override
            public void onLastItemVisible() {
                page++;
                getData();
                Log.e("data", "more");
            }
        });

    }

    private void getData() {
        Map<String, Object> params = new HashMap<>();
        params.put("page", page);
        params.put("longitude", lon);
        params.put("latitude", lat);
        params.put("city", "昆明市");
        Log.e("road",params.toString());
        HttpProxy.obtain().get(PlatformContans.RoadRescue.getRoadRescueShopListByApp, params,"", new ICallBack() {
            @Override
            public void OnSuccess(String result) {
                Log.e("road", result);
                refreshListView.onRefreshComplete();
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    //jsonObject = jsonObject.getJSONObject("data");
                    JSONArray data = jsonObject.getJSONArray("data");
                    for (int i = 0; i < data.length(); i++) {
                        JSONObject item = data.getJSONObject(i);
                        RoadItem phoneShopEntity = new Gson().fromJson(item.toString(), RoadItem.class);
                        list.add(phoneShopEntity);
                    }
                    adapter.notifyDataSetChanged();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(String error) {

            }
        });
    }

    @OnClick({R.id.back, R.id.textBtn})
    public void OnClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                onBackPressed();
                break;
            case R.id.textBtn:
                startActivityForResult(new Intent(RoadAssistanceListActivity.this, CityListActivity.class), 1);
                break;
        }
    }
}
