package com.payencai.library.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import com.payencai.library.R;

public class BubbleTextView extends TextView {


    private Context context;
    private Paint paint;//基础画笔

    private int bubbleArrowWidth;//气泡箭头的宽度
    private int bubbleArrowHeight;//气泡箭头的高度
    private boolean bubbleArrowWhetherCenterVertical;//气泡箭头是否居中
    private int bubbleRadius;//气泡的圆角角度
    private String bubbleDirection;//气泡的方向
    private int bubbleMarginTop;//气泡的如果不居中的话距离顶部的距离
    private int bubbleMarginLeft;//气泡的如果不居中的话距离左边的距离
    private int bubbleStrokeWidth;//气泡边框的宽度
    private int bubbleStrokeColor;//气泡边框的颜色
    private int bubbleBackGround;//气泡背景颜色

    private final String BUBBLE_DIRECTION_LEFT = "0";
    private final String BUBBLE_DIRECTION_TOP = "1";
    private final String BUBBLE_DIRECTION_RIGHT = "2";
    private final String BUBBLE_DIRECTION_BOTTOM = "3";

    private float[] radiusArray = { 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f };//圆角半径集合

    public BubbleTextView(Context context) {
        this(context,null);
    }

    public BubbleTextView(Context context, AttributeSet attrs) {
        this(context,attrs,0);
    }

    public BubbleTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        this.context = context;

        TypedArray attributes = context.obtainStyledAttributes(attrs, R.styleable.bubble);
        this.bubbleArrowWidth = attributes.getDimensionPixelOffset(R.styleable.bubble_bubbleArrowWidth,dip2px(10));
        this.bubbleArrowHeight = attributes.getDimensionPixelOffset(R.styleable.bubble_bubbleArrowHeight,dip2px(10));
        this.bubbleArrowWhetherCenterVertical = attributes.getBoolean(R.styleable.bubble_bubbleArrowWhetherCenterVertical,true);
        this.bubbleRadius = attributes.getDimensionPixelOffset(R.styleable.bubble_bubbleRadius,dip2px(8));
        this.bubbleMarginTop = attributes.getDimensionPixelOffset(R.styleable.bubble_bubbleMarginTop,dip2px(10));
        this.bubbleMarginLeft = attributes.getDimensionPixelOffset(R.styleable.bubble_bubbleMarginLeft,dip2px(10));
        this.bubbleStrokeWidth = attributes.getDimensionPixelOffset(R.styleable.bubble_bubbleStrokeWidth,0);
        this.bubbleStrokeColor = attributes.getColor(R.styleable.bubble_bubbleStrokeColor, Color.RED);
        this.bubbleBackGround = attributes.getColor(R.styleable.bubble_bubbleBackGround, Color.parseColor("#00ffffff"));
        this.bubbleDirection = attributes.getString(R.styleable.bubble_bubbleDirection);
        if(this.bubbleDirection == null || "".equals(this.bubbleDirection)){
            this.bubbleDirection = BUBBLE_DIRECTION_LEFT;
        }

        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL);
        paint.setStrokeWidth(bubbleStrokeWidth);
        paint.setColor(bubbleStrokeColor);
        //设置系统背景为空
        setBackground(null);
        int newPaddingLeft = getPaddingLeft();
        int newPaddingTop = getPaddingTop();
        int newPaddingRight = getPaddingRight();
        int newPaddingBottom = getPaddingBottom();
        //设置偏移量
        switch (this.bubbleDirection) {
            case BUBBLE_DIRECTION_LEFT:
                newPaddingLeft += bubbleArrowWidth + bubbleStrokeWidth;
                newPaddingTop += bubbleStrokeWidth;
                newPaddingRight += bubbleStrokeWidth;
                newPaddingBottom += bubbleStrokeWidth;
                break;
            case BUBBLE_DIRECTION_TOP:
                newPaddingLeft += bubbleStrokeWidth;
                newPaddingTop += bubbleArrowHeight + bubbleStrokeWidth;
                newPaddingRight += bubbleStrokeWidth;
                newPaddingBottom += bubbleStrokeWidth;
                break;
            case BUBBLE_DIRECTION_RIGHT:
                newPaddingLeft += bubbleStrokeWidth;
                newPaddingTop += bubbleStrokeWidth;
                newPaddingRight += bubbleArrowWidth + bubbleStrokeWidth;
                newPaddingBottom += bubbleStrokeWidth;
                break;
            case BUBBLE_DIRECTION_BOTTOM:
                newPaddingLeft += bubbleStrokeWidth;
                newPaddingTop += bubbleStrokeWidth;
                newPaddingRight += bubbleStrokeWidth;
                newPaddingBottom += bubbleArrowHeight + bubbleStrokeWidth;
                break;
        }

        setPadding(newPaddingLeft,newPaddingTop,newPaddingRight,newPaddingBottom);

    }


    /**
     * 将dip或dp值转换为px值，保证尺寸大小不变
     *
     * @param dipValue
     * @return
     */
    public int dip2px(float dipValue) {
        if (context == null) {
            context = getContext();
        }
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //绘制底图范围
        canvas.drawBitmap(initBubbleBackGroundBitmap(),0,0,paint);
        super.onDraw(canvas);
        if(bubbleStrokeWidth != 0) {
            //绘制顶图边框
            canvas.drawBitmap(initBubbleStrokeBitmap(), 0, 0, null);
        }

    }


    /**
     * 初始化底图位图
     * @return
     */
    private Bitmap initBubbleBackGroundBitmap(){
        int paddingLeft = 0;
        int paddingTop = 0;
        int paddingRight = 0;
        int paddingBottopm = 0;
        int viewWidth = getMeasuredWidth();
        int viewHeight = getMeasuredHeight();
        Bitmap bubbleBackGroundBitmap = Bitmap.createBitmap(viewWidth ,viewHeight,Bitmap.Config.ARGB_8888);
        Canvas bubbleBackGroundCanvas = new Canvas(bubbleBackGroundBitmap);
        Paint bubbleBackGroundPaint = new Paint();
        bubbleBackGroundPaint.setAntiAlias(true);
        bubbleBackGroundPaint.setStyle(Paint.Style.FILL);
        bubbleBackGroundPaint.setColor(bubbleBackGround);
        Path bubbleBackGroundPath = new Path();

        int nowBubbleArrowX = 0;//箭头的尖的坐标
        int nowBubbleArrowY = 0;//箭头的尖的坐标
        switch (this.bubbleDirection) {
            case BUBBLE_DIRECTION_LEFT:
                if(bubbleArrowWhetherCenterVertical){
                    nowBubbleArrowX = paddingLeft + bubbleStrokeWidth / 2;
                    nowBubbleArrowY = (viewHeight - paddingTop - paddingBottopm) / 2 + paddingTop;
                }else {
                    nowBubbleArrowX = paddingLeft + bubbleStrokeWidth / 2;
                    nowBubbleArrowY = bubbleMarginTop + paddingTop;
                }
                //绘制实心箭头
                bubbleBackGroundPath.moveTo(nowBubbleArrowX + bubbleArrowWidth,nowBubbleArrowY - bubbleArrowHeight / 2);
                bubbleBackGroundPath.lineTo(nowBubbleArrowX,nowBubbleArrowY);
                bubbleBackGroundPath.lineTo(nowBubbleArrowX + bubbleArrowWidth,nowBubbleArrowY + bubbleArrowHeight / 2);
                //绘制圆角矩形
                bubbleBackGroundPath.addRoundRect(new RectF(nowBubbleArrowX + bubbleArrowWidth
                                ,bubbleStrokeWidth / 2 + paddingTop
                                ,viewWidth - bubbleStrokeWidth / 2 - paddingRight
                                ,viewHeight - bubbleStrokeWidth / 2 - paddingBottopm)
                        ,bubbleRadius,bubbleRadius, Path.Direction.CCW);
                //绘制到画布
                bubbleBackGroundCanvas.drawPath(bubbleBackGroundPath,bubbleBackGroundPaint);
                break;
            case BUBBLE_DIRECTION_TOP:
                if(bubbleArrowWhetherCenterVertical){
                    nowBubbleArrowX = (viewWidth - paddingLeft - paddingRight) / 2 + paddingLeft;
                    nowBubbleArrowY = paddingTop + bubbleStrokeWidth / 2;
                }else {
                    nowBubbleArrowX = paddingLeft + bubbleMarginLeft;
                    nowBubbleArrowY = paddingTop + bubbleStrokeWidth / 2;
                }
                //绘制实心箭头
                bubbleBackGroundPath.moveTo(nowBubbleArrowX + bubbleArrowWidth / 2,nowBubbleArrowY + bubbleArrowHeight);
                bubbleBackGroundPath.lineTo(nowBubbleArrowX,nowBubbleArrowY);
                bubbleBackGroundPath.lineTo(nowBubbleArrowX - bubbleArrowWidth / 2,nowBubbleArrowY + bubbleArrowHeight);
                //绘制圆角矩形
                bubbleBackGroundPath.addRoundRect(new RectF(paddingLeft + bubbleStrokeWidth / 2
                                ,nowBubbleArrowY + bubbleArrowHeight
                                ,viewWidth - bubbleStrokeWidth / 2 - paddingRight
                                ,viewHeight - bubbleStrokeWidth / 2 - paddingBottopm)
                        ,bubbleRadius,bubbleRadius, Path.Direction.CCW);
                //绘制到画布
                bubbleBackGroundCanvas.drawPath(bubbleBackGroundPath,bubbleBackGroundPaint);
                break;
            case BUBBLE_DIRECTION_RIGHT:
                if(bubbleArrowWhetherCenterVertical){
                    nowBubbleArrowX = viewWidth - paddingRight - bubbleStrokeWidth / 2;
                    nowBubbleArrowY = (viewHeight - paddingTop - paddingBottopm) / 2 + paddingTop;
                }else {
                    nowBubbleArrowX = viewWidth - paddingRight - bubbleStrokeWidth / 2;
                    nowBubbleArrowY = bubbleMarginTop + paddingTop;
                }
                //绘制实心箭头
                bubbleBackGroundPath.moveTo(nowBubbleArrowX - bubbleArrowWidth,nowBubbleArrowY - bubbleArrowHeight / 2);
                bubbleBackGroundPath.lineTo(nowBubbleArrowX,nowBubbleArrowY);
                bubbleBackGroundPath.lineTo(nowBubbleArrowX - bubbleArrowWidth,nowBubbleArrowY + bubbleArrowHeight / 2);
                //绘制圆角矩形
                bubbleBackGroundPath.addRoundRect(new RectF(paddingLeft + bubbleStrokeWidth / 2
                                ,bubbleStrokeWidth / 2 + paddingTop
                                ,nowBubbleArrowX - bubbleArrowWidth
                                ,viewHeight - bubbleStrokeWidth / 2 - paddingBottopm)
                        ,bubbleRadius,bubbleRadius, Path.Direction.CCW);
                //绘制到画布
                bubbleBackGroundCanvas.drawPath(bubbleBackGroundPath,bubbleBackGroundPaint);
                break;
            case BUBBLE_DIRECTION_BOTTOM:
                if(bubbleArrowWhetherCenterVertical){
                    nowBubbleArrowX = (viewWidth - paddingLeft - paddingRight) / 2 + paddingLeft;
                    nowBubbleArrowY = viewHeight - paddingBottopm - bubbleStrokeWidth / 2;
                }else {
                    nowBubbleArrowX = paddingLeft + bubbleMarginLeft;
                    nowBubbleArrowY = viewHeight - paddingBottopm - bubbleStrokeWidth / 2;
                }
                //绘制实心箭头
                bubbleBackGroundPath.moveTo(nowBubbleArrowX + bubbleArrowWidth / 2,nowBubbleArrowY - bubbleArrowHeight);
                bubbleBackGroundPath.lineTo(nowBubbleArrowX,nowBubbleArrowY);
                bubbleBackGroundPath.lineTo(nowBubbleArrowX - bubbleArrowWidth / 2,nowBubbleArrowY - bubbleArrowHeight);
                //绘制圆角矩形
                bubbleBackGroundPath.addRoundRect(new RectF(paddingLeft + bubbleStrokeWidth / 2
                                ,paddingTop + bubbleStrokeWidth / 2
                                ,viewWidth - bubbleStrokeWidth / 2 - paddingRight
                                ,nowBubbleArrowY - bubbleArrowHeight)
                        ,bubbleRadius,bubbleRadius, Path.Direction.CCW);
                //绘制到画布
                bubbleBackGroundCanvas.drawPath(bubbleBackGroundPath,bubbleBackGroundPaint);
                break;
        }

        return bubbleBackGroundBitmap;

    }

    /**
     * 流程：先确定方向--》确定箭头尖部的坐标--》确定箭头三个点的位置坐标---》绘制圆角矩形
     * @return
     */
    private Bitmap initBubbleStrokeBitmap(){
        int paddingLeft = 0;
        int paddingTop = 0;
        int paddingRight = 0;
        int paddingBottopm = 0;
        int viewWidth = getMeasuredWidth();
        int viewHeight = getMeasuredHeight();
        Bitmap bubbleStrokeBitmap = Bitmap.createBitmap(viewWidth ,viewHeight,Bitmap.Config.ARGB_8888);
        Canvas bubbleStrokeCanvas = new Canvas(bubbleStrokeBitmap);
        Paint bubbleStrokePaint = new Paint();
        bubbleStrokePaint.setAntiAlias(true);
        bubbleStrokePaint.setStyle(Paint.Style.STROKE);
        bubbleStrokePaint.setColor(bubbleStrokeColor);
        bubbleStrokePaint.setStrokeWidth(bubbleStrokeWidth);
        Path bubbleStrokePath = new Path();
        Paint bubbleStrokeOverlayPaint;
        Path bubbleStrokeOverlayPath;

        int nowBubbleArrowX = 0;//箭头的尖的坐标
        int nowBubbleArrowY = 0;//箭头的尖的坐标
        switch (this.bubbleDirection) {
            case BUBBLE_DIRECTION_LEFT:
                if(bubbleArrowWhetherCenterVertical){
                    nowBubbleArrowX = paddingLeft + bubbleStrokeWidth / 2;
                    nowBubbleArrowY = (viewHeight - paddingTop - paddingBottopm) / 2 + paddingTop;
                }else {
                    nowBubbleArrowX = paddingLeft + bubbleStrokeWidth / 2;
                    nowBubbleArrowY = bubbleMarginTop + paddingTop;
                }
                //绘制实心箭头
                bubbleStrokePath.moveTo(nowBubbleArrowX + bubbleArrowWidth,nowBubbleArrowY - bubbleArrowHeight / 2);
                bubbleStrokePath.lineTo(nowBubbleArrowX,nowBubbleArrowY);
                bubbleStrokePath.lineTo(nowBubbleArrowX + bubbleArrowWidth,nowBubbleArrowY + bubbleArrowHeight / 2);
                bubbleStrokePaint.setStyle(Paint.Style.FILL);
                bubbleStrokeCanvas.drawPath(bubbleStrokePath,bubbleStrokePaint);
                //绘制空心圆角矩形
                bubbleStrokePaint.setStyle(Paint.Style.STROKE);
                bubbleStrokePath.addRoundRect(new RectF(nowBubbleArrowX + bubbleArrowWidth
                                ,bubbleStrokeWidth / 2 + paddingTop
                                ,viewWidth - bubbleStrokeWidth / 2 - paddingRight
                                ,viewHeight - bubbleStrokeWidth / 2 - paddingBottopm)
                        ,bubbleRadius,bubbleRadius, Path.Direction.CCW);
                //绘制到画布
                bubbleStrokeCanvas.drawPath(bubbleStrokePath,bubbleStrokePaint);
                bubbleStrokeCanvas.save();
                //绘制覆盖掉箭头的封闭线的实心三角
                bubbleStrokeOverlayPaint = new Paint(bubbleStrokePaint);
                bubbleStrokeOverlayPaint.setStyle(Paint.Style.FILL);
                bubbleStrokeOverlayPaint.setColor(Color.parseColor("#00ffffff"));//使用透明的颜色可以使范围设置的稍微大一些，防止出现2.5dp这种情况
                bubbleStrokeOverlayPaint.setStrokeWidth(1);
                bubbleStrokeOverlayPaint.setAntiAlias(true);
                bubbleStrokeOverlayPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_OUT));//设置覆盖物的显示方式
                bubbleStrokeOverlayPath = new Path();
                bubbleStrokeOverlayPath.moveTo(nowBubbleArrowX + bubbleArrowWidth + bubbleStrokeWidth,nowBubbleArrowY - bubbleArrowHeight / 2);
                bubbleStrokeOverlayPath.lineTo(nowBubbleArrowX + bubbleStrokeWidth,nowBubbleArrowY);
                bubbleStrokeOverlayPath.lineTo(nowBubbleArrowX + bubbleArrowWidth + bubbleStrokeWidth,nowBubbleArrowY + bubbleArrowHeight / 2);
                bubbleStrokeOverlayPath.close();
                //绘制进行覆盖显示
                bubbleStrokeCanvas.drawPath(bubbleStrokeOverlayPath,bubbleStrokeOverlayPaint);
                bubbleStrokeCanvas.restore();
                break;
            case BUBBLE_DIRECTION_TOP:
                if(bubbleArrowWhetherCenterVertical){
                    nowBubbleArrowX = (viewWidth - paddingLeft - paddingRight) / 2 + paddingLeft;
                    nowBubbleArrowY = paddingTop + bubbleStrokeWidth / 2;
                }else {
                    nowBubbleArrowX = paddingLeft + bubbleMarginLeft;
                    nowBubbleArrowY = paddingTop + bubbleStrokeWidth / 2;
                }
                //绘制实心箭头
                bubbleStrokePath.moveTo(nowBubbleArrowX + bubbleArrowWidth / 2,nowBubbleArrowY + bubbleArrowHeight);
                bubbleStrokePath.lineTo(nowBubbleArrowX,nowBubbleArrowY);
                bubbleStrokePath.lineTo(nowBubbleArrowX - bubbleArrowWidth / 2,nowBubbleArrowY + bubbleArrowHeight);
                bubbleStrokePaint.setStyle(Paint.Style.FILL);
                bubbleStrokeCanvas.drawPath(bubbleStrokePath,bubbleStrokePaint);
                //绘制空心圆角矩形
                bubbleStrokePaint.setStyle(Paint.Style.STROKE);
                bubbleStrokePath.addRoundRect(new RectF(paddingLeft + bubbleStrokeWidth / 2
                                ,nowBubbleArrowY + bubbleArrowHeight
                                ,viewWidth - bubbleStrokeWidth / 2 - paddingRight
                                ,viewHeight - bubbleStrokeWidth / 2 - paddingBottopm)
                        ,bubbleRadius,bubbleRadius, Path.Direction.CCW);
                //绘制到画布
                bubbleStrokeCanvas.drawPath(bubbleStrokePath,bubbleStrokePaint);
                bubbleStrokeCanvas.save();
                //绘制覆盖掉箭头的封闭线的实心三角
                bubbleStrokeOverlayPaint = new Paint(bubbleStrokePaint);
                bubbleStrokeOverlayPaint.setStyle(Paint.Style.FILL);
                bubbleStrokeOverlayPaint.setColor(Color.parseColor("#00ffffff"));//使用透明的颜色可以使范围设置的稍微大一些，防止出现2.5dp这种情况
                bubbleStrokeOverlayPaint.setStrokeWidth(1);
                bubbleStrokeOverlayPaint.setAntiAlias(true);
                bubbleStrokeOverlayPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_OUT));//设置覆盖物的显示方式
                bubbleStrokeOverlayPath = new Path();
                bubbleStrokeOverlayPath.moveTo(nowBubbleArrowX + bubbleArrowWidth / 2,nowBubbleArrowY + bubbleArrowHeight + bubbleStrokeWidth);
                bubbleStrokeOverlayPath.lineTo(nowBubbleArrowX,nowBubbleArrowY + bubbleStrokeWidth);
                bubbleStrokeOverlayPath.lineTo(nowBubbleArrowX - bubbleArrowWidth / 2,nowBubbleArrowY + bubbleArrowHeight + bubbleStrokeWidth);
                bubbleStrokeOverlayPath.close();
                //绘制进行覆盖显示
                bubbleStrokeCanvas.drawPath(bubbleStrokeOverlayPath,bubbleStrokeOverlayPaint);
                bubbleStrokeCanvas.restore();
                break;
            case BUBBLE_DIRECTION_RIGHT:
                if(bubbleArrowWhetherCenterVertical){
                    nowBubbleArrowX = viewWidth - paddingRight - bubbleStrokeWidth / 2;
                    nowBubbleArrowY = (viewHeight - paddingTop - paddingBottopm) / 2 + paddingTop;
                }else {
                    nowBubbleArrowX = viewWidth - paddingRight - bubbleStrokeWidth / 2;
                    nowBubbleArrowY = bubbleMarginTop + paddingTop;
                }
                //绘制实心箭头
                bubbleStrokePath.moveTo(nowBubbleArrowX - bubbleArrowWidth,nowBubbleArrowY - bubbleArrowHeight / 2);
                bubbleStrokePath.lineTo(nowBubbleArrowX,nowBubbleArrowY);
                bubbleStrokePath.lineTo(nowBubbleArrowX - bubbleArrowWidth,nowBubbleArrowY + bubbleArrowHeight / 2);
                bubbleStrokePaint.setStyle(Paint.Style.FILL);
                bubbleStrokeCanvas.drawPath(bubbleStrokePath,bubbleStrokePaint);
                //绘制空心圆角矩形
                bubbleStrokePaint.setStyle(Paint.Style.STROKE);
                bubbleStrokePath.addRoundRect(new RectF(paddingLeft + bubbleStrokeWidth / 2
                                ,bubbleStrokeWidth / 2 + paddingTop
                                ,nowBubbleArrowX - bubbleArrowWidth
                                ,viewHeight - bubbleStrokeWidth / 2 - paddingBottopm)
                        ,bubbleRadius,bubbleRadius, Path.Direction.CCW);
                //绘制到画布
                bubbleStrokeCanvas.drawPath(bubbleStrokePath,bubbleStrokePaint);
                bubbleStrokeCanvas.save();
                //绘制覆盖掉箭头的封闭线的实心三角
                bubbleStrokeOverlayPaint = new Paint(bubbleStrokePaint);
                bubbleStrokeOverlayPaint.setStyle(Paint.Style.FILL);
                bubbleStrokeOverlayPaint.setColor(Color.parseColor("#00ffffff"));//使用透明的颜色可以使范围设置的稍微大一些，防止出现2.5dp这种情况
                bubbleStrokeOverlayPaint.setStrokeWidth(1);
                bubbleStrokeOverlayPaint.setAntiAlias(true);
                bubbleStrokeOverlayPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_OUT));//设置覆盖物的显示方式
                bubbleStrokeOverlayPath = new Path();
                bubbleStrokeOverlayPath.moveTo(nowBubbleArrowX - bubbleArrowWidth - bubbleStrokeWidth ,nowBubbleArrowY - bubbleArrowHeight / 2);
                bubbleStrokeOverlayPath.lineTo(nowBubbleArrowX - bubbleStrokeWidth,nowBubbleArrowY);
                bubbleStrokeOverlayPath.lineTo(nowBubbleArrowX - bubbleArrowWidth - bubbleStrokeWidth,nowBubbleArrowY + bubbleArrowHeight / 2);
                bubbleStrokeOverlayPath.close();
                //绘制进行覆盖显示
                bubbleStrokeCanvas.drawPath(bubbleStrokeOverlayPath,bubbleStrokeOverlayPaint);
                bubbleStrokeCanvas.restore();
                break;
            case BUBBLE_DIRECTION_BOTTOM:
                if(bubbleArrowWhetherCenterVertical){
                    nowBubbleArrowX = (viewWidth - paddingLeft - paddingRight) / 2 + paddingLeft;
                    nowBubbleArrowY = viewHeight - paddingBottopm - bubbleStrokeWidth / 2;
                }else {
                    nowBubbleArrowX = paddingLeft + bubbleMarginLeft;
                    nowBubbleArrowY = viewHeight - paddingBottopm - bubbleStrokeWidth / 2;
                }
                //绘制实心箭头
                bubbleStrokePath.moveTo(nowBubbleArrowX + bubbleArrowWidth / 2,nowBubbleArrowY - bubbleArrowHeight);
                bubbleStrokePath.lineTo(nowBubbleArrowX,nowBubbleArrowY);
                bubbleStrokePath.lineTo(nowBubbleArrowX - bubbleArrowWidth / 2,nowBubbleArrowY - bubbleArrowHeight);
                bubbleStrokePaint.setStyle(Paint.Style.FILL);
                bubbleStrokeCanvas.drawPath(bubbleStrokePath,bubbleStrokePaint);
                //绘制空心圆角矩形
                bubbleStrokePaint.setStyle(Paint.Style.STROKE);
                bubbleStrokePath.addRoundRect(new RectF(paddingLeft + bubbleStrokeWidth / 2
                                ,paddingTop + bubbleStrokeWidth / 2
                                ,viewWidth - bubbleStrokeWidth / 2 - paddingRight
                                ,nowBubbleArrowY - bubbleArrowHeight)
                        ,bubbleRadius,bubbleRadius, Path.Direction.CCW);
                //绘制到画布
                bubbleStrokeCanvas.drawPath(bubbleStrokePath,bubbleStrokePaint);
                bubbleStrokeCanvas.save();
                //绘制覆盖掉箭头的封闭线的实心三角
                bubbleStrokeOverlayPaint = new Paint(bubbleStrokePaint);
                bubbleStrokeOverlayPaint.setStyle(Paint.Style.FILL);
                bubbleStrokeOverlayPaint.setColor(Color.parseColor("#00ffffff"));//使用透明的颜色可以使范围设置的稍微大一些，防止出现2.5dp这种情况
                bubbleStrokeOverlayPaint.setStrokeWidth(1);
                bubbleStrokeOverlayPaint.setAntiAlias(true);
                bubbleStrokeOverlayPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_OUT));//设置覆盖物的显示方式
                bubbleStrokeOverlayPath = new Path();
                bubbleStrokeOverlayPath.moveTo(nowBubbleArrowX + bubbleArrowWidth / 2,nowBubbleArrowY - bubbleArrowHeight - bubbleStrokeWidth);
                bubbleStrokeOverlayPath.lineTo(nowBubbleArrowX,nowBubbleArrowY - bubbleStrokeWidth);
                bubbleStrokeOverlayPath.lineTo(nowBubbleArrowX - bubbleArrowWidth / 2,nowBubbleArrowY - bubbleArrowHeight - bubbleStrokeWidth);
                bubbleStrokeOverlayPath.close();
                //绘制进行覆盖显示
                bubbleStrokeCanvas.drawPath(bubbleStrokeOverlayPath,bubbleStrokeOverlayPaint);
                bubbleStrokeCanvas.restore();
                break;
        }

        return bubbleStrokeBitmap;
    }

    @Override
    public void setBackgroundColor(int color) {
        super.setBackgroundColor(color);
    }
}
