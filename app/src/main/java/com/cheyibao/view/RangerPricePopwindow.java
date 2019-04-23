package com.cheyibao.view;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.PopupWindow;

import com.common.ResourceUtils;
import com.example.yunchebao.R;

import static android.widget.ListPopupWindow.MATCH_PARENT;
import static android.widget.ListPopupWindow.WRAP_CONTENT;

public class RangerPricePopwindow {
    private EditText minPriceEt;
    private EditText maxPriceEt;
    private View confirmCardView;
    private PopupWindow popupWindow;
    public RangerPricePopwindow(Context context){
        View inflate= LayoutInflater.from(context).inflate(R.layout.pop_window_ranger_price,null,false);
        popupWindow =new PopupWindow(inflate, MATCH_PARENT, WRAP_CONTENT, true);
        popupWindow.setBackgroundDrawable(new ColorDrawable(ResourceUtils.getColorByResource(context,R.color.white)));
        minPriceEt=inflate.findViewById(R.id.min_price_et);
        maxPriceEt=inflate.findViewById(R.id.max_price_et);
        confirmCardView=inflate.findViewById(R.id.confirm_card_view);
        minPriceEt.setBackground(drawable(context));
        maxPriceEt.setBackground(drawable(context));
        confirmCardView.setOnClickListener(v -> popupWindow.dismiss());
    }

    public Drawable drawable(Context context){
        GradientDrawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setShape(GradientDrawable.RECTANGLE);
        gradientDrawable.setColor(ResourceUtils.getColorByResource(context,R.color.white));
        gradientDrawable.setCornerRadius(4);
        gradientDrawable.setStroke(2,ResourceUtils.getColorByResource(context,R.color.yellow_65));
        return gradientDrawable;
    }

    public double getMinPrice(){
        String text = minPriceEt.getText().toString();
        if (TextUtils.isEmpty(text)){
            return 0;
        }
        return Double.parseDouble(text);
    }

    public double getMaxPrice(){
        return Double.parseDouble(maxPriceEt.getText().toString());
    }

    public void showAsDropDown(View view){
        popupWindow.showAsDropDown(view);
    }

    public void showAsDropDown(View anchor, int xoff, int yoff) {
        popupWindow.showAsDropDown(anchor,xoff,yoff);
    }

    public void showAsDropDown(View anchor, int xoff, int yoff, int gravity) {
        popupWindow.showAsDropDown(anchor,xoff,yoff,gravity);
    }

}
