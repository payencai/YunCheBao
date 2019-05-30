package com.example.yunchebao.road;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yunchebao.MyApplication;
import com.bumptech.glide.Glide;
import com.costans.PlatformContans;
import com.example.yunchebao.R;
import com.example.yunchebao.road.model.RoadDetail;
import com.gyf.immersionbar.ImmersionBar;
import com.http.HttpProxy;
import com.http.ICallBack;
import com.payencai.library.mediapicker.PickerActivity;
import com.payencai.library.mediapicker.PickerConfig;
import com.payencai.library.mediapicker.entity.Media;
import com.payencai.library.util.ToastUtil;
import com.payencai.library.util.VideoUtil;
import com.system.X5WebviewActivity;
import com.system.model.AddressBean;
import com.tool.FileUtil;
import com.tool.GlideImageEngine;
import com.tool.StringUtils;
import com.tool.view.GridViewForScrollView;
import com.xihubao.CarBrandSelectActivity;
import com.example.yunchebao.yuedan.adapter.ImageAdapter;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.internal.entity.CaptureStrategy;

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
import top.zibin.luban.CompressionPredicate;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

public class PubRoadActivity extends AppCompatActivity {
    @BindView(R.id.gv_pic)
    GridViewForScrollView gv_pic;
    @BindView(R.id.iv_logo)
    ImageView iv_logo;
    @BindView(R.id.tv_shopname)
    TextView tv_shopname;
    @BindView(R.id.tv_phone)
    TextView tv_phone;
    @BindView(R.id.tv_addr)
    TextView tv_addr;
    @BindView(R.id.iv_video)
    ImageView iv_video;
    @BindView(R.id.iv_play)
    ImageView iv_play;
    @BindView(R.id.tv_type)
    TextView tv_type;
    @BindView(R.id.tv_address)
    TextView tv_address;
    @BindView(R.id.et_phone)
    EditText et_phone;
    @BindView(R.id.et_color)
    EditText et_color;
    @BindView(R.id.et_addr)
    EditText et_addr;
    @BindView(R.id.et_detail)
    EditText et_detail;
    @BindView(R.id.tv_num)
    TextView tv_num;
    ImageAdapter mImageAdapter;
    List<String> images;
    AddressBean mAddressBean;
    String address;
    String carCategory;
    String imgs;
    String video;
    String vimg;
    String id;
    RoadDetail mRoadDetail;
    ArrayList<Media> defaultSelect = new ArrayList<>();
    List<Uri> mSelected;
    int count=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pub_road);
        ButterKnife.bind(this);
        ImmersionBar.with(this).autoDarkModeEnable(true).fitsSystemWindows(true).statusBarColor(R.color.white).init();
        id = getIntent().getStringExtra("id");
        mRoadDetail = (RoadDetail) getIntent().getSerializableExtra("data");
        initView();
    }

    private void initView() {
        tv_shopname.setText(mRoadDetail.getShopName());
        findViewById(R.id.tv_item1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                et_detail.setText("拖车");
            }
        });
        et_detail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                tv_num.setText(s.length()+"/200");
            }
        });
        findViewById(R.id.tv_item2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                et_detail.setText("加油");
            }
        });
        findViewById(R.id.tv_item3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                et_detail.setText("换胎");
            }
        });
        tv_phone.setText("商家电话：" + mRoadDetail.getSaleTelephone());
        tv_addr.setText("商家地址：" + mRoadDetail.getAddress());
        Glide.with(this).load(mRoadDetail.getLogo()).into(iv_logo);
        images = new ArrayList<>();
        images.add("");
        mImageAdapter = new ImageAdapter(this, images);
        gv_pic.setAdapter(mImageAdapter);
        gv_pic.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if (position == 0) {

                    Matisse.from(PubRoadActivity.this)
                            .choose(MimeType.ofImage())
                            .countable(true)
                            .maxSelectable(4)
                            .capture(true)
                            .originalEnable(true)
                            .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
                            .thumbnailScale(0.85f)
                            .captureStrategy(new CaptureStrategy(true, "com.yancy.gallerypickdemo.fileprovider"))
                            .imageEngine(new GlideImageEngine())
                            .forResult(3);
                }
            }
        });
    }

    @OnClick({R.id.rl_cartype, R.id.addressLay, R.id.iv_video, R.id.tv_public, R.id.back})
    void OnClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.tv_public:
                if (check())
                    addService();
                break;
            case R.id.iv_video:
                chooseVideo();
                break;
            case R.id.rl_cartype:
                startActivityForResult(new Intent(this, CarBrandSelectActivity.class), 1);
                break;
            case R.id.addressLay:
                startActivityForResult(new Intent(this, X5WebviewActivity.class), 2);
                break;
        }
    }

    private boolean check() {
        boolean isOk = true;
        if (TextUtils.isEmpty(carCategory)) {
            isOk = false;
            ToastUtil.showToast(this, "请选择车型！");
            return isOk;
        }
        if (TextUtils.isEmpty(et_phone.getEditableText().toString())) {
            isOk = false;
            ToastUtil.showToast(this, "请输入手机号！");
            return isOk;
        }
        if (TextUtils.isEmpty(et_color.getEditableText().toString())) {
            isOk = false;
            ToastUtil.showToast(this, "请输入颜色！");
            return isOk;
        }
        if (TextUtils.isEmpty(et_detail.getEditableText().toString())) {
            isOk = false;
            ToastUtil.showToast(this, "请输入问题！");
            return isOk;
        }
        if (TextUtils.isEmpty(et_addr.getEditableText().toString())) {
            isOk = false;
            ToastUtil.showToast(this, "请输入地址！");
            return isOk;
        }
        if (mAddressBean == null) {
            isOk = false;
            ToastUtil.showToast(this, "请选择车辆位置！");
            return isOk;
        }
        return isOk;
    }

    private void addService() {

        Map<String, Object> params = new HashMap<>();
        params.put("telephone", et_phone.getEditableText().toString());
        params.put("detail", et_detail.getEditableText().toString());
        params.put("carCategory", carCategory);
        params.put("province", mAddressBean.getProvince() + "");
        params.put("color", et_color.getEditableText().toString());
        params.put("city", mAddressBean.getCityname() + "");
        params.put("area", mAddressBean.getDistrict() + "");
        params.put("address", mAddressBean.getPoiaddress());
        params.put("addressDetail", et_addr.getEditableText().toString());
        params.put("longitude", mAddressBean.getLatlng().getLng() + "");
        params.put("latitude", mAddressBean.getLatlng().getLat() + "");
        if (!TextUtils.isEmpty(imgs))
            params.put("imgs", imgs.substring(1));
        params.put("video", video);
        params.put("vimg", vimg);
        params.put("shopId", id);
        Log.e("result", params.toString());
        HttpProxy.obtain().post(PlatformContans.RoadRescue.addRoadRescueOrder, MyApplication.token, params, new ICallBack() {
            @Override
            public void OnSuccess(String result) {
                Toast.makeText(PubRoadActivity.this, "发布成功", Toast.LENGTH_LONG).show();
                Log.e("result", result);
                finish();
            }

            @Override
            public void onFailure(String error) {

            }
        });
    }

    private void chooseVideo() {
        Intent intent = new Intent(this, PickerActivity.class);
        intent.putExtra(PickerConfig.SELECT_MODE, PickerConfig.PICKER_VIDEO);//default image and video (Optional)
        long maxSize = 10485760L;//long long long long类型
        intent.putExtra(PickerConfig.MAX_SELECT_SIZE, maxSize); //default 10MB (Optional)
        intent.putExtra(PickerConfig.MAX_SELECT_COUNT, 1);  //default 40 (Optional)
        intent.putExtra(PickerConfig.DEFAULT_SELECTED_LIST, defaultSelect); //(Optional)默认选中的照片
        startActivityForResult(intent, 4);
    }

    public void upLoadVideo(String url, File file) {
        OkHttpClient mOkHttpClent = new OkHttpClient();
        MultipartBody.Builder builder = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("file", "file",
                        RequestBody.create(MediaType.parse("multipart/form-data"), file));
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
                    video = data;

                    ///
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
    }

    public void upThumbImage(String url, File file) {
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
                Log.e("vimg", "onResponse: " + e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String string = response.body().string();
                Log.e("vimg", "onResponse: " + string);
                try {
                    JSONObject object = new JSONObject(string);
                    int resultCode = object.getInt("resultCode");
                    final String data = object.getString("data");
                    vimg = data;
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Glide.with(PubRoadActivity.this).load(vimg).into(iv_video);
                            iv_play.setVisibility(View.VISIBLE);
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
                    final String data = object.getString("data");
                    if (!images.contains(data)) {
                        images.add(data);
                        imgs = StringUtils.listToString2(images, ',');
                    }
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


    public void setImages(Intent data) {
        mSelected = Matisse.obtainResult(data);
        for (int i = 0; i < mSelected.size(); i++) {

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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && data != null) {
            carCategory = data.getStringExtra("name");
            tv_type.setText(carCategory);
        }
        if (requestCode == 2 && data != null) {
            mAddressBean = (AddressBean) data.getSerializableExtra("address");
            address = mAddressBean.getPoiaddress();
            tv_address.setText(address);
        }
        if (requestCode == 4 && data != null) {
            defaultSelect = data.getParcelableArrayListExtra(PickerConfig.EXTRA_RESULT);
            if (defaultSelect.size() > 0) {
                String path = defaultSelect.get(0).path;
                Bitmap bitmap = VideoUtil.voidToFirstBitmap(path);
                String thumb = VideoUtil.bitmapToStringPath(this, bitmap);
                upThumbImage(PlatformContans.Commom.uploadImg, new File(thumb));
                File filevideo = new File(path);
                upLoadVideo(PlatformContans.Commom.uploadVideo, filevideo);
            }
        }
        if (requestCode == 3 && data != null) {
            images.clear();
            images.add("");
            setImages(data);
        }
    }
}
