package com.furuichi.chatfirebase.ui.fragments;

import android.content.Intent;
import android.view.View;
import android.widget.EditText;

import com.furuichi.chatfirebase.App;
import com.furuichi.chatfirebase.R;
import com.furuichi.chatfirebase.base.BaseFragment;
import com.furuichi.chatfirebase.di.module.ViewModule;
import com.furuichi.chatfirebase.ui.activities.MainActivity;
import com.furuichi.chatfirebase.ui.presenter.LoginPresenter;
import com.furuichi.chatfirebase.ui.view.LoginView;
import com.olddog.common.ToastUtils;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by quyenlx on 7/23/2017.
 */

public class LoginFragment extends BaseFragment implements LoginView {
    @BindView(R.id.username)
    EditText username;
    @BindView(R.id.password)
    EditText password;
    @Inject
    LoginPresenter presenter;

    @Override
    public void injectDependence() {
        App.get().getComponent().plus(new ViewModule(this)).injectTo(this);
    }

    @Override
    public int getLayoutRes() {
        return R.layout.fragment_login;
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {

    }

    @OnClick({R.id.login, R.id.register})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.login:
                mBaseActivity.showLoading();
                presenter.login(username.getText().toString(), password.getText().toString());
                break;
            case R.id.register:
                mBaseActivity.startActivity(RegisterFragment.class);
                break;
        }
    }

    @Override
    public void onLoginSuccess(String message) {
        mBaseActivity.hideLoading();
        ToastUtils.show("Logged in successfully");
        MainActivity.start(mBaseActivity,
                Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
    }

    @Override
    public void onLLoginFailure(String message) {
        mBaseActivity.hideLoading();
        ToastUtils.show("Error: " + message);
    }
}
