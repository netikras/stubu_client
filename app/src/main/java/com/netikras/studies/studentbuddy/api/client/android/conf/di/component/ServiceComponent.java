
package com.netikras.studies.studentbuddy.api.client.android.conf.di.component;

import com.netikras.studies.studentbuddy.api.client.android.conf.di.PerService;
import com.netikras.studies.studentbuddy.api.client.android.conf.di.module.ServiceModule;
import com.netikras.studies.studentbuddy.api.client.android.service.ApiService;

import dagger.Component;

/**
 * Created by janisharali on 01/02/17.
 */
@PerService
@Component(dependencies = ApplicationComponent.class, modules = ServiceModule.class)
public interface ServiceComponent {

    void inject(ApiService service);

}
