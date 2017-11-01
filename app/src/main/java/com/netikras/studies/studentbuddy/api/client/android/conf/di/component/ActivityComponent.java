package com.netikras.studies.studentbuddy.api.client.android.conf.di.component;

import com.netikras.studies.studentbuddy.api.client.android.conf.di.PerActivity;
import com.netikras.studies.studentbuddy.api.client.android.conf.di.module.ActivityModule;
import com.netikras.studies.studentbuddy.api.client.android.ui.person.view.UserInfoActivity;

import dagger.Component;

/**
 * Created by netikras on 17.10.30.
 */

@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {

//     void inject(UserInfoActivity activity);
//
//     void inject(PerActivity activity);
}
