package com.example.yunchebao.rongcloud.activity.contact;

import android.app.Dialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yunchebao.MyApplication;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.costans.PlatformContans;
import com.entity.UserMsg;
import com.example.yunchebao.R;
import com.google.gson.Gson;
import com.gyf.immersionbar.ImmersionBar;
import com.http.HttpProxy;
import com.http.ICallBack;
import com.example.yunchebao.rongcloud.model.ApplyFriend;

import org.json.JSONException;
import org.json.JSONObject;

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
    @BindView(R.id.tv_sex)
    TextView tv_sex;
    @BindView(R.id.tv_car)
    TextView tv_car;
    @BindView(R.id.iv_icon)
    ImageView iv_icon;
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.cd_card)
    CardView cd_card;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mApplyFriend= (ApplyFriend) getIntent().getSerializableExtra("apply");
        setContentView(R.layout.activity_apply_detail);
        ButterKnife.bind(this);
        ImmersionBar.with(this).autoDarkModeEnable(true).fitsSystemWindows(true).statusBarColor(R.color.white).init();
        initView();
        getDetail();
    }
    private void setUi( UserMsg userMsg){
        tv_sex.setText(userMsg.getSex());
        if(userMsg.getCarShowState()==1){
            cd_card.setVisibility(View.VISIBLE);
            if(userMsg.getCarList()!=null){
                if(userMsg.getCarList().size()>0)
                    tv_car.setText(userMsg.getCarList().get(0).getModels());
            }

        }
    }
    private void getDetail(){
        Map<String,Object> params=new HashMap<>();
        params.put("userId",mApplyFriend.getUserId());
        HttpProxy.obtain().get(PlatformContans.User.getUserResultById, params, MyApplication.token, new ICallBack() {
            @Override
            public void OnSuccess(String result) {
                Log.e("detail",result);
                try {
                    JSONObject jsonObject=new JSONObject(result);
                    JSONObject data=jsonObject.getJSONObject("data");
                    UserMsg userMsg=new Gson().fromJson(data.toString(),UserMsg.class);
                    setUi(userMsg);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(String error) {

            }
        });
    }
    private void initView(){
        RequestOptions mRequestOptions = RequestOptions.circleCropTransform()
                .diskCacheStrategy(DiskCacheStrategy.NONE)//不做磁盘缓存
                .skipMemoryCache(true);//不做内存缓存
        mRequestOptions.error(R.mipmap.ic_default_head);
        mRequestOptions.placeholder(R.mipmap.ic_default_head);
        Glide.with(this).load(mApplyFriend.getHeadPortrait()).apply(mRequestOptions).into(iv_icon);
        chatname.setText(mApplyFriend.getName());
        account.setText(mApplyFriend.getHxAccount());
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

            HttpProxy.obtain().post(PlatformContans.Chat.updateFriendApply, MyApplication.token, params, new ICallBack() {
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
