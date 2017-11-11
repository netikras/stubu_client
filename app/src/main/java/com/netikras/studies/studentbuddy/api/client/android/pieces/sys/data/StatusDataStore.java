package com.netikras.studies.studentbuddy.api.client.android.pieces.sys.data;

import com.netikras.studies.studentbuddy.api.client.android.data.stores.BaseDataStore;
import com.netikras.studies.studentbuddy.api.client.android.service.ServiceRequest;

import java.util.List;

/**
 * Created by netikras on 17.11.11.
 */

public interface StatusDataStore extends BaseDataStore {


    void testEcho(String text, ServiceRequest.Subscriber<String>... subscribers);

    void getResources(ServiceRequest.Subscriber<List<String>>... subscribers);

    void getResourceExample(String name, ServiceRequest.Subscriber<Object>... subscribers);
}
