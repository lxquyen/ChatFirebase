package com.furuichi.chatfirebase.ui.presenter;

import android.text.TextUtils;

import com.furuichi.chatfirebase.common.Constants;
import com.furuichi.chatfirebase.models.User;
import com.furuichi.chatfirebase.ui.view.MainView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * Created by quyenlx on 7/22/2017.
 */

public class MainPresenter {
    private MainView view;
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;

    @Inject
    public MainPresenter(MainView view, FirebaseAuth firebaseAuth, FirebaseDatabase firebaseDatabase) {
        this.view = view;
        this.firebaseAuth = firebaseAuth;
        this.firebaseDatabase = firebaseDatabase;
    }

    public void getListUser() {
        view.onStartRequest();
        if (firebaseAuth.getCurrentUser() != null) {
            firebaseDatabase.getReference().child(Constants.ARG_USERS).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    Iterator<DataSnapshot> dataSnapshots = dataSnapshot.getChildren().iterator();
                    List<User> users = new ArrayList<>();
                    while (dataSnapshots.hasNext()) {
                        DataSnapshot child = dataSnapshots.next();
                        User user = child.getValue(User.class);
                        if (!TextUtils.equals(user.uid, firebaseAuth.getCurrentUser().getUid())) {
                            users.add(user);
                        }
                    }
                    view.onFinishRequest();
                    view.onGetAllUsersSuccess(users);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    view.onFinishRequest();
                    view.onGetAllUsersFailure(databaseError.getMessage());
                }
            });
        } else {
            view.goToLoginScreen();
        }
    }

    public void logout() {
        if (firebaseAuth.getCurrentUser() != null) {
            firebaseAuth.signOut();
            view.onLogoutSuccess("Successfully logged out!");
        } else {
            view.onLogoutFailure("No user logged in yet!");
        }
    }
}
