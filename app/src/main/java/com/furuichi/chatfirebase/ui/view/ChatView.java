package com.furuichi.chatfirebase.ui.view;

import com.furuichi.chatfirebase.base.BaseView;
import com.furuichi.chatfirebase.models.Chat;

/**
 * Created by quyenlx on 7/23/2017.
 */

public interface ChatView extends BaseView {

    void onSendMessageSuccess();

    void onSendMessageFailure(String message);

    void onGetMessageSuccess(Chat chat);

    void onGetMessageFailure(String message);
}
