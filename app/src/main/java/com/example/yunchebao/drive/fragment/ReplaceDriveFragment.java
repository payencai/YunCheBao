package com.example.yunchebao.drive.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.yunchebao.MyApplication;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.costans.PlatformContans;
import com.example.yunchebao.drive.activity.ReplaceDriveDetailActivity;
import com.example.yunchebao.drive.adapter.ReplaceDriveAdapter;
import com.example.yunchebao.drive.model.ReplaceDrive;
import com.example.yunchebao.R;
import com.google.gson.Gson;
import com.http.HttpProxy;
import com.http.ICallBack;

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
public class ReplaceDriveFragment extends Fragment {
    ReplaceDriveAdapter mReplaceDriveAdapter;
    List<ReplaceDrive> mReplaceDrives;
    @BindView(R.id.rv_drive)
    RecyclerView rv_drive;
    int page = 1;
    int sort = 1;
    boolean isLoadMore = false;

    public ReplaceDriveFragment() {
        // Required empty public constructor
    }
    public static ReplaceDriveFragment newInstance(int sort){
        ReplaceDriveFragment replaceDriveFragment=new ReplaceDriveFragment();
        Bundle bundle=new Bundle();
        bundle.putInt("sort",sort);
        replaceDriveFragment.setArguments(bundle);
        return replaceDriveFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_replace_drive, container, false);
        ButterKnife.bind(this, view);
        sort=getArguments().getInt("sort");
        initView();
        return view;
    }

    private void initView() {
        mReplaceDrives = new ArrayList<>();
//        for (int i = 0; i <10 ; i++) {
//            mReplaceDrives.add(new ReplaceDrive());
//        }
        mReplaceDriveAdapter = new ReplaceDriveAdapter(R.layout.item_replace_drive, mReplaceDrives);
        mReplaceDriveAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                page++;
                isLoadMore=true;
                getData();
            }
        }, rv_drive);
        mReplaceDriveAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent=new Intent(getContext(), ReplaceDriveDetailActivity.class);
                ReplaceDrive replaceDrive= (ReplaceDrive) adapter.getItem(position);
                intent.putExtra("id",replaceDrive.getId());
                startActivity(intent);
            }
        });
       // mReplaceDriveAdapter.disableLoadMoreIfNotFullPage(rv_drive);
        rv_drive.setLayoutManager(new LinearLayoutManager(getContext()));
        rv_drive.setAdapter(mReplaceDriveAdapter);

        getData();
    }

    private void getData() {
        Map<String, Object> params = new HashMap<>();
        params.put("sort", sort);
        params.put("page", page);
        params.put("longitude", MyApplication.getaMapLocation().getLongitude() + "");
        params.put("latitude", MyApplication.getaMapLocation().getLatitude() + "");
        Log.e("params", params.toString());
        HttpProxy.obtain().get(PlatformContans.SubstituteDriving.getSubstituteDrivingShopListByApp, params, new ICallBack() {
            @Override
            public void OnSuccess(String result) {
                Log.e("getDrivingSchool", result);
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    JSONArray data = jsonObject.getJSONArray("data");
                    List<ReplaceDrive> replaceDrives=new ArrayList<>();
                    for (int i = 0; i < data.length(); i++) {
                        JSONObject item = data.getJSONObject(i);
                        ReplaceDrive replaceDrive = new Gson().fromJson(item.toString(), ReplaceDrive.class);
                        mReplaceDrives.add(replaceDrive);
                        replaceDrives.add(replaceDrive);
                    }
                    if (isLoadMore) {
                        isLoadMore = false;
                        if (data.length() == 0) {
                            mReplaceDriveAdapter.loadMoreEnd(true);
                        } else {
                            mReplaceDriveAdapter.addData(replaceDrives);
                            mReplaceDriveAdapter.loadMoreComplete();
                        }
                    } else {
                        mReplaceDriveAdapter.setNewData(mReplaceDrives);

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
