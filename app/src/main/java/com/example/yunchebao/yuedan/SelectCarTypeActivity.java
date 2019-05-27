package com.example.yunchebao.yuedan;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.costans.PlatformContans;
import com.example.yunchebao.R;
import com.example.yunchebao.yuedan.adapter.CarTypeAdapter;
import com.example.yunchebao.yuedan.model.CarCategoryDetail;
import com.example.yunchebao.yuedan.model.CarType;
import com.google.gson.Gson;
import com.gyf.immersionbar.ImmersionBar;
import com.http.HttpProxy;
import com.http.ICallBack;
import com.xihubao.model.CarBrand;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SelectCarTypeActivity extends AppCompatActivity {
    @BindView(R.id.lv_first)
    ListView lv_first;
    @BindView(R.id.lv_sec)
    ListView lv_sec;
    @BindView(R.id.lv_third)
    ListView lv_third;
    CarTypeAdapter firstAdapter;
    CarTypeAdapter secAdapter;
    CarTypeAdapter thirdAdapter;
    List<CarType> mFirstCarTypes;
    List<CarType> mSecCarTypes;
    List<CarType> mThirdCarTypes;
    int level = 1;//选中级别
    String id1;
    String id2;
    String id3;
    String name1;
    String name2;
    String name3;
    String id;
    String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_car_type);
        ButterKnife.bind(this);
        ImmersionBar.with(this).autoDarkModeEnable(true).fitsSystemWindows(true).statusBarColor(R.color.white).init();
        initView();
    }

    @Override
    public void onBackPressed() {
        if (level == 1) {
            finish();
        } else if (level == 2) {
            level = 1;
            lv_first.setVisibility(View.VISIBLE);
            lv_sec.setVisibility(View.GONE);
            lv_third.setVisibility(View.GONE);
        } else if (level == 3) {
            level = 2;
            lv_first.setVisibility(View.GONE);
            lv_sec.setVisibility(View.VISIBLE);
            lv_third.setVisibility(View.GONE);
        }
    }

    private void initView() {
        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (level == 1) {
                    finish();
                } else if (level == 2) {
                    level = 1;
                    lv_first.setVisibility(View.VISIBLE);
                    lv_sec.setVisibility(View.GONE);
                    lv_third.setVisibility(View.GONE);
                } else if (level == 3) {
                    level = 2;
                    lv_first.setVisibility(View.GONE);
                    lv_sec.setVisibility(View.VISIBLE);
                    lv_third.setVisibility(View.GONE);
                }
            }
        });
        mFirstCarTypes = new ArrayList<>();
        mSecCarTypes = new ArrayList<>();
        mThirdCarTypes = new ArrayList<>();
        firstAdapter = new CarTypeAdapter(this, mFirstCarTypes);
        secAdapter = new CarTypeAdapter(this, mSecCarTypes);
        thirdAdapter = new CarTypeAdapter(this, mThirdCarTypes);
        lv_first.setAdapter(firstAdapter);
        lv_sec.setAdapter(secAdapter);
        lv_third.setAdapter(thirdAdapter);
        lv_first.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                id1 = mFirstCarTypes.get(position).getId();
                name1 = mFirstCarTypes.get(position).getName();
                level = 2;
                lv_first.setVisibility(View.GONE);
                lv_sec.setVisibility(View.VISIBLE);
                lv_third.setVisibility(View.GONE);
                mSecCarTypes.clear();
                getSecType(mFirstCarTypes.get(position).getId());
            }
        });
        lv_sec.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                id2 = mSecCarTypes.get(position).getId();
                name2 = mSecCarTypes.get(position).getName();
                level = 3;
                lv_first.setVisibility(View.GONE);
                lv_sec.setVisibility(View.GONE);
                lv_third.setVisibility(View.VISIBLE);
                mThirdCarTypes.clear();
                getThirdType(mSecCarTypes.get(position).getId());
            }
        });
        lv_third.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                id3 = mThirdCarTypes.get(position).getId();
                name3 = mThirdCarTypes.get(position).getName();
                getDetail(mThirdCarTypes.get(position).getId());
            }
        });
        getFirstType();
    }

    private void getFirstType() {
        Map<String, Object> params = new HashMap<>();
        params.put("level", 1);
        HttpProxy.obtain().get(PlatformContans.CarCategory.getFirstCategory, params, "", new ICallBack() {
            @Override
            public void OnSuccess(String result) {
                Log.e("First", result);
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    JSONArray data = jsonObject.getJSONArray("data");
                    for (int i = 0; i < data.length(); i++) {
                        JSONObject item = data.getJSONObject(i);
                        CarType baikeItem = new Gson().fromJson(item.toString(), CarType.class);
                        mFirstCarTypes.add(baikeItem);
                    }
                    firstAdapter.notifyDataSetChanged();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(String error) {

            }
        });
    }

    private void getSecType(String id) {
        Map<String, Object> params = new HashMap<>();
        params.put("categoryId", id);
        HttpProxy.obtain().get(PlatformContans.CarCategory.getSubclass, params, "", new ICallBack() {
            @Override
            public void OnSuccess(String result) {
                Log.e("second", result);
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    JSONObject data = jsonObject.getJSONObject("data");
                    JSONArray carCategory = data.getJSONArray("carCategory");
                    for (int i = 0; i < carCategory.length(); i++) {
                        JSONObject item = carCategory.getJSONObject(i);
                        CarType baikeItem = new Gson().fromJson(item.toString(), CarType.class);
                        mSecCarTypes.add(baikeItem);
                    }
                    secAdapter.notifyDataSetChanged();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(String error) {

            }
        });
    }

    private void getThirdType(String id) {
        Map<String, Object> params = new HashMap<>();
        params.put("categoryId", id);
        HttpProxy.obtain().get(PlatformContans.CarCategory.getSubclass, params, "", new ICallBack() {
            @Override
            public void OnSuccess(String result) {

                Log.e("second", result);
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    JSONObject data = jsonObject.getJSONObject("data");
                    JSONArray carCategory = data.getJSONArray("carCategory");
                    for (int i = 0; i < carCategory.length(); i++) {
                        JSONObject item = carCategory.getJSONObject(i);
                        CarType baikeItem = new Gson().fromJson(item.toString(), CarType.class);
                        mThirdCarTypes.add(baikeItem);
                    }
                    thirdAdapter.notifyDataSetChanged();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(String error) {

            }
        });
    }

    private void getDetail(String id) {
        Map<String, Object> params = new HashMap<>();
        params.put("categoryId", id);
        HttpProxy.obtain().get(PlatformContans.CarCategory.getDetail, params,"", new ICallBack() {
            @Override
            public void OnSuccess(String result) {

                Log.e("getDetail", result);
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    int code = jsonObject.getInt("resultCode");
                    if (code == 0) {
                        JSONObject data = jsonObject.getJSONObject("data");
                        if (data != null) {
                            CarCategoryDetail carCategoryDetail = new Gson().fromJson(data.toString(), CarCategoryDetail.class);
                            Intent intent = new Intent();
                            intent.putExtra("name", name1+name2+name3);
                            intent.putExtra("logo", "");
                            intent.putExtra("id1",id1);
                            intent.putExtra("id2",id2);
                            intent.putExtra("id3",id3);
                            intent.putExtra("name1",name1);
                            intent.putExtra("name2",name2);
                            intent.putExtra("name3",name3);
                            intent.putExtra("id",carCategoryDetail.getId());
                            setResult(1, intent);
                            finish();
                        }
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(String error) {

            }
        });
    }
}
