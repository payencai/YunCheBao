package com.example.yunchebao.gift;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.yunchebao.MyApplication;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.costans.PlatformContans;
import com.example.yunchebao.R;
import com.google.gson.Gson;
import com.gyf.immersionbar.ImmersionBar;
import com.http.HttpProxy;
import com.http.ICallBack;
import com.payencai.library.util.ToastUtil;
import com.tool.ActivityAnimationUtils;
import com.tool.ActivityConstans;
import com.tool.UIControlUtils;
import com.tool.listview.PersonalScrollView;
import com.vipcenter.GiftBaobeiCenterActivity;
import com.vipcenter.GiftGoodDetailActivity;
import com.vipcenter.GiftMoreActivity;
import com.vipcenter.GiftRuleActivity;
import com.vipcenter.adapter.GiftHomeListAdapter;
import com.vipcenter.model.Gift;
import com.vipcenter.model.MyWallet;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by sdhcjhss on 2018/1/15.
 * 礼品汇首页
 */

public class GiftMarketHomeActivity extends AppCompatActivity {
    @BindView(R.id.rv_gift)
    RecyclerView rv_gift;
    @BindView(R.id.tv_coin)
    TextView tv_coin;
    @BindView(R.id.scrollview)
    PersonalScrollView mScrollView;
    GiftHomeListAdapter adapter;
    List<Gift> list;
    private Context ctx;
    int page=1;
    private RecyclerView.LayoutManager mLayoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gift_market_home);
        ImmersionBar.with(this).fitsSystemWindows(true).statusBarColor(R.color.wallet).init();
        initView();
    }

    private void initView() {
        ctx = this;
        ButterKnife.bind(this);
        UIControlUtils.UITextControlsUtils.setUIText(findViewById(R.id.title), ActivityConstans.UITag.TEXT_VIEW, "礼品汇");

        findViewById(R.id.title).setFocusable(true);
        findViewById(R.id.title).setFocusableInTouchMode(true);
        findViewById(R.id.title).requestFocus();
        list = new ArrayList<>();
        mLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        adapter = new GiftHomeListAdapter(R.layout.gift_home_list_item,list);
        rv_gift.setLayoutManager(mLayoutManager);
        rv_gift.setAdapter(adapter);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Gift gift= (Gift) adapter.getItem(position);
                Intent intent=new Intent(GiftMarketHomeActivity.this, GiftGoodDetailActivity.class);
                intent.putExtra("data",gift);
                startActivity(intent);

            }
        });

        mScrollView.setOnScrollBottomListener(new PersonalScrollView.OnScrollBottomListener() {
            @Override
            public void onScrollBottom() {
                ToastUtil.showToast(GiftMarketHomeActivity.this,"到底了");
            }
        });
        getData();
        getWallet();
    }

    MyWallet myWallet;
    private void getWallet() {
        HttpProxy.obtain().get(PlatformContans.User.getMyWallet, MyApplication.token, new ICallBack() {
            @Override
            public void OnSuccess(String result) {
                Log.e("result", result);
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    int code = jsonObject.getInt("resultCode");
                    if (code == 0) {
                        JSONObject data=jsonObject.getJSONObject("data") ;
                        myWallet=new Gson().fromJson(data.toString(),MyWallet.class);
                        tv_coin.setText(myWallet.getGoldCoin()+"");

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
    public void getData(){
        String token="";
        if(MyApplication.isLogin){
            token=MyApplication.token;
        }
      //  Log.e("token",MyApplication.getUserInfo().getToken());
        Map<String,Object> params=new HashMap<>();
        params.put("page",page);
        HttpProxy.obtain().get(PlatformContans.Gift.getGiftCommodityListToAPP, params, token,new ICallBack() {
            @Override
            public void OnSuccess(String result) {
                Log.e("getGiftCommodity", result);
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    jsonObject=jsonObject.getJSONObject("data");
                    JSONArray data = jsonObject.getJSONArray("beanList");
                    for (int i = 0; i < data.length(); i++) {
                        JSONObject item = data.getJSONObject(i);
                        Gift gift = new Gson().fromJson(item.toString(), Gift.class);
                        int height=(i % 2)*100 + 400;
                        gift.setHeight(height);
                        list.add(gift);
                    }
                    adapter.notifyDataSetChanged();
                    //updateData();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(String error) {

            }
        });
    }

    @OnClick({R.id.back, R.id.menuLay1, R.id.menuLay2, R.id.menuLay3})
    public void OnClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                onBackPressed();
                break;
            case R.id.menuLay1:
                ActivityAnimationUtils.commonTransition(GiftMarketHomeActivity.this, GiftBaobeiCenterActivity.class, ActivityConstans.Animation.FADE);
                break;
            case R.id.menuLay2:
                ActivityAnimationUtils.commonTransition(GiftMarketHomeActivity.this, GiftMoreActivity.class, ActivityConstans.Animation.FADE);
                break;
            case R.id.menuLay3:
                ActivityAnimationUtils.commonTransition(GiftMarketHomeActivity.this, GiftRuleActivity.class, ActivityConstans.Animation.FADE);
                break;
//            case R.id.hotGoodsLay:
//                ActivityAnimationUtils.commonTransition(GiftMarketHomeActivity.this, GiftGoodsListActivity.class, ActivityConstans.Animation.FADE);
//                break;
//            case R.id.menuBtn:
//                ActivityAnimationUtils.commonTransition(GiftMarketHomeActivity.this, GiftAllKindActivity.class, ActivityConstans.Animation.FADE);
//                break;

        }
    }
}
