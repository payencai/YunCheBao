package com.bbcircle;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
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
import com.bbcircle.adapter.ArticleCommentListAdapter;
import com.bbcircle.adapter.CarShowMiddleListAdapter;
import com.bbcircle.adapter.CircleCommentAdapter;
import com.bbcircle.data.CarFriendDetail;
import com.bbcircle.data.CarshowDetail;
import com.bbcircle.data.CircleComment;
import com.bbcircle.view.NoScrollWebView;
import com.bbcircle.view.SampleCoverVideo;
import com.bbcircle.view.SoftKeyBoardListener;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
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
import com.tool.SimpleCommonUtils;
import com.tool.UIControlUtils;
import com.tool.slideshowview.SlideShowView;
import com.tool.view.HorizontalListView;
import com.tool.view.ListViewForScrollView;
import com.vipcenter.RegisterActivity;

import org.json.JSONArray;
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

public class CarShowDetailActivity extends NoHttpBaseActivity {
    private EmoticonsKeyBoardPopWindow mKeyBoardPopWindow;
    private Context ctx;
    List<PhoneMagEntity> horiList;

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
    @BindView(R.id.tv_contact)
    TextView tv_contact;
    @BindView(R.id.webView)
    NoScrollWebView mWebView;
    @BindView(R.id.video)
    SampleCoverVideo mSampleCoverVideo;
    @BindView(R.id.rv_comment)
    RecyclerView rv_comment;
    @BindView(R.id.et_comment)
    EditText et_comment;
    @BindView(R.id.tv_pub)
    TextView tv_pub;
    CircleCommentAdapter mCircleCommentAdapter;
    List<CircleComment> mCircleComments = new ArrayList<>();
    int page = 1;
    String commentId;
    int id;
    int type;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = LayoutInflater.from(this).inflate(R.layout.car_show_detail_layout,null);
        setContentView(view);
        commHiddenKeyboard(view);
        initView();
    }
    CarshowDetail mSeldDrvingDetail;
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
            Glide.with(this).load(mSeldDrvingDetail.getHeadPortrait()).into(tv_head);
            getComment();
        }

    }
    private void getDatail() {
        Map<String, Object> params = new HashMap<>();
        params.put("userId",MyApplication.getUserInfo().getId());
        params.put("id", id);
        HttpProxy.obtain().get(PlatformContans.BabyCircle.getCarShowCircleDetailsById, params, MyApplication.getUserInfo().getToken(), new ICallBack() {
            @Override
            public void OnSuccess(String result) {
                try {
                    Log.e("detail", MyApplication.getUserInfo().getToken());
                    JSONObject jsonObject = new JSONObject(result);
                    JSONObject data = jsonObject.getJSONObject("data");
                    mSeldDrvingDetail = new Gson().fromJson(data.toString(), CarshowDetail.class);
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
        params.put("type", 3);
        String json = new Gson().toJson(params);
        HttpProxy.obtain().post(PlatformContans.Collect.addBabyCollection, MyApplication.getUserInfo().getToken(), json, new ICallBack() {
            @Override
            public void OnSuccess(String result) {
                Log.e("heart", result);
                if(mSeldDrvingDetail.getIsCollection()==0){
                    iv_heart.setImageResource(R.mipmap.orange_heart_icon);
                    Toast.makeText(CarShowDetailActivity.this, "收藏成功", Toast.LENGTH_SHORT).show();}
                else {
                    iv_heart.setImageResource(R.mipmap.collect_gray_hole);
                    Toast.makeText(CarShowDetailActivity.this, "取消成功", Toast.LENGTH_SHORT).show();
                }
                getDatail();
            }

            @Override
            public void onFailure(String error) {

            }
        });
    }
    private void initView() {
        ButterKnife.bind(this);
        UIControlUtils.UITextControlsUtils.setUIText(findViewById(R.id.title), ActivityConstans.UITag.TEXT_VIEW, "汽车秀");
        ctx = this;
        horiList = new ArrayList<PhoneMagEntity>();


        commentList = new ArrayList<>();
        commentAdapter = new ArticleCommentListAdapter(ctx, commentList, this);
        listView.setAdapter(commentAdapter);
        Bundle bundle=getIntent().getExtras();
        id=bundle.getInt("id");
        type=bundle.getInt("type");
        iv_heart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (MyApplication.isLogin) {
                    collect();
                } else {
                    startActivity(new Intent(CarShowDetailActivity.this, RegisterActivity.class));
                }
            }
        });
        tv_contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("userid",mSeldDrvingDetail.getUserId());
                RongIM.getInstance().startPrivateChat(CarShowDetailActivity.this, mSeldDrvingDetail.getUserId(), mSeldDrvingDetail.getName());
            }
        });

        SoftKeyBoardListener.setListener(CarShowDetailActivity.this, new SoftKeyBoardListener.OnSoftKeyBoardChangeListener() {
            @Override
            public void keyBoardShow(int height) {
                //Toast.makeText(getActivity(), "键盘显示 高度" + height, Toast.LENGTH_SHORT).show();
                //introl_iv.setBackground(null);  //使LOGO消失
            }
            @Override
            public void keyBoardHide(int height) {
                commentId="";
                // Toast.makeText(DriverFriendsDetailActivity.this, "键盘隐藏 高度" + height, Toast.LENGTH_SHORT).show();
            }
        });


        mCircleCommentAdapter = new CircleCommentAdapter(R.layout.item_circle_comment, mCircleComments);
        mCircleCommentAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                if (view.getId() == R.id.tv_reply) {
                    CircleComment circleComment= (CircleComment) adapter.getItem(position);
                    commentId=circleComment.getId();
                    //  ToastUtil.showToast(DriverFriendsDetailActivity.this, "回复");
                    showSoftInputFromWindow(et_comment);
                }
            }
        });
        rv_comment.setLayoutManager(new LinearLayoutManager(this));
        rv_comment.setHasFixedSize(true);
        rv_comment.setNestedScrollingEnabled(false);
        rv_comment.setAdapter(mCircleCommentAdapter);
        getDatail();
    }
    public void showSoftInputFromWindow(EditText editText) {
        editText.setFocusable(true);
        editText.setFocusableInTouchMode(true);
        editText.requestFocus();
//        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);

        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT);

    }

    private void comment() {
        String content = et_comment.getEditableText().toString();
        Map<String, Object> params = new HashMap<>();
        params.put("circleId", mSeldDrvingDetail.getId());
        params.put("content", content);
        params.put("type", 3);
        HttpProxy.obtain().post(PlatformContans.BabyCircle.addBabyCircleComment, MyApplication.getUserInfo().getToken(), params, new ICallBack() {
            @Override
            public void OnSuccess(String result) {
                page = 1;
                mCircleComments.clear();
                getComment();
                //Toast.makeText(DriverFriendsDetailActivity.this, "收藏成功", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(String error) {

            }
        });
    }

    private void reply(String id) {
        String content = et_comment.getEditableText().toString();
        Map<String, Object> params = new HashMap<>();
        params.put("recordId", id);
        params.put("content", content);
        HttpProxy.obtain().post(PlatformContans.BabyCircle.replyBabyCircleComment, MyApplication.getUserInfo().getToken(), params, new ICallBack() {
            @Override
            public void OnSuccess(String result) {
                Log.e("result",result);
                page = 1;
                mCircleComments.clear();
                getComment();
                //Toast.makeText(DriverFriendsDetailActivity.this, "收藏成功", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(String error) {

            }
        });
    }
    private void initKeyBoardPopWindow(EmoticonsEditText editText) {
        mKeyBoardPopWindow = new EmoticonsKeyBoardPopWindow(ctx);

        EmoticonClickListener emoticonClickListener = SimpleCommonUtils.getCommonEmoticonClickListener(editText);
        PageSetAdapter pageSetAdapter = new PageSetAdapter();
        SimpleCommonUtils.addEmojiPageSetEntity(pageSetAdapter, this, emoticonClickListener);
        SimpleCommonUtils.addXhsPageSetEntity(pageSetAdapter, this, emoticonClickListener);
        mKeyBoardPopWindow.setAdapter(pageSetAdapter);
    }
    private void getComment() {
        Map<String, Object> params = new HashMap<>();
        params.put("id", mSeldDrvingDetail.getId());
        params.put("type", 3);
        params.put("page", 1);
        HttpProxy.obtain().get(PlatformContans.BabyCircle.getBabyCircleCommentDetailsById, params, MyApplication.getUserInfo().getToken(), new ICallBack() {
            @Override
            public void OnSuccess(String result) {
                try {
                    Log.e("getComment", MyApplication.getUserInfo().getToken());
                    JSONObject jsonObject = new JSONObject(result);
                    JSONArray data = jsonObject.getJSONArray("data");
                    for (int i = 0; i < data.length(); i++) {
                        JSONObject item = data.getJSONObject(i);
                        CircleComment circleComment = new Gson().fromJson(item.toString(), CircleComment.class);
                        mCircleComments.add(circleComment);
                    }
                    mCircleCommentAdapter.setNewData(mCircleComments);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(String error) {

            }
        });
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
        GSYVideoManager.releaseAllVideos();
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

    @OnClick({R.id.back,R.id.tv_pub})
    public void OnClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                onBackPressed();
                break;
            case R.id.tv_pub:
                if(TextUtils.isEmpty(commentId)){
                    comment();
                }else{
                    reply(commentId);
                }
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
