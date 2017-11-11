package com.netikras.studies.studentbuddy.api.client.android.pieces.location.data.impl;

import com.netikras.studies.studentbuddy.api.client.android.data.stores.ApiBasedDataStore;
import com.netikras.studies.studentbuddy.api.client.android.pieces.location.data.BuildingDataStore;
import com.netikras.studies.studentbuddy.api.client.android.service.ServiceRequest;
import com.netikras.studies.studentbuddy.core.data.api.dto.location.BuildingDto;

import java.util.Collection;

/**
 * Created by netikras on 17.11.11.
 */

public class BuildingDataStoreApiImpl extends ApiBasedDataStore<String, BuildingDto> implements BuildingDataStore {
    @Override
    public void getById(String id, ServiceRequest.Subscriber<BuildingDto>[] subscribers) {

    }

    @Override
    public void create(BuildingDto item, ServiceRequest.Subscriber<BuildingDto>[] subscribers) {

    }

    @Override
    public void update(BuildingDto item, ServiceRequest.Subscriber<BuildingDto>[] subscribers) {

    }

    @Override
    public void purge(String id, ServiceRequest.Subscriber<Boolean>[] subscribers) {

    }

    @Override
    public void delete(String id, ServiceRequest.Subscriber[] subscribers) {

    }

    @Override
    public void getAll(ServiceRequest.Subscriber<Collection<BuildingDto>>[] subscribers) {

    }
}
