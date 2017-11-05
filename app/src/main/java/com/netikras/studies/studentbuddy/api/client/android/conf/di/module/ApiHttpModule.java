package com.netikras.studies.studentbuddy.api.client.android.conf.di.module;

import com.netikras.studies.studentbuddy.api.client.android.conf.di.module.carrier.RestConfig;
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

import dagger.Module;
import dagger.Provides;

/**
 * Created by netikras on 17.11.2.
 */

@Module
public class ApiHttpModule {

    @Provides
    public RemoteEndpointServer server() {
//        RemoteEndpointServer server = RemoteEndpointServer.parse("http://192.168.1.6:8080/stubu");
        RemoteEndpointServer server = RemoteEndpointServer.parse("http://192.168.0.107:8080/stubu");
        return server;
    }

    @Provides
    public SessionContext context() {
        return new SessionContext();
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
    public RestConfig config(RestServiceProvider serviceProvider) {
        RestConfig config = new RestConfig();
        config.addListener("default", listener());
        config.setSessionContext(context());
//        config.setRestServiceProvider(serviceProvider);
        config.addServer("default", server());
        return config;
    }
}
