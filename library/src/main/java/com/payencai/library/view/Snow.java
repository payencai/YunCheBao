package com.payencai.library.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.payencai.library.view.entity.SnowFlake;

public class Snow extends SurfaceView implements SurfaceHolder.Callback {
    private SurfaceHolder mSurfaceHolder;
    private SnowFlake[] mSnowFlakes;
    private int mViewWidth = 200;
    private int mViewHeight = 100;
    private int mFlakeCount = 20;
    private int mMinSize = 50;
    private int mMaxSize = 70;
    private int mSpeedX = 10;
    private int mSpeedY = 20;
    private Bitmap mSnowBitmap = null;
    private boolean mStart = false;

    public Snow(Context context) {
        this(context, null);
    }

    public Snow(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public Snow(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {

    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {

    }
}
