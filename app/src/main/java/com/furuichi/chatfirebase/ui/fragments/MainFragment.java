package com.furuichi.chatfirebase.ui.fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.furuichi.chatfirebase.App;
import com.furuichi.chatfirebase.R;
import com.furuichi.chatfirebase.base.BaseFragment;
import com.furuichi.chatfirebase.di.module.ViewModule;
import com.furuichi.chatfirebase.models.User;
import com.furuichi.chatfirebase.ui.activities.ChatActivity;
import com.furuichi.chatfirebase.ui.adapters.MainAdapter;
import com.furuichi.chatfirebase.ui.presenter.MainPresenter;
import com.furuichi.chatfirebase.ui.view.MainView;
import com.olddog.common.ToastUtils;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * Created by quyenlx on 7/22/2017.
 */

public class MainFragment extends BaseFragment implements MainView, SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.swipe_layout)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.recycler)
    RecyclerView recycler;


    private MainAdapter mAdapter;

    @Inject
    MainPresenter presenter;


    @Override
    public void injectDependence() {
        App
                .get()
                .getComponent()
                .plus(new ViewModule(this)).injectTo(this);

    }

    @Override
    public int getLayoutRes() {
        return R.layout.fragment_main;
    }

    @Override
    public void initView() {
        toolbar.setTitle(R.string.list_user);
        toolbar.inflateMenu(R.menu.menu_main);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                if (menuItem.getItemId() == R.id.action_logout) {
                    logout();
                }
                return false;
            }
        });
        swipeRefreshLayout.setOnRefreshListener(this);
        mAdapter = getAdapter();
        recycler.setLayoutManager(new LinearLayoutManager(mBaseActivity));
        recycler.setAdapter(mAdapter);
    }

    private MainAdapter getAdapter() {
        if (mAdapter == null) {
            mAdapter = createAdapter();
        }
        return mAdapter;
    }

    private MainAdapter createAdapter() {
        return new MainAdapter() {
            @Override
            public void onBindViewHolder(ViewHolder holder, final int position) {
                super.onBindViewHolder(holder, position);
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        User user = mAdapter.getUsers().get(position);
                        ChatActivity.start(
                                mBaseActivity,
                                user.email,
                                user.uid,
                                user.firebaseToken);
                    }
                });
            }
        };
    }

    @Override
    public void initData() {
        presenter.getListUser();
    }

    private void logout() {
        new AlertDialog.Builder(mBaseActivity)
                .setTitle(R.string.logout)
                .setMessage(R.string.are_you_sure)
                .setPositiveButton(R.string.logout, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        presenter.logout();
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .show();
    }


    @Override
    public void onGetAllUsersSuccess(List<User> users) {
        mAdapter.setUsers(users);
    }

    @Override
    public void onGetAllUsersFailure(String message) {
        ToastUtils.show(message);
    }

    @Override
    public void goToLoginScreen() {
        mBaseActivity.startActivity(LoginFragment.class);
        mBaseActivity.finish();
    }

    @Override
    public void onLogoutSuccess(String message) {
        ToastUtils.show(message);
        mBaseActivity.startActivity(LoginFragment.class,
                Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
    }

    @Override
    public void onLogoutFailure(String message) {
        ToastUtils.show(message);
    }

    @Override
    public void onRefresh() {
        presenter.getListUser();
    }

    @Override
    public void onStartRequest() {
        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(true);
            }
        });
    }

    @Override
    public void onFinishRequest() {
        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }
}
