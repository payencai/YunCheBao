package com.vipcenter;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.application.MyApplication;
import com.costans.PlatformContans;
import com.example.yunchebao.R;
import com.google.gson.Gson;
import com.http.HttpProxy;
import com.http.ICallBack;
import com.nohttp.sample.NoHttpBaseActivity;
import com.tool.ActivityAnimationUtils;
import com.tool.ActivityConstans;
import com.tool.UIControlUtils;
import com.vipcenter.model.MemberCard;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by sdhcjhss on 2018/1/2.
 */

public class MyWalletActivity extends NoHttpBaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_wallet_layout);
        initView();
    }
    List<MemberCard> mMemberCards=new ArrayList<>();
    private void showDialog(){
        List<View> mViews=new ArrayList<>();
        for (int i = 0; i <mMemberCards.size() ; i++) {
            View view=LayoutInflater.from(this).inflate(R.layout.item_card,null);
            mViews.add(view);
        }

        View contentView= LayoutInflater.from(this).inflate(R.layout.dialog_card,null);
        ViewPager viewPager= (ViewPager) contentView.findViewById(R.id.vp_card);
        NoticePagerAdapter noticePagerAdapter=new NoticePagerAdapter(mViews);
        viewPager.setAdapter(noticePagerAdapter);
        Dialog dialog=new Dialog(this,R.style.dialog);
        dialog.show();
        dialog.setContentView(contentView);
        Window window = dialog.getWindow() ;
        window.setGravity(Gravity.CENTER);
        WindowManager.LayoutParams params = window.getAttributes() ;
        params.width =ViewGroup.LayoutParams.MATCH_PARENT ;
        params.height =ViewGroup.LayoutParams.WRAP_CONTENT ; //使用这种方式更改了dialog的框宽
        window.setAttributes(params);
    }
    private void getMemberCard(){
        HttpProxy.obtain().get(PlatformContans.MemberCard.getMemberCardRuleList, MyApplication.getUserInfo().getToken(), new ICallBack() {
            @Override
            public void OnSuccess(String result) {
                Log.e("result",result);
                try {
                    JSONObject jsonObject=new JSONObject(result);
                    int code=jsonObject.getInt("resultCode");
                    if(code==0){
                        JSONArray jsonArray=jsonObject.getJSONArray("data");
                        for (int i = 0; i <jsonArray.length() ; i++) {
                            JSONObject item=jsonArray.getJSONObject(i);
                            MemberCard memberCard=new Gson().fromJson(item.toString(),MemberCard.class);
                            mMemberCards.add(memberCard);
                        }
                        showDialog();
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
    private void initView() {
        ButterKnife.bind(this);
    }
    public class NoticePagerAdapter extends PagerAdapter {

        private List<View> views;

        public NoticePagerAdapter(List<View> views){
            this.views = views;
        }

        @Override
        public int getCount() {
            return views.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(views.get(position), 0);  //添加页卡
            return views.get(position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(views.get(position));   //删除页卡
        }
    }
    @OnClick({R.id.back, R.id.consumptionLay,R.id.depositLay,R.id.toDeposit})
    public void OnClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                onBackPressed();
                break;
//            case R.id.toMoreBtn:
//                ActivityAnimationUtils.commonTransition(MyWalletActivity.this, GiftMarketHomeActivity.class, ActivityConstans.Animation.FADE);
//                break;
            case R.id.consumptionLay:
                ActivityAnimationUtils.commonTransition(MyWalletActivity.this, ConsumptionListActivity.class, ActivityConstans.Animation.FADE);
                break;
            case R.id.depositLay:
                ActivityAnimationUtils.commonTransition(MyWalletActivity.this, DepositListActivity.class, ActivityConstans.Animation.FADE);
                break;
            case R.id.toDeposit:
                getMemberCard();
                //ActivityAnimationUtils.commonTransition(MyWalletActivity.this,AccountDepositActivity.class, ActivityConstans.Animation.FADE);
                break;
        }
    }
}
