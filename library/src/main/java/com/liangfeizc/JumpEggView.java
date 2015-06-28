package com.liangfeizc;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by liangfeizc on 6/26/15.
 */
public class JumpEggView extends View {
    private Paint mInnerCirclePaint;
    private Paint mOuterCirclePaint;

    public JumpEggView(Context context) {
        super(context);
        init();
    }

    public JumpEggView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public JumpEggView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public JumpEggView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        mInnerCirclePaint = new Paint();
        mInnerCirclePaint.setColor(0xFFAF3854);
        mInnerCirclePaint.setStrokeWidth(1);
        mInnerCirclePaint.setStyle(Paint.Style.FILL_AND_STROKE);

        mOuterCirclePaint = new Paint();
        mOuterCirclePaint.setColor(0xFF533456);
        mOuterCirclePaint.setStrokeWidth(1);
        mOuterCirclePaint.setStyle(Paint.Style.FILL_AND_STROKE);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawCircle(getWidth() / 2, getHeight() / 2, Math.min(getWidth(), getHeight()) / 2, mOuterCirclePaint);
        canvas.drawCircle(getWidth() / 2, getHeight() / 2, Math.min(getWidth(), getHeight()) / 2 - 50, mInnerCirclePaint);
    }
}
