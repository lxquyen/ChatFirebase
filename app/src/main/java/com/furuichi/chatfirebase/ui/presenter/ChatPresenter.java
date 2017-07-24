package com.furuichi.chatfirebase.ui.presenter;

import com.furuichi.chatfirebase.common.Constants;
import com.furuichi.chatfirebase.fcm.FcmNotificationBuilder;
import com.furuichi.chatfirebase.manager.StorageManager;
import com.furuichi.chatfirebase.models.Chat;
import com.furuichi.chatfirebase.ui.view.ChatView;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import javax.inject.Inject;

import timber.log.Timber;

/**
 * Created by quyenlx on 7/23/2017.
 */

public class ChatPresenter {
    private ChatView view;
    private FirebaseDatabase firebaseDatabase;
    private StorageManager storageManager;

    @Inject
    public ChatPresenter(ChatView view, FirebaseDatabase firebaseDatabase, StorageManager storageManager) {
        this.view = view;
        this.firebaseDatabase = firebaseDatabase;
        this.storageManager = storageManager;
    }

    public void getMessage(String senderUid, String receiverUid) {
        view.onStartRequest();
        final String room_type_1 = senderUid + "_" + receiverUid;
        final String room_type_2 = receiverUid + "_" + senderUid;

        final DatabaseReference databaseReference = firebaseDatabase.getReference();
        databaseReference.child(Constants.ARG_CHAT_ROOMS).getRef().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChild(room_type_1)) {
                    firebaseDatabase
                            .getReference()
                            .child(Constants.ARG_CHAT_ROOMS)
                            .child(room_type_1).addChildEventListener(new ChildEventListener() {
                        @Override
                        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                            Chat chat = dataSnapshot.getValue(Chat.class);
                            view.onGetMessageSuccess(chat);
                        }

                        @Override
                        public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                        }

                        @Override
                        public void onChildRemoved(DataSnapshot dataSnapshot) {

                        }

                        @Override
                        public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            view.onFinishRequest();
                            view.onGetMessageFailure("Unable to get message: " + databaseError.getMessage());
                        }
                    });
                } else if (dataSnapshot.hasChild(room_type_2)) {
                    firebaseDatabase.getReference()
                            .child(Constants.ARG_CHAT_ROOMS)
                            .child(room_type_2).addChildEventListener(new ChildEventListener() {
                        @Override
                        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                            Chat chat = dataSnapshot.getValue(Chat.class);
                            view.onGetMessageSuccess(chat);
                        }

                        @Override
                        public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                        }

                        @Override
                        public void onChildRemoved(DataSnapshot dataSnapshot) {

                        }

                        @Override
                        public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            view.onFinishRequest();
                            view.onGetMessageFailure("Unable to get message: " + databaseError.getMessage());
                        }
                    });
                } else {
                    Timber.e("getMessageFromFirebaseUser: no such room available");
                }
                view.onFinishRequest();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                view.onFinishRequest();
                view.onGetMessageFailure("Unable to get message: " + databaseError.getMessage());
            }
        });

    }

    public void sendMessage(final Chat chat, final String receiverFirebaseToken) {
        final String room_type_1 = chat.senderUid + "_" + chat.receiverUid;
        final String room_type_2 = chat.receiverUid + "_" + chat.senderUid;
        final DatabaseReference databaseReference = firebaseDatabase.getReference();

        databaseReference.child(Constants.ARG_CHAT_ROOMS).getRef().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChild(room_type_1)) {
                    Timber.e("sendMessageToFirebaseUser: " + room_type_1 + " exists");
                    databaseReference
                            .child(Constants.ARG_CHAT_ROOMS)
                            .child(room_type_1)
                            .child(String.valueOf(chat.timestamp))
                            .setValue(chat);
                } else if (dataSnapshot.hasChild(room_type_2)) {
                    Timber.e("sendMessageToFirebaseUser: " + room_type_2 + " exists");
                    databaseReference
                            .child(Constants.ARG_CHAT_ROOMS)
                            .child(room_type_2)
                            .child(String.valueOf(chat.timestamp))
                            .setValue(chat);
                } else {
                    databaseReference
                            .child(Constants.ARG_CHAT_ROOMS)
                            .child(room_type_1)
                            .child(String.valueOf(chat.timestamp))
                            .setValue(chat);
                    getMessage(chat.senderUid, chat.receiverUid);
                }
                sendPushNotificationToReceiver(chat.sender,
                        chat.message,
                        chat.senderUid,
                        storageManager.getStringValue(Constants.ARG_FIREBASE_TOKEN),
                        receiverFirebaseToken);
                view.onSendMessageSuccess();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                view.onSendMessageFailure("Unable to send message: " + databaseError.getMessage());
            }
        });
    }

    private void sendPushNotificationToReceiver(String username,
                                                String message,
                                                String uid,
                                                String firebaseToken,
                                                String receiverFirebaseToken) {
        FcmNotificationBuilder.initialize()
                .title(username)
                .message(message)
                .username(username)
                .uid(uid)
                .firebaseToken(firebaseToken)
                .receiverFirebaseToken(receiverFirebaseToken)
                .send();
    }
}
