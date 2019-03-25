package com.vipcenter;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.FrameLayout;

import com.example.yunchebao.R;
import com.vipcenter.fragment.CarOrderCommentFragment;
import com.vipcenter.fragment.WashOrderCommentFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OrderCommentsActivity extends AppCompatActivity {

    String id;
    int type=0;
    WashOrderCommentFragment mWashOrderCommentFragment;
    CarOrderCommentFragment mCarOrderCommentFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_comments);
        id=getIntent().getStringExtra("id");
        type =getIntent().getIntExtra("type",0);
        ButterKnife.bind(this);
        mWashOrderCommentFragment=WashOrderCommentFragment.newInstance(id);
        mCarOrderCommentFragment=CarOrderCommentFragment.newInstance(id);
        if(type==1)
          getSupportFragmentManager().beginTransaction().add(R.id.fr_content,mWashOrderCommentFragment).commit();
        else{
            getSupportFragmentManager().beginTransaction().add(R.id.fr_content,mCarOrderCommentFragment).commit();
        }
    }
}
