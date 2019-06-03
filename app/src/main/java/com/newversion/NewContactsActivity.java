package com.newversion;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.yunchebao.R;
import com.example.yunchebao.rongcloud.activity.contact.ContactsFragment;
import com.example.yunchebao.rongcloud.activity.contact.FriendListFragment;
import com.example.yunchebao.rongcloud.activity.contact.GroupListFragment;
import com.example.yunchebao.rongcloud.activity.contact.NewFriendFragment;
import com.gyf.immersionbar.ImmersionBar;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NewContactsActivity extends AppCompatActivity {
    int type=0;
    @BindView(R.id.title)
    TextView title;
    Fragment mFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_contacts);
        ButterKnife.bind(this);
        ImmersionBar.with(this).autoDarkModeEnable(true).fitsSystemWindows(true).statusBarColor(R.color.white).init();
        type=getIntent().getIntExtra("type",0);
        if(type==1){
            mFragment=new ContactsFragment();
            title.setText("通讯录");
        }else if(type==2){
            mFragment=new NewFriendFragment();
            title.setText("新的好友");
        }else if(type==3){
            mFragment=new GroupListFragment();
            title.setText("群聊");
        }
        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        getSupportFragmentManager().beginTransaction().add(R.id.fr_content,mFragment).commit();
    }
}
