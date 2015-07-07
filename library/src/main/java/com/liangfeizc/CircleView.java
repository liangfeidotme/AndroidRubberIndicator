package com.liangfeizc;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by liangfeizc on 6/26/15.
 */
public class CircleView extends View {
    private static final int DEFAULT_COLOR = Color.BLACK;

    private int mColor;
    private Paint mPaint;
    private float mRadius;
    private float mCenterX;
    private float mCenterY;

    public CircleView(Context context) {
        this(context, null);
    }

    public CircleView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CircleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.CircleView);
        try {
            mColor = a.getColor(R.styleable.CircleView_cv_color, DEFAULT_COLOR);
        } finally {
            a.recycle();
        }
        init();
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(mColor);
        mPaint.setStrokeWidth(1);
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawCircle(mCenterX, mCenterY, mRadius - 5, mPaint);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        mCenterX = (right - left) / 2;
        mCenterY = (bottom - top) / 2;

        mRadius = Math.min(mCenterX, mCenterY);
    }

    public void setColor(final int colorValue) {
        mPaint.setColor(colorValue);
        invalidate();
    }

    public PointF getCenter() {
        return new PointF(getX() + getWidth() / 2, getY() + getHeight() / 2);
    }

    public void setCenter(final PointF center) {
        setCenter(center.x, center.y);
    }

    public void setCenter(float cx, float cy) {
        setX(cx - getWidth() / 2);
        setY(cy - getHeight() / 2);
    }

    public void setRadius(final float radius) {
        mRadius = radius;
        invalidate();
    }

    public float getRadius() {
        return mRadius;
    }
}
