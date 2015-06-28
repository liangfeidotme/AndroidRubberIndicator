package com.liangfeizc;

import android.content.Context;
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
    private int mWrapperColor = 0xFF533456;
    private int mIndicatorColor = 0xFFDF8D81;
    private int mWrapperRadius = 20;
    private int mIndicatorRaiuds = 10;

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
        mCircleViewList = new ArrayList<>();

        mContainer = new LinearLayout(getContext());
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT);
        params.gravity = Gravity.BOTTOM;
        mContainer.setLayoutParams(new ViewGroup.LayoutParams(WRAP_CONTENT, WRAP_CONTENT));
        mContainer.setGravity(Gravity.CENTER);

        // create circle wrapper view
        mWrapperView = createCircleView(mWrapperColor, Utils.dp2px(getContext(), mWrapperRadius));
    }

    public void setCurrentPos(int location) {
        mCircleViewList.add(location, mWrapperView);
    }

    public void setNumber(int number) {
        final Context context = getContext();
        final int width = Utils.dp2px(context, 20);

        for (int i = 0; i < number; i++) {
            mCircleViewList.add(createCircleView(mIndicatorColor,
                    Utils.dp2px(getContext(), mIndicatorRaiuds)));
        }
        setCurrentPos(0);
    }

    private CircleView createCircleView(int color, int radius) {
        CircleView view = new CircleView(getContext());
        int size = radius >> 1;
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(size, size);
        params.gravity = Gravity.CENTER_VERTICAL;
        view.setLayoutParams(params);
        view.setColor(color);
        return view;
    }
}
