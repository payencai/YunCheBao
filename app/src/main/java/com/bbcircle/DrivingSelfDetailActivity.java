package com.bbcircle;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.application.MyApplication;
import com.bbcircle.data.SeldDrvingDetail;
import com.bbcircle.view.NoScrollWebView;
import com.bumptech.glide.Glide;
import com.costans.PlatformContans;
import com.example.yunchebao.R;
import com.google.gson.Gson;
import com.http.HttpProxy;
import com.http.ICallBack;
import com.nohttp.sample.NoHttpBaseActivity;
import com.payencai.library.util.ToastUtil;
import com.rongcloud.activity.contact.FriendDetailActivity;
import com.tool.ActivityAnimationUtils;
import com.tool.ActivityConstans;
import com.tool.UIControlUtils;
import com.tool.slideshowview.SlideShowView;
import com.vipcenter.RegisterActivity;
import com.xihubao.WashCarDetailActivity;

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

/**
 * Created by sdhcjhss on 2018/1/8.
 */

public class DrivingSelfDetailActivity extends NoHttpBaseActivity {
    //轮播图片
    private List<Map<String, String>> imageList = new ArrayList<>();
    @BindView(R.id.slideshowView)
    SlideShowView slideShowView;
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.createtime)
    TextView tv_createTime;
    @BindView(R.id.readNum)
    TextView tv_readNum;
    @BindView(R.id.sedtime)
    TextView tv_sedtime;
    @BindView(R.id.aleady)
    TextView tv_aleady;
    @BindView(R.id.tv_detail)
    TextView tv_detail;
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
    int id;
    int type = 0;
    SeldDrvingDetail mSeldDrvingDetail;
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
            Map<String, String> image_uri = new HashMap<String, String>();
            image_uri.put("imageUrls", mSeldDrvingDetail.getImage());
            imageList.add(image_uri);
            slideShowView.setImageUrls(imageList);
            if(mSeldDrvingDetail.getList()!=null)
            tv_aleady.setText("已有" + mSeldDrvingDetail.getList().size() + "人报名");
            tv_title.setText(mSeldDrvingDetail.getTitle());
            tv_createTime.setText(mSeldDrvingDetail.getCreateTime());
            tv_readNum.setText(mSeldDrvingDetail.getReadNum() + "");
            tv_sedtime.setText(mSeldDrvingDetail.getStartTime() + "至" + mSeldDrvingDetail.getEndTime());
           // tv_detail.setText(mSeldDrvingDetail.getContent());
            initWebview(mSeldDrvingDetail.getContent());
            tv_name.setText(mSeldDrvingDetail.getName());
            if(mSeldDrvingDetail.getIsCollection()==1){
                iv_heart.setImageResource(R.mipmap.orange_heart_icon);
            }else{
                //iv_heart.setImageResource(R.mipmap.white_heart_icon);
            }
            Glide.with(this).load(mSeldDrvingDetail.getHeadPortrait()).into(tv_head);
        }
    }

    private void collect() {
        Map<String, Object> params = new HashMap<>();
        params.put("circleId", mSeldDrvingDetail.getId());
        params.put("image", mSeldDrvingDetail.getImage());
        params.put("title", mSeldDrvingDetail.getTitle());
        params.put("type", 1);
        String json = new Gson().toJson(params);
        HttpProxy.obtain().post(PlatformContans.Collect.addBabyCollection, MyApplication.getUserInfo().getToken(), json, new ICallBack() {
            @Override
            public void OnSuccess(String result) {
                if(mSeldDrvingDetail.getIsCollection()==0) {
                    Toast.makeText(DrivingSelfDetailActivity.this, "收藏成功", Toast.LENGTH_SHORT).show();
                    iv_heart.setImageResource(R.mipmap.orange_heart_icon);
                }
                else {
                    Toast.makeText(DrivingSelfDetailActivity.this, "取消成功", Toast.LENGTH_SHORT).show();
                    iv_heart.setImageResource(R.mipmap.collect_gray_hole);
                }
                getDatail();
                //Toast.makeText(DrivingSelfDetailActivity.this, "收藏成功", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(String error) {

            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.driving_self_detail_layout);
        initView();
    }

    private void initView() {
        ButterKnife.bind(this);
        UIControlUtils.UITextControlsUtils.setUIText(findViewById(R.id.title), ActivityConstans.UITag.TEXT_VIEW, "详情");
        id = getIntent().getExtras().getInt("id");
        //网络地址获取轮播图
        imageList.clear();
        ll_heart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (MyApplication.isLogin) {
                    collect();
                } else {
                    startActivity(new Intent(DrivingSelfDetailActivity.this, RegisterActivity.class));
                }
            }
        });
        tv_contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                RongIM.getInstance().startPrivateChat(DrivingSelfDetailActivity.this, mSeldDrvingDetail.getUserId(), mSeldDrvingDetail.getName());
            }
        });
        getDatail();
    }


    private void getDatail() {
        Map<String, Object> params = new HashMap<>();
        params.put("userId",MyApplication.getUserInfo().getId());
        params.put("id", id);
        HttpProxy.obtain().get(PlatformContans.BabyCircle.getSelfDrivingCircleDetailsById, params, MyApplication.getUserInfo().getToken(), new ICallBack() {
            @Override
            public void OnSuccess(String result) {
                try {
                    Log.e("detai", MyApplication.getUserInfo().getToken());
                    JSONObject jsonObject = new JSONObject(result);
                    JSONObject data = jsonObject.getJSONObject("data");
                    mSeldDrvingDetail = new Gson().fromJson(data.toString(), SeldDrvingDetail.class);
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
        View view = getLayoutInflater().inflate(R.layout.atten_apply_submit, null);
        RelativeLayout ll = (RelativeLayout) view.findViewById(R.id.ll_root);
        EditText name= (EditText) view.findViewById(R.id.name);
        EditText phone= (EditText) view.findViewById(R.id.phone);
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
                postEnter(name.getEditableText().toString(),phone.getEditableText().toString());

            }
        });

    }
    private void postEnter(String name,String phone){
        Map<String,Object> params=new HashMap<>();
        params.put("id",mSeldDrvingDetail.getId());
        params.put("name",name);
        params.put("telephone",phone);
        HttpProxy.obtain().post(PlatformContans.BabyCircle.addSelfDrivingEnter, MyApplication.getUserInfo().getToken(), params, new ICallBack() {
            @Override
            public void OnSuccess(String result) {
                Log.e("enter",result);
                try {
                    JSONObject jsonObject=new JSONObject(result);
                    String msg=jsonObject.getString("message");
                    int code=jsonObject.getInt("resultCode");
                    if(code==0){
                        ActivityAnimationUtils.commonTransition(DrivingSelfDetailActivity.this, DrivingSelfReplaySuccessActivity.class, ActivityConstans.Animation.FADE);
                        ToastUtil.showToast(DrivingSelfDetailActivity.this,"报名成功");
                    }else{
                        ToastUtil.showToast(DrivingSelfDetailActivity.this,msg);
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
    @OnClick({R.id.back, R.id.submitBtn})
    public void OnClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                onBackPressed();
                break;
            case R.id.submitBtn:
                attenToast();
                break;
        }
    }
}
