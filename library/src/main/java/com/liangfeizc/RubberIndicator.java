package com.liangfeizc;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.RectF;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.BounceInterpolator;
import android.widget.LinearLayout;

import java.util.List;

/**
 * Created by liangfeizc on 6/28/15.
 */
public class RubberIndicator extends LinearLayout {
    private static final int DEFAULT_BACKGROUND_COLOR = 0xFF533456;
    private static final int DEFAULT_SMALL_CIRCLE_COLOR = 0xFFDF8D81;
    private static final int DEFAULT_LARGE_CIRCLE_COLOR = 0xFFAF3854;

    private List<CircleView> mCircleViews;
    private int mCount;

    private CircleView mLargeCircle;
    private CircleView mSmallCircle;
    private CircleView mOuterCircle;

    private AnimatorSet mAnim;

    public RubberIndicator(Context context) {
        super(context);
        init();
    }

    public RubberIndicator(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        View rootView = inflate(getContext(), R.layout.rubber_indicator, this);

        mLargeCircle = (CircleView) rootView.findViewById(R.id.large_circle);
        mSmallCircle = (CircleView) rootView.findViewById(R.id.small_circle);
        mOuterCircle = (CircleView) rootView.findViewById(R.id.outer_circle);

        mAnim = new AnimatorSet();
    }

    public void setCount(int count) {
        mCount = count;
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public void pageUp() {
        float smallCircleX = mLargeCircle.getX();
        float largeCircleX = mSmallCircle.getX() + mSmallCircle.getWidth() - mLargeCircle.getWidth();
        float outerCircleX = mOuterCircle.getX() + largeCircleX - mLargeCircle.getX();

        PropertyValuesHolder pvhScaleX = PropertyValuesHolder.ofFloat("scaleX", 1, 0.8f, 1);
        PropertyValuesHolder pvhScaleY = PropertyValuesHolder.ofFloat("scaleY", 1, 0.8f, 1);

        PropertyValuesHolder pvhX = PropertyValuesHolder.ofFloat("x", largeCircleX);
        ObjectAnimator largeCircleAnim = ObjectAnimator.ofPropertyValuesHolder(mLargeCircle, pvhX, pvhScaleX, pvhScaleY);

        pvhX = PropertyValuesHolder.ofFloat("x", outerCircleX);
        ObjectAnimator outerCircleAnim = ObjectAnimator.ofPropertyValuesHolder(mOuterCircle, pvhX, pvhScaleX, pvhScaleY);


        PointF smallCircleCenter = mSmallCircle.getCenter();
        PointF smallCircleEndCenter = new PointF(
                smallCircleCenter.x - (mSmallCircle.getX() - smallCircleX), smallCircleCenter.y);
        float radius = (smallCircleCenter.x - smallCircleEndCenter.x) / 2;
        RectF oval = new RectF(smallCircleEndCenter.x, smallCircleEndCenter.y - radius,
                smallCircleCenter.x, smallCircleEndCenter.y + radius);
        Path motionPath = new Path();
        motionPath.arcTo(oval, 0, 180);
        ObjectAnimator smallCircleAnim = ObjectAnimator.ofObject(mSmallCircle, "center", null, motionPath);
        smallCircleAnim.setInterpolator(new BounceInterpolator());

        PropertyValuesHolder pvhRotation = PropertyValuesHolder.ofFloat("rotation", 0, -30f, 0, 30f, 0);
        PropertyValuesHolder pvhScale = PropertyValuesHolder.ofFloat("scaleY", 1, 0.5f, 1);
        ObjectAnimator otherAnim = ObjectAnimator.ofPropertyValuesHolder(mSmallCircle, pvhRotation, pvhScale);
        otherAnim.setInterpolator(new BounceInterpolator());

        mAnim.play(smallCircleAnim).with(otherAnim)
                .with(largeCircleAnim).with(outerCircleAnim);
        mAnim.setDuration(500);
        mAnim.start();
    }

    public void pageDown() {

    }
}
