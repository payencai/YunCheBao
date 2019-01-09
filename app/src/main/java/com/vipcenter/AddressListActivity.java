package com.vipcenter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.entity.PhoneAddressEntity;
import com.example.yunchebao.R;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.nohttp.sample.NoHttpBaseActivity;
import com.tool.ActivityConstans;
import com.tool.UIControlUtils;
import com.vipcenter.adapter.AddressListAdapter;

import java.util.ArrayList;
import java.util.List;

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
    private List<PhoneAddressEntity> list;
    private Context ctx;

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
//                //数据是使用Intent返回
//                Intent intent = new Intent();
//                //把返回数据存入Intent
//                intent.putExtra("result", list.get(position-1));
//                //设置返回数据
//                AddressListActivity.this.setResult(RESULT_OK, intent);
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
    }

//    private void requestMethod(int type,String value) {
//        Request<String> request = null;
//        switch (type){
//            case 0:
//                request = NoHttp.createStringRequest(PlatformContans.rootUrl + PlatformContans.Urls.ShopUrls.getAllAddress , RequestMethod.GET);
//                request(0, request, httpListener, true, true);
//                break;
//            case 1:
//                request = NoHttp.createStringRequest(PlatformContans.rootUrl + PlatformContans.Urls.ShopUrls.changeDefault , RequestMethod.POST);
//                request.add("address_id",list.get(Integer.parseInt(value)).getId());
//                request(1, request, httpListener, true, true);
//                break;
//            case 2:
//                request = NoHttp.createStringRequest(PlatformContans.rootUrl + PlatformContans.Urls.ShopUrls.deleteAddress , RequestMethod.POST);
//                request.add("id",list.get(Integer.parseInt(value)).getId());
//                request(2, request, httpListener, true, true);
//                break;
//        }
//    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        switch (requestCode){
//            case 1:
//                if (resultCode == RESULT_OK){
//                    requestMethod(0,"");
//                }
//                break;
//        }
//    }

//    private HttpListener<String> httpListener = new HttpListener<String>() {
//        @Override
//        public void onSucceed(int what, Response response) {
//            pullToRefreshListView.onRefreshComplete();
//            String res = response.get().toString();
//            HttpJsonClient client = new HttpJsonClient();
//            client.setResp(res);
//            if (client.isSuccess()) {
//                switch (what) {
//                    case 0:
//                        List<PhoneAddressEntity> rList = client.getList(PhoneAddressEntity.class,"data");
//                        if (rList != null ){
//                            list.clear();
//                            list.addAll(rList);
//                            adapter.notifyDataSetChanged();
//                        }
//                        break;
//                    default:
//                        setToast(client.getMsg());
//                        requestMethod(0,"");
//                        break;
//                }
//            } else {
//                setToast(client.getMsg());
//            }
//        }
//
//        @Override
//        public void onFailed(int what, Response<String> response) {
//        }
//    };

    //adapter中按钮点击事件
    public void onShortcutMenuClickListener(Integer t, Integer loc) {
        int location = loc.intValue();
        switch (t) {
            case 0://修改
//                PlatformContans.OBJECT_MAP.put(PlatformContans.LoginContacts.ENTITY,list.get(loc));
//                startActivityForResult(new Intent(AddressListActivity.this,AddressAddActivity.class),1);
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
