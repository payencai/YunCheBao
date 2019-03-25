package com.cheyibao;

import android.app.Activity;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.application.MyApplication;
import com.costans.PlatformContans;
import com.example.yunchebao.R;
import com.google.gson.Gson;
import com.http.HttpProxy;
import com.http.ICallBack;
import com.iarcuschin.simpleratingbar.SimpleRatingBar;
import com.order.CarOrder;
import com.payencai.library.util.ToastUtil;
import com.tool.GlideImageLoader;
import com.vipcenter.PubCommentActivity;
import com.vipcenter.adapter.PhotoAdapter;
import com.yancy.gallerypick.config.GalleryConfig;
import com.yancy.gallerypick.config.GalleryPick;
import com.yancy.gallerypick.inter.IHandlerCallBack;

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
import go.error;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class AddRentCommentActivity extends AppCompatActivity {


    private List<String> path = new ArrayList<>();
    private GalleryConfig galleryConfig;
    private IHandlerCallBack iHandlerCallBack;
    private String TAG = "---Yancy---";
    PhotoAdapter mPhotoAdapter;
    @BindView(R.id.ll_add)
    LinearLayout ll_add;
    @BindView(R.id.gv_photo)
    GridView gv_photo;
    @BindView(R.id.tv_pub)
    TextView tv_pub;
    @BindView(R.id.et_content)
    EditText et_content;
    @BindView(R.id.sb_score)
    SimpleRatingBar sb_score;
    @BindView(R.id.iv_shop)
    ImageView iv_shop;
    String imgs = "";
    int isAnonymous=1;
    CarOrder mCarOrder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_rent_comment);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        mCarOrder = (CarOrder) getIntent().getSerializableExtra("item");

        initGallery();
        tv_pub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content = et_content.getEditableText().toString();
                double score = (double) sb_score.getRating();
                if (TextUtils.isEmpty(content)) {
                    ToastUtil.showToast(AddRentCommentActivity.this, "请输入评论");
                    return;
                }
                if (score == 0) {
                    ToastUtil.showToast(AddRentCommentActivity.this, "请给出评分");
                    return;
                }
                shopcomment(content, score);
            }
        });
        iv_shop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isAnonymous==2){
                    iv_shop.setImageResource(R.mipmap.select);
                    isAnonymous=1;
                }else{
                    iv_shop.setImageResource(R.mipmap.unselect);
                    isAnonymous=2;
                }
            }
        });
        mPhotoAdapter = new PhotoAdapter(this, path);
        gv_photo.setAdapter(mPhotoAdapter);
        galleryConfig = new GalleryConfig.Builder()
                .imageLoader(new GlideImageLoader())    // ImageLoader 加载框架（必填）
                .iHandlerCallBack(iHandlerCallBack)     // 监听接口（必填）
                .provider("com.yancy.gallerypickdemo.fileprovider")   // provider(必填)
                .pathList(path)                         // 记录已选的图片
                .multiSelect(true)                      // 是否多选   默认：false
                .multiSelect(true, 6)                   // 配置是否多选的同时 配置多选数量   默认：false ， 9
                .maxSize(6)                             // 配置多选时 的多选数量。    默认：9
                .crop(false)                             // 快捷开启裁剪功能，仅当单选 或直接开启相机时有效
                .crop(false, 1, 1, 500, 500)             // 配置裁剪功能的参数，   默认裁剪比例 1:1
                .isShowCamera(true)                     // 是否现实相机按钮  默认：false
                .filePath("/Gallery/Pictures")          // 图片存放路径
                .build();
        ll_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GalleryPick.getInstance().setGalleryConfig(galleryConfig).open(AddRentCommentActivity.this);
            }
        });

    }

    private void shopcomment(String comment, double score) {
        Map<String, Object> params = new HashMap<>();
        params.put("orderId", mCarOrder.getOrderNo());
        params.put("content", comment);
        params.put("isAnonymous", isAnonymous);
        params.put("score", score);
        if (!TextUtils.isEmpty(imgs))
            params.put("imgs", imgs.substring(1));
        String json=new Gson().toJson(params);
        HttpProxy.obtain().post(PlatformContans.Evaluation.addOrderEvaluation, MyApplication.token, json, new ICallBack() {
            @Override
            public void OnSuccess(String result) {
                Log.e("result", result);
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    int code = jsonObject.getInt("resultCode");
                    if (code == 0) {
                            ToastUtil.showToast(AddRentCommentActivity.this, "发布成功");
                            finish();
                    }else{
                        String msg=jsonObject.getString("message");
                        ToastUtil.showToast(AddRentCommentActivity.this, msg);
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

    private void initGallery() {
        iHandlerCallBack = new IHandlerCallBack() {
            @Override
            public void onStart() {
                Log.i(TAG, "onStart: 开启");
            }

            @Override
            public void onSuccess(List<String> photoList) {
                Log.i(TAG, "onSuccess: 返回数据");
                path.clear();
                for (String s : photoList) {
                    Log.i(TAG, s);
                    path.add(s);
                    upImage(PlatformContans.Commom.uploadImg, new File(s));
                }
                mPhotoAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancel() {
                Log.i(TAG, "onCancel: 取消");
            }

            @Override
            public void onFinish() {
                Log.i(TAG, "onFinish: 结束");
            }

            @Override
            public void onError() {
                Log.i(TAG, "onError: 出错");
            }
        };

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
                Log.e("upload", "onResponse: " + imgs);
                try {
                    JSONObject object = new JSONObject(string);
                    int resultCode = object.getInt("resultCode");
                    final String data = object.getString("data");
                    imgs = imgs + "," + data;
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
    }
}
