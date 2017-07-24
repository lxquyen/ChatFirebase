package com.furuichi.chatfirebase.di.component;

import com.furuichi.chatfirebase.di.module.ApplicationModule;
import com.furuichi.chatfirebase.di.module.FirebaseModule;
import com.furuichi.chatfirebase.di.module.StorageModule;
import com.furuichi.chatfirebase.di.module.ViewModule;
import com.furuichi.chatfirebase.di.scope.PerFragment;
import com.furuichi.chatfirebase.fcm.MyFirebaseInstanceIDService;
import com.furuichi.chatfirebase.fcm.MyFirebaseMessagingService;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by quyenlx on 7/22/2017.
 */
@Component(modules = {
        ApplicationModule.class,
        FirebaseModule.class,
        StorageModule.class
})
public interface ApplicationComponent {
    void injectTo(MyFirebaseInstanceIDService service);

    void injectTo(MyFirebaseMessagingService messagingService);

    ViewComponent plus(ViewModule module);

}
