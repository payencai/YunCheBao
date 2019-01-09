package com.xihubao;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.application.MyApplication;
import com.baiiu.filter.DropDownMenu;
import com.baiiu.filter.interfaces.OnFilterDoneListener;
import com.costans.PlatformContans;
import com.entity.FilterUrl;
import com.entity.PhoneShopEntity;
import com.example.yunchebao.R;
import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.nohttp.NoHttp;
import com.nohttp.RequestMethod;
import com.nohttp.rest.Request;
import com.nohttp.rest.Response;
import com.nohttp.sample.HttpListener;
import com.nohttp.sample.NoHttpBaseActivity;
import com.nohttp.tools.HttpJsonClient;
import com.rongcloud.model.CarShop;
import com.tool.ActivityAnimationUtils;
import com.tool.ActivityConstans;
import com.tool.UIControlUtils;
import com.vipcenter.RegisterActivity;
import com.xihubao.adapter.DropMenuAdapter;
import com.xihubao.adapter.WashCarListAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by sdhcjhss on 2017/12/7.
 * 洗车店列表页
 */

public class WashCarListActivity extends NoHttpBaseActivity implements OnFilterDoneListener {
    private Context ctx;
    @BindView(R.id.dropDownMenu)
    DropDownMenu dropDownMenu;
    ListView mListView;
    @BindView(R.id.listview)
    PullToRefreshListView pullToRefreshListView;

    List<CarShop> list = new ArrayList<>();
    WashCarListAdapter adapter ;
    int page=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.washcar_shoplist);
        ButterKnife.bind(this);
        initView();
        initFilterDropDownView();
    }

    private void initView() {
        UIControlUtils.UITextControlsUtils.setUIText(findViewById(R.id.title), ActivityConstans.UITag.TEXT_VIEW, "洗衣店");
        ctx = this;
        mListView = pullToRefreshListView.getRefreshableView();
        mListView.setDivider(getResources().getDrawable(R.color.gray_cc));
        mListView.setDividerHeight(1);
        adapter = new WashCarListAdapter(ctx, list);
        mListView.setAdapter(adapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bundle bundle=new Bundle();
                Log.e("pos",position+"");
                bundle.putSerializable("id",list.get(position-1));
                bundle.putString("type","洗车店");
                bundle.putInt("flag",1);
                if(MyApplication.isLogin)
                ActivityAnimationUtils.commonTransition(WashCarListActivity.this, WashCarDetailActivity.class, ActivityConstans.Animation.FADE,bundle);
                else{
                    startActivity(new Intent(WashCarListActivity.this, RegisterActivity.class));
                }
            }
        });
        pullToRefreshListView.setMode(PullToRefreshBase.Mode.PULL_FROM_START);//支持下拉
        pullToRefreshListView.setScrollingWhileRefreshingEnabled(true);//滚动的时候不加载数据
        pullToRefreshListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                page = 1;
                list.clear();
                refreshView.setRefreshing();
                getData();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                page++;
                getData();
            }
        });
        getData();
    }

    private void getData() {

        Request<String> request = null;
        request = NoHttp.createStringRequest(PlatformContans.CarWashRepairShop
                .getCarWashRepairShopListByApp, RequestMethod.GET);
        request.add("page", page);
        request.add("type", 1);
        request.add("grade", 1);
        request.add("longitude", MyApplication.getaMapLocation().getLongitude());
        request.add("latitude", MyApplication.getaMapLocation().getLatitude());
        request.add("city", MyApplication.getaMapLocation().getCity());
        request(0, request, httpListener, true, true);
    }

    private HttpListener<String> httpListener = new HttpListener<String>() {
        @Override
        public void onSucceed(int what, Response response) throws JSONException {
            pullToRefreshListView.onRefreshComplete();
            String res = response.get().toString();
            Log.e("res",res);
            try {
                JSONObject jsonObject = new JSONObject(res);
                jsonObject = jsonObject.getJSONObject("data");
                JSONArray data = jsonObject.getJSONArray("beanList");
                for (int i = 0; i < data.length(); i++) {
                    JSONObject item = data.getJSONObject(i);
                    CarShop carShop = new Gson().fromJson(item.toString(), CarShop.class);
                    list.add(carShop);
                }
                adapter.notifyDataSetChanged();

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

        @Override
        public void onFailed(int what, Response<String> response) {
            pullToRefreshListView.onRefreshComplete();
        }
    };

    private void initFilterDropDownView() {
        String[] titleList = new String[]{"昆明", "服务类型", "默认排序"};
        dropDownMenu.setMenuAdapter(new DropMenuAdapter(this, titleList, this));
    }

    @Override
    public void onFilterDone(int position, String positionTitle, String urlValue) {
        if (position != 3) {
            dropDownMenu.setPositionIndicatorText(FilterUrl.instance().position, FilterUrl.instance().positionTitle);
        }
        dropDownMenu.close();
//        mFilterContentView.setText(FilterUrl.instance().toString());
    }

    @OnClick({R.id.back})
    public void OnClick(View v){
        switch (v.getId()){
            case R.id.back:
                onBackPressed();
                break;
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        FilterUrl.instance().clear();
    }
}
