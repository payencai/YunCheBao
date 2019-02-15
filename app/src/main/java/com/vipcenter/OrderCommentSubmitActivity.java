package com.vipcenter;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.application.MyApplication;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.cheyibao.AddRentCommentActivity;
import com.costans.PlatformContans;
import com.entity.PhoneGoodEntity;
import com.entity.PhoneOrderEntity;
import com.example.yunchebao.R;
import com.google.gson.Gson;
import com.http.HttpProxy;
import com.http.ICallBack;
import com.iarcuschin.simpleratingbar.SimpleRatingBar;

import com.maket.adapter.CommentAdapter;
import com.nohttp.sample.NoHttpBaseActivity;
import com.payencai.library.util.ToastUtil;
import com.tool.ActivityConstans;
import com.tool.GlideImageEngine;
import com.tool.GlideImageLoader;
import com.tool.UIControlUtils;
import com.vipcenter.adapter.PhotoAdapter;
import com.yancy.gallerypick.config.GalleryConfig;
import com.yancy.gallerypick.config.GalleryPick;
import com.yancy.gallerypick.inter.IHandlerCallBack;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.engine.impl.GlideEngine;
import com.zhihu.matisse.filter.Filter;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by sdhcjhss on 2018/1/5.
 */

public class OrderCommentSubmitActivity extends NoHttpBaseActivity {

    PhoneOrderEntity mPhoneOrderEntity;
    String imgs = "";
    int isAnonymous=1;
    @BindView(R.id.tv_pub)
    TextView tv_pub;
    @BindView(R.id.rv_comment)
    RecyclerView rv_comment;
    CommentAdapter mCommentAdapter;

    List<PhoneOrderEntity.ItemListBean>mItemListBeans=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_comment_submit_layout);
        ButterKnife.bind(this);
        mPhoneOrderEntity= (PhoneOrderEntity) getIntent().getSerializableExtra("data");
        mPhoneOrderEntity.getItemList();
        initView();
    }

    private void initView() {
        mItemListBeans.addAll(mPhoneOrderEntity.getItemList());
        mCommentAdapter=new CommentAdapter(R.layout.item_goods_comment,mPhoneOrderEntity.getItemList());
        mCommentAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                if(view.getId()==R.id.iv_select){
                    // 进入相册 以下是例子：用不到的api可以不写
                    Matisse.from(OrderCommentSubmitActivity.this)
                            .choose(MimeType.ofImage())
                            .countable(true)
                            .maxSelectable(6)
                            .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
                            .thumbnailScale(0.85f)
                            .imageEngine(new GlideImageEngine())
                            .forResult(188);
                }
            }
        });
        rv_comment.setLayoutManager(new LinearLayoutManager(this));
        rv_comment.setAdapter(mCommentAdapter);
    }
    List<Uri> mSelected;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 188 && resultCode == RESULT_OK) {
            mSelected = Matisse.obtainResult(data);
            List<String> path=new ArrayList<>();
            for (int i = 0; i <mSelected.size() ; i++) {
                path.add(mSelected.get(i).getPath());
            }
            mCommentAdapter.getPath().addAll(path);
            mCommentAdapter.getPhotoAdapter().notifyDataSetChanged();
            Log.d("Matisse", "mSelected: " + mSelected);
        }
    }

    public void saveImgs(int position, String imgs){
        PhoneOrderEntity.ItemListBean itemListBean = mItemListBeans.get(position);
        itemListBean.setImgs(imgs);
        mItemListBeans.remove(position);
        mItemListBeans.add(position, itemListBean);
        //mCommentAdapter.setNewData(mItemListBeans);
    }
    public void saveContent(int position,String content){
        PhoneOrderEntity.ItemListBean itemListBean = mItemListBeans.get(position);
        itemListBean.setContent(content);
        mItemListBeans.remove(position);
        mItemListBeans.add(position, itemListBean);
    }
    public void saveScore(int position,float score){
        PhoneOrderEntity.ItemListBean itemListBean = mItemListBeans.get(position);
        itemListBean.setScore(score);
        mItemListBeans.remove(position);
        mItemListBeans.add(position, itemListBean);
    }
    public void saveIsShow(int position,int isShow){
        PhoneOrderEntity.ItemListBean itemListBean = mItemListBeans.get(position);
        itemListBean.setIsRealName(isShow);
        mItemListBeans.remove(position);
        mItemListBeans.add(position, itemListBean);
    }
    private void shopcomment(String comment, double score) {
        Map<String, Object> params = new HashMap<>();
        params.put("orderItemId", mPhoneOrderEntity.getId());
        params.put("content", comment);
        params.put("isRealName", isAnonymous);
        params.put("score", score);
        if (!TextUtils.isEmpty(imgs))
            params.put("imgs", imgs.substring(1));
        String json=new Gson().toJson(params);
        HttpProxy.obtain().post(PlatformContans.GoodsOrder.addBabyMerchantComment, MyApplication.getUserInfo().getToken(), json, new ICallBack() {
            @Override
            public void OnSuccess(String result) {
                Log.e("result", result);
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    int code = jsonObject.getInt("resultCode");
                    if (code == 0) {
                        ToastUtil.showToast(OrderCommentSubmitActivity.this, "发布成功");
                        finish();
                    }else{
                        String msg=jsonObject.getString("message");
                        ToastUtil.showToast(OrderCommentSubmitActivity.this, msg);
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

    @OnClick({R.id.tv_pub})
    public void OnClick(View v){
        switch (v.getId()){
            case  R.id.tv_pub:
               // shopcomment(comment,score);
                break;


        }
    }
}
