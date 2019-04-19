package com.newversion;

import android.Manifest;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.cjt2325.cameralibrary.JCameraView;
import com.example.yunchebao.R;
import com.tbruyelle.rxpermissions2.RxPermissions;

import io.reactivex.functions.Consumer;

/**
 * 长按录制视频，点击拍照
 */
public class RecordVideoActivity extends AppCompatActivity {

    private JCameraView mJCameraView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);//没有标题
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);//设置全屏
        this.getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_NOSENSOR);//设置竖屏

        setContentView(R.layout.activity_record_video);
        mJCameraView = (JCameraView) findViewById(R.id.cameraView);
        initView();

    }

    @Override
    protected void onResume() {
        mJCameraView.onResume();
        super.onResume();
    }

    @Override
    protected void onPause() {
        mJCameraView.onPause();
        super.onPause();
    }

    private void initView() {

       /* mJCameraView.setActivity(this);
        设置视频保存路径（如果不设置默认为Environment.getExternalStorageDirectory().getPath()）
        mJCameraView.setAutoFocus(false);*/
        mJCameraView.setSaveVideoPath(Environment.getExternalStorageDirectory().getPath());
        mJCameraView.setCameraViewListener(new JCameraView.CameraViewListener() {
            @Override
            public void quit() {
                RecordVideoActivity.this.finish();
            }

            @Override
            public void captureSuccess(Bitmap bitmap) {//拍照成功
                String path = BitmapUtils.saveBitmapToSDCard(bitmap, RecordVideoActivity.this);//拍照保存路径
                Intent intent = new Intent();
                //把返回数据存入Intent pic是照片,video是视频
                intent.putExtra("path", path);
                intent.putExtra("mediatype", "pic");
                //设置返回数据
                setResult(RESULT_OK, intent);
                finish();
            }

            @Override
            public void recordSuccess(String path) {//录视频成功
                Intent intent = new Intent();
                //把返回数据存入Intent pic是照片,video是视频
                intent.putExtra("path", path);
                intent.putExtra("mediatype", "video");
                //设置返回数据
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }
}
