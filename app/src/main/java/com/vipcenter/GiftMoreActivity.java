package com.vipcenter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.example.yunchebao.MyApplication;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.costans.PlatformContans;
import com.example.yunchebao.R;
import com.google.gson.Gson;
import com.http.HttpProxy;
import com.http.ICallBack;
import com.tool.ActivityAnimationUtils;
import com.tool.ActivityConstans;
import com.vipcenter.adapter.GiftMoreAdapter;
import com.vipcenter.model.Gift;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GiftMoreActivity extends AppCompatActivity {
    @BindView(R.id.rv_gift)
    RecyclerView rv_gift;
    GiftMoreAdapter mGiftMoreAdapter;
    List<Gift> mGiftList;
    int page=1;
    boolean isLoadMore=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gift_more);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        mGiftList=new ArrayList<>();
        mGiftMoreAdapter=new GiftMoreAdapter(R.layout.gift_record_list_item_layout,null);
        mGiftMoreAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Gift gift= (Gift) adapter.getItem(position);
                Bundle bundle=new Bundle();
                bundle.putSerializable("data",gift);
                ActivityAnimationUtils.commonTransition(GiftMoreActivity.this, GiftGoodDetailActivity.class, ActivityConstans.Animation.FADE,bundle);
            }
        });
        mGiftMoreAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                page++;
                isLoadMore=true;
                getData();
            }
        },rv_gift);
        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        rv_gift.setLayoutManager(new LinearLayoutManager(this));
        rv_gift.setAdapter(mGiftMoreAdapter);
        getData();
    }
    private void getData(){
        Map<String,Object> params=new HashMap<>();
        params.put("page",page);
        HttpProxy.obtain().get(PlatformContans.Gift.getGiftCommodityListToAPP, params, MyApplication.token,new ICallBack() {
            @Override
            public void OnSuccess(String result) {
                Log.e("getGiftCommodity", result);
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    jsonObject=jsonObject.getJSONObject("data");
                    JSONArray data = jsonObject.getJSONArray("beanList");
                    List<Gift> gifts=new ArrayList<>();
                    for (int i = 0; i < data.length(); i++) {
                        JSONObject item = data.getJSONObject(i);
                        Gift gift = new Gson().fromJson(item.toString(), Gift.class);
                        mGiftList.add(gift);
                        gifts.add(gift);
                    }
                    if(isLoadMore){
                        isLoadMore=false;
                        mGiftMoreAdapter.addData(gifts);
                        mGiftMoreAdapter.loadMoreComplete();
                    }else {
                        mGiftMoreAdapter.setNewData(mGiftList);
                        mGiftMoreAdapter.loadMoreEnd(true);
                    }
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

}
