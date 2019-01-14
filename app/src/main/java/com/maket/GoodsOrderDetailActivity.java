package com.maket;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.entity.PhoneOrderEntity;
import com.example.yunchebao.R;
import com.maket.adapter.GoodsOrderChildAdapter;
import com.maket.adapter.GoodsOrderDetailAdapter;
import com.tool.BottomMenuDialog;
import com.tool.listview.PersonalListView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GoodsOrderDetailActivity extends AppCompatActivity {
    PhoneOrderEntity mPhoneOrderEntity;
    @BindView(R.id.lv_goods)
    PersonalListView lv_goods;
    @BindView(R.id.tv_ordernum)
    TextView tv_ordernum;
    @BindView(R.id.tv_status)
    TextView tv_status;
    @BindView(R.id.shopName)
    TextView shopName;
    @BindView(R.id.tv_goodsnum)
    TextView tv_goodsnum;
    @BindView(R.id.total1)
    TextView total1;
    @BindView(R.id.total2)
    TextView total2;
    @BindView(R.id.total3)
    TextView total3;
    @BindView(R.id.total4)
    TextView total4;
    GoodsOrderChildAdapter mGoodsOrderChildAdapter;
    private String[] items = new String[]{"我不想买了", "信息填写错误，重新拍", "卖家缺货", "同城见面交易", "其他原因"};
    private BottomMenuDialog bottomDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);
        mPhoneOrderEntity= (PhoneOrderEntity) getIntent().getSerializableExtra("data");
        ButterKnife.bind(this);
        initView();
    }
    private void initView(){
        mGoodsOrderChildAdapter=new GoodsOrderChildAdapter(this,mPhoneOrderEntity.getItemList());
        lv_goods.setAdapter(mGoodsOrderChildAdapter);
        tv_ordernum.setText("订单号: "+mPhoneOrderEntity.getOrderNo());
        tv_goodsnum.setText("共"+mPhoneOrderEntity.getNumber()+"件商品");
        total1.setText("￥"+mPhoneOrderEntity.getTotal());
        total2.setText("￥"+mPhoneOrderEntity.getTotal());
        total3.setText("￥"+mPhoneOrderEntity.getTotal());
        total4.setText("￥"+mPhoneOrderEntity.getTotal());
        switch(mPhoneOrderEntity.getState()){
            case 1:
                break;
            case 2:
                break;
            case 3:
                break;
            case 4:
                break;
        }
    }

    private void alertCancelPanel(Context ctx) {
        BottomMenuDialog.Builder builder = new BottomMenuDialog.Builder(ctx);
        builder.setTitle("请选择取消订单的理由");
        for (int i = 0; i < items.length; i++) {
            builder.addMenu(items[i], new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    bottomDialog.dismiss();
                }
            });
        }
        bottomDialog = builder.create();
        bottomDialog.show();
    }

}
