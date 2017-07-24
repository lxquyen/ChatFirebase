package com.furuichi.chatfirebase.ui.view;

import com.google.firebase.auth.FirebaseUser;

/**
 * Created by quyenlx on 7/23/2017.
 */

public interface RegisterView {
    void onRegistrationSuccess(FirebaseUser firebaseUser);

    void onRegistrationFailure(String message);

    void onAddUserSuccess(String message);

    void onAddUserFailure(String message);
}
