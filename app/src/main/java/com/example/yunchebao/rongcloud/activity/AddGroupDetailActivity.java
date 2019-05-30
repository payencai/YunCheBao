package com.example.yunchebao.rongcloud.activity;

import android.app.Dialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import com.costans.PlatformContans;
import com.example.yunchebao.R;
import com.gyf.immersionbar.ImmersionBar;
import com.http.HttpProxy;
import com.http.ICallBack;
import com.example.yunchebao.rongcloud.model.Group;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddGroupDetailActivity extends AppCompatActivity {
     Group mGroup;
    @BindView(R.id.iv_icon)
    ImageView head;
    @BindView(R.id.chatname)
    TextView chatname;
    @BindView(R.id.account)
    TextView account;
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.apply)
    TextView apply;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mGroup= (Group) getIntent().getSerializableExtra("group");
        setContentView(R.layout.activity_add_group_detail);
        ButterKnife.bind(this);
        ImmersionBar.with(this).autoDarkModeEnable(true).fitsSystemWindows(true).statusBarColor(R.color.white).init();
        initView();
    }
    private void initView() {
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

        chatname.setText(mGroup.getCrowdName());
        account.setText(mGroup.getHxCrowdId());
        Glide.with(this).load(mGroup.getImage()).into(head);

    }

    private void showApplyDialog() {
        final Dialog dialog = new Dialog(this);
        View view = LayoutInflater.from(this).inflate(R.layout.dialog, null);
        final EditText et_season = (EditText) view.findViewById(R.id.et_season);
        TextView tv_cancel = (TextView) view.findViewById(R.id.tv_cancel);
        TextView tv_name = (TextView) view.findViewById(R.id.tv_name);
        TextView tv_submit = (TextView) view.findViewById(R.id.tv_submit);
        tv_name.setText("入群申请");
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
        params.put("crowdId", mGroup.getHxCrowdId());
        params.put("applyReason", reason);


            HttpProxy.obtain().post(PlatformContans.Chat.addCrowdApply,MyApplication.token , params, new ICallBack() {
                @Override
                public void OnSuccess(String result) {
                    Log.e("friend", mGroup.getId()+result);
                    dialog.dismiss();
                    try {
                        JSONObject jsonObject = new JSONObject(result);
                        int code = jsonObject.getInt("resultCode");
                        String msg=jsonObject.getString("message");
                        if (code == 0) {
                            dialog.dismiss();
                            Toast.makeText(AddGroupDetailActivity.this, "已提交申请", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(AddGroupDetailActivity.this, msg, Toast.LENGTH_SHORT).show();
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
