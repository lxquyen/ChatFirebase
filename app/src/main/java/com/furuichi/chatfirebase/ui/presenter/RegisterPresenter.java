package com.furuichi.chatfirebase.ui.presenter;

import android.support.annotation.NonNull;

import com.furuichi.chatfirebase.common.Constants;
import com.furuichi.chatfirebase.manager.StorageManager;
import com.furuichi.chatfirebase.models.User;
import com.furuichi.chatfirebase.ui.view.RegisterView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import javax.inject.Inject;

import timber.log.Timber;

/**
 * Created by quyenlx on 7/23/2017.
 */

public class RegisterPresenter {
    private FirebaseAuth mAuth;
    private FirebaseDatabase mDatabase;
    private RegisterView mView;
    private StorageManager mStorageManager;


    @Inject
    public RegisterPresenter(FirebaseAuth mAuth, FirebaseDatabase mDatabase, RegisterView mView, StorageManager mStorageManager) {
        this.mAuth = mAuth;
        this.mDatabase = mDatabase;
        this.mView = mView;
        this.mStorageManager = mStorageManager;
    }

    public void register(String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Timber.e("Register: " + task.isSuccessful());
                        if (!task.isSuccessful()) {
                            mView.onRegistrationFailure(task.getException().getMessage());
                        } else {
                            mView.onRegistrationSuccess(task.getResult().getUser());
                        }
                    }
                });
    }

    public void addUser(FirebaseUser firebaseUser) {
        User user = new User(firebaseUser.getUid(),
                firebaseUser.getEmail(),
                mStorageManager.getStringValue(Constants.ARG_FIREBASE_TOKEN));
        mDatabase.getReference()
                .child(Constants.ARG_USERS)
                .child(firebaseUser.getUid())
                .setValue(user)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            mView.onAddUserSuccess("User successfully added!");
                        } else {
                            mView.onAddUserFailure("Unable to add user!");
                        }
                    }
                });
    }
}
