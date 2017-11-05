package com.netikras.studies.studentbuddy.api.client.android.conf.di.module.carrier;

import com.netikras.tools.common.remote.RemoteEndpointServer;
import com.netikras.tools.common.remote.http.RequestListener;
import com.netikras.tools.common.remote.http.RestClient;
import com.netikras.tools.common.remote.http.RestServiceProvider;
import com.netikras.tools.common.remote.http.SessionContext;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by netikras on 17.11.3.
 */

public class RestConfig {

    private RestClient restClient;
    private RestServiceProvider restServiceProvider;
    private SessionContext sessionContext;
    private Map<String, RequestListener> listeners;
    private Map<String, RemoteEndpointServer> servers;

    public RestClient getRestClient() {
        return restClient;
    }

    public void setRestClient(RestClient restClient) {
        this.restClient = restClient;
    }

    public RestServiceProvider getRestServiceProvider() {
        return restServiceProvider;
    }

    public void setRestServiceProvider(RestServiceProvider restServiceProvider) {
        this.restServiceProvider = restServiceProvider;
    }

    public SessionContext getSessionContext() {
        return sessionContext;
    }

    public void setSessionContext(SessionContext sessionContext) {
        this.sessionContext = sessionContext;
    }

    public Map<String, RequestListener> getListeners() {
        return listeners;
    }

    public void setListeners(Map<String, RequestListener> listeners) {
        this.listeners = listeners;
    }

    public Map<String, RemoteEndpointServer> getServers() {
        return servers;
    }

    public void setServers(Map<String, RemoteEndpointServer> servers) {
        this.servers = servers;
    }

    public void addListener(String name, RequestListener listener) {
        if (listeners == null) {
            listeners = new HashMap<>();
        }
        listeners.put(name, listener);
    }

    public void addServer(String name, RemoteEndpointServer server) {
        if (servers == null) {
            servers = new HashMap<>();
        }
        servers.put(name, server);
    }
}
