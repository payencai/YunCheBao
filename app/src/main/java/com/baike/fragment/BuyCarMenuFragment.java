package com.baike.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.TextView;
import android.widget.Toast;

import com.baike.BaikeDetailActivity;
import com.baike.adapter.BaikePersonAdapter;
import com.costans.PlatformContans;
import com.entity.GoodsListBean;
import com.entity.PhoneShopEntity;
import com.eowise.recyclerview.stickyheaders.OnHeaderClickListener;
import com.eowise.recyclerview.stickyheaders.StickyHeadersBuilder;
import com.eowise.recyclerview.stickyheaders.StickyHeadersItemDecoration;
import com.example.yunchebao.R;
import com.nohttp.sample.BaseFragment;
import com.nohttp.tools.HttpJsonClient;
import com.tool.ActivityAnimationUtils;
import com.tool.ActivityConstans;
import com.xihubao.OrderPayActivity;
import com.xihubao.adapter.BigramHeaderAdapter;
import com.xihubao.adapter.PersonAdapter;
import com.xihubao.adapter.RecycleGoodsCategoryListAdapter;

import java.util.ArrayList;
import java.util.List;


/**
 * 商品
 */
public class BuyCarMenuFragment extends BaseFragment implements OnHeaderClickListener {


    private RecyclerView mGoodsCateGoryList;
    private RecycleGoodsCategoryListAdapter mGoodsCategoryListAdapter;
    //商品类别列表
    private List<GoodsListBean.DataEntity.GoodscatrgoryEntity> goodscatrgoryEntities=new ArrayList<>();
    //商品列表
    private List<GoodsListBean.DataEntity.GoodscatrgoryEntity> goodsitemEntities=new ArrayList<>();

    //存储含有标题的第一个含有商品类别名称的条目的下表
    private List<Integer> titlePois = new ArrayList<>();
    //上一个标题的小标
    private String lastTitlePoi;
    private RecyclerView recyclerView;
    private BaikePersonAdapter personAdapter;
    private StickyHeadersItemDecoration top;
    private BigramHeaderAdapter headerAdapter;
    private LinearLayoutManager mLinearLayoutManager;
    private Context ctx;
    PhoneShopEntity entity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.shop_detail_goods, container, false);
        initView(view);
        initData(new ArrayList<GoodsListBean.DataEntity.GoodscatrgoryEntity>());
        return view;
    }

    private void initData(List<GoodsListBean.DataEntity.GoodscatrgoryEntity> dataList) {
        HttpJsonClient client = new HttpJsonClient();
        client.setResp(PlatformContans.data);
        List<GoodsListBean.DataEntity.GoodscatrgoryEntity> list =  client.getList(GoodsListBean.DataEntity.GoodscatrgoryEntity.class,"data");
        dataList.clear();
        dataList.addAll(list);
        int i = 0;
        int j = 0;
        boolean isFirst;
        goodscatrgoryEntities.clear();
        goodsitemEntities.clear();
        titlePois.clear();
        for (GoodsListBean.DataEntity.GoodscatrgoryEntity dataItem : dataList) {
            if (dataItem.getGoods() != null && dataItem.getGoods().size() > 0){
                goodscatrgoryEntities.add(dataItem);
                isFirst = true;
                List<GoodsListBean.DataEntity.GoodscatrgoryEntity> goodList = dataItem.getGoods();
                for (GoodsListBean.DataEntity.GoodscatrgoryEntity goodsitemEntity :goodList) {
                    if (isFirst) {
                        titlePois.add(j);
                        isFirst = false;
                    }
                    j++;
                    goodsitemEntity.setPosition(i);
                    goodsitemEntities.add(goodsitemEntity);
                }
                i++;
            }
        }

        mGoodsCategoryListAdapter = new RecycleGoodsCategoryListAdapter(goodscatrgoryEntities, getActivity());
        mGoodsCateGoryList.setLayoutManager(new LinearLayoutManager(getContext()));
        mGoodsCateGoryList.setAdapter(mGoodsCategoryListAdapter);
        mGoodsCategoryListAdapter.setOnItemClickListener(new RecycleGoodsCategoryListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                recyclerView.scrollToPosition(titlePois.get(position)+position+2);
                mGoodsCategoryListAdapter.setCheckPosition(position);
            }
        });

        mLinearLayoutManager =new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(mLinearLayoutManager);
        personAdapter = new BaikePersonAdapter(getActivity(),goodsitemEntities,goodscatrgoryEntities);
        personAdapter.setOnItemClickListener(new BaikePersonAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                ActivityAnimationUtils.commonTransition(getActivity(), BaikeDetailActivity.class, ActivityConstans.Animation.FADE);
            }
        });
        headerAdapter=new BigramHeaderAdapter(getActivity(),goodsitemEntities,goodscatrgoryEntities);
        top = new StickyHeadersBuilder()
                .setAdapter(personAdapter)
                .setRecyclerView(recyclerView)
                .setStickyHeadersAdapter(headerAdapter)
                .setOnHeaderClickListener(this)
                .build();
        recyclerView.addItemDecoration(top);
        recyclerView.setAdapter(personAdapter);
        recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                for (int i=0;i<titlePois.size();i++){
                    if(mLinearLayoutManager.findFirstVisibleItemPosition()>= titlePois.get(i)){
                        mGoodsCategoryListAdapter.setCheckPosition(i);
                    }
                }
            }
        });
    }




    private void initView(View view) {
        ctx = getActivity();
        mGoodsCateGoryList = (RecyclerView)view.findViewById(R.id.goods_category_list);
        recyclerView = (RecyclerView) view.findViewById(R.id.goods_recycleView);
        requestMethod(0,"");
    }

    private void requestMethod(int tag, String value) {
//        if (PlatformContans.OBJECT_MAP.get(PlatformContans.LoginContacts.ENTITY) != null && !PlatformContans.OBJECT_MAP.get(PlatformContans.LoginContacts.ENTITY).equals("")) {
//            entity = (PhoneShopEntity) PlatformContans.OBJECT_MAP.get(PlatformContans.LoginContacts.ENTITY);
//            Request<String> request = null;
//            request = NoHttp.createStringRequest(PlatformContans.rootUrl + PlatformContans.Urls.ShopUrls.getGoods+entity.getShop_type(), RequestMethod.GET);
//            request(0, request, httpListener, true, true);
//        }else  if (PlatformContans.OBJECT_MAP.get(PlatformContans.LoginContacts.SHOP_ID) != null && !PlatformContans.OBJECT_MAP.get(PlatformContans.LoginContacts.SHOP_ID).equals("")){
//            Request<String> request = null;
//            request = NoHttp.createStringRequest(PlatformContans.rootUrl + PlatformContans.Urls.ShopUrls.getGoods+PlatformContans.OBJECT_MAP.get(PlatformContans.LoginContacts.SHOP_ID), RequestMethod.GET);
//            request(0, request, httpListener, true, true);
//        }
    }

//    private HttpListener<String> httpListener = new HttpListener<String>() {
//        @Override
//        public void onSucceed(int what, Response response) {
//            String res = response.get().toString();
//            HttpJsonClient client = new HttpJsonClient();
//            client.setResp(res);
//            if (client.isSuccess()) {
////                GoodsListBean dataList = DataUtils.GsonToBean(client.getObject(String.class,"data"), GoodsListBean.class);
//                List<GoodsListBean.DataEntity.GoodscatrgoryEntity> rList = client.getList(GoodsListBean.DataEntity.GoodscatrgoryEntity.class,"data");
//                initData(rList);
//            } else {
//            }
//        }
//
//        @Override
//        public void onFailed(int what, Response<String> response) {
//        }
//    };


    @Override
    public void onHeaderClick(View header, long headerId) {
        TextView text = (TextView)header.findViewById(R.id.tvGoodsItemTitle);
        Toast.makeText(getActivity(), "Click on " + text.getText(), Toast.LENGTH_SHORT).show();
    }

    /**
     * 处理滑动 是两个ListView联动
     */
    private class MyOnGoodsScrollListener implements AbsListView.OnScrollListener {

        @Override
        public void onScrollStateChanged(AbsListView view, int scrollState) {

        }

        @Override
        public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
            if (!(lastTitlePoi.equals(goodsitemEntities.get(firstVisibleItem).getId()))) {
                lastTitlePoi = goodsitemEntities.get(firstVisibleItem).getId();
                mGoodsCategoryListAdapter.setCheckPosition(goodsitemEntities.get(firstVisibleItem).getPosition());
                mGoodsCategoryListAdapter.notifyDataSetChanged();
            }
        }
    }

}
