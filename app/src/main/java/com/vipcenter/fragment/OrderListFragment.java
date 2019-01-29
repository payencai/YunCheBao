package com.vipcenter.fragment;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ListView;

import com.application.MyApplication;
import com.costans.PlatformContans;
import com.entity.PhoneOrderEntity;
import com.example.yunchebao.R;
import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.http.HttpProxy;
import com.http.ICallBack;
import com.maket.GoodsOrderDetailActivity;
import com.nohttp.sample.BaseFragment;
import com.tool.ActivityAnimationUtils;
import com.tool.ActivityConstans;
import com.vipcenter.CheckLogisticsActivity;
import com.vipcenter.HaveGotGoodsActivity;
import com.vipcenter.OrderCommentSubmitActivity;
import com.vipcenter.OrderConfirmActivity;
import com.vipcenter.OrderReturnTypeActivity;
import com.vipcenter.adapter.OrderListAdapter;
import com.vipcenter.view.PayWayDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;


public class OrderListFragment extends BaseFragment implements OnClickListener {


    private final static String TAG = "OrderListFragment";
    ListView mListView;

    PullToRefreshListView pullToRefreshListView;
    private int typeId;//
    private OrderListAdapter adapter;
    private OrderListFragment obj;
    private View view;
    private List<PhoneOrderEntity> list = new ArrayList<>();


    private boolean isDown = true;//true 下拉动作  false 上拉操作
    private int pageNum = 1;//查询页


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        view = LayoutInflater.from(getActivity()).inflate(R.layout.listview_newonly, null);
        initView();
        return view;
    }

    private void initView() {
        pullToRefreshListView = (PullToRefreshListView) view.findViewById(R.id.listview);
        mListView = pullToRefreshListView.getRefreshableView();
        mListView.setDivider(getResources().getDrawable(R.color.gray_ee));
        mListView.setDividerHeight(1);
        obj = this;
        adapter = new OrderListAdapter(getActivity(), typeId, this, list);
        mListView.setAdapter(adapter);
        pullToRefreshListView.setMode(PullToRefreshBase.Mode.PULL_FROM_START);//支持下拉
        pullToRefreshListView.setScrollingWhileRefreshingEnabled(true);//滚动的时候不加载数据
        pullToRefreshListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                pullToRefreshListView.onRefreshComplete();
                isDown = true;
                pageNum = 1;
                initListData();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                isDown = false;
                pageNum++;
                initListData();
            }
        });


        list.clear();
        getData(getTypeId());

    }

    @Override
    public void onResume() {
        super.onResume();
        // initListData();
    }

    private void initListData() {
        List<PhoneOrderEntity> rlist = new ArrayList<>();
//        switch (typeId) {
//            case 0:
//                rlist.add(new PhoneOrderEntity(1, "1"));
//
//
//                break;
//            case 1:
//                rlist.add(new PhoneOrderEntity(1, "1"));
//
//                break;
//            case 2:
//                rlist.add(new PhoneOrderEntity(1, "2"));
//
//                break;
//            case 3:
//                rlist.add(new PhoneOrderEntity(1, "3"));
//
//                break;
//            case 4:
//                rlist.add(new PhoneOrderEntity(1, "4"));
//
//                break;
//        }
//
//        list.clear();
//        list.addAll(rlist);
        adapter.notifyDataSetChanged();

    }

    private void getData(int state) {
        String token = "";
        if (MyApplication.isLogin) {
            token = MyApplication.getUserInfo().getToken();
        } else {
            return;
            // tv.setText(MyApplication.getaMapLocation().getProvince() + MyApplication.getaMapLocation().getCity() + MyApplication.getaMapLocation().getDistrict());
        }
        Map<String, Object> params = new HashMap<>();
        params.put("page", pageNum);
        params.put("state", state);
        HttpProxy.obtain().get(PlatformContans.GoodsOrder.getMyOrderList, params, token, new ICallBack() {
            @Override
            public void OnSuccess(String result) {
                Log.e("getGoodList", result);
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    JSONArray data = jsonObject.getJSONArray("data");
                    for (int i = 0; i < data.length(); i++) {
                        JSONObject item = data.getJSONObject(i);
                        PhoneOrderEntity baikeItem = new Gson().fromJson(item.toString(), PhoneOrderEntity.class);
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


    /* 摧毁该Fragment，一般是FragmentActivity 被摧毁的时候伴随着摧毁 */
    @Override
    public void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();

    }


    //adapter中按钮点击事件
    public void onShortcutMenuClickListener(Integer t, Integer loc) {
        int location = loc.intValue();
        Intent intent;
        switch (t) {
            case 0://详情

                // ActivityAnimationUtils.commonTransition(getActivity(), OrderDetailActivity.class, ActivityConstans.Animation.FADE);
                break;
            case 1://联系
                //ActivityAnimationUtils.commonTransition(getActivity(), OrderChatDetailActivity.class, ActivityConstans.Animation.FADE);
                break;
            case 2://取消
                intent = new Intent(getContext(), GoodsOrderDetailActivity.class);
                intent.putExtra("data", list.get(location));
                startActivity(intent);
                //alertCancelPanel(getActivity());
                break;
            case 3://付款
                intent = new Intent(getContext(), GoodsOrderDetailActivity.class);
                intent.putExtra("data", list.get(location));
                startActivity(intent);
                //  initDialog();
                break;
            case 4://申请退货
                ActivityAnimationUtils.commonTransition(getActivity(), OrderReturnTypeActivity.class, ActivityConstans.Animation.FADE);
                break;
            case 5://提醒
                alert5Sweet();
                break;
            case 6://延长收货
                alert6Sweet();
                break;
            case 7://物流
                ActivityAnimationUtils.commonTransition(getActivity(), CheckLogisticsActivity.class, ActivityConstans.Animation.FADE);
                break;
            case 8://确认收货
                alert8Sweet();
                break;
            case 9://再来一单
                ActivityAnimationUtils.commonTransition(getActivity(), OrderConfirmActivity.class, ActivityConstans.Animation.FADE);
                break;
            case 10://评价
                ActivityAnimationUtils.commonTransition(getActivity(), OrderCommentSubmitActivity.class, ActivityConstans.Animation.FADE);
                break;
        }
    }



    /**
     * 初始化支付方式Dialog
     */
    private Dialog dialog;

    private void initDialog() {
        // 隐藏输入法
        dialog = new PayWayDialog(getActivity(), R.style.recharge_pay_dialog, true, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //确认充值
            }
        });
        dialog.show();
    }


    //提醒
    private void alert5Sweet() {
        new SweetAlertDialog(getActivity(), SweetAlertDialog.NORMAL_TYPE)
                .setTitleText("您好！客服已提醒商家尽快发货，请勿重复点击！")
                .setConfirmText("确认")
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        sweetAlertDialog.dismiss();
                    }
                })
                .show();
    }

    //延长收货
    private void alert6Sweet() {
        new SweetAlertDialog(getActivity(), SweetAlertDialog.NORMAL_TYPE)
                .setTitleText("确认延长收货时间？")
                .setContentText("每笔订单只能延迟一次哦")
                .setConfirmText("确定")
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        sweetAlertDialog.dismiss();
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

    //确认收货
    private void alert8Sweet() {
        new SweetAlertDialog(getActivity(), SweetAlertDialog.NORMAL_TYPE)
                .setTitleText("您是否收到该订单商品？")
                .setConfirmText("已收货")
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        sweetAlertDialog.dismiss();
                        ActivityAnimationUtils.commonTransition(getActivity(), HaveGotGoodsActivity.class, ActivityConstans.Animation.FADE);
                    }
                })
                .setCancelText("未收货")
                .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        sweetAlertDialog.dismiss();
                    }
                })
                .show();
    }


    public void setPosition(int position) {
        setTypeId(position);
//        adapter.setTypeId(position);

    }

    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    @Override
    public void onClick(View v) {


    }
}
