package com.bbcircle;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yunchebao.MyApplication;
import com.bbcircle.adapter.ArticleCommentListAdapter;
import com.bbcircle.adapter.CarShowMiddleListAdapter;
import com.bbcircle.adapter.CircleCommentAdapter;
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
import com.payencai.library.util.ToastUtil;
import com.shuyu.gsyvideoplayer.GSYVideoManager;
import com.tool.ActivityConstans;
import com.tool.UIControlUtils;
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


/**
 * Created by sdhcjhss on 2018/1/8.
 */

public class CarShowDetailActivity extends NoHttpBaseActivity {

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
    @BindView(R.id.tv_focus)
    TextView tv_focus;
    CircleCommentAdapter mCircleCommentAdapter;
    List<CircleComment> mCircleComments = new ArrayList<>();
    int page = 1;
    String commentId;
    int id;
    int type;
    String focus;
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
    private void focus(String userId){
        Map<String,Object> params=new HashMap<>();
        params.put("otherId",userId);
        params.put("type","1");
        HttpProxy.obtain().post(PlatformContans.User.addUserFocus,MyApplication.token, params,  new ICallBack() {
            @Override
            public void OnSuccess(String result) {
                Log.e("focus",result);
                ToastUtil.showToast(CarShowDetailActivity.this,"关注成功");
                tv_focus.setText("取消关注");
                focus="1";
            }

            @Override
            public void onFailure(String error) {

            }
        });
    }
    private void isfocus(String userId){
        Map<String,Object> params=new HashMap<>();
        params.put("otherId",userId);
        HttpProxy.obtain().get(PlatformContans.User.deleteUserFocus, params,  MyApplication.token,new ICallBack() {
            @Override
            public void OnSuccess(String result) {
                try {
                    JSONObject jsonObject=new JSONObject(result);
                    focus=jsonObject.getString("data");
                    if("1".equals(focus)){
                        tv_focus.setText("取消关注");
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
    private void delfocus(String userId){
        Map<String,Object> params=new HashMap<>();
        params.put("otherId",userId);
        HttpProxy.obtain().post(PlatformContans.User.deleteUserFocus,MyApplication.token, params,  new ICallBack() {
            @Override
            public void OnSuccess(String result) {
                Log.e("focus",result);
                focus="0";
                tv_focus.setText("+ 关注");
                ToastUtil.showToast(CarShowDetailActivity.this,"已取消");
            }

            @Override
            public void onFailure(String error) {

            }
        });
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
        HttpProxy.obtain().get(PlatformContans.BabyCircle.getCarShowCircleDetailsById, params, MyApplication.token, new ICallBack() {
            @Override
            public void OnSuccess(String result) {
                try {
                    //Log.e("detail", MyApplication.getUserInfo().getToken());
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
        HttpProxy.obtain().post(PlatformContans.Collect.addBabyCollection, MyApplication.token, json, new ICallBack() {
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
        UIControlUtils.UITextControlsUtils.setUIText(findViewById(R.id.title), ActivityConstans.UITag.TEXT_VIEW, "好友圈");
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
        HttpProxy.obtain().post(PlatformContans.BabyCircle.addBabyCircleComment, MyApplication.token, params, new ICallBack() {
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
        HttpProxy.obtain().post(PlatformContans.BabyCircle.replyBabyCircleComment, MyApplication.token, params, new ICallBack() {
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

    private void getComment() {
        Map<String, Object> params = new HashMap<>();
        params.put("id", mSeldDrvingDetail.getId());
        params.put("type", 3);
        params.put("page", 1);
        HttpProxy.obtain().get(PlatformContans.BabyCircle.getBabyCircleCommentDetailsById, params, MyApplication.token, new ICallBack() {
            @Override
            public void OnSuccess(String result) {
                try {
                    ///Log.e("getComment", MyApplication.getUserInfo().getToken());
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








    @OnClick({R.id.back,R.id.tv_pub,R.id.tv_focus})
    public void OnClick(View v) {
        switch (v.getId()) {
            case R.id.tv_focus:
                if("1".equals(focus)){
                    delfocus(mSeldDrvingDetail.getUserId());
                }else{
                    focus(mSeldDrvingDetail.getUserId());
                }
                break;
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
