package com.vipcenter;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.yunchebao.MyApplication;
import com.comment.Comment;
import com.comment.CommentReuslt;
import com.comment.EvaluationChoiceImageView;
import com.costans.PlatformContans;
import com.entity.PhoneOrderEntity;
import com.example.yunchebao.R;
import com.google.gson.Gson;
import com.http.HttpProxy;
import com.http.ICallBack;

import com.example.yunchebao.maket.adapter.CommentAdapter;
import com.nohttp.sample.NoHttpBaseActivity;
import com.payencai.library.util.ToastUtil;
import com.tool.FileUtil;
import com.tool.GlideImageEngine;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
 * Created by sdhcjhss on 2018/1/5.
 */

public class OrderCommentSubmitActivity extends NoHttpBaseActivity {

    PhoneOrderEntity mPhoneOrderEntity;
    String imgs = "";
    int isAnonymous = 1;
    @BindView(R.id.tv_pub)
    TextView tv_pub;
    @BindView(R.id.rv_comment)
    RecyclerView rv_comment;
    CommentAdapter mCommentAdapter;
    int position;
    private EvaluationChoiceImageView mTempEvaluationChoiceImageView;//存放临时的EvaluationChoiceImageView
    List<PhoneOrderEntity.ItemListBean> mItemListBeans = new ArrayList<>();
    List<CommentReuslt> mCommentReuslts = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_comment_submit_layout);
        ButterKnife.bind(this);
        mPhoneOrderEntity = (PhoneOrderEntity) getIntent().getSerializableExtra("data");
        mPhoneOrderEntity.getItemList();
        initView();
    }

    private void initView() {
        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mItemListBeans.addAll(mPhoneOrderEntity.getItemList());
        mCommentAdapter = new CommentAdapter(R.layout.item_goods_comment, mPhoneOrderEntity.getItemList());
        mCommentAdapter.setOnAddImageListener(new CommentAdapter.OnAddImageListener() {
            @Override
            public void addImage(int pos, EvaluationChoiceImageView evaluationChoiceImageView) {
                mTempEvaluationChoiceImageView = evaluationChoiceImageView;
                position = pos;
                Matisse.from(OrderCommentSubmitActivity.this)
                        .choose(MimeType.ofImage())
                        .countable(true)
                        .maxSelectable(1)
                        .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
                        .thumbnailScale(0.85f)
                        .imageEngine(new GlideImageEngine())
                        .forResult(188);
            }
        });
        rv_comment.setLayoutManager(new LinearLayoutManager(this));
        rv_comment.setAdapter(mCommentAdapter);
        for (int i = 0; i < mItemListBeans.size(); i++) {
            PhoneOrderEntity.ItemListBean itemListBean = mItemListBeans.get(i);
            CommentReuslt commentReuslt = new CommentReuslt();
            commentReuslt.setOrderItemId(itemListBean.getId());
            mCommentReuslts.add(commentReuslt);
        }
    }

    List<Uri> mSelected;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 188 && resultCode == RESULT_OK) {
            mSelected = Matisse.obtainResult(data);
            File fileByUri = FileUtil.getFileByUri(Matisse.obtainResult(data).get(0), this);
//            压缩文件
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
                            mTempEvaluationChoiceImageView.addImage(file.getAbsolutePath());
                            upImage(PlatformContans.Commom.uploadImg, file, position);
                        }

                        @Override
                        public void onError(Throwable e) {
                        }
                    }).launch();

        }
    }

    public void upImage(String url, File file, int position) {
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
                Log.e("upload", "onResponse: error" + e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String string = response.body().string();
                Log.e("upload", "onResponse: " + string);
                try {
                    JSONObject object = new JSONObject(string);
                    int resultCode = object.getInt("resultCode");
                    final String data = object.getString("data");
                    mCommentReuslts.get(position).getImages().add(data);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
    }

    public void saveImgs(int position, String imgs) {
        PhoneOrderEntity.ItemListBean itemListBean = mItemListBeans.get(position);
        itemListBean.setImgs(imgs);
        mItemListBeans.remove(position);
        mItemListBeans.add(position, itemListBean);
        //mCommentAdapter.setNewData(mItemListBeans);
    }

    public void saveContent(int position, String content) {
        mCommentReuslts.get(position).setContent(content);
    }

    public void saveScore(int position, float score) {
        mCommentReuslts.get(position).setScore((int) score);
    }

    public void saveIsShow(int position, int isShow) {
        mCommentReuslts.get(position).setIsRealName(isShow);
    }

    private void shopcomment(String json) {

        HttpProxy.obtain().post(PlatformContans.GoodsOrder.addBabyMerchantComment, MyApplication.token, json, new ICallBack() {
            @Override
            public void OnSuccess(String result) {
                Log.e("result", result);
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    int code = jsonObject.getInt("resultCode");
                    if (code == 0) {
                        ToastUtil.showToast(OrderCommentSubmitActivity.this, "发布成功");
                        finish();
                    } else {
                        String msg = jsonObject.getString("message");
                        ToastUtil.showToast(OrderCommentSubmitActivity.this, msg);
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

    @OnClick({R.id.tv_pub})
    public void OnClick(View v) {
        switch (v.getId()) {
            case R.id.tv_pub:
                List<Comment> comments = new ArrayList<>();
                for (int i = 0; i < mCommentReuslts.size(); i++) {
                    CommentReuslt commentReuslt = mCommentReuslts.get(i);
                    Comment comment = new Comment();
                    comment.setContent(commentReuslt.getContent());
                    comment.setImgs(commentReuslt.getImgs());
                    comment.setIsRealName(commentReuslt.getIsRealName());
                    comment.setScore(commentReuslt.getScore());
                    comment.setOrderItemId(commentReuslt.getOrderItemId());
                    comments.add(comment);
                }
                String json = new Gson().toJson(comments);
                json = "{\n" +
                        "  \"data\": " + json + "}";
                Log.e("mCommentReuslts", json);
                shopcomment(json);
                break;


        }
    }
}
