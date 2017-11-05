package com.netikras.studies.studentbuddy.api.client.android.conf.di.module;

import com.netikras.studies.studentbuddy.api.client.android.data.DataManager;
import com.netikras.studies.studentbuddy.api.client.android.ui.login.presenter.LoginMvpPresenter;
import com.netikras.studies.studentbuddy.api.client.android.ui.login.presenter.LoginPresenter;
import com.netikras.studies.studentbuddy.api.client.android.ui.login.view.LoginMvpView;
import com.netikras.studies.studentbuddy.api.client.android.ui.person.presenter.PersonMvpPresenter;
import com.netikras.studies.studentbuddy.api.client.android.ui.person.presenter.PersonPresenter;
import com.netikras.studies.studentbuddy.api.client.android.ui.person.presenter.UserMvpPresenter;
import com.netikras.studies.studentbuddy.api.client.android.ui.person.presenter.UserPresenter;
import com.netikras.studies.studentbuddy.api.client.android.ui.person.view.PersonMvpView;
import com.netikras.studies.studentbuddy.api.client.android.ui.person.view.UserMvpView;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by netikras on 17.11.1.
 */

@Module
public class PresenterModule {

//    @Singleton
    @Provides
    UserMvpPresenter<UserMvpView> userMvpPresenter(UserPresenter<UserMvpView> userPresenter) {
        return userPresenter;
    }

//    @Singleton
    @Provides
    PersonMvpPresenter<PersonMvpView> personPresenter(PersonPresenter<PersonMvpView> personPresenter) {
        return personPresenter;
    }
    @Provides
    LoginMvpPresenter<LoginMvpView> loginPresenter(LoginPresenter<LoginMvpView> loginPresenter) {
        return loginPresenter;
    }


}
