package com.netikras.studies.studentbuddy.api.client.android.data.stores;

import android.content.Context;

import com.netikras.studies.studentbuddy.api.client.android.service.ApiService;
import com.netikras.studies.studentbuddy.api.client.android.service.ServiceRequest;

/**
 * Created by netikras on 17.10.31.
 */

public class ApiBasedDataStore<I, E> implements BaseDataStore<I, E> {

    @Override
    public void orderData(ServiceRequest request) {
        ApiService.enqueueRequest(request);
    }

    protected void orderData(ServiceRequest request, ServiceRequest.Subscriber... subscribers) {
        if (subscribers != null) {
            for (ServiceRequest.Subscriber subscriber : subscribers) {
                request.addSubscriber(subscriber);
            }
        }
        orderData(request);
    }

    @Override
    public void processOrders(Context context) {
        ApiService.processQueue(context);
    }

}
