package com.netikras.studies.studentbuddy.api.client.android.conf.di;

import android.app.Activity;
import android.app.Application;
import android.app.Service;

import com.netikras.studies.studentbuddy.api.client.android.conf.di.component.ApplicationComponent;
import com.netikras.studies.studentbuddy.api.client.android.conf.di.component.DaggerApplicationComponent;
import com.netikras.studies.studentbuddy.api.client.android.conf.di.module.ApplicationModule;
import com.netikras.studies.studentbuddy.api.client.android.data.DataManager;
import com.netikras.studies.studentbuddy.api.client.android.ui.base.MvpPresenter;
import com.netikras.studies.studentbuddy.api.client.android.ui.base.MvpView;

/**
 * Created by netikras on 17.10.30.
 */

public class DepInjector {

    private static ApplicationComponent applicationComponent;


    private static ApplicationComponent getApplicationComponent() {
        if (applicationComponent == null) {
            throw new IllegalStateException("Application is not injected!");
        }
        return applicationComponent;
    }

    public static ApplicationComponent inject(Application app) {
        if (applicationComponent == null) {
            applicationComponent = DaggerApplicationComponent.builder()
                    .applicationModule(new ApplicationModule(app))
                    .build();
        }
        return applicationComponent;
    }

    public static void inject(Service service) {

    }

    public static void inject(Activity activity) {

    }

    public static void inject(MvpView view) {

    }

    public static void inject(MvpPresenter presenter) {

    }
}
