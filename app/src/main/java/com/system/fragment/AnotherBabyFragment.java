package com.system.fragment;


import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.application.MyApplication;
import com.example.yunchebao.R;
import com.newversion.FriendCircleActivity;
import com.newversion.MyTagsActivity;
import com.newversion.NewCarFriendActivity;
import com.newversion.NewContactsActivity;
import com.newversion.NewSelfDrvingActivity;
import com.rongcloud.activity.AddFriendActivity;
import com.rongcloud.activity.CreateGroupActivity;
import com.rongcloud.activity.stranger.SaomaActivity;
import com.rongcloud.activity.stranger.StrangerMsgActivity;
import com.system.View.MenuWindow;
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
        View view = inflater.inflate(R.layout.fragment_another_baby, container, false);
        ButterKnife.bind(this, view);

        view.findViewById(R.id.ll_item1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (MyApplication.isLogin) {
                    Intent intent = new Intent(getContext(), NewContactsActivity.class);
                    intent.putExtra("type", 1);
                    startActivity(intent);
                } else {
                    startActivityForResult(new Intent(getActivity(), RegisterActivity.class),6);
                }
            }
        });
        view.findViewById(R.id.ll_item2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (MyApplication.isLogin) {
                    Intent intent = new Intent(getContext(), NewContactsActivity.class);
                    intent.putExtra("type", 2);
                    startActivity(intent);
                } else {
                    startActivityForResult(new Intent(getActivity(), RegisterActivity.class),7);
                }
            }
        });
        view.findViewById(R.id.ll_item3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), FriendCircleActivity.class));

            }
        });
        view.findViewById(R.id.ll_item4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (MyApplication.isLogin) {
                    Intent intent = new Intent(getContext(), NewContactsActivity.class);
                    intent.putExtra("type", 3);
                    startActivity(intent);
                } else {
                    startActivityForResult(new Intent(getActivity(), RegisterActivity.class),8);
                }
            }
        });
        view.findViewById(R.id.ll_item5).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (MyApplication.isLogin)
                    startActivity(new Intent(getContext(), MyTagsActivity.class));
                else {
                    startActivityForResult(new Intent(getActivity(), RegisterActivity.class),9);
                }
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
        view.findViewById(R.id.rl_stranger).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (MyApplication.isLogin)
                    startActivity(new Intent(getContext(), StrangerMsgActivity.class));
                else {
                    startActivityForResult(new Intent(getActivity(), RegisterActivity.class),10);
                }
            }
        });
        view.findViewById(R.id.user_center_icon).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (MyApplication.isLogin) {
                    startActivity(new Intent(getContext(), UserCenterActivity.class));
                } else {
                    startActivityForResult(new Intent(getActivity(), RegisterActivity.class),11);
                }
            }
        });
        view.findViewById(R.id.messenger_icon).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initWindow(v);
            }
        });
        return view;
    }

    private void initWindow(View view) {
        MenuWindow menuWindow=new MenuWindow(getContext());
        View v = menuWindow.getContentView();
        LinearLayout ll_friends = (LinearLayout) v.findViewById(R.id.ll_friends);
        LinearLayout ll_group = (LinearLayout) v.findViewById(R.id.ll_group);
        LinearLayout ll_shaoma = (LinearLayout) v.findViewById(R.id.ll_shaoma);
        LinearLayout ll_qrcode = (LinearLayout) v.findViewById(R.id.ll_qrcode);
        ll_friends.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (MyApplication.isLogin)
                    startActivity(new Intent(getContext(), AddFriendActivity.class));
                else {
                    startActivity(new Intent(getContext(), RegisterActivity.class));
                }
            }
        });
        ll_group.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (MyApplication.isLogin)
                    startActivity(new Intent(getContext(), CreateGroupActivity.class));
                else {
                    startActivity(new Intent(getContext(), RegisterActivity.class));
                }
            }
        });
        ll_shaoma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(getContext(), SaomaActivity.class), 1);
            }
        });
        ll_qrcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (MyApplication.isLogin) {
                    //startActivity(new Intent(getContext(), CreateGroupActivity.class));}
                } else {
                    startActivity(new Intent(getContext(), RegisterActivity.class));
                }
            }
        });
        menuWindow.setBlurBackgroundEnable(true);
        menuWindow.showPopupWindow(view);


    }
}
