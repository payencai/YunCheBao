package com.bbcircle;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yunchebao.MyApplication;
import com.bbcircle.adapter.ArticleCommentListAdapter;
import com.bbcircle.adapter.CarShowMiddleListAdapter;
import com.bbcircle.data.RaceDetail;
import com.bbcircle.view.NoScrollWebView;
import com.bbcircle.view.SampleCoverVideo;
import com.bumptech.glide.Glide;
import com.costans.PlatformContans;
import com.entity.PhoneCommentEntity;
import com.entity.PhoneMagEntity;
import com.example.yunchebao.R;
import com.google.gson.Gson;
import com.http.HttpProxy;
import com.http.ICallBack;
import com.nohttp.sample.NoHttpBaseActivity;
import com.shuyu.gsyvideoplayer.GSYVideoManager;
import com.tool.ActivityConstans;
import com.tool.UIControlUtils;
import com.tool.view.HorizontalListView;
import com.tool.view.ListViewForScrollView;
import com.vipcenter.RegisterActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.rong.imkit.RongIM;


public class RaceDetailActivity extends NoHttpBaseActivity {

    private Context ctx;
    List<PhoneMagEntity> horiList;
    @BindView(R.id.horiListview)
    HorizontalListView horizontalListView;
    CarShowMiddleListAdapter horiAdapter;
    @BindView(R.id.listView)
    ListViewForScrollView listView;
    private List<PhoneCommentEntity> commentList;
    private ArticleCommentListAdapter commentAdapter;
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.createtime)
    TextView tv_createTime;
    @BindView(R.id.watchNum)
    TextView tv_readNum;
    @BindView(R.id.name)
    TextView tv_name;
    @BindView(R.id.head)
    ImageView tv_head;
    @BindView(R.id.tv_heart)
    ImageView iv_heart;
    @BindView(R.id.ll_heart)
    LinearLayout ll_heart;
    @BindView(R.id.tv_contact)
    TextView tv_contact;
    @BindView(R.id.webView)
    NoScrollWebView mWebView;
    @BindView(R.id.video)
    SampleCoverVideo mSampleCoverVideo;
    int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = LayoutInflater.from(this).inflate(R.layout.activity_race_detail,null);
        setContentView(view);
        commHiddenKeyboard(view);
        initView();
    }
    RaceDetail mSeldDrvingDetail;
    private void initWebview(String url) {
        WebSettings settings = mWebView.getSettings();
        mWebView.requestFocusFromTouch();
        settings.setJavaScriptEnabled(true);  //支持js
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN); //支持内容重新布局
        settings.setJavaScriptCanOpenWindowsAutomatically(true); //支持通过JS打开新窗口
        settings.setLoadsImagesAutomatically(true);  //支持自动加载图片
        settings.setDefaultTextEncodingName("utf-8");//设置编码格式

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            settings.setBlockNetworkImage(false);
        } else {
            settings.setBlockNetworkImage(true);//图片最后加载，
        }
        mWebView.setWebChromeClient(new WebChromeClient() {

        });
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
        mWebView.loadUrl(url);
    }
    private void setUi() {
        if (mSeldDrvingDetail != null) {
            mSampleCoverVideo.setUpLazy(mSeldDrvingDetail.getVideo(),true,null,null,"");
            mSampleCoverVideo.getTitleTextView().setVisibility(View.GONE);
            mSampleCoverVideo.getBackButton().setVisibility(View.GONE);

            mSampleCoverVideo.getFullscreenButton().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mSampleCoverVideo.startWindowFullscreen(v.getContext(), false, true);
                }
            });
            mSampleCoverVideo.loadCoverImage(mSeldDrvingDetail.getVideo(),R.mipmap.pic1);
            tv_title.setText(mSeldDrvingDetail.getTitle());
            tv_createTime.setText(mSeldDrvingDetail.getCreateTime());
            tv_readNum.setText(mSeldDrvingDetail.getReadNum() + "次播放");
            initWebview(mSeldDrvingDetail.getContent());
            tv_name.setText(mSeldDrvingDetail.getName());
            initWebview(mSeldDrvingDetail.getContent());
            if(mSeldDrvingDetail.getIsCollection()==1){
                iv_heart.setImageResource(R.mipmap.orange_heart_icon);
            }else{
                iv_heart.setImageResource(R.mipmap.collect_gray_hole);
            }
            Glide.with(this).load(mSeldDrvingDetail.getImage()).into(tv_head);
        }
    }
    private void getDatail() {
        Map<String, Object> params = new HashMap<>();
        params.put("userId",MyApplication.getUserInfo().getId());
        params.put("id", id);
        HttpProxy.obtain().get(PlatformContans.BabyCircle.getMatchCircleById, params, MyApplication.token, new ICallBack() {
            @Override
            public void OnSuccess(String result) {
                try {
                    Log.e("detail", result);
                    JSONObject jsonObject = new JSONObject(result);
                    JSONObject data = jsonObject.getJSONObject("data");
                    mSeldDrvingDetail = new Gson().fromJson(data.toString(), RaceDetail.class);
                    setUi();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(String error) {

            }
        });
    }
    private void collect() {
        Map<String, Object> params = new HashMap<>();
        params.put("circleId", mSeldDrvingDetail.getId());
        params.put("image", mSeldDrvingDetail.getImage());
        params.put("title", mSeldDrvingDetail.getTitle());
        params.put("type", 4);
        String json = new Gson().toJson(params);
        HttpProxy.obtain().post(PlatformContans.Collect.addBabyCollection, MyApplication.token, json, new ICallBack() {
            @Override
            public void OnSuccess(String result) {
                if(mSeldDrvingDetail.getIsCollection()==0) {
                    Toast.makeText(RaceDetailActivity.this, "收藏成功", Toast.LENGTH_SHORT).show();
                    iv_heart.setImageResource(R.mipmap.orange_heart_icon);
                }
                else {
                    Toast.makeText(RaceDetailActivity.this, "取消成功", Toast.LENGTH_SHORT).show();
                    iv_heart.setImageResource(R.mipmap.collect_gray_hole);
                }
                getDatail();
              //  Toast.makeText(RaceDetailActivity.this, "收藏成功", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(String error) {

            }
        });
    }
    private void initView() {
        ButterKnife.bind(this);
        UIControlUtils.UITextControlsUtils.setUIText(findViewById(R.id.title), ActivityConstans.UITag.TEXT_VIEW, "赛事发布");
        ctx = this;
        horiList = new ArrayList<PhoneMagEntity>();
        horiAdapter = new CarShowMiddleListAdapter(ctx, horiList);
        horizontalListView.setAdapter(horiAdapter);
        commentList = new ArrayList<>();
        commentAdapter = new ArticleCommentListAdapter(ctx, commentList, this);
        listView.setAdapter(commentAdapter);
        Bundle bundle=getIntent().getExtras();
        id=bundle.getInt("id");
        ll_heart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (MyApplication.isLogin) {
                    collect();
                } else {
                    startActivity(new Intent(RaceDetailActivity.this, RegisterActivity.class));
                }
            }
        });
        tv_contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("userid",mSeldDrvingDetail.getUserId());
                RongIM.getInstance().startPrivateChat(RaceDetailActivity.this, mSeldDrvingDetail.getUserId(), mSeldDrvingDetail.getName());
            }
        });
        getDatail();
    }



    /**
     * 自定义Toast信息显示面板,我要报名
     *
     * @Title: commonToastDefined
     * @Description: TODO
     * @param @param text
     * @return void
     * @throws
     */
    Dialog dialog;

    public void attenToast() {
        final View view = getLayoutInflater().inflate(R.layout.atten_comment_submit, null);
        RelativeLayout ll = (RelativeLayout) view.findViewById(R.id.ll_root);
        ll.getBackground().setAlpha(20);
        dialog = new Dialog(this, R.style.DialogStyleNoTitle);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable());
        dialog.setContentView(view);
        dialog.show();



        ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dialog != null && dialog.isShowing()) {
                    dialog.dismiss();
                }
            }
        });
        view.findViewById(R.id.cancelBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dialog != null && dialog.isShowing()) {
                    dialog.dismiss();
                }
            }
        });
        view.findViewById(R.id.submitBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dialog != null && dialog.isShowing()) {
                    dialog.dismiss();
                }
            }
        });
        view.findViewById(R.id.faceBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


    }



    @OnClick({R.id.back, R.id.commentLay, R.id.shareBtn})
    public void OnClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                onBackPressed();
                break;
            case R.id.commentLay:
                attenToast();
                break;
            case R.id.shareBtn:
                setToast("第三方");
//                attenShareToast();
                break;
        }
    }
    @Override
    public void onBackPressed() {
        if (GSYVideoManager.backFromWindowFull(this)) {
            return;
        }
        super.onBackPressed();
    }

    @Override
    protected void onPause() {
        super.onPause();
        GSYVideoManager.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        GSYVideoManager.onResume();
    }


}
