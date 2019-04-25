package com.cheyibao;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.cheyibao.adapter.ImageAdapter;
import com.common.ResourceUtils;
import com.coorchice.library.SuperTextView;
import com.costans.PlatformContans;
import com.example.yunchebao.R;
import com.iarcuschin.simpleratingbar.SimpleRatingBar;
import com.tool.FileUtil;
import com.tool.GlideImageEngine;
import com.tool.StringUtils;
import com.yuedan.PubRoadActivity;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.internal.entity.CaptureStrategy;

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

public class RentCarOrderCommentActivity extends AppCompatActivity {

    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.searchLay)
    LinearLayout searchLay;
    @BindView(R.id.superText)
    SuperTextView superText;
    @BindView(R.id.textBtn)
    TextView textBtn;
    @BindView(R.id.shareBtn)
    ImageView shareBtn;
    @BindView(R.id.shopCartBtn)
    ImageView shopCartBtn;
    @BindView(R.id.menuBtn)
    ImageView menuBtn;
    @BindView(R.id.userBtn)
    ImageView userBtn;
    @BindView(R.id.starbar)
    SimpleRatingBar starbar;
    @BindView(R.id.score_view)
    TextView scoreView;
    @BindView(R.id.comment_content_view)
    EditText commentContentView;
    @BindView(R.id.image_list_view)
    RecyclerView imageListView;

    private String orderId;
    private ImageAdapter adapter;

    private Activity activity;

    private List<Uri> mSelected;
    private List<String> images;
    private String imgs;;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = this;
        setContentView(R.layout.activity_rent_car_order_comment);
        ButterKnife.bind(this);
        title.setText("发表评论");
        orderId = getIntent().getStringExtra("rent_car_order_id");
        textBtn.setText("发布");
        textBtn.setTextColor(ResourceUtils.getColorByResource(this,R.color.yellow_65));
        textBtn.setVisibility(View.VISIBLE);
        List<ImageAdapter.Self> selfList = new ArrayList<>();
        selfList.add(new ImageAdapter.Self());
        adapter = new ImageAdapter(selfList);
        imageListView.setLayoutManager(new GridLayoutManager(this,4));
        adapter.bindToRecyclerView(imageListView);
        adapter.setOnItemClickListener((a, view, position) -> {
            ImageAdapter.Self self = adapter.getItem(position);
            if (self!=null){
                if (TextUtils.isEmpty(self.getUrl())){
                    Matisse.from(activity)
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

    @OnClick(R.id.back)
    public void onBackClicked() {
        onBackPressed();
    }

    @OnClick(R.id.textBtn)
    public void onShareBtnClicked() {
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 3 && data!=null){
            mSelected = Matisse.obtainResult(data);
            List<ImageAdapter.Self> selfList = new ArrayList<>();
            for (int i = 0; i < mSelected.size(); i++) {
                Uri uri = mSelected.get(i);
                selfList.add(new ImageAdapter.Self(uri));
            }
            if (selfList.size()< 4){
                selfList.add(new ImageAdapter.Self());
            }
            adapter.setNewData(selfList);
        }

    }



    public void setImages(Intent data) {
        mSelected = Matisse.obtainResult(data);
        for (int i = 0; i < mSelected.size(); i++) {
            File fileByUri = FileUtil.getFileByUri(Matisse.obtainResult(data).get(i), this);
            Luban.with(this)
                    .load(fileByUri)
                    .ignoreBy(100)
                    .filter(path -> !(TextUtils.isEmpty(path) || path.toLowerCase().endsWith(".gif")))
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
                        imgs= StringUtils.listToString2(images,',');
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
//                            mImageAdapter.notifyDataSetChanged();
                        }
                    });


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
    }
}
