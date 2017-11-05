package com.netikras.studies.studentbuddy.api.client.android.conf.di.module;

import com.netikras.studies.studentbuddy.api.client.android.conf.di.module.carrier.RestConfig;
import com.netikras.studies.studentbuddy.api.user.generated.PersonApiConsumer;
import com.netikras.studies.studentbuddy.api.user.generated.UserApiConsumer;
import com.netikras.studies.studentbuddy.api.user.mgmt.generated.AdminPersonApiConsumer;
import com.netikras.studies.studentbuddy.api.user.mgmt.generated.AdminUserApiConsumer;
import com.netikras.tools.common.remote.RemoteEndpointServer;
import com.netikras.tools.common.remote.http.GenericRestConsumer;
import com.netikras.tools.common.remote.http.RequestListener;
import com.netikras.tools.common.remote.http.SessionContext;

import java.util.Map;

import dagger.Module;
import dagger.Provides;

/**
 * Created by netikras on 17.11.2.
 */

@Module
public class ApiConsumerModule {


    private <C extends GenericRestConsumer> C prepare(C consumer, RestConfig config) {

        consumer.setServiceProvider(config.getRestServiceProvider());

        if (config.getListeners() != null) {
            for (Map.Entry<String, RequestListener> listenerEntry : config.getListeners().entrySet()) {
                consumer.addListener(listenerEntry.getKey(), listenerEntry.getValue());
            }
        }

        if (config.getServers() != null) {
            for (Map.Entry<String, RemoteEndpointServer> serverEntry : config.getServers().entrySet()) {
                consumer.addServer(serverEntry.getKey(), serverEntry.getValue());
            }
        }

        consumer.setSessionContext(config.getSessionContext());

        return consumer;
    }


    @Provides
    public PersonApiConsumer personApiConsumer(RestConfig config) {
        return prepare(new PersonApiConsumer(), config);
    }

    @Provides
    public AdminPersonApiConsumer adminPersonApiConsumer(RestConfig config) {
        return prepare(new AdminPersonApiConsumer(), config);
    }

    @Provides
    public UserApiConsumer userApiConsumer(RestConfig config) {
        return prepare(new UserApiConsumer(), config);
    }

    @Provides
    public AdminUserApiConsumer adminUserApiConsumer(RestConfig config) {
        return prepare(new AdminUserApiConsumer(), config);
    }

}
