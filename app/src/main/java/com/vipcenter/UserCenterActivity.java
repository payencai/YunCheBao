package com.vipcenter;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.application.MyApplication;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.chat.MessageMainActivity;
import com.costans.PlatformContans;
import com.example.yunchebao.R;
import com.maket.ShopCartActivity;
import com.nohttp.sample.NoHttpBaseActivity;
import com.tool.ActivityAnimationUtils;
import com.tool.ActivityConstans;
import com.vipcenter.model.UserInfo;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import io.rong.imlib.RongIMClient;

/**
 * Created by sdhcjhss on 2017/12/14.
 */

public class UserCenterActivity extends NoHttpBaseActivity {
    @BindView(R.id.name)
    TextView nameText;
    @BindView(R.id.info)
    TextView infoText;
    @BindView(R.id.headIcon)
    ImageView headIcon;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_center_layout);
        ButterKnife.bind(this);
         //headIcon.setImageURI(Uri.parse(MyApplication.getUserInfo().getHeadPortrait()));
        Glide.with(this).load(MyApplication.getUserInfo().getHeadPortrait())
                .apply(RequestOptions.bitmapTransform(new RoundedCorners(40)))
                .into(headIcon);
        initLoginView();
    }

    private void initLoginView() {
        if (PlatformContans.isLogin) {
            findViewById(R.id.signIn).setVisibility(View.GONE);
            nameText.setText("登录");
            infoText.setVisibility(View.GONE);
        } else {
            findViewById(R.id.signIn).setVisibility(View.VISIBLE);
            nameText.setText("洗护宝专家");
            infoText.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        initLoginView();
    }

    /**
     * 自定义Toast信息显示面板,签到成功
     *
     * @Title: commonToastDefined
     * @Description: TODO
     * @param @param text
     * @return void
     * @throws
     */
    Dialog dialog;

    public void attenSuccessToast() {
        View view = getLayoutInflater().inflate(R.layout.sign_success_layout, null);
        LinearLayout ll = (LinearLayout) view.findViewById(R.id.ll_root);
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

    }

    @OnClick({R.id.back, R.id.headIcon, R.id.signIn, R.id.shopCart, R.id.message, R.id.middleMenu1, R.id.middleMenu2, R.id.middleMenu3, R.id.lay1, R.id.lay2, R.id.lay3, R.id.lay4, R.id.lay5, R.id.lay6, R.id.lay7, R.id.lay8, R.id.lay9, R.id.lay10, R.id.lay11})
    public void Onclick(View v) {
        switch (v.getId()) {
            case R.id.back:
                onBackPressed();
                break;
            case R.id.headIcon:
                if (MyApplication.isLogin) {
                    ActivityAnimationUtils.commonTransition(UserCenterActivity.this, UserInfoActivity.class, ActivityConstans.Animation.FADE);
                } else {
                    startActivityForResult(new Intent(UserCenterActivity.this, RegisterActivity.class), 0);
                    // ActivityAnimationUtils.commonTransition(UserCenterActivity.this, RegisterActivity.class, ActivityConstans.Animation.FADE);
                }
                break;
            case R.id.signIn:
                attenSuccessToast();
                break;
            case R.id.shopCart:
                ActivityAnimationUtils.commonTransition(UserCenterActivity.this, ShopCartActivity.class, ActivityConstans.Animation.FADE);
                break;
            case R.id.message:
                ActivityAnimationUtils.commonTransition(UserCenterActivity.this, MessageMainActivity.class, ActivityConstans.Animation.FADE);
                break;
            case R.id.middleMenu1://我的钱包
                ActivityAnimationUtils.commonTransition(UserCenterActivity.this, MyWalletActivity.class, ActivityConstans.Animation.FADE);
                break;
            case R.id.middleMenu2://礼品汇
                ActivityAnimationUtils.commonTransition(UserCenterActivity.this, GiftMarketHomeActivity.class, ActivityConstans.Animation.FADE);
                break;
            case R.id.middleMenu3://我的订单
                ActivityAnimationUtils.commonTransition(UserCenterActivity.this, MyOrderListActivity.class, ActivityConstans.Animation.FADE);
                break;
            case R.id.lay1://全部收藏
                ActivityAnimationUtils.commonTransition(UserCenterActivity.this, AllCollectionActivity.class, ActivityConstans.Animation.FADE);
                break;
            case R.id.lay2://我的发表
                ActivityAnimationUtils.commonTransition(UserCenterActivity.this, MyPublishListActivity.class, ActivityConstans.Animation.FADE);
                break;
            case R.id.lay3://待售车辆
                ActivityAnimationUtils.commonTransition(UserCenterActivity.this, OnSaleCarListActivity.class, ActivityConstans.Animation.FADE);
                break;
            case R.id.lay4://浏览历史
                ActivityAnimationUtils.commonTransition(UserCenterActivity.this, HistoryListActivity.class, ActivityConstans.Animation.FADE);
                break;
            case R.id.lay5://手机认证
                ActivityAnimationUtils.commonTransition(UserCenterActivity.this, MobileCertificationActivity.class, ActivityConstans.Animation.FADE);
                break;
            case R.id.lay6://身份证认证
                ActivityAnimationUtils.commonTransition(UserCenterActivity.this, IDCardCertificationActivity.class, ActivityConstans.Animation.FADE);
                break;
            case R.id.lay7://关于我们
                ActivityAnimationUtils.commonTransition(UserCenterActivity.this, AboutUsActivity.class, ActivityConstans.Animation.FADE);
                break;
            case R.id.lay8://客服热线
                ActivityAnimationUtils.commonTransition(UserCenterActivity.this, MyCustomerServiceActivity.class, ActivityConstans.Animation.FADE);
                break;
            case R.id.lay9://帮助反馈
                ActivityAnimationUtils.commonTransition(UserCenterActivity.this, FeedbackHelpActivity.class, ActivityConstans.Animation.FADE);
                break;
            case R.id.lay11://洗护评论
                ActivityAnimationUtils.commonTransition(UserCenterActivity.this, WashCommentActivity.class, ActivityConstans.Animation.FADE);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0) {
            if (data != null) {
                UserInfo userInfo = (UserInfo) data.getSerializableExtra("user");
                if(userInfo!=null)
                connect(userInfo.getHxPassword());
                updateUI(userInfo);
            }
        }
    }
    private void connect(String token) {

        RongIMClient.connect(token, new RongIMClient.ConnectCallback() {
            /**
             * Token 错误。可以从下面两点检查 1.  Token 是否过期，如果过期您需要向 App Server 重新请求一个新的 Token
             *                  2.  token 对应的 appKey 和工程里设置的 appKey 是否一致
             */
            @Override
            public void onTokenIncorrect() {

            }
            /**
             * 连接融云成功
             * @param userid 当前 token 对应的用户 id
             */
            @Override
            public void onSuccess(String userid) {

                Log.d("LoginActivity", "--onSuccess" + userid);
                //startActivity(new Intent(RegisterActivity.this, MainActivity.class));
                //finish();
            }

            /**
             * 连接融云失败
             * @param errorCode 错误码，可到官网 查看错误码对应的注释
             */
            @Override
            public void onError(RongIMClient.ErrorCode errorCode) {

            }
        });
    }
    private void updateUI(UserInfo userInfo) {

    }
}
