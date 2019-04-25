package com.cheyibao.view;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.PopupWindow;

import com.common.ResourceUtils;
import com.example.yunchebao.R;

import static android.widget.ListPopupWindow.MATCH_PARENT;
import static android.widget.ListPopupWindow.WRAP_CONTENT;

public class RangerPricePopwindow {
    private Context context;
    private EditText minPriceEt;
    private EditText maxPriceEt;
    private View confirmCardView;
    private PopupWindow popupWindow;

    private View view;


    public RangerPricePopwindow(View view){
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

    public RangerPricePopwindow(Context context){
        this.context = context;
        View inflate= LayoutInflater.from(context).inflate(R.layout.pop_window_ranger_price,null,false);
        popupWindow =new PopupWindow(inflate, MATCH_PARENT, WRAP_CONTENT, true);
        popupWindow.setBackgroundDrawable(new ColorDrawable(ResourceUtils.getColorByResource(context,R.color.white)));
        minPriceEt=inflate.findViewById(R.id.min_price_et);
        maxPriceEt=inflate.findViewById(R.id.max_price_et);
        confirmCardView=inflate.findViewById(R.id.confirm_card_view);
        minPriceEt.setBackground(drawable(context));
        maxPriceEt.setBackground(drawable(context));

        popupWindow.setOnDismissListener(() -> {
            if (this.view!=null){
                setBackgroundAlpha(0);
            }else {
                setBackgroundAlpha(1.0f);
            }
        } );
    }

    public double getMinPrice(){
        String text = minPriceEt.getText().toString();
        if (TextUtils.isEmpty(text)){
            return 0;
        }
        return Double.parseDouble(text);
    }

    public double getMaxPrice(){
        String text = maxPriceEt.getText().toString();
        if (TextUtils.isEmpty(text)){
            return 0;
        }
        return Double.parseDouble(text);
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
