package com.furuichi.chatfirebase.di.module;

import com.furuichi.chatfirebase.ui.view.ChatView;
import com.furuichi.chatfirebase.ui.view.LoginView;
import com.furuichi.chatfirebase.ui.view.MainView;
import com.furuichi.chatfirebase.ui.view.RegisterView;

import dagger.Module;
import dagger.Provides;

/**
 * Created by quyenlx on 7/23/2017.
 */

@Module
public class ViewModule {
    ChatView chatView;
    RegisterView registerView;
    LoginView loginView;
    MainView mainView;

    public ViewModule(MainView mainView) {
        this.mainView = mainView;
    }

    public ViewModule(ChatView chatView) {
        this.chatView = chatView;
    }

    public ViewModule(LoginView loginView) {
        this.loginView = loginView;
    }

    public ViewModule(RegisterView view) {
        this.registerView = view;
    }

    @Provides
    public ChatView getChatView() {
        return chatView;
    }

    @Provides
    public RegisterView getView() {
        return registerView;
    }

    @Provides
    public LoginView getLoginView() {
        return loginView;
    }

    @Provides
    public MainView getMainView() {
        return mainView;
    }
}
