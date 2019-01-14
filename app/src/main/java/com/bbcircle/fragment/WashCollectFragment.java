package com.bbcircle.fragment;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.application.MyApplication;
import com.bbcircle.CarShowDetailActivity;
import com.bbcircle.DriverFriendsDetailActivity;
import com.bbcircle.DrivingSelfDetailActivity;
import com.bbcircle.RaceDetailActivity;
import com.bbcircle.adapter.WashCollectAdapter;
import com.bbcircle.data.WashCollect;
import com.costans.PlatformContans;
import com.entity.PhoneArticleEntity;
import com.example.yunchebao.R;
import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.http.HttpProxy;
import com.http.ICallBack;
import com.nohttp.sample.BaseFragment;
import com.tool.ActivityAnimationUtils;
import com.tool.ActivityConstans;
import com.tool.listview.PersonalListView;
import com.vipcenter.adapter.ArticleListAdapter;
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

public class WashCollectFragment extends BaseFragment {
    @BindView(R.id.listview)
    PersonalListView listView;
    private Context ctx;
    List<WashCollect> list = new ArrayList<>();
    WashCollectAdapter adapter;
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

       // listView = refreshListView.getRefreshableView();
        listView.setDivider(getResources().getDrawable(R.color.gray_cc));
        listView.setDividerHeight(1);
        list = new ArrayList<>();
        adapter = new WashCollectAdapter(ctx, list);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                position--;
//                Bundle bundle=new Bundle();
//                bundle.putInt("id",list.get(position).get());
//                bundle.putInt("type",list.get(position).getType());
//                switch (list.get(position).getType()){
//                    case 1:
//                        ActivityAnimationUtils.commonTransition(getActivity(), DrivingSelfDetailActivity.class, ActivityConstans.Animation.FADE,bundle);
//                        break;
//                    case 2:
//                        ActivityAnimationUtils.commonTransition(getActivity(), DriverFriendsDetailActivity.class, ActivityConstans.Animation.FADE,bundle);
//                        break;
//                    case 3:
//                        ActivityAnimationUtils.commonTransition(getActivity(), CarShowDetailActivity.class, ActivityConstans.Animation.FADE,bundle);
//                        break;
//                    case 4:
//                        ActivityAnimationUtils.commonTransition(getActivity(), RaceDetailActivity.class, ActivityConstans.Animation.FADE,bundle);
//                        break;
//                }

            }
        });
        getData();

    }
    private void getData(){
        String token="";
        if(MyApplication.isLogin){
            token=MyApplication.getUserInfo().getToken();
        }
        Map<String,Object> params=new HashMap<>();
        params.put("page",page);
        HttpProxy.obtain().get(PlatformContans.Collect.getWashCollectionList, params,token,new ICallBack() {
            @Override
            public void OnSuccess(String result) {
                Log.e("washcollect", result);
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    JSONArray data = jsonObject.getJSONArray("data");
                    for (int i = 0; i < data.length(); i++) {
                        JSONObject item = data.getJSONObject(i);
                        WashCollect baikeItem = new Gson().fromJson(item.toString(), WashCollect.class);
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
