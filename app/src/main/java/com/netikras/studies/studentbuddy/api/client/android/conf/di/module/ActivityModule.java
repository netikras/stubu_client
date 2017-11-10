package com.netikras.studies.studentbuddy.api.client.android.conf.di.module;

import android.app.Activity;

import dagger.Module;

/**
 * Created by netikras on 17.10.30.
 */

@Module
public class ActivityModule {

    private Activity activity;

    public ActivityModule(Activity activity) {
        this.activity = activity;
    }

    

}
