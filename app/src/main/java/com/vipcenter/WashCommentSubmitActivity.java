package com.vipcenter;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.TextView;

import com.example.yunchebao.R;
import com.nohttp.sample.NoHttpBaseActivity;
import com.tool.ActivityConstans;
import com.tool.UIControlUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by sdhcjhss on 2018/1/5.
 * 洗护评论——发表评论
 */

public class WashCommentSubmitActivity extends NoHttpBaseActivity {
    @BindView(R.id.textBtn)
    TextView rightBtn;

    private Context ctx;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wash_comment_submit_layout);
        initView();
    }

    private void initView() {
        UIControlUtils.UITextControlsUtils.setUIText(findViewById(R.id.title), ActivityConstans.UITag.TEXT_VIEW,"发表评论");
        ButterKnife.bind(this);
        ctx = this;
        rightBtn.setTextColor(ContextCompat.getColor(ctx, R.color.yellow_65));
        rightBtn.setText("发布");
        rightBtn.setVisibility(View.VISIBLE);
    }
    @OnClick({R.id.back,R.id.textBtn})
    public void OnClick(View v){
        switch (v.getId()){
            case R.id.back:
                onBackPressed();
                break;
            case R.id.textBtn:
                break;

        }
    }
}
