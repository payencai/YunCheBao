package com.vipcenter;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.yunchebao.R;
import com.nohttp.sample.NoHttpBaseActivity;
import com.tool.ActivityAnimationUtils;
import com.tool.ActivityConstans;
import com.tool.UIControlUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by sdhcjhss on 2018/1/3.
 */

public class FeedbackHelpActivity extends NoHttpBaseActivity {

    @BindView(R.id.answ1)
    TextView answ1;
    @BindView(R.id.answ2)
    TextView answ2;
    @BindView(R.id.answ3)
    TextView answ3;
    @BindView(R.id.answ4)
    TextView answ4;
    @BindView(R.id.answ5)
    TextView answ5;
    @BindView(R.id.answ6)
    TextView answ6;
    @BindView(R.id.answ7)
    TextView answ7;
    @BindView(R.id.arrow1)
    ImageView arrow1;
    @BindView(R.id.arrow2)
    ImageView arrow2;
    @BindView(R.id.arrow3)
    ImageView arrow3;
    @BindView(R.id.arrow4)
    ImageView arrow4;
    @BindView(R.id.arrow5)
    ImageView arrow5;
    @BindView(R.id.arrow6)
    ImageView arrow6;
    @BindView(R.id.arrow7)
    ImageView arrow7;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.help_info_layout);
        initView();
    }

    private void initView() {
        UIControlUtils.UITextControlsUtils.setUIText(findViewById(R.id.title), ActivityConstans.UITag.TEXT_VIEW, "帮助反馈");
        ButterKnife.bind(this);
    }

    @OnClick({R.id.back,R.id.btn, R.id.ques1, R.id.ques2, R.id.ques3, R.id.ques4, R.id.ques5, R.id.ques6, R.id.ques7})
    public void OnClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                onBackPressed();
                break;
            case R.id.btn:
                ActivityAnimationUtils.commonTransition(FeedbackHelpActivity.this,FeedbackSubmitActivity.class, ActivityConstans.Animation.FADE);
                break;
            case R.id.ques1:
                if (answ1.isShown()) {
                    answ1.setVisibility(View.GONE);
                    arrow1.setImageResource(R.mipmap.arrow_down);
                } else {
                    answ1.setVisibility(View.VISIBLE);
                    arrow1.setImageResource(R.mipmap.arrow_up);
                }
                break;
            case R.id.ques2:
                if (answ2.isShown()) {
                    answ2.setVisibility(View.GONE);
                    arrow2.setImageResource(R.mipmap.arrow_down);
                } else {
                    answ2.setVisibility(View.VISIBLE);
                    arrow2.setImageResource(R.mipmap.arrow_up);
                }
                break;
            case R.id.ques3:
                if (answ3.isShown()) {
                    answ3.setVisibility(View.GONE);
                    arrow3.setImageResource(R.mipmap.arrow_down);
                } else {
                    answ3.setVisibility(View.VISIBLE);
                    arrow3.setImageResource(R.mipmap.arrow_up);
                }
                break;
            case R.id.ques4:
                if (answ4.isShown()) {
                    answ4.setVisibility(View.GONE);
                    arrow4.setImageResource(R.mipmap.arrow_down);
                } else {
                    answ4.setVisibility(View.VISIBLE);
                    arrow4.setImageResource(R.mipmap.arrow_up);
                }
                break;
            case R.id.ques5:
                if (answ5.isShown()) {
                    answ5.setVisibility(View.GONE);
                    arrow5.setImageResource(R.mipmap.arrow_down);
                } else {
                    answ5.setVisibility(View.VISIBLE);
                    arrow5.setImageResource(R.mipmap.arrow_up);
                }
                break;
            case R.id.ques6:
                if (answ6.isShown()) {
                    answ6.setVisibility(View.GONE);
                    arrow6.setImageResource(R.mipmap.arrow_down);
                } else {
                    answ6.setVisibility(View.VISIBLE);
                    arrow6.setImageResource(R.mipmap.arrow_up);
                }
                break;
            case R.id.ques7:
                if (answ7.isShown()) {
                    answ7.setVisibility(View.GONE);
                    arrow7.setImageResource(R.mipmap.arrow_down);
                } else {
                    answ7.setVisibility(View.VISIBLE);
                    arrow7.setImageResource(R.mipmap.arrow_up);
                }
                break;
        }
    }
}
