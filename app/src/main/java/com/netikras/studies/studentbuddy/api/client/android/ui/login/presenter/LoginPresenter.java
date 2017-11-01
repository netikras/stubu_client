package com.netikras.studies.studentbuddy.api.client.android.ui.login.presenter;

import android.content.Context;

import com.netikras.studies.studentbuddy.api.client.android.App;
import com.netikras.studies.studentbuddy.api.client.android.data.DataManager;
import com.netikras.studies.studentbuddy.api.client.android.data.stores.user.UserDataStore;
import com.netikras.studies.studentbuddy.api.client.android.ui.base.BasePresenter;
import com.netikras.studies.studentbuddy.api.client.android.ui.base.MvpPresenter;
import com.netikras.studies.studentbuddy.api.client.android.ui.login.view.LoginActivity;
import com.netikras.studies.studentbuddy.api.client.android.ui.person.presenter.UserMvpPresenter;
import com.netikras.studies.studentbuddy.api.client.android.ui.person.view.UserMvpView;
import com.netikras.studies.studentbuddy.core.data.api.dto.meta.UserDto;

import javax.inject.Inject;

/**
 * Created by netikras on 17.11.1.
 */

public class LoginPresenter extends BasePresenter<LoginActivity> implements LoginMvpPresenter<LoginActivity> {
    @Inject
    App app;

    @Inject
    UserMvpPresenter<? extends UserMvpView> userPresenter;

    @Inject
    public LoginPresenter(DataManager dataManager) {
        super(dataManager);
    }

    private UserDataStore getDatastore() {
        return getDataManager().getStore(UserDataStore.class);
    }


    @Override
    public void proceedLogin(String username, String password) {
        getDatastore().login(username, password, new ErrorsAwareSubscriber<UserDto>() {
            @Override
            public void onSuccess(UserDto response) {
                app.setCurrentUser(response);
                getLandingPresenter().startView(getMvpView());
            }
        });
    }

    @Override
    public void startView(Context fromContext) {
        startView(fromContext, LoginActivity.class);
    }

    private MvpPresenter getLandingPresenter() {
        return userPresenter;
    }
}
