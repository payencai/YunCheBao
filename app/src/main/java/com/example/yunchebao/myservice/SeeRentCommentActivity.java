package com.example.yunchebao.myservice;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import com.example.yunchebao.MyApplication;
import com.bumptech.glide.Glide;
import com.costans.PlatformContans;
import com.example.yunchebao.R;
import com.example.yunchebao.myservice.model.RentOrderComment;
import com.google.gson.Gson;
import com.http.HttpProxy;
import com.http.ICallBack;
import com.iarcuschin.simpleratingbar.SimpleRatingBar;
import com.luffy.imagepreviewlib.core.PictureConfig;
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

public class SeeRentCommentActivity extends AppCompatActivity {

    @BindView(R.id.userhead)
    CircleImageView userhead;
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
    PhotoAdapter mPhotoAdapter;
    String id;
    RentOrderComment mWashOrderComment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_see_rent_comment);
        ButterKnife.bind(this);
        id=getIntent().getStringExtra("id");
        initView();
    }

    private void initView() {
        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        getDetail();
    }
    private void getDetail() {
        Map<String, Object> params = new HashMap<>();
        params.put("orderId", id);
        HttpProxy.obtain().get(PlatformContans.MyService.getRentCarCommentByOrderId, params, MyApplication.token,new ICallBack() {
            @Override
            public void OnSuccess(String result) {
                Log.e("data", result);
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    JSONObject data = jsonObject.getJSONObject("data");
                    mWashOrderComment = new Gson().fromJson(data.toString(), RentOrderComment.class);
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

    private void setData() {
        Glide.with(this).load(mWashOrderComment.getHeadPortrait()).into(userhead);
        tv_shopname.setText(mWashOrderComment.getName());
        tv_shopcontent.setText(mWashOrderComment.getContent());
        tv_shoptime.setText(mWashOrderComment.getCreateTime().substring(0,10));
        sb_shop.setRating((float) mWashOrderComment.getScore());
        ArrayList<String> shopimages=new ArrayList<>();
        shopimages.addAll(StringUtils.StringToArrayList(mWashOrderComment.getImgs(),","));
        mPhotoAdapter=new PhotoAdapter(this,shopimages);
        gv_shopphoto.setAdapter(mPhotoAdapter);
        gv_shopphoto.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                PictureConfig config = new PictureConfig.Builder()
                        .setListData(shopimages)  //图片数据List<String> list
                        .setPosition(position)                         //图片下标（从第position张图片开始浏览）
                        .setDownloadPath("imagepreview")        //图片下载文件夹地址
                        .setIsShowNumber(true)                  //是否显示数字下标
                        .needDownload(true)                     //是否支持图片下载
                        .setPlaceHolder(R.mipmap.ic_launcher)   //占位符
                        .build();
                config.gotoActivity(SeeRentCommentActivity.this, config);

            }

        });
    }
}
