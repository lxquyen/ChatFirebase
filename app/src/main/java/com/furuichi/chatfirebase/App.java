package com.furuichi.chatfirebase;

import com.furuichi.chatfirebase.di.component.ApplicationComponent;
import com.furuichi.chatfirebase.di.component.DaggerApplicationComponent;
import com.furuichi.chatfirebase.di.module.ApplicationModule;
import com.olddog.common.AppCommon;

/**
 * Created by quyenlx on 7/22/2017.
 */

public class App extends AppCommon {
    ApplicationComponent component;

    private static App instance;
    private boolean isChatActivityOpen = false;


    public static synchronized App get() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        component = DaggerApplicationComponent
                .builder()
                .applicationModule(new ApplicationModule(this))
                .build();
    }

    public ApplicationComponent getComponent() {
        return component;
    }


    public boolean isChatActivityOpen() {
        return isChatActivityOpen;
    }

    public void setChatActivityOpen(boolean isChatActivityOpen) {
        this.isChatActivityOpen = isChatActivityOpen;
    }
}
