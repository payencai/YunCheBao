package com.xihubao.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.costans.PlatformContans;
import com.entity.FourShop;
import com.entity.PhoneOrderEntity;
import com.entity.PhoneShopEntity;
import com.example.yunchebao.R;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.nohttp.NoHttp;
import com.nohttp.RequestMethod;
import com.nohttp.rest.Request;
import com.nohttp.rest.Response;
import com.nohttp.sample.BaseFragment;
import com.nohttp.sample.HttpListener;
import com.nohttp.tools.HttpJsonClient;
import com.tool.ActivityAnimationUtils;
import com.tool.ActivityConstans;
import com.xihubao.Shop4SOrderActivity;
import com.xihubao.adapter.Shop4SListAdapter;

import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;


public class Shop4SListFragment extends BaseFragment implements OnClickListener {


    private final static String TAG = "Shop4SListFragment";
    ListView mListView;

    PullToRefreshListView pullToRefreshListView;
    private int typeId;//
    private Shop4SListAdapter adapter;
    private Shop4SListFragment obj;
    private View view;
    private List<FourShop> list = new ArrayList<>();


    private boolean isDown = true;//true 下拉动作  false 上拉操作
    private int pageNum = 1;//查询页
    String time;
    String city = "";
    String car = "";
    String dis;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCar() {
        return car;
    }

    public void setCar(String car) {
        this.car = car;
    }

    public String getDis() {
        return dis;
    }

    public void setDis(String dis) {
        this.dis = dis;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        view = LayoutInflater.from(getActivity()).inflate(R.layout.listview_replace, null);
        initView();
        return view;
    }

    private void initView() {
        Log.e("data", time + car + dis + city);
        pullToRefreshListView = (PullToRefreshListView) view.findViewById(R.id.listview);
        mListView = pullToRefreshListView.getRefreshableView();
        mListView.setDivider(getResources().getDrawable(R.color.gray_ee));
        mListView.setDividerHeight(10);
        obj = this;
        adapter = new Shop4SListAdapter(getActivity(), typeId, obj, list);
        mListView.setAdapter(adapter);

        pullToRefreshListView.setMode(PullToRefreshBase.Mode.PULL_FROM_START);//支持下拉
        pullToRefreshListView.setScrollingWhileRefreshingEnabled(true);//滚动的时候不加载数据
        pullToRefreshListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                isDown = true;
                //pullToRefreshListView.setRefreshing();
                pageNum = 1;
                initListData();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                isDown = false;
                pageNum++;
                initListData();
            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();
        initListData();
    }

    public void initListData() {
        Request<String> request = null;
        request = NoHttp.createStringRequest(PlatformContans.FourShop.getFourShopListByApp, RequestMethod.GET);
        if (request != null) {
            request.add("page", pageNum);
            request.add("brand", car);
            request.add("longitude", "113.324343");
            request.add("latitude", "23.5456656");
            request.add("city", city);
            request(0, request, httpListener, true, true);
        }
    }

    public static Shop4SListFragment newInstance(String time) {
        Shop4SListFragment shop4SListFragment = new Shop4SListFragment();
        Bundle bundle = new Bundle();
        bundle.putString("time", time);
        shop4SListFragment.setArguments(bundle);
        return shop4SListFragment;
    }

    private HttpListener<String> httpListener = new HttpListener<String>() {
        @Override
        public void onSucceed(int what, Response response) {
            String res = response.get().toString();
            HttpJsonClient client = new HttpJsonClient();
            client.setResp(res);
            Log.e("res", res);
            pullToRefreshListView.onRefreshComplete();//停止刷新
            switch (what) {
                case 0:
                    List<FourShop> mList = client.getList(FourShop.class, "data.beanList");
                    if (isDown) {
                        list.clear();
                    }
                    Log.e("size", mList.size() + "");
                    list.addAll(mList);
                    if (adapter == null) {
                        return;
                    }
                    adapter.notifyDataSetChanged();
                    break;
                case 1:
                    isDown = true;
                    initListData();
                    break;
            }


        }

        @Override
        public void onFailed(int what, com.nohttp.rest.Response<String> response) {
            // setToast("onFailed");
            pullToRefreshListView.onRefreshComplete();//停止刷新
        }
    };

    String buyTime;

    public String getBuyTime() {
        return buyTime;
    }

    public void setBuyTime(String buyTime) {
        this.buyTime = buyTime;
    }

    //adapter中按钮点击事件
    public void onShortcutMenuClickListener(Integer t, Integer loc) {
        int position = loc.intValue();

        switch (t) {
            case 1://预约上午
                Bundle bundle1=new Bundle();
                bundle1.putString("maintainTime",time+" "+"08:00:00");
                bundle1.putString("buyTime",buyTime);
                bundle1.putString("mileage",dis);
                bundle1.putString("carType",car);
                bundle1.putString("city",city);
                bundle1.putSerializable("fourshop",list.get(position));
                Log.e("bundle",bundle1.toString());
                ActivityAnimationUtils.commonTransition(getActivity(), Shop4SOrderActivity.class, ActivityConstans.Animation.FADE,bundle1);
                break;
            case 2://预约下午
                Bundle bundle2=new Bundle();
                bundle2.putString("maintainTime",time+" "+"13:00:00");
                bundle2.putString("buyTime",buyTime);
                bundle2.putString("mileage",dis);
                bundle2.putString("carType",car);
                bundle2.putString("city",city);
                bundle2.putSerializable("fourshop",list.get(position));
                Log.e("bundle",bundle2.toString());
                ActivityAnimationUtils.commonTransition(getActivity(), Shop4SOrderActivity.class, ActivityConstans.Animation.FADE,bundle2);
                break;
        }
    }


    /* 摧毁该Fragment，一般是FragmentActivity 被摧毁的时候伴随着摧毁 */
    @Override
    public void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();

    }


    public void setPosition(int position) {
        setTypeId(position);
//        adapter.setTypeId(position);

    }

    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    @Override
    public void onClick(View v) {


    }
}
