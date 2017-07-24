package com.furuichi.chatfirebase.ui.activities;

import android.content.Context;
import android.content.Intent;

import com.furuichi.chatfirebase.base.BaseActivity;
import com.furuichi.chatfirebase.base.BaseFragment;
import com.furuichi.chatfirebase.ui.fragments.MainFragment;

public class MainActivity extends BaseActivity {
    public static void start(Context context, int flags) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.setFlags(flags);
        context.startActivity(intent);
    }

    @Override
    protected BaseFragment initFragment() {
        return new MainFragment();
    }
}
