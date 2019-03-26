package com.vipcenter;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.entity.PhoneOrderEntity;
import com.example.yunchebao.R;
import com.tool.view.ListViewForScrollView;
import com.vipcenter.adapter.RebackMoneyAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RebackMoneyActivity extends AppCompatActivity {
    PhoneOrderEntity mPhoneOrderEntity;
    RebackMoneyAdapter mRebackMoneyAdapter;
    List<PhoneOrderEntity.ItemListBean> mItemListBeans=new ArrayList<>();
    @BindView(R.id.lv_goods)
    ListViewForScrollView lv_goods;
    @BindView(R.id.tv_state)
    TextView tv_state;
    @BindView(R.id.tv_time)
    TextView tv_time;
    @BindView(R.id.tv_total)
    TextView tv_total;
    @BindView(R.id.tv_money)
    TextView tv_money;
    @BindView(R.id.tv_chat)
    TextView tv_chat;
    double money=0;
    int state=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reback_money);
        mPhoneOrderEntity= (PhoneOrderEntity) getIntent().getSerializableExtra("data");
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        for (int i = 0; i <mPhoneOrderEntity.getItemList().size() ; i++) {
            PhoneOrderEntity.ItemListBean itemListBean=mPhoneOrderEntity.getItemList().get(i);
            if(itemListBean.getRefuseState()==2||itemListBean.getRefuseState()==1||itemListBean.getRefuseState()==3){
                money=money+itemListBean.getRefusePrice();
                state=itemListBean.getRefuseState();
                itemListBean.setHandleTime(mPhoneOrderEntity.getCreateTime());
                mItemListBeans.add(itemListBean);
            }
        }
        tv_total.setText("￥"+mPhoneOrderEntity.getTotal());
        tv_money.setText("￥"+money);
        mRebackMoneyAdapter=new RebackMoneyAdapter(this,mItemListBeans);
        lv_goods.setAdapter(mRebackMoneyAdapter);
        switch (state){
            case 3:
                tv_state.setText("已拒绝");
                tv_time.setText(mPhoneOrderEntity.getFinishTime()+"");
                break;

            case 2:
                tv_state.setText("已退款");
                tv_time.setText(mPhoneOrderEntity.getFinishTime());
                break;
        }
        tv_chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callPhone(mPhoneOrderEntity.getMerchTelephone());
            }
        });
    }
    public void callPhone(String phoneNum) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        Uri data = Uri.parse("tel:" + phoneNum);
        intent.setData(data);
        startActivity(intent);
    }
}
