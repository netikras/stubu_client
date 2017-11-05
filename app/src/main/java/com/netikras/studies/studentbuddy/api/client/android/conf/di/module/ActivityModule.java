package com.netikras.studies.studentbuddy.api.client.android.conf.di.module;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;

import com.netikras.studies.studentbuddy.api.client.android.ui.person.presenter.UserMvpPresenter;
import com.netikras.studies.studentbuddy.api.client.android.ui.person.presenter.UserPresenter;
import com.netikras.studies.studentbuddy.api.client.android.ui.person.view.UserInfoActivity;
import com.netikras.studies.studentbuddy.api.client.android.ui.person.view.UserMvpView;

import dagger.Module;
import dagger.Provides;

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
