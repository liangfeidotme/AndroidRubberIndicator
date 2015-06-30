package com.liangfeizc;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

/**
 * Created by liangfeizc on 6/28/15.
 */
public class RubberIndicator extends FrameLayout {
    private static final int DEFAULT_CIRCLE_MARGIN = 10;
    private static final int DEFAULT_BACKGROUND_COLOR = 0xFF533456;

    private int mSmallCircleColor = 0xFFDF8D81;
    private int mBigCircleColor = 0xFFAF3854;
    private int mOuterCircleColor = DEFAULT_BACKGROUND_COLOR;

    private int mIndicatorRadius = 20;

    private int mCircleMargin;
    private int mCurrentPosition = 0;
    private int mIndicatorCount;

    private CircleView mWrapperView;
    private CircleView mIndicatorView;

    private LinearLayout mContainer;
    private List<CircleView> mCircleViewList;

    public RubberIndicator(Context context) {
        super(context);
        init();
    }

    public RubberIndicator(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public RubberIndicator(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        // init values
        mCircleMargin = Utils.dp2px(getContext(), DEFAULT_CIRCLE_MARGIN);

        mCircleViewList = new ArrayList<>();

        // create a container to store circle views
        mContainer = new LinearLayout(getContext());
        mContainer.setBackgroundColor(DEFAULT_BACKGROUND_COLOR);
        mContainer.setOrientation(LinearLayout.HORIZONTAL);
        mContainer.setGravity(Gravity.CENTER_VERTICAL);

        LayoutParams params = new LayoutParams(WRAP_CONTENT, WRAP_CONTENT);
        params.gravity = Gravity.CENTER;
        mContainer.setLayoutParams(params);

        // add container to parent view
        addView(mContainer);

    }

    public void setNumber(int number) {
        mIndicatorCount = number;

        int radius = Utils.dp2px(getContext(), mIndicatorRadius);

        // insert the bigger circle
        CircleView bigCircle = createCircleView(mBigCircleColor, radius * 1.5f);
        mCircleViewList.add(mCurrentPosition, bigCircle);

        // insert the outer circle to this FrameLayout
        insertOuterCircle();

        // insert small circles
        for (int i = 1; i < number; i++) {
            mCircleViewList.add(createCircleView(mSmallCircleColor, radius));
        }

        buildUI();
    }

    private void buildUI() {
        mContainer.removeAllViews();
        for (int i = 0, size = mCircleViewList.size(); i < size; i++) {
            mContainer.addView(mCircleViewList.get(i));
        }
    }

    private CircleView createCircleView(int color, float radius) {
        CircleView view = new CircleView(getContext());

        int size = (int) (radius * 2);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(size, size);
        params.setMargins(mCircleMargin, mCircleMargin, mCircleMargin, mCircleMargin);
        params.gravity = Gravity.CENTER_VERTICAL;
        view.setLayoutParams(params);

        view.setColor(color);
        return view;
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private void insertOuterCircle() {
    }

    public void startMoving() {
        int oldPosition = mCurrentPosition;
        mCurrentPosition = (mCurrentPosition + 1) % mIndicatorCount;

        CircleView view = mCircleViewList.get(oldPosition);
        mCircleViewList.set(oldPosition, mCircleViewList.get(mCurrentPosition));
        mCircleViewList.set(mCurrentPosition, view);

        buildUI();
    }
}
