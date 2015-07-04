package com.liangfeizc;

import android.content.Context;
import android.graphics.Path;
import android.graphics.PointF;
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
    private float mRadius = DEFAULT_RADIUS;

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
        mStartPoint = new PointF(100, 100);
        mEndPoint = new PointF(600, 100);

        addView(createCircleView(mStartPoint, mRadius));
        addView(createCircleView(mEndPoint, mRadius));

        Path path = new Path();
        path.moveTo(mStartPoint.x, mStartPoint.y);
    }

    private CircleView createCircleView(PointF centerPoint, float radius) {
        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);

        CircleView circleView = new CircleView(getContext());
        circleView.setLayoutParams(params);
        circleView.setCenterPoint(centerPoint);
        circleView.setRadius(mRadius);

        return circleView;
    }

    
}
