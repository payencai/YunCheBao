package com.cheyibao.view;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.common.ResourceUtils;
import com.example.yunchebao.R;

import static android.widget.ListPopupWindow.MATCH_PARENT;
import static android.widget.ListPopupWindow.WRAP_CONTENT;

public class MaxDistancePopwindow {
    private Context context;
    private EditText maxDistanceEt;
    private RadioGroup radioGroup;
    private RadioButton oneKmRadioButton;
    private RadioButton twoKmRadioButton;
    private RadioButton threeKmRadioButton;
    private RadioButton fiveKmRadioButton;
    private RadioButton sevenKmRadioButton;
    private RadioButton tenKmRadioButton;
    private View confirmCardView;
    private PopupWindow popupWindow;

    private View view;

    private int distance;


    public MaxDistancePopwindow(View view){
        this(view.getContext());
        this.context = view.getContext();
        this.view = view;
    }

    public Drawable drawable(Context context){
        GradientDrawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setShape(GradientDrawable.RECTANGLE);
        gradientDrawable.setColor(ResourceUtils.getColorByResource(context,R.color.white));
        gradientDrawable.setCornerRadius(4);
        gradientDrawable.setStroke(2,ResourceUtils.getColorByResource(context,R.color.yellow_65));
        return gradientDrawable;
    }

    public Drawable getStatusDrawable(){
        StateListDrawable stateListDrawable = new StateListDrawable();
        stateListDrawable.addState(new int[]{android.R.attr.state_checked},drawable(context));
        stateListDrawable.addState(new int[]{}, new ColorDrawable(ResourceUtils.getColorByResource(context,R.color.transparent)));
        return stateListDrawable;
    }

    public MaxDistancePopwindow(Context context){
        this.context = context;
        View inflate= LayoutInflater.from(context).inflate(R.layout.pop_window_max_distance_view,null,false);
        popupWindow =new PopupWindow(inflate, MATCH_PARENT, WRAP_CONTENT, true);
        popupWindow.setBackgroundDrawable(new ColorDrawable(ResourceUtils.getColorByResource(context,R.color.white)));

        maxDistanceEt=inflate.findViewById(R.id.max_distance_et);
        maxDistanceEt.setBackground(drawable(context));

        radioGroup = inflate.findViewById(R.id.radio_group);

        oneKmRadioButton=inflate.findViewById(R.id.one_km_view);
        oneKmRadioButton.setBackground(getStatusDrawable());
        twoKmRadioButton=inflate.findViewById(R.id.two_km_view);
        twoKmRadioButton.setBackground(getStatusDrawable());
        threeKmRadioButton=inflate.findViewById(R.id.three_km_view);
        threeKmRadioButton.setBackground(getStatusDrawable());
        fiveKmRadioButton=inflate.findViewById(R.id.five_km_view);
        fiveKmRadioButton.setBackground(getStatusDrawable());
        sevenKmRadioButton=inflate.findViewById(R.id.seven_km_view);
        sevenKmRadioButton.setBackground(getStatusDrawable());
        tenKmRadioButton=inflate.findViewById(R.id.ten_km_view);
        tenKmRadioButton.setBackground(getStatusDrawable());


        confirmCardView=inflate.findViewById(R.id.confirm_card_view);

        radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            switch (checkedId){
                case R.id.one_km_view:
                    distance = 1;
                    break;
                case R.id.two_km_view:
                    distance = 2;
                    break;
                case R.id.three_km_view:
                    distance = 3;
                    break;
                case R.id.five_km_view:
                    distance = 51;
                    break;
                case R.id.seven_km_view:
                    distance = 7;
                    break;
                case R.id.ten_km_view:
                    distance = 10;
                    break;
            }
        });

        maxDistanceEt.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus){
                radioGroup.clearCheck();
            }
        });

        popupWindow.setOnDismissListener(() -> {
            if (this.view!=null){
                setBackgroundAlpha(0);
            }else {
                setBackgroundAlpha(1.0f);
            }
        } );
    }


    public int getMaxDistance(){
        String text = maxDistanceEt.getText().toString();
        if (!TextUtils.isEmpty(text)){
            distance = Integer.parseInt(text);
        }else {
            int checkedId = radioGroup.getCheckedRadioButtonId();
            distance = 0;
            switch (checkedId){
                case R.id.one_km_view:
                    distance = 1;
                    break;
                case R.id.two_km_view:
                    distance = 2;
                    break;
                case R.id.three_km_view:
                    distance = 3;
                    break;
                case R.id.five_km_view:
                    distance = 51;
                    break;
                case R.id.seven_km_view:
                    distance = 7;
                    break;
                case R.id.ten_km_view:
                    distance = 10;
                    break;
            }
        }
        return distance;
    }

    public void showAsDropDown(View view){
        popupWindow.showAsDropDown(view);
        if (this.view!=null){
            setBackgroundAlpha(126);
        }else {
            setBackgroundAlpha(0.5f);
        }
    }

    public void showAsDropDown(View anchor, int xoff, int yoff) {
        popupWindow.showAsDropDown(anchor,xoff,yoff);
        if (this.view!=null){
            setBackgroundAlpha(126);
        }else {
            setBackgroundAlpha(0.5f);
        }
    }

    public void showAsDropDown(View anchor, int xoff, int yoff, int gravity) {
        popupWindow.showAsDropDown(anchor,xoff,yoff,gravity);
        if (this.view!=null){
            setBackgroundAlpha(126);
        }else {
            setBackgroundAlpha(0.5f);
        }
    }

    /**
     * 设置添加屏幕的背景透明度
     *
     * @param bgAlpha
     *            屏幕透明度0.0-1.0 1表示完全不透明
     */
    private void setBackgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = ((Activity) context).getWindow()
                .getAttributes();
        lp.alpha = bgAlpha;
        ((Activity) context).getWindow().setAttributes(lp);
    }

    /**
     * 设置添加屏幕的背景透明度
     *
     * @param bgAlpha
     *            屏幕透明度0-255 255表示完全不透明
     */
    private void setBackgroundAlpha(int bgAlpha) {
        Drawable drawable =  this.view.getBackground();
        if (drawable==null){
            drawable = new ColorDrawable(ResourceUtils.getColorByResource(context,R.color.black));
            this.view.setBackground(drawable);
        }
        this.view.getBackground().setAlpha(bgAlpha);
    }

    public void setConfirmListener(View.OnClickListener listener){
        confirmCardView.setOnClickListener(listener);
    }

    public void dismiss(){
        popupWindow.dismiss();
    }

}
