package com.vipcenter.fragment;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ScrollView;

import com.application.MyApplication;
import com.bbcircle.CarShowDetailActivity;
import com.bbcircle.DriverFriendsDetailActivity;
import com.bbcircle.DrivingSelfDetailActivity;
import com.bbcircle.RaceDetailActivity;
import com.bbcircle.data.CarshowDetail;
import com.bbcircle.data.SelfDrive;
import com.costans.PlatformContans;
import com.entity.PhoneArticleEntity;
import com.entity.PhoneShopEntity;
import com.example.yunchebao.R;
import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.http.HttpProxy;
import com.http.ICallBack;
import com.nohttp.sample.BaseFragment;
import com.tool.ActivityAnimationUtils;
import com.tool.ActivityConstans;
import com.tool.listview.PersonalListView;
import com.tool.view.ListViewForScrollView;
import com.vipcenter.adapter.ArticleListAdapter;
import com.xihubao.WashCarDetailActivity;
import com.xihubao.adapter.WashCarListAdapter;

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
 * Created by sdhcjhss on 2018/1/6.
 */

public class ArticleFragment extends BaseFragment {
    @BindView(R.id.listview)
    PersonalListView listView;
    private Context ctx;
    List<PhoneArticleEntity> list = new ArrayList<>();
    ArticleListAdapter adapter;
    int page=1;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.personal_list, container, false);
        ButterKnife.bind(this, rootView);
        init();
        return rootView;
    }

    private void init() {
        ctx = getActivity();

      //  listView = refreshListView.getRefreshableView();
        listView.setDivider(getResources().getDrawable(R.color.gray_cc));
        listView.setDividerHeight(1);
        list = new ArrayList<>();
        adapter = new ArticleListAdapter(ctx, list);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               // position--;
                Bundle bundle=new Bundle();
                bundle.putInt("id",list.get(position).getCircleId());
                bundle.putInt("type",list.get(position).getType());
                switch (list.get(position).getType()){
                    case 1:
                        ActivityAnimationUtils.commonTransition(getActivity(), DrivingSelfDetailActivity.class, ActivityConstans.Animation.FADE,bundle);
                        break;
                    case 2:
                        ActivityAnimationUtils.commonTransition(getActivity(), DriverFriendsDetailActivity.class, ActivityConstans.Animation.FADE,bundle);
                        break;
                    case 3:
                        ActivityAnimationUtils.commonTransition(getActivity(), CarShowDetailActivity.class, ActivityConstans.Animation.FADE,bundle);
                        break;
                    case 4:
                        ActivityAnimationUtils.commonTransition(getActivity(), RaceDetailActivity.class, ActivityConstans.Animation.FADE,bundle);
                        break;
                }

            }
        });
        getData();

    }
    private void getData(){
        String token="";
        if(MyApplication.isLogin){
            token=MyApplication.token;
        }
        Map<String,Object> params=new HashMap<>();
        params.put("page",page);
        HttpProxy.obtain().get(PlatformContans.Collect.getBabyCollection, params, token,new ICallBack() {
            @Override
            public void OnSuccess(String result) {
                Log.e("getdata", result);
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    jsonObject=jsonObject.getJSONObject("data");
                    JSONArray data = jsonObject.getJSONArray("beanList");
                    for (int i = 0; i < data.length(); i++) {
                        JSONObject item = data.getJSONObject(i);
                        PhoneArticleEntity baikeItem = new Gson().fromJson(item.toString(), PhoneArticleEntity.class);
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

}
