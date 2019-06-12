package com.system;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.azhon.appupdate.config.UpdateConfiguration;
import com.azhon.appupdate.listener.OnButtonClickListener;
import com.azhon.appupdate.listener.OnDownloadListener;
import com.azhon.appupdate.manager.DownloadManager;
import com.example.yunchebao.MyApplication;
import com.costans.PlatformContans;
import com.entity.UserMsg;
import com.example.yunchebao.R;
import com.example.yunchebao.net.Api;
import com.example.yunchebao.net.NetUtils;
import com.example.yunchebao.net.OnMessageReceived;
import com.google.gson.Gson;
import com.gyf.immersionbar.ImmersionBar;
import com.http.HttpProxy;
import com.http.ICallBack;
import com.lzy.okgo.model.HttpParams;
import com.nohttp.sample.NoHttpFragmentBaseActivity;
import com.example.yunchebao.rongcloud.activity.AddGroupDetailActivity;
import com.example.yunchebao.rongcloud.activity.StrangerDelActivity;
import com.example.yunchebao.rongcloud.activity.contact.FriendDetailActivity;
import com.example.yunchebao.rongcloud.activity.contact.GroupDetailActivity;
import com.example.yunchebao.rongcloud.activity.contact.MyGroupDetailActivity;
import com.example.yunchebao.rongcloud.model.MyFriend;
import com.example.yunchebao.rongcloud.model.MyGroup;
import com.example.yunchebao.rongcloud.sidebar.ContactModel;
import com.system.fragment.AnotherBabyFragment;
import com.system.fragment.BaikeFragment;
import com.system.fragment.CheyiFragment;
import com.system.fragment.HomeFragment;
import com.system.fragment.MallFragment;
import com.system.model.Version;
import com.uuzuche.lib_zxing.activity.CodeUtils;
import com.vipcenter.model.UserInfo;
import com.yancy.gallerypick.utils.AppUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.litepal.LitePal;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.rong.callkit.util.SPUtils;
import io.rong.imkit.RongIM;
import io.rong.imkit.manager.IUnReadMessageObserver;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.Group;

public class MainActivity extends NoHttpFragmentBaseActivity implements View.OnClickListener {

    private static final String TAG = "MainActivity";
    HomeFragment fragment1;
    CheyiFragment fragment2;
    AnotherBabyFragment fragment3;
    BaikeFragment fragment4;
    MallFragment fragment5;

    boolean isFrist = true;
    private FragmentManager fm;
    private List<Fragment> fragments;

    View tab1, tab2, tab3, tab4, tab5;
    private ImageView iv_tab1;
    private ImageView iv_tab2;
    private ImageView iv_tab3;
    private ImageView iv_tab4;
    private ImageView iv_tab5;
    private TextView tv_tab1;
    private TextView tv_tab2;
    private TextView tv_tab3;
    private TextView tv_tab4;
    private TextView tv_tab5;
    DownloadManager manager;
    @BindView(R.id.tv_unread)
    TextView tv_unread;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //FitStateUI.setImmersionStateMode(this);
        setContentView(R.layout.activity_main);
        ImmersionBar.with(this).autoDarkModeEnable(true).fitsSystemWindows(true).statusBarColor(R.color.white).init();
        ButterKnife.bind(this);
        //openGPS(this);
        autoLogin();
        RongIM.setConnectionStatusListener(new RongIMClient.ConnectionStatusListener() {
            @Override
            public void onChanged(ConnectionStatus connectionStatus) {
                switch (connectionStatus){
                    case KICKED_OFFLINE_BY_OTHER_CLIENT:
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                MyApplication.isLogin=false;
                                MyApplication.token="";
                                MyApplication.setUserInfo(null);
                                SPUtils.put(MainActivity.this, "phone", "");
                                showNickDialog();
                            }
                        });

                        break;
                }
            }
        });
        getNewVersion();
    }


    private void startUpdate(Version version) {
        /*
         * 整个库允许配置的内容
         * 非必选
         */
        if(TextUtils.isEmpty(version.getDownUrl())||!version.getDownUrl().contains("apk")){
            return;
            //version.setRemarks("https://raw.githubusercontent.com/azhon/AppUpdate/master/apk/appupdate.apk");
        }
        UpdateConfiguration configuration = new UpdateConfiguration()
                //输出错误日志
                .setEnableLog(true)
                //设置自定义的下载
                //.setHttpManager()
                //下载完成自动跳动安装页面
                .setJumpInstallPage(true)
                //设置对话框背景图片 (图片规范参照demo中的示例图)
                //.setDialogImage(R.drawable.ic_dialog)
                //设置按钮的颜色
                //.setDialogButtonColor(Color.parseColor("#E743DA"))
                //设置按钮的文字颜色
                .setDialogButtonTextColor(Color.WHITE)
                //支持断点下载
                .setBreakpointDownload(true)
                //设置是否显示通知栏进度
                .setShowNotification(true)
                //设置是否提示后台下载toast
                .setShowBgdToast(false)
                //设置强制更新
                .setForcedUpgrade(false)
                //设置对话框按钮的点击监听
                .setButtonClickListener(new OnButtonClickListener() {
                    @Override
                    public void onButtonClick(int id) {
                        Log.e("TAG", String.valueOf(id));
                    }
                })
                //设置下载过程的监听
                .setOnDownloadListener(new OnDownloadListener() {
                    @Override
                    public void start() {

                    }

                    @Override
                    public void downloading(int max, int progress) {

                    }

                    @Override
                    public void done(File apk) {

                    }

                    @Override
                    public void cancel() {

                    }

                    @Override
                    public void error(Exception e) {

                    }
                });

        manager = DownloadManager.getInstance(this);
        manager.setApkName("miaoyizhai.apk")
                .setApkUrl(version.getDownUrl())
                .setSmallIcon(R.mipmap.icon)
                .setShowNewerToast(true)
                .setAuthorities("com.yunkang.miaoyizhai")
                .setConfiguration(configuration)
//                .setDownloadPath(Environment.getExternalStorageDirectory() + "/AppUpdate")
                .setApkVersionCode(2)
                .setApkVersionName(version.getVersion())
//                .setApkSize("11.2")
                .setApkDescription(version.getContent())
                .download();
    }
    Version version;
    public static int compareAppVersion(String newVersion, String oldVersion) {
        if (newVersion == null || oldVersion == null) {
            throw new RuntimeException("版本号不能为空");
        }
        // 注意此处为正则匹配，不能用.
        String[] versionArray1 = newVersion.split("\\.");
        String[] versionArray2 = oldVersion.split("\\.");
        int idx = 0;
        // 取数组最小长度值
        int minLength = Math.min(versionArray1.length, versionArray2.length);
        int diff = 0;
        // 先比较长度，再比较字符
        while (idx < minLength
                && (diff = versionArray1[idx].length() - versionArray2[idx].length()) == 0
                && (diff = versionArray1[idx].compareTo(versionArray2[idx])) == 0) {
            ++idx;
        }
        // 如果已经分出大小，则直接返回，如果未分出大小，则再比较位数，有子版本的为大
        diff = (diff != 0) ? diff : versionArray1.length - versionArray2.length;
        return diff;
    }
    private  void getNewVersion(){
//        Map<String,Object> params=new HashMap<>();
//        params.put("type","2");
//        HttpProxy.obtain().get(Api.Veision.getVersion, params, new ICallBack() {
//            @Override
//            public void OnSuccess(String result) {
//                Log.e("version",result);
//                try {
//                    JSONObject jsonObject=new JSONObject(result);
//                    jsonObject=jsonObject.getJSONObject("data");
//                    JSONArray data=jsonObject.getJSONArray("data");
//
//                    version.setParkey("1.0");
//                    for (int i = 0; i <data.length() ; i++) {
//                        if(i==0){
//                            JSONObject item=data.getJSONObject(i);
//                            version =new Gson().fromJson(item.toString(),Version.class);
//                            break;
//                        }
//                    }
//                    if(compareAppVersion(version.getParkey(), AppUtils.getAppVersionName())>0){
//                        startUpdate3(version);
//                    }
//
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//
//            }
//
//            @Override
//            public void onFailure(String error) {
//
//            }
//        });

        HttpParams httpParams=new HttpParams();
        httpParams.put("id",1);
        NetUtils.getInstance().get(MyApplication.token, Api.Veision.getVersion, httpParams, new OnMessageReceived() {
            @Override
            public void onSuccess(String response) {
                Log.e("version",response);
                try {
                    JSONObject jsonObject=new JSONObject(response);
                    JSONObject data=jsonObject.getJSONObject("data");

                    version=new Gson().fromJson(data.toString(),Version.class);
                    if(compareAppVersion(version.getVersion(), AppUtils.getVersionName(getApplicationContext()))>0){
                        startUpdate(version);
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



    private void showNickDialog() {
        final Dialog dialog = new Dialog(this, R.style.dialog);
        View dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_exit, null);
        //获得dialog的window窗口
        //将自定义布局加载到dialog上
        TextView tv_confirm= (TextView) dialogView.findViewById(R.id.tv_confirm);

        tv_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();

            }
        });
        dialog.setContentView(dialogView);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
        Window window = dialog.getWindow();
        WindowManager windowManager=getWindowManager();
        Display display=windowManager.getDefaultDisplay();
        //设置dialog在屏幕底部
        window.setGravity(Gravity.CENTER);
        //设置dialog弹出时的动画效果，从屏幕底部向上弹出
        //获得window窗口的属性
        android.view.WindowManager.LayoutParams lp = window.getAttributes();
        //设置窗口宽度为充满全屏
        lp.width = (int) (display.getWidth()*0.7);
        //设置窗口高度为包裹内容
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        //将设置好的属性set回去
        window.setAttributes(lp);
    }
    private void autoLogin() {
        String phone = (String) SPUtils.get(MainActivity.this, "phone", "");
        String pwd = (String) SPUtils.get(MainActivity.this, "pwd", "");
        if (!TextUtils.isEmpty(phone)) {
            initview();
            loginByPwd(phone, pwd);
        } else {
            initview();
        }
    }

    private void loginByPwd(String account, String pwd) {
        Map<String, Object> params = new HashMap<>();
        params.put("username", account);
        params.put("password", pwd);
        HttpProxy.obtain().post(PlatformContans.User.loginByPwd, params, new ICallBack() {
            @Override
            public void OnSuccess(String result) {
                Log.e("loginUser", result);
                try {
                    //Toast.makeText(RegisterActivity.this,"",Toast.LENGTH_LONG).show();
                    JSONObject object = new JSONObject(result);
                    JSONObject data = object.getJSONObject("data");
                    int code = object.getInt("resultCode");
                    if (code == 0) {
                        //Toast.makeText(MainActivity.this,"登录成功",Toast.LENGTH_SHORT).show();
                        UserInfo userInfo = new Gson().fromJson(data.toString(), UserInfo.class);
                        MyApplication.token = userInfo.getToken();
                        MyApplication.setUserInfo(userInfo);
                        MyApplication.setIsLogin(true);
                        MyApplication.isLogin=true;
                        getData();

                    } else {
                        initview();
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                    initview();
                }

            }

            @Override
            public void onFailure(String error) {

            }
        });
    }

    //关闭时解除监听器
    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();

    }

    private void initview() {

        tab1 = findViewById(R.id.main_fl_1);
        tab2 = findViewById(R.id.main_fl_2);
        tab3 = findViewById(R.id.main_fl_3);
        tab4 = findViewById(R.id.main_fl_4);
        tab5 = findViewById(R.id.main_fl_5);

        iv_tab1 = (ImageView) findViewById(R.id.main_iv_1);
        iv_tab2 = (ImageView) findViewById(R.id.main_iv_2);
        iv_tab3 = (ImageView) findViewById(R.id.main_iv_3);
        iv_tab4 = (ImageView) findViewById(R.id.main_iv_4);
        iv_tab5 = (ImageView) findViewById(R.id.main_iv_5);
        tv_tab1 = (TextView) findViewById(R.id.main_tv_1);
        tv_tab2 = (TextView) findViewById(R.id.main_tv_2);
        tv_tab3 = (TextView) findViewById(R.id.main_tv_3);
        tv_tab4 = (TextView) findViewById(R.id.main_tv_4);
        tv_tab5 = (TextView) findViewById(R.id.main_tv_5);

        fm = getSupportFragmentManager();
        fragment1 = new HomeFragment();
        fragment2 = new CheyiFragment();
        fragment3 = new AnotherBabyFragment();
        fragment4 = new BaikeFragment();
        fragment5 = new MallFragment();
        fragments = new ArrayList<>();
        fragments.add(fragment1);
        fragments.add(fragment2);
        fragments.add(fragment3);
        fragments.add(fragment4);
        fragments.add(fragment5);
        for (Fragment fragment : fragments) {
            fm.beginTransaction().add(R.id.main_frame, fragment).commit();
        }
        //显示主页
        resetStateForTagbar(R.id.main_fl_1);
        hideAllFragment();
        showFragment(0);
        initListener();
        RongIM.getInstance().addUnReadMessageCountChangedObserver(new IUnReadMessageObserver() {
            @Override
            public void onCountChanged(int i) {

                tv_unread.setText(i+"");
                if(i>0)
                    tv_unread.setVisibility(View.VISIBLE);
                else{
                    tv_unread.setVisibility(View.GONE);
                }
            }
        },  Conversation.ConversationType.PRIVATE, Conversation.ConversationType.GROUP);
    }

    private void initListener() {
        tab1.setOnClickListener(this);
        tab2.setOnClickListener(this);
        tab3.setOnClickListener(this);
        tab4.setOnClickListener(this);
        tab5.setOnClickListener(this);
    }


    private void resetStateForTagbar(int viewId) {
        clearTagbarState();
        if (viewId == R.id.main_fl_1) {
            tv_tab1.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary));
            iv_tab1.setImageResource(R.mipmap.ic_home_select);
            return;
        }
        if (viewId == R.id.main_fl_2) {
            tv_tab2.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary));
            iv_tab2.setImageResource(R.mipmap.ic_cheyi_select);
            return;
        }
        if (viewId == R.id.main_fl_3) {
            tv_tab3.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary));
            iv_tab3.setImageResource(R.mipmap.ic_circle_select);
            return;
        }
        if (viewId == R.id.main_fl_4) {
            tv_tab4.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary));
            iv_tab4.setImageResource(R.mipmap.ic_baike_select);
            return;
        }
        if (viewId == R.id.main_fl_5) {
            tv_tab5.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary));
            iv_tab5.setImageResource(R.mipmap.ic_shop_select);
            return;
        }
    }

    private void clearTagbarState() {
        tv_tab1.setTextColor(ContextCompat.getColor(this, R.color.black_33));
        iv_tab1.setImageResource(R.mipmap.ic_home_unselect);
        tv_tab2.setTextColor(ContextCompat.getColor(this, R.color.black_33));
        iv_tab2.setImageResource(R.mipmap.ic_cheyi_unselect);
        tv_tab3.setTextColor(ContextCompat.getColor(this, R.color.black_33));
        iv_tab3.setImageResource(R.mipmap.ic_circle_unselect);
        tv_tab4.setTextColor(ContextCompat.getColor(this, R.color.black_33));
        iv_tab4.setImageResource(R.mipmap.ic_baike_unselect);
        tv_tab5.setTextColor(ContextCompat.getColor(this, R.color.black_33));
        iv_tab5.setImageResource(R.mipmap.ic_shop_unselect);
    }

    private void hideAllFragment() {
        for (Fragment fragment : fragments) {
            fm.beginTransaction().hide(fragment).commitAllowingStateLoss();
        }
    }

    private void showFragment(int position) {
        fm.beginTransaction().show(fragments.get(position)).commitAllowingStateLoss();
    }

    boolean isEmpty = true;

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.main_fl_1:
                //状态重置
                resetStateForTagbar(R.id.main_fl_1);
                hideAllFragment();
                showFragment(0);
                break;

            case R.id.main_fl_2:
                //状态重置
                resetStateForTagbar(R.id.main_fl_2);
                hideAllFragment();
                showFragment(1);
                break;
            case R.id.main_fl_3:
                //状态重置
                resetStateForTagbar(R.id.main_fl_3);
                hideAllFragment();
                showFragment(2);
                if(fragment3!=null){
                    fragment3.getNewApplyCount();
                    fragment3.getNoticeCount();
                    fragment3.getNoticeImage();
                }
                break;
            case R.id.main_fl_4:
                //状态重置
                resetStateForTagbar(R.id.main_fl_4);
                hideAllFragment();
                showFragment(3);
                break;
            case R.id.main_fl_5:
                //状态重置
                resetStateForTagbar(R.id.main_fl_5);
                hideAllFragment();
                showFragment(4);
                //fragment5.onResume();
                break;

            default:
                break;
        }
    }

    private void getContacts() {
        LitePal.deleteAll(MyFriend.class);
        com.vipcenter.model.UserInfo userinfo = MyApplication.getUserInfo();
        if (userinfo != null)
            HttpProxy.obtain().get(PlatformContans.Chat.getMyFriendList, MyApplication.token, new ICallBack() {
                @Override
                public void OnSuccess(String result) {
                    Log.e("getContacts", result);
                    try {
                        JSONObject jsonObject = new JSONObject(result);
                        JSONArray data = jsonObject.getJSONArray("data");
                        List<MyFriend> myFriends = new ArrayList<>();
                        for (int i = 0; i < data.length(); i++) {
                            JSONObject item = data.getJSONObject(i);
                            MyFriend myFriend = new Gson().fromJson(item.toString(), MyFriend.class);
                            myFriends.add(myFriend);
                            if (!myFriend.isSaved())
                                myFriend.save();
                        }
                        // MyApplication.getDataSave().setDataList("friends", myFriends);
                        connect(MyApplication.getUserInfo().getHxPassword());


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(String error) {

                }
            });
    }

    private void getData() {


            HttpProxy.obtain().get(PlatformContans.Chat.getCrowdsList, MyApplication.token, new ICallBack() {
                @Override
                public void OnSuccess(String result) {
                    Log.e("apply", result);
                    try {
                        JSONObject jsonObject = new JSONObject(result);
                        JSONArray data = jsonObject.getJSONArray("data");
                        List<MyGroup> myGroups = new ArrayList<>();
                        for (int i = 0; i < data.length(); i++) {
                            JSONObject item = data.getJSONObject(i);
                            MyGroup myGroup = new Gson().fromJson(item.toString(), MyGroup.class);
                            myGroup.save();
                            myGroups.add(myGroup);
                        }
                        getContacts();
                        //MyApplication.getDataSave().setDataList("groups", myGroups);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(String error) {
                }
            });
    }

    private void connect(String token) {

        RongIM.connect(token, new RongIMClient.ConnectCallback() {
            /**
             * Token 错误。可以从下面两点检查 1.  Token 是否过期，如果过期您需要向 App Server 重新请求一个新的 Token
             *                  2.  token 对应的 appKey 和工程里设置的 appKey 是否一致
             */

            @Override
            public void onTokenIncorrect() {

            }

            /**
             * 连接融云成功
             * @param userid 当前 token 对应的用户 id
             */
            @Override
            public void onSuccess(String userid) {
                RongIM.getInstance().setCurrentUserInfo(new io.rong.imlib.model.UserInfo(MyApplication.getUserInfo().getId(), MyApplication.getUserInfo().getName(), Uri.parse(MyApplication.getUserInfo().getHeadPortrait())));
                RongIM.setGroupInfoProvider(new RongIM.GroupInfoProvider() {
                                                @Override
                                                public Group getGroupInfo(String s) {
                                                    List<MyGroup> myFriends = LitePal.findAll(MyGroup.class);
                                                    Log.e("data", s);
                                                    for (int i = 0; i < myFriends.size(); i++) {
                                                        MyGroup myFriend = myFriends.get(i);
                                                        String myid = myFriend.getHxCrowdId();
                                                        final String name = myFriend.getCrowdName();
                                                        final String head = myFriend.getImage();
                                                        if (s.equals(myid)) {
                                                            io.rong.imlib.model.Group userInfo = new io.rong.imlib.model.Group(s, name, Uri.parse(head));
                                                            RongIM.getInstance().refreshGroupInfoCache(userInfo);
                                                        }
                                                    }
                                                    return null;
                                                }
                                            }, true
                );
                RongIM.setUserInfoProvider(new RongIM.UserInfoProvider() {
                    @Override
                    public io.rong.imlib.model.UserInfo getUserInfo(String s) {
                        List<MyFriend> myFriends = LitePal.findAll(MyFriend.class);
                        Log.e("data", s);
                        for (int i = 0; i < myFriends.size(); i++) {
                            MyFriend myFriend = myFriends.get(i);
                            String myid = myFriend.getUserId();
                            final String name = myFriend.getName();
                            final String head = myFriend.getHeadPortrait();
                            if (s.equals(myid)) {
                                io.rong.imlib.model.UserInfo userInfo = new io.rong.imlib.model.UserInfo(s, name, Uri.parse(head));
                                RongIM.getInstance().refreshUserInfoCache(userInfo);
                            }
                        }
                        return null;
                    }
                }, true);





                //startActivity(new Intent(Re, ChatActivity.class));
                Log.d("LoginActivity", "--onSuccess" + userid);
            }

            /**
             * 连接融云失败
             * @param errorCode 错误码，可到官网 查看错误码对应的注释
             */
            @Override
            public void onError(RongIMClient.ErrorCode errorCode) {
                Log.d("LoginActivity", "--onSuccess" + errorCode.getMessage() + errorCode.getValue());
            }
        });
    }


    //获取群组成员90039d05-4b5e-4381-92a0-8346c6233afc
    private void getGroupData(String id) {
        Map<String, Object> params = new HashMap<>();
        params.put("crowdId", id);

        HttpProxy.obtain().get(PlatformContans.Chat.getCrowdDetailsByCrowdId, params, MyApplication.token, new ICallBack() {
            @Override
            public void OnSuccess(String result) {
                Log.e("getGroupData", result);
                try {
                    JSONObject Json = new JSONObject(result);
                    JSONObject data = Json.getJSONObject("data");
                    String name = data.getString("crowdName");
                    String crowId=data.getString("hxCrowdId");
                    String image = data.getString("image");
                    JSONArray indexList = data.getJSONArray("indexList");
                    String groupOwnerId = data.getString("crowdUserId");


                    if (groupOwnerId.equals(MyApplication.getUserInfo().getId())) {
                        Intent intent = new Intent(MainActivity.this, MyGroupDetailActivity.class);
                        intent.putExtra("id", crowId);
                        startActivity(intent);
                    } else {
                        boolean isIn = false;
                        for (int i = 0; i < indexList.length(); i++) {
                            JSONObject item = indexList.getJSONObject(i);
                            ContactModel contactModel = new Gson().fromJson(item.toString(),ContactModel.class);
                            Log.e("groupuser",contactModel.getUserId());
                            Log.e("Myuserid",MyApplication.getUserInfo().getId());
                            if (TextUtils.equals(contactModel.getUserId(),MyApplication.getUserInfo().getId())) {
                                isIn = true;
                                break;
                            }
                        }
                        if (isIn) {
                            Intent intent = new Intent(MainActivity.this, GroupDetailActivity.class);
                            intent.putExtra("id", crowId);
                            startActivity(intent);
                        } else {
                            Intent intent = new Intent(MainActivity.this, AddGroupDetailActivity.class);
                            com.example.yunchebao.rongcloud.model.Group group = new com.example.yunchebao.rongcloud.model.Group();
                            group.setCrowdName(name);
                            group.setHxCrowdId(id);
                            group.setImage(image);

                            intent.putExtra("group", group);
                            startActivity(intent);
                        }
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode,resultCode,data);
        //ToastUtil.showToast(this,"解析成功");
        if (requestCode == 1) {
            Toast.makeText(this, "GPS模块已开启", Toast.LENGTH_SHORT).show();
            fragment1.startLocate();
        }
        if (resultCode == 200 && data != null) {
            String result = data.getExtras().getString(CodeUtils.RESULT_STRING);
            if (!TextUtils.isEmpty(result)) {
                if (result.contains("云车宝群组:")) {
                    getGroupData(result.substring(6));
                } else if (result.contains("云车宝好友:")) {
                    getPrivateDetail(result.substring(6));
                }
            }
            //Log.e("result",result);
        }

    }

    private void getPrivateDetail(String id) {
        Map<String, Object> params = new HashMap<>();
        params.put("userId", id);
        HttpProxy.obtain().get(PlatformContans.User.getUserResultById, params, MyApplication.token, new ICallBack() {
            @Override
            public void OnSuccess(String result) {
                Log.e("detail", result);
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    JSONObject data = jsonObject.getJSONObject("data");
                    UserMsg userMsg = new Gson().fromJson(data.toString(), UserMsg.class);
                    Intent intent;
                    if ("1".equals(userMsg.getIsFriend())) {
                        intent = new Intent(MainActivity.this, FriendDetailActivity.class);
                        intent.putExtra("id", id);
                        startActivity(intent);
                    } else {
                        intent = new Intent(MainActivity.this, StrangerDelActivity.class);
                        intent.putExtra("id", id);
                        startActivity(intent);
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
