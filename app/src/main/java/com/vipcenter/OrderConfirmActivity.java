package com.vipcenter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.application.MyApplication;
import com.costans.PlatformContans;
import com.entity.PhoneGoodEntity;
import com.example.yunchebao.R;
import com.google.gson.Gson;
import com.http.HttpProxy;
import com.http.ICallBack;
import com.maket.GoodsPayActivity;
import com.maket.adapter.GoodsPayAdapter;
import com.maket.model.GoodsSelect;
import com.nohttp.sample.NoHttpBaseActivity;
import com.tool.ActivityAnimationUtils;
import com.tool.ActivityConstans;
import com.tool.UIControlUtils;
import com.tool.listview.PersonalListView;
import com.vipcenter.model.PersonAddress;
import com.vipcenter.view.PayCashierDialog;
import com.vipcenter.view.PayWayDialog;
import com.xihubao.fragment.GoodsFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

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
    @BindView(R.id.tv_contact)
    TextView tv_contact;
    @BindView(R.id.tv_default)
    TextView tv_default;
    @BindView(R.id.tv_totalmoney)
    TextView tv_totalmoney;
    @BindView(R.id.tv_addrname)
    TextView tv_addrname;
    @BindView(R.id.tv_detail)
    TextView tv_detail;

    List<PhoneGoodEntity> newSortGoods = new ArrayList<>();
    List<GoodsSelect> newGoodsSelects = new ArrayList<>();
    PersonAddress personAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        View view = LayoutInflater.from(this).inflate(R.layout.order_confim_layout, null);
        setContentView(view);
        ButterKnife.bind(this);
        ctx = this;
        Bundle bundle=getIntent().getExtras();
        if(bundle!=null){
        mGoodsSelects = (ArrayList<GoodsSelect>) bundle.getSerializable("list");
        mselectGoods = (ArrayList<PhoneGoodEntity>) bundle.getSerializable("select");}
        initView();
    }

    Map<String, Integer> mapCount = new HashMap<>();
    int lastNum = 0;
    double money;

    private void initView() {
        getAddress();
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
            double totalmoney = 0;
            for (int j = lastNum; j < mselectGoods.size(); j++) {
                PhoneGoodEntity phoneGoodEntity = mselectGoods.get(j);
                if (phoneGoodEntity.getShopId().equals(shopid)) {
                    if (num == 1) {//只有一个
                        phoneGoodEntity.setFlag(3);//添加首尾
                        totalmoney = totalmoney + phoneGoodEntity.getCount() * (Double.parseDouble(phoneGoodEntity.getPrice()));
                        phoneGoodEntity.setTotalPrice(totalmoney);
                        money = money + totalmoney;
                        lastNum++;
                    } else if (num > 1) {
                        totalmoney = totalmoney + phoneGoodEntity.getCount() * (Double.parseDouble(phoneGoodEntity.getPrice()));
                        if (j == lastNum) {
                            phoneGoodEntity.setFlag(1);

                        } else if (j == (lastNum + num - 1)) {
                            phoneGoodEntity.setFlag(2);
                            phoneGoodEntity.setTotalPrice(totalmoney);
                            money = money + totalmoney;

                            lastNum = j + 1;
                        }
                    }
                    newSortGoods.add(phoneGoodEntity);
                }

            }
            newGoodsSelects.add(mGoodsSelect);
        }
        tv_totalmoney.setText("￥" + money);
        mGoodsPayAdapter = new GoodsPayAdapter(ctx, newSortGoods);
        lv_select.setAdapter(mGoodsPayAdapter);

    }


    public void saveEditData(int position, String str) {
        PhoneGoodEntity goodEntity = newSortGoods.get(position);
        goodEntity.setRemark(str);
        newSortGoods.remove(position);
        newSortGoods.add(position, goodEntity);


        //Toast.makeText(this,str+"----"+position,Toast.LENGTH_LONG).show();
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

    List<GoodsSelect> finalGoodsSelect = new ArrayList<>();

    @OnClick({R.id.back, R.id.submit, R.id.addressLay})
    public void OnClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                onBackPressed();
                break;
            case R.id.submit:
                if (MyApplication.isIsLogin())
                    for (int i = 0; i < newGoodsSelects.size(); i++) {
                        String remark = "hhh";
                        GoodsSelect goodsSelect = newGoodsSelects.get(i);
                        for (int j = 0; j < newSortGoods.size(); j++) {
                            if (goodsSelect.getShopId().equals(newSortGoods.get(j).getShopId())) {
                                if (newSortGoods.get(j).getFlag() >= 2) {
                                    remark = newSortGoods.get(j).getRemark();
                                }
                            }
                        }
                        goodsSelect.setRemark(remark);
                        goodsSelect.setName(personAddress.getNickname());
                        goodsSelect.setTelephone(personAddress.getTelephone());
                        goodsSelect.setAddress(personAddress.getProvince() + personAddress.getCity() + personAddress.getDistrict() + personAddress.getAddress());
                        finalGoodsSelect.add(goodsSelect);
                    }
                String json = new Gson().toJson(finalGoodsSelect);
                json = "{\n" +
                        "  \"data\": " + json + "}";
                Log.e("newGoodsSelects", json);
                takeOrder(json);
               // initDialog();
                break;
            case R.id.addressLay:
                startActivityForResult(new Intent(OrderConfirmActivity.this, AddressListActivity.class), 1);
                break;
        }
    }


    private void takeOrder(String json){
        String token = "";
        if (MyApplication.isLogin) {
            token = MyApplication.getUserInfo().getToken();
        } else {
            return;
            // tv.setText(MyApplication.getaMapLocation().getProvince() + MyApplication.getaMapLocation().getCity() + MyApplication.getaMapLocation().getDistrict());
        }
      //  Map<String,Object> params=new HashMap<>();
        HttpProxy.obtain().post(PlatformContans.GoodsOrder.addOrderByShoppingCar, token, json, new ICallBack() {
            @Override
            public void OnSuccess(String result) {
                Log.e("rsult",result);
                try {
                    JSONObject jsonObject=new JSONObject(result);
                    int code=jsonObject.getInt("resultCode");
                    if(code==0){
                        String orderId=jsonObject.getString("data");

                        Intent intent=new Intent(OrderConfirmActivity.this,GoodsPayActivity.class);
                        intent.putExtra("orderid",orderId);
                        intent.putExtra("money",money);
                        startActivity(intent);
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



    private void getAddress() {
        String token = "";
        if (MyApplication.isLogin) {
            token = MyApplication.getUserInfo().getToken();
        } else {
            // tv.setText(MyApplication.getaMapLocation().getProvince() + MyApplication.getaMapLocation().getCity() + MyApplication.getaMapLocation().getDistrict());
        }
        Map<String, Object> params = new HashMap<>();
        params.put("page", 1);
        params.put("isDefault", 1);
        HttpProxy.obtain().get(PlatformContans.AddressManage.getUserAddress, params, token, new ICallBack() {
            @Override
            public void OnSuccess(String result) {

                Log.e("getAddress", result);
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    JSONArray data = jsonObject.getJSONArray("data");
                    int code = jsonObject.getInt("resultCode");
                    if (code == 0) {

                        for (int i = 0; i < data.length(); i++) {
                            JSONObject item = data.getJSONObject(i);
                            personAddress = new Gson().fromJson(item.toString(), PersonAddress.class);

                        }
                        tv_contact.setText(personAddress.getNickname() + "    " + personAddress.getTelephone());
                        if (personAddress.getIsDefault() == 2) {
                            tv_default.setVisibility(View.GONE);
                        }
                        tv_addrname.setText(personAddress.getProvince() + personAddress.getCity() + personAddress.getDistrict());
                        tv_detail.setText(personAddress.getAddress());

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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && data != null) {
            personAddress = (PersonAddress) data.getSerializableExtra("address");
            tv_contact.setText(personAddress.getNickname() + "    " + personAddress.getTelephone());
            if (personAddress.getIsDefault() == 2) {
                tv_default.setVisibility(View.GONE);
            }
            tv_addrname.setText(personAddress.getProvince() + personAddress.getCity() + personAddress.getDistrict());
            tv_detail.setText(personAddress.getAddress());
        }
    }
}
