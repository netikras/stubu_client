package com.netikras.studies.studentbuddy.api.client.android.conf.di.module;

import android.app.Application;
import android.content.Context;

import com.netikras.studies.studentbuddy.api.client.android.App;
import com.netikras.studies.studentbuddy.api.client.android.conf.di.ApplicationCtx;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by netikras on 17.10.30.
 */

@Module
public class ApplicationModule {

    private Application application;

    public ApplicationModule(Application application) {
        this.application = application;
    }

    @Singleton
    @Provides
    App app() {
        return (App) application;
    }

    @Singleton
    @Provides
    Application application() {
        return application;
    }

    @Singleton
    @ApplicationCtx
    @Provides
    Context applicationContext() {
        return application();
    }
}
