package com.netikras.studies.studentbuddy.api.client.android.conf.di.component;

import com.netikras.studies.studentbuddy.api.client.android.conf.di.PerPresenter;
import com.netikras.studies.studentbuddy.api.client.android.conf.di.module.ApiConsumerModule;
import com.netikras.studies.studentbuddy.api.client.android.conf.di.module.ApiHttpModule;
import com.netikras.studies.studentbuddy.api.client.android.conf.di.module.ApplicationModule;
import com.netikras.studies.studentbuddy.api.client.android.conf.di.module.DataModule;
import com.netikras.studies.studentbuddy.api.client.android.conf.di.module.PresenterModule;
import com.netikras.studies.studentbuddy.api.client.android.pieces.login.ui.impl.presenter.LoginPresenter;
import com.netikras.studies.studentbuddy.api.client.android.pieces.login.ui.impl.view.LoginActivity;
import com.netikras.studies.studentbuddy.api.client.android.pieces.main.ui.impl.presenter.MainPresenter;
import com.netikras.studies.studentbuddy.api.client.android.pieces.main.ui.impl.view.MainActivity;
import com.netikras.studies.studentbuddy.api.client.android.pieces.person.ui.impl.presenter.PersonPresenter;
import com.netikras.studies.studentbuddy.api.client.android.pieces.person.ui.impl.presenter.UserPresenter;
import com.netikras.studies.studentbuddy.api.client.android.pieces.person.ui.impl.view.PersonInfoActivity;
import com.netikras.studies.studentbuddy.api.client.android.pieces.person.ui.impl.view.UserInfoActivity;
import com.netikras.studies.studentbuddy.api.client.android.pieces.settings.ui.impl.presenter.SettingsPresenter;
import com.netikras.studies.studentbuddy.api.client.android.pieces.settings.ui.impl.view.SettingsActivity;

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

    void inject(SettingsPresenter<SettingsActivity> presenter);

}
