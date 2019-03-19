package com.cheyibao;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.application.MyApplication;
import com.bbcircle.data.ClassItem;
import com.cheyibao.model.CoachItem;
import com.cheyibao.model.DrvingSchool;
import com.costans.PlatformContans;
import com.example.yunchebao.R;
import com.http.HttpProxy;
import com.http.ICallBack;
import com.lljjcoder.style.citylist.Toast.ToastUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DrivingOrderActivity extends AppCompatActivity implements View.OnClickListener {
    @BindView(R.id.rl_class)
    RelativeLayout rl_class;
    @BindView(R.id.rl_coash)
    RelativeLayout rl_coash;
    @BindView(R.id.et_name)
    EditText et_name;
    @BindView(R.id.et_phone)
    EditText et_phone;
    @BindView(R.id.tv_submit)
    TextView tv_submit;
    @BindView(R.id.tv_schoolname)
    TextView tv_schoolname;
    @BindView(R.id.tv_selectedClass)
    TextView tv_selectedClass;
    @BindView(R.id.tv_selectedCoash)
    TextView tv_selectedCoash;
    @BindView(R.id.tv_price)
    TextView tv_price;
    @BindView(R.id.tv_class)
    TextView tv_class;
    @BindView(R.id.tv_coash)
    TextView tv_coash;

    ClassItem mClassItem;
    CoachItem mCoachItem;
    String id;
    DrvingSchool mDrvingSchool;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driving_order);
        ButterKnife.bind(this);
        id = getIntent().getStringExtra("id");
        mClassItem = (ClassItem) getIntent().getSerializableExtra("class");
        mCoachItem = (CoachItem) getIntent().getSerializableExtra("coash");
        mDrvingSchool= (DrvingSchool) getIntent().getSerializableExtra("name");
        initView();
    }

    private void initView() {
        rl_class.setOnClickListener(this);
        rl_coash.setOnClickListener(this);
        tv_submit.setOnClickListener(this);
        if(mClassItem!=null){
            tv_selectedClass.setText("已选班级: "+mClassItem.getClassName());
            tv_price.setText("价    格: ￥"+mClassItem.getClassPrice());
            tv_class.setText(mClassItem.getClassName());
        }
        tv_schoolname.setText(mDrvingSchool.getName());
        if(mCoachItem!=null){
            tv_selectedCoash.setText("已选教练: "+mCoachItem.getCoachName());
            tv_coash.setText(mCoachItem.getCoachName());
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(data!=null){
            if(requestCode==1){
                mClassItem= (ClassItem) data.getSerializableExtra("class");
                tv_selectedClass.setText("已选班级: "+mClassItem.getClassName());
                tv_price.setText("价    格: ￥"+mClassItem.getClassPrice());
                tv_class.setText(mClassItem.getClassName());
            }
            if(requestCode==2){
                mCoachItem= (CoachItem) data.getSerializableExtra("coash");
                tv_selectedCoash.setText("已选教练: "+mCoachItem.getCoachName());
                tv_coash.setText(mCoachItem.getCoachName());
            }
        }
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.rl_class:
                intent = new Intent(this, ClassSelelctActivity.class);
                intent.putExtra("id", id);
                startActivityForResult(intent, 1);
                break;
            case R.id.rl_coash:
                intent = new Intent(this, CoashSelectActivity.class);
                intent.putExtra("id", id);
                startActivityForResult(intent, 2);
                break;
            case R.id.tv_submit:
                if(MyApplication.isLogin){
                    if(mClassItem!=null&&mCoachItem!=null)
                    postOrder();
                }
                break;
        }
    }
    private void postOrder() {
        Map<String, Object> params = new HashMap<>();
        params.put("name",et_name.getEditableText().toString());//驾校
        params.put("telephone", et_phone.getEditableText().toString());//驾校名字
        params.put("shopId",mDrvingSchool.getId());//驾校
        params.put("shopName", mDrvingSchool.getName());//驾校名字
        params.put("title", mDrvingSchool.getName());//驾校名字
        params.put("image", mDrvingSchool.getLogo());//驾校图片
        params.put("number", 1);
        params.put("type", 4);
        params.put("commodityId", mClassItem.getId());
         params.put("className",mClassItem.getClassName());
        params.put("coachId", mCoachItem.getId());
        params.put("coachName", mCoachItem.getCoachName());
        HttpProxy.obtain().post(PlatformContans.CarOrder.addCarOrder, MyApplication.token, params, new ICallBack() {
            @Override
            public void OnSuccess(String result) {
                Log.e("result", result);
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    int code = jsonObject.getInt("resultCode");
                    if (code == 0) {
                        String orderId = jsonObject.getString("data");
                        Intent intent = new Intent(DrivingOrderActivity.this, CarPayActivity.class);
                        intent.putExtra("money", mClassItem.getClassPrice() + "");
                        intent.putExtra("orderid", orderId);
                        startActivity(intent);
                    }else if(code==9999){
                        ToastUtils.showLongToast(DrivingOrderActivity.this,"请先去实名认证");
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
