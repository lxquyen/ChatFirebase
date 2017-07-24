package com.olddog.common;

/**
 * Created by QuyenLx on 7/20/2017.
 */

public class ConvertUtils {
    private ConvertUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    public static int px2dp(float pxValue) {
        final float scale = AppCommon.get().getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    public static int dp2px(float dpValue) {
        final float scale = AppCommon.get().getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    public static int sp2px(float spValue) {
        final float fontScale = AppCommon.get().getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    public static int px2sp(float pxValue) {
        final float fontScale = AppCommon.get().getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }
}
