package com.liangfeizc;

import android.animation.AnimatorSet;
import android.animation.Keyframe;
import android.animation.ObjectAnimator;
import android.animation.PointFEvaluator;
import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Path;
import android.graphics.PointF;
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

    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    public void next() {
        float smallCircleX = mLargeCircle.getX();
        float largeCircleX = mSmallCircle.getX() + mSmallCircle.getWidth() - mLargeCircle.getWidth();
        float outerCircleX = mOuterCircle.getX() + largeCircleX - mLargeCircle.getX();

        //if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
        //    mSmallCircle.animate().x(smallCircleX).start();
        //    mLargeCircle.animate().x(largeCircleX).start();
        //    mOuterCircle.animate().x(outerCircleX).start();
        //} else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            animate(smallCircleX, largeCircleX, outerCircleX);
        //}
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void animate(float smallCircleX, float largeCircleX, float outerCircleX) {
        float radius = mSmallCircle.getRadius();

        ObjectAnimator radiusAnim = ObjectAnimator.ofFloat(mSmallCircle, "radius", radius - 5);
        radiusAnim.setDuration(150).reverse();

        ObjectAnimator smallCircleAnim = ObjectAnimator.ofFloat(mSmallCircle, "x", smallCircleX);
        ObjectAnimator largeCircleAnim = ObjectAnimator.ofFloat(mLargeCircle, "x", largeCircleX);
        ObjectAnimator outerCircleAnim = ObjectAnimator.ofFloat(mOuterCircle, "x", outerCircleX);

        PointF startPoint = mSmallCircle.getCenterPoint();
        PointF endPoint = new PointF(startPoint.x - (mSmallCircle.getX() - smallCircleX), startPoint.y);

        PointF midPoint = new PointF((startPoint.x + endPoint.x) / 2, mSmallCircle.getY() + mSmallCircle.getRadius());

        ValueAnimator moveDownAnimator = ValueAnimator.ofObject(new PointFEvaluator(), startPoint, midPoint);
        ValueAnimator moveUpAnimator = ValueAnimator.ofObject(new PointFEvaluator(), midPoint, endPoint);

        AnimatorSet animSet = new AnimatorSet();
        animSet.setInterpolator(new BounceInterpolator());
        animSet.playTogether(largeCircleAnim, outerCircleAnim, radiusAnim, moveDownAnimator, moveUpAnimator);
        animSet.start();
    }
}
