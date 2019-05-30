package com.cheyibao;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
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
import com.example.yunchebao.yuedan.adapter.ImageAdapter;
import com.google.gson.Gson;
import com.gyf.immersionbar.ImmersionBar;
import com.http.HttpProxy;
import com.http.ICallBack;
import com.jzxiang.pickerview.TimePickerDialog;
import com.jzxiang.pickerview.data.Type;
import com.jzxiang.pickerview.listener.OnDateSetListener;
import com.nohttp.sample.NoHttpFragmentBaseActivity;
import com.payencai.library.util.ToastUtil;
import com.tool.ActivityConstans;
import com.tool.CommonDateTools;
import com.tool.FileUtil;
import com.tool.GlideImageEngine;
import com.tool.StringUtils;
import com.tool.UIControlUtils;
import com.tool.view.GridViewForScrollView;
import com.vipcenter.RegisterActivity;
import com.xiasuhuei321.loadingdialog.view.LoadingDialog;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
import top.zibin.luban.CompressionPredicate;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

/**
 * Created by sdhcjhss on 2017/12/26.
 */

public class SellCarInfoImproveActivity extends NoHttpFragmentBaseActivity implements OnDateSetListener {
    TimePickerDialog mDialogYearMonth;
    TimePickerDialog mDialogYearMonthDay;
    SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM");
    SimpleDateFormat sf2 = new SimpleDateFormat("yyyy-MM-dd");
    @BindView(R.id.time1)
    TextView time1Text;
    @BindView(R.id.time2)
    TextView time2Text;
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
    int count = 60;
    @BindView(R.id.gv_pic)
    GridViewForScrollView gv_pic;
    ImageAdapter mImageAdapter;
    List<Uri> mSelected;
    List<String> images;
    TimeCount mTimeCount;
    String image1, image2, image3, image4, image5, image6, image7;
    String detailId, id1, id2, id3, b_img, b_price, b_dis, b_city, b_time = null;
    LoadingDialog mLoadingDialog;

    class TimeCount extends CountDownTimer {

        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            getcode.setEnabled(false);
            getcode.setTextColor(getResources().getColor(R.color.gray_99));
            count--;
            //倒计时的过程中回调该函数
            getcode.setText("剩余" + count + "s");
        }

        @Override
        public void onFinish() {
            count = 60;
            getcode.setText("重新获取");
            getcode.setEnabled(true);
            getcode.setTextColor(getResources().getColor(R.color.white));
            //倒计时结束时回调该函数
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = LayoutInflater.from(this).inflate(R.layout.sell_car_info_improve_layout, null);
        setContentView(view);
        mTimeCount = new TimeCount(60000, 1000);
        mLoadingDialog = new LoadingDialog(this);
        Bundle bundle = getIntent().getExtras();
        detailId = bundle.getString("id");
        id1 = bundle.getString("id1");
        id2 = bundle.getString("id2");
        id3 = bundle.getString("id3");
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
                        upFileImage(PlatformContans.Commom.uploadImg, file);
                    } else {
                        Toast.makeText(SellCarInfoImproveActivity.this, "找不到照片", Toast.LENGTH_SHORT).show();
                    }
                    break;
                case 188:
                    mSelected = Matisse.obtainResult(data);
                    for (int i = 0; i < mSelected.size(); i++) {
                        Log.e("images", Matisse.obtainPathResult(data).get(i));
                        File fileByUri = FileUtil.getFileByUri(Matisse.obtainResult(data).get(i), this);
                        Luban.with(this)
                                .load(fileByUri)
                                .ignoreBy(100)
                                .filter(new CompressionPredicate() {
                                    @Override
                                    public boolean apply(String path) {
                                        return !(TextUtils.isEmpty(path) || path.toLowerCase().endsWith(".gif"));
                                    }
                                })
                                .setCompressListener(new OnCompressListener() {
                                    @Override
                                    public void onStart() {
                                    }

                                    @Override
                                    public void onSuccess(File file) {
                                        //evaluationBeans.get(mTempPosition).getEvaluationImages().add(0,file);
                                        upImage(PlatformContans.Commom.uploadImg, file);
                                    }

                                    @Override
                                    public void onError(Throwable e) {
                                    }
                                }).launch();
                    }

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

    public void upFileImage(String url, File file) {
        mLoadingDialog.setLoadingText("上传中")
                .setSuccessText("上传成功")//显示加载成功时的文字
                .setFailedText("上传失败")
                .setInterceptBack(false)
                .setRepeatCount(3)
                .show();
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
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mLoadingDialog.loadFailed();
                    }
                });
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
                            mLoadingDialog.loadSuccess();
                            switch (curpos) {
                                case 1:
                                    Glide.with(SellCarInfoImproveActivity.this).load(image).into(iv_dj);
                                    image1 = image;
                                    break;
                                case 2:
                                    Glide.with(SellCarInfoImproveActivity.this).load(image).into(iv_xs);
                                    image2 = image;
                                    break;
                                case 3:
                                    Glide.with(SellCarInfoImproveActivity.this).load(image).into(iv_gc);
                                    image3 = image;
                                    break;
                            }

                        }
                    });

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
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
                    images.add(image);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mImageAdapter.notifyDataSetChanged();
                        }
                    });

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
    }

    private boolean checkInput() {
        boolean isOk = true;
        if (TextUtils.isEmpty(et_name.getEditableText().toString())) {
            isOk = false;
            ToastUtil.showToast(this, "输入不能为空！");
            return isOk;
        }
        if (TextUtils.isEmpty(et_phone.getEditableText().toString())) {
            isOk = false;
            ToastUtil.showToast(this, "输入不能为空！");
            return isOk;
        }
        if (TextUtils.isEmpty(et_des.getEditableText().toString())) {
            isOk = false;
            ToastUtil.showToast(this, "输入不能为空！");
            return isOk;
        }
        if (TextUtils.isEmpty(et_num.getEditableText().toString())) {
            isOk = false;
            ToastUtil.showToast(this, "输入不能为空！");
            return isOk;
        }

        if (TextUtils.isEmpty(et_code.getEditableText().toString())) {
            isOk = false;
            ToastUtil.showToast(this, "验证码不能为空！");
            return isOk;
        }
        if (TextUtils.isEmpty(image1)) {
            isOk = false;
            ToastUtil.showToast(this, "证件不能为空！");
            return isOk;
        }
        if (TextUtils.isEmpty(image2)) {
            isOk = false;
            ToastUtil.showToast(this, "证件不能为空！");
            return isOk;
        }
        if (TextUtils.isEmpty(image3)) {
            isOk = false;
            ToastUtil.showToast(this, "证件不能为空！");
            return isOk;
        }
        return isOk;
    }

    private void initView() {
        UIControlUtils.UITextControlsUtils.setUIText(findViewById(R.id.title), ActivityConstans.UITag.TEXT_VIEW, "我要卖车");
        ButterKnife.bind(this);
        ImmersionBar.with(this).autoDarkModeEnable(true).fitsSystemWindows(true).statusBarColor(R.color.white).init();
        time1Text.setText(CommonDateTools.getCurrentDate("yyyy-MM"));
        time2Text.setText(CommonDateTools.getCurrentDate("yyyy-MM-dd"));
        initTimePickerView();
        initTime();
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkInput()) {
                    if (MyApplication.isLogin) {
                        postData();
                    }else {
                        startActivity(new Intent(SellCarInfoImproveActivity.this, RegisterActivity.class));
                    }
                }

            }
        });
        getcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getCodeByType(4, et_phone.getEditableText().toString());
            }
        });
        images = new ArrayList<>();
        images.add("");
        mImageAdapter = new ImageAdapter(this, images);
        gv_pic.setAdapter(mImageAdapter);
        gv_pic.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    images.clear();
                    mImageAdapter.notifyDataSetChanged();
                    images.add("");
                    Matisse.from(SellCarInfoImproveActivity.this)
                            .choose(MimeType.ofImage())
                            .countable(true)
                            .maxSelectable(10)
                            .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
                            .thumbnailScale(0.85f)
                            .imageEngine(new GlideImageEngine())
                            .forResult(188);
                }
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
                        mTimeCount.start();
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
        params.put("thirdId", id3);
        params.put("carDescribe", et_des.getEditableText().toString());
        params.put("oldPrice", Double.parseDouble(b_price));
        params.put("registrationAddress", b_city);
        params.put("color", color);
        params.put("code", et_code.getEditableText().toString());
        params.put("change", Integer.parseInt(et_num.getEditableText().toString()));
        params.put("carImage", StringUtils.listToString2(images, ','));
        params.put("distance", b_dis);
        params.put("linkman", et_name.getEditableText().toString());
        params.put("linkmanTelephone", et_phone.getEditableText().toString());
        params.put("insuranceValidTime", time2Text.getText().toString());//有效期年月日
        params.put("lastValidateCar", "2019-04-10 00:00:00");
        params.put("registrationTime", b_time + "-10 00:00:00");
        params.put("linkmanBuyCarInvoice", image3);
        params.put("linkmanDrivingLicense", image2);
        params.put("linkmanRegistrationCertificate", image1);
        String json = new Gson().toJson(params);
        Log.e("json", json);
        HttpProxy.obtain().post(PlatformContans.OldCar.addOldCarUserCar, MyApplication.token, json, new ICallBack() {
            @Override
            public void OnSuccess(String result) {
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    int code = jsonObject.getInt("resultCode");
                    if (code == 0) {
                        ToastUtil.showToast(SellCarInfoImproveActivity.this, "提交成功");
                        finish();
                    } else {
                        String msg = jsonObject.getString("message");
                        ToastUtil.showToast(SellCarInfoImproveActivity.this, msg);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Log.e("result", result);

            }

            @Override
            public void onFailure(String error) {

            }
        });
    }

    private void initTime() {
        mDialogYearMonthDay = new TimePickerDialog.Builder()
                .setCallBack(this)
                .setCancelStringId("取消")
                .setSureStringId("确定")
                .setTitleStringId("有效期")
                .setCyclic(false)
                .setThemeColor(getResources().getColor(R.color.timepicker_dialog_bg))
                .setToolBarTextColorId(R.color.colorPrimary)
                .setThemeColor(getResources().getColor(R.color.colorPrimary))
                .setType(Type.YEAR_MONTH_DAY)
                .setWheelItemTextSize(12)
                .build();
    }

    private void initTimePickerView() {
        mDialogYearMonth = new TimePickerDialog.Builder()
                .setCallBack(this)
                .setCancelStringId("取消")
                .setSureStringId("确定")
                .setTitleStringId("验车时间")
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

    private void alertPicker() {
        mDialogYearMonthDay.show(getSupportFragmentManager(), "year_month_day");
    }

    @Override
    public void onDateSet(TimePickerDialog timePickerDialog, long millseconds) {

        switch (timeTag) {
            case 1:
                String text = getDateToString(millseconds);
                time1Text.setText(text);
                break;
            case 2:
                String text2 = getDate(millseconds);
                time2Text.setText(text2);
                break;

        }

    }

    public String getDateToString(long time) {
        Date d = new Date(time);
        return sf.format(d);
    }

    public String getDate(long time) {
        Date d = new Date(time);
        return sf2.format(d);
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
                color = "灰色";
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
                color = "黑色";
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
                color = "蓝色";
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
                color = "咖啡色";
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
                color = "白色";
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
                color = "橙色";
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
                color = "黄色";
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
                color = "彩色";
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
                color = "其他";
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
                color = "绿色";
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
                color = "红色";
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
            case R.id.timeLay2:
                timeTag = 2;
                if (mDialogYearMonth != null) {
                    alertPicker();
                }
                break;
        }
    }
}
