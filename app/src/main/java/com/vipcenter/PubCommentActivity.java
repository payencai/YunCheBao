package com.vipcenter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.yunchebao.MyApplication;
import com.costans.PlatformContans;
import com.example.yunchebao.R;
import com.http.HttpProxy;
import com.http.ICallBack;
import com.iarcuschin.simpleratingbar.SimpleRatingBar;
import com.payencai.library.util.ToastUtil;
import com.tool.GlideImageLoader;
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

public class PubCommentActivity extends AppCompatActivity {
    private List<String> path = new ArrayList<>();
    private GalleryConfig galleryConfig;
    private IHandlerCallBack iHandlerCallBack;
    private String TAG = "---Yancy---";
    private Context mContext;
    private Activity mActivity;
    PhotoAdapter mPhotoAdapter;
    private final int PERMISSIONS_REQUEST_READ_CONTACTS = 8;
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
    String imgs = "";
    String id;
    int flag = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pub_comment);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        id = getIntent().getStringExtra("id");
        flag = getIntent().getIntExtra("flag", 0);
        mContext = this;
        mActivity = this;
        initGallery();
        tv_pub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content = et_content.getEditableText().toString();
                int score = (int) sb_score.getRating();
                if (TextUtils.isEmpty(content)) {
                    ToastUtil.showToast(PubCommentActivity.this, "请输入评论");
                    return;
                }
                if (score == 0) {
                    ToastUtil.showToast(PubCommentActivity.this, "请给出评分");
                    return;
                }
                if (flag == 1)
                    Washcomment(content, score);
                else {
                    Fourcomment(content, score);
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
                GalleryPick.getInstance().setGalleryConfig(galleryConfig).open(PubCommentActivity.this);
            }
        });

    }

    private void Fourcomment(String comment, int score) {
        Map<String, Object> params = new HashMap<>();
        params.put("orderId", id);
        params.put("content", comment);
        params.put("score", score);
        if (!TextUtils.isEmpty(imgs))
            params.put("imgs", imgs.substring(1));
        HttpProxy.obtain().post(PlatformContans.FourShop.addFourShopOrderComment, MyApplication.token, params, new ICallBack() {
            @Override
            public void OnSuccess(String result) {
                Log.e("result", result);
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    int code = jsonObject.getInt("resultCode");
                    if (code == 0) {
                        ToastUtil.showToast(PubCommentActivity.this, "发布成功");
                        finish();
                    } else {
                        String msg = jsonObject.getString("message");
                        ToastUtil.showToast(PubCommentActivity.this, msg);
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

    private void Washcomment(String comment, int score) {
        Map<String, Object> params = new HashMap<>();
        params.put("orderId", id);
        params.put("content", comment);
        params.put("score", score);
        if (!TextUtils.isEmpty(imgs))
            params.put("imgs", imgs.substring(1));
        HttpProxy.obtain().post(PlatformContans.CarWashRepairShop.addWashRepairOrderComment, MyApplication.token, params, new ICallBack() {
            @Override
            public void OnSuccess(String result) {
                Log.e("result", result);
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    int code = jsonObject.getInt("resultCode");
                    if (code == 0) {
                        ToastUtil.showToast(PubCommentActivity.this, "发布成功");
                        finish();
                    } else {
                        String msg = jsonObject.getString("message");
                        ToastUtil.showToast(PubCommentActivity.this, msg);
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
