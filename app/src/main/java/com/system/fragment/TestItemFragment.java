package com.system.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.baike.fragment.BaikeItemFragment;
import com.example.yunchebao.R;

import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class TestItemFragment extends Fragment {

    int type=0;
    public TestItemFragment() {
        // Required empty public constructor
    }

    public static TestItemFragment newInstance(int type) {
        TestItemFragment testItemFragment = new TestItemFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("type", type);
        testItemFragment.setArguments(bundle);
        return testItemFragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_test_item, container, false);
        ButterKnife.bind(this,view);

        type=getArguments().getInt("type");
        ( (TextView)view.findViewById(R.id.tv_name)).setText(type+"");
        return view;
    }

}
