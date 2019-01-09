package com.vipcenter;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.entity.PhoneAddressEntity;
import com.example.yunchebao.R;
import com.nohttp.sample.NoHttpBaseActivity;
import com.tool.ActivityConstans;
import com.tool.UIControlUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by fanzh on 2017/11/16.
 */

public class AddressAddActivity extends NoHttpBaseActivity {
    @BindView(R.id.address)
    TextView addrText;
    @BindView(R.id.name)
    EditText nameEdit;
    @BindView(R.id.mobile)
    EditText mobileEdit;
    @BindView(R.id.detailAddress)
    EditText detailEdit;

    private PhoneAddressEntity entity = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.address_add_layout);
        initView();
    }

    private void initView() {
        ButterKnife.bind(this);
        UIControlUtils.UITextControlsUtils.setUIText(findViewById(R.id.title), ActivityConstans.UITag.TEXT_VIEW, "新增地址");
        UIControlUtils.UITextControlsUtils.setUIText(findViewById(R.id.textBtn), ActivityConstans.UITag.TEXT_VIEW, "保存");
//        if (PlatformContans.OBJECT_MAP.get(PlatformContans.LoginContacts.ENTITY) != null && !PlatformContans.OBJECT_MAP.get(PlatformContans.LoginContacts.ENTITY).equals("") ){
//            entity = (PhoneAddressEntity) PlatformContans.OBJECT_MAP.get(PlatformContans.LoginContacts.ENTITY);
//            setForm(entity);
//        }else{
//            entity = new PhoneAddressEntity();
//        }
    }


    private void requestMethod(int type, String value) {
//        Request<String> request = null;
//        switch (type){
//            case 0:
//                request = NoHttp.createStringRequest(PlatformContans.rootUrl + PlatformContans.Urls.ShopUrls.addAdress , RequestMethod.POST);
//                request.add("province",entity.getProvince());
//                request.add("city",entity.getCity());
//                request.add("area",entity.getArea());
//                request.add("town","");
//                request.add("name",entity.getName());
//                request.add("mobile",entity.getMobile());
//                request.add("detail",entity.getDetail());
//                request(0, request, httpListener, true, true);
//                break;
//            case 1:
//                request = NoHttp.createStringRequest(PlatformContans.rootUrl + PlatformContans.Urls.ShopUrls.changeAddress , RequestMethod.PUT);
//                request.add("province",entity.getProvince());
//                request.add("city",entity.getCity());
//                request.add("area",entity.getArea());
//                request.add("town","");
//                request.add("name",entity.getName());
//                request.add("mobile",entity.getMobile());
//                request.add("detail",entity.getDetail());
//                request.add("id",entity.getId());
//                request(1, request, httpListener, true, true);
//                break;
//        }

    }

//    private HttpListener<String> httpListener = new HttpListener<String>() {
//        @Override
//        public void onSucceed(int what, Response response) {
//            String res = response.get().toString();
//            HttpJsonClient client = new HttpJsonClient();
//            client.setResp(res);
//            if (client.isSuccess()) {
//                switch (what) {
//                    case 0:
//                    case 1:
//                        setToast(client.getMsg());
//                        //数据是使用Intent返回
//                        Intent intent = new Intent();
////                        //把返回数据存入Intent
////                        intent.putExtra("result", "My name is linjiqin");
//                        //设置返回数据
//                        AddressAddActivity.this.setResult(RESULT_OK, intent);
//                        //关闭Activity
//                        AddressAddActivity.this.finish();
//                        break;
//                }
//            } else {
//                setToast(client.getMsg());
//            }
//        }
//
//        @Override
//        public void onFailed(int what, Response<String> response) {
//        }
//    };


    private boolean checkForm() {
        if (entity != null) {
            if (entity.getProvince() != null && !entity.getProvince().equals("")) {
                if (nameEdit.getText() != null && !nameEdit.getText().toString().equals("")) {
                    entity.setName(nameEdit.getText().toString());
                    if (mobileEdit.getText() != null && !mobileEdit.getText().toString().equals("")) {
                        entity.setMobile(mobileEdit.getText().toString());
                        if (detailEdit.getText() != null && !detailEdit.getText().toString().equals("")) {
                            entity.setDetail(detailEdit.getText().toString());
                        } else {
                            setToast("请输入详细地址");
                            return false;
                        }
                    } else {
                        setToast("请输入联系人电话");
                        return false;
                    }
                } else {
                    setToast("请输入联系人姓名");
                    return false;
                }
            } else {
                setToast("请选择地址");
                return false;
            }
        }
        return true;
    }


    @OnClick({R.id.back, R.id.addressLay, R.id.textBtn, R.id.streetLay})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                onBackPressed();
                break;
            case R.id.addressLay:
            case R.id.streetLay:
                setToast("组件待定");
                break;
            case R.id.textBtn:
//                if (checkForm()){
//                    if (PlatformContans.OBJECT_MAP.get(PlatformContans.LoginContacts.ENTITY) != null && !PlatformContans.OBJECT_MAP.get(PlatformContans.LoginContacts.ENTITY).equals("") ){
//                        requestMethod(1,"");
//                    }else{
//                        requestMethod(0,"");
//                    }
//
//                }
                break;
        }
    }


    public void setForm(PhoneAddressEntity editEntity) {
        nameEdit.setText(editEntity.getName());
        mobileEdit.setText(editEntity.getMobile());
        addrText.setText(editEntity.getProvince() + editEntity.getCity() + "市" + editEntity.getArea());
        detailEdit.setText(editEntity.getDetail());
    }
}
