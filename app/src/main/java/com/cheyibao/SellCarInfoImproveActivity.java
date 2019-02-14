package com.cheyibao;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.application.MyApplication;
import com.bumptech.glide.Glide;
import com.coorchice.library.SuperTextView;
import com.costans.PlatformContans;
import com.example.yunchebao.R;
import com.google.gson.Gson;
import com.http.HttpProxy;
import com.http.ICallBack;
import com.jzxiang.pickerview.TimePickerDialog;
import com.jzxiang.pickerview.data.Type;
import com.jzxiang.pickerview.listener.OnDateSetListener;
import com.nohttp.sample.NoHttpBaseActivity;
import com.nohttp.sample.NoHttpFragmentBaseActivity;
import com.tool.ActivityConstans;
import com.tool.CommonDateTools;
import com.tool.UIControlUtils;
import com.vipcenter.RegisterActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
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
 * Created by sdhcjhss on 2017/12/26.
 */

public class SellCarInfoImproveActivity extends NoHttpFragmentBaseActivity implements OnDateSetListener {
    TimePickerDialog mDialogYearMonth;
    SimpleDateFormat sf = new SimpleDateFormat("yyyy年MM月");

    @BindView(R.id.time1)
    TextView time1Text;
    @BindView(R.id.time2)
    EditText time2Text;
    @BindView(R.id.iv_dj)
    ImageView iv_dj;
    @BindView(R.id.iv_xs)
    ImageView iv_xs;
    @BindView(R.id.et_num)
    EditText et_num;
    @BindView(R.id.iv_gc)
    ImageView iv_gc;
    @BindView(R.id.submit)
    SuperTextView submit;
    @BindView(R.id.et_code)
    EditText et_code;
    @BindView(R.id.et_name)
    EditText et_name;
    @BindView(R.id.et_phone)
    EditText et_phone;
    @BindView(R.id.et_des)
    EditText et_des;
    @BindView(R.id.iv_zj1)
    ImageView iv_zj1;
    @BindView(R.id.iv_zj2)
    ImageView iv_zj2;
    @BindView(R.id.iv_zj3)
    ImageView iv_zj3;
    @BindView(R.id.iv_zj4)
    ImageView iv_zj4;
    @BindView(R.id.ll_black)
    LinearLayout ll_black;
    @BindView(R.id.ll_blue)
    LinearLayout ll_blue;
    @BindView(R.id.ll_gray)
    LinearLayout ll_gray;
    @BindView(R.id.ll_white)
    LinearLayout ll_white;
    @BindView(R.id.ll_green)
    LinearLayout ll_green;
    @BindView(R.id.ll_red)
    LinearLayout ll_red;
    @BindView(R.id.getcode)
    SuperTextView getcode;
    @BindView(R.id.ll_yellow)
    LinearLayout ll_yellow;
    @BindView(R.id.ll_origin)
    LinearLayout ll_origin;
    @BindView(R.id.ll_coffee)
    LinearLayout ll_coffee;
    @BindView(R.id.ll_rgb)
    LinearLayout ll_rgb;
    @BindView(R.id.ll_other)
    LinearLayout ll_other;
    String image;
    int timeTag = 1;
    int curpos = 0;
    String color;
    String image1, image2, image3, image4, image5, image6, image7;
    String detailId, id1, id2, id3, b_img, b_price, b_dis, b_city, b_time = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = LayoutInflater.from(this).inflate(R.layout.sell_car_info_improve_layout, null);
        setContentView(view);
        Bundle bundle = getIntent().getExtras();
        detailId = bundle.getString("id");
        id1 = bundle.getString("id1");
        id2 = bundle.getString("id2");
        id3 = bundle.getString("id3");
        b_img = bundle.getString("image");
        b_city = bundle.getString("city");
        b_time = bundle.getString("time");
        b_dis = bundle.getString("dis");
        b_price = bundle.getString("price");
        commHiddenKeyboard(view);
        initView();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            switch (requestCode) {
                case 3:
                    cropPhoto(data.getData());
                    break;
                case 4:
                    File file = new File(photoOutputUri.getPath());
                    if (file.exists()) {
                        //Bitmap bitmap = BitmapFactory.decodeFile(photoOutputUri.getPath());
                        //server_head.setImageBitmap(bitmap);
                        upImage(PlatformContans.Commom.uploadImg, file);
                    } else {
                        Toast.makeText(SellCarInfoImproveActivity.this, "找不到照片", Toast.LENGTH_SHORT).show();
                    }
                    break;
            }
        }

    }

    /**
     * 裁剪图片
     */
    Uri photoOutputUri;

    private void startCategery() {
        Intent mIntent = new Intent(Intent.ACTION_GET_CONTENT);
        mIntent.addCategory(Intent.CATEGORY_OPENABLE);
        mIntent.setType("image/*");
        startActivityForResult(mIntent, 3);
    }

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
                    image = object.getString("data");
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            switch (curpos) {
                                case 1:
                                    image1 = image;
                                    Glide.with(SellCarInfoImproveActivity.this).load(image).into(iv_dj);
                                    break;
                                case 2:
                                    Glide.with(SellCarInfoImproveActivity.this).load(image).into(iv_xs);
                                    image2 = image;
                                    break;
                                case 3:
                                    Glide.with(SellCarInfoImproveActivity.this).load(image).into(iv_gc);
                                    image3 = image;
                                    break;
                                case 4:
                                    Glide.with(SellCarInfoImproveActivity.this).load(image).into(iv_zj1);
                                    image4 = image;
                                    break;
                                case 5:
                                    Glide.with(SellCarInfoImproveActivity.this).load(image).into(iv_zj2);
                                    image5 = image;
                                    break;
                                case 6:
                                    Glide.with(SellCarInfoImproveActivity.this).load(image).into(iv_zj3);
                                    image6 = image;
                                    break;
                                case 7:
                                    Glide.with(SellCarInfoImproveActivity.this).load(image).into(iv_zj4);
                                    image7 = image;
                                    break;
                            }
                            // Glide.with(SellCarInfoImproveActivity.this).load(image).into(img);
                        }
                    });

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
    }

    private void initView() {
        UIControlUtils.UITextControlsUtils.setUIText(findViewById(R.id.title), ActivityConstans.UITag.TEXT_VIEW, "我要卖车");
        ButterKnife.bind(this);
        time1Text.setText(CommonDateTools.getCurrentDate("yyyy年MM月"));
        // time2Text.setText(CommonDateTools.getCurrentDate("yyyy年MM月"));
        initTimePickerView();
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(MyApplication.isLogin)
                    postData();
                else{
                    startActivity(new Intent(SellCarInfoImproveActivity.this,RegisterActivity.class));
                }
            }
        });
        getcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getCodeByType(4, et_phone.getEditableText().toString());
            }
        });
    }

    private void getCodeByType(int type, String phone) {
        Map<String, Object> params = new HashMap<>();
        params.put("telephone", phone);
        params.put("type", type);
        HttpProxy.obtain().get(PlatformContans.User.getVeriCode, params, new ICallBack() {
            @Override
            public void OnSuccess(String result) {
                Log.e("result", result);
                try {
                    JSONObject object = new JSONObject(result);
                    int code = object.getInt("resultCode");
                    if (code == 0) {
                        Toast.makeText(SellCarInfoImproveActivity.this, "验证码已发送，请注意查收", Toast.LENGTH_LONG).show();
                        //注册,并且登录
                    } else {
                        //登录
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

    public void postData() {
        Map<String, Object> params = new HashMap<>();
        params.put("carCategoryDetailId", detailId);
        params.put("secondId", id2);
        params.put("firstId", id1);
        params.put("thirdId",id3);
        params.put("carDescribe", et_des.getEditableText().toString());
        params.put("oldPrice", Double.parseDouble(b_price));
        params.put("registrationAddress", b_city);
        params.put("color", color);
        params.put("code", et_code.getEditableText().toString());
        params.put("change", Integer.parseInt(et_num.getEditableText().toString()));
        params.put("carImage", image4 + "," + image5 + "," + image6 + "," + image7);
        params.put("distance", b_dis);
        params.put("linkman", et_name.getEditableText().toString());
        params.put("linkmanTelephone", et_phone.getEditableText().toString());
        params.put("insuranceValidTime", time2Text.getEditableText().toString());//有效期年月日
        params.put("lastValidateCar", "2019-04-10 00:00:00");
        params.put("registrationTime", b_time+"-10 00:00:00");
        params.put("linkmanBuyCarInvoice", image3);
        params.put("linkmanDrivingLicense", image2);
        params.put("linkmanRegistrationCertificate", image1);
        String json = new Gson().toJson(params);
        Log.e("json", json);
        HttpProxy.obtain().post(PlatformContans.OldCar.addOldCarUserCar, MyApplication.getUserInfo().getToken(), json, new ICallBack() {
            @Override
            public void OnSuccess(String result) {
                Log.e("result", result);
                finish();
            }

            @Override
            public void onFailure(String error) {

            }
        });
    }

    private void initTimePickerView() {
        mDialogYearMonth = new TimePickerDialog.Builder()
                .setCallBack(this)
                .setCancelStringId("取消")
                .setSureStringId("确定")
                .setTitleStringId("上牌时间")
                .setCyclic(false)
                .setThemeColor(getResources().getColor(R.color.timepicker_dialog_bg))
                .setToolBarTextColorId(R.color.colorPrimary)
                .setThemeColor(getResources().getColor(R.color.colorPrimary))
                .setType(Type.YEAR_MONTH)
                .setWheelItemTextSize(12)
                .build();
    }

    private void alertTimePicker() {
        mDialogYearMonth.show(getSupportFragmentManager(), "year_month");
    }

    @Override
    public void onDateSet(TimePickerDialog timePickerDialog, long millseconds) {
        String text = getDateToString(millseconds);
        switch (timeTag) {
            case 1:
                time1Text.setText(text);
                break;

        }

    }

    public String getDateToString(long time) {
        Date d = new Date(time);
        return sf.format(d);
    }

    @OnClick({R.id.back, R.id.timeLay1, R.id.timeLay2, R.id.ll_gc, R.id.ll_xs, R.id.ll_dj, R.id.fr_zj1, R.id.fr_zj2, R.id.fr_zj3, R.id.fr_zj4
            , R.id.ll_gray, R.id.ll_blue, R.id.ll_black, R.id.ll_coffee, R.id.ll_green, R.id.ll_red, R.id.ll_origin,
            R.id.ll_white, R.id.ll_rgb, R.id.ll_other, R.id.ll_yellow})
    public void OnClick(View v) {
        switch (v.getId()) {
            case R.id.ll_dj:
                curpos = 1;
                startCategery();
                break;
            case R.id.ll_xs:
                curpos = 2;
                startCategery();
                break;
            case R.id.ll_gc:
                curpos = 3;
                startCategery();
                break;
            case R.id.fr_zj1:
                curpos = 4;
                startCategery();
                break;
            case R.id.fr_zj2:
                curpos = 5;
                startCategery();
                break;
            case R.id.fr_zj3:
                curpos = 6;
                startCategery();
                break;
            case R.id.fr_zj4:
                curpos = 7;
                startCategery();
                break;
            case R.id.back:
                onBackPressed();
                break;
            case R.id.timeLay1:
                timeTag = 1;
                if (mDialogYearMonth != null) {
                    alertTimePicker();
                }
                break;
            case R.id.ll_gray:
                color = "#707070";
                ll_gray.setBackground(getResources().getDrawable(R.drawable.selected_bg_yellow_small));
                ll_black.setBackground(getResources().getDrawable(R.drawable.gray_stroke_r4));
                ll_blue.setBackground(getResources().getDrawable(R.drawable.gray_stroke_r4));
                ll_coffee.setBackground(getResources().getDrawable(R.drawable.gray_stroke_r4));
                ll_white.setBackground(getResources().getDrawable(R.drawable.gray_stroke_r4));
                ll_origin.setBackground(getResources().getDrawable(R.drawable.gray_stroke_r4));
                ll_yellow.setBackground(getResources().getDrawable(R.drawable.gray_stroke_r4));
                ll_rgb.setBackground(getResources().getDrawable(R.drawable.gray_stroke_r4));
                ll_other.setBackground(getResources().getDrawable(R.drawable.gray_stroke_r4));
                ll_green.setBackground(getResources().getDrawable(R.drawable.gray_stroke_r4));
                ll_red.setBackground(getResources().getDrawable(R.drawable.gray_stroke_r4));

                break;
            case R.id.ll_black:
                color = "#333333";
                ll_black.setBackground(getResources().getDrawable(R.drawable.selected_bg_yellow_small));
                ll_gray.setBackground(getResources().getDrawable(R.drawable.gray_stroke_r4));
                ll_blue.setBackground(getResources().getDrawable(R.drawable.gray_stroke_r4));
                ll_coffee.setBackground(getResources().getDrawable(R.drawable.gray_stroke_r4));
                ll_white.setBackground(getResources().getDrawable(R.drawable.gray_stroke_r4));
                ll_origin.setBackground(getResources().getDrawable(R.drawable.gray_stroke_r4));
                ll_yellow.setBackground(getResources().getDrawable(R.drawable.gray_stroke_r4));
                ll_rgb.setBackground(getResources().getDrawable(R.drawable.gray_stroke_r4));
                ll_other.setBackground(getResources().getDrawable(R.drawable.gray_stroke_r4));
                ll_green.setBackground(getResources().getDrawable(R.drawable.gray_stroke_r4));
                ll_red.setBackground(getResources().getDrawable(R.drawable.gray_stroke_r4));
                break;
            case R.id.ll_blue:
                color = "#58c1f9";
                ll_blue.setBackground(getResources().getDrawable(R.drawable.selected_bg_yellow_small));
                ll_gray.setBackground(getResources().getDrawable(R.drawable.gray_stroke_r4));
                ll_black.setBackground(getResources().getDrawable(R.drawable.gray_stroke_r4));
                ll_coffee.setBackground(getResources().getDrawable(R.drawable.gray_stroke_r4));
                ll_white.setBackground(getResources().getDrawable(R.drawable.gray_stroke_r4));
                ll_origin.setBackground(getResources().getDrawable(R.drawable.gray_stroke_r4));
                ll_yellow.setBackground(getResources().getDrawable(R.drawable.gray_stroke_r4));
                ll_rgb.setBackground(getResources().getDrawable(R.drawable.gray_stroke_r4));
                ll_other.setBackground(getResources().getDrawable(R.drawable.gray_stroke_r4));
                ll_green.setBackground(getResources().getDrawable(R.drawable.gray_stroke_r4));
                ll_red.setBackground(getResources().getDrawable(R.drawable.gray_stroke_r4));
                break;
            case R.id.ll_coffee:
                color = "#996922";
                ll_coffee.setBackground(getResources().getDrawable(R.drawable.selected_bg_yellow_small));
                ll_gray.setBackground(getResources().getDrawable(R.drawable.gray_stroke_r4));
                ll_black.setBackground(getResources().getDrawable(R.drawable.gray_stroke_r4));
                ll_blue.setBackground(getResources().getDrawable(R.drawable.gray_stroke_r4));
                ll_white.setBackground(getResources().getDrawable(R.drawable.gray_stroke_r4));
                ll_origin.setBackground(getResources().getDrawable(R.drawable.gray_stroke_r4));
                ll_yellow.setBackground(getResources().getDrawable(R.drawable.gray_stroke_r4));
                ll_rgb.setBackground(getResources().getDrawable(R.drawable.gray_stroke_r4));
                ll_other.setBackground(getResources().getDrawable(R.drawable.gray_stroke_r4));
                ll_green.setBackground(getResources().getDrawable(R.drawable.gray_stroke_r4));
                ll_red.setBackground(getResources().getDrawable(R.drawable.gray_stroke_r4));
                break;
            case R.id.ll_white:
                color = "#ffffff";
                ll_white.setBackground(getResources().getDrawable(R.drawable.selected_bg_yellow_small));
                ll_gray.setBackground(getResources().getDrawable(R.drawable.gray_stroke_r4));
                ll_black.setBackground(getResources().getDrawable(R.drawable.gray_stroke_r4));
                ll_blue.setBackground(getResources().getDrawable(R.drawable.gray_stroke_r4));
                ll_coffee.setBackground(getResources().getDrawable(R.drawable.gray_stroke_r4));
                ll_origin.setBackground(getResources().getDrawable(R.drawable.gray_stroke_r4));
                ll_yellow.setBackground(getResources().getDrawable(R.drawable.gray_stroke_r4));
                ll_rgb.setBackground(getResources().getDrawable(R.drawable.gray_stroke_r4));
                ll_other.setBackground(getResources().getDrawable(R.drawable.gray_stroke_r4));
                ll_green.setBackground(getResources().getDrawable(R.drawable.gray_stroke_r4));
                ll_red.setBackground(getResources().getDrawable(R.drawable.gray_stroke_r4));
                break;
            case R.id.ll_origin:
                color = "#f1a03d";
                ll_origin.setBackground(getResources().getDrawable(R.drawable.selected_bg_yellow_small));
                ll_gray.setBackground(getResources().getDrawable(R.drawable.gray_stroke_r4));
                ll_black.setBackground(getResources().getDrawable(R.drawable.gray_stroke_r4));
                ll_blue.setBackground(getResources().getDrawable(R.drawable.gray_stroke_r4));
                ll_coffee.setBackground(getResources().getDrawable(R.drawable.gray_stroke_r4));
                ll_white.setBackground(getResources().getDrawable(R.drawable.gray_stroke_r4));
                ll_yellow.setBackground(getResources().getDrawable(R.drawable.gray_stroke_r4));
                ll_rgb.setBackground(getResources().getDrawable(R.drawable.gray_stroke_r4));
                ll_other.setBackground(getResources().getDrawable(R.drawable.gray_stroke_r4));
                ll_green.setBackground(getResources().getDrawable(R.drawable.gray_stroke_r4));
                ll_red.setBackground(getResources().getDrawable(R.drawable.gray_stroke_r4));
                break;
            case R.id.ll_yellow:
                color = "#ead795";
                ll_yellow.setBackground(getResources().getDrawable(R.drawable.selected_bg_yellow_small));
                ll_gray.setBackground(getResources().getDrawable(R.drawable.gray_stroke_r4));
                ll_black.setBackground(getResources().getDrawable(R.drawable.gray_stroke_r4));
                ll_blue.setBackground(getResources().getDrawable(R.drawable.gray_stroke_r4));
                ll_coffee.setBackground(getResources().getDrawable(R.drawable.gray_stroke_r4));
                ll_white.setBackground(getResources().getDrawable(R.drawable.gray_stroke_r4));
                ll_origin.setBackground(getResources().getDrawable(R.drawable.gray_stroke_r4));
                ll_rgb.setBackground(getResources().getDrawable(R.drawable.gray_stroke_r4));
                ll_other.setBackground(getResources().getDrawable(R.drawable.gray_stroke_r4));
                ll_green.setBackground(getResources().getDrawable(R.drawable.gray_stroke_r4));
                ll_red.setBackground(getResources().getDrawable(R.drawable.gray_stroke_r4));
                break;
            case R.id.ll_rgb:
                color = "#rgb";
                ll_rgb.setBackground(getResources().getDrawable(R.drawable.selected_bg_yellow_small));
                ll_gray.setBackground(getResources().getDrawable(R.drawable.gray_stroke_r4));
                ll_black.setBackground(getResources().getDrawable(R.drawable.gray_stroke_r4));
                ll_blue.setBackground(getResources().getDrawable(R.drawable.gray_stroke_r4));
                ll_coffee.setBackground(getResources().getDrawable(R.drawable.gray_stroke_r4));
                ll_white.setBackground(getResources().getDrawable(R.drawable.gray_stroke_r4));
                ll_origin.setBackground(getResources().getDrawable(R.drawable.gray_stroke_r4));
                ll_yellow.setBackground(getResources().getDrawable(R.drawable.gray_stroke_r4));
                ll_other.setBackground(getResources().getDrawable(R.drawable.gray_stroke_r4));
                ll_green.setBackground(getResources().getDrawable(R.drawable.gray_stroke_r4));
                ll_red.setBackground(getResources().getDrawable(R.drawable.gray_stroke_r4));
                break;
            case R.id.ll_other:
                color = "#other";
                ll_other.setBackground(getResources().getDrawable(R.drawable.selected_bg_yellow_small));
                ll_gray.setBackground(getResources().getDrawable(R.drawable.gray_stroke_r4));
                ll_black.setBackground(getResources().getDrawable(R.drawable.gray_stroke_r4));
                ll_blue.setBackground(getResources().getDrawable(R.drawable.gray_stroke_r4));
                ll_coffee.setBackground(getResources().getDrawable(R.drawable.gray_stroke_r4));
                ll_white.setBackground(getResources().getDrawable(R.drawable.gray_stroke_r4));
                ll_origin.setBackground(getResources().getDrawable(R.drawable.gray_stroke_r4));
                ll_rgb.setBackground(getResources().getDrawable(R.drawable.gray_stroke_r4));
                ll_green.setBackground(getResources().getDrawable(R.drawable.gray_stroke_r4));
                ll_yellow.setBackground(getResources().getDrawable(R.drawable.gray_stroke_r4));
                ll_red.setBackground(getResources().getDrawable(R.drawable.gray_stroke_r4));
                break;
            case R.id.ll_green:
                color = "#87e869";
                ll_green.setBackground(getResources().getDrawable(R.drawable.selected_bg_yellow_small));
                ll_gray.setBackground(getResources().getDrawable(R.drawable.gray_stroke_r4));
                ll_black.setBackground(getResources().getDrawable(R.drawable.gray_stroke_r4));
                ll_blue.setBackground(getResources().getDrawable(R.drawable.gray_stroke_r4));
                ll_coffee.setBackground(getResources().getDrawable(R.drawable.gray_stroke_r4));
                ll_white.setBackground(getResources().getDrawable(R.drawable.gray_stroke_r4));
                ll_origin.setBackground(getResources().getDrawable(R.drawable.gray_stroke_r4));
                ll_rgb.setBackground(getResources().getDrawable(R.drawable.gray_stroke_r4));
                ll_other.setBackground(getResources().getDrawable(R.drawable.gray_stroke_r4));
                ll_yellow.setBackground(getResources().getDrawable(R.drawable.gray_stroke_r4));
                ll_red.setBackground(getResources().getDrawable(R.drawable.gray_stroke_r4));
                break;
            case R.id.ll_red:
                color = "#ead795";
                ll_red.setBackground(getResources().getDrawable(R.drawable.selected_bg_yellow_small));
                ll_gray.setBackground(getResources().getDrawable(R.drawable.gray_stroke_r4));
                ll_black.setBackground(getResources().getDrawable(R.drawable.gray_stroke_r4));
                ll_blue.setBackground(getResources().getDrawable(R.drawable.gray_stroke_r4));
                ll_coffee.setBackground(getResources().getDrawable(R.drawable.gray_stroke_r4));
                ll_white.setBackground(getResources().getDrawable(R.drawable.gray_stroke_r4));
                ll_origin.setBackground(getResources().getDrawable(R.drawable.gray_stroke_r4));
                ll_rgb.setBackground(getResources().getDrawable(R.drawable.gray_stroke_r4));
                ll_other.setBackground(getResources().getDrawable(R.drawable.gray_stroke_r4));
                ll_green.setBackground(getResources().getDrawable(R.drawable.gray_stroke_r4));
                ll_yellow.setBackground(getResources().getDrawable(R.drawable.gray_stroke_r4));
                break;
//            case R.id.timeLay2:
//                timeTag = 2;
//                if (mDialogYearMonth != null) {
//                    alertTimePicker();
//                }
//                break;
        }
    }
}
