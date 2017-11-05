package com.netikras.studies.studentbuddy.api.client.android.conf.di.component;

import com.netikras.studies.studentbuddy.api.client.android.conf.di.PerPresenter;
import com.netikras.studies.studentbuddy.api.client.android.conf.di.module.ApiConsumerModule;
import com.netikras.studies.studentbuddy.api.client.android.conf.di.module.ApiHttpModule;
import com.netikras.studies.studentbuddy.api.client.android.conf.di.module.ApplicationModule;
import com.netikras.studies.studentbuddy.api.client.android.conf.di.module.DataModule;
import com.netikras.studies.studentbuddy.api.client.android.conf.di.module.PresenterModule;
import com.netikras.studies.studentbuddy.api.client.android.ui.base.MvpPresenter;
import com.netikras.studies.studentbuddy.api.client.android.ui.base.MvpView;
import com.netikras.studies.studentbuddy.api.client.android.ui.login.presenter.LoginMvpPresenter;
import com.netikras.studies.studentbuddy.api.client.android.ui.login.presenter.LoginPresenter;
import com.netikras.studies.studentbuddy.api.client.android.ui.login.view.LoginActivity;
import com.netikras.studies.studentbuddy.api.client.android.ui.login.view.LoginMvpView;
import com.netikras.studies.studentbuddy.api.client.android.ui.main.presenter.MainPresenter;
import com.netikras.studies.studentbuddy.api.client.android.ui.main.view.MainActivity;
import com.netikras.studies.studentbuddy.api.client.android.ui.person.presenter.PersonPresenter;
import com.netikras.studies.studentbuddy.api.client.android.ui.person.presenter.UserPresenter;
import com.netikras.studies.studentbuddy.api.client.android.ui.person.view.PersonInfoActivity;
import com.netikras.studies.studentbuddy.api.client.android.ui.person.view.PersonMvpView;
import com.netikras.studies.studentbuddy.api.client.android.ui.person.view.UserInfoActivity;
import com.netikras.studies.studentbuddy.api.client.android.ui.person.view.UserMvpView;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by netikras on 17.11.1.
 */

@PerPresenter
@Component(
//        dependencies = {ApplicationComponent.class},
        modules = {ApplicationModule.class, PresenterModule.class, DataModule.class, ApiHttpModule.class, ApiConsumerModule.class}
        )
public interface PresenterComponent {

    void inject(LoginPresenter<LoginActivity> presenter);

    void inject(MainPresenter<MainActivity> presenter);

    void inject(UserPresenter<UserInfoActivity> presenter);

    void inject(PersonPresenter<PersonInfoActivity> presenter);

}
