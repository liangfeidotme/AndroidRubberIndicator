package com.liangfeizc;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.TargetApi;
import android.content.Context;
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

    public RubberIndicator(Context context) {
        super(context);
        init();
    }

    public RubberIndicator(Context context, AttributeSet attrs) {
        super(context, attrs);
        initAttrs(attrs);
        init();
    }

    private void initAttrs(AttributeSet attrs) {

    }

    private void init() {
        View rootView = inflate(getContext(), R.layout.rubber_indicator, this);

        mLargeCircle = (CircleView) rootView.findViewById(R.id.large_circle);
        mSmallCircle = (CircleView) rootView.findViewById(R.id.small_circle);
        mOuterCircle = (CircleView) rootView.findViewById(R.id.outer_circle);
    }

    public void setCount(int count) {
        mCount = count;
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public void next() {
        float leftX = mLargeCircle.getX();
        float rightX = mSmallCircle.getX() + mSmallCircle.getWidth();
        float midX = (leftX + rightX) / 2;

        float newLeftX = midX * 2 - rightX;
        float newRightX = midX * 2 - (leftX + mLargeCircle.getWidth());

        //mLargeCircle.setX(newRightX);
        //mSmallCircle.setX(newLeftX);
        //animateSmallCircle(0, newLeftX);

        //mOuterCircle.setX(mOuterCircle.getX() + mLargeCircle.getX() - leftX);

        createAnimator(newLeftX, newRightX, mOuterCircle.getX() + newRightX - leftX);
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private void createAnimator(float smallCircleX, float largeCircleX, float outerCircleX) {
        float radius = mSmallCircle.getRadius();

        ObjectAnimator radiusDecreaseAnim = ObjectAnimator.ofFloat(mSmallCircle, "radius", radius - 5);
        ObjectAnimator radiusIncreaseAnim = ObjectAnimator.ofFloat(mSmallCircle, "radius", radius);

        radiusDecreaseAnim.setDuration(150);
        radiusIncreaseAnim.setDuration(150);

        AnimatorSet radiusAnim = new AnimatorSet();
        radiusAnim.playSequentially(radiusDecreaseAnim, radiusIncreaseAnim);

        ObjectAnimator smallCircleAnim = ObjectAnimator.ofFloat(mSmallCircle, "x", smallCircleX);
        ObjectAnimator largeCircleAnim = ObjectAnimator.ofFloat(mLargeCircle, "x", largeCircleX);
        ObjectAnimator outerCircleAnim = ObjectAnimator.ofFloat(mOuterCircle, "x", outerCircleX);

        AnimatorSet animSet = new AnimatorSet();
        animSet.setInterpolator(new BounceInterpolator());
        animSet.playTogether(smallCircleAnim, largeCircleAnim, outerCircleAnim, radiusAnim);
        animSet.start();
    }
}
