package com.netikras.studies.studentbuddy.api.client.android.pieces.location.data.impl;

import com.netikras.studies.studentbuddy.api.client.android.data.stores.ApiBasedDataStore;
import com.netikras.studies.studentbuddy.api.client.android.pieces.location.data.FloorDataStore;
import com.netikras.studies.studentbuddy.api.client.android.service.ServiceRequest;
import com.netikras.studies.studentbuddy.core.data.api.dto.location.BuildingFloorDto;

import java.util.Collection;

/**
 * Created by netikras on 17.11.11.
 */

public class FloorDataStoreApiImpl extends ApiBasedDataStore<String, BuildingFloorDto> implements FloorDataStore {
    @Override
    public void getById(String id, ServiceRequest.Subscriber<BuildingFloorDto>[] subscribers) {

    }

    @Override
    public void create(BuildingFloorDto item, ServiceRequest.Subscriber<BuildingFloorDto>[] subscribers) {

    }

    @Override
    public void update(BuildingFloorDto item, ServiceRequest.Subscriber<BuildingFloorDto>[] subscribers) {

    }

    @Override
    public void purge(String id, ServiceRequest.Subscriber<Boolean>[] subscribers) {

    }

    @Override
    public void delete(String id, ServiceRequest.Subscriber[] subscribers) {

    }

    @Override
    public void getAll(ServiceRequest.Subscriber<Collection<BuildingFloorDto>>[] subscribers) {

    }
}
