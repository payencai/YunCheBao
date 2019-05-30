package com.newversion;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;

import com.example.yunchebao.MyApplication;
import com.bbcircle.CarShowDetailActivity;
import com.bbcircle.CarsShowRepublishActivity;
import com.bbcircle.data.CarShow;
import com.bumptech.glide.Glide;
import com.costans.PlatformContans;
import com.example.yunchebao.R;
import com.google.gson.Gson;
import com.http.HttpProxy;
import com.http.ICallBack;
import com.payencai.library.view.CircleImageView;
import com.tool.ActivityAnimationUtils;
import com.tool.ActivityConstans;
import com.tool.listview.PersonalListView;
import com.tool.listview.PersonalScrollView;
import com.vipcenter.RegisterActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FriendCircleActivity extends AppCompatActivity {
    CarShowAdapter mCarShowAdapter;
    List<CarShow> mCarShows;
    int page=1;
    boolean isLoadMore=false;
    @BindView(R.id.lv_shop)
    PersonalListView lv_shop;
    @BindView(R.id.sv_shop)
    PersonalScrollView sv_shop;
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.tv_pub)
    ImageView tv_pub;
    @BindView(R.id.iv_head)
    CircleImageView iv_head;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_circle);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        mCarShows=new ArrayList<>();
        mCarShowAdapter=new CarShowAdapter(FriendCircleActivity.this,mCarShows);
        sv_shop.setOnScrollBottomListener(new PersonalScrollView.OnScrollBottomListener() {
            @Override
            public void onScrollBottom() {
                page++;
                isLoadMore=true;
                getData();
            }
        });
        if(MyApplication.isLogin){
            Glide.with(this).load(MyApplication.getUserInfo().getHeadPortrait()).into(iv_head);
        }
        lv_shop.setAdapter(mCarShowAdapter);
        lv_shop.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bundle bundle = new Bundle();
                bundle.putInt("id", mCarShows.get(position).getId());
                bundle.putInt("type", 3);
                if (MyApplication.isLogin) {
                    ActivityAnimationUtils.commonTransition(FriendCircleActivity.this, CarShowDetailActivity.class, ActivityConstans.Animation.FADE, bundle);
                } else {
                    startActivity(new Intent(FriendCircleActivity.this, RegisterActivity.class));
                }
            }
        });
        tv_pub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(FriendCircleActivity.this, CarsShowRepublishActivity.class));
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        getData();
    }

    public void getData() {
        Map<String, Object> params = new HashMap<>();
        params.put("page", page);
        HttpProxy.obtain().get(PlatformContans.BabyCircle.getCarShowCircleList, params, new ICallBack() {
            @Override
            public void OnSuccess(String result) {

                Log.e("getCarShowCircleList", result);
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    jsonObject = jsonObject.getJSONObject("data");
                    JSONArray data = jsonObject.getJSONArray("beanList");
                    for (int i = 0; i < data.length(); i++) {
                        JSONObject item = data.getJSONObject(i);
                        CarShow baikeItem = new Gson().fromJson(item.toString(), CarShow.class);
                        mCarShows.add(baikeItem);
                    }
                    mCarShowAdapter.notifyDataSetChanged();

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
