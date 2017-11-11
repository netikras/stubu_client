package com.netikras.studies.studentbuddy.api.client.android.pieces.sys.data.impl;

import com.netikras.studies.studentbuddy.api.client.android.data.stores.ApiBasedDataStore;
import com.netikras.studies.studentbuddy.api.client.android.pieces.sys.data.StatusDataStore;
import com.netikras.studies.studentbuddy.api.client.android.service.ServiceRequest;
import com.netikras.studies.studentbuddy.api.client.android.service.ServiceRequest.Subscriber;
import com.netikras.studies.studentbuddy.api.sys.generated.StatusApiConsumer;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by netikras on 17.11.11.
 */

public class StatusDataStoreApiImpl extends ApiBasedDataStore implements StatusDataStore {

    @Inject
    StatusApiConsumer statusApiConsumer;

    @Override
    public void testEcho(String text, Subscriber<String>... subscribers) {
        orderData(new ServiceRequest<String>() {
            @Override
            public String request() {
                return statusApiConsumer.getStringMessageEcho(text);
            }
        }, subscribers);
    }

    @Override
    public void getResources(Subscriber<List<String>>... subscribers) {
        orderData(new ServiceRequest<List<String>>() {
            @Override
            public List<String> request() {
                return statusApiConsumer.getListDtoResources();
            }
        }, subscribers);
    }

    @Override
    public void getResourceExample(String name, Subscriber<Object>... subscribers) {
        orderData(new ServiceRequest<Object>() {
            @Override
            public Object request() {
                return statusApiConsumer.getObjectDtoStructure(name);
            }
        }, subscribers);
    }


}
