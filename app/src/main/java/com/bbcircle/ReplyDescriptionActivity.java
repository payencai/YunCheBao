package com.bbcircle;

import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;

import com.example.yunchebao.R;
import com.nohttp.sample.NoHttpBaseActivity;
import com.tool.SimpleCommonUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import sj.keyboard.EmoticonsKeyBoardPopWindow;
import sj.keyboard.adpater.PageSetAdapter;
import sj.keyboard.interfaces.EmoticonClickListener;
import sj.keyboard.widget.EmoticonsEditText;

/**
 * Created by sdhcjhss on 2018/1/12.
 */

public class ReplyDescriptionActivity extends NoHttpBaseActivity {
    private EmoticonsKeyBoardPopWindow mKeyBoardPopWindow;
    private Context ctx;

    @BindView(R.id.et_content)
    EmoticonsEditText contentEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reply_description_layout);
        initView();
    }

    private void initView() {
        ButterKnife.bind(this);
        ctx = this;
        initKeyBoardPopWindow(contentEdit);
    }

    private void initKeyBoardPopWindow(EmoticonsEditText editText) {
        mKeyBoardPopWindow = new EmoticonsKeyBoardPopWindow(ctx);

        EmoticonClickListener emoticonClickListener = SimpleCommonUtils.getCommonEmoticonClickListener(editText);
        PageSetAdapter pageSetAdapter = new PageSetAdapter();
        SimpleCommonUtils.addEmojiPageSetEntity(pageSetAdapter, this, emoticonClickListener);
        SimpleCommonUtils.addXhsPageSetEntity(pageSetAdapter, this, emoticonClickListener);
        mKeyBoardPopWindow.setAdapter(pageSetAdapter);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK
                && mKeyBoardPopWindow != null && mKeyBoardPopWindow.isShowing()) {
            mKeyBoardPopWindow.dismiss();
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mKeyBoardPopWindow != null && mKeyBoardPopWindow.isShowing()) {
            mKeyBoardPopWindow.dismiss();
        }
    }

    @OnClick({R.id.back, R.id.faceBtn, R.id.text1})
    public void OnClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                onBackPressed();
                break;
            case R.id.faceBtn:
                if (mKeyBoardPopWindow != null && mKeyBoardPopWindow.isShowing()) {
                    mKeyBoardPopWindow.dismiss();
                } else {
                    if (mKeyBoardPopWindow == null) {
                        initKeyBoardPopWindow(contentEdit);
                    }
//                    mKeyBoardPopWindow.showPopupWindow();
                    if (mKeyBoardPopWindow.isShowing()) {
                        mKeyBoardPopWindow.dismiss();
                    } else {
                        mKeyBoardPopWindow.showAsDropDown(findViewById(R.id.faceBtn), 0, 0);
                    }
                }
                break;
            case R.id.text1:
                onBackPressed();
                break;
        }
    }
}
