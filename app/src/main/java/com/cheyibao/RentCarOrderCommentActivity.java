package com.cheyibao;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.common.ResourceUtils;
import com.coorchice.library.SuperTextView;
import com.example.yunchebao.R;
import com.iarcuschin.simpleratingbar.SimpleRatingBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RentCarOrderCommentActivity extends AppCompatActivity {

    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.searchLay)
    LinearLayout searchLay;
    @BindView(R.id.superText)
    SuperTextView superText;
    @BindView(R.id.textBtn)
    TextView textBtn;
    @BindView(R.id.shareBtn)
    ImageView shareBtn;
    @BindView(R.id.shopCartBtn)
    ImageView shopCartBtn;
    @BindView(R.id.menuBtn)
    ImageView menuBtn;
    @BindView(R.id.userBtn)
    ImageView userBtn;
    @BindView(R.id.starbar)
    SimpleRatingBar starbar;
    @BindView(R.id.score_view)
    TextView scoreView;
    @BindView(R.id.comment_content_view)
    EditText commentContentView;
    @BindView(R.id.image_list_view)
    RecyclerView imageListView;

    private String orderId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rent_car_order_comment);
        ButterKnife.bind(this);
        orderId = getIntent().getStringExtra("rent_car_order_id");
        textBtn.setText("发布");
        textBtn.setTextColor(ResourceUtils.getColorByResource(this,R.color.yellow_65));
    }

    @OnClick(R.id.back)
    public void onBackClicked() {
        onBackPressed();
    }

    @OnClick(R.id.textBtn)
    public void onShareBtnClicked() {
    }
}
