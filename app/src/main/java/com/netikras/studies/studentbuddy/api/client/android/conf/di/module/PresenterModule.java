package com.netikras.studies.studentbuddy.api.client.android.conf.di.module;

import com.netikras.studies.studentbuddy.api.client.android.ui.discipline.presenter.DisciplineMvpPresenter;
import com.netikras.studies.studentbuddy.api.client.android.ui.discipline.impl.presenter.DisciplinePresenter;
import com.netikras.studies.studentbuddy.api.client.android.ui.discipline.view.DisciplineMvpView;
import com.netikras.studies.studentbuddy.api.client.android.ui.login.presenter.LoginMvpPresenter;
import com.netikras.studies.studentbuddy.api.client.android.ui.login.impl.presenter.LoginPresenter;
import com.netikras.studies.studentbuddy.api.client.android.ui.login.view.LoginMvpView;
import com.netikras.studies.studentbuddy.api.client.android.ui.person.presenter.PersonMvpPresenter;
import com.netikras.studies.studentbuddy.api.client.android.ui.person.impl.presenter.PersonPresenter;
import com.netikras.studies.studentbuddy.api.client.android.ui.person.presenter.UserMvpPresenter;
import com.netikras.studies.studentbuddy.api.client.android.ui.person.impl.presenter.UserPresenter;
import com.netikras.studies.studentbuddy.api.client.android.ui.person.view.PersonMvpView;
import com.netikras.studies.studentbuddy.api.client.android.ui.person.view.UserMvpView;
import com.netikras.studies.studentbuddy.api.client.android.ui.settings.presenter.SettingsMvpPresenter;
import com.netikras.studies.studentbuddy.api.client.android.ui.settings.impl.presenter.SettingsPresenter;
import com.netikras.studies.studentbuddy.api.client.android.ui.settings.view.SettingsMvpView;

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

    @Provides
    SettingsMvpPresenter<SettingsMvpView> settingsPresenter(SettingsPresenter<SettingsMvpView> presenter) {
        return presenter;
    }

    @Provides
    DisciplineMvpPresenter<DisciplineMvpView> settingsPresenter(DisciplinePresenter<DisciplineMvpView> presenter) {
        return presenter;
    }


}
