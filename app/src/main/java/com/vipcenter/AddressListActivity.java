package com.vipcenter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.yunchebao.MyApplication;
import com.costans.PlatformContans;
import com.example.yunchebao.R;
import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.http.HttpProxy;
import com.http.ICallBack;
import com.nohttp.sample.NoHttpBaseActivity;
import com.payencai.library.util.ToastUtil;
import com.tool.ActivityConstans;
import com.tool.UIControlUtils;
import com.vipcenter.adapter.AddressListAdapter;
import com.vipcenter.model.PersonAddress;

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
import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by sdhcjhss on 2017/11/16.
 */

public class AddressListActivity extends NoHttpBaseActivity {
    ListView mListView;
    @BindView(R.id.listview)
    PullToRefreshListView pullToRefreshListView;
    private AddressListActivity obj;
    private AddressListAdapter adapter;
    private List<PersonAddress> list;
    private Context ctx;
    int page=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listview_with_top_bottom);
        initView();
//        requestMethod(0,"");
    }

    private void initView() {
        UIControlUtils.UITextControlsUtils.setUIText(findViewById(R.id.title), ActivityConstans.UITag.TEXT_VIEW, "管理收货地址");
        ctx = this;
        findViewById(R.id.btn).setVisibility(View.VISIBLE);
        ButterKnife.bind(this);
        mListView = pullToRefreshListView.getRefreshableView();
        mListView.setDivider(getResources().getDrawable(R.color.gray_ee));
        mListView.setDividerHeight(10);
        obj = this;
        list = new ArrayList<>();
        adapter = new AddressListAdapter(this, list, obj);
        mListView.setAdapter(adapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                position--;
                PersonAddress personAddress=list.get(position);
                Intent intent=new Intent();
                intent.putExtra("address",personAddress);
                setResult(2,intent);
                AddressListActivity.this.finish();
            }
        });

        pullToRefreshListView.setMode(PullToRefreshBase.Mode.PULL_FROM_START);//支持下拉
        pullToRefreshListView.setScrollingWhileRefreshingEnabled(true);//滚动的时候不加载数据
        pullToRefreshListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
//                requestMethod(0,"");
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
            }
        });
        getData();
    }
    private void getData(){
        String token= "";
        if(MyApplication.isLogin){
            token=MyApplication.token;
        }
        Map<String,Object> params=new HashMap<>();
        params.put("page",page);
        HttpProxy.obtain().get(PlatformContans.AddressManage.getUserAddress,params, token,new ICallBack() {
            @Override
            public void OnSuccess(String result) {
                Log.e("getGoodList", result);
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    JSONArray data = jsonObject.getJSONArray("data");
                    for (int i = 0; i < data.length(); i++) {
                        JSONObject item = data.getJSONObject(i);
                        PersonAddress baikeItem = new Gson().fromJson(item.toString(), PersonAddress.class);
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
    //adapter中按钮点击事件
    public void onShortcutMenuClickListener(Integer t, Integer loc) {
        int location = loc.intValue();
        switch (t) {
            case 0://修改
               // PlatformContans.OBJECT_MAP.put(PlatformContans.LoginContacts.ENTITY,list.get(loc));
                Intent intent=new Intent(AddressListActivity.this,AddressAddActivity.class);
                intent.putExtra("data",list.get(loc));
                startActivityForResult(intent,1);
                break;
            case 1://删除
                deleteAlert(loc);
                break;
            case 2://设为默认收货地址
//                requestMethod(1,loc+"");
                break;
        }
    }

    public void deleteAlert(final Integer loc) {
        new SweetAlertDialog(ctx, SweetAlertDialog.NORMAL_TYPE)
                .setTitleText("确定删除吗？")
                .setConfirmText("确定")
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
//                        requestMethod(2,loc+"");
                        delete(loc);
                        sDialog.dismiss();
                    }
                })
                .setCancelText("取消")
                .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        sweetAlertDialog.dismiss();
                    }
                })
                .show();
    }
    private void delete(int pos){
        Map<String,Object> params=new HashMap<>();
        params.put("id",list.get(pos).getId());
        HttpProxy.obtain().post(PlatformContans.AddressManage.deleteUserAddress,MyApplication.token,params,new ICallBack() {
            @Override
            public void OnSuccess(String result) {
                Log.e("getGoodList", result);
                Log.e("result", result);
                ToastUtil.showToast(AddressListActivity.this, "删除成功");
                getData();
                finish();
            }

            @Override
            public void onFailure(String error) {

            }
        });
    }
    @OnClick({R.id.back, R.id.btn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                onBackPressed();
                break;
            case R.id.btn:
//                PlatformContans.OBJECT_MAP.put(PlatformContans.LoginContacts.ENTITY,null);
                startActivityForResult(new Intent(AddressListActivity.this, AddressAddActivity.class), 1);
                break;
        }
    }
}
