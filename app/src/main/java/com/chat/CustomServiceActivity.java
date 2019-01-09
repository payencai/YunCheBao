package com.chat;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;

import com.chat.fragment.ChatFragment;
import com.chat.runtimepermissions.PermissionsManager;
import com.example.yunchebao.R;
import com.hyphenate.easeui.ui.EaseChatFragment;
import com.hyphenate.util.EasyUtils;
import com.nohttp.sample.NoHttpFragmentBaseActivity;

/**
 *
 */
public class CustomServiceActivity extends NoHttpFragmentBaseActivity {
    public static CustomServiceActivity activityInstance;
    private EaseChatFragment chatFragment;
    String toChatUsername;

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.chat_fragment_container);
        activityInstance = this;
        //get user id or group id
//        toChatUsername = getIntent().getExtras().getString("userId");
        toChatUsername = "fanfan";
        //use EaseChatFratFragment
        chatFragment = new ChatFragment();
        //pass parameters to chat fragment
        Bundle bundle = getIntent().getExtras();
        chatFragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().add(R.id.container, chatFragment).commit();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        activityInstance = null;
    }

    @Override
    protected void onNewIntent(Intent intent) {
        // make sure only one chat activity is opened
        String username = intent.getStringExtra("userId");
        if (toChatUsername.equals(username))
            super.onNewIntent(intent);
        else {
            finish();
            startActivity(intent);
        }
    }

    @Override
    public void onBackPressed() {
        chatFragment.onBackPressed();
//        if (EasyUtils.isSingleActivity(this)) {
//            Intent intent = new Intent(this, Tab3MainActivity.class);
//            startActivity(intent);
//        }
    }

    public String getToChatUsername() {
        return toChatUsername;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        PermissionsManager.getInstance().notifyPermissionsChange(permissions, grantResults);
    }
}
