package com.netikras.studies.studentbuddy.api.client.android.conf.di.component;

import android.app.Activity;

import com.netikras.studies.studentbuddy.api.client.android.conf.di.module.ActivityModule;
import com.netikras.studies.studentbuddy.api.client.android.conf.di.module.ApiConsumerModule;
import com.netikras.studies.studentbuddy.api.client.android.conf.di.module.ApiHttpModule;
import com.netikras.studies.studentbuddy.api.client.android.conf.di.module.ApplicationModule;
import com.netikras.studies.studentbuddy.api.client.android.conf.di.module.DataModule;
import com.netikras.studies.studentbuddy.api.client.android.conf.di.module.PresenterModule;
import com.netikras.studies.studentbuddy.api.client.android.pieces.discipline.ui.impl.view.DisciplineInfoActivity;
import com.netikras.studies.studentbuddy.api.client.android.pieces.login.ui.presenter.LoginMvpPresenter;
import com.netikras.studies.studentbuddy.api.client.android.pieces.login.ui.impl.view.LoginActivity;
import com.netikras.studies.studentbuddy.api.client.android.pieces.login.ui.view.LoginMvpView;
import com.netikras.studies.studentbuddy.api.client.android.pieces.main.ui.impl.view.MainActivity;
import com.netikras.studies.studentbuddy.api.client.android.pieces.person.ui.impl.view.PersonInfoActivity;
import com.netikras.studies.studentbuddy.api.client.android.pieces.person.ui.impl.view.UserInfoActivity;
import com.netikras.studies.studentbuddy.api.client.android.pieces.settings.ui.impl.view.SettingsActivity;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by netikras on 17.10.30.
 */

@Singleton
@Component(
//        dependencies = {ApplicationComponent.class, PresenterComponent.class},
        modules = {ActivityModule.class, ApplicationModule.class, PresenterModule.class, DataModule.class, ApiConsumerModule.class, ApiHttpModule.class})
public interface ActivityComponent {

    void inject(Activity activity);

    void inject(DisciplineInfoActivity activity);

    void inject(LoginActivity loginActivity);

    void inject(UserInfoActivity activity);

    void inject(PersonInfoActivity activity);

    void inject(MainActivity activity);

    void inject(SettingsActivity settingsActivity);

//    LoginMvpPresenter<? extends LoginMvpView> loginPresenter();

//    LoginPresenter getLoginPresenter();

    LoginMvpPresenter<LoginMvpView> loginMvpViewLoginMvpPresenter();
}
