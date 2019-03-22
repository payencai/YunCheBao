package com.vipcenter;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.entity.PhoneOrderEntity;
import com.example.yunchebao.R;
import com.nohttp.sample.NoHttpBaseActivity;
import com.tool.ActivityAnimationUtils;
import com.tool.ActivityConstans;
import com.tool.UIControlUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by sdhcjhss on 2018/1/5.
 */

public class OrderReturnTypeActivity extends NoHttpBaseActivity {
    private Context ctx;
    PhoneOrderEntity.ItemListBean mItemListBean;
    @BindView(R.id.iv_goods)
    ImageView iv_goods;
    @BindView(R.id.tv_goodsname)
    TextView tv_goodsname;
    @BindView(R.id.tv_price)
    TextView tv_price;
    @BindView(R.id.tv_num)
    TextView tv_num;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_return_type_layout);
        initView();
    }

    private void initView() {
        UIControlUtils.UITextControlsUtils.setUIText(findViewById(R.id.title), ActivityConstans.UITag.TEXT_VIEW, "选择服务类型");
        ButterKnife.bind(this);
        mItemListBean= (PhoneOrderEntity.ItemListBean) getIntent().getSerializableExtra("data");
        ctx = this;
        setUI();
    }
    private void setUI()
    {
        tv_num.setText("x"+mItemListBean.getNumber());
        tv_price.setText("￥"+mItemListBean.getPrice());
        tv_goodsname.setText(mItemListBean.getCommodityName());
        String images=mItemListBean.getCommodityImage();
        if (!TextUtils.isEmpty(images)) {
            if(images.contains(",")){
                images=images.split(",")[0];
            }
        }
        Glide.with(this).load(images).into(iv_goods);
    }
    @OnClick({R.id.back,R.id.lay1,R.id.lay2})
    public void OnClick(View v) {
        Bundle bundle=new Bundle();
        bundle.putSerializable("data",mItemListBean);
        switch (v.getId()) {
            case R.id.back:
                onBackPressed();
                break;
            case R.id.lay1:
                bundle.putInt("type",1);
                ActivityAnimationUtils.commonTransition(OrderReturnTypeActivity.this,OrderReturnApplyActivity.class, ActivityConstans.Animation.FADE,bundle);
                break;
            case R.id.lay2:
                bundle.putInt("type",2);
                ActivityAnimationUtils.commonTransition(OrderReturnTypeActivity.this,OrderReturnApplyActivity.class, ActivityConstans.Animation.FADE,bundle);
                break;
        }
    }
}
