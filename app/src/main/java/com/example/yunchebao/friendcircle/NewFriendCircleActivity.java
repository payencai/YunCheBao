package com.example.yunchebao.friendcircle;

import android.Manifest;
import android.app.ActionBar;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.application.MyApplication;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.costans.PlatformContans;
import com.entity.UserMsg;
import com.example.yunchebao.R;
import com.example.yunchebao.friendcircle.adapter.CircleDataAdapter;
import com.google.gson.Gson;
import com.gyf.immersionbar.ImmersionBar;
import com.http.HttpProxy;
import com.http.ICallBack;
import com.newversion.CircleData;
import com.newversion.DynamicPublishActivity;
import com.newversion.FriendsCircleActivity;
import com.newversion.RecordVideoActivity;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.tool.GlideImageEngine;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.functions.Consumer;

public class NewFriendCircleActivity extends AppCompatActivity {

    @BindView(R.id.rv_circle)
    RecyclerView rv_circle;
    @BindView(R.id.iv_publish)
    ImageView iv_publish;
    @BindView(R.id.tv_nickname)
    TextView tv_nickname;
    @BindView(R.id.iv_headpic)
    ImageView iv_headpic;
    @BindView(R.id.bg_img)
    ImageView friend_background;
    CircleDataAdapter mCircleDataAdapter;
    List<CircleData> mCircleData;
    private int friendsCircleType = 0;

    int page = 1;
    boolean isLoadMore;
    String firstItemId;
    String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_friend_circle);
        ButterKnife.bind(this);
        ImmersionBar.with(this).autoDarkModeEnable(true).init();
        userId = getIntent().getStringExtra("userId");
        initView();
    }
    @OnClick({R.id.iv_back,R.id.iv_publish,R.id.iv_headpic})
    void OnClick(View view){
        switch (view.getId()){
            case R.id.iv_back:
                finish();
                break;
            case R.id.iv_publish:
                showPublishTypeDialog();
                break;
            case R.id.iv_headpic:
                if (MyApplication.isLogin) {
                    // 跳转到自己或者别人的朋友圈
                    if (TextUtils.isEmpty(userId)) {
                        userId = MyApplication.getUserInfo().getId();
                    }
                    Intent intent = new Intent(NewFriendCircleActivity.this, NewFriendCircleActivity.class);
                    intent.putExtra("userId", userId);
                    NewFriendCircleActivity.this.startActivity(intent);
                }
                break;
        }
    }

    /**
     * 展示发表动态类型弹窗
     */
    private void showPublishTypeDialog() {
        View view = this.getLayoutInflater().inflate(R.layout.dialog_publish_dynamic, null);

        final Dialog dialog = new Dialog(this, R.style.MyDialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable());
        dialog.setContentView(view);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();

        TextView tv_dynamic_text = (TextView) view.findViewById(R.id.tv_dynamic_text);
        LinearLayout ll_dynamic_camera = (LinearLayout) view.findViewById(R.id.ll_dynamic_camera);
        TextView tv_dynamic_gallery = (TextView) view.findViewById(R.id.tv_dynamic_gallery);

        tv_dynamic_gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectPic(101);
                dialog.dismiss();
            }
        });

        tv_dynamic_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.putExtra("mediatype", "text");
                intent.setClass(NewFriendCircleActivity.this, DynamicPublishActivity.class);
                startActivityForResult(intent, 201);
                dialog.dismiss();
            }
        });

        ll_dynamic_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RxPermissions rxPermissions = new RxPermissions(NewFriendCircleActivity.this);

                rxPermissions.request(
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.CAMERA,
                        Manifest.permission.RECORD_AUDIO,
                        Manifest.permission.WRITE_SETTINGS)
                        .subscribe(new Consumer<Boolean>() {
                            @Override
                            public void accept(Boolean aBoolean) throws Exception {
                                if (aBoolean) {
                                    openCamera();
                                }
                            }
                        });

                dialog.dismiss();
            }
        });


        TextView tv_cancel = (TextView) view.findViewById(R.id.tv_cancel);
        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });



        Window window = dialog.getWindow();
        window.setGravity(Gravity.BOTTOM);
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(params);

    }

    private void openCamera() {
        Intent intent = new Intent();
        intent.setClass(NewFriendCircleActivity.this, RecordVideoActivity.class);
        startActivityForResult(intent, 102);
    }

    private void selectPic(int code) {
        Matisse
                .from(this)
                //选择视频和图片
                //选择图片
                .choose(MimeType.ofImage(), false)
                //有序选择图片 123456...
                .countable(true)
                //最大选择数量为9
                .maxSelectable(9)
                //选择方向
                .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
                //界面中缩略图的质量
                .thumbnailScale(0.8f)
                //蓝色主题
                //黑色主题
                .theme(R.style.Matisse_Dracula)
                //Glide加载方式
                //Picasso加载方式
                .imageEngine(new GlideImageEngine())
                //请求码
                .forResult(code);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            switch (requestCode) {
                case 101:
                    Intent picIntent = new Intent();
                    ArrayList<String> pathList = new ArrayList<>();

                    pathList = (ArrayList<String>) Matisse.obtainPathResult(data);
                  /*  if(pathList.size()==1){
                        if(MediaFileUtil.isImageFileType(pathList.get(0))){
                            intent.putExtra("mediatype","pic");
                            intent.putStringArrayListExtra("pathList",pathList);
                        }else if(MediaFileUtil.isVideoFileType(pathList.get(0))){
                            intent.putExtra("mediatype","video");
                            intent.putExtra("videoPath",pathList.get(0));
                        }
                    }else {*/
                    picIntent.putExtra("mediatype", "pic");
                    picIntent.putStringArrayListExtra("pathList", pathList);
                    picIntent.setClass(NewFriendCircleActivity.this, DynamicPublishActivity.class);
                    startActivityForResult(picIntent, 201);
                    /*  }*/
                    break;
                case 102:
                    Intent cameraIntent = new Intent();
                    ArrayList<String> cameraPathList = new ArrayList<>();

                    //pic是照片,video是视频
                    String mediatype = data.getStringExtra("mediatype");
                    cameraIntent.putExtra("mediatype", mediatype);
                    //照片，视频保存路径
                    String path = data.getStringExtra("path");
                    if (mediatype.equals("pic")) {
                        cameraPathList.add(path);
                        cameraIntent.putStringArrayListExtra("pathList", cameraPathList);
                    } else {
                        cameraIntent.putExtra("videoPath", path);
                    }
                    cameraIntent.setClass(NewFriendCircleActivity.this, DynamicPublishActivity.class);
                    startActivityForResult(cameraIntent, 201);
                    break;


            }
        }

    }
    /**
     * 根据ID获取他人信息
     *
     * @param userId
     */
    private void getUserInfo(String userId) {
        Map<String, Object> params = new HashMap<>();
        params.put("userId", userId);
        HttpProxy.obtain().get(PlatformContans.User.getUserResultById, params, MyApplication.token, new ICallBack() {
            @Override
            public void OnSuccess(String result) {
                Log.e("detail", result);
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    JSONObject data = jsonObject.getJSONObject("data");
                    UserMsg userMsg = new Gson().fromJson(data.toString(), UserMsg.class);
                    setUserInfo(userMsg);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(String error) {

            }
        });

    }
    /**
     * 设置他人的头像 昵称，背景图等相关信息
     *
     * @param userMsg
     */
    private void setUserInfo(UserMsg userMsg) {
        Glide.with(this).load(userMsg.getHeadPortrait()).into(iv_headpic);
        tv_nickname.setText(userMsg.getName());
        Glide.with(this).load(userMsg.getBackground()).into(friend_background);
    }

    private void initView() {
        mCircleData = new ArrayList<>();
        mCircleDataAdapter = new CircleDataAdapter(this, R.layout.item_friends_dynamic, mCircleData);
        mCircleDataAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                page++;
                getData(false);
            }
        }, rv_circle);
        //rv_circle.setFocusableInTouchMode(false);
        rv_circle.setLayoutManager(new LinearLayoutManager(this));
        rv_circle.setAdapter(mCircleDataAdapter);
        getFriendType();
        getData(true);

    }
    private void getFriendType(){
        if (!TextUtils.isEmpty(userId)) {
            if (userId.equals(MyApplication.getUserInfo().getId())) {
                //获取我自己的朋友圈
                friendsCircleType = 1;

                if (MyApplication.isLogin) {
                    Glide.with(this).load(MyApplication.getUserInfo().getHeadPortrait()).into(iv_headpic);
                    tv_nickname.setText(MyApplication.getUserInfo().getName());
                    Glide.with(this).load(MyApplication.getUserInfo().getBackground()).into(friend_background);

                }
            } else {
                //获取他人的朋友圈列表
                friendsCircleType = 2;

                //通过id获取他人信息,
                getUserInfo(userId);

            }
        } else {
            //获取全部人的朋友圈
            friendsCircleType = 0;

            if (MyApplication.isLogin) {
                Glide.with(this).load(MyApplication.getUserInfo().getHeadPortrait()).into(iv_headpic);
                tv_nickname.setText(MyApplication.getUserInfo().getName());
                Glide.with(this).load(MyApplication.getUserInfo().getBackground()).into(friend_background);
            }
        }
    }
    private void getData(boolean first) {
        Map<String, Object> params = new HashMap<>();
        params.put("page", page);
        if (!first) {
            params.put("scrollId", firstItemId);
        }
        String url;
        if (friendsCircleType == 1) {
            //获取我自己的朋友圈
            url = PlatformContans.CommunicationCircle.getMyCommunicationCircleList;
        } else if (friendsCircleType == 2) {
            //获取他人的朋友圈列表
            params.put("userId", userId);
            url = PlatformContans.CommunicationCircle.getCommunicationCircleListByUserId;
        } else {
            //获取全部人的App
            url = PlatformContans.CommunicationCircle.getCommunicationCircleListForApp;
        }
        HttpProxy.obtain().get(url, params, MyApplication.token, new ICallBack() {
            @Override
            public void OnSuccess(String result) {
                Log.e("getCircleList", result);
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    JSONArray data = jsonObject.getJSONArray("data");
                    List<CircleData> circleDataList = new ArrayList<>();
                    if (data != null && data.length() > 0) {
                        for (int i = 0; i < data.length(); i++) {
                            JSONObject obj = data.getJSONObject(i);
                            CircleData circleData = new Gson().fromJson(obj.toString(), CircleData.class);
                            mCircleData.add(circleData);
                            circleDataList.add(circleData);
                        }
                        if (first) {
                            firstItemId = mCircleData.get(0).getId();
                        }
                        if (isLoadMore) {
                            isLoadMore = false;
                            mCircleDataAdapter.addData(circleDataList);
                            mCircleDataAdapter.loadMoreComplete();
                        } else {
                            mCircleDataAdapter.setNewData(mCircleData);
                        }


                    } else {
                        mCircleDataAdapter.loadMoreEnd(true);
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

    /**
     * 展示删除动态弹窗
     */
    public void showDeleteCirclePoppupWindow(View view, String circleId) {
        View contentView = LayoutInflater.from(NewFriendCircleActivity.this).inflate(
                R.layout.poppupwindow_delete_friends_circle, null);

        final PopupWindow popupWindow = new PopupWindow(contentView,
                ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT, true);

        contentView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 0; i < mCircleDataAdapter.getData().size(); i++) {
                    if (circleId.equals(mCircleDataAdapter.getData().get(i).getId())) {
                        mCircleDataAdapter.remove(i);
                        //circleAdapter.notifyDataSetChanged();
                        break;
                    }
                }

                popupWindow.dismiss();
                deleteFriendsCircle(circleId);
            }
        });

        popupWindow.setTouchable(true);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);

        popupWindow.showAsDropDown(view, 0, 0, Gravity.RIGHT);

    }

    /**
     * 删除朋友圈
     *
     * @param circleId
     */
    private void deleteFriendsCircle(String circleId) {
        Map<String, Object> params = new HashMap<>();
        params.put("id", circleId);

        HttpProxy.obtain().post(PlatformContans.CommunicationCircle.deleteCommunicationCircleById, MyApplication.token, params, new ICallBack() {
            @Override
            public void OnSuccess(String result) {
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    String resultCode = jsonObject.getString("resultCode");
                    if (resultCode.equals("0")) {
                        Toast.makeText(NewFriendCircleActivity.this, "删除成功", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(NewFriendCircleActivity.this, "删除失败", Toast.LENGTH_SHORT).show();
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

    /**
     * 点赞，取消点赞
     *
     * @param didPraise
     * @param id
     */
    public void performPraise(boolean didPraise, String id) {
        Map<String, Object> params = new HashMap<>();
        params.put("id", id);

        String url;
        if (didPraise) {
            url = PlatformContans.CommunicationCircle.clickCommunicationCircleById;
        } else {
            url = PlatformContans.CommunicationCircle.cancelClickById;
        }

        HttpProxy.obtain().post(url, MyApplication.token, params, new ICallBack() {
            @Override
            public void OnSuccess(String result) {
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    String resultCode = jsonObject.getString("resultCode");
                    if (resultCode.equals("0")) {
                        Toast.makeText(NewFriendCircleActivity.this, "操作成功", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(NewFriendCircleActivity.this, "操作失败", Toast.LENGTH_SHORT).show();
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

    /**
     * 添加回复消息
     *
     * @param smsgContent
     * @param id
     */
    public void addReplay(String id, String smsgContent) {
        Map<String, Object> params = new HashMap<>();
        params.put("id", id);
        params.put("content", smsgContent);

        String url = PlatformContans.CommunicationCircle.addCommunicationCircleCommentById;

        HttpProxy.obtain().post(url, MyApplication.token, params, new ICallBack() {
            @Override
            public void OnSuccess(String result) {
                Log.e("addReplay", result);
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    String resultCode = jsonObject.getString("resultCode");
                    if (resultCode.equals("0")) {
                        Toast.makeText(NewFriendCircleActivity.this, "评论成功", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(NewFriendCircleActivity.this, "评论失败", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(String error) {
                Toast.makeText(NewFriendCircleActivity.this, "评论失败", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * 回复别人
     *
     * @param id
     * @param msg
     * @param replyUserId
     */
    public void sendReplay(String id, String msg, String replyUserId) {
        Map<String, Object> params = new HashMap<>();
        params.put("id", id);
        params.put("content", msg);
        params.put("replyUserId", replyUserId);

        String url = PlatformContans.CommunicationCircle.replyCommunicationCircleComment;

        HttpProxy.obtain().post(url, MyApplication.token, params, new ICallBack() {
            @Override
            public void OnSuccess(String result) {
                Log.e("sendReplay", result);
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    String resultCode = jsonObject.getString("resultCode");
                    if (resultCode.equals("0")) {
                        Toast.makeText(NewFriendCircleActivity.this, "回复成功", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(NewFriendCircleActivity.this, "回复失败", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(String error) {
                Toast.makeText(NewFriendCircleActivity.this, "回复失败", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * 删除评论或回复
     *
     * @param id
     */
    public void deleteCommon(String id) {
        Map<String, Object> params = new HashMap<>();
        params.put("id", id);
        String url = PlatformContans.CommunicationCircle.deleteCommunicationCircleComment;

        HttpProxy.obtain().post(url, MyApplication.token, params, new ICallBack() {
            @Override
            public void OnSuccess(String result) {
                Log.e("deleteCommon", result);
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    String resultCode = jsonObject.getString("resultCode");
                    if (resultCode.equals("0")) {
                        Toast.makeText(NewFriendCircleActivity.this, "删除成功", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(NewFriendCircleActivity.this, "删除失败", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {

                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(String error) {
                Toast.makeText(NewFriendCircleActivity.this, "删除失败", Toast.LENGTH_SHORT).show();
            }
        });
    }
}