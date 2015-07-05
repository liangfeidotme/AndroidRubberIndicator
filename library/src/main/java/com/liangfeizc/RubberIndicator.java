package com.liangfeizc;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.TargetApi;
import android.content.Context;
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

        mAnim = new AnimatorSet();

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
    public void animate(float smallCircleX, float largeCircleX, float outerCircleX) {

        /** radius animator */
        float radius = mSmallCircle.getRadius();
        ObjectAnimator radiusDecreaseAnim = ObjectAnimator.ofFloat(mSmallCircle, "radius", radius - 5);
        ObjectAnimator radiusIncreaseAnim = ObjectAnimator.ofFloat(mSmallCircle, "radius", radius);

        radiusDecreaseAnim.setDuration(150);
        radiusIncreaseAnim.setDuration(150);

        AnimatorSet radiusAnim = new AnimatorSet();
        radiusAnim.playSequentially(radiusDecreaseAnim, radiusIncreaseAnim);

        /** line motion */
        ObjectAnimator smallCircleAnim = ObjectAnimator.ofFloat(mSmallCircle, "x", smallCircleX);
        ObjectAnimator largeCircleAnim = ObjectAnimator.ofFloat(mLargeCircle, "x", largeCircleX);
        ObjectAnimator outerCircleAnim = ObjectAnimator.ofFloat(mOuterCircle, "x", outerCircleX);

        /** arc motion **/
        PointF startLocation = mSmallCircle.getLocation();
        PointF endLocation = new PointF(startLocation.x - (mSmallCircle.getX() - smallCircleX),
                startLocation.y);
        Animator  arcAnim = createArcMotion(startLocation, endLocation);

        AnimatorSet animSet = new AnimatorSet();
        animSet.setInterpolator(new BounceInterpolator());
        animSet.playTogether(smallCircleAnim, largeCircleAnim, outerCircleAnim, radiusAnim, arcAnim);
        animSet.start();
    }

    public Animator createArcMotion(PointF startPoint, PointF endPoint) {
        AnimatorPath path = new AnimatorPath();

        float distance = (startPoint.x - endPoint.x) / 2;
        RectF oval = new RectF(endPoint.x, endPoint.y - distance, startPoint.x, startPoint.y + distance);

        path.arcTo(oval, 0, 180);

        ObjectAnimator anim = ObjectAnimator.ofObject(mSmallCircle, "location",
                new PathEvaluator(), path.getPoints().toArray());
        anim.setDuration(300);

        return anim;
    }
}
