package com.system.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.yunchebao.R;
import com.flyco.tablayout.SlidingTabLayout;
import com.system.adapter.FragmentDreamAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class TestFragment extends Fragment {

    @BindView(R.id.tab_order)
    SlidingTabLayout tab_order;
    @BindView(R.id.viewPager)
    ViewPager viewPager;
    @BindView(R.id.tv_update)
    TextView tv_update;
    ArrayList<Fragment> mFragments;
    FragmentDreamAdapter mFragmentDreamAdapter;
    List<String> titles=new ArrayList<>();
    List<String> titles2=new ArrayList<>();
    String []mTitles1={"全部","待付款","待发货","待收货","待评价","退款/售后"};
    String []mTitles2={"gfhgh","hghj","hghghj","hghghg","hghh"};

    public TestFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_test, container, false);
        ButterKnife.bind(this,view);
        initView();
        return view;
    }

    private void initView() {
        mFragments=new ArrayList<>();
        for (int i = 0; i <6 ; i++) {
            mFragments.add(TestItemFragment.newInstance(1));
            titles.add("A"+i);
        }
        mFragmentDreamAdapter=new FragmentDreamAdapter(getContext(),getChildFragmentManager(),mFragments,titles);
        viewPager.setAdapter(mFragmentDreamAdapter);
        tab_order.setViewPager(viewPager);
        tv_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mFragments.clear();
                for (int i = 0; i <10 ; i++) {
                    mFragments.add(TestItemFragment.newInstance(2));
                    titles2.add("B"+i);
                }
                mFragmentDreamAdapter.setNewTitleFragment(mFragments,titles2);
                tab_order.notifyDataSetChanged();
                viewPager.setCurrentItem(0);
            }
        });
    }

}
