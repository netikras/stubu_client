package com.netikras.studies.studentbuddy.api.client.android.pieces.login.ui.impl.presenter;

import android.content.Context;

import com.netikras.studies.studentbuddy.api.client.android.App;
import com.netikras.studies.studentbuddy.api.client.android.conf.di.module.carrier.RestConfig;
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
import com.netikras.studies.studentbuddy.api.client.android.service.ServiceRequest;
import com.netikras.studies.studentbuddy.api.client.android.service.ServiceRequest.Subscriber;
import com.netikras.studies.studentbuddy.core.data.api.dto.meta.UserDto;
import com.netikras.tools.common.exception.ErrorsCollection;

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
    public void proceedLogin(String username, String password, Subscriber<UserDto> subscriber) {


        getDataStore().login(username, password, new Subscriber<UserDto>() {
            @Override
            public void onSuccess(UserDto response) {
                app.setCurrentUser(response);
//                getLandingPresenter().startView((Context) getMvpView());
                if (subscriber != null) {
                    subscriber.onSuccess(response);
                }
            }

            @Override
            public void onError(ErrorsCollection errors) {
                if (subscriber != null) {
                    subscriber.onError(errors);
                } else {
                    if (getMvpView() != null) {
                        getMvpView().onError((Context) getMvpView(), errors.buildSingleMessage());
                    }

                }
            }
        });
        getDataStore().processOrders((Context) getMvpView());
    }

    @Override
    public void logout(Subscriber<Boolean> subscriber, UserDto userDto) {
        getDataStore().logout(subscriber);
        getDataStore().processOrders(getContext());
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
