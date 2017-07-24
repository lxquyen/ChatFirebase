package com.furuichi.chatfirebase.ui.view;

import com.furuichi.chatfirebase.base.BaseView;
import com.furuichi.chatfirebase.models.User;

import java.util.List;

/**
 * Created by quyenlx on 7/22/2017.
 */

public interface MainView extends BaseView {

    void onGetAllUsersSuccess(List<User> users);

    void onGetAllUsersFailure(String message);

    void goToLoginScreen();

    void onLogoutSuccess(String message);

    void onLogoutFailure(String message);
}
