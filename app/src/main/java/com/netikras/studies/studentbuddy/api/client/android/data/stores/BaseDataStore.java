package com.netikras.studies.studentbuddy.api.client.android.data.stores;

import android.content.Context;

import com.netikras.studies.studentbuddy.api.client.android.service.ServiceRequest;

/**
 * Created by netikras on 17.10.31.
 */

public interface BaseDataStore<I, E> {

    void orderData(ServiceRequest request);

    void processOrders(Context context);

    default void refresh() {

    }


}
