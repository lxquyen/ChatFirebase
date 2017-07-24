package com.furuichi.chatfirebase.ui.fragments;

import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.furuichi.chatfirebase.App;
import com.furuichi.chatfirebase.R;
import com.furuichi.chatfirebase.base.BaseFragment;
import com.furuichi.chatfirebase.di.module.ViewModule;
import com.furuichi.chatfirebase.events.PushNotificationEvent;
import com.furuichi.chatfirebase.models.Chat;
import com.furuichi.chatfirebase.ui.adapters.ChatAdapter;
import com.furuichi.chatfirebase.ui.presenter.ChatPresenter;
import com.furuichi.chatfirebase.ui.view.ChatView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.olddog.common.ToastUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * Created by quyenlx on 7/23/2017.
 */

public class ChatFragment extends BaseFragment implements ChatView {
    @BindView(R.id.recycler)
    RecyclerView recycler;
    @BindView(R.id.edit_text_message)
    EditText editTextMessage;

    @Inject
    EventBus bus;
    @Inject
    ChatPresenter presenter;
    @Inject
    FirebaseAuth firebaseAuth;

    private FirebaseUser mFirebaseUser;
    private ChatAdapter mAdapter;

    public static ChatFragment newInstance(String receiver,
                                           String receiverUid,
                                           String firebaseToken) {
        ChatFragment fragment = new ChatFragment();
        fragment.receiver = receiver;
        fragment.receiverUid = receiverUid;
        fragment.receiverFirebaseToken = firebaseToken;
        return fragment;
    }

    private String receiver;
    private String receiverUid;
    private String receiverFirebaseToken;

    @Override
    public void injectDependence() {
        App.get().getComponent().plus(new ViewModule(this)).injectTo(this);
    }

    @Override
    public int getLayoutRes() {
        return R.layout.fragment_chat;
    }

    @Override
    public void initView() {
        editTextMessage.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == EditorInfo.IME_ACTION_SEND) {
                    sendMessage();
                    return true;
                }
                return false;
            }
        });
        mAdapter = getAdapter();
        recycler.setAdapter(mAdapter);
    }

    private ChatAdapter getAdapter() {
        if (mAdapter == null) {
            mAdapter = createAdapter();
        }
        return mAdapter;
    }

    private ChatAdapter createAdapter() {
        return new ChatAdapter(firebaseAuth);
    }

    @Override
    public void initData() {
        mFirebaseUser = firebaseAuth.getCurrentUser();
        presenter.getMessage(mFirebaseUser.getUid(), receiverUid);
    }

    private void sendMessage() {
        String message = editTextMessage.getText().toString();
        String sender = mFirebaseUser.getEmail();
        String senderUid = mFirebaseUser.getUid();
        Chat chat = new Chat(sender, receiver, senderUid, receiverUid, message, System.currentTimeMillis());
        presenter.sendMessage(chat, receiverFirebaseToken);
    }

    @Override
    public void onStart() {
        super.onStart();
        bus.register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        bus.unregister(this);
    }

    @Override
    public void onStartRequest() {
        mBaseActivity.showLoading();
    }

    @Override
    public void onFinishRequest() {
        mBaseActivity.hideLoading();
    }

    @Override
    public void onSendMessageSuccess() {
        editTextMessage.setText("");
    }

    @Override
    public void onSendMessageFailure(String message) {
        ToastUtils.show(message);
    }

    @Override
    public void onGetMessageSuccess(Chat chat) {
        mAdapter.add(chat);
        if (recycler != null)
            recycler.smoothScrollToPosition(mAdapter.getItemCount() - 1);
    }

    @Override
    public void onGetMessageFailure(String message) {
        ToastUtils.show(message);
    }


    @Subscribe
    public void onPushNotificationEvent(PushNotificationEvent pushNotificationEvent) {
        if (mAdapter == null || mAdapter.getItemCount() == 0) {
            presenter.getMessage(FirebaseAuth.getInstance().getCurrentUser().getUid(),
                    pushNotificationEvent.getUid());
        }
    }


}
