package com.example.yunchebao.rongcloud.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.yunchebao.R;
import com.gyf.immersionbar.ImmersionBar;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SearchAddActivity extends AppCompatActivity {
    @BindView(R.id.et_search)
    EditText et_search;
    @BindView(R.id.ll_search)
    LinearLayout ll_search;
    @BindView(R.id.friend)
    TextView friend;
    @BindView(R.id.group)
    TextView group;
    @BindView(R.id.tv_cancel)
    TextView tv_cancel;
    @BindView(R.id.rl_friend)
    RelativeLayout rl_friend;
    @BindView(R.id.rl_group)
    RelativeLayout rl_group;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_add);
        ButterKnife.bind(this);
        ImmersionBar.with(this).autoDarkModeEnable(true).fitsSystemWindows(true).statusBarColor(R.color.white).init();
        initView();
    }

    private void initView() {
        rl_friend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(SearchAddActivity.this,SearchResultActivity.class);
                intent.putExtra("type","0");
                intent.putExtra("content",et_search.getEditableText().toString());
                startActivity(intent);
            }
        });
        rl_group.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(SearchAddActivity.this,SearchResultActivity.class);
                intent.putExtra("type","1");
                intent.putExtra("content",et_search.getEditableText().toString());
                startActivity(intent);
            }
        });
        et_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                 String content=s.toString();
                 if(content.length()<=0){
                     ll_search.setVisibility(View.GONE);
                 }else{
                     ll_search.setVisibility(View.VISIBLE);
                     friend.setText(content);
                     group.setText(content);
                 }
            }
        });
        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
