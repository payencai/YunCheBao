package com.newversion;

import android.app.ActionBar;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yunchebao.MyApplication;
import com.bbcircle.view.SampleCoverVideo;
import com.costans.PlatformContans;
import com.example.yunchebao.R;
import com.example.yunchebao.net.NetUtils;
import com.example.yunchebao.net.OnMessageReceived;
import com.http.HttpProxy;
import com.http.ICallBack;
import com.payencai.library.util.ToastUtil;
import com.payencai.library.util.VideoUtil;
import com.system.X5WebviewActivity;
import com.system.model.AddressBean;
import com.tool.GlideImageEngine;
import com.tool.view.GridViewForScrollView;
import com.xiasuhuei321.loadingdialog.view.LoadingDialog;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.internal.entity.CaptureStrategy;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import top.zibin.luban.CompressionPredicate;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

public class DynamicPublishActivity extends AppCompatActivity {

    @BindView(R.id.et_dynamic_text)
    EditText etDynamicText;
    @BindView(R.id.iv_play)
    ImageView iv_play;
    @BindView(R.id.sampleCoverVideo)
    SampleCoverVideo sampleCoverVideo;
    @BindView(R.id.gv_dynamic_photos)
    GridViewForScrollView gvDynamicPhotos;
    @BindView(R.id.ll_look_permission)
    LinearLayout llLookPermission;
    @BindView(R.id.ll_user_location)
    LinearLayout ll_user_location;
    @BindView(R.id.rootView)
    LinearLayout rootView;
    @BindView(R.id.tv_look_permission)
    TextView tvLookPermission;
    @BindView(R.id.tv_location)
    TextView tv_location;
    @BindView(R.id.tv_publish_dynamic)
    TextView tv_pub;
    @BindView(R.id.frame_video_player)
    FrameLayout frameVideoPlayer;
    private ArrayList<String> pathList = new ArrayList<>();
    private ArrayList<File> fileList = new ArrayList<>();
    private int kind = 1;
    private String looks;
    private String video;
    private String vimg;
    private String unLooks;
    private String users;
    String videoPath;
    private String mediatype;
    SelectPicGridAdapter adapter;

    int count = 0;
//    private Handler popupHandler = new Handler() {
//        @Override
//        public void handleMessage(Message msg) {
//            switch (msg.what) {
//                case 0:
//                    popupWindow.showAtLocation(rootView, Gravity.NO_GRAVITY, 0, 0);
//                    popupWindow.update();
//                    break;
//             }
//}
//
//    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dynamic_publish);
        ButterKnife.bind(this);

        initView();
    }

    private void initView() {
        //initPopWindow();

        mediatype = getIntent().getStringExtra("mediatype");
        if (mediatype.equals("text")) {
            gvDynamicPhotos.setVisibility(View.GONE);
            frameVideoPlayer.setVisibility(View.GONE);
        } else if (mediatype.equals("pic")) {
            pathList = getIntent().getStringArrayListExtra("pathList");
            pathList.add(0, "");
            //etDynamicText.setText(pathList.get(0));
            if (pathList.size() > 0) {
                frameVideoPlayer.setVisibility(View.GONE);
                gvDynamicPhotos.setVisibility(View.VISIBLE);
                adapter = new SelectPicGridAdapter(this, pathList);
                adapter.setShow(true);
                adapter.setOnImageClick(new SelectPicGridAdapter.OnImageClick() {
                    @Override
                    public void onClick(int pos) {
                        pathList.remove(pos);
                        adapter.notifyDataSetChanged();
                    }
                });
                gvDynamicPhotos.setAdapter(adapter);
            }

        } else if (mediatype.equals("video")) {
            gvDynamicPhotos.setVisibility(View.GONE);
            frameVideoPlayer.setVisibility(View.VISIBLE);
            videoPath = getIntent().getStringExtra("videoPath");
            sampleCoverVideo.setUpLazy(videoPath, true, null, null, "");
            sampleCoverVideo.getTitleTextView().setVisibility(View.GONE);
            sampleCoverVideo.getBackButton().setVisibility(View.GONE);

            sampleCoverVideo.getFullscreenButton().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    iv_play.setVisibility(View.GONE);
                    sampleCoverVideo.startWindowFullscreen(v.getContext(), false, true);
                }
            });
            sampleCoverVideo.loadCoverImage(videoPath, R.mipmap.pic1);
            //initVideo();
            //etDynamicText.setText(videoPath);
        }
        gvDynamicPhotos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    int num = 10 - pathList.size();
                    if (num > 0)
                        selectFile(200, num);
                    else {
                        ToastUtil.showToast(DynamicPublishActivity.this, "最多只能上传9张图片");
                    }
                }

            }
        });
    }

    private void selectFile(int code, int num) {
        Matisse
                .from(this)
                //选择视频和图片
                //选择图片
                .choose(MimeType.ofImage(), true)//不能同时选择视频和照片
                //有序选择图片 123456...
                .countable(true)
                //最大选择数量为9
                .maxSelectable(num)
                .capture(true)
                //选择方向
                .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
                //界面中缩略图的质量
                .thumbnailScale(0.8f)
                //蓝色主题
                //黑色主题
                .theme(R.style.Matisse_Dracula)
                .captureStrategy(new CaptureStrategy(true, "com.yancy.gallerypickdemo.fileprovider"))
                //Glide加载方式
                //Picasso加载方式
                .imageEngine(new GlideImageEngine())
                //请求码
                .forResult(200);
    }


    private String content;

    @OnClick({R.id.tv_cancel_publish, R.id.tv_publish_dynamic, R.id.ll_look_permission, R.id.ll_user_location})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_cancel_publish:
                onBackPressed();
                break;
            case R.id.tv_publish_dynamic:
                content = etDynamicText.getEditableText().toString();
                if (mediatype.equals("text")) {
                    if (!TextUtils.isEmpty(content)) {
                        ToastUtil.showToast(DynamicPublishActivity.this, "请输入要发布的文字内容");
                        return;
                    }
                } else if (mediatype.equals("pic")) {
                    if (pathList.size() == 1) {
                        ToastUtil.showToast(this, "请至少选择一张图片");
                        return;
                    }
                    tv_pub.setEnabled(false);
                    uploadImages(pathList);

                } else if (mediatype.equals("video")) {
                    initVideo();
                }

                break;
            case R.id.ll_look_permission:
                startActivityForResult(new Intent(DynamicPublishActivity.this, DynamicLookPermissionActivity.class), 2);
                break;
            case R.id.ll_user_location:
                startActivityForResult(new Intent(DynamicPublishActivity.this, X5WebviewActivity.class), 1);
                break;
        }
    }

    private AddressBean mAddressBean;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (data != null) {
            switch (requestCode) {
                case 1:
                    mAddressBean = (AddressBean) data.getSerializableExtra("address");
                    String address = mAddressBean.getPoiaddress();
                    Log.e("mAddressBean", mAddressBean.toString());
                    tv_location.setText(address);
                    break;
                case 2:
                    kind = data.getIntExtra("kind", 1);
                    if (kind == 2) {
                        tvLookPermission.setText("私密");

                    } else if (kind == 3) {
                        looks = data.getStringExtra("ids");
                        looks = MyApplication.getUserInfo().getId() + "," + looks;
                        users = data.getStringExtra("users");

                        tvLookPermission.setText(users);


                    } else if (kind == 4) {
                        unLooks = data.getStringExtra("ids");
                        users = data.getStringExtra("users");
                        tvLookPermission.setText("除去  " + users);

                    } else {
                        tvLookPermission.setText("公开");
                    }

                    break;
                case 200:

                    pathList.addAll(Matisse.obtainPathResult(data));
                    adapter.notifyDataSetChanged();
                    break;
            }
        }


    }

    private ArrayList<String> imgsUrl = new ArrayList();

    /***
     * 上传多张图片
     * @param pathList
     */
    private void uploadImages(ArrayList<String> pathList) {
        count = 0;

        for (int i = 0; i < pathList.size(); i++) {
            if (!TextUtils.isEmpty(pathList.get(i))) {
                File file = new File(pathList.get(i));
                Luban.with(this)
                        .load(file)
                        .ignoreBy(100)

                        .filter(new CompressionPredicate() {
                            @Override
                            public boolean apply(String path) {
                                return !(TextUtils.isEmpty(path) || path.toLowerCase().endsWith(".gif"));
                            }
                        })
                        .setCompressListener(new OnCompressListener() {
                            @Override
                            public void onStart() {
                            }

                            @Override
                            public void onSuccess(File file) {
                                //evaluationBeans.get(mTempPosition).getEvaluationImages().add(0,file);
                                upImage(PlatformContans.Commom.uploadImg, file);
                            }

                            @Override
                            public void onError(Throwable e) {
                            }
                        }).launch();

            }

        }
        finish();
        // upImages(PlatformContans.Commom.uploadImgs, fileList);
    }

    public void upImage(String url, File file) {
        NetUtils.getInstance().upLoadImage(url, file, new OnMessageReceived() {
            @Override
            public void onSuccess(String response) {

                Log.e("upload", "onResponse: " + response);
                try {
                    JSONObject object = new JSONObject(response);
                    final String data = object.getString("data");
                    imgsUrl.add(data);
                    count++;
                    if ((pathList.size() - 1) == count) {
                        count = 0;
                        publishDynamic();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(String error) {
                Log.e("upload", "onResponse: " + error);

            }
        });


    }


    private String imgs;


    /**
     * 发布动态
     */
    private void publishDynamic() {
        Map<String, Object> params = new HashMap<>();
        if (!TextUtils.isEmpty(content)) {
            params.put("content", content);
        }

        if (!TextUtils.isEmpty(listToString(imgsUrl))) {
            params.put("imgs", listToString(imgsUrl));
        }
        if (!TextUtils.isEmpty(video)) {
            params.put("video", video);
        }
        if (!TextUtils.isEmpty(vimg)) {
            params.put("vimg", vimg);
        }
        if (mAddressBean != null) {
            params.put("address", mAddressBean.getPoiaddress());
            params.put("longitude", mAddressBean.getLatlng().getLng() + "");
            params.put("latitude", mAddressBean.getLatlng().getLat() + "");
        }

        params.put("type", 1);//（1.普通朋友圈，2.转发链接）

        params.put("kind", kind);//查看权限（1.公开，2私密，3.部分可见，4.不给谁看）

        Log.e("kind", kind + "");

        if (kind == 3) {
            if (!TextUtils.isEmpty(looks)) {
                params.put("looks", looks);
            }
        }

        if (kind == 4) {
            if (!TextUtils.isEmpty(unLooks)) {
                params.put("unLooks", unLooks);
            }
        }

        HttpProxy.obtain().post(PlatformContans.CommunicationCircle.addCommunicationCircle, MyApplication.token, params, new ICallBack() {
            @Override
            public void OnSuccess(String result) {
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    String resultCode = jsonObject.getString("resultCode");
                    if (resultCode.equals("0")) {
                        Toast.makeText(DynamicPublishActivity.this, "发布成功", Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(DynamicPublishActivity.this, "发布失败", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(String error) {
                Toast.makeText(DynamicPublishActivity.this, "发布失败", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * List转String逗号分隔
     */
    private String listToString(ArrayList<String> list) {
        if (list.size() == 0) {
            return "";
        }
        StringBuilder stringBuilder = new StringBuilder();
        for (String str : list) {
            stringBuilder.append(str);
            stringBuilder.append(",");
        }
        String strs = stringBuilder.toString();
        strs = strs.substring(0, strs.length() - 1);

        return strs;
    }

    public void upLoadVideo(String url, File file) {

        NetUtils.getInstance().upLoadVideo(url, file, new OnMessageReceived() {
            @Override
            public void onSuccess(String response) {

                String string = response;
                Log.e("video", "onResponse: " + string);
                try {
                    JSONObject object = new JSONObject(string);
                    int resultCode = object.getInt("resultCode");
                    final String data = object.getString("data");
                    video = data;
                    publishDynamic();
                    ///
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(String error) {

                Log.e("video", "onResponse: " + error);
            }
        });
        finish();
    }

    private void initVideo() {
        Bitmap bitmap = VideoUtil.voidToFirstBitmap(videoPath);
        String thumb = VideoUtil.bitmapToStringPath(this, bitmap);
        upThumbImage(PlatformContans.Commom.uploadImg, new File(thumb));

    }

    public void upThumbImage(String url, File file) {

        NetUtils.getInstance().upLoadImage(url, file, new OnMessageReceived() {
            @Override
            public void onSuccess(String response) {

                Log.e("vimg", "onResponse: " + response);
                try {
                    JSONObject object = new JSONObject(response);
                    int resultCode = object.getInt("resultCode");
                    final String data = object.getString("data");
                    vimg = data;
                    File filevideo = new File(videoPath);
                    upLoadVideo(PlatformContans.Commom.uploadVideo, filevideo);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(String error) {

            }
        });
    }
}
