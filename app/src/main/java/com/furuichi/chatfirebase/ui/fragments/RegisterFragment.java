package com.furuichi.chatfirebase.ui.fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.widget.EditText;

import com.furuichi.chatfirebase.App;
import com.furuichi.chatfirebase.R;
import com.furuichi.chatfirebase.base.BaseFragment;
import com.furuichi.chatfirebase.di.module.ViewModule;
import com.furuichi.chatfirebase.ui.activities.MainActivity;
import com.furuichi.chatfirebase.ui.presenter.RegisterPresenter;
import com.furuichi.chatfirebase.ui.view.RegisterView;
import com.google.firebase.auth.FirebaseUser;
import com.olddog.common.ToastUtils;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by quyenlx on 7/23/2017.
 */

public class RegisterFragment extends BaseFragment implements RegisterView {
    @BindView(R.id.username)
    EditText username;
    @BindView(R.id.password)
    EditText password;
    private ProgressDialog mProgressDialog;

    @Inject
    RegisterPresenter presenter;

    @Override
    public void injectDependence() {
        App.get().getComponent().plus(new ViewModule(this)).injectTo(this);
    }

    @Override
    public int getLayoutRes() {
        return R.layout.fragment_register;
    }

    @Override
    public void initView() {
        mProgressDialog = new ProgressDialog(mBaseActivity);
        mProgressDialog.setTitle(R.string.loading);
        mProgressDialog.setMessage(getString(R.string.please_wait));
        mProgressDialog.setIndeterminate(true);
    }

    @Override
    public void initData() {

    }

    @OnClick(R.id.register)
    public void onViewClicked() {
        presenter.register(username.getText().toString(), password.getText().toString());
        mProgressDialog.show();
    }

    @Override
    public void onRegistrationSuccess(FirebaseUser firebaseUser) {
        mProgressDialog.setMessage(getString(R.string.add_user_to_db));
        ToastUtils.show(getString(R.string.register_success));
        presenter.addUser(firebaseUser);
    }

    @Override
    public void onRegistrationFailure(String message) {
        mProgressDialog.dismiss();
        mProgressDialog.setMessage(getString(R.string.please_wait));
        ToastUtils.show(getString(R.string.register_failed));
    }

    @Override
    public void onAddUserSuccess(String message) {
        mProgressDialog.dismiss();
        ToastUtils.show(message);
        MainActivity.start(mBaseActivity,
                Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
    }

    @Override
    public void onAddUserFailure(String message) {
        mProgressDialog.dismiss();
        ToastUtils.show(message);
    }
}
