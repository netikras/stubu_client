package com.netikras.studies.studentbuddy.api.client.android.conf.di.module;

import com.netikras.studies.studentbuddy.api.client.android.data.DataManager;
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

    @Singleton
    @Provides
    UserMvpPresenter<? extends UserMvpView> userMvpPresenter(DataManager dataManager) {
        return new UserPresenter<>(dataManager);
    }

    @Singleton
    @Provides
    PersonMvpPresenter<? extends PersonMvpView> personPresenter(DataManager dataManager) {
        return new PersonPresenter<>(dataManager);
    }



}
