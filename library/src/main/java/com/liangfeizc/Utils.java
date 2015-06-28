package com.liangfeizc;

import android.content.Context;

/**
 * Created by liangfeizc on 6/28/15.
 */
public class Utils {
    public static int dp2px(final Context context, int dpValue) {
        return (int) context.getResources().getDisplayMetrics().density * dpValue;
    }
}
