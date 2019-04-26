package com.example.yunchebao.myservice;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.application.MyApplication;
import com.bumptech.glide.Glide;
import com.costans.PlatformContans;
import com.example.yunchebao.R;
import com.example.yunchebao.fourshop.bean.FourShopData;
import com.example.yunchebao.myservice.model.SchoolCommentDetail;
import com.google.gson.Gson;
import com.http.HttpProxy;
import com.http.ICallBack;
import com.iarcuschin.simpleratingbar.SimpleRatingBar;
import com.payencai.library.view.CircleImageView;
import com.tool.StringUtils;
import com.tool.view.GridViewForScrollView;
import com.vipcenter.adapter.PhotoAdapter;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import go.error;

public class SeeSchoolCommentActivity extends AppCompatActivity {
    @BindView(R.id.userhead)
    CircleImageView userhead;
    @BindView(R.id.coachhead)
    CircleImageView coachhead;
    @BindView(R.id.tv_shopname)
    TextView tv_shopname;
    @BindView(R.id.tv_shoptime)
    TextView tv_shoptime;
    @BindView(R.id.tv_shopcontent)
    TextView tv_shopcontent;
    @BindView(R.id.sb_shop)
    SimpleRatingBar sb_shop;
    @BindView(R.id.gv_shopphoto)
    GridViewForScrollView gv_shopphoto;
    @BindView(R.id.tv_name)
    TextView tv_name;
    @BindView(R.id.tv_time)
    TextView tv_time;
    @BindView(R.id.tv_content)
    TextView tv_content;
    @BindView(R.id.starbar)
    SimpleRatingBar starbar;
    @BindView(R.id.gv_photo)
    GridViewForScrollView gv_photo;
    PhotoAdapter mShopAdapter;
    PhotoAdapter mCoachAdapter;
    String id;
    SchoolCommentDetail mSchoolCommentDetail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_see_school_comment);
        ButterKnife.bind(this);
        id=getIntent().getStringExtra("id");
        initView();
    }
    private void getDetail() {
        Map<String, Object> params = new HashMap<>();
        params.put("orderId", id);
        HttpProxy.obtain().get(PlatformContans.MyService.getSchoolCommentDetail, params, MyApplication.token,new ICallBack() {
            @Override
            public void OnSuccess(String result) {
                Log.e("data", result);
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    JSONObject data = jsonObject.getJSONObject("data");
                    mSchoolCommentDetail = new Gson().fromJson(data.toString(), SchoolCommentDetail.class);
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
    private void initView() {
        getDetail();
    }
    private void setData() {
        Glide.with(this).load(mSchoolCommentDetail.getMerchantEvaluation().getHeadPortrait()).into(userhead);
        tv_shopname.setText(mSchoolCommentDetail.getMerchantEvaluation().getName());
        tv_shopcontent.setText(mSchoolCommentDetail.getMerchantEvaluation().getContent());
        tv_shoptime.setText(mSchoolCommentDetail.getMerchantEvaluation().getCreateTime().substring(0,10));
        sb_shop.setRating(mSchoolCommentDetail.getMerchantEvaluation().getScore());
        List<String> shopimages=new ArrayList<>();
        List<String> images=new ArrayList<>();
        shopimages.addAll(StringUtils.StringToArrayList(mSchoolCommentDetail.getMerchantEvaluation().getPhoto(),","));
        images.addAll(StringUtils.StringToArrayList(mSchoolCommentDetail.getCoachEvaluation().getPhoto(),","));
        mShopAdapter=new PhotoAdapter(this,shopimages);
        gv_shopphoto.setAdapter(mShopAdapter);
        Glide.with(this).load(mSchoolCommentDetail.getCoachEvaluation().getCoachHead()).into(coachhead);
        tv_name.setText(mSchoolCommentDetail.getCoachEvaluation().getCoachName());
        tv_content.setText(mSchoolCommentDetail.getCoachEvaluation().getContent());
        tv_time.setText(mSchoolCommentDetail.getCoachEvaluation().getCreateTime().substring(0,10));
        starbar.setRating(mSchoolCommentDetail.getCoachEvaluation().getScore());
        mCoachAdapter=new PhotoAdapter(this,images);
        gv_photo.setAdapter(mCoachAdapter);
    }
}
