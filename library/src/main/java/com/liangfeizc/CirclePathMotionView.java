package com.liangfeizc;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.PointF;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.FrameLayout;

/**
 * Created by rufi on 7/4/15.
 */
public class CirclePathMotionView extends FrameLayout {
    private static final float DEFAULT_RADIUS = 50;

    private PointF mStartPoint;
    private PointF mEndPoint;
    private CircleView mCircleView;
    private AnimatorPath mPath;
    private ObjectAnimator mAnimator;
    private AnimatorSet mAnimatorSet;

    public CirclePathMotionView(Context context) {
        super(context);
        init();
    }

    public CirclePathMotionView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CirclePathMotionView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mStartPoint = new PointF(200, 100);
        mEndPoint = new PointF(600, 100);

        mPath = new AnimatorPath();
        //mPath.moveTo(mStartPoint.x, mEndPoint.y);

        RectF oval = new RectF(mStartPoint.x, mStartPoint.y - 200, mEndPoint.x, mEndPoint.y + 200);
        mPath.arcTo(oval, -180, -180);

        //mPath.lineTo(mEndPoint.x, mEndPoint.y);

        mCircleView = createCircleView(mStartPoint, DEFAULT_RADIUS);
        addView(mCircleView);
        addView(createCircleView(mEndPoint, DEFAULT_RADIUS));

        mAnimator = ObjectAnimator.ofObject(this, "centerPoint",
                new PathEvaluator(), mPath.getPoints().toArray());
        mAnimator.setDuration(1000);

        final float radius = mCircleView.getRadius();
        ObjectAnimator radiusDecreaseAnim = ObjectAnimator.ofFloat(mCircleView, "radius", radius - 20);
        radiusDecreaseAnim.setDuration(500);
        ObjectAnimator radiusIncreaseAnim = ObjectAnimator.ofFloat(mCircleView, "radius", radius);
        radiusIncreaseAnim.setDuration(500);

        AnimatorSet radiusAnim = new AnimatorSet();
        radiusAnim.playSequentially(radiusDecreaseAnim, radiusIncreaseAnim);
        //radiusAnim.setDuration(1000);

        mAnimatorSet = new AnimatorSet();
        mAnimatorSet.playTogether(radiusAnim, mAnimator);
    }

    private CircleView createCircleView(PointF centerPoint, float radius) {
        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);

        CircleView circleView = new CircleView(getContext());
        circleView.setLayoutParams(params);
        circleView.setCenterPoint(centerPoint);
        circleView.setRadius(radius);

        return circleView;
    }

    public void setLoc(PathPoint point) {
        mCircleView.setCenterPoint(new PointF(point.mX, point.mY));
    }

    public void startAnimating() {
        if (mAnimatorSet.isRunning()) {
            mAnimatorSet.end();
        }

        mAnimatorSet.start();
    }
}
