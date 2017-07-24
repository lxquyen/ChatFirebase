package com.furuichi.chatfirebase.ui.adapters;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.furuichi.chatfirebase.R;
import com.furuichi.chatfirebase.models.Chat;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by quyenlx on 7/23/2017.
 */

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.BaseViewHolder> {
    private static final int VIEW_TYPE_ME = 1;
    private static final int VIEW_TYPE_OTHER = 2;


    private List<Chat> mChats = new ArrayList<>();
    private FirebaseAuth firebaseAuth;

    public ChatAdapter(FirebaseAuth firebaseAuth) {
        this.firebaseAuth = firebaseAuth;
    }

    public void add(Chat chat) {
        mChats.add(chat);
        notifyItemChanged(mChats.size() - 1);
    }

    public List<Chat> getChats() {
        return mChats;
    }

    public void setChats(List<Chat> mChats) {
        this.mChats = mChats;
    }

    @Override
    public ChatAdapter.BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ChatAdapter.BaseViewHolder viewHolder = null;
        switch (viewType) {
            case VIEW_TYPE_ME:
                View viewChatMine = layoutInflater.inflate(R.layout.item_chat_mine, parent, false);
                viewHolder = new MyChatViewHolder(viewChatMine);
                break;
            case VIEW_TYPE_OTHER:
                View viewChatOther = layoutInflater.inflate(R.layout.item_chat_other, parent, false);
                viewHolder = new OtherChatViewHolder(viewChatOther);
                break;
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ChatAdapter.BaseViewHolder holder, int position) {
        holder.bind(mChats.get(position));
    }

    @Override
    public int getItemCount() {
        return mChats.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (TextUtils.equals(mChats.get(position).senderUid, firebaseAuth.getCurrentUser().getUid())) {
            return VIEW_TYPE_ME;
        } else {
            return VIEW_TYPE_OTHER;
        }
    }

    public class BaseViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.text_view_chat_message)
        TextView textViewChatMessage;
        @BindView(R.id.text_view_user_alphabet)
        TextView textViewUserAlphabet;

        public BaseViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bind(Chat chat) {
            String alphabet = chat.sender.substring(0, 1);
            textViewChatMessage.setText(chat.message);
            textViewUserAlphabet.setText(alphabet);
        }
    }

    public class MyChatViewHolder extends BaseViewHolder {
        public MyChatViewHolder(View itemView) {
            super(itemView);
        }
    }

    public class OtherChatViewHolder extends BaseViewHolder {
        public OtherChatViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
