package com.xihubao;

import android.content.Intent;
import android.location.Location;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.UiSettings;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.MyLocationStyle;
import com.example.yunchebao.MyApplication;

import com.bumptech.glide.Glide;
import com.costans.PlatformContans;
import com.example.yunchebao.R;
import com.google.gson.Gson;
import com.http.HttpProxy;
import com.http.ICallBack;
import com.example.yunchebao.rongcloud.model.CarShop;
import com.tool.ActivityAnimationUtils;
import com.tool.ActivityConstans;
import com.vipcenter.RegisterActivity;
import com.xihubao.adapter.MapPagerAdapter;
import com.xihubao.model.MapShop;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MapShopActivity extends AppCompatActivity {
    @BindView(R.id.mapview)
    MapView mMapView;
    @BindView(R.id.id_viewpager)
    ViewPager mViewPager;
    MapPagerAdapter mMapPagerAdapter;
    List<View> mViews = new ArrayList<>();
    List<MapShop> mMapShops = new ArrayList<>();
    AMap mAMap;
    private UiSettings mUiSettings;//定义一个UiSettings对象
    MyLocationStyle myLocationStyle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_shop);
        ButterKnife.bind(this);
        mMapView.onCreate(savedInstanceState);
        initView();
    }

    private void initView() {
        initMapView();
        getData();
    }

    private void initMapView() {
        mAMap = mMapView.getMap();
        mAMap.moveCamera(CameraUpdateFactory.zoomTo(15));
        mUiSettings = mAMap.getUiSettings();
        mUiSettings.setZoomControlsEnabled(false);
        mUiSettings.setScaleControlsEnabled(false);
        myLocationStyle = new MyLocationStyle();//初始化定位蓝点样式类myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE);//连续定位、且将视角移动到地图中心点，定位点依照设备方向旋转，并且会跟随设备移动。（1秒1次定位）如果不设置myLocationType，默认也会执行此种模式。
        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATE);//只定位一次。
        myLocationStyle.showMyLocation(true);
        //myLocationStyle.interval(3000); //设置连续定位模式下的定位间隔，只在连续定位模式下生效，单次定位模式下不会生效。单位为毫秒。
        mAMap.setMyLocationStyle(myLocationStyle);//设置定位蓝点的Style
        mAMap.getUiSettings().setMyLocationButtonEnabled(true);
        mAMap.setMyLocationEnabled(true);// 设置为true表示启动显示定位蓝点，false表示隐藏定位蓝点并不进行定位，默认是false。
        mAMap.setOnMyLocationChangeListener(new AMap.OnMyLocationChangeListener() {
            @Override
            public void onMyLocationChange(Location location) {
                Log.e("locate", location.getLatitude() + "," + location.getLongitude());
            }
        });
        mAMap.setOnMarkerClickListener(new AMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                mViewPager.setCurrentItem(Integer.parseInt(marker.getTitle()));
                return true;
            }
        });
    }

    private void getData() {
        Map<String, Object> params = new HashMap<>();
        params.put("type", 1);
        params.put("longitude", MyApplication.getaMapLocation().getLongitude());
        params.put("latitude", MyApplication.getaMapLocation().getLatitude());
        params.put("city", MyApplication.getaMapLocation().getCity());
        HttpProxy.obtain().get(PlatformContans.Commom.findCarWashRepairShopList, params,"", new ICallBack() {
            @Override
            public void OnSuccess(String result) {
                Log.e("getmap", result);
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    JSONArray data = jsonObject.getJSONArray("data");
                    for (int i = 0; i < data.length(); i++) {
                        JSONObject item = data.getJSONObject(i);
                        MapShop baikeItem = new Gson().fromJson(item.toString(), MapShop.class);
                        mMapShops.add(baikeItem);
                    }
                    iniViewPager();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(String error) {

            }
        });


}

    private void iniViewPager() {
        for (int i = 0; i <mMapShops.size() ; i++) {
            View view= LayoutInflater.from(this).inflate(R.layout.item_map_shop,null);
            view.setTag(i);
            ImageView imageView= (ImageView) view.findViewById(R.id.img);
            TextView title= (TextView) view.findViewById(R.id.item1);
            TextView comment= (TextView) view.findViewById(R.id.item2);
            TextView time= (TextView) view.findViewById(R.id.item5);
            TextView address= (TextView) view.findViewById(R.id.address);
            TextView dis= (TextView) view.findViewById(R.id.dis);
            MapShop mapShop=mMapShops.get(i);
            Glide.with(this).load(mapShop.getLogo()).into(imageView);
            title.setText(mapShop.getShopName());
            address.setText(mapShop.getAddress());
            dis.setText(mapShop.getDistance()+"km");
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle bundle=new Bundle();
                    CarShop carShop=new CarShop();
                    carShop.setAddress(mapShop.getAddress());
                    carShop.setBanner(mapShop.getBanner());
                    carShop.setCity(mapShop.getCity());
                    carShop.setCreateTime(mapShop.getCreateTime());
                    carShop.setDistance(mapShop.getDistance());
                    carShop.setId(mapShop.getId());
                    carShop.setOrderNum(mapShop.getOrderNum());
                    carShop.setPrice(mapShop.getPrice());
                    carShop.setSaleTelephone(mapShop.getSaleTelephone());
                    carShop.setGrade(mapShop.getGrade());
                    carShop.setShopName(mapShop.getShopName());
                    carShop.setRemark(mapShop.getRemark());
                    carShop.setLogo(mapShop.getLogo());
                    carShop.setLatitude(mapShop.getLatitude());
                    carShop.setLongitude(mapShop.getLongitude());
                    carShop.setArea(mapShop.getArea());
                    bundle.putSerializable("id",carShop);
                    bundle.putString("type","洗车店");
                    bundle.putInt("flag",1);
                    if(MyApplication.isLogin)
                        ActivityAnimationUtils.commonTransition(MapShopActivity.this, WashCarDetailActivity.class, ActivityConstans.Animation.FADE,bundle);
                    else{
                        startActivity(new Intent(MapShopActivity.this, RegisterActivity.class));
                    }
                }
            });
            comment.setText("评价"+mapShop.getScore()+" | 订单"+mapShop.getOrderNum());
           // time.setText(mapShop.getAmStart()+"-"+mapShop.getPmStop());
            mViews.add(view);
        }

        mMapPagerAdapter=new MapPagerAdapter(this,mViews);
        mViewPager.setAdapter(mMapPagerAdapter);
        if(mMapShops.size()>1){
            mViewPager.setCurrentItem(1);
        }
        for (int i = 0; i <mMapShops.size() ; i++) {
            MapShop mapShop=mMapShops.get(i);
            MarkerOptions markerOption = new MarkerOptions();
            markerOption.position(new LatLng(Double.parseDouble(mapShop.getLatitude()),
                    Double.parseDouble(mapShop.getLongitude())));
            markerOption.draggable(false);//设置Marker可拖动
            markerOption.title(String.valueOf(i));
            markerOption.icon(com.amap.api.maps.model.BitmapDescriptorFactory.fromResource(R.mipmap.redlocate));
            mAMap.addMarker(markerOption);
        }
    }
    private View getIcon(){
        View view=null;
        return view;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，销毁地图
        mMapView.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView.onResume ()，重新绘制加载地图
        mMapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView.onPause ()，暂停地图的绘制
        mMapView.onPause();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //在activity执行onSaveInstanceState时执行mMapView.onSaveInstanceState (outState)，保存地图当前的状态
        mMapView.onSaveInstanceState(outState);
    }
}
