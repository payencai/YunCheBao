package com.system.fragment;


import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.application.MyApplication;
import com.example.yunchebao.R;
import com.newversion.FriendCircleActivity;
import com.newversion.FriendsCircleActivity;
import com.newversion.MyTagsActivity;
import com.newversion.NewCarFriendActivity;
import com.newversion.NewContactsActivity;
import com.newversion.NewSelfDrvingActivity;
import com.payencai.library.util.ToastUtil;
import com.rongcloud.activity.AddFriendActivity;
import com.rongcloud.activity.CreateGroupActivity;
import com.rongcloud.activity.stranger.SaomaActivity;
import com.rongcloud.activity.stranger.StrangerMsgActivity;
import com.system.View.MenuWindow;
import com.vipcenter.MyQrcodeActivity;
import com.vipcenter.RegisterActivity;
import com.vipcenter.UserCenterActivity;
import com.zyyoona7.popup.EasyPopup;
import com.zyyoona7.popup.XGravity;
import com.zyyoona7.popup.YGravity;

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
                if (MyApplication.isLogin)
                    startActivity(new Intent(getContext(), FriendsCircleActivity.class));
                else {
                    startActivity(new Intent(getContext(), RegisterActivity.class));
                }
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //ToastUtil.showToast(getContext(),"解析成功");
        Toast.makeText(getContext(), "---"+resultCode, Toast.LENGTH_SHORT).show();

    }

    private void initWindow(View view) {
        EasyPopup mCirclePop = EasyPopup.create()
                .setContentView(getContext(), R.layout.dialog_four_menu)
                //是否允许点击PopupWindow之外的地方消失
                .setFocusAndOutsideEnable(true)
                //允许背景变暗
                .setBackgroundDimEnable(true)
                //变暗的透明度(0-1)，0为完全透明
                .setDimValue(0.4f)

                //变暗的背景颜色
                .setDimColor(Color.BLACK)
                .apply();
        LinearLayout ll_friends = (LinearLayout) mCirclePop.findViewById(R.id.ll_friends);
        LinearLayout ll_group = (LinearLayout) mCirclePop.findViewById(R.id.ll_group);
        LinearLayout ll_shaoma = (LinearLayout) mCirclePop.findViewById(R.id.ll_shaoma);
        LinearLayout ll_qrcode = (LinearLayout) mCirclePop.findViewById(R.id.ll_qrcode);
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
                startActivityForResult(new Intent(getContext(), SaomaActivity.class), 5);
            }
        });
        ll_qrcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (MyApplication.isLogin) {
                    startActivity(new Intent(getContext(), MyQrcodeActivity.class));
                }
                 else {
                    startActivity(new Intent(getContext(), RegisterActivity.class));
                }
            }
        });
        mCirclePop.showAtAnchorView(view, YGravity.BELOW, XGravity.LEFT,0,0);

    }
}
