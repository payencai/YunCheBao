package com.cheyibao.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.cheyibao.DrivingSchoolActivity;
import com.cheyibao.adapter.DrivingSchoolListAdapter;
import com.entity.PhoneShopEntity;
import com.example.yunchebao.R;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.nohttp.sample.BaseFragment;
import com.tool.ActivityAnimationUtils;
import com.tool.ActivityConstans;

import java.util.ArrayList;
import java.util.List;


public class StudyListFragment extends BaseFragment implements OnClickListener{


    private final static String TAG = "StudyListFragment";
    ListView mListView;

    PullToRefreshListView pullToRefreshListView;
    private int typeId;//
    private DrivingSchoolListAdapter adapter;
    private StudyListFragment obj;
    private View view;
    private List<PhoneShopEntity> list = new ArrayList<>();


    private boolean isDown = true;//true 下拉动作  false 上拉操作
    private int pageNum = 1;//查询页


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        view = LayoutInflater.from(getActivity()).inflate(R.layout.listview_replace, null);
        initView();
        return view;
    }

    private void initView() {
        pullToRefreshListView = (PullToRefreshListView)view.findViewById(R.id.listview);
        mListView = pullToRefreshListView.getRefreshableView();
        mListView.setDivider(getResources().getDrawable(R.color.gray_ee));
        mListView.setDividerHeight(1);
        obj = this;
        adapter = new DrivingSchoolListAdapter(getActivity(),list);
        mListView.setAdapter(adapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ActivityAnimationUtils.commonTransition(getActivity(), DrivingSchoolActivity.class, ActivityConstans.Animation.FADE);
            }
        });
        pullToRefreshListView.setMode(PullToRefreshBase.Mode.PULL_FROM_START);//支持下拉
        pullToRefreshListView.setScrollingWhileRefreshingEnabled(true);//滚动的时候不加载数据
        pullToRefreshListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                pullToRefreshListView.onRefreshComplete();
                isDown = true;
                pageNum = 1;

            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                isDown = false;
                pageNum++;

            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();

    }

    /* 摧毁该Fragment，一般是FragmentActivity 被摧毁的时候伴随着摧毁 */
    @Override
    public void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();

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
