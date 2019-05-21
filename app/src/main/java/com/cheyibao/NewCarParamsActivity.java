package com.cheyibao;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import com.cheyibao.adapter.NewCarParamsAdapter;
import com.cheyibao.list.SpreadListView;
import com.cheyibao.model.NewCarParams;
import com.costans.PlatformContans;
import com.example.yunchebao.R;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
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

public class NewCarParamsActivity extends AppCompatActivity {
    @BindView(R.id.lv_params)
    ListView mSpreadListView;
    NewCarParamsAdapter mNewCarParamsAdapter;
    List<NewCarParams.ParamBean> paramBeans;
    String id = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_car_params);
        ButterKnife.bind(this);
        ImmersionBar.with(this).autoDarkModeEnable(true).fitsSystemWindows(true).statusBarColor(R.color.white).init();
        id = getIntent().getStringExtra("param");
        paramBeans = new ArrayList<>();
        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        getParams();
    }

    private void getParams() {
        Map<String, Object> params = new HashMap<>();
        params.put("carCategoryDetailId", id);
        HttpProxy.obtain().get(PlatformContans.NewCar.getDetailParams, params, new ICallBack() {
            @Override
            public void OnSuccess(String result) {

                try {
                    JSONObject jsonObject = new JSONObject(result);
                    JSONObject data = jsonObject.getJSONObject("data");
                    JSONArray param = data.getJSONArray("param");
                    for (int i = 0; i < param.length(); i++) {
                        JSONObject param1 = param.getJSONObject(i);
                        String paramValue = param1.getString("paramValue");
                        JSONArray param2 = param1.getJSONArray("param");
                        Log.e("params", param.length()+"");
                        for (int j = 0; j < param2.length(); j++) {
                            JSONObject child = param2.getJSONObject(j);
                            NewCarParams.ParamBean paramBean = new NewCarParams.ParamBean();
                            if (child != null) {
                                paramBean.setParamName(child.getString("paramName"));
                                paramBean.setParamValue(child.getString("paramValue"));
                            }
                            if (j == 0) {
                                paramBean.setParent(paramValue);
                            }
                            paramBeans.add(paramBean);
                        }
                    }

                    mNewCarParamsAdapter = new NewCarParamsAdapter(NewCarParamsActivity.this, paramBeans);
                    mSpreadListView.setAdapter(mNewCarParamsAdapter);

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
