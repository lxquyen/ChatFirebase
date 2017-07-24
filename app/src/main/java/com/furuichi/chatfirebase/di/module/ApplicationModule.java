package com.furuichi.chatfirebase.di.module;

import android.content.Context;

import org.greenrobot.eventbus.EventBus;

import dagger.Module;
import dagger.Provides;

/**
 * Created by quyenlx on 7/22/2017.
 */

@Module
public class ApplicationModule {
    private Context context;

    public ApplicationModule(Context context) {
        this.context = context;
    }

    @Provides
    public Context getContext() {
        return context;
    }

    @Provides
    public EventBus getEventBus() {
        return EventBus.getDefault();
    }
}
