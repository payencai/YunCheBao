package com.example.yunchebao.drive.fragment;


import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.yunchebao.MyApplication;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.costans.PlatformContans;
import com.example.yunchebao.drive.activity.PayDetailActivity;
import com.example.yunchebao.drive.adapter.DriveOrderAdapter;
import com.example.yunchebao.drive.model.ReplaceOrder;
import com.example.yunchebao.R;
import com.google.gson.Gson;
import com.http.HttpProxy;
import com.http.ICallBack;
import com.example.yunchebao.maket.GoodsPayActivity;
import com.payencai.library.util.ToastUtil;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

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
public class DriveOrderFragment extends Fragment {

   @BindView(R.id.rv_order)
    RecyclerView rv_content;
   @BindView(R.id.refresh)
    SmartRefreshLayout refresh;
    List<ReplaceOrder> mYueOrders;
    DriveOrderAdapter mYuedanAdapter;
    int page=1;
    int state=1;
    public DriveOrderFragment() {
        // Required empty public constructor
    }
    public static DriveOrderFragment newInstance(int state){
        DriveOrderFragment orderItemFragment=new DriveOrderFragment();
        Bundle bundle=new Bundle();
        bundle.putInt("state",state);
        orderItemFragment.setArguments(bundle);
        return  orderItemFragment;
    }
    public void refresh(){
        page=1;
        mYueOrders.clear();
        mYuedanAdapter.setNewData(mYueOrders);
        getData();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragmentView
        View view=inflater.inflate(R.layout.fragment_replace_order, container, false);
        ButterKnife.bind(this,view);
        state=getArguments().getInt("state");
        initView();
        return view;
    }

    private void initView() {
        mYueOrders=new ArrayList<>();
        mYuedanAdapter=new DriveOrderAdapter(R.layout.item_yuedan_order,mYueOrders);
        mYuedanAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                ReplaceOrder yueOrder= (ReplaceOrder) adapter.getItem(position);
                if(view.getId()==R.id.fukuan){
                    Intent intent=new Intent(getActivity(), PayDetailActivity.class);
                    intent.putExtra("data",yueOrder);
                    startActivityForResult(intent,1);

                    //showPay(yueOrder.getId(),yueOrder.getShopName());
                }else if(view.getId()==R.id.delete){
                    deleteOrder(yueOrder.getId());
                    //startActivity(new Intent(getContext(),AddOrderCommentActivity.class));
                }
            }
        });
        refresh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                page=1;
                mYueOrders.clear();
                mYuedanAdapter.setNewData(mYueOrders);
                getData();
            }
        });
        rv_content.setLayoutManager(new LinearLayoutManager(getContext()));
        rv_content.setAdapter(mYuedanAdapter);
        getData();
    }
    private void deleteOrder(String id){
        Map<String,Object> params=new HashMap<>();
        params.put("id",id);
        HttpProxy.obtain().post(PlatformContans.SubstituteDriving.deleteSubstituteDrivingOrderByUser, MyApplication.token, params, new ICallBack() {
            @Override
            public void OnSuccess(String result) {
                Log.e("reuslt",result);
                ToastUtil.showToast(getContext(),"操作成功");
                mYueOrders.clear();
                page=1;
                getData();
               // finish();
            }

            @Override
            public void onFailure(String error) {

            }
        });
    }
    private void payForOrder(String id,String money){
        Map<String,Object> params=new HashMap<>();
        params.put("id",id);
        HttpProxy.obtain().post(PlatformContans.Pay.appointmentPay,MyApplication.token, params, new ICallBack() {
            @Override
            public void OnSuccess(String result) {
                Log.e("reuslt",result);
                ToastUtil.showToast(getContext(),"操作成功");
                mYueOrders.clear();
                page=1;
                getData();
                // finish();
            }

            @Override
            public void onFailure(String error) {

            }
        });
    }
    private void showPay(String id ,String name) {
        View view = getActivity().getLayoutInflater().inflate(R.layout.dialog_pay_order, null);
        ImageView iv_del= (ImageView) view.findViewById(R.id.iv_del);
        TextView tv_confirm= (TextView) view.findViewById(R.id.tv_confirm);
        TextView tv_name= (TextView) view.findViewById(R.id.tv_name);
        EditText et_money= (EditText) view.findViewById(R.id.et_money);
        final Dialog dialog = new Dialog(getContext(), R.style.MyDialog);
        iv_del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        tv_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String money=et_money.getEditableText().toString();
                if(!TextUtils.isEmpty(money)){
                    Intent intent=new Intent(dialog.getContext(), GoodsPayActivity.class);
                    intent.putExtra("orderid",id);
                    intent.putExtra("money",money);
                    intent.putExtra("flag","3");
                    startActivity(intent);
                    dialog.dismiss();
                }

            }
        });
        tv_name.setText(name);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable());
        dialog.setContentView(view);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
        Window window = dialog.getWindow();
        WindowManager windowManager=window.getWindowManager();
        Display display=windowManager.getDefaultDisplay();
        window.setGravity(Gravity.CENTER);
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = (int) (display.getWidth()*0.8);
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(params);

    }
    private void getData() {
        String token = "";
        if (MyApplication.isLogin) {
            token = MyApplication.token;
        } else {
            return;
        }
        Map<String, Object> params = new HashMap<>();
        params.put("page", page);
        params.put("state", state);
        HttpProxy.obtain().get(PlatformContans.SubstituteDriving.getSubstituteDrivingOrderListByUser, params, token, new ICallBack() {
            @Override
            public void OnSuccess(String result) {
                refresh.finishRefresh();
                Log.e("getGoodList", result);
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    JSONArray data = jsonObject.getJSONArray("data");
                    for (int i = 0; i < data.length(); i++) {
                        JSONObject item = data.getJSONObject(i);
                        ReplaceOrder yueOrder = new Gson().fromJson(item.toString(), ReplaceOrder.class);
                        mYueOrders.add(yueOrder);
                    }
                    mYuedanAdapter.notifyDataSetChanged();
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
