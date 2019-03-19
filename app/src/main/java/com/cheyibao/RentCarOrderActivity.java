package com.cheyibao;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.application.MyApplication;
import com.bumptech.glide.Glide;
import com.cheyibao.model.RentCar;
import com.cheyibao.model.RentCarType;
import com.costans.PlatformContans;
import com.example.yunchebao.R;
import com.http.HttpProxy;
import com.http.ICallBack;

import org.androidannotations.annotations.App;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RentCarOrderActivity extends AppCompatActivity {
    RentCarType mRentCarType;
    RentCar mRentCar;
    @BindView(R.id.iv_car)
    ImageView iv_car;
    @BindView(R.id.tv_name1)
    TextView tv_name1;
    @BindView(R.id.tv_name2)
    TextView tv_name2;
    @BindView(R.id.tv_param)
    TextView tv_param;
    @BindView(R.id.tv_price)
    TextView tv_price;
    @BindView(R.id.tv_total)
    TextView tv_total;
    @BindView(R.id.et_name)
    EditText et_name;
    @BindView(R.id.et_phone)
    EditText et_phone;
    @BindView(R.id.et_beizhu)
    EditText et_beizhu;
    @BindView(R.id.tv_delete)
    TextView tv_delete;
    @BindView(R.id.tv_add)
    TextView tv_add;
    @BindView(R.id.tv_count)
    TextView tv_count;
    @BindView(R.id.tv_submit)
    TextView tv_submit;
    int count = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mRentCarType = (RentCarType) getIntent().getSerializableExtra("data");
        mRentCar= (RentCar) getIntent().getSerializableExtra("rent");
        setContentView(R.layout.activity_rent_car_order);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        Glide.with(RentCarOrderActivity.this).load(mRentCarType.getPhoto()).into(iv_car);
        tv_name1.setText(mRentCarType.getBrand());
        tv_name2.setText(mRentCarType.getModel());
        tv_param.setText(mRentCarType.getManualAutomatic() + "/" + mRentCarType.getSeat() + "座");
        tv_price.setText("￥" + mRentCarType.getDayPrice());
        tv_total.setText("￥" + mRentCarType.getDayPrice());
        tv_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (count > 1) {
                    count--;
                    tv_count.setText(count + "天");
                    double money = count * mRentCarType.getDayPrice();
                    tv_total.setText("￥" + money);
                }
            }
        });
        tv_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count++;
                tv_count.setText(count + "天");
                double money = count * mRentCarType.getDayPrice();
                tv_total.setText("￥" + money);
            }
        });
        tv_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(MyApplication.isLogin)
                postOrder();

            }
        });
    }
    private void postOrder(){
        Map<String,Object> params=new HashMap<>();
        params.put("commodityId",mRentCarType.getId());
        params.put("shopId",mRentCar.getId());
        params.put("shopName",mRentCar.getName());
        params.put("title",mRentCar.getName());
        params.put("image",mRentCarType.getPhoto());
        params.put("number",count);
        params.put("type",3);
        params.put("remark",et_beizhu.getEditableText().toString());
        params.put("name",et_name.getEditableText().toString());
        params.put("telephone",et_phone.getEditableText().toString());
        params.put("seat",mRentCarType.getSeat()+"");
        params.put("carCategory",mRentCarType.getModel()+"");
        HttpProxy.obtain().post(PlatformContans.CarOrder.addCarOrder, MyApplication.token, params, new ICallBack() {
            @Override
            public void OnSuccess(String result) {
                try {
                    JSONObject jsonObject=new JSONObject(result);
                    String orderId=jsonObject.getString("data");
                    Intent intent=new Intent(RentCarOrderActivity.this,CarPayActivity.class);
                    intent.putExtra("money",(mRentCarType.getDayPrice()*count)+"");
                    intent.putExtra("orderid",orderId);
                    startActivity(intent);
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
