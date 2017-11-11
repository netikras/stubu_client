package com.netikras.studies.studentbuddy.api.client.android.pieces.location.data.impl;

import com.netikras.studies.studentbuddy.api.client.android.data.stores.ApiBasedDataStore;
import com.netikras.studies.studentbuddy.api.client.android.pieces.location.data.LayoutDataStore;
import com.netikras.studies.studentbuddy.api.client.android.service.ServiceRequest;
import com.netikras.studies.studentbuddy.core.data.api.dto.location.FloorLayoutDto;

import java.util.Collection;

/**
 * Created by netikras on 17.11.11.
 */

public class LayoutDataStoreApiImpl extends ApiBasedDataStore<String, FloorLayoutDto> implements LayoutDataStore {
    @Override
    public void getById(String id, ServiceRequest.Subscriber<FloorLayoutDto>[] subscribers) {

    }

    @Override
    public void create(FloorLayoutDto item, ServiceRequest.Subscriber<FloorLayoutDto>[] subscribers) {

    }

    @Override
    public void update(FloorLayoutDto item, ServiceRequest.Subscriber<FloorLayoutDto>[] subscribers) {

    }

    @Override
    public void purge(String id, ServiceRequest.Subscriber<Boolean>[] subscribers) {

    }

    @Override
    public void delete(String id, ServiceRequest.Subscriber[] subscribers) {

    }

    @Override
    public void getAll(ServiceRequest.Subscriber<Collection<FloorLayoutDto>>[] subscribers) {

    }
}
