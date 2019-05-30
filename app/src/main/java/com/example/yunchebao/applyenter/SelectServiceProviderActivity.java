package com.example.yunchebao.applyenter;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.example.yunchebao.MyApplication;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.costans.PlatformContans;
import com.example.yunchebao.R;
import com.google.gson.Gson;
import com.gyf.immersionbar.ImmersionBar;
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

public class SelectServiceProviderActivity extends AppCompatActivity {
    @BindView(R.id.rv_agency)
    RecyclerView rv_agency;
    AgencyAdapter mAgencyAdapter;
    List<Agency> mAgencies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_service_provider);
        ButterKnife.bind(this);
        ImmersionBar.with(this).autoDarkModeEnable(true).fitsSystemWindows(true).statusBarColor(R.color.white).init();
        mAgencies = new ArrayList<>();
        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        getAgencyLists(MyApplication.getaMapLocation().getCity());
    }

    /**
     * 获取代理商列表
     *
     * @param cityname 定位得到的当前城市
     */
    private void getAgencyLists(String cityname) {
        Map<String, Object> params = new HashMap<>();
        params.put("city", cityname);
        HttpProxy.obtain().get(PlatformContans.Agency.getAgencyList, params, MyApplication.token, new ICallBack() {
            @Override
            public void OnSuccess(String result) {
                Log.e("result", result);
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    JSONArray data = jsonObject.getJSONArray("data");
                    if (data != null && data.length() > 0) {
                        for (int i = 0; i < data.length(); i++) {
                            JSONObject item = data.getJSONObject(i);
                            Agency agencyInfo = new Gson().fromJson(item.toString(), Agency.class);
                            mAgencies.add(agencyInfo);
                        }
                        initData();
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

    private void initData() {
        mAgencyAdapter = new AgencyAdapter(mAgencies);
        mAgencyAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                Agency agency = (Agency) adapter.getItem(position);
                if (view.getId() == R.id.select) {
                    Intent intent = new Intent();
                    intent.putExtra("data", agency);
                    setResult(0, intent);
                    finish();
                }
            }
        });
        mAgencyAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Agency agency = (Agency) adapter.getItem(position);
                Intent intent = new Intent(SelectServiceProviderActivity.this, AgencyDetailActivity.class);
                intent.putExtra("data", agency);
                startActivity(intent);

            }
        });
        rv_agency.setLayoutManager(new LinearLayoutManager(this));
        rv_agency.setAdapter(mAgencyAdapter);
    }
}
