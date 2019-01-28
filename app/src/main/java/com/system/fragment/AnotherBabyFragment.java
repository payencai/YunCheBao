package com.system.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.application.MyApplication;
import com.example.yunchebao.R;
import com.newversion.NewCarFriendActivity;
import com.newversion.NewContactsActivity;
import com.newversion.NewSelfDrvingActivity;
import com.vipcenter.RegisterActivity;
import com.vipcenter.UserCenterActivity;

import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class AnotherBabyFragment extends Fragment {


    public AnotherBabyFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_another_baby, container, false);
        ButterKnife.bind(this,view);

        view.findViewById(R.id.ll_item1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 if(MyApplication.isLogin){
                     Intent intent=new Intent(getContext(), NewContactsActivity.class);
                     intent.putExtra("type",1);
                     startActivity(intent);
                 }else{
                     startActivity(new Intent(getContext(), RegisterActivity.class));
                 }
            }
        });
        view.findViewById(R.id.ll_item2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(MyApplication.isLogin){
                    Intent intent=new Intent(getContext(), NewContactsActivity.class);
                    intent.putExtra("type",2);
                    startActivity(intent);
                }else{
                    startActivity(new Intent(getContext(), RegisterActivity.class));
                }
            }
        });
        view.findViewById(R.id.ll_item3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        view.findViewById(R.id.ll_item4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(MyApplication.isLogin){
                    Intent intent=new Intent(getContext(), NewContactsActivity.class);
                    intent.putExtra("type",3);
                    startActivity(intent);
                }else{
                    startActivity(new Intent(getContext(), RegisterActivity.class));
                }
            }
        });
        view.findViewById(R.id.ll_item5).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        view.findViewById(R.id.ll_item6).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        view.findViewById(R.id.ll_item7).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), NewSelfDrvingActivity.class));
            }
        });
        view.findViewById(R.id.ll_item8).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), NewCarFriendActivity.class));
            }
        });

        view.findViewById(R.id.user_center_icon).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(MyApplication.isLogin){
                    startActivity(new Intent(getContext(), UserCenterActivity.class));
                }else{
                    startActivity(new Intent(getContext(), RegisterActivity.class));
                }
            }
        });
        view.findViewById(R.id.messenger_icon).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        return view;
    }

}
