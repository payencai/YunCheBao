package com.bbcircle;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
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

import com.application.MyApplication;
import com.bbcircle.data.CarFriendDetail;
import com.bbcircle.data.SeldDrvingDetail;
import com.bbcircle.view.NoScrollWebView;
import com.bumptech.glide.Glide;
import com.costans.PlatformContans;
import com.example.yunchebao.R;
import com.google.gson.Gson;
import com.http.HttpProxy;
import com.http.ICallBack;
import com.nohttp.sample.NoHttpBaseActivity;
import com.tool.ActivityAnimationUtils;
import com.tool.ActivityConstans;
import com.tool.SimpleCommonUtils;
import com.tool.UIControlUtils;
import com.tool.slideshowview.SlideShowView;
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
import sj.keyboard.EmoticonsKeyBoardPopWindow;
import sj.keyboard.adpater.PageSetAdapter;
import sj.keyboard.interfaces.EmoticonClickListener;
import sj.keyboard.widget.EmoticonsEditText;

/**
 * Created by sdhcjhss on 2018/1/8.
 */

public class DriverFriendsDetailActivity extends NoHttpBaseActivity {
    //轮播图片
    private List<Map<String, String>> imageList = new ArrayList<>();
    @BindView(R.id.slideshowView)
    SlideShowView slideShowView;
    private EmoticonsKeyBoardPopWindow mKeyBoardPopWindow;
    private Context ctx;

    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.createtime)
    TextView tv_createTime;
    @BindView(R.id.readNum)
    TextView tv_readNum;
    @BindView(R.id.name)
    TextView tv_name;
    @BindView(R.id.head)
    ImageView tv_head;
    @BindView(R.id.tv_heart)
    ImageView iv_heart;
    @BindView(R.id.ll_heart)
    LinearLayout ll_heart;
    @BindView(R.id.contacts)
    TextView tv_contact;
    @BindView(R.id.webView)
    NoScrollWebView mWebView;
    int id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.driver_friends_detail_layout);
        initView();
    }
    CarFriendDetail mSeldDrvingDetail;
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
            tv_title.setText(mSeldDrvingDetail.getTitle());
            tv_createTime.setText(mSeldDrvingDetail.getCreateTime());
            tv_readNum.setText(mSeldDrvingDetail.getReadNum() + "");
            initWebview(mSeldDrvingDetail.getContent());
            tv_name.setText(mSeldDrvingDetail.getName());
            initWebview(mSeldDrvingDetail.getContent());
            if(mSeldDrvingDetail.getIsCollection()==1){
                iv_heart.setImageResource(R.mipmap.orange_heart_icon);
            }else{
                //iv_heart.setImageResource(R.mipmap.white_heart_icon);
            }
            Glide.with(this).load(mSeldDrvingDetail.getHeadPortrait()).into(tv_head);
        }
    }
    private void getDatail() {
        Map<String, Object> params = new HashMap<>();
        params.put("userId",MyApplication.getUserInfo().getId());
        params.put("id", id);
        HttpProxy.obtain().get(PlatformContans.BabyCircle.getCarCommunicationCircleById, params, MyApplication.getUserInfo().getToken(), new ICallBack() {
            @Override
            public void OnSuccess(String result) {
                try {
                    Log.e("detail", MyApplication.getUserInfo().getToken());
                    JSONObject jsonObject = new JSONObject(result);
                    JSONObject data = jsonObject.getJSONObject("data");
                    mSeldDrvingDetail = new Gson().fromJson(data.toString(), CarFriendDetail.class);
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
        params.put("type", 2);
        String json = new Gson().toJson(params);
        HttpProxy.obtain().post(PlatformContans.Collect.addBabyCollection, MyApplication.getUserInfo().getToken(), json, new ICallBack() {
            @Override
            public void OnSuccess(String result) {
                if(mSeldDrvingDetail.getIsCollection()==0) {
                    Toast.makeText(DriverFriendsDetailActivity.this, "收藏成功", Toast.LENGTH_SHORT).show();
                    iv_heart.setImageResource(R.mipmap.orange_heart_icon);
                }
                else {
                    Toast.makeText(DriverFriendsDetailActivity.this, "取消成功", Toast.LENGTH_SHORT).show();
                    iv_heart.setImageResource(R.mipmap.collect_gray_hole);
                }
                getDatail();
               // Toast.makeText(DriverFriendsDetailActivity.this, "收藏成功", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(String error) {

            }
        });
    }

    private void initView() {
        ButterKnife.bind(this);
        UIControlUtils.UITextControlsUtils.setUIText(findViewById(R.id.title), ActivityConstans.UITag.TEXT_VIEW, "详情");
        ctx = this;
        //网络地址获取轮播图
        id=getIntent().getExtras().getInt("id");
        ll_heart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (MyApplication.isLogin) {
                    collect();
                } else {
                    startActivity(new Intent(DriverFriendsDetailActivity.this, RegisterActivity.class));
                }
            }
        });
        tv_contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("userid",mSeldDrvingDetail.getUserId());
                RongIM.getInstance().startPrivateChat(DriverFriendsDetailActivity.this, mSeldDrvingDetail.getUserId(), mSeldDrvingDetail.getName());
            }
        });
        getDatail();
//        imageList.clear();
//        for (int i = 0; i < 3; i++) {
//            Map<String, String> image_uri = new HashMap<String, String>();
//            image_uri.put("imageUrls", "http://image.tianjimedia.com/uploadImages/2015/217/32/17O1Z9FE863O.jpg");
////            image_uri.put("imageUris", adList.get(i).getCid());
//            imageList.add(image_uri);
//        }
//        slideShowView.setImageUrls(imageList);
    }

    private void initKeyBoardPopWindow(EmoticonsEditText editText) {
        mKeyBoardPopWindow = new EmoticonsKeyBoardPopWindow(ctx);

        EmoticonClickListener emoticonClickListener = SimpleCommonUtils.getCommonEmoticonClickListener(editText);
        PageSetAdapter pageSetAdapter = new PageSetAdapter();
        SimpleCommonUtils.addEmojiPageSetEntity(pageSetAdapter, this, emoticonClickListener);
        SimpleCommonUtils.addXhsPageSetEntity(pageSetAdapter, this, emoticonClickListener);
        mKeyBoardPopWindow.setAdapter(pageSetAdapter);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK
                && mKeyBoardPopWindow != null && mKeyBoardPopWindow.isShowing()) {
            mKeyBoardPopWindow.dismiss();
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mKeyBoardPopWindow != null && mKeyBoardPopWindow.isShowing()) {
            mKeyBoardPopWindow.dismiss();
        }
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
        final EmoticonsEditText editText = (EmoticonsEditText) view.findViewById(R.id.et_content);
        SimpleCommonUtils.initEmoticonsEditText(editText);
        initKeyBoardPopWindow(editText);
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
                if (mKeyBoardPopWindow != null && mKeyBoardPopWindow.isShowing()) {
                    mKeyBoardPopWindow.dismiss();
                } else {
                    if (mKeyBoardPopWindow == null) {
                        initKeyBoardPopWindow(editText);
                    }
//                    mKeyBoardPopWindow.showPopupWindow();
                    if (mKeyBoardPopWindow.isShowing()) {
                        mKeyBoardPopWindow.dismiss();
                    } else {
                        mKeyBoardPopWindow.showAsDropDown(view.findViewById(R.id.faceBtn), 0, 0);
                    }
                }
            }
        });


    }

    public void attenShareToast() {
        final View view = getLayoutInflater().inflate(R.layout.atten_comment_submit, null);
        RelativeLayout ll = (RelativeLayout) view.findViewById(R.id.ll_root);
        ll.getBackground().setAlpha(20);
        dialog = new Dialog(this, R.style.DialogStyleNoTitle);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable());
        dialog.setContentView(view);
        dialog.show();
        final EmoticonsEditText editText = (EmoticonsEditText) view.findViewById(R.id.et_content);
        SimpleCommonUtils.initEmoticonsEditText(editText);
        initKeyBoardPopWindow(editText);
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
}
