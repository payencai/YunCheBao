package com.vipcenter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import com.entity.PhoneGoodEntity;
import com.example.yunchebao.R;
import com.maket.adapter.GoodsPayAdapter;
import com.maket.model.GoodsSelect;
import com.nohttp.sample.NoHttpBaseActivity;
import com.tool.ActivityAnimationUtils;
import com.tool.ActivityConstans;
import com.tool.UIControlUtils;
import com.tool.listview.PersonalListView;
import com.vipcenter.view.PayCashierDialog;
import com.vipcenter.view.PayWayDialog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.tool.MyProgressDialog.dialog;

/**
 * Created by sdhcjhss on 2018/1/5.
 */

public class OrderConfirmActivity extends NoHttpBaseActivity {
    private Context ctx;
    ArrayList<GoodsSelect> mGoodsSelects = new ArrayList<>();
    ArrayList<PhoneGoodEntity> mselectGoods = new ArrayList<>();
    GoodsPayAdapter mGoodsPayAdapter;
    @BindView(R.id.lv_select)
    PersonalListView lv_select;
    List<PhoneGoodEntity> newSortGoods = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        View view = LayoutInflater.from(this).inflate(R.layout.order_confim_layout, null);
        setContentView(view);
        ButterKnife.bind(this);
        ctx = this;
        mGoodsSelects = (ArrayList<GoodsSelect>) getIntent().getExtras().getSerializable("list");
        mselectGoods = (ArrayList<PhoneGoodEntity>) getIntent().getExtras().getSerializable("select");
        initView();
    }

    Map<String, Integer> mapCount = new HashMap<>();
    int lastNum = 0;

    private void initView() {
        for (int i = 0; i < mGoodsSelects.size(); i++) {
            int count = 0;
            GoodsSelect mGoodsSelect = mGoodsSelects.get(i);
            String shopid = mGoodsSelect.getShopId();
            for (int j = 0; j < mselectGoods.size(); j++) {
                PhoneGoodEntity phoneGoodEntity = mselectGoods.get(j);
                if (phoneGoodEntity.getShopId().equals(shopid)) {
                    count++;
                }
            }
            mapCount.put(shopid, count);
        }
        for (int i = 0; i < mGoodsSelects.size(); i++) {
            GoodsSelect mGoodsSelect = mGoodsSelects.get(i);
            String shopid = mGoodsSelect.getShopId();
            int num = mapCount.get(shopid);
            Log.e("num", num + "");
            double totalmoney=0;
            for (int j = lastNum; j < mselectGoods.size(); j++) {
                PhoneGoodEntity phoneGoodEntity = mselectGoods.get(j);
                if (phoneGoodEntity.getShopId().equals(shopid)) {
                    if (num == 1) {//只有一个
                        phoneGoodEntity.setFlag(3);//添加首尾
                        totalmoney=totalmoney+phoneGoodEntity.getCount()*(Double.parseDouble(phoneGoodEntity.getPrice()));
                        phoneGoodEntity.setTotalPrice(totalmoney);
                        lastNum ++;
                    } else if (num > 1) {
                        totalmoney=totalmoney+phoneGoodEntity.getCount()*(Double.parseDouble(phoneGoodEntity.getPrice()));
                        if (j == lastNum) {
                            phoneGoodEntity.setFlag(1);
                        } else if(j==(lastNum+num-1)){
                            phoneGoodEntity.setFlag(2);
                            phoneGoodEntity.setTotalPrice(totalmoney);
                            lastNum=j+1;
                        }
                    }
                    newSortGoods.add(phoneGoodEntity);
                }

            }
        }
        mGoodsPayAdapter = new GoodsPayAdapter(ctx, newSortGoods);
        lv_select.setAdapter(mGoodsPayAdapter);

    }

    /**
     * 初始化支付方式Dialog
     */
    private void initDialog() {
        // 隐藏输入法
        dialog = new PayCashierDialog(this, R.style.recharge_pay_dialog, true, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //确认充值
            }
        });
        dialog.show();
    }

    @OnClick({R.id.back, R.id.submit, R.id.addressLay})
    public void OnClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                onBackPressed();
                break;
            case R.id.submit:
                initDialog();
                break;
            case R.id.addressLay:
                startActivityForResult(new Intent(OrderConfirmActivity.this, AddressListActivity.class), 1);
                break;
        }
    }
}
