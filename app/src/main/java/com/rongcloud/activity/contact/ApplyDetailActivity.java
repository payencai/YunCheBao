package com.rongcloud.activity.contact;

import android.app.Dialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.application.MyApplication;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.costans.PlatformContans;
import com.example.yunchebao.R;
import com.http.HttpProxy;
import com.http.ICallBack;
import com.rongcloud.model.ApplyFriend;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ApplyDetailActivity extends AppCompatActivity {
    ApplyFriend mApplyFriend;
    @BindView(R.id.send)
    TextView tv_agree;
    @BindView(R.id.refuse)
    TextView refuse;
    @BindView(R.id.chatname)
    TextView chatname;
    @BindView(R.id.account)
    TextView account;
    @BindView(R.id.iv_icon)
    ImageView iv_icon;
    @BindView(R.id.back)
    ImageView back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mApplyFriend= (ApplyFriend) getIntent().getSerializableExtra("apply");
        setContentView(R.layout.activity_apply_detail);
        ButterKnife.bind(this);
        initView();
    }
    private void initView(){
        RequestOptions mRequestOptions = RequestOptions.circleCropTransform()
                .diskCacheStrategy(DiskCacheStrategy.NONE)//不做磁盘缓存
                .skipMemoryCache(true);//不做内存缓存
        mRequestOptions.error(R.mipmap.ic_default_head);
        mRequestOptions.placeholder(R.mipmap.ic_default_head);
        Glide.with(this).load(mApplyFriend.getHeadPortrait()).apply(mRequestOptions).into(iv_icon);
        chatname.setText(mApplyFriend.getName());
        account.setText(mApplyFriend.getId());
        refuse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showApplyDialog(mApplyFriend);
            }
        });
        tv_agree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                applyAndagree(1,mApplyFriend,"",null);
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void showApplyDialog(final ApplyFriend applyFriend) {
        final Dialog dialog = new Dialog(this);
        View view = LayoutInflater.from(this).inflate(R.layout.dialog, null);
        final EditText et_season = (EditText) view.findViewById(R.id.et_season);
        TextView tv_cancel = (TextView) view.findViewById(R.id.tv_cancel);
        TextView tv_name = (TextView) view.findViewById(R.id.tv_name);
        TextView tv_submit = (TextView) view.findViewById(R.id.tv_submit);
        tv_name.setText("好友申请");
        tv_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String reson = et_season.getEditableText().toString();
                applyAndagree(2, applyFriend, reson, dialog);

            }
        });
        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.setContentView(view);
        dialog.show();
        Window window = dialog.getWindow();
        WindowManager.LayoutParams layoutParams = window.getAttributes();
        layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        window.setAttributes(layoutParams);

    }

    private void applyAndagree(final int state, ApplyFriend applyFriend, String reason, final Dialog dialog) {
        Map<String, Object> params = new HashMap<>();
        params.put("id", applyFriend.getId());
        params.put("state", state);
        if (state == 2) {
            params.put("rejectReason", reason);
        }
        com.vipcenter.model.UserInfo userinfo = MyApplication.getUserInfo();
        if (userinfo != null)
            HttpProxy.obtain().post(PlatformContans.Chat.updateFriendApply, userinfo.getToken(), params, new ICallBack() {
                @Override
                public void OnSuccess(String result) {
                    if(dialog!=null)
                        dialog.dismiss();
                    if (state == 2)
                        Toast.makeText(ApplyDetailActivity.this, "已拒绝", Toast.LENGTH_SHORT).show();
                    else {
                        Toast.makeText(ApplyDetailActivity.this, "添加好友成功", Toast.LENGTH_SHORT).show();
                    }
                    finish();

                }

                @Override
                public void onFailure(String error) {

                }
            });
    }
}
