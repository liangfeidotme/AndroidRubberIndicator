package com.liangfeizc;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.PointF;
import android.os.Build;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

/**
 * Created by liangfeizc on 6/28/15.
 */
public class RubberIndicator extends RelativeLayout {
    private static final String TAG = "RubberIndicator";

    private static final int SMALL_CIRCLE_COLOR = 0xFFDF8D81;
    private static final int LARGE_CIRCLE_COLOR = 0xFFAF3854;
    private static final int OUTER_CIRCLE_COLOR = 0xFF533456;

    private static final int SMALL_CIRCLE_RADIUS = 20;
    private static final int LARGE_CIRCLE_RADIUS = 25;
    private static final int OUTER_CIRCLE_RADIUS = 50;

    private static final int CIRCLE_TYPE_SMALL = 0x00;
    private static final int CIRCLE_TYPE_LARGE = 0x01;
    private static final int CIRCLE_TYPE_OUTER = 0x02;


    /**
     * colors
     */
    private int mSmallCircleColor;
    private int mLargeCircleColor;
    private int mOuterCircleColor;

    /**
     * coordinate values
     */
    private int mSmallCircleRadius;
    private int mLargeCircleRadius;
    private int mOuterCircleRadius;

    /**
     * views
     */
    private LinearLayout mContainer;
    private CircleView mLargeCircle;
    private CircleView mSmallCircle;
    private CircleView mOuterCircle;
    private CircleView[] mSmallCircleViews;

    /**
     * animations
     */
    private AnimatorSet mAnim;
    private PropertyValuesHolder pvhScaleX;
    private PropertyValuesHolder pvhScaleY;
    private PropertyValuesHolder pvhRotation;
    private PropertyValuesHolder pvhScale;
    private PropertyValuesHolder mPvhShake;

    private int mSmallCircleIndex = 0;
    private boolean mDown = true;

    public RubberIndicator(Context context) {
        super(context);
        init();
    }

    public RubberIndicator(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        /** Initialize views */
        View rootView = inflate(getContext(), R.layout.rubber_indicator, this);
        mContainer = (LinearLayout) rootView.findViewById(R.id.container);
        mOuterCircle = (CircleView) rootView.findViewById(R.id.outer_circle);

        /** values */
        mSmallCircleColor = SMALL_CIRCLE_COLOR;
        mLargeCircleColor = LARGE_CIRCLE_COLOR;
        mOuterCircleColor = OUTER_CIRCLE_COLOR;

        mSmallCircleRadius = dp2px(SMALL_CIRCLE_RADIUS);
        mLargeCircleRadius = dp2px(LARGE_CIRCLE_RADIUS);
        mOuterCircleRadius = dp2px(OUTER_CIRCLE_RADIUS);

        mAnim = new AnimatorSet();
        pvhScaleX = PropertyValuesHolder.ofFloat("scaleX", 1, 0.8f, 1);
        pvhScaleY = PropertyValuesHolder.ofFloat("scaleY", 1, 0.8f, 1);
        pvhScale = PropertyValuesHolder.ofFloat("scaleY", 1, 0.5f, 1);

    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);

        mOuterCircle.setCenter(mLargeCircle.getCenter());
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    public void setCount(int count) {
        if (count < 2) {
            throw new IllegalArgumentException("count must be greater than 2");
        }

        // create the large circle
        mLargeCircle = createCircleView(CIRCLE_TYPE_LARGE);
        mContainer.addView(mLargeCircle);

        int size = count - 1;
        mSmallCircleViews = new CircleView[size];
        for (int i = 0; i < size; i++) {
            CircleView circleView = createCircleView(CIRCLE_TYPE_SMALL);
            mContainer.addView(circleView);
            mSmallCircleViews[i] = circleView;
        }
    }

    private CircleView createCircleView(int type) {
        CircleView circleView = new CircleView(getContext());

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.weight = 1;
        params.gravity = Gravity.CENTER_VERTICAL;

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
    private void move(final boolean down) {
        mSmallCircle = mSmallCircleViews[mSmallCircleIndex];

        float smallCircleX = down ? mLargeCircle.getX()
                : mLargeCircle.getX() + mLargeCircle.getWidth() - mSmallCircle.getWidth();
        float largeCircleX = down ?
                mSmallCircle.getX() + mSmallCircle.getWidth() - mLargeCircle.getWidth() : mSmallCircle.getX();
        float outerCircleX = mOuterCircle.getX() + largeCircleX - mLargeCircle.getX();

        // animations for large circle and outer circle
        PropertyValuesHolder pvhX = PropertyValuesHolder.ofFloat("x", mLargeCircle.getX(), largeCircleX);
        ObjectAnimator largeCircleAnim = ObjectAnimator.ofPropertyValuesHolder(
                mLargeCircle, pvhX, pvhScaleX, pvhScaleY);

        pvhX = PropertyValuesHolder.ofFloat("x", mOuterCircle.getX(), outerCircleX);
        ObjectAnimator outerCircleAnim = ObjectAnimator.ofPropertyValuesHolder(
                mOuterCircle, pvhX, pvhScaleX, pvhScaleY);

        // Animations for small circle
        PointF smallCircleCenter = mSmallCircle.getCenter();
        PointF smallCircleEndCenter = new PointF(
                smallCircleCenter.x - (mSmallCircle.getX() - smallCircleX), smallCircleCenter.y);

        Path motionPath = new Path();
        motionPath.moveTo(smallCircleCenter.x, smallCircleCenter.y);
        motionPath.quadTo(smallCircleCenter.x, smallCircleCenter.y,
                (smallCircleCenter.x + smallCircleEndCenter.x) / 2,
                (smallCircleCenter.y + smallCircleEndCenter.y) / 2 + 80);
        motionPath.lineTo(smallCircleEndCenter.x, smallCircleEndCenter.y);

        final PathMeasure pathMeasure = new PathMeasure(motionPath, false);

        ValueAnimator smallCircleAnim = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            smallCircleAnim = ObjectAnimator.ofObject(mSmallCircle, "center", null, motionPath);

        } else {
            final float[] point = new float[2];
            smallCircleAnim = ValueAnimator.ofFloat(0.0f, 1.0f);
            smallCircleAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    pathMeasure.getPosTan(
                            pathMeasure.getLength() * animation.getAnimatedFraction(), point, null);
                    mSmallCircle.setCenter(new PointF(point[0], point[1]));
                }
            });
        }

        pvhRotation = PropertyValuesHolder.ofFloat("rotation", 0, mDown ? -30f : 30f, 0, mDown ? 30f : -30f, 0);
        ObjectAnimator otherAnim = ObjectAnimator.ofPropertyValuesHolder(mSmallCircle, pvhRotation, pvhScale);

        //float y = mSmallCircle.getY();
        //PropertyValuesHolder pvhShake = PropertyValuesHolder.ofFloat("y", y, y - 10, y);
        //ObjectAnimator shakeAnim = ObjectAnimator.ofPropertyValuesHolder(mSmallCircle, pvhShake);
        //shakeAnim.setInterpolator(new BounceInterpolator());
        //shakeAnim.setDuration(20);

        mAnim = new AnimatorSet();
        mAnim.play(smallCircleAnim)
                .with(otherAnim).with(largeCircleAnim).with(outerCircleAnim)/*.before(shakeAnim)*/;
        mAnim.setInterpolator(new AccelerateDecelerateInterpolator());
        mAnim.setDuration(500);
        mAnim.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                mSmallCircle.setX(Math.round(mSmallCircle.getX() + 0.5));
                mSmallCircle.setY(Math.round(mSmallCircle.getY() + 0.5));

                if (mDown && mSmallCircleIndex == mSmallCircleViews.length - 1) {
                    mDown = false;
                } else if (!mDown && mSmallCircleIndex == 0) {
                    mDown = true;
                } else {
                    mSmallCircleIndex = mSmallCircleIndex + (mDown ? 1 : -1);
                }


            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        mAnim.start();
    }

    private int dp2px(int dpValue) {
        return (int) getContext().getResources().getDisplayMetrics().density * dpValue;
    }

    public void move() {
        if (mAnim.isRunning()) {
            return;
        }
        move(mDown);
    }
}
