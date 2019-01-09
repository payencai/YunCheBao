package com.payencai.library.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;

import com.payencai.library.R;
import com.payencai.library.util.DensityUtil;

public class CircleImageView extends AppCompatImageView {
    public int radius;
    public CircleImageView(Context context) {
        this(context,null);
    }

    public CircleImageView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public CircleImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray=context.obtainStyledAttributes(attrs, R.styleable.CircleImageView,defStyleAttr,0);
        radius=typedArray.getIndex(R.styleable.CircleImageView_my_radius);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //在draw之前做一些事情
        Path path=new Path();
        int height=getHeight();
        int width=getWidth();
        path.addRoundRect(new RectF(0,0,width,height), DensityUtil.dip2px(getContext(),radius),DensityUtil.dip2px(getContext(),radius),Path.Direction.CW);
        canvas.clipPath(path);
        super.onDraw(canvas);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
    private void setRadius(int radius){
        this.radius=radius;
    }
}
