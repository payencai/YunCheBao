package com.rongcloud.activity;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.application.MyApplication;
import com.example.yunchebao.R;
import com.uuzuche.lib_zxing.activity.CodeUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GroupQrcodeActivity extends AppCompatActivity {
    @BindView(R.id.iv_qrcode)
    ImageView iv_qrcode;
    String id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_qrcode);
        ButterKnife.bind(this);
        id=getIntent().getStringExtra("id");
        initView();
    }

    private void initView() {
        String url="云车宝群组:"+ id;
        Bitmap bitmap = CodeUtils.createImage(url,200,200,null);
        iv_qrcode.setImageBitmap(bitmap);
        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
