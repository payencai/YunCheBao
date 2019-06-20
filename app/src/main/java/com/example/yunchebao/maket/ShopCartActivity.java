package com.example.yunchebao.maket;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.yunchebao.MyApplication;
import com.costans.PlatformContans;
import com.entity.PhoneGoodEntity;
import com.example.yunchebao.R;
import com.http.HttpProxy;
import com.http.ICallBack;
import com.example.yunchebao.maket.adapter.ShopCartAdapter;
import com.example.yunchebao.maket.model.GoodsSelect;
import com.nohttp.sample.NoHttpBaseActivity;
import com.payencai.library.util.ToastUtil;
import com.tool.ActivityConstans;
import com.tool.MathUtil;
import com.tool.UIControlUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class ShopCartActivity extends NoHttpBaseActivity {

    private TextView tvShopCartSubmit, tvShopCartSelect, tvShopCartTotalNum;

    private RecyclerView rlvShopCart, rlvHotProducts;
    private ShopCartAdapter mShopCartAdapter;
    private LinearLayout llPay;
    private RelativeLayout rlHaveProduct;
    private List<PhoneGoodEntity> mAllOrderList = new ArrayList<>();
    private ArrayList<PhoneGoodEntity> mGoPayList = new ArrayList<>();
    private List<String> mHotProductsList = new ArrayList<>();
    private TextView tvShopCartTotalPrice;
    private int mCount, mPosition;
    private double mTotalPrice1;
    private boolean mSelect;
    private ArrayList<PhoneGoodEntity> mselectGoods = new ArrayList<>();

    public void deleteGoods(String id) {
        String token = "";
        if (MyApplication.isLogin) {
            token = MyApplication.token;
        }
        Map<String, Object> params = new HashMap<>();
        params.put("shoppingCarId", id);
        Log.e("params", params.toString());
        HttpProxy.obtain().post(PlatformContans.GoodsOrder.deleteShoppingCar,
                token, params, new ICallBack() {
                    @Override
                    public void OnSuccess(String result) {
                        Log.e("data", result);
                        //ToastUtil.showToast(ShopCartActivity.this, "修改成功");
                    }

                    @Override
                    public void onFailure(String error) {

                    }
                });
    }

    public void updateGoodsCar(String id, int number) {
        String token = "";
        if (MyApplication.isLogin) {
            token = MyApplication.token;
        }
        Map<String, Object> params = new HashMap<>();
        params.put("shoppingCarId", id);
        params.put("number", number);
        Log.e("params", params.toString());
        HttpProxy.obtain().post(PlatformContans.GoodsOrder.updateShoppingCarNumber,
                token, params, new ICallBack() {
                    @Override
                    public void OnSuccess(String result) {
                        Log.e("data", result);
                        //ToastUtil.showToast(ShopCartActivity.this, "修改成功");
                    }

                    @Override
                    public void onFailure(String error) {

                    }
                });
    }

    List<String> shopIds = new ArrayList<>();
    Map<String, String> shopIdName = new HashMap<>();
    ArrayList<GoodsSelect> mGoodsSelects=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shop_cart_layout);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().getDecorView()
                    .setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            getWindow().setStatusBarColor(getResources().getColor(R.color.white));
        }
        initView();
        tvShopCartSubmit= (TextView) findViewById(R.id.tv_shopcart_submit);
        tvShopCartSelect = (TextView) findViewById(R.id.tv_shopcart_addselect);
        tvShopCartTotalPrice = (TextView) findViewById(R.id.tv_shopcart_totalprice);
        tvShopCartTotalNum = (TextView) findViewById(R.id.tv_shopcart_totalnum);

        rlHaveProduct = (RelativeLayout) findViewById(R.id.rl_shopcart_have);
        rlvShopCart = (RecyclerView) findViewById(R.id.rlv_shopcart);
        llPay = (LinearLayout) findViewById(R.id.ll_pay);
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.FILL_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        lp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
        llPay.setLayoutParams(lp);



        rlvShopCart.setLayoutManager(new LinearLayoutManager(this));
        mShopCartAdapter = new ShopCartAdapter(this, mAllOrderList);
        rlvShopCart.setAdapter(mShopCartAdapter);
        //删除商品接口
        mShopCartAdapter.setOnDeleteClickListener(new ShopCartAdapter.OnDeleteClickListener() {
            @Override
            public void onDeleteClick(int position, String cartid) {
                deleteGoods(cartid);
                mShopCartAdapter.notifyDataSetChanged();
            }
        });
        //修改数量接口
        mShopCartAdapter.setOnEditClickListener(new ShopCartAdapter.OnEditClickListener() {
            @Override
            public void onEditClick(int position, String cartid, int count) {
                mCount = count;
                mPosition = position;
                updateGoodsCar(cartid, count);
            }
        });
        //实时监控全选按钮
        mShopCartAdapter.setResfreshListener(new ShopCartAdapter.OnResfreshListener() {
            @Override
            public void onResfresh(boolean isSelect) {
                mSelect = isSelect;
                if (isSelect) {
                    Drawable left = getResources().getDrawable(R.mipmap.yellowcheck_icon);
                    tvShopCartSelect.setCompoundDrawablesWithIntrinsicBounds(left, null, null, null);
                } else {
                    Drawable left = getResources().getDrawable(R.mipmap.gray_circle);
                    tvShopCartSelect.setCompoundDrawablesWithIntrinsicBounds(left, null, null, null);
                }
                double mTotalPrice = 0;
                int mTotalNum = 0;
                mTotalPrice1 = 0;
                mGoPayList.clear();
                for (int i = 0; i < mAllOrderList.size(); i++)
                    if (mAllOrderList.get(i).getIsSelect()) {
                        mTotalPrice += Double.parseDouble(MathUtil.getDoubleTwo(mAllOrderList.get(i).getPrice())) * mAllOrderList.get(i).getCount();
                        mTotalNum += 1;
                        mGoPayList.add(mAllOrderList.get(i));
                    }
                mTotalPrice1 = mTotalPrice;
                tvShopCartTotalPrice.setText("总价：" + MathUtil.getDoubleTwo(mTotalPrice));
                tvShopCartTotalNum.setText("共" + mTotalNum + "件商品");
            }
        });
        tvShopCartSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                mselectGoods.clear();
                mGoodsSelects.clear();
                for (int i = 0; i < mAllOrderList.size(); i++) {
                    PhoneGoodEntity phoneShopEntity = mAllOrderList.get(i);
                    if (phoneShopEntity.getIsSelect()) {
                        mselectGoods.add(phoneShopEntity);
                        if (!shopIds.contains(phoneShopEntity.getShopId())) {
                            shopIds.add(phoneShopEntity.getShopId());
                            shopIdName.put(phoneShopEntity.getShopId(), phoneShopEntity.getShopName());
                        }

                    }
                }
                for (int i = 0; i < shopIds.size(); i++) {
                    GoodsSelect goodsSelect=new GoodsSelect();
                    String shoppingCarIds = "";
                    String shopid = shopIds.get(i);
                    for (int j = 0; j < mselectGoods.size(); j++) {
                        PhoneGoodEntity phoneShopEntity = mselectGoods.get(j);
                        if (shopid.equals(phoneShopEntity.getShopId())) {
                            shoppingCarIds=shoppingCarIds+","+phoneShopEntity.getId();
                            // shoppingCarIds.add(phoneShopEntity.getId());
                        }
                    }
                    if(!TextUtils.isEmpty(shoppingCarIds))
                    goodsSelect.setShoppingCarIds(shoppingCarIds.substring(1));
                    goodsSelect.setShopId(shopid);
                    goodsSelect.setShopName(shopIdName.get(shopid));
                    mGoodsSelects.add(goodsSelect);
                }
                if(mGoodsSelects.size()==0){
                    ToastUtil.showToast(ShopCartActivity.this,"暂无商品");
                    return;
                }
                Bundle bundle=new Bundle();
                Log.e("omclick","oclick");
                bundle.putSerializable("select",mselectGoods);
                bundle.putSerializable("list",mGoodsSelects);
                Intent intent=new Intent(ShopCartActivity.this, OrderConfirmActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
              //  ActivityAnimationUtils.commonTransition(ShopCartActivity.this, OrderConfirmActivity.class, ActivityConstans.Animation.FADE,bundle);
            }
        });


        //全选
        tvShopCartSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSelect = !mSelect;
                if (mSelect) {
                    Drawable left = getResources().getDrawable(R.mipmap.yellowcheck_icon);
                    tvShopCartSelect.setCompoundDrawablesWithIntrinsicBounds(left, null, null, null);
                    for (int i = 0; i < mAllOrderList.size(); i++) {
                        mAllOrderList.get(i).setSelect(true);
                        mAllOrderList.get(i).setShopSelect(true);
                    }
                } else {
                    Drawable left = getResources().getDrawable(R.mipmap.gray_circle);
                    tvShopCartSelect.setCompoundDrawablesWithIntrinsicBounds(left, null, null, null);
                    for (int i = 0; i < mAllOrderList.size(); i++) {
                        mAllOrderList.get(i).setSelect(false);
                        mAllOrderList.get(i).setShopSelect(false);
                    }
                }
                mShopCartAdapter.notifyDataSetChanged();

            }
        });

        // initData();
        getShopCar();
        mShopCartAdapter.notifyDataSetChanged();
    }

    private void initView() {
        UIControlUtils.UITextControlsUtils.setUIText(findViewById(R.id.title), ActivityConstans.UITag.TEXT_VIEW, "购物车");
        ButterKnife.bind(this);
    }



    public void getShopCar() {
        HttpProxy.obtain().get(PlatformContans.GoodsOrder.getShoppingCarList, MyApplication.token, new ICallBack() {
            @Override
            public void OnSuccess(String result) {
                Log.e("jjj", result);
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    JSONArray data = jsonObject.getJSONArray("data");
                    for (int i = 0; i < data.length(); i++) {
                        JSONObject item = data.getJSONObject(i);
                        JSONArray shopcar = item.getJSONArray("shoppingCarList");
                        for (int j = 0; j < shopcar.length(); j++) {
                            JSONObject shop = shopcar.getJSONObject(j);
                            PhoneGoodEntity sb = new PhoneGoodEntity();
                            sb.setShopName(shop.getString("shopName"));
                            sb.setShopId(shop.getString("shopId"));
                            sb.setPrice(Double.parseDouble(MathUtil.getDoubleTwo(shop.getDouble("price")) + ""));
                            sb.setProductId(shop.getString("commodityId"));
                            sb.setDefaultPic(shop.getString("commodityImage"));
                            sb.setProductName(shop.getString("commodityName"));
                            sb.setCount(shop.getInt("number"));
                            sb.setId(shop.getString("id"));
                           // sb.setOriginalPrice(shop.getDouble("originalPrice"));
                            sb.setFirstSpecificationValue(shop.getString("firstSpecificationValue"));
                            sb.setFirstSpecificationName(shop.getString("firstSpecificationName"));
                            sb.setFirstSpecificationId(shop.getString("firstSpecificationId"));
                            sb.setSecondSpecificationValue(shop.getString("secondSpecificationValue"));
                            sb.setSecondSpecificationName(shop.getString("secondSpecificationName"));
                            sb.setSecondSpecificationId(shop.getString("secondSpecificationId"));
                            if (j == 0) {
                                sb.setIsFirst(1);
                            } else {
                                sb.setIsFirst(2);
                            }
                            mAllOrderList.add(sb);

                        }
                    }
                    //isSelectFirst(mAllOrderList);
                    mShopCartAdapter.notifyDataSetChanged();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(String error) {

            }
        });
    }

    public static void isSelectFirst(List<PhoneGoodEntity> list) {
        if (list.size() > 0) {
            list.get(0).setIsFirst(1);
            for (int i = 1; i < list.size(); i++) {
                if (list.get(i).getShopId().equals(list.get(i - 1).getShopId())) {
                    list.get(i).setIsFirst(2);
                } else {
                    list.get(i).setIsFirst(1);
                }
            }
        }

    }


    @OnClick({R.id.back})
    public void OnClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                onBackPressed();
                break;

        }
    }
}
