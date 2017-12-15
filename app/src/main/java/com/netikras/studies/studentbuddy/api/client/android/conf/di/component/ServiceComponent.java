
package com.netikras.studies.studentbuddy.api.client.android.conf.di.component;

import com.netikras.studies.studentbuddy.api.client.android.conf.di.PerService;
import com.netikras.studies.studentbuddy.api.client.android.conf.di.module.ApiConsumerModule;
import com.netikras.studies.studentbuddy.api.client.android.conf.di.module.ApiHttpModule;
import com.netikras.studies.studentbuddy.api.client.android.conf.di.module.ApplicationModule;
import com.netikras.studies.studentbuddy.api.client.android.conf.di.module.DataModule;
import com.netikras.studies.studentbuddy.api.client.android.conf.di.module.ServiceModule;
import com.netikras.studies.studentbuddy.api.client.android.service.ApiService;
import com.netikras.studies.studentbuddy.api.client.android.service.ScheduledUpdateService;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by janisharali on 01/02/17.
 */
//@PerService
@Singleton
@Component(modules = {ApplicationModule.class, ServiceModule.class, DataModule.class, ApiHttpModule.class, ApiConsumerModule.class})
public interface ServiceComponent {

    void inject(ApiService service);

    void inject(ScheduledUpdateService service);

}
