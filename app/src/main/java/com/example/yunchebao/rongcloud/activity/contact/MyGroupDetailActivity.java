package com.example.yunchebao.rongcloud.activity.contact;

import android.Manifest;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yunchebao.MyApplication;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.costans.PlatformContans;
import com.example.yunchebao.R;
import com.google.gson.Gson;
import com.gyf.immersionbar.ImmersionBar;
import com.http.HttpProxy;
import com.http.ICallBack;
import com.payencai.library.util.ToastUtil;
import com.payencai.library.view.CircleImageView;
import com.example.yunchebao.rongcloud.activity.GroupQrcodeActivity;
import com.example.yunchebao.rongcloud.adapter.GridAdapter;
import com.example.yunchebao.rongcloud.model.GroupUser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.rong.imkit.RongIM;
import io.rong.imkit.model.GroupUserInfo;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Conversation;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MyGroupDetailActivity extends AppCompatActivity {
    //收缩时显示的行数
    private static final int SHOWED_LINES = 2;
    //GridView的列数
    private static final int NUM_COLUMNS = 5;

    private List<GroupUser> mAllGroupUser;
    private List<GroupUser> mShowGroupUser;
    private List<GroupUser> m2GroupUser=new ArrayList<>();
    //是否收缩标志，默认收缩
    private boolean mIsShrink = true;

    private GridAdapter mAdapter;


    @BindView(R.id.iv_icon)
    CircleImageView iv_icon;
    @BindView(R.id.chatname)
    TextView chatname;
    @BindView(R.id.account)
    TextView crow;
    @BindView(R.id.nickname)
    TextView nickname;
    @BindView(R.id.rl_group)
    RelativeLayout rl_group;
    @BindView(R.id.crowuser)
    GridView mGridView;
    @BindView(R.id.tv_show)
    TextView mToggle;
    @BindView(R.id.sendmsg)
    TextView sendmsg;
    @BindView(R.id.people)
    TextView people;
    @BindView(R.id.rl_add)
    RelativeLayout rl_top;
    @BindView(R.id.iv_menu)
    ImageView iv_menu;
    @BindView(R.id.iv_msg)
    ImageView iv_msg;
    String groupId;
    String name;
    int crowId;
    int indexId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        groupId= (String) getIntent().getStringExtra("id");
        setContentView(R.layout.activity_my_group_detail);
        ButterKnife.bind(this);
        ImmersionBar.with(this).autoDarkModeEnable(true).fitsSystemWindows(true).statusBarColor(R.color.white).init();
        initView();
        getDetail();
    }
    String cloudId;

    File tempFile;
    Uri photoOutputUri;
    Uri photoUri;
    String image;
    public void openCamera() {
        //獲取系統版本
        int currentapiVersion = android.os.Build.VERSION.SDK_INT;
        // 激活相机
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // 判断存储卡是否可以用，可用进行存储
        if (hasSdcard()) {
            SimpleDateFormat timeStampFormat = new SimpleDateFormat(
                    "yyyy_MM_dd_HH_mm_ss");
            String filename = timeStampFormat.format(new Date());
            tempFile = new File(Environment.getExternalStorageDirectory(),
                    filename + ".jpg");
            if (currentapiVersion < 24) {
                // 从文件中创建uri
                photoUri = Uri.fromFile(tempFile);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
            } else {
                //兼容android7.0 使用共享文件的形式
                ContentValues contentValues = new ContentValues(1);
                contentValues.put(MediaStore.Images.Media.DATA, tempFile.getAbsolutePath());
                //检查是否有存储权限，以免崩溃
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                    //申请WRITE_EXTERNAL_STORAGE权限
                    Toast.makeText(this, "请开启存储权限", Toast.LENGTH_SHORT).show();
                    return;
                }
                photoUri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
            }
        }
        // 开启一个带有返回值的Activity，请求码为PHOTO_REQUEST_CAREMA
        startActivityForResult(intent, 2);
    }
    private void cropPhoto(Uri inputUri) {
        // 调用系统裁剪图片的 Action
        Intent cropPhotoIntent = new Intent("com.android.camera.action.CROP");
        // 设置数据Uri 和类型
        cropPhotoIntent.setDataAndType(inputUri, "image/*");
        // 授权应用读取 Uri，这一步要有，不然裁剪程序会崩溃
        cropPhotoIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        // 设置图片的最终输出目录
        cropPhotoIntent.putExtra(MediaStore.EXTRA_OUTPUT,
                photoOutputUri = Uri.parse("file:////sdcard/image_output.jpg"));
        startActivityForResult(cropPhotoIntent, 3);
    }

    public void upImage(String url, File file) {
        OkHttpClient mOkHttpClent = new OkHttpClient();

        MultipartBody.Builder builder = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("image", "image",
                        RequestBody.create(MediaType.parse("image/png"), file));
        RequestBody requestBody = builder.build();
        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();
        Call call = mOkHttpClent.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("upload", "onResponse: " + e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String string = response.body().string();
                Log.e("upload", "onResponse: " + string);
                try {
                    JSONObject object = new JSONObject(string);
                    int resultCode = object.getInt("resultCode");
                    final String data = object.getString("data");
                    image = data;
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            // Glide.with(DriverFriendsRepublishActivity.this).load(data).into(iv_img);
                            Glide.with(MyGroupDetailActivity.this).load(image)
                                    .apply(RequestOptions.bitmapTransform(new RoundedCorners(20)))
                                    .into(iv_icon);
                            updateHead();
                        }
                    });


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && data != null) {
            cropPhoto(data.getData());
        }
        if (requestCode == 2 && data != null) {
            cropPhoto(data.getData());
        }
        if (requestCode == 3 && data != null) {

            File file = new File(photoOutputUri.getPath());
            if (file.exists()) {
                upImage(PlatformContans.Commom.uploadImg, file);
            } else {
                Toast.makeText(this, "找不到照片", Toast.LENGTH_SHORT).show();
            }

        }
        if(requestCode==188||requestCode==189){
            mShowGroupUser.clear();
            mAllGroupUser.clear();
            m2GroupUser.clear();
            getDetail();
        }


    }
    private void updateHead() {
        Map<String, Object> params = new HashMap<>();
        params.put("image", image);
        params.put("id", crowId);
        HttpProxy.obtain().post(PlatformContans.Chat.updateCrowds, MyApplication.token, params, new ICallBack() {
            @Override
            public void OnSuccess(String result) {
                Log.e("resutl", result);
                ToastUtil.showToast(MyGroupDetailActivity.this,"修改成功");
            }

            @Override
            public void onFailure(String error) {

            }
        });
    }
    /*
     * 判断sdcard是否被挂载
     */
    public static boolean hasSdcard() {
        return Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED);
    }
    private void showDialog() {
        final Dialog dialog = new Dialog(this, R.style.dialog);
        View dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_select_photo, null);
        //获得dialog的window窗口
        Window window = dialog.getWindow();
        //设置dialog在屏幕底部
        window.setGravity(Gravity.BOTTOM);
        //设置dialog弹出时的动画效果，从屏幕底部向上弹出
        window.setWindowAnimations(R.style.mypopwindow_anim_style);
        window.getDecorView().setPadding(0, 0, 0, 0);
        //获得window窗口的属性
        android.view.WindowManager.LayoutParams lp = window.getAttributes();
        //设置窗口宽度为充满全屏
        lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
        //设置窗口高度为包裹内容
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        //将设置好的属性set回去
        window.setAttributes(lp);
        //将自定义布局加载到dialog上
        dialog.setContentView(dialogView);
        dialog.findViewById(R.id.tv_select_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.findViewById(R.id.tv_select_camera).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                openCamera();
            }
        });
        dialog.findViewById(R.id.tv_select_gallery).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                Intent mIntent = new Intent(Intent.ACTION_GET_CONTENT);
                mIntent.addCategory(Intent.CATEGORY_OPENABLE);
                mIntent.setType("image/*");
                startActivityForResult(mIntent, 1);
            }
        });
        dialog.show();
    }
    private void updateMyNick(String name){
        Map<String,Object> params=new HashMap<>();
        params.put("id",indexId);
        params.put("nickName",name);
        HttpProxy.obtain().post(PlatformContans.Chat.updateMyCrowdData, MyApplication.token, params, new ICallBack() {
            @Override
            public void OnSuccess(String result) {
                Log.e("result",result);
                ToastUtil.showToast(MyGroupDetailActivity.this,"修改成功");
                 mShowGroupUser.clear();
                 mAllGroupUser.clear();
                 m2GroupUser.clear();
                 getDetail();
            }

            @Override
            public void onFailure(String error) {

            }
        });
    }
    private void showCrowNickDialog() {
        final Dialog dialog = new Dialog(this, R.style.dialog);
        View dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_group_nick, null);
        //获得dialog的window窗口
        //将自定义布局加载到dialog上


        TextView tv_confirm= (TextView) dialogView.findViewById(R.id.tv_confirm);
        EditText et_nick= (EditText) dialogView.findViewById(R.id.et_nick);
        TextView tv_cancel= (TextView) dialogView.findViewById(R.id.tv_cancel);

        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        tv_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                String name=et_nick.getEditableText().toString();
                if(!TextUtils.isEmpty(name)){
                    chatname.setText(name);
                    updateCrowNick(name);
                }
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
    private void showNickDialog() {
        final Dialog dialog = new Dialog(this, R.style.dialog);
        View dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_nick, null);
        //获得dialog的window窗口
        //将自定义布局加载到dialog上
        TextView tv_confirm= (TextView) dialogView.findViewById(R.id.tv_confirm);
        EditText et_nick= (EditText) dialogView.findViewById(R.id.et_nick);
        TextView tv_cancel= (TextView) dialogView.findViewById(R.id.tv_cancel);
        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        tv_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                String name=et_nick.getEditableText().toString();
                if(!TextUtils.isEmpty(name)){
                    RongIM.getInstance().refreshGroupUserInfoCache(new GroupUserInfo(cloudId,MyApplication.getUserInfo().getId(),name));
                    nickname.setText(name);
                    updateMyNick(name);
                }
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
    private void getDetail(){
        Map<String,Object> params=new HashMap<>();
        params.put("hxCrowdId",groupId);

        HttpProxy.obtain().get(PlatformContans.Chat.getCrowdDetailsByHxCrowdId, params,MyApplication.token, new ICallBack() {
            @Override
            public void OnSuccess(String result) {
                Log.e("data",result);
                try {
                    JSONObject Json=new JSONObject(result);
                    JSONObject data=Json.getJSONObject("data");
                    name=data.getString("crowdName");
                    crowId=data.getInt("id");
                    cloudId=data.getString("hxCrowdId");
                    JSONArray indexList = data.getJSONArray("indexList");
                    JSONObject object=data.getJSONObject("indexUser");
                    GroupUser groupUser = new Gson().fromJson(object.toString(), GroupUser.class);
                    chatname.setText(name);
                    indexId=groupUser.getId();
                    crow.setText(data.getString("id"));
                    Glide.with(MyGroupDetailActivity.this).load(data.getString("image")).into(iv_icon);
                    nickname.setText(groupUser.getNickName());
                    people.setText(indexList.length()+"人");
                    for (int i = 0; i <indexList.length(); i++) {
                        JSONObject item = indexList.getJSONObject(i);
                        GroupUser applyFriend = new Gson().fromJson(item.toString(), GroupUser.class);
                        mAllGroupUser.add(applyFriend);
                        if(i<8){
                            mShowGroupUser.add(applyFriend);
                        }
                    }
                    GroupUser add=new GroupUser();
                    add.setFlag(1);
                    GroupUser delete=new GroupUser();
                    delete.setFlag(2);
                    mShowGroupUser.add(add);
                    mShowGroupUser.add(delete);
                    mAllGroupUser.add(add);
                    mAllGroupUser.add(delete);
                    mAdapter.notifyDataSetChanged();
                    setGridViewHeightBasedOnChildren(mGridView);
                    getStatus();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(String error) {

            }
        });
    }
    private void deleteGroup(){
        if (true) {
            final com.vipcenter.model.UserInfo userInfo = MyApplication.getUserInfo();
            Map<String, Object> params = new HashMap<>();
            params.put("crowdId",crowId);
            if (userInfo != null)
                HttpProxy.obtain().post(PlatformContans.Chat.dismissCrowdByCrowdId, MyApplication.token, params, new ICallBack() {
                    @Override
                    public void OnSuccess(String result) {
                        Log.e("delete", result);
                        try {
                            JSONObject jsonObject = new JSONObject(result);
                            int code = jsonObject.getInt("resultCode");
                            if (code == 0) {
                                Toast.makeText(MyGroupDetailActivity.this, "解散成功", Toast.LENGTH_SHORT).show();
                                finish();
                            }else{
                                //Toast.makeText(GroupManageActivity.this, msg, Toast.LENGTH_SHORT).show();
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
    private void updateCrowNick(String name){
        Map<String,Object> params=new HashMap<>();
        params.put("id",crowId);
        params.put("crowdName",name);
        HttpProxy.obtain().post(PlatformContans.Chat.updateCrowds, MyApplication.token, params, new ICallBack() {
            @Override
            public void OnSuccess(String result) {
                Log.e("result",result);
                ToastUtil.showToast(MyGroupDetailActivity.this,"修改成功");

            }

            @Override
            public void onFailure(String error) {

            }
        });
    }
    private void initWindow(View view) {
        PopupWindow popupWindow = new PopupWindow(this);
        popupWindow.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        View v = LayoutInflater.from(this).inflate(R.layout.dialog_group, null);

        LinearLayout ll_shaoma = (LinearLayout) v.findViewById(R.id.ll_shaoma);

        ll_shaoma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // initWindow(rl_top);
                deleteGroup();
            }
        });

        popupWindow.setContentView(v);
        popupWindow.setBackgroundDrawable(new ColorDrawable(0x00000000));
        popupWindow.setOutsideTouchable(false);
        popupWindow.setFocusable(true);
        popupWindow.showAsDropDown(view);


    }
    Conversation.ConversationNotificationStatus conversationNotificationStatus1;
    private void getStatus(){
        RongIM.getInstance().getConversationNotificationStatus(Conversation.ConversationType.GROUP, groupId, new RongIMClient.ResultCallback<Conversation.ConversationNotificationStatus>() {
            @Override
            public void onSuccess(Conversation.ConversationNotificationStatus conversationNotificationStatus) {
                final int value = conversationNotificationStatus.getValue();
                //ToastUtil.showToast(MyGroupDetailActivity.this,value+"");
                if (value == 1) {
                    iv_msg.setImageResource(R.mipmap.white_switch);
                    conversationNotificationStatus1 = conversationNotificationStatus.setValue(0);

                } else {
                    iv_msg.setImageResource(R.mipmap.blue_switch);
                    conversationNotificationStatus1 = conversationNotificationStatus.setValue(1);
                }

            }

            @Override
            public void onError(RongIMClient.ErrorCode errorCode) {
                //ToastUtil.showToast(MyGroupDetailActivity.this, errorCode.getMessage() + "");
            }
        });
    }
    private void initView(){
        rl_group.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCrowNickDialog();
            }
        });
        sendmsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RongIM.getInstance().startGroupChat(MyGroupDetailActivity.this, cloudId, name);
            }
        });
        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        findViewById(R.id.rl_code).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MyGroupDetailActivity.this, GroupQrcodeActivity.class);
                intent.putExtra("id",crowId+"");
                startActivity(intent);
            }
        });
        rl_top.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initWindow(v);
            }
        });
        findViewById(R.id.rl_nick).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showNickDialog();
            }
        });
        findViewById(R.id.tv_clear).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RongIM.getInstance().clearMessages(Conversation.ConversationType.GROUP,groupId , new RongIMClient.ResultCallback<Boolean>() {
                    @Override
                    public void onSuccess(Boolean aBoolean) {
                        ToastUtil.showToast(MyGroupDetailActivity.this,aBoolean+"清除成功");
                    }

                    @Override
                    public void onError(RongIMClient.ErrorCode errorCode) {

                    }
                });
            }
        });
        iv_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
            }
        });
        mAllGroupUser = new ArrayList();
        mShowGroupUser = new ArrayList();
        mAdapter=new GridAdapter(this,mShowGroupUser);
        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                 GroupUser groupUser=mShowGroupUser.get(position);
                 if(groupUser.getFlag()==1){
                     Intent intent=new Intent(MyGroupDetailActivity.this,GroupManageActivity.class);
                     //intent.putExtra("user",mGroup);
                     intent.putExtra("flag",groupUser.getFlag());
                     intent.putExtra("id",crowId+"");
                     startActivityForResult(intent,188);
                 }else if(groupUser.getFlag()==2){
                     Intent intent=new Intent(MyGroupDetailActivity.this,GroupManageActivity.class);
                     intent.putExtra("id",crowId+"");
                    // intent.putExtra("user",mGroup);
                     intent.putExtra("flag",groupUser.getFlag());
                     startActivityForResult(intent,189);
                 }else{
                     Intent intent=new Intent(MyGroupDetailActivity.this,FriendDetailActivity.class);
                     intent.putExtra("id",groupUser.getUserId());
                     startActivity(intent);
                 }
            }
        });
        //绑定Adapter
        mGridView.setAdapter(mAdapter);

        mToggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mIsShrink) {
                    expand();
                } else {
                    collapse();
                }
                setGridViewHeightBasedOnChildren(mGridView);
                //每次点击都要调用
                // setGridViewHeightBasedOnChildren(mGridView);
            }
        });

        iv_msg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                RongIM.getInstance().setConversationNotificationStatus(Conversation.ConversationType.GROUP, groupId, conversationNotificationStatus1, new RongIMClient.ResultCallback<Conversation.ConversationNotificationStatus>() {
                    @Override
                    public void onSuccess(Conversation.ConversationNotificationStatus conversationNotificationStatus) {
                        getStatus();
                    }

                    @Override
                    public void onError(RongIMClient.ErrorCode errorCode) {

                    }
                });
            }

        });
        //第一次调用
        //setListViewHeightBasedOnChildren(mGridView);

    }
    public  void setGridViewHeightBasedOnChildren(GridView gridView) {
        // 获取GridView对应的Adapter
        ListAdapter listAdapter = gridView.getAdapter();
        if (listAdapter == null) {
            return;
        }
        int rows;
        int columns=0;
        int horizontalBorderHeight=0;
        int verticalBorderHeight =0;
        Class<?> clazz=gridView.getClass();
        try {
            //利用反射，取得每行显示的个数
            Field column=clazz.getDeclaredField("mRequestedNumColumns");
            column.setAccessible(true);
            columns=(Integer)column.get(gridView);
            //利用反射，取得横向分割线高度
            Field horizontalSpacing=clazz.getDeclaredField("mRequestedHorizontalSpacing");
            horizontalSpacing.setAccessible(true);
            horizontalBorderHeight=(Integer)horizontalSpacing.get(gridView);
            //利用反射，取得属竖向分割线高度
//            Field verticalSpacing =clazz.getDeclaredField("mRequestedVerticalSpacing");
//            verticalSpacing.setAccessible(true);
//            verticalBorderHeight=(Integer)verticalSpacing.get(gridView);
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        //判断数据总数除以每行个数是否整除。不能整除代表有多余，需要加一行
        if(listAdapter.getCount()%columns>0){
            rows=listAdapter.getCount()/columns+1;
        }else {
            rows=listAdapter.getCount()/columns;
        }
        int totalHeight = 0;
        for (int i = 0; i < rows; i++) { //只计算每项高度*行数
            View listItem = listAdapter.getView(i, null, gridView);
            listItem.measure(0, 0); // 计算子项View 的宽高
            totalHeight += listItem.getMeasuredHeight(); // 统计所有子项的总高度
        }
        ViewGroup.LayoutParams params = gridView.getLayoutParams();
        params.height = totalHeight+horizontalBorderHeight*(rows-1);//最后加上分割线总高度
        gridView.setLayoutParams(params);
    }


    //展开
    private void expand() {
        m2GroupUser.addAll(mShowGroupUser);
        mShowGroupUser.clear();
        mShowGroupUser.addAll(mAllGroupUser);
        mAdapter.notifyDataSetChanged();
        mToggle.setText("收起");
        mIsShrink = false;
    }

    //收缩
    private void collapse() {
        mShowGroupUser.clear();
        mShowGroupUser.addAll(m2GroupUser);
        m2GroupUser.clear();
        mAdapter.notifyDataSetChanged();
        mToggle.setText("全部展开");
        mIsShrink = true;
    }



}