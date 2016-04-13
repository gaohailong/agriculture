package com.example.administrator.myapplication;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.CornerPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.widget.LinearLayout;

public class ViewPagerIndicator extends LinearLayout {
    private Paint mPaint;
    private Path mPath;
    private int mTriangleWidth;
    private int mTriangleHeight;
    private static final float RADIO_TRIANGLE_WIDTH = 1 / 6F;//三角形的底边宽度
    private int mInitTranslationX;//三角形的偏移位置（第一个tab中间）
    private int mTranslationX;//三角形和手指一起移动（三角形移动的位置）

    public ViewPagerIndicator(Context context) {
        this(context, null);
    }

    public ViewPagerIndicator(Context context, AttributeSet attrs) {
        super(context, attrs);
        //初始化画笔
        mPaint = new Paint();
        mPaint.setAntiAlias(true);//图像边缘相对清晰一点
        mPaint.setColor(Color.parseColor("#ffffff"));
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setPathEffect(new CornerPathEffect(3));
    }

    /**
     * 绘制三角形
     *
     * @param canvas
     */
    @Override
    protected void dispatchDraw(Canvas canvas) {
        canvas.save();
        canvas.translate(mInitTranslationX + mTranslationX, getHeight() + 2);
        canvas.drawPath(mPath, mPaint);
        canvas.restore();
        super.dispatchDraw(canvas);
    }

    /**
     * 设置三角形大小（控件大小变化时都会回调这个方法）总单位是tab
     *
     * @param w
     * @param h
     * @param oldw
     * @param oldh
     */
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mTriangleWidth = (int) (w / 3 * RADIO_TRIANGLE_WIDTH);//三角形的底边宽度
        mInitTranslationX = w / 3 / 2 - mTriangleWidth / 2;
        intiTriangle();
    }

    /**
     * 初始化三角形
     * 下 +、上-
     */
    private void intiTriangle() {
        mTriangleHeight = mTriangleWidth / 2;
        mPath = new Path();
        mPath.moveTo(0, 0);
        mPath.lineTo(mTriangleWidth, 0);
        mPath.lineTo(mTriangleWidth / 2, -mTriangleHeight);
        mPath.close();
    }

    /**
     * 指示器跟随手指进行滚动
     *
     * @param position
     * @param positionOffset
     */
    public void scroll(int position, float positionOffset) {
        int tabWidth = getWidth() / 3;
        mTranslationX = (int) (tabWidth * (positionOffset + position));
        invalidate();
    }
}
