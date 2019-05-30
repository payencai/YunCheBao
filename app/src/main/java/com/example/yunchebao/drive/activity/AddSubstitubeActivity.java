package com.example.yunchebao.drive.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.yunchebao.MyApplication;
import com.costans.PlatformContans;
import com.example.yunchebao.drive.model.DriveMan;
import com.example.yunchebao.R;
import com.gyf.immersionbar.ImmersionBar;
import com.http.HttpProxy;
import com.http.ICallBack;
import com.payencai.library.util.ToastUtil;
import com.system.X5WebviewActivity;
import com.system.model.AddressBean;
import com.vipcenter.RegisterActivity;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddSubstitubeActivity extends AppCompatActivity {
    DriveMan mDriveMan;
    @BindView(R.id.tv_driver)
    TextView tv_driver;
    @BindView(R.id.et_name)
    EditText et_name;
    @BindView(R.id.et_phone)
    EditText et_phone;
    @BindView(R.id.tv_here)
    TextView tv_here;
    @BindView(R.id.tv_dest)
    TextView tv_dest;
    AddressBean addrHere;
    AddressBean addrDest;
    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_substitube);
        ButterKnife.bind(this);
        ImmersionBar.with(this).autoDarkModeEnable(true).fitsSystemWindows(true).statusBarColor(R.color.white).init();
        mDriveMan = (DriveMan) getIntent().getSerializableExtra("data");
        id = getIntent().getStringExtra("id");
        initView();
    }

    private void addOrder() {
        String name = et_name.getEditableText().toString();
        String phone = et_phone.getEditableText().toString();
        Map<String, Object> params = new HashMap<>();
        params.put("name", name);
        params.put("telephone", phone);
        params.put("shopId", mDriveMan.getShopId());
        params.put("driverId", mDriveMan.getId());
        params.put("startLongitude", addrHere.getLatlng().getLng());
        params.put("startLatitude", addrHere.getLatlng().getLat());
        params.put("startAddress", addrHere.getProvince() + "" + addrHere.getCityname() + "" + addrHere.getDistrict() + addrHere.getPoiaddress());
        params.put("endLongitude", addrDest.getLatlng().getLng());
        params.put("endLatitude", addrDest.getLatlng().getLat());
        params.put("endAddress", addrDest.getProvince() + "" + addrDest.getCityname() + "" + addrDest.getDistrict() + addrDest.getPoiaddress());
        HttpProxy.obtain().post(PlatformContans.SubstituteDriving.addSubstituteDrivingOrder, MyApplication.token, params, new ICallBack() {
            @Override
            public void OnSuccess(String result) {
                Log.e("result", result);
                ToastUtil.showToast(AddSubstitubeActivity.this, "发布成功！请耐心等待商家联系~");
                finish();
            }

            @Override
            public void onFailure(String error) {

            }
        });
    }

    @OnClick({R.id.rl_here, R.id.rl_dest, R.id.tv_add, R.id.rl_driver, R.id.back})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.rl_driver:
                Intent intent = new Intent(AddSubstitubeActivity.this, DriverMoreActivity.class);
                intent.putExtra("id", id);
                startActivityForResult(intent, 3);
                break;
            case R.id.tv_add:
                if (TextUtils.isEmpty(et_name.getEditableText().toString())) {
                    ToastUtil.showToast(this, "姓名不能为空");
                    return;
                }
                if (TextUtils.isEmpty(et_phone.getEditableText().toString())) {
                    ToastUtil.showToast(this, "电话不能为空");
                    return;
                }
                if(mDriveMan==null){
                    ToastUtil.showToast(this, "司机不能为空");
                    return;
                }
                if(addrHere==null){
                    ToastUtil.showToast(this, "所在位置不能为空");
                    return;
                }
                if(addrDest==null){
                    ToastUtil.showToast(this, "目的地址不能为空");
                    return;
                }
                if(!MyApplication.isLogin){
                    startActivity(new Intent(AddSubstitubeActivity.this, RegisterActivity.class));
                    return;
                }
                addOrder();
                break;
            case R.id.rl_here:
                startActivityForResult(new Intent(AddSubstitubeActivity.this, X5WebviewActivity.class), 1);
                break;
            case R.id.rl_dest:
                startActivityForResult(new Intent(AddSubstitubeActivity.this, X5WebviewActivity.class), 2);
                break;
        }
    }

    private void initView() {
        if (mDriveMan != null) {
            tv_driver.setText(mDriveMan.getName());
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 & data != null) {
            addrHere = (AddressBean) data.getSerializableExtra("address");
            tv_here.setText(addrHere.getPoiname() + addrHere.getPoiaddress());
        }
        if (requestCode == 2 & data != null) {
            addrDest = (AddressBean) data.getSerializableExtra("address");
            tv_dest.setText(addrDest.getPoiname() + addrDest.getPoiaddress());
        }
        if (requestCode == 3 & data != null) {
            mDriveMan = (DriveMan) data.getSerializableExtra("data");
            tv_driver.setText(mDriveMan.getName());
        }
    }
}
