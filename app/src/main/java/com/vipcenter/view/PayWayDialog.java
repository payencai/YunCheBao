package com.vipcenter.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.ImageView;

import com.example.yunchebao.R;

import java.util.List;

import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 选择支付方式Dialog
 * Created by Hh on 2017/3/6.
 */

public class PayWayDialog extends Dialog {

    @BindViews({R.id.selectBtn1, R.id.selectBtn2})
    List<ImageView> checks;
    private View.OnClickListener onClickListener;

    /** 区别三种支付方式 0:我的钱包 1:支付宝 2:微信支付 **/
    public static int payWay = 0;

    private Context context;

    /**
     *  如果ifRecharge 传入true 则是充值,就隐藏掉我的钱包, 否则则显示
     * @param context
     * @param themeResId
     * @param isRecharge
     * @param onClickListener
     */
    public PayWayDialog(Context context, int themeResId, boolean isRecharge, View.OnClickListener onClickListener) {
        super(context, themeResId);
        this.context = context;
        this.onClickListener = onClickListener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_pay);
        ButterKnife.bind(this);
        payWay = 1;
        Window dialogWindow = getWindow();
        dialogWindow.setGravity(Gravity.BOTTOM);
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.width = AbsListView.LayoutParams.MATCH_PARENT;
        lp.y = 0;//设置Dialog距离底部的距离
        dialogWindow.setAttributes(lp);
    }


    @OnClick({R.id.recharge_dialog_close, R.id.card1, R.id.card2, R.id.cardAdd})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.recharge_dialog_close:
                dismiss();
                break;
            case R.id.card1:
                checkChanges(0);
                break;
            case R.id.card2:
                checkChanges(1);
                break;
            case R.id.cardAdd:
                dismiss();
                break;
        }
    }

    /**
     * 改变选中
     */
    private void checkChanges(int index) {
        for (int i = 0; i < 2; i++) {
            if (i != index) {
                checks.get(i).setVisibility(View.GONE);
            }
        }
        payWay = index;
        checks.get(index).setVisibility(View.VISIBLE);
        dismiss();
    }
}