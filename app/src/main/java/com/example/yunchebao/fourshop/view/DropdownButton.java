package com.example.yunchebao.fourshop.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.yunchebao.R;


public class DropdownButton extends RelativeLayout {
    TextView textView;
    ImageView iv_logo;

    public DropdownButton(Context context) {
        this(context, null);
    }

    public DropdownButton(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DropdownButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        View view =  LayoutInflater.from(getContext()).inflate(R.layout.dropdown_tab_button,this, true);
        textView = (TextView) view.findViewById(R.id.textView);
        iv_logo=(ImageView) view.findViewById(R.id.iv_logo);
    }


    public void setText(CharSequence text) {
        textView.setText(text);
    }

    public void setChecked(boolean checked) {
        if (checked) {
            iv_logo.setImageResource(R.drawable.ic_dropdown_actived);
            textView.setTextColor(getResources().getColor(R.color.green));
        } else {
            iv_logo.setImageResource(R.drawable.ic_dropdown_normal);
            textView.setTextColor(getResources().getColor(R.color.font_black_content));

        }
        //textView.setCompoundDrawablesWithIntrinsicBounds(null, null, icon, null);
    }


}
