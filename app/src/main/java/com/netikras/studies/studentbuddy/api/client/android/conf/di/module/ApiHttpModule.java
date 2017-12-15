package com.netikras.studies.studentbuddy.api.client.android.conf.di.module;

import android.util.Log;

import com.netikras.studies.studentbuddy.api.client.android.conf.di.module.carrier.RestConfig;
import com.netikras.studies.studentbuddy.api.client.android.data.prefs.AppPreferencesHelper;
import com.netikras.studies.studentbuddy.api.client.android.data.prefs.PreferencesHelper;
import com.netikras.studies.studentbuddy.api.client.android.util.misc.HttpClientOkImpl;
import com.netikras.tools.common.exception.ErrorBody;
import com.netikras.tools.common.exception.ErrorsCollection;
import com.netikras.tools.common.exception.FriendlyException;
import com.netikras.tools.common.exception.FriendlyExceptionBase;
import com.netikras.tools.common.exception.FriendlyUncheckedException;
import com.netikras.tools.common.remote.RemoteEndpointServer;
import com.netikras.tools.common.remote.http.HttpClient;
import com.netikras.tools.common.remote.http.HttpRequest;
import com.netikras.tools.common.remote.http.HttpResponse;
import com.netikras.tools.common.remote.http.RequestListener;
import com.netikras.tools.common.remote.http.RestClient;
import com.netikras.tools.common.remote.http.RestServiceProvider;
import com.netikras.tools.common.remote.http.SessionContext;
import com.netikras.tools.common.remote.http.impl.json.HttpResponseJsonImpl;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

import static com.netikras.tools.common.security.IntegrityUtils.isNullOrEmpty;

/**
 * Created by netikras on 17.11.2.
 */

@Module
@Singleton
public class ApiHttpModule {

    @Provides
    public RemoteEndpointServer server() {
        RemoteEndpointServer server = RemoteEndpointServer.parse("http://192.168.1.6:8080/stubu");
//        RemoteEndpointServer server = RemoteEndpointServer.parse("http://192.168.0.107:8080/stubu");
        return server;
    }

    @Provides
    public SessionContext context(PreferencesHelper preferencesHelper) {
        return new SessionContext() {
            @Override
            public void updateContext(HttpResponse response) {
                long cookiesHashBeforeUpdate = hashCookies();
                super.updateContext(response);
                long cookiesHashAfterUpdate = hashCookies();

                if (cookiesHashBeforeUpdate != cookiesHashAfterUpdate) {
                    preferencesHelper.setCookies(cookies);
                }
            }

            @Override
            public synchronized void applyContext(HttpRequest request) {
                if (hashCookies() == 0) {
                    cookies = preferencesHelper.getCookies();
//                    Log.d("COOKIES:", "" + cookies);
                }
                super.applyContext(request);
            }

            private long hashCookies() {
                return isNullOrEmpty(cookies) ? 0 : cookies.hashCode();
            }
        };
    }

    @Provides
    public RequestListener listener() {

        return new RequestListener(){

            private void onError(Object response) {
                ErrorsCollection errors = new ErrorsCollection();
                if (response == null) {
                    errors.add(new ErrorBody().setMessage1("Error"));
                } else if (ErrorsCollection.class.isAssignableFrom(response.getClass())) {
                    errors = (ErrorsCollection) response;
                } else if (ErrorBody.class.isAssignableFrom(response.getClass())) {
                    errors.add((ErrorBody) response);
                }

                throw new FriendlyUncheckedException().setErrors(errors);
            }

            @Override
            public Object onServerErrorResponse(HttpRequest request, HttpResponse response) {
                if (HttpResponseJsonImpl.class.isAssignableFrom(response.getClass())) {
                    Log.w("RequestListener", "Error status code: " + response.getStatus());
                    Log.w("RequestListener", "Error message: " + ((HttpResponseJsonImpl)response).getErrorMessage());
                    Log.w("RequestListener", "Error body: " + ((HttpResponseJsonImpl)response).getErrorBody());
                }
                onError(response.getObject());
                return super.onServerErrorResponse(request, response);
            }

            @Override
            public Object onClientErrorResponse(HttpRequest request, HttpResponse response) {
                onError(((HttpResponseJsonImpl)response).getErrorBody());
                return super.onClientErrorResponse(request, response);
            }
        };
    }


    @Provides
    HttpClient client() {
        return new HttpClientOkImpl();
    }

    @Provides
    RestClient restClient(HttpClient httpClient) {
        return new RestClient(httpClient);
    }


    @Provides
    public RestServiceProvider restServiceProvider(RestClient restClient) {
        return new RestServiceProvider(){
            @Override
            protected RestClient getRestClient() {
                return restClient;
            }
        };
    }


    @Provides
    @Singleton
    public RestConfig config(SessionContext context, RequestListener listener, RestServiceProvider serviceProvider, AppPreferencesHelper preferencesHelper) {
        RestConfig config = new RestConfig();
        config.addListener("default", listener);
        config.setSessionContext(context);
//        config.setRestServiceProvider(serviceProvider);

        RemoteEndpointServer server = RemoteEndpointServer.parse(preferencesHelper.getApiServerUrl());
        config.addServer("default", server);
        return config;
    }
}
