package com.system.fragment;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.yunchebao.MyApplication;
import com.baike.BaikeTagActivity;
import com.baike.model.BaikeItem;
import com.baike.model.ClassifyWiki;
import com.bbcircle.adapter.BKItemAdapter;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.costans.PlatformContans;
import com.entity.Banner;
import com.example.yunchebao.R;
import com.google.gson.Gson;
import com.http.HttpProxy;
import com.http.ICallBack;
import com.system.SearchActivity;
import com.system.WebviewActivity;
import com.system.adapter.BaikeTypeAdapter;
import com.tool.view.HorizontalListView;
import com.vipcenter.RegisterActivity;
import com.vipcenter.UserCenterActivity;
import com.youth.banner.BannerConfig;
import com.youth.banner.listener.OnBannerListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class NewBaikeFragment extends Fragment {


    @BindView(R.id.user_center_icon)
    ImageView leftIcon;
    @BindView(R.id.search_lay)
    RelativeLayout search_lay;
    @BindView(R.id.rv_baike)
    RecyclerView rv_baike;
    private int type=1;
    private String id;
    BKItemAdapter mBaikeItemAdapter;
    int page = 1;
    boolean isLoadMore = false;
    List<Banner> mBanners = new ArrayList<>();
    List<BaikeItem> mBaikeItems ;
    List<String> images = new ArrayList<>();
    View rootView=null;
    View header;
    int pos=0;
    HorizontalListView lv_type;
    com.youth.banner.Banner mBanner;
    List<ClassifyWiki> mClassifyWikis1 ;
    BaikeTypeAdapter mBaikeTypeAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_new_baike, container, false);
        ButterKnife.bind(this, rootView);
        mBaikeItems=new ArrayList<>();
        mClassifyWikis1=new ArrayList<>();
        mBaikeItemAdapter=new BKItemAdapter(R.layout.item_baike,mBaikeItems);
        mBaikeItemAdapter.setHeaderView(getheaderView());
        rv_baike.setLayoutManager(new LinearLayoutManager(getContext()));
        rv_baike.setAdapter(mBaikeItemAdapter);
        mBaikeItemAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                BaikeItem baikeItem= (BaikeItem) adapter.getItem(position);
                Intent intent = new Intent(getContext(), WebviewActivity.class);
                String token="";
                if(MyApplication.isLogin){
                    token=MyApplication.token;
                }
                intent.putExtra("url", "http://www.yunchebao.com:8080/h5baby/?id="+baikeItem.getId()+"&token="+token);
                startActivity(intent);
            }
        });
        mBaikeItemAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {

                loadMore();
                // loadData();
            }
        });
        leftIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(MyApplication.isLogin){
                    startActivity(new Intent(getContext(),UserCenterActivity.class));
                }else{
                    startActivity(new Intent(getContext(),RegisterActivity.class));
                }
            }
        });
        search_lay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), SearchActivity.class));
            }
        });
        getType(type);
        return rootView;
    }
    public void loadMore() {
        isLoadMore = true;
        page++;
        getData(id);
    }
    private void initBanner() {
        mBanner.setImageLoader(new com.youth.banner.loader.ImageLoader() {
            @Override
            public void displayImage(Context context, Object path, ImageView imageView) {
                //此处可以自行选择，我直接用的Picasso
                imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                Glide.with(getContext()).load((String) path).into(imageView);
            }
        });
        mBanner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
                Log.e("url", mBanners.get(position).getPicture() + "-" + mBanners.get(position).getSkipUrl());
                Intent intent = new Intent(getContext(), WebviewActivity.class);
                String url = mBanners.get(position).getSkipUrl();
                if (!url.contains("http") && !url.contains("https")) {
                    url = "http://" + url;
                }
                intent.putExtra("url", url);
                startActivity(intent);
            }
        });
        mBanner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);//设置圆形指示器与标题
        mBanner.setIndicatorGravity(BannerConfig.CENTER);//设置指示器位置
        mBanner.setDelayTime(2000);//设置轮播时间
        mBanner.setImages(images);//设置图片源
        mBanner.start();
    }

    private View getheaderView(){
        header=LayoutInflater.from(getContext()).inflate(R.layout.header_baike,null);
        TextView tv_type= (TextView) header.findViewById(R.id.tv_type);
        ImageView iv_add= (ImageView) header.findViewById(R.id.iv_add);
        LinearLayout menuLay1=header.findViewById(R.id.menuLay1);
        LinearLayout menuLay2=header.findViewById(R.id.menuLay2);
        LinearLayout menuLay3=header.findViewById(R.id.menuLay3);
        LinearLayout menuLay4=header.findViewById(R.id.menuLay4);
        menuLay1.setSelected(true);
        menuLay2.setSelected(false);
        menuLay3.setSelected(false);
        menuLay4.setSelected(false);
        iv_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(), BaikeTagActivity.class);
                intent.putExtra("type",type);
                intent.putExtra("pos",pos);
                startActivityForResult(intent,1);
            }
        });
        header.findViewById(R.id.menuLay1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                menuLay1.setSelected(true);
                menuLay2.setSelected(false);
                menuLay3.setSelected(false);
                menuLay4.setSelected(false);
                tv_type.setText("装饰百科");
                type=1;
                page=1;
                mBaikeTypeAdapter.setPos(0);
                mBaikeTypeAdapter.notifyDataSetChanged();
                mClassifyWikis1.clear();
                getType(type);
            }
        });
        header.findViewById(R.id.menuLay2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                menuLay1.setSelected(false);
                menuLay2.setSelected(true);
                menuLay3.setSelected(false);
                menuLay4.setSelected(false);
                tv_type.setText("养护百科");
                type=2;
                page=1;

                mBaikeTypeAdapter.setPos(0);
                mBaikeTypeAdapter.notifyDataSetChanged();
                mClassifyWikis1.clear();
                getType(type);
            }
        });
        header.findViewById(R.id.menuLay3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                menuLay1.setSelected(false);
                menuLay2.setSelected(false);
                menuLay3.setSelected(true);
                menuLay4.setSelected(false);
                tv_type.setText("购车指南");
                type=3;
                page=1;
                mBaikeTypeAdapter.setPos(0);
                mBaikeTypeAdapter.notifyDataSetChanged();
                mClassifyWikis1.clear();
                getType(type);
            }
        });
        header.findViewById(R.id.menuLay4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                menuLay1.setSelected(false);
                menuLay2.setSelected(false);
                menuLay3.setSelected(false);
                menuLay4.setSelected(true);
                tv_type.setText("交通百科");
                type=4;
                page=1;
                mBaikeTypeAdapter.setPos(0);
                mBaikeTypeAdapter.notifyDataSetChanged();
                mClassifyWikis1.clear();
                getType(type);

            }
        });
        mBanner= (com.youth.banner.Banner) header.findViewById(R.id.banner);
        lv_type= (HorizontalListView) header.findViewById(R.id.lv_type);
        mBaikeTypeAdapter=new BaikeTypeAdapter(getContext(),mClassifyWikis1);
        lv_type.setAdapter(mBaikeTypeAdapter);
        lv_type.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                 page=1;
                 pos=position;
                 mBaikeTypeAdapter.setPos(position);
                 mBaikeTypeAdapter.notifyDataSetChanged();
                 refresh(mClassifyWikis1.get(position).getId());
            }
        });
        getBaner();
        return  header;
    }

    private void getBaner() {
        Map<String, Object> params = new HashMap<>();
        params.put("type", 6);
        HttpProxy.obtain().get(PlatformContans.Commom.getBannerList, params, new ICallBack() {
            @Override
            public void OnSuccess(String result) {
                Log.e("data", result);
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    JSONArray data = jsonObject.getJSONArray("data");
                    for (int i = 0; i < data.length(); i++) {
                        JSONObject item = data.getJSONObject(i);
                        Banner banner = new Gson().fromJson(item.toString(), Banner.class);
                        mBanners.add(banner);
                        String url = item.getString("picture");
                        images.add(url);
                    }
                    initBanner();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(String error) {

            }
        });
    }

    private void getData(String id) {
        Map<String, Object> params = new HashMap<>();
        params.put("type", type);
        params.put("page", page);
        params.put("classifyId", id);
        HttpProxy.obtain().get(PlatformContans.WiKi.getBabyWikiByclassifyId, params, new ICallBack() {
            @Override
            public void OnSuccess(String result) {

                Log.e("getWikiClassifyByType", result);
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    jsonObject = jsonObject.getJSONObject("data");
                    JSONArray data = jsonObject.getJSONArray("beanList");
                    List<BaikeItem> baikeItems = new ArrayList<>();
                    for (int i = 0; i < data.length(); i++) {
                        JSONObject item = data.getJSONObject(i);
                        BaikeItem baikeItem = new Gson().fromJson(item.toString(), BaikeItem.class);
                        baikeItems.add(baikeItem);
                        mBaikeItems.add(baikeItem);
                    }
                    if(isLoadMore){
                        isLoadMore=false;
                        mBaikeItemAdapter.addData(baikeItems);
                        if(baikeItems.size()==0){
                            mBaikeItemAdapter.loadMoreEnd(true);
                        }else{
                            mBaikeItemAdapter.loadMoreComplete();
                        }
                    }else{
                        mBaikeItemAdapter.setNewData(mBaikeItems);
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
    private void getType(final int type) {
        Map<String, Object> params = new HashMap<>();
        params.put("type", type);
        HttpProxy.obtain().get(PlatformContans.WiKi.getWikiClassifyByType, params, new ICallBack() {
            @Override
            public void OnSuccess(String result) {
                Log.e("getWikiClassifyByType", result);
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    JSONArray data = jsonObject.getJSONArray("data");
                    for (int i = 0; i < data.length(); i++) {
                        JSONObject item = data.getJSONObject(i);
                        ClassifyWiki classifyWiki = new Gson().fromJson(item.toString(), ClassifyWiki.class);
                        mClassifyWikis1.add(classifyWiki);
                    }
                    mBaikeTypeAdapter.notifyDataSetChanged();
                    if(mClassifyWikis1.size()>0){
                         refresh(mClassifyWikis1.get(0).getId());
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
    private void refresh(String id){
        page=1;
        mBaikeItems.clear();
        mBaikeItemAdapter.setNewData(mBaikeItems);
        getData(id);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==1&&data!=null){
            page=1;
            pos=data.getExtras().getInt("pos",0);
            mBaikeTypeAdapter.setPos(pos);
            mBaikeTypeAdapter.notifyDataSetChanged();
            refresh(mClassifyWikis1.get(pos).getId());
        }
    }
}
