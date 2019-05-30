package com.vipcenter;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yunchebao.MyApplication;
import com.bumptech.glide.Glide;
import com.coorchice.library.SuperTextView;
import com.costans.PlatformContans;
import com.example.yunchebao.R;
import com.example.yunchebao.applyenter.Agency;
import com.example.yunchebao.applyenter.SelectServiceProviderActivity;
import com.google.gson.Gson;
import com.gyf.immersionbar.ImmersionBar;
import com.http.HttpProxy;
import com.http.ICallBack;
import com.nohttp.sample.NoHttpBaseActivity;
import com.payencai.library.util.ToastUtil;
import com.system.X5WebviewActivity;
import com.system.model.AddressBean;
import com.tool.ActivityConstans;
import com.tool.UIControlUtils;
import com.tool.WheelView;
import com.vipcenter.model.AgencyInfo;
import com.xihubao.CarBrandSelectActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * 入驻申请
 */
public class EnteringActivity extends NoHttpBaseActivity {

    @BindView(R.id.et_shop_name)
    EditText etShopName;
    @BindView(R.id.tv_shop_type)
    TextView tvShopType;
    @BindView(R.id.ll_shop_type)
    LinearLayout llShopType;
    @BindView(R.id.tv_shop_address)
    TextView tvShopAddress;
    @BindView(R.id.ll_shop_address)
    LinearLayout llShopAddress;
    @BindView(R.id.et_shop_address_detail)
    EditText etShopAddressDetail;
    @BindView(R.id.et_leader_name)
    EditText etLeaderName;
    @BindView(R.id.et_leader_tell)
    EditText etLeaderTell;
    @BindView(R.id.et_shop_tell)
    EditText etShopTell;
    @BindView(R.id.admin_account)
    EditText adminAccount;
    @BindView(R.id.tv_brand)
    TextView tvBrand;
    @BindView(R.id.ll_brand)
    LinearLayout llBrand;
    @BindView(R.id.iv_idcard_front)
    ImageView ivIdcardFront;
    @BindView(R.id.iv_idcard_back)
    ImageView ivIdcardBack;
    @BindView(R.id.iv_business_license)
    ImageView ivBusinessLicense;
    @BindView(R.id.tv_inviter)
    TextView tvInviter;
    @BindView(R.id.ll_inviter)
    LinearLayout llInviter;
    @BindView(R.id.tv_submit)
    SuperTextView tvSubmit;
    @BindView(R.id.ll_add)
    LinearLayout llAdd;
    Agency mAgency;
    private List<AgencyInfo> agencyInfoList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entering);
        ButterKnife.bind(this);
        ImmersionBar.with(this).autoDarkModeEnable(true).fitsSystemWindows(true).statusBarColor(R.color.white).init();
        initView();
    }

    private void initView() {
        UIControlUtils.UITextControlsUtils.setUIText(findViewById(R.id.title), ActivityConstans.UITag.TEXT_VIEW, "入驻申请");
    }
    public void callPhone(String phoneNum) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        Uri data = Uri.parse("tel:" + phoneNum);
        intent.setData(data);
        startActivity(intent);
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
                        agencyInfoList = new ArrayList<>();
                        for (int i = 0; i < data.length(); i++) {
                            JSONObject item = data.getJSONObject(i);
                            AgencyInfo agencyInfo = new Gson().fromJson(item.toString(), AgencyInfo.class);
                            agencyInfoList.add(agencyInfo);
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

    private AddressBean mAddressBean;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            switch (requestCode) {
                case 1:
                    String brand = data.getExtras().getString("name");
                    tvBrand.setText(brand);
                    break;
                case 2:
                    mAddressBean = (AddressBean) data.getSerializableExtra("address");
                    String address = mAddressBean.getPoiaddress();
                    Log.e("mAddressBean", mAddressBean.toString());
                    tvShopAddress.setText(address);
                    getAgencyLists(mAddressBean.getCityname());
                    break;
                case 3:
                    cropPhoto(data.getData());
                    break;
                case 4:
                    File file = new File(photoOutputUri.getPath());
                    if (file.exists()) {
                        upImage(PlatformContans.Commom.uploadImg, file);
                    } else {
                        Toast.makeText(this, "找不到照片", Toast.LENGTH_SHORT).show();
                    }

                    break;
                case 5:
                    mAgency= (Agency) data.getSerializableExtra("data");
                    tvInviter.setText(mAgency.getName());
                    agencyId=mAgency.getId();
                    break;
            }
        }

    }

    /**
     * 上传图片
     */
    public void upImage(String url, File file) {
        OkHttpClient mOkHttpClent = new OkHttpClient();

        MultipartBody.Builder builder = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("image", "image",
                        RequestBody.create(MediaType.parse("image/png"), file));
        RequestBody requestBody = builder.build();
        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();
        Call call = mOkHttpClent.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("upload", "onResponse: " + e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String string = response.body().string();
                Log.e("upload", "onResponse: " + string);
                try {
                    JSONObject object = new JSONObject(string);
                    int resultCode = object.getInt("resultCode");
                    final String data = object.getString("data");
                    if (flagChoosePhoto == 1) {
                        imageIdcardFront = data;
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Glide.with(EnteringActivity.this).load(imageIdcardFront).into(ivIdcardFront);
                            }
                        });
                    } else if (flagChoosePhoto == 2) {
                        imageIdcardBack = data;
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Glide.with(EnteringActivity.this).load(imageIdcardBack).into(ivIdcardBack);
                            }
                        });

                    } else {
                        imageBusinessLicense = data;
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Glide.with(EnteringActivity.this).load(imageBusinessLicense).into(ivBusinessLicense);
                            }
                        });

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
    }

    /**
     * 选择照片类型  1 身份证正面  2 身份证反面  3 营业执照
     */
    private int flagChoosePhoto = 0;
    private String imageIdcardFront, imageIdcardBack, imageBusinessLicense;

    @OnClick({R.id.tv_phone,R.id.back, R.id.ll_shop_type, R.id.ll_shop_address, R.id.ll_brand, R.id.iv_idcard_front, R.id.iv_idcard_back, R.id.iv_business_license, R.id.ll_inviter, R.id.tv_submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_phone:
                callPhone("0871-68106401");
                break;
            case R.id.back:
                onBackPressed();
                break;
            case R.id.ll_shop_type://店铺类型选择
                showShopType();
                break;
            case R.id.ll_shop_address://城市选择
                startActivityForResult(new Intent(EnteringActivity.this, X5WebviewActivity.class), 2);
                break;
            case R.id.ll_brand://品牌选择
                if (MyApplication.isLogin){
                    Intent intent = new Intent(EnteringActivity.this, CarBrandSelectActivity.class);
                    intent.putExtra("oneLevelSelect",true);
                    startActivityForResult(intent, 1);
                }
                else {
                    startActivity(new Intent(EnteringActivity.this, RegisterActivity.class));
                }
                break;
            case R.id.iv_idcard_front:
                flagChoosePhoto = 1;
                startCategery();
                break;
            case R.id.iv_idcard_back:
                flagChoosePhoto = 2;
                startCategery();
                break;
            case R.id.iv_business_license:
                flagChoosePhoto = 3;
                startCategery();
                break;
            case R.id.ll_inviter:
                startActivityForResult(new Intent(EnteringActivity.this, SelectServiceProviderActivity.class),5);
                //showAgencyList();
                break;
            case R.id.tv_submit:
                checkMsg();
                break;
        }
    }

    /**
     * 选择代理商
     */
    private void showAgencyList() {
        if (tvShopAddress.getText().toString().equals("请选择省市区")) {
            ToastUtil.showToast(EnteringActivity.this, "请先选择店铺地址");
            return;
        }

        if (agencyInfoList != null && agencyInfoList.size() > 0) {
            selectAgency();
        }
    }

    /**
     * 表单校验
     */
    private void checkMsg() {
        if (TextUtils.isEmpty(etShopName.getEditableText())) {
            ToastUtil.showToast(EnteringActivity.this, "请输入店铺名称");
            return;
        }

        if (TextUtils.isEmpty(tvShopType.getText())) {
            ToastUtil.showToast(EnteringActivity.this, "请选择店铺类别");
            return;
        }

        if (tvShopAddress.getText().toString().equals("请选择省市区")) {
            ToastUtil.showToast(EnteringActivity.this, "请选择店铺地址");
            return;
        }

        if (TextUtils.isEmpty(etShopAddressDetail.getEditableText())) {
            ToastUtil.showToast(EnteringActivity.this, "请输入地址描述");
            return;
        }

        if (TextUtils.isEmpty(etLeaderName.getEditableText())) {
            ToastUtil.showToast(EnteringActivity.this, "请输入负责人名字");
            return;
        }

        if (TextUtils.isEmpty(etLeaderTell.getEditableText())) {
            ToastUtil.showToast(EnteringActivity.this, "请输入负责人手机号码");
            return;
        }

        if (TextUtils.isEmpty(adminAccount.getEditableText())) {
            ToastUtil.showToast(EnteringActivity.this, "请输入管理账号");
            return;
        }

        if (TextUtils.isEmpty(imageIdcardFront)) {
            ToastUtil.showToast(EnteringActivity.this, "请选择身份证正面照片");
            return;
        }

        if (TextUtils.isEmpty(imageIdcardBack)) {
            ToastUtil.showToast(EnteringActivity.this, "请选择身份证背面照片");
            return;
        }

        if (TextUtils.isEmpty(imageBusinessLicense)) {
            ToastUtil.showToast(EnteringActivity.this, "请选择营业执照照片");
            return;
        }

        if (tvInviter.getText().toString().equals("请选择邀请人员")) {
            ToastUtil.showToast(EnteringActivity.this, "请选择邀请人员");
            return;
        }

        postMsg();
    }

    /**
     * 表单提交
     */
    private void postMsg() {
        Map<String, Object> params = new HashMap<>();
        params.put("shopName", etShopName.getEditableText().toString());
        params.put("shopHeader", etLeaderName.getEditableText().toString());
        params.put("telephone", etLeaderTell.getEditableText().toString());
        params.put("idPositive", imageIdcardFront);
        params.put("idBack", imageIdcardBack);
        params.put("businessImage", imageBusinessLicense);
        params.put("longitude", mAddressBean.getLatlng().getLng() + "");
        params.put("latitude", mAddressBean.getLatlng().getLat() + "");
        params.put("province", mAddressBean.getProvince() + "");
        params.put("city", mAddressBean.getCityname() + "");
        params.put("area", mAddressBean.getDistrict() + "");
        params.put("address", etShopAddressDetail.getEditableText().toString());
        if (shopType == 1 || shopType == 3 || shopType == 4) {
            params.put("brand", tvBrand.getText().toString());
        }
        params.put("saleTelephone", etShopTell.getEditableText().toString());
        if (agencyId != null) {
            params.put("agencyId", agencyId);
        }
        params.put("account", tvInviter.getText().toString());
        params.put("shopType", shopType);

        Log.e("result", params.toString());
        HttpProxy.obtain().post(PlatformContans.Commom.addShop, MyApplication.token, params, new ICallBack() {
            @Override
            public void OnSuccess(String result) {
                Log.e("result", result);
                ToastUtil.showToast(EnteringActivity.this, "申请成功");
                finish();
            }

            @Override
            public void onFailure(String error) {
                ToastUtil.showToast(EnteringActivity.this, "申请失败");
            }
        });

    }

    /**
     * 打开文件管理选择照片
     */
    private void startCategery() {
        Intent mIntent = new Intent(Intent.ACTION_GET_CONTENT);
        mIntent.addCategory(Intent.CATEGORY_OPENABLE);
        mIntent.setType("image/*");
        startActivityForResult(mIntent, 3);
    }

    /**
     * 裁剪图片
     */
    Uri photoOutputUri;

    private void cropPhoto(Uri inputUri) {
        // 调用系统裁剪图片的 Action
        Intent cropPhotoIntent = new Intent("com.android.camera.action.CROP");
        // 设置数据Uri 和类型
        cropPhotoIntent.setDataAndType(inputUri, "image/*");
        // 授权应用读取 Uri，这一步要有，不然裁剪程序会崩溃
        cropPhotoIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        // 设置图片的最终输出目录
        cropPhotoIntent.putExtra(MediaStore.EXTRA_OUTPUT,
                photoOutputUri = Uri.parse("file:////sdcard/image_output.jpg"));
        startActivityForResult(cropPhotoIntent, 4);
    }


    /**
     * 申请店铺类型（1. 4S店，2. 洗车/修车店，3 新车汇，4 二手车，5 租车，6 驾校,7. 道路救援,8 宝贝商城）
     * <p>
     * 1,3,4  这三个有品牌选择，其余没有。
     */
    private int shopType;

    /**
     * 展示店铺类型选择列表
     */
    private void showShopType() {
        List<String> shopTypes = new ArrayList<>();
        shopTypes.add("4S店");
        shopTypes.add("洗车店/修车店");
        shopTypes.add("新车汇");
        shopTypes.add("二手车");
        shopTypes.add("租车");
        shopTypes.add("驾校");
        shopTypes.add("道路救援");
        shopTypes.add("宝贝商城");
        shopTypes.add("代驾");
        shopTypes.add("加油站");
        View view = this.getLayoutInflater().inflate(R.layout.dialog_washcar_type, null);

        final Dialog dialog = new Dialog(this, R.style.MyDialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable());
        dialog.setContentView(view);
        dialog.show();
        WheelView wv = (WheelView) view.findViewById(R.id.wheelview);
        TextView tv_confirm = (TextView) view.findViewById(R.id.tv_confirm);
        TextView tv_cancel = (TextView) view.findViewById(R.id.tv_cancel);

        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        tv_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                tvShopType.setText(wv.getSeletedItem());
                shopType = wv.getSeletedIndex() + 1;

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (shopType == 1 || shopType == 3 || shopType == 4) {
                            llBrand.setVisibility(View.VISIBLE);
                        } else {
                            llBrand.setVisibility(View.GONE);
                        }
                    }
                });
            }
        });
        wv.setOffset(1);

        wv.setItems(shopTypes);
        wv.setSeletion(0);

        Window window = dialog.getWindow();
        window.setGravity(Gravity.BOTTOM);
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(params);

    }


    private String agencyId;

    /**
     * 展示代理商选择列表
     */
    private void selectAgency() {
        List<String> agencyNameList = new ArrayList<>();
        for (int i = 0; i < agencyInfoList.size(); i++) {
            agencyNameList.add(agencyInfoList.get(i).getName());
        }

        View view = this.getLayoutInflater().inflate(R.layout.dialog_washcar_type, null);

        final Dialog dialog = new Dialog(this, R.style.MyDialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable());
        dialog.setContentView(view);
        dialog.show();
        WheelView wv = (WheelView) view.findViewById(R.id.wheelview);
        TextView tv_confirm = (TextView) view.findViewById(R.id.tv_confirm);
        TextView tv_cancel = (TextView) view.findViewById(R.id.tv_cancel);

        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        tv_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                tvInviter.setText(wv.getSeletedItem());
                int index = wv.getSeletedIndex();
                agencyId = agencyInfoList.get(index).getId();
            }
        });
        wv.setOffset(1);

        wv.setItems(agencyNameList);
        wv.setSeletion(0);

        Window window = dialog.getWindow();
        window.setGravity(Gravity.BOTTOM);
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(params);
    }

}
