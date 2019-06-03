package com.system.fragment;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.yunchebao.MyApplication;
import com.costans.PlatformContans;
import com.example.yunchebao.R;
import com.example.yunchebao.friendcircle.NewFriendCircleActivity;
import com.example.yunchebao.net.Api;
import com.example.yunchebao.net.NetUtils;
import com.example.yunchebao.net.OnMessageReceived;
import com.example.yunchebao.rongcloud.model.ApplyFriend;
import com.google.gson.Gson;
import com.http.HttpProxy;
import com.http.ICallBack;
import com.newversion.MyTagsActivity;
import com.newversion.NewCarFriendActivity;
import com.newversion.NewContactsActivity;
import com.example.yunchebao.babycircle.selfdrive.NewSelfDrvingActivity;
import com.example.yunchebao.rongcloud.activity.AddFriendActivity;
import com.example.yunchebao.rongcloud.activity.CreateGroupActivity;
import com.example.yunchebao.rongcloud.activity.NearbyActivity;
import com.example.yunchebao.rongcloud.activity.stranger.SaomaActivity;
import com.example.yunchebao.rongcloud.activity.stranger.StrangerMsgActivity;
import com.order.NewPublish;
import com.payencai.library.view.CircleImageView;
import com.system.SearchActivity;
import com.vipcenter.MyQrcodeActivity;
import com.vipcenter.RegisterActivity;
import com.vipcenter.UserCenterActivity;
import com.zyyoona7.popup.EasyPopup;
import com.zyyoona7.popup.XGravity;
import com.zyyoona7.popup.YGravity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.rong.imkit.RongIM;
import io.rong.imkit.manager.IUnReadMessageObserver;
import io.rong.imlib.model.Conversation;

/**
 * A simple {@link Fragment} subclass.
 */
public class AnotherBabyFragment extends Fragment {

    @BindView(R.id.tv_unread)
    TextView tv_unread;
    @BindView(R.id.tv_newCount)
    TextView tv_newCount;
    @BindView(R.id.tv_notice)
    TextView tv_notice;
    @BindView(R.id.iv_img)
    CircleImageView  iv_img;
    @BindView(R.id.ll_circle)
    LinearLayout ll_circle;
    int count = 0;

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
                    startActivityForResult(new Intent(getActivity(), RegisterActivity.class), 6);
                }
            }
        });
        view.findViewById(R.id.search_lay).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), SearchActivity.class));
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
                    startActivityForResult(new Intent(getActivity(), RegisterActivity.class), 7);
                }
            }
        });
        view.findViewById(R.id.ll_item3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (MyApplication.isLogin)
                    startActivity(new Intent(getContext(), NewFriendCircleActivity.class));
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
                    startActivityForResult(new Intent(getActivity(), RegisterActivity.class), 8);
                }
            }
        });
        view.findViewById(R.id.ll_item5).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (MyApplication.isLogin)
                    startActivity(new Intent(getContext(), MyTagsActivity.class));
                else {
                    startActivityForResult(new Intent(getActivity(), RegisterActivity.class), 9);
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
        view.findViewById(R.id.ll_item9).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (MyApplication.isLogin)
                    startActivity(new Intent(getContext(), NearbyActivity.class));
                else {
                    startActivityForResult(new Intent(getActivity(), RegisterActivity.class), 11);
                }

            }
        });
        view.findViewById(R.id.rl_stranger).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (MyApplication.isLogin)
                    startActivity(new Intent(getContext(), StrangerMsgActivity.class));
                else {
                    startActivityForResult(new Intent(getActivity(), RegisterActivity.class), 10);
                }
            }
        });
        view.findViewById(R.id.user_center_icon).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (MyApplication.isLogin) {
                    startActivity(new Intent(getContext(), UserCenterActivity.class));
                } else {
                    startActivityForResult(new Intent(getActivity(), RegisterActivity.class), 11);
                }
            }
        });
        view.findViewById(R.id.messenger_icon).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (MyApplication.isLogin)
                    initWindow(v);
                else {
                    startActivity(new Intent(getContext(), RegisterActivity.class));
                }
            }
        });

        return view;
    }

    public void getNewApplyCount() {
        if(MyApplication.isIsLogin())
        HttpProxy.obtain().get(PlatformContans.Chat.getFriendApplyList, MyApplication.token, new ICallBack() {
            @Override
            public void OnSuccess(String result) {
                Log.e("apply", result);
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    JSONArray data = jsonObject.getJSONArray("data");
                    for (int i = 0; i < data.length(); i++) {
                        JSONObject item = data.getJSONObject(i);
                        ApplyFriend applyFriend = new Gson().fromJson(item.toString(), ApplyFriend.class);
                        if (applyFriend.getState() == 0) {
                            count++;
                        }
                    }
                    if (count > 0) {
                        tv_newCount.setText(count + "");
                        tv_newCount.setVisibility(View.VISIBLE);
                    } else {
                        tv_newCount.setVisibility(View.GONE);
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

    public void getNoticeCount(){
        if(MyApplication.isIsLogin())
        NetUtils.getInstance().get( MyApplication.token,Api.CommunicationCircle.getShowNoticeList, new OnMessageReceived() {
            @Override
            public void onSuccess(String response) {
                Log.e("getNoticeCount",response);

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int code = jsonObject.getInt("resultCode");
                    if (code == 0) {
                        JSONArray data = jsonObject.getJSONArray("data");
                        if(data!=null&&data.length()>0){
                            tv_notice.setText(data.length()+"");
                            tv_notice.setVisibility(View.VISIBLE);
                        }else{
                            tv_notice.setVisibility(View.GONE);
                        }


                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(String error) {

            }
        });
    }
    public void getNoticeImage(){
        if(MyApplication.isLogin)
        NetUtils.getInstance().get(MyApplication.token,Api.CommunicationCircle.getCommunicationImage,  new OnMessageReceived() {
            @Override
            public void onSuccess(String response) {
                Log.e("getCommunicationImage",response);

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int code = jsonObject.getInt("resultCode");
                    if (code == 0) {
                        String image = jsonObject.getString("data");
                        if(!TextUtils.isEmpty(image)){
                            Glide.with(getActivity()).load(image).into(iv_img);
                            ll_circle.setVisibility(View.VISIBLE);
                        }else{
                            ll_circle.setVisibility(View.GONE);
                        }


                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(String error) {

            }
        });
    }
    @Override
    public void onResume() {
        super.onResume();
        count = 0;
        RongIM.getInstance().addUnReadMessageCountChangedObserver(new IUnReadMessageObserver() {
            @Override
            public void onCountChanged(int i) {

                tv_unread.setText(i + "");
                if (i > 0)
                    tv_unread.setVisibility(View.VISIBLE);
                else {
                    tv_unread.setVisibility(View.GONE);
                }
            }
        }, Conversation.ConversationType.PRIVATE, Conversation.ConversationType.GROUP);
        getNewApplyCount();
        getNoticeCount();
        getNoticeImage();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //ToastUtil.showToast(getContext(),"解析成功");
        //Toast.makeText(getContext(), "---"+resultCode, Toast.LENGTH_SHORT).show();

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
                } else {
                    startActivity(new Intent(getContext(), RegisterActivity.class));
                }
            }
        });
        mCirclePop.showAtAnchorView(view, YGravity.BELOW, XGravity.LEFT, 0, 0);

    }
}
