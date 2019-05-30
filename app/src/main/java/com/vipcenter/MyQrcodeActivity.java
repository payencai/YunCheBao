package com.vipcenter;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.yunchebao.MyApplication;
import com.example.yunchebao.R;
import com.uuzuche.lib_zxing.activity.CodeUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyQrcodeActivity extends AppCompatActivity {
    @BindView(R.id.iv_qrcode)
    ImageView iv_qrcode;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_qrcode);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        String url="云车宝好友:"+ MyApplication.getUserInfo().getId();
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
