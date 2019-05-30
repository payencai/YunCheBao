package com.example.yunchebao.babycircle.selfdrive;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
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

import com.example.yunchebao.MyApplication;
import com.bbcircle.DrivingSelfReplaySuccessActivity;
import com.bbcircle.adapter.CircleCommentAdapter;
import com.bbcircle.data.CircleComment;
import com.bbcircle.data.SeldDrvingDetail;
import com.bbcircle.view.NoScrollWebView;
import com.bbcircle.view.SoftKeyBoardListener;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.costans.PlatformContans;
import com.example.yunchebao.R;
import com.example.yunchebao.babycircle.adapter.JoinAdapter;
import com.google.gson.Gson;
import com.http.HttpProxy;
import com.http.ICallBack;
import com.nohttp.sample.NoHttpBaseActivity;
import com.payencai.library.util.ToastUtil;
import com.payencai.library.view.CircleImageView;
import com.tool.ActivityAnimationUtils;
import com.tool.ActivityConstans;
import com.tool.UIControlUtils;
import com.tool.listview.PersonalScrollView;
import com.tool.slideshowview.SlideShowView;
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
import io.rong.imlib.model.UserInfo;

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
    @BindView(R.id.lv_join)
    ListViewForScrollView lv_join;
    @BindView(R.id.createtime)
    TextView tv_createTime;
    @BindView(R.id.readNum)
    TextView tv_readNum;
    @BindView(R.id.sedtime)
    TextView tv_sedtime;
    @BindView(R.id.aleady)
    TextView tv_aleady;
    @BindView(R.id.tv_focus)
    TextView tv_focus;
    @BindView(R.id.rl_comment)
    RelativeLayout rl_comment;
    @BindView(R.id.ll_comment)
    LinearLayout ll_comment;
    @BindView(R.id.name)
    TextView tv_name;
    @BindView(R.id.head)
    CircleImageView tv_head;
    @BindView(R.id.tv_heart)
    ImageView iv_heart;
    @BindView(R.id.ll_heart)
    LinearLayout ll_heart;
    @BindView(R.id.tv_contact)
    TextView tv_contact;
    @BindView(R.id.webView)
    NoScrollWebView mWebView;
    @BindView(R.id.rv_comment)
    RecyclerView rv_comment;
    @BindView(R.id.et_comment)
    EditText et_comment;
    @BindView(R.id.sc_self)
    PersonalScrollView ll_bottom;
    @BindView(R.id.ll_content)
    LinearLayout ll_content;
    String id;
    int type = 0;
    int page = 1;
    String focus;
    String commentId;
    CircleCommentAdapter mCircleCommentAdapter;
    List<CircleComment> mCircleComments = new ArrayList<>();
    SeldDrvingDetail mSeldDrvingDetail;
    JoinAdapter joinAdapter;
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
    /**
     * EditText获取焦点并显示软键盘
     */
    public void showSoftInputFromWindow(EditText editText) {
        editText.setFocusable(true);
        editText.setFocusableInTouchMode(true);
        editText.requestFocus();
//        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);

        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT);

    }
    private void getComment() {
        Map<String, Object> params = new HashMap<>();
        params.put("id", mSeldDrvingDetail.getId());
        params.put("type", 1);
        params.put("page", 1);
        HttpProxy.obtain().get(PlatformContans.BabyCircle.getBabyCircleCommentDetailsById, params, MyApplication.token, new ICallBack() {
            @Override
            public void OnSuccess(String result) {
                try {
                   // Log.e("getComment", MyApplication.getUserInfo().getToken());
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
    private void setUi() {
        if (mSeldDrvingDetail != null) {
            Map<String, String> image_uri = new HashMap<String, String>();
            image_uri.put("imageUrls", mSeldDrvingDetail.getImage());
            imageList.add(image_uri);
            slideShowView.setImageUrls(imageList);

            tv_aleady.setText("已有" + mSeldDrvingDetail.getEnterNum() + "人报名");
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
                iv_heart.setImageResource(R.mipmap.collect_gray_hole);
            }
            isfocus(mSeldDrvingDetail.getUserId());
            Glide.with(this).load(mSeldDrvingDetail.getHeadPortrait()).into(tv_head);
            getComment();
            RongIM.setUserInfoProvider(new RongIM.UserInfoProvider() {
                @Override
                public UserInfo getUserInfo(String s) {
                    UserInfo userInfo=new UserInfo(mSeldDrvingDetail.getUserId(),mSeldDrvingDetail.getName(), Uri.parse(mSeldDrvingDetail.getHeadPortrait()));
                    return userInfo;
                }
            },true);
            if(MyApplication.getUserInfo().getId().equals(mSeldDrvingDetail.getUserId())){
                ll_content.setVisibility(View.GONE);
            }
            if(mSeldDrvingDetail.getList()!=null&&mSeldDrvingDetail.getList().size()>0){
                joinAdapter=new JoinAdapter(this,mSeldDrvingDetail.getList());
                lv_join.setAdapter(joinAdapter);
            }

        }
    }

    private void collect() {
        Map<String, Object> params = new HashMap<>();
        params.put("circleId", mSeldDrvingDetail.getId());
        params.put("image", mSeldDrvingDetail.getImage());
        params.put("title", mSeldDrvingDetail.getTitle());
        params.put("type", 1);
        String json = new Gson().toJson(params);
        HttpProxy.obtain().post(PlatformContans.Collect.addBabyCollection, MyApplication.token, json, new ICallBack() {
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
        ButterKnife.bind(this);
        //ImmersionBar.with(this).autoDarkModeEnable(true).fitsSystemWindows(true).statusBarColor(R.color.white).init();
        UIControlUtils.UITextControlsUtils.setUIText(findViewById(R.id.title), ActivityConstans.UITag.TEXT_VIEW, "自驾游");
        id = getIntent().getStringExtra("id");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().getDecorView()
                    .setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            getWindow().setStatusBarColor(getResources().getColor(R.color.white));
        }
        initView();
    }

    private void initView() {

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
        SoftKeyBoardListener.setListener(DrivingSelfDetailActivity.this, new SoftKeyBoardListener.OnSoftKeyBoardChangeListener() {
            @Override
            public void keyBoardShow(int height) {
                //Toast.makeText(getActivity(), "键盘显示 高度" + height, Toast.LENGTH_SHORT).show();
                //introl_iv.setBackground(null);  //使LOGO消失
            }
            @Override
            public void keyBoardHide(int height) {

                rl_comment.setVisibility(View.GONE);
                ll_comment.setVisibility(View.VISIBLE);
                // Toast.makeText(DriverFriendsDetailActivity.this, "键盘隐藏 高度" + height, Toast.LENGTH_SHORT).show();
            }
        });

        mCircleCommentAdapter = new CircleCommentAdapter(R.layout.item_circle_comment, mCircleComments);
        mCircleCommentAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                if (view.getId() == R.id.tv_reply) {
                    CircleComment circleComment= (CircleComment) adapter.getItem(position);
                   // Toast.makeText(DrivingSelfDetailActivity.this, "回复", Toast.LENGTH_SHORT).show();
                    commentId=circleComment.getId();
                    ll_comment.setVisibility(View.GONE);
                    rl_comment.setVisibility(View.VISIBLE);
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

    //目标项是否在最后一个可见项之后
    private boolean mShouldScroll;
    //记录目标项位置
    private int mToPosition;
    /**
     * 滑动到指定位置
     */
    private void smoothMoveToPosition(RecyclerView mRecyclerView, final int position) {
        // 第一个可见位置
        int firstItem = mRecyclerView.getChildLayoutPosition(mRecyclerView.getChildAt(0));
        // 最后一个可见位置
        int lastItem = mRecyclerView.getChildLayoutPosition(mRecyclerView.getChildAt(mRecyclerView.getChildCount() - 1));
        if (position < firstItem) {
            // 第一种可能:跳转位置在第一个可见位置之前
            mRecyclerView.smoothScrollToPosition(position);
        } else if (position <= lastItem) {
            // 第二种可能:跳转位置在第一个可见位置之后
            int movePosition = position - firstItem;
            if (movePosition >= 0 && movePosition < mRecyclerView.getChildCount()) {
                int top = mRecyclerView.getChildAt(movePosition).getTop();
                mRecyclerView.smoothScrollBy(0, top);
            }
        } else {
            // 第三种可能:跳转位置在最后可见项之后
            mRecyclerView.smoothScrollToPosition(position);
            mToPosition = position;
            mShouldScroll = true;
        }
    }

    private void getDatail() {
        Map<String, Object> params = new HashMap<>();
        params.put("userId",MyApplication.getUserInfo().getId());
        params.put("id", id);
        HttpProxy.obtain().get(PlatformContans.BabyCircle.getSelfDrivingCircleDetailsById, params, MyApplication.token, new ICallBack() {
            @Override
            public void OnSuccess(String result) {
                try {
                    Log.d("detail", result);
                    JSONObject jsonObject = new JSONObject(result);
                    int code=jsonObject.getInt("resultCode");
                    if(code==0){
                        JSONObject data = jsonObject.getJSONObject("data");
                        mSeldDrvingDetail = new Gson().fromJson(data.toString(), SeldDrvingDetail.class);
                        setUi();
                    }else{
                        ToastUtil.showToast(DrivingSelfDetailActivity.this,"数据已不存在");
                        finish();
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
        HttpProxy.obtain().post(PlatformContans.BabyCircle.addSelfDrivingEnter, MyApplication.token, params, new ICallBack() {
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
    private void focus(String userId){
        Map<String,Object> params=new HashMap<>();
        params.put("otherId",userId);
        params.put("type","1");
        HttpProxy.obtain().post(PlatformContans.User.addUserFocus,MyApplication.token, params,  new ICallBack() {
            @Override
            public void OnSuccess(String result) {
                  Log.e("focus",result);
                  ToastUtil.showToast(DrivingSelfDetailActivity.this,"关注成功");
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
        HttpProxy.obtain().get(PlatformContans.User.isFocus, params,  MyApplication.token,new ICallBack() {
            @Override
            public void OnSuccess(String result) {
                Log.e("result",result);
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
                ToastUtil.showToast(DrivingSelfDetailActivity.this,"已取消");
            }

            @Override
            public void onFailure(String error) {

            }
        });
    }
    @OnClick({R.id.back, R.id.submitBtn,R.id.comment,R.id.tv_pub,R.id.tv_focus})
    public void OnClick(View v) {
        switch (v.getId()) {
            case R.id.tv_focus:
                if("1".equals(focus)){
                    delfocus(mSeldDrvingDetail.getUserId());
                }else{
                    focus(mSeldDrvingDetail.getUserId());
                }
                break;
            case R.id.tv_pub:
                if(TextUtils.isEmpty(commentId)){
                    comment();
                }else{
                    reply(commentId);
                }
                break;
            case R.id.back:
                onBackPressed();
                break;
            case R.id.submitBtn:
                if(MyApplication.getUserInfo().getId().equals(mSeldDrvingDetail.getUserId())){
                    ToastUtil.showToast(this,"你是发布者不能报名！");
                    return;
                }
                attenToast();
                break;
            case R.id.comment:
                commentId="";
                ll_comment.setVisibility(View.GONE);
                rl_comment.setVisibility(View.VISIBLE);
                showSoftInputFromWindow(et_comment);
                break;

        }
    }
    private void comment() {
        String content = et_comment.getEditableText().toString();
        Map<String, Object> params = new HashMap<>();
        params.put("circleId", mSeldDrvingDetail.getId());
        params.put("content", content);
        params.put("type", 1);
        HttpProxy.obtain().post(PlatformContans.BabyCircle.addBabyCircleComment, MyApplication.token, params, new ICallBack() {
            @Override
            public void OnSuccess(String result) {

                page = 1;
                mCircleComments.clear();
                getComment();
                ll_bottom.smoothScrollTo(0,rv_comment.getTop());
                Toast.makeText(DrivingSelfDetailActivity.this, "评论成功", Toast.LENGTH_SHORT).show();
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
                Toast.makeText(DrivingSelfDetailActivity.this, "回复成功", Toast.LENGTH_SHORT).show();
                //Toast.makeText(DriverFriendsDetailActivity.this, "收藏成功", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(String error) {

            }
        });
    }
}
