package com.netikras.studies.studentbuddy.api.client.android.conf.di.component;

import android.app.Application;
import android.content.Context;

import com.netikras.studies.studentbuddy.api.client.android.App;
import com.netikras.studies.studentbuddy.api.client.android.conf.di.ApplicationCtx;
import com.netikras.studies.studentbuddy.api.client.android.conf.di.module.ApplicationModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by netikras on 17.10.30.
 */

@Singleton
@Component (modules = ApplicationModule.class)
public interface ApplicationComponent {

    void inject(App app);

    void inject(Application application);

    @ApplicationCtx
    Context context();

    Application application();


}
