package com.bbcircle;

import com.application.MyApplication;
import com.bbcircle.adapter.KindAdapter;
import com.bbcircle.fragment.KindFragment;
import com.costans.PlatformContans;
import com.example.yunchebao.R;
import com.google.gson.Gson;
import com.http.HttpProxy;
import com.http.ICallBack;
import com.maket.MarketSelectListActivity;
import com.maket.adapter.GridTypeAdapter;
import com.maket.model.GoodsType;
import com.nohttp.sample.NoHttpFragmentBaseActivity;
import com.tool.ActivityAnimationUtils;
import com.tool.ActivityConstans;
import com.tool.listview.PersonalListView;
import com.vipcenter.model.PersonAddress;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ListView;

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
 * Created by sdhcjhss on 2018/1/8.
 */

public class AllKindActivity extends NoHttpFragmentBaseActivity
        implements OnItemClickListener {
    private KindAdapter adapter;
    GridTypeAdapter mGridTypeAdapter;
    public static int mPosition;
    @BindView(R.id.listview)
    ListView listView;
    @BindView(R.id.pl_goods)
    GridView pl_goods;
    List<GoodsType> mFirstGoodsTypes=new ArrayList<>();
    List<GoodsType> mSecondGoodsTypes=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);//去掉标题栏
        setContentView(R.layout.all_kind_layout);
        ButterKnife.bind(this);
        initView();

    }

    /**
     * 初始化view
     */
    private void initView() {
        mGridTypeAdapter=new GridTypeAdapter(this,mSecondGoodsTypes);
        listView = (ListView) findViewById(R.id.listview);
        adapter = new KindAdapter(this, mFirstGoodsTypes);
        listView.setAdapter(adapter);
        pl_goods.setAdapter(mGridTypeAdapter);
        pl_goods.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bundle bundle =new Bundle();
                bundle.putString("id",mSecondGoodsTypes.get(position).getId());
                ActivityAnimationUtils.commonTransition(AllKindActivity.this, MarketSelectListActivity.class, ActivityConstans.Animation.FADE,bundle);
            }
        });
        listView.setOnItemClickListener(this);
        //创建MyFragment对象
        getFirstData("0");

    }

    private void getSecData(String id) {
        mSecondGoodsTypes.clear();
        Map<String, Object> params = new HashMap<>();
        params.put("superId", id);
        HttpProxy.obtain().get(PlatformContans.GoodMenu.getBabyMerchantCategoryList, params, new ICallBack() {
            @Override
            public void OnSuccess(String result) {
                Log.e("gettype", result);
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    JSONArray data = jsonObject.getJSONArray("data");
                    for (int i = 0; i < data.length(); i++) {
                        JSONObject item = data.getJSONObject(i);
                        GoodsType baikeItem = new Gson().fromJson(item.toString(), GoodsType.class);
                        mSecondGoodsTypes.add(baikeItem);
                    }
                    mGridTypeAdapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(String error) {

            }
        });
    }

    private void getFirstData(String id) {
        Map<String, Object> params = new HashMap<>();
        params.put("superId", id);
        HttpProxy.obtain().get(PlatformContans.GoodMenu.getBabyMerchantCategoryList, params, new ICallBack() {
            @Override
            public void OnSuccess(String result) {
                Log.e("gettype", result);
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    JSONArray data = jsonObject.getJSONArray("data");
                        for (int i = 0; i < data.length(); i++) {
                            JSONObject item = data.getJSONObject(i);
                            GoodsType baikeItem = new Gson().fromJson(item.toString(), GoodsType.class);
                            mFirstGoodsTypes.add(baikeItem);
                        }
                        if(data.length()>0){
                            getSecData(mFirstGoodsTypes.get(0).getId());
                        }
                        adapter.notifyDataSetChanged();
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
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        //拿到当前位置
        mPosition = position;
        //即使刷新adapter
        adapter.notifyDataSetChanged();
        getSecData(mFirstGoodsTypes.get(position).getId());

    }

//    @OnClick({R.id.back})
//    public void OnClick(View v) {
//        switch (v.getId()) {
//            case R.id.back:
//                onBackPressed();
//                break;
//        }
//    }
}
