package com.furuichi.chatfirebase.ui.presenter;

import android.support.annotation.NonNull;

import com.furuichi.chatfirebase.common.Constants;
import com.furuichi.chatfirebase.manager.StorageManager;
import com.furuichi.chatfirebase.ui.view.LoginView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import javax.inject.Inject;

/**
 * Created by quyenlx on 7/23/2017.
 */

public class LoginPresenter {
    private FirebaseAuth firebaseAuth;
    private LoginView view;
    private StorageManager storageManager;
    private FirebaseDatabase firebaseDatabase;

    @Inject
    public LoginPresenter(FirebaseAuth firebaseAuth, LoginView view, StorageManager storageManager, FirebaseDatabase database) {
        this.firebaseAuth = firebaseAuth;
        this.view = view;
        this.storageManager = storageManager;
        this.firebaseDatabase = database;
    }

    public void login(String email, String password) {
        firebaseAuth
                .signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            view.onLoginSuccess(task.getResult().toString());
                            updateFirebaseToken(task.getResult().getUser().getUid(),
                                    storageManager.getStringValue(Constants.ARG_FIREBASE_TOKEN));
                        } else {
                            view.onLLoginFailure(task.getException().getMessage());
                        }
                    }
                });
    }

    private void updateFirebaseToken(String uid, String token) {
        firebaseDatabase.getReference()
                .child(Constants.ARG_USERS)
                .child(uid)
                .child(Constants.ARG_FIREBASE_TOKEN)
                .setValue(token);
    }
}
