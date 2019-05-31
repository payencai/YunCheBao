package com.example.yunchebao.rongcloud.activity;

import android.graphics.Bitmap;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.example.yunchebao.R;
import com.gyf.immersionbar.ImmersionBar;
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
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().getDecorView()
                    .setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            getWindow().setStatusBarColor(getResources().getColor(R.color.yellow_64));
        }
        initView();
    }

    private void initView() {
        String url="云车宝群组:"+ id;
        Log.e("url",url);
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
