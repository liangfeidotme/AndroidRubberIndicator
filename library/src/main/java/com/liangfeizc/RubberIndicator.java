package com.liangfeizc;

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
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.BounceInterpolator;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liangfeizc on 6/28/15.
 */
public class RubberIndicator extends FrameLayout {
    private static final int SMALL_CIRCLE_COLOR = 0xFFDF8D81;
    private static final int LARGE_CIRCLE_COLOR = 0xFFAF3854;
    private static final int OUTER_CIRCLE_COLOR = 0xFF533456;

    private static final int SMALL_CIRCLE_MARGIN = 20;
    private static final int SMALL_CIRCLE_RADIUS = 20;
    private static final int LARGE_CIRCLE_RADIUS = 25;
    private static final int OUTER_CIRCLE_RADIUS = 50;

    private static final int CIRCLE_TYPE_SMALL = 0x00;
    private static final int CIRCLE_TYPE_LARGE = 0x01;
    private static final int CIRCLE_TYPE_OUTER = 0x02;

    /** colors */
    private int mSmallCircleColor;
    private int mLargeCircleColor;
    private int mOuterCircleColor;

    /** coordinate values */
    private int mCircleMargin;
    private int mSmallCircleRadius;
    private int mLargeCircleRadius;
    private int mOuterCircleRadius;

    /** views */
    private View mContainerWrapper;
    private LinearLayout mContainer;
    private CircleView mLargeCircle;
    private CircleView mSmallCircle;
    private CircleView mOuterCircle;
    private List<CircleView> mCircleViewList;

    /** animations */
    private AnimatorSet mAnim;
    private PropertyValuesHolder pvhScaleX;
    private PropertyValuesHolder pvhScaleY;
    private PropertyValuesHolder pvhRotation;
    private PropertyValuesHolder pvhScale;

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
        mContainer = (LinearLayout) rootView.findViewById(R.id.container);
        mContainerWrapper = rootView.findViewById(R.id.container_wrapper);
        mOuterCircle = (CircleView) rootView.findViewById(R.id.outer_circle);

        /** values */
        mSmallCircleColor = SMALL_CIRCLE_COLOR;
        mLargeCircleColor = LARGE_CIRCLE_COLOR;
        mOuterCircleColor = OUTER_CIRCLE_COLOR;

        mCircleViewList = new ArrayList<>();
        mCircleMargin = dp2px(SMALL_CIRCLE_MARGIN);
        mSmallCircleRadius = dp2px(SMALL_CIRCLE_RADIUS);
        mLargeCircleRadius = dp2px(LARGE_CIRCLE_RADIUS);
        mOuterCircleRadius = dp2px(OUTER_CIRCLE_RADIUS);

        mAnim = new AnimatorSet();
        pvhScaleX = PropertyValuesHolder.ofFloat("scaleX", 1, 0.8f, 1);
        pvhScaleY = PropertyValuesHolder.ofFloat("scaleY", 1, 0.8f, 1);
        pvhRotation = PropertyValuesHolder.ofFloat("rotation", 0, -30f, 0, 30f, 0);
        pvhScale = PropertyValuesHolder.ofFloat("scaleY", 1, 0.5f, 1);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
    }

    public void setCount(int count) {
        if (count < 2) {
            throw new IllegalArgumentException("count must be greater than 2");
        }

        mLargeCircle = createCircleView(CIRCLE_TYPE_LARGE);
        mContainer.addView(createCircleView(CIRCLE_TYPE_LARGE));
        mCircleViewList.add(mLargeCircle);
        count = count - 1;
        for (int i = 0; i < count; i++) {
            CircleView circleView = createCircleView(CIRCLE_TYPE_SMALL);
            mContainer.addView(circleView);
            mCircleViewList.add(circleView);
        }
        mSmallCircle = mCircleViewList.get(1);
    }

    private CircleView createCircleView(int type) {
        CircleView circleView = new CircleView(getContext());

        LayoutParams params = new LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.CENTER_VERTICAL;
        params.setMargins(mCircleMargin, mCircleMargin, mCircleMargin, mCircleMargin);

        switch (type) {
            case CIRCLE_TYPE_SMALL:
                params.height = params.width = mSmallCircleRadius << 1;
                circleView.setColor(mSmallCircleColor);
                break;
            case CIRCLE_TYPE_LARGE:
                params.height = params.width = mLargeCircleRadius << 1;
                circleView.setColor(mLargeCircleColor);
                break;
            case CIRCLE_TYPE_OUTER:
                params.height = params.width = mOuterCircleRadius << 1;
                circleView.setColor(mOuterCircleColor);
                break;
        }

        circleView.setLayoutParams(params);

        return circleView;
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public void pageUp() {
        float smallCircleX = mLargeCircle.getX();
        float largeCircleX = mSmallCircle.getX() + mSmallCircle.getWidth() - mLargeCircle.getWidth();
        float outerCircleX = mOuterCircle.getX() + largeCircleX - mLargeCircle.getX();


        PropertyValuesHolder pvhX = PropertyValuesHolder.ofFloat("x", largeCircleX);
        ObjectAnimator largeCircleAnim = ObjectAnimator.ofPropertyValuesHolder(
                mLargeCircle, pvhX, pvhScaleX, pvhScaleY);

        pvhX = PropertyValuesHolder.ofFloat("x", outerCircleX);
        ObjectAnimator outerCircleAnim = ObjectAnimator.ofPropertyValuesHolder(
                mOuterCircle, pvhX, pvhScaleX, pvhScaleY);


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

        ObjectAnimator otherAnim = ObjectAnimator.ofPropertyValuesHolder(mSmallCircle, pvhRotation, pvhScale);
        otherAnim.setInterpolator(new BounceInterpolator());

        mAnim.play(smallCircleAnim).with(otherAnim).with(largeCircleAnim).with(outerCircleAnim);
        mAnim.setDuration(800);
        mAnim.start();
    }

    private int dp2px(int dpValue) {
        return (int)getContext().getResources().getDisplayMetrics().density * dpValue;
    }
}
