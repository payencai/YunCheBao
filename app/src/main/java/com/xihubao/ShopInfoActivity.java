package com.xihubao;

import android.app.Dialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
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

import com.application.MyApplication;
import com.bumptech.glide.Glide;
import com.costans.PlatformContans;
import com.entity.Banner;
import com.example.yunchebao.R;
import com.google.gson.Gson;
import com.http.HttpProxy;
import com.http.ICallBack;
import com.rongcloud.activity.AddFriendDetailActivity;
import com.rongcloud.model.ApplyGroup;
import com.system.model.ShopInfo;
import com.vipcenter.model.UserInfo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ShopInfoActivity extends AppCompatActivity {
    String id;
    ShopInfo mShopInfo;
    @BindView(R.id.iv_logo)
    ImageView iv_logo;
    @BindView(R.id.tv_nick)
    TextView tv_nick;
    @BindView(R.id.tv_add)
    TextView tv_add;
    @BindView(R.id.tv_account)
    TextView tv_account;
    @BindView(R.id.tv_content)
    TextView tv_content;
    @BindView(R.id.back)
    ImageView back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_info);
        ButterKnife.bind(this);
        id=getIntent().getStringExtra("id");
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        tv_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showApplyDialog();
            }
        });
        getData();
    }
    private void getData(){
        Map<String,Object> params=new HashMap<>();
        params.put("shopId",id);
        HttpProxy.obtain().get(PlatformContans.MerchAdmin.getMerchInformationByShopId, params, new ICallBack() {
            @Override
            public void OnSuccess(String result) {
                Log.e("heart",result);
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    JSONObject data = jsonObject.getJSONObject("data");
                    mShopInfo=new Gson().fromJson(data.toString(),ShopInfo.class);
                    setData();
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
        params.put("friendId", mShopInfo.getId());
        params.put("applyReason", reason);
        UserInfo userInfo = MyApplication.getUserInfo();
        if (userInfo != null)
            HttpProxy.obtain().post(PlatformContans.Chat.addFriendApply, userInfo.getToken(), params, new ICallBack() {
                @Override
                public void OnSuccess(String result) {
                    Log.e("friend", result);
                    dialog.dismiss();
                    try {
                        JSONObject jsonObject = new JSONObject(result);
                        int code = jsonObject.getInt("resultCode");
                        if (code == 0) {
                            dialog.dismiss();
                            Toast.makeText(ShopInfoActivity.this, "已提交申请", Toast.LENGTH_SHORT).show();
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
    private void setData(){
        String images=mShopInfo.getImgs();
        if(!TextUtils.isEmpty(images)){
            if(images.contains(",")){
                String[] imgs=images.split(",");
                Glide.with(this).load(imgs[0]).into(iv_logo);
            }else{
                Glide.with(this).load(mShopInfo.getImgs()).into(iv_logo);
            }
        }

        tv_account.setText(mShopInfo.getYcbAccount());
        tv_nick.setText(mShopInfo.getName());
        tv_content.setText(mShopInfo.getPersonSign());
    }
}
