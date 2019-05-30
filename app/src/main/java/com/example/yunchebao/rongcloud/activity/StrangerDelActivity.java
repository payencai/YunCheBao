package com.example.yunchebao.rongcloud.activity;

import android.app.Dialog;
import android.content.Intent;
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
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yunchebao.MyApplication;
import com.bumptech.glide.Glide;
import com.costans.PlatformContans;
import com.entity.UserMsg;
import com.example.yunchebao.R;
import com.google.gson.Gson;
import com.gyf.immersionbar.ImmersionBar;
import com.http.HttpProxy;
import com.http.ICallBack;
import com.luffy.imagepreviewlib.core.PictureConfig;
import com.newversion.MyTagsActivity;
import com.payencai.library.util.ToastUtil;
import com.payencai.library.view.CircleImageView;
import com.vipcenter.model.UserInfo;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.rong.imkit.RongIM;

public class StrangerDelActivity extends AppCompatActivity {
    @BindView(R.id.iv_icon)
    CircleImageView head;
    @BindView(R.id.chatname)
    TextView chatname;
    @BindView(R.id.account)
    TextView account;
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.apply)
    TextView apply;
    @BindView(R.id.send)
    TextView send;
    @BindView(R.id.cd_car)
    CardView cd_card;
    @BindView(R.id.tv_sex)
    TextView  tv_sex;
    @BindView(R.id.car)
    TextView  tv_car;
    @BindView(R.id.rl_tag)
    RelativeLayout rl_tag;
    String userId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_friend_detail);
        ButterKnife.bind(this);
        ImmersionBar.with(this).autoDarkModeEnable(true).fitsSystemWindows(true).statusBarColor(R.color.white).init();
        userId=getIntent().getStringExtra("id");
        initView();
    }

    private void initView() {

            getDetail(userId);
        head.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<String> images=new ArrayList<>();
                images.add(userMsg.getHeadPortrait());
                PictureConfig config = new PictureConfig.Builder()
                        .setListData(images)  //图片数据List<String> list
                        .setPosition(0)                         //图片下标（从第position张图片开始浏览）
                        .setDownloadPath("head")        //图片下载文件夹地址
                        .setIsShowNumber(true)                  //是否显示数字下标
                        .needDownload(true)                     //是否支持图片下载
                        .setPlaceHolder(R.mipmap.ic_launcher)   //占位符
                        .build();
                config.gotoActivity(StrangerDelActivity.this, config);
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showApplyDialog();
            }
        });
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RongIM.getInstance().startPrivateChat(StrangerDelActivity.this, userId, userMsg.getName());
            }
        });
        rl_tag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(StrangerDelActivity.this, MyTagsActivity.class));
            }
        });


    }
    private void setUi(UserMsg userMsg){
        tv_sex.setText(userMsg.getSex());
        chatname.setText(userMsg.getName());
        account.setText(userMsg.getHxAccount());
        Glide.with(this).load(userMsg.getHeadPortrait()).into(head);
        if(userMsg.getCarShowState()==1){
            cd_card.setVisibility(View.VISIBLE);
            if(userMsg.getCarList()!=null){
                if(userMsg.getCarList().size()>0)
                    tv_car.setText(userMsg.getCarList().get(0).getModels());
            }

        }
    }
    UserMsg userMsg;
    private void getDetail(String id){
        Map<String,Object> params=new HashMap<>();
        params.put("userId",id);
        HttpProxy.obtain().get(PlatformContans.User.getUserResultById, params, MyApplication.token, new ICallBack() {
            @Override
            public void OnSuccess(String result) {
                Log.e("detail",result);
                try {
                    JSONObject jsonObject=new JSONObject(result);
                    JSONObject data=jsonObject.getJSONObject("data");
                     userMsg=new Gson().fromJson(data.toString(),UserMsg.class);
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
    private void showApplyDialog() {
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
                apply(reson, dialog);

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

    private void apply(String reason, final Dialog dialog) {
        Map<String, Object> params = new HashMap<>();
        params.put("friendId", userId);
        params.put("applyReason", reason);
        UserInfo userInfo = MyApplication.getUserInfo();
        if (userInfo != null)
            HttpProxy.obtain().post(PlatformContans.Chat.addFriendApply, MyApplication.token, params, new ICallBack() {
                @Override
                public void OnSuccess(String result) {
                    Log.e("friend", result);
                    dialog.dismiss();
                    try {
                        JSONObject jsonObject = new JSONObject(result);
                        int code = jsonObject.getInt("resultCode");
                        String msg=jsonObject.getString("message");
                        if (code == 0) {
                            dialog.dismiss();
                            Toast.makeText(StrangerDelActivity.this, "已提交申请", Toast.LENGTH_SHORT).show();
                        }else{
                            ToastUtil.showToast(StrangerDelActivity.this,msg);
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
}
