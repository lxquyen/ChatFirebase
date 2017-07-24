package com.furuichi.chatfirebase.fcm;

import com.furuichi.chatfirebase.App;
import com.furuichi.chatfirebase.common.Constants;
import com.furuichi.chatfirebase.manager.StorageManager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import javax.inject.Inject;

import timber.log.Timber;

/**
 * Created by quyenlx on 7/23/2017.
 */

public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {
    public static final String TAG = MyFirebaseInstanceIDService.class.getSimpleName();
    @Inject
    StorageManager manager;
    @Inject
    FirebaseAuth auth;
    @Inject
    FirebaseDatabase database;

    @Override
    public void onCreate() {
        super.onCreate();
        App.get().getComponent().injectTo(this);
    }

    @Override
    public void onTokenRefresh() {
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Timber.d("onTokenRefresh: " + refreshedToken);

        sendRegistrationToServer(refreshedToken);
    }

    private void sendRegistrationToServer(final String token) {
        manager.setStringValue(Constants.ARG_FIREBASE_TOKEN, token);
        if (auth.getCurrentUser() != null) {
            database.getReference()
                    .child(Constants.ARG_USERS)
                    .child(auth.getCurrentUser().getUid())
                    .child(Constants.ARG_FIREBASE_TOKEN)
                    .setValue(token);
        }
    }
}
