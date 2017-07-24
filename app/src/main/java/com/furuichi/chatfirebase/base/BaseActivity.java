package com.furuichi.chatfirebase.base;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.furuichi.chatfirebase.R;
import com.furuichi.chatfirebase.ui.activities.SubActivity;

/**
 * Created by quyenlx on 7/22/2017.
 */

public abstract class BaseActivity extends AppCompatActivity {
    protected abstract BaseFragment initFragment();

    private ProgressDialog dialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        replaceFragment(initFragment());
    }

    private void replaceFragment(BaseFragment baseFragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container, baseFragment)
                .commit();
    }


    public void showLoading() {
        if (dialog != null) {
            if (dialog.isShowing()) dialog.dismiss();
            dialog.show();
            return;
        }
        dialog = ProgressDialog
                .show(this, "", "Loading. Please wait...", true);
    }

    public void hideLoading() {
        if (dialog != null && dialog.isShowing())
            dialog.dismiss();
    }

    public void startActivity(Class<?> classOf) {
        Intent intent = SubActivity.createIntent(this);
        intent.putExtra(SubActivity.EXTRA_FRAGMENT_CLASS, classOf);
        SubActivity.start(this, intent);
    }
    public void startActivity(Class<?> classOf, int flags) {
        Intent intent = SubActivity.createIntent(this);
        intent.putExtra(SubActivity.EXTRA_FRAGMENT_CLASS, classOf);
        intent.setFlags(flags);
        SubActivity.start(this, intent);
    }

}
