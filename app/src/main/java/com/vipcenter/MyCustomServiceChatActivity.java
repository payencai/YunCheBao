package com.vipcenter;

import android.os.Bundle;
import android.view.View;

import com.example.yunchebao.R;
import com.hyphenate.easeui.widget.EaseChatInputMenu;
import com.nohttp.sample.NoHttpBaseActivity;
import com.tool.ActivityConstans;
import com.tool.UIControlUtils;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by sdhcjhss on 2018/1/24.
 */

public class MyCustomServiceChatActivity extends NoHttpBaseActivity {
    protected EaseChatInputMenu inputMenu;
    protected int[] itemStrings = {com.hyphenate.easeui.R.string.attach_take_pic, com.hyphenate.easeui.R.string.attach_picture, com.hyphenate.easeui.R.string.attach_location};
    protected int[] itemdrawables = {com.hyphenate.easeui.R.drawable.ease_chat_takepic_selector, com.hyphenate.easeui.R.drawable.ease_chat_image_selector,
            com.hyphenate.easeui.R.drawable.ease_chat_location_selector};
    protected int[] itemIds = {ITEM_TAKE_PICTURE, ITEM_PICTURE, ITEM_LOCATION};
    static final int ITEM_TAKE_PICTURE = 1;
    static final int ITEM_PICTURE = 2;
    static final int ITEM_LOCATION = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat_detail_layout);
        initView();


    }

    private void initView() {
        ButterKnife.bind(this);
        UIControlUtils.UITextControlsUtils.setUIText(findViewById(R.id.title), ActivityConstans.UITag.TEXT_VIEW, "在线客服");
        inputMenu = (EaseChatInputMenu) findViewById(R.id.input_menu);
        // init input menu
        inputMenu.init(null);
    }


    @OnClick({R.id.back})
    public void OnClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                onBackPressed();
                break;
        }
    }

}
