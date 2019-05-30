package com.cheyibao;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.yunchebao.MyApplication;
import com.cheyibao.adapter.ImageAdapter;
import com.common.BaseModel;
import com.common.EndLoadDataType;
import com.common.HandlerData;
import com.common.LoadDataType;
import com.common.ResourceUtils;
import com.common.UploadFile;
import com.coorchice.library.SuperTextView;
import com.costans.PlatformContans;
import com.example.yunchebao.R;
import com.google.gson.reflect.TypeToken;
import com.http.HttpProxy;
import com.http.ICallBack;
import com.iarcuschin.simpleratingbar.SimpleRatingBar;
import com.payencai.library.util.ToastUtil;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.tool.GlideImageEngine;
import com.tool.MyProgressDialog;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.internal.entity.CaptureStrategy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

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
    private String imgs;

    @SuppressLint("CheckResult")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = this;
        setContentView(R.layout.activity_rent_car_order_comment);
        ButterKnife.bind(this);
        title.setText("发表评论");
        orderId = getIntent().getStringExtra("rent_car_order_id");
        textBtn.setText("发布");
        textBtn.setTextColor(ResourceUtils.getColorByResource(this, R.color.yellow_65));
        textBtn.setVisibility(View.VISIBLE);
        List<ImageAdapter.Self> selfList = new ArrayList<>();
        selfList.add(new ImageAdapter.Self());
        adapter = new ImageAdapter(selfList);
        imageListView.setLayoutManager(new GridLayoutManager(this, 4));
        adapter.bindToRecyclerView(imageListView);
        adapter.setOnItemChildClickListener((a, view, position) -> {
            if (view.getId() == R.id.delete_photo_view) {
                adapter.remove(position);
                mSelected.remove(position);
                ImageAdapter.Self self = adapter.getItem(adapter.getItemCount() - 1);
                if (self != null && (self.getUri() != null || !TextUtils.isEmpty(self.getUrl()))) {
                    adapter.addData(new ImageAdapter.Self());
                }
            }
        });
        adapter.setOnItemClickListener((a, view, position) -> {
            ImageAdapter.Self self = adapter.getItem(position);
            if (self != null && self.getUri() == null && TextUtils.isEmpty(self.getUrl())) {
                if (TextUtils.isEmpty(self.getUrl())) {
                    int max = 4 - (mSelected == null ? 0 : mSelected.size());
                    if (max > 0) {
                        RxPermissions rxPermissions = new RxPermissions(this);
                        rxPermissions.request(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE).subscribe(granted -> {
                            if (granted) {
                                Matisse.from(activity)
                                        .choose(MimeType.ofImage())
                                        .countable(true)
                                        .maxSelectable(max)
                                        .capture(true)
                                        .originalEnable(true)
                                        .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
                                        .thumbnailScale(0.85f)
                                        .captureStrategy(new CaptureStrategy(true, "com.yancy.gallerypickdemo.fileprovider"))
                                        .imageEngine(new GlideImageEngine())
                                        .forResult(3);
                            }
                        });
                    }

                }
            }

        });

        int score = (int) starbar.getRating();
        scoreView.setText(String.format("%s", score));
        starbar.setOnRatingBarChangeListener((simpleRatingBar, rating, fromUser) -> {
            int score1 = (int) starbar.getRating();
            if (score1 < 1) {
                score1 = 1;
                starbar.setRating(1);
            }
            scoreView.setText(String.format("%s", score1));
        });
    }

    @OnClick(R.id.back)
    public void onBackClicked() {
       finish();
    }

    @OnClick(R.id.textBtn)
    public void onShareBtnClicked() {
        UploadFile uploadFile = new UploadFile(this);
        MyProgressDialog.show(activity,"正在上传图片");
        uploadFile.upLoadFile(mSelected, new UploadFile.OnFileUploadListener() {
            @Override
            public void onFileUploadSucess(String images) {
                imgs = images;
                runOnUiThread(() -> {
                    MyProgressDialog.dismiss();
                    loadDataType.submitData();
                });
            }

            @Override
            public void onFileUploadFailed(String message) {
                runOnUiThread(() -> {
                    ToastUtil.showToast(activity, message);
                    MyProgressDialog.dismiss();
                });
            }
        });
    }

    private LoadDataType loadDataType = new LoadDataType() {
        @Override
        public Map<String, Object> initParam() {
            String commentContent = commentContentView.getText().toString();
            if (TextUtils.isEmpty(commentContent)) {
                ToastUtil.showToast(activity, "评论内容不能为空");
                return null;
            }

            int score = Integer.parseInt(scoreView.getText().toString());
            if (score < 1) {
                ToastUtil.showToast(activity, "请至少点亮一颗星，给点鼓励！");
                return null;
            }

            Map<String, Object> map = new HashMap<>();
            map.put("orderId", orderId);
            map.put("content", commentContentView.getText().toString());
            map.put("score", Integer.parseInt(scoreView.getText().toString()));
            map.put("imgs", imgs);
            return map;
        }

        @Override
        public void submitData() {
            HttpProxy.obtain().post(PlatformContans.CarRent.addRentCarComment, MyApplication.token, initParam(), new ICallBack() {
                @Override
                public void OnSuccess(String result) {
                    HandlerData.handlerData(result, new TypeToken<BaseModel<String>>() {
                    }.getType(), new EndLoadDataType<String>() {
                        @Override
                        public void onFailed() {
                            ToastUtil.showToast(activity, "评论发布失败");
                        }

                        @Override
                        public void onSuccess(String s) {
                            if (!TextUtils.isEmpty(s)) {
                                ToastUtil.showToast(activity, s);
                            }
                        }

                        @Override
                        public void onSuccessBaseModel(BaseModel baseModel) {
                            if (baseModel != null) {
                                ToastUtil.showToast(activity, baseModel.getMessage());
                            }
                        }
                    });
                }

                @Override
                public void onFailure(String error) {
                    ToastUtil.showToast(activity, "评论发布失败");
                }
            });
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 3 && data != null) {
            if (mSelected == null) mSelected = new ArrayList<>();
            mSelected.addAll(Matisse.obtainResult(data));
            List<ImageAdapter.Self> selfList = new ArrayList<>();
            for (int i = 0; i < mSelected.size(); i++) {
                Uri uri = mSelected.get(i);
                selfList.add(new ImageAdapter.Self(uri));
            }
            if (selfList.size() < 4) {
                selfList.add(new ImageAdapter.Self());
            }
            adapter.setNewData(selfList);
        }

    }

}
