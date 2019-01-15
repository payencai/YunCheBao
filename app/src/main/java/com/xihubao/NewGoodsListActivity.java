package com.xihubao;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.costans.PlatformContans;
import com.entity.PhoneShopEntity;
import com.example.yunchebao.R;
import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.http.HttpProxy;
import com.http.ICallBack;
import com.maket.GoodDetailActivity;
import com.maket.adapter.KnowYouAdapter;
import com.maket.model.GoodList;
import com.nohttp.sample.NoHttpBaseActivity;
import com.payencai.library.util.ToastUtil;
import com.tool.ActivityAnimationUtils;
import com.tool.ActivityConstans;
import com.tool.UIControlUtils;
import com.xihubao.adapter.GoodsListAdapter;

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
 * Created by sdhcjhss on 2017/12/11.
 */

public class NewGoodsListActivity extends NoHttpBaseActivity {
    @BindView(R.id.listview)
    PullToRefreshListView refreshListView;
    ListView listView;
    private KnowYouAdapter adapter;
    private List<GoodList> list;
    private Context ctx;
    int page=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listview_newonly);
        initView();
    }

    private void initView() {
        UIControlUtils.UITextControlsUtils.setUIText(findViewById(R.id.title), ActivityConstans.UITag.TEXT_VIEW, "热卖商品");
        ButterKnife.bind(this);
        ctx = this;
        findViewById(R.id.topPanel).setVisibility(View.VISIBLE);

        refreshListView.setMode(PullToRefreshBase.Mode.BOTH);
        listView = refreshListView.getRefreshableView();
        listView.setDivider(getResources().getDrawable(R.color.gray_cc));
        listView.setDividerHeight(1);
        list = new ArrayList<>();
        adapter = new KnowYouAdapter(ctx, list);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                position--;
                Bundle bundle=new Bundle();
                bundle.putSerializable("data",list.get(position));
                ActivityAnimationUtils.commonTransition(NewGoodsListActivity.this, GoodDetailActivity.class, ActivityConstans.Animation.FADE,bundle);
                //ActivityAnimationUtils.commonTransition(NewGoodsListActivity.this, GoodDetailActivity.class, ActivityConstans.Animation.FADE);
            }
        });
        refreshListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
            @Override
            public void onRefresh(PullToRefreshBase<ListView> refreshView) {
                ToastUtil.showToast(NewGoodsListActivity.this,"刷新");
                refreshView.onRefreshComplete();
            }
        });
        refreshListView.setOnLastItemVisibleListener(new PullToRefreshBase.OnLastItemVisibleListener() {
            @Override
            public void onLastItemVisible() {
                ToastUtil.showToast(NewGoodsListActivity.this,"加载更多");

            }
        });
//        refreshListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
//            @Override
//            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
//
//            }
//
//            @Override
//            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
//
//            }
//        });
        getHotGoods();
    }

    private void getHotGoods(){
        Map<String,Object> params=new HashMap<>();
        params.put("page",page);
        HttpProxy.obtain().get(PlatformContans.GoodMenu.getHotCommodity, params,new ICallBack() {
            @Override
            public void OnSuccess(String result) {
                Log.e("getGoodMenu",result);
                Log.e("getdata", result);
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    JSONArray data = jsonObject.getJSONArray("data");
                    for (int i = 0; i < data.length(); i++) {
                        JSONObject item = data.getJSONObject(i);
                        GoodList baikeItem = new Gson().fromJson(item.toString(), GoodList.class);
                        list.add(baikeItem);
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
    @OnClick({R.id.back})
    public void OnClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                onBackPressed();
                break;
        }
    }
}
