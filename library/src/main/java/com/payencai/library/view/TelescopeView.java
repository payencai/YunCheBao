package com.payencai.library.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Shader;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import com.payencai.library.R;

public class TelescopeView extends View {
    private Paint mPaint;
    private Bitmap mBitmap, mBitmapBg;
    private int mTouchX = -1, mTouchY = -1;

    public TelescopeView(Context context) {
        super(context);
        init();
    }

    public TelescopeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public TelescopeView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }
    private void init() {
        mPaint = new Paint();
        mBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.telescope);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mTouchX=(int)event.getX();
                mTouchY=(int)event.getY();//getX是默认坐标系的起点，不是touch的坐标，很容易搞错
                postInvalidate();
                return true;//当手指按下时马上触发重新绘制
            case MotionEvent.ACTION_MOVE:
                mTouchX=(int)event.getX();
                mTouchY=(int)event.getY();
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
//                mTouchX=-1;
//                mTouchY=-1;
                break;
        }
        postInvalidate();
        return super.onTouchEvent(event);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mBitmapBg == null) {
            mBitmapBg = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
            Canvas canvasbg = new Canvas(mBitmapBg);
            canvasbg.drawBitmap(mBitmap, null, new Rect(0, 0, getWidth(), getHeight()), mPaint);
        }
        if (mTouchX != -1 && mTouchY != -1) {
            mPaint.setShader(new BitmapShader(mBitmapBg, Shader.TileMode.REPEAT, Shader.TileMode.REPEAT));
            canvas.drawCircle(mTouchX, mTouchY-200, 200, mPaint);
        }

    }
}
