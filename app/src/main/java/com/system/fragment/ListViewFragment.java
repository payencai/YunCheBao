package com.system.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;


import com.example.yunchebao.R;
import com.system.adapter.TestAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class ListViewFragment extends Fragment {

    @BindView(R.id.list)
    ListView list;
    TestAdapter mTestAdapter;
    List<String> mStringList;
    public ListViewFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_list_view, container, false);
        ButterKnife.bind(this,view);
        mStringList=new ArrayList<>();
        for (int i = 0; i <10 ; i++) {
            mStringList.add(""+i);
        }
        mTestAdapter=new TestAdapter(getContext(),mStringList);
        list.setAdapter(mTestAdapter);
        return view;
    }

}
