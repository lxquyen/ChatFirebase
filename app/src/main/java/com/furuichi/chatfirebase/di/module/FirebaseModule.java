package com.furuichi.chatfirebase.di.module;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import dagger.Module;
import dagger.Provides;

/**
 * Created by quyenlx on 7/23/2017.
 */

@Module
public class FirebaseModule {
    @Provides
    public FirebaseAuth getFrFirebaseAuth() {
        return FirebaseAuth.getInstance();
    }

    @Provides
    public FirebaseDatabase getFirebaseDatabase() {
        return FirebaseDatabase.getInstance();
    }

}
