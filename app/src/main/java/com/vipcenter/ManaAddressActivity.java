package com.vipcenter;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.yunchebao.MyApplication;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.costans.PlatformContans;
import com.example.yunchebao.R;
import com.google.gson.Gson;
import com.http.HttpProxy;
import com.http.ICallBack;
import com.payencai.library.util.ToastUtil;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.vipcenter.adapter.NewAddressAdapter;
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
import cn.pedant.SweetAlert.SweetAlertDialog;

public class ManaAddressActivity extends AppCompatActivity {
    @BindView(R.id.rv_order)
    RecyclerView rv_addr;
    @BindView(R.id.refresh)
    SmartRefreshLayout refresh;
    @BindView(R.id.tv_add)
    TextView tv_add;
    NewAddressAdapter mNewAddressAdapter;
    List<PersonAddress> mPersonAddresses;

    boolean isLoadMore=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mana_address);
        ButterKnife.bind(this);
        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        initView();
    }
     private void refresh(){

        mPersonAddresses.clear();
        mNewAddressAdapter.setNewData(mPersonAddresses);
        getData();
     }
    private void initView() {
        mPersonAddresses=new ArrayList<>();
        mNewAddressAdapter=new NewAddressAdapter(R.layout.item_address,mPersonAddresses);

        mNewAddressAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                  PersonAddress personAddress= (PersonAddress) adapter.getItem(position);
                  Intent intent=new Intent();
                  intent.putExtra("address",personAddress);
                  setResult(RESULT_OK,intent);
                  finish();
            }
        });
        mNewAddressAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                PersonAddress personAddress= (PersonAddress) adapter.getItem(position);
                  switch (view.getId()){
                      case R.id.ll_default:
                          if(personAddress.getIsDefault()==0)
                              update(personAddress,1);
                          else{
                              update(personAddress,0);
                          }
                          break;
                      case R.id.ll_delete:
                          deleteAlert(personAddress.getId());
                          break;
                      case R.id.ll_edit:
                          Intent intent=new Intent(ManaAddressActivity.this,AddressAddActivity.class);
                          intent.putExtra("data",personAddress);
                          startActivityForResult(intent,2);
                          break;
                  }
            }
        });
        refresh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                refresh();
                refreshLayout.finishRefresh(1000);
            }
        });
        rv_addr.setLayoutManager(new LinearLayoutManager(this));
        rv_addr.setAdapter(mNewAddressAdapter);
        tv_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(ManaAddressActivity.this, AddressAddActivity.class), 1);
            }
        });
        getData();
    }
    private void update(PersonAddress personAddress,int isdeault) {

        Map<String, Object> params = new HashMap<>();
        params.put("address", personAddress.getAddress());
        params.put("city", personAddress.getCity());
        params.put("id", personAddress.getId());
        params.put("district", personAddress.getDistrict());
        params.put("isDefault", isdeault);
        params.put("nickname", personAddress.getNickname());
        params.put("province", personAddress.getProvince());
        params.put("telephone", personAddress.getTelephone());
        String json = new Gson().toJson(params);
        Log.e("json",json);
        HttpProxy.obtain().post(PlatformContans.AddressManage.updateUserAddress, MyApplication.token, json, new ICallBack() {
            @Override
            public void OnSuccess(String result) {
                Log.e("json", result);
               /// ToastUtil.showToast(mA.this, "修改成功");
                refresh();
           }

            @Override
            public void onFailure(String error) {

            }
        });
    }
    public void deleteAlert(String id) {
        new SweetAlertDialog(this, SweetAlertDialog.NORMAL_TYPE)
                .setTitleText("确定删除吗？")
                .setConfirmText("确定")
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
//                        requestMethod(2,loc+"");
                        delete(id);
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
    private void delete(String id){
        Map<String,Object> params=new HashMap<>();
        params.put("id",id);
        HttpProxy.obtain().post(PlatformContans.AddressManage.deleteUserAddress,MyApplication.token,params,new ICallBack() {
            @Override
            public void OnSuccess(String result) {
                Log.e("result", result);
                ToastUtil.showToast(ManaAddressActivity.this, "删除成功");
                refresh();
            }

            @Override
            public void onFailure(String error) {

            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        refresh();
    }

    private void getData(){

        HttpProxy.obtain().get(PlatformContans.AddressManage.getUserAddress, MyApplication.token,new ICallBack() {
            @Override
            public void OnSuccess(String result) {
                Log.e("getGoodList", result);
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    JSONArray data = jsonObject.getJSONArray("data");

                    for (int i = 0; i < data.length(); i++) {
                        JSONObject item = data.getJSONObject(i);
                        PersonAddress personAddress = new Gson().fromJson(item.toString(), PersonAddress.class);
                        mPersonAddresses.add(personAddress);
                    }
                    mNewAddressAdapter.setNewData(mPersonAddresses);


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
