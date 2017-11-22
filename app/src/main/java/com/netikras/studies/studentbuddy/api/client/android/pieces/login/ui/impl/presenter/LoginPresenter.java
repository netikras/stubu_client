package com.netikras.studies.studentbuddy.api.client.android.pieces.login.ui.impl.presenter;

import android.content.Context;

import com.netikras.studies.studentbuddy.api.client.android.App;
import com.netikras.studies.studentbuddy.api.client.android.data.DataManager;
import com.netikras.studies.studentbuddy.api.client.android.data.prefs.PreferencesHelper;
import com.netikras.studies.studentbuddy.api.client.android.pieces.person.data.UserDataStore;
import com.netikras.studies.studentbuddy.api.client.android.pieces.base.BasePresenter;
import com.netikras.studies.studentbuddy.api.client.android.pieces.base.MvpPresenter;
import com.netikras.studies.studentbuddy.api.client.android.pieces.login.ui.impl.view.LoginActivity;
import com.netikras.studies.studentbuddy.api.client.android.pieces.login.ui.presenter.LoginMvpPresenter;
import com.netikras.studies.studentbuddy.api.client.android.pieces.login.ui.view.LoginMvpView;
import com.netikras.studies.studentbuddy.api.client.android.pieces.person.ui.presenter.UserMvpPresenter;
import com.netikras.studies.studentbuddy.api.client.android.pieces.person.ui.view.UserMvpView;
import com.netikras.studies.studentbuddy.core.data.api.dto.meta.UserDto;

import javax.inject.Inject;

/**
 * Created by netikras on 17.11.1.
 */

public class LoginPresenter<V extends LoginMvpView> extends BasePresenter<V> implements LoginMvpPresenter<V> {
    @Inject
    App app;

    @Inject
    UserMvpPresenter<UserMvpView> userPresenter;

    @Inject
    PreferencesHelper preferences;

    @Inject
    public LoginPresenter(DataManager dataManager) {
        super(dataManager);
    }

    private UserDataStore getDataStore() {
        return getDataManager().getStore(UserDataStore.class);
    }


    @Override
    public void proceedLogin(String username, String password) {
        getDataStore().login(username, password, new ErrorsAwareSubscriber<UserDto>() {
            @Override
            public void onSuccess(UserDto response) {
                app.setCurrentUser(response);
                getLandingPresenter().startView((Context) getMvpView());
            }
        });
        getDataStore().processOrders((Context) getMvpView());
    }

    @Override
    public String getLastLoginUsername() {
        return preferences.getLoginUsername();
    }

    @Override
    public void setLastLoginUsername(String username) {
        preferences.setLoginUsername(username);
    }

    @Override
    public void startView(Context fromContext) {
        startView(fromContext, LoginActivity.class);
    }

    private MvpPresenter getLandingPresenter() {
        return userPresenter;
    }
}
