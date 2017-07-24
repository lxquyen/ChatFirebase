package com.olddog.common;

import android.widget.Toast;

/**
 * Created by QuyenLx on 7/20/2017.
 */

public class ToastUtils {
    private ToastUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }
    public static void show(String message) {
        Toast.makeText(AppCommon.get(), message, Toast.LENGTH_SHORT).show();
    }

    public static void showLong(String message) {
        Toast.makeText(AppCommon.get(), message, Toast.LENGTH_LONG).show();
    }
}
