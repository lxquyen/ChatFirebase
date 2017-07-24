package com.furuichi.chatfirebase.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;

import com.furuichi.chatfirebase.App;
import com.furuichi.chatfirebase.base.BaseActivity;
import com.furuichi.chatfirebase.base.BaseFragment;
import com.furuichi.chatfirebase.common.Constants;
import com.furuichi.chatfirebase.ui.fragments.ChatFragment;

/**
 * Created by quyenlx on 7/23/2017.
 */

public class ChatActivity extends BaseActivity {
    public static void start(Context context,
                             String receiver,
                             String receiverUid,
                             String firebaseToken) {
        Intent intent = new Intent(context, ChatActivity.class);
        intent.putExtra(Constants.ARG_RECEIVER, receiver);
        intent.putExtra(Constants.ARG_RECEIVER_UID, receiverUid);
        intent.putExtra(Constants.ARG_FIREBASE_TOKEN, firebaseToken);
        context.startActivity(intent);
    }

    @Override
    protected BaseFragment initFragment() {
        Bundle bundle = getIntent().getExtras();
        return ChatFragment.newInstance(
                bundle.getString(Constants.ARG_RECEIVER),
                bundle.getString(Constants.ARG_RECEIVER_UID),
                bundle.getString(Constants.ARG_FIREBASE_TOKEN));
    }

    @Override
    protected void onResume() {
        super.onResume();
        App.get().setChatActivityOpen(true);
    }

    @Override
    protected void onPause() {
        super.onPause();
        App.get().setChatActivityOpen(false);
    }
}
