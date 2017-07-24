package com.furuichi.chatfirebase.di.module;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import dagger.Module;
import dagger.Provides;

/**
 * Created by quyenlx on 7/22/2017.
 */

@Module
public class StorageModule {
    @Provides
    public SharedPreferences getSharedPreferences(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

    @Provides
    public SharedPreferences.Editor getEditor(SharedPreferences prefs) {
        return prefs.edit();
    }
}
