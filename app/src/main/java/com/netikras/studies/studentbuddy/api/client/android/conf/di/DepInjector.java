package com.netikras.studies.studentbuddy.api.client.android.conf.di;

import android.app.Activity;
import android.app.Application;

import com.netikras.studies.studentbuddy.api.client.android.conf.di.component.ActivityComponent;
import com.netikras.studies.studentbuddy.api.client.android.conf.di.component.ApplicationComponent;
import com.netikras.studies.studentbuddy.api.client.android.conf.di.component.DaggerActivityComponent;
import com.netikras.studies.studentbuddy.api.client.android.conf.di.component.DaggerApplicationComponent;
import com.netikras.studies.studentbuddy.api.client.android.conf.di.component.DaggerPresenterComponent;
import com.netikras.studies.studentbuddy.api.client.android.conf.di.component.PresenterComponent;
import com.netikras.studies.studentbuddy.api.client.android.conf.di.module.ActivityModule;
import com.netikras.studies.studentbuddy.api.client.android.conf.di.module.ApplicationModule;
import com.netikras.studies.studentbuddy.api.client.android.conf.di.module.DataModule;
import com.netikras.studies.studentbuddy.api.client.android.conf.di.module.PresenterModule;
import com.netikras.studies.studentbuddy.api.client.android.ui.base.MvpPresenter;
import com.netikras.studies.studentbuddy.api.client.android.ui.discipline.impl.view.DisciplineInfoActivity;
import com.netikras.studies.studentbuddy.api.client.android.ui.login.impl.presenter.LoginPresenter;
import com.netikras.studies.studentbuddy.api.client.android.ui.login.impl.view.LoginActivity;
import com.netikras.studies.studentbuddy.api.client.android.ui.main.impl.presenter.MainPresenter;
import com.netikras.studies.studentbuddy.api.client.android.ui.person.impl.presenter.PersonPresenter;
import com.netikras.studies.studentbuddy.api.client.android.ui.person.impl.presenter.UserPresenter;
import com.netikras.studies.studentbuddy.api.client.android.ui.person.impl.view.PersonInfoActivity;
import com.netikras.studies.studentbuddy.api.client.android.ui.person.impl.view.UserInfoActivity;
import com.netikras.studies.studentbuddy.api.client.android.ui.settings.impl.presenter.SettingsPresenter;
import com.netikras.studies.studentbuddy.api.client.android.ui.settings.impl.view.SettingsActivity;

/**
 * Created by netikras on 17.10.30.
 */

public class DepInjector {

    private static ApplicationComponent applicationComponent;
    private static ActivityComponent activityComponent;
    private static PresenterComponent presenterComponent;


    public static ApplicationComponent getApplicationComponent() {
        if (applicationComponent == null) {
            throw new IllegalStateException("Application is not injected!");
        }
        return applicationComponent;
    }

    public static ActivityComponent getActivityComponent() {
        if (activityComponent == null) {
            throw new IllegalStateException("Activity is not injected");
        }
        return activityComponent;
    }

    public static PresenterComponent getPresenterComponent() {
        if (presenterComponent == null) {
            throw new IllegalStateException("Presenter is not injected");
        }
        return presenterComponent;
    }





    private static ApplicationComponent getApplicationComponent(Application application) {
        if (applicationComponent == null) {
            applicationComponent = DaggerApplicationComponent.builder()
                    .applicationModule(new ApplicationModule(application))
                    .build();
        }
        applicationComponent.inject(application);
        return applicationComponent;
    }

    private static ActivityComponent getActivityComponent(Activity activity) {
        if (activityComponent == null) {
            activityComponent = DaggerActivityComponent.builder()
                    .activityModule(new ActivityModule(activity))
//                    .applicationComponent(getApplicationComponent())
                    .applicationModule(new ApplicationModule(getApplicationComponent().application()))
                    .presenterModule(new PresenterModule())
                    .dataModule(new DataModule())
                    .build();
        }
        return activityComponent;
    }

    private static PresenterComponent getPresenterComponent(MvpPresenter presenter) {
        if (presenterComponent == null) {
            presenterComponent = DaggerPresenterComponent.builder()
                    .build();
        }
        return presenterComponent;
    }





    public static ApplicationComponent inject(Application app) {
        getApplicationComponent(app).inject(app);
        return getApplicationComponent();
    }

    public static ActivityComponent inject(Activity activity) {
        getActivityComponent(activity).inject(activity);
        return getActivityComponent();
    }

    public static ActivityComponent inject(DisciplineInfoActivity activity) {
        getActivityComponent(activity).inject(activity);
        return getActivityComponent();
    }

    public static ActivityComponent inject(UserInfoActivity activity) {
        getActivityComponent(activity).inject(activity);
        return getActivityComponent();
    }

    public static ActivityComponent inject(PersonInfoActivity activity) {
        getActivityComponent(activity).inject(activity);
        return getActivityComponent();
    }

    public static ActivityComponent inject(LoginActivity activity) {
        getActivityComponent(activity).inject(activity);
        return getActivityComponent();
    }

    public static ActivityComponent inject(SettingsActivity activity) {
        getActivityComponent(activity).inject(activity);
        return getActivityComponent();
    }





    public static PresenterComponent inject(MainPresenter presenter) {
        getPresenterComponent(presenter).inject(presenter);
        return getPresenterComponent();
    }

    public static PresenterComponent inject(LoginPresenter presenter) {
        getPresenterComponent(presenter).inject(presenter);
        return getPresenterComponent();
    }

    public static PresenterComponent inject(UserPresenter presenter) {
        getPresenterComponent(presenter).inject(presenter);
        return getPresenterComponent();
    }

    public static PresenterComponent inject(PersonPresenter presenter) {
        getPresenterComponent(presenter).inject(presenter);
        return getPresenterComponent();
    }

    public static PresenterComponent inject(SettingsPresenter presenter) {
        getPresenterComponent(presenter).inject(presenter);
        return getPresenterComponent();
    }



}
