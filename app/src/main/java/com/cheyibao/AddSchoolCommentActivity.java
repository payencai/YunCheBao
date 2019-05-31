package com.cheyibao;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.yunchebao.MyApplication;
import com.costans.PlatformContans;
import com.example.yunchebao.R;
import com.example.yunchebao.myservice.model.SchoolOrderDetail;
import com.google.gson.Gson;
import com.http.HttpProxy;
import com.http.ICallBack;
import com.iarcuschin.simpleratingbar.SimpleRatingBar;
import com.order.CarOrder;
import com.payencai.library.util.ToastUtil;
import com.tool.GlideImageLoader;
import com.tool.view.GridViewForScrollView;
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
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class AddSchoolCommentActivity extends AppCompatActivity {

    private List<String> path = new ArrayList<>();
    private List<String> path2 = new ArrayList<>();
    private GalleryConfig galleryConfig;
    private IHandlerCallBack iHandlerCallBack;
    private IHandlerCallBack iHandlerCallBack2;
    private String TAG = "---Yancy---";
    PhotoAdapter mPhotoAdapter;
    PhotoAdapter mPhotoAdapter2;
    @BindView(R.id.ll_coash)
    LinearLayout ll_coash;
    @BindView(R.id.ll_add)
    LinearLayout ll_add;
    @BindView(R.id.gv_photo)
    GridViewForScrollView gv_photo;
    @BindView(R.id.gv_coash)
    GridViewForScrollView gv_coash;
    @BindView(R.id.tv_pub)
    TextView tv_pub;
    @BindView(R.id.et_content)
    EditText et_content;
    @BindView(R.id.et_coash)
    EditText et_coash;
    @BindView(R.id.sb_score)
    SimpleRatingBar sb_score;
    @BindView(R.id.sb_coash)
    SimpleRatingBar sb_coash;
    @BindView(R.id.iv_shop)
    ImageView iv_shop;
    @BindView(R.id.iv_coash)
    ImageView iv_coash;
    String imgs = "";
    String imgs2="";
    CarOrder mCarOrder;
    int isAnonymous=1;
    int isAnonymous2=1;
    SchoolOrderDetail mSchoolOrderDetail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_school_comment);
        ButterKnife.bind(this);
        initView();
    }
    private void getDetail() {
        Map<String, Object> params = new HashMap<>();
        params.put("orderId", mCarOrder.getId());
        HttpProxy.obtain().get(PlatformContans.MyService.getSchoolOrderDetail, params,MyApplication.token, new ICallBack() {
            @Override
            public void OnSuccess(String result) {
                Log.e("data", result);
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    JSONObject data = jsonObject.getJSONObject("data");
                    mSchoolOrderDetail = new Gson().fromJson(data.toString(), SchoolOrderDetail.class);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(String error) {

            }
        });
    }
    private void initView() {
        mCarOrder = (CarOrder) getIntent().getSerializableExtra("item");
        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        initGallery();
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
        iv_coash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isAnonymous2==2){
                    iv_coash.setImageResource(R.mipmap.select);
                    isAnonymous2=1;
                }else{
                    iv_coash.setImageResource(R.mipmap.unselect);
                    isAnonymous2=2;
                }
            }
        });
        tv_pub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content = et_content.getEditableText().toString();
                String content2 = et_coash.getEditableText().toString();
                double score = (double) sb_score.getRating();
                double score2 = (double) sb_coash.getRating();
                if (TextUtils.isEmpty(content)) {
                    ToastUtil.showToast(AddSchoolCommentActivity.this, "请输入店铺评论");
                    return;
                }
                if (score == 0) {
                    ToastUtil.showToast(AddSchoolCommentActivity.this, "请给出店铺评分");
                    return;
                }
                if (TextUtils.isEmpty(content2)) {
                    ToastUtil.showToast(AddSchoolCommentActivity.this, "请输入教练评论");
                    return;
                }
                if (score2 == 0) {
                    ToastUtil.showToast(AddSchoolCommentActivity.this, "请给出教练评分");
                    return;
                }
                coashcomment(content2,score2);
                shopcomment(content, score);
            }
        });

        mPhotoAdapter = new PhotoAdapter(this, path);
        mPhotoAdapter2= new PhotoAdapter(this, path2);
        gv_photo.setAdapter(mPhotoAdapter);
        gv_coash.setAdapter(mPhotoAdapter2);

        ll_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
                        .isShowCamera(false)                     // 是否现实相机按钮  默认：false
                        .filePath("/Gallery/Pictures")          // 图片存放路径
                        .build();
                GalleryPick.getInstance().setGalleryConfig(galleryConfig).open(AddSchoolCommentActivity.this);
            }
        });
        ll_coash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                galleryConfig = new GalleryConfig.Builder()
                .imageLoader(new GlideImageLoader())    // ImageLoader 加载框架（必填）
                .iHandlerCallBack(iHandlerCallBack2)     // 监听接口（必填）
                .provider("com.yancy.gallerypickdemo.fileprovider")   // provider(必填)
                .pathList(path2)                         // 记录已选的图片
                .multiSelect(true)                      // 是否多选   默认：false
                .multiSelect(true, 6)                   // 配置是否多选的同时 配置多选数量   默认：false ， 9
                .maxSize(6)                             // 配置多选时 的多选数量。    默认：9
                .crop(false)                             // 快捷开启裁剪功能，仅当单选 或直接开启相机时有效
                .crop(false, 1, 1, 500, 500)             // 配置裁剪功能的参数，   默认裁剪比例 1:1
                .isShowCamera(true)                     // 是否现实相机按钮  默认：false
                .filePath("/Gallery/Pictures")          // 图片存放路径
                .build();
                GalleryPick.getInstance().setGalleryConfig(galleryConfig).open(AddSchoolCommentActivity.this);
            }
        });
        getDetail();

    }
    int  count=0;
    private void coashcomment(String comment, double score) {
        Map<String, Object> params = new HashMap<>();
        params.put("id", mCarOrder.getId());
        params.put("content", comment);
        params.put("isAnonymous", isAnonymous2);
        params.put("merchantId", mCarOrder.getShopId());
        params.put("score", score);
        params.put("coachId", mSchoolOrderDetail.getCoachId());
        if (!TextUtils.isEmpty(imgs2))
            params.put("photo", imgs2.substring(1));
        String json=new Gson().toJson(params);
        HttpProxy.obtain().post(PlatformContans.Evaluation.addDrivingSchoolCoachEva, MyApplication.token, json, new ICallBack() {
            @Override
            public void OnSuccess(String result) {
                Log.e("coashcomment", result);
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    int code = jsonObject.getInt("resultCode");
                    if (code == 0) {
                        count++;
                        if(count==2){
                            count=0;
                            ToastUtil.showToast(AddSchoolCommentActivity.this, "发布成功");
                            finish();
                        }

                    }else{
                        String msg=jsonObject.getString("message");
                        ToastUtil.showToast(AddSchoolCommentActivity.this, msg);
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

    private void shopcomment(String comment, double score) {
        Map<String, Object> params = new HashMap<>();
        params.put("orderId", mCarOrder.getId());
        params.put("content", comment);
        params.put("isAnonymous", isAnonymous);
        params.put("score", score);
        if (!TextUtils.isEmpty(imgs))
            params.put("imgs", imgs.substring(1));
        String json=new Gson().toJson(params);
        HttpProxy.obtain().post(PlatformContans.Evaluation.addOrderEvaluation, MyApplication.token, json, new ICallBack() {
            @Override
            public void OnSuccess(String result) {
                Log.e("shopcomment", result);
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    int code = jsonObject.getInt("resultCode");
                    if (code == 0) {
                        count++;
                        if(count==2){
                            count=0;
                            ToastUtil.showToast(AddSchoolCommentActivity.this, "发布成功");
                            finish();

                        }
                    }else{
                        String msg=jsonObject.getString("message");
                        ToastUtil.showToast(AddSchoolCommentActivity.this, msg);
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
        iHandlerCallBack2 = new IHandlerCallBack() {
            @Override
            public void onStart() {
                Log.i(TAG, "onStart: 开启");
            }

            @Override
            public void onSuccess(List<String> photoList) {
                Log.i(TAG, "onSuccess: 返回数据");
                path2.clear();
                for (String s : photoList) {
                    Log.i(TAG, s);
                    path2.add(s);
                    upImage2(PlatformContans.Commom.uploadImg, new File(s));
                }
                mPhotoAdapter2.notifyDataSetChanged();
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

    public void upImage2(String url, File file) {
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
                Log.e("upload", "onResponse: " + imgs2);
                try {
                    JSONObject object = new JSONObject(string);
                    int resultCode = object.getInt("resultCode");
                    final String data = object.getString("data");
                    imgs2 = imgs2 + "," + data;
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
