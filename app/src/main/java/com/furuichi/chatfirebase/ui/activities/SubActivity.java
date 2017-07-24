package com.furuichi.chatfirebase.ui.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.furuichi.chatfirebase.BuildConfig;
import com.furuichi.chatfirebase.base.BaseActivity;
import com.furuichi.chatfirebase.base.BaseFragment;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class SubActivity extends BaseActivity {

    public static final String EXTRA_FRAGMENT_CLASS = BuildConfig.APPLICATION_ID + ".EXTRA_FRAGMENT_CLASS";
    public static final String EXTRA_FRAGMENT_ARGS = BuildConfig.APPLICATION_ID + ".EXTRA_FRAGMENT_ARGS";

    public static Intent createIntent(Context context) {
        return new Intent(context, SubActivity.class);
    }

    public static void start(Activity activity, Intent intent) {
        activity.startActivity(intent);
    }

    public static void startForResult(Activity activity, Intent intent, int requestCode) {
        activity.startActivityForResult(intent, requestCode);
    }


    @Override
    protected BaseFragment initFragment() {
        Bundle extras = getIntent().getExtras();
        if (extras == null)
            return null;
        Class<? extends BaseFragment> clazz
                = (Class<? extends BaseFragment>) extras.getSerializable(EXTRA_FRAGMENT_CLASS);
        if (clazz == null)
            return null;

        BaseFragment f = null;
        try {
            Constructor c = clazz.getConstructor();
            try {
                f = (BaseFragment) c.newInstance();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        Bundle args = extras.getBundle(EXTRA_FRAGMENT_ARGS);
        if (args != null) f.setArguments(args);
        return f;
    }

}
