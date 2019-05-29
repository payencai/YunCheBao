package com.example.yunchebao.washrepair;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.costans.PlatformContans;
import com.example.yunchebao.R;
import com.example.yunchebao.fourshop.activity.FourShopDetailActivity;
import com.example.yunchebao.fourshop.adapter.FourShopCommentAdapter;
import com.example.yunchebao.fourshop.bean.FourShopComment;
import com.example.yunchebao.fourshop.fragment.FourShopCommentFragment;
import com.google.gson.Gson;
import com.http.HttpProxy;
import com.http.ICallBack;
import com.luffy.imagepreviewlib.core.PictureConfig;

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
public class WashrepairCommentFragment extends Fragment {

    @BindView(R.id.rv_order)
    RecyclerView rv_order;
    WashrepairCommentAdapter mFourShopCommentAdapter;
    List<WashRepairComment> mFourShopComments;
    int page=1;
    boolean isLoadMore=false;
    String id;

    public WashrepairCommentFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_four_shop_comment, container, false);
        ButterKnife.bind(this,view);
        id=getArguments().getString("id");
        initView();
        return view;
    }
    public static WashrepairCommentFragment newInstance(String id){
        WashrepairCommentFragment fourShopCommentFragment=new WashrepairCommentFragment();
        Bundle bundle=new Bundle();
        bundle.putString("id",id);
        fourShopCommentFragment.setArguments(bundle);
        return fourShopCommentFragment;
    }
    private void initView() {
        mFourShopComments=new ArrayList<>();
        mFourShopCommentAdapter=new WashrepairCommentAdapter(R.layout.item_shop_comment,mFourShopComments);
        mFourShopCommentAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                page++;
                isLoadMore=true;
                getData();
            }
        },rv_order);
        mFourShopCommentAdapter.setOnImageClick(new FourShopCommentAdapter.OnImageClick() {
            @Override
            public void onClick(int pos, ArrayList<String> images) {
                PictureConfig config = new PictureConfig.Builder()
                        .setListData(images)  //图片数据List<String> list
                        .setPosition(pos)                         //图片下标（从第position张图片开始浏览）
                        .setDownloadPath("imagepreview")        //图片下载文件夹地址
                        .setIsShowNumber(true)                  //是否显示数字下标
                        .needDownload(true)                     //是否支持图片下载
                        .setPlaceHolder(R.mipmap.ic_launcher)   //占位符
                        .build();
                config.gotoActivity(getContext(), config);
            }
        });
        rv_order.setLayoutManager(new LinearLayoutManager(getContext()));
        rv_order.setAdapter(mFourShopCommentAdapter);
        getData();
    }

    private void getData(){
        Map<String, Object> params = new HashMap<>();
        params.put("page", page);
        params.put("shopId", id);
        HttpProxy.obtain().get(PlatformContans.CarWashRepairShop.getWashRepairCommentDetailsList, params, new ICallBack() {
            @Override
            public void OnSuccess(String result) {
                Log.e("getDrivingSchool", result);
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    JSONArray data = jsonObject.getJSONArray("data");
                    List<WashRepairComment> replaceDrives=new ArrayList<>();
                    for (int i = 0; i < data.length(); i++) {
                        JSONObject item = data.getJSONObject(i);
                        WashRepairComment replaceDrive = new Gson().fromJson(item.toString(), WashRepairComment.class);
                        mFourShopComments.add(replaceDrive);
                        replaceDrives.add(replaceDrive);
                    }
                    if (isLoadMore) {
                        isLoadMore = false;
                        if (data.length() == 0) {
                            mFourShopCommentAdapter.loadMoreEnd(true);
                        } else {
                            mFourShopCommentAdapter.addData(replaceDrives);
                            mFourShopCommentAdapter.loadMoreComplete();
                        }
                    } else {
                        mFourShopCommentAdapter.setNewData(mFourShopComments);

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


}
