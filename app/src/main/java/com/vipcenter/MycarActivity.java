package com.vipcenter;

import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.application.MyApplication;
import com.costans.PlatformContans;
import com.example.yunchebao.R;
import com.google.gson.Gson;
import com.http.HttpProxy;
import com.http.ICallBack;
import com.payencai.library.util.ToastUtil;
import com.wx.wheelview.adapter.ArrayWheelAdapter;
import com.wx.wheelview.widget.WheelView;
import com.xihubao.CarBrandSelectActivity;
import com.xihubao.Shop4SInfoActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MycarActivity extends AppCompatActivity {
    @BindView(R.id.tv_number)
    TextView tv_number;
    @BindView(R.id.rl_cartype)
    RelativeLayout rl_cartype;
    @BindView(R.id.tv_cartype)
    TextView tv_cartype;
    @BindView(R.id.et_bianhao)
    EditText et_bianhao;
    @BindView(R.id.et_name)
    EditText et_name;
    @BindView(R.id.et_number)
    EditText et_number;
    @BindView(R.id.tv_submit)
    TextView tv_submit;
    @BindView(R.id.back)
    ImageView back;
    String carNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mycar);
        ButterKnife.bind(this);
        initView();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            switch (requestCode) {
                case 1:
                    String brand = data.getExtras().getString("name");
                    tv_cartype.setText(brand);
                    break;

            }
        }

    }

    private void initView() {
        tv_number.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        rl_cartype.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (MyApplication.isLogin)
                    startActivityForResult(new Intent(MycarActivity.this, CarBrandSelectActivity.class), 1);
                else {
                    startActivity(new Intent(MycarActivity.this, RegisterActivity.class));
                }
            }
        });
        tv_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name=et_name.getEditableText().toString();
                String bianhao=et_bianhao.getEditableText().toString();
                String cartype=tv_cartype.getText().toString();
                carNumber=tv_number.getText().toString()+et_number.getEditableText().toString();
                if(TextUtils.isEmpty(name)){
                    return;
                }
                if(TextUtils.isEmpty(bianhao)){
                    return;
                }
                if(TextUtils.isEmpty(cartype)){
                    return;
                }
                if(TextUtils.isEmpty(carNumber)){
                    return;
                }
                submit(bianhao,cartype,carNumber,name);
            }
        });
    }
    private void submit(String fileNumber,String models,String plateNumber,String userName){
        Map<String,Object> params=new HashMap<>();
        params.put("fileNumber",fileNumber);
        params.put("models",models);
        params.put("plateNumber",plateNumber);
        params.put("userName",userName);
        String json=new Gson().toJson(params);
        HttpProxy.obtain().post(PlatformContans.Commom.adddrivingLicense, MyApplication.token, json, new ICallBack() {
            @Override
            public void OnSuccess(String result) {
                Log.e("result",result);
                try {
                    JSONObject res=new JSONObject(result);
                    int code=res.getInt("resultCode");
                    if(code==0){
                        ToastUtil.showToast(MycarActivity.this,"认证成功");
                        finish();
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
    private void showDialog() {
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_car_number, null);
        WheelView w1 = (WheelView) view.findViewById(R.id.main_wheelview);
        WheelView w2 = (WheelView) view.findViewById(R.id.sub_wheelview);
        TextView tv_cancel = (TextView) view.findViewById(R.id.tv_cancel);
        w1.setOnWheelItemSelectedListener(new WheelView.OnWheelItemSelectedListener() {
            @Override
            public void onItemSelected(int position, Object o) {

            }
        });
        w2.setOnWheelItemSelectedListener(new WheelView.OnWheelItemSelectedListener() {
            @Override
            public void onItemSelected(int position, Object o) {

            }
        });
        TextView tv_confirm = (TextView) view.findViewById(R.id.tv_confirm);
        w1.setWheelAdapter(new ArrayWheelAdapter(this)); // 文本数据源
        w1.setSkin(WheelView.Skin.Holo); // common皮肤
        w1.setWheelData(getCity());  // 数据集合
        w2.setWheelAdapter(new ArrayWheelAdapter(this)); // 文本数据源
        w2.setSkin(WheelView.Skin.Holo); // common皮肤
        w2.setWheelData(getNumber());  // 数据集合
        Dialog bottomDialog = new Dialog(this, R.style.dialog);
        bottomDialog.setContentView(view);
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        layoutParams.width = LinearLayout.LayoutParams.MATCH_PARENT;
        layoutParams.height = LinearLayout.LayoutParams.WRAP_CONTENT;
        view.setLayoutParams(layoutParams);
        bottomDialog.getWindow().setGravity(Gravity.BOTTOM);
        bottomDialog.show();
        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomDialog.dismiss();
            }
        });
        tv_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String city = (String) w1.getSelectionItem();
                String number = (String) w2.getSelectionItem();
                tv_number.setText(city + number);
                bottomDialog.dismiss();
            }
        });
    }

    /*返回城市字母缩写*/
    public static ArrayList<String> getNumber() {
        ArrayList<String> c = new ArrayList();
        c.add("A");
        c.add("B");
        c.add("C");
        c.add("D");
        c.add("E");
        c.add("F");
        c.add("G");
        c.add("H");
        c.add("I");
        c.add("J");
        c.add("K");
        c.add("L");
        c.add("M");
        c.add("N");
        c.add("O");
        c.add("P");
        c.add("Q");
        c.add("R");
        c.add("S");
        c.add("T");
        c.add("U");
        c.add("V");
        c.add("W");
        c.add("X");
        c.add("Y");
        c.add("Z");
        return c;
    }

    /*返回省份缩写的集合*/
    public static ArrayList<String> getCity() {
        ArrayList<String> a = new ArrayList();
        a.add("赣");
        a.add("粤");
        a.add("京");
        a.add("沪");
        a.add("鄂");
        a.add("湘");
        a.add("川");
        a.add("渝");
        a.add("闽");
        a.add("晋");
        a.add("黑");
        a.add("津");
        a.add("浙");
        a.add("豫");
        a.add("贵");
        a.add("青");
        a.add("琼");
        a.add("宁");
        a.add("蒙");
        a.add("吉");
        a.add("冀");
        a.add("苏");
        a.add("皖");
        a.add("桂");
        a.add("云");
        a.add("陕");
        a.add("甘");
        a.add("藏");
        a.add("新");
        a.add("辽");
        a.add("鲁");
        return a;
    }

}
