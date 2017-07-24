package com.furuichi.chatfirebase.di.component;

import com.furuichi.chatfirebase.di.module.ViewModule;
import com.furuichi.chatfirebase.ui.fragments.ChatFragment;
import com.furuichi.chatfirebase.ui.fragments.LoginFragment;
import com.furuichi.chatfirebase.ui.fragments.MainFragment;
import com.furuichi.chatfirebase.ui.fragments.RegisterFragment;

import dagger.Subcomponent;

/**
 * Created by quyenlx on 7/23/2017.
 */
@Subcomponent(modules = ViewModule.class)
public interface ViewComponent {
    void injectTo(ChatFragment fragment);

    void injectTo(RegisterFragment fragment);

    void injectTo(LoginFragment fragment);

    void injectTo(MainFragment fragment);
}
