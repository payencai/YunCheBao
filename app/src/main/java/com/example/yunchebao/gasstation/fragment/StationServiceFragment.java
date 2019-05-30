package com.example.yunchebao.gasstation.fragment;


import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.example.yunchebao.MyApplication;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.costans.PlatformContans;
import com.example.yunchebao.R;
import com.example.yunchebao.gasstation.AddStationCommentActivity;
import com.example.yunchebao.gasstation.GasStationDetailActivity;
import com.example.yunchebao.gasstation.adapter.StationServiceAdapter;
import com.example.yunchebao.gasstation.model.StationService;
import com.google.gson.Gson;
import com.http.HttpProxy;
import com.http.ICallBack;
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

/**
 * A simple {@link Fragment} subclass.
 */
public class StationServiceFragment extends Fragment {


    @BindView(R.id.rv_order)
    RecyclerView rv_order;
    StationServiceAdapter mFourShopCommentAdapter;
    List<StationService> mFourShopComments;
    int page = 1;
    boolean isLoadMore = false;
    String id;
    GasStationDetailActivity mActivity;

    public StationServiceFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_station, container, false);
        ButterKnife.bind(this, view);
        mActivity = (GasStationDetailActivity) getActivity();
        id = mActivity.getId();
        initView();
        return view;
    }

    private void showFinishDialog(String id) {
        Dialog dialog = new Dialog(getContext(), R.style.dialog);
        View view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_order_finish, null);
        TextView tv_title = (TextView) view.findViewById(R.id.tv_title);
        TextView tv_content = (TextView) view.findViewById(R.id.tv_content);
        TextView tv_refused = (TextView) view.findViewById(R.id.tv_refused);
        TextView tv_comment = (TextView) view.findViewById(R.id.tv_comment);
        tv_title.setText("请与加油站当面交易");
        tv_content.setText("您有什么想对加油站说的吗？");
        tv_refused.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        tv_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                Intent intent = new Intent(getContext(), AddStationCommentActivity.class);
                intent.putExtra("id", id);
                startActivity(intent);
            }
        });
        dialog.setCanceledOnTouchOutside(false);
        dialog.setContentView(view);
        dialog.show();
        Window window = dialog.getWindow();
        WindowManager windowManager = getActivity().getWindowManager();
        WindowManager.LayoutParams layoutParams = window.getAttributes();
        Display display = windowManager.getDefaultDisplay();
        layoutParams.width = (int) (display.getWidth() * 0.7);
        layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        window.setAttributes(layoutParams);
    }

    private void initView() {
        mFourShopComments = new ArrayList<>();

        mFourShopCommentAdapter = new StationServiceAdapter(R.layout.item_goods_list, mFourShopComments);
        mFourShopCommentAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                page++;
                isLoadMore = true;
                getData();
            }
        }, rv_order);
        mFourShopCommentAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (MyApplication.isLogin) {
                    StationService stationService = (StationService) adapter.getItem(position);
                    showFinishDialog(stationService.getShopId());
                }else{
                    startActivity(new Intent(getContext(), RegisterActivity.class));
                }
            }
        });
        rv_order.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        rv_order.setLayoutManager(new LinearLayoutManager(getContext()));
        rv_order.setAdapter(mFourShopCommentAdapter);
        getData();
    }

    private void getData() {
        Map<String, Object> params = new HashMap<>();
        params.put("page", page);
        params.put("shopId", id);
        HttpProxy.obtain().get(PlatformContans.Station.getGasStationServeListForApp, params, new ICallBack() {
            @Override
            public void OnSuccess(String result) {
                Log.e("getDrivingSchool", result);
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    JSONArray data = jsonObject.getJSONArray("data");
                    List<StationService> replaceDrives = new ArrayList<>();
                    for (int i = 0; i < data.length(); i++) {
                        JSONObject item = data.getJSONObject(i);
                        StationService replaceDrive = new Gson().fromJson(item.toString(), StationService.class);
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
