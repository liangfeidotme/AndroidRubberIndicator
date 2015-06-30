package com.liangfeizc;

import android.content.Context;
import android.util.AttributeSet;
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
        inflate(getContext(), R.layout.rubber_indicator, this);
    }

    public void setCount(int count) {
        mCount = count;
    }
}
