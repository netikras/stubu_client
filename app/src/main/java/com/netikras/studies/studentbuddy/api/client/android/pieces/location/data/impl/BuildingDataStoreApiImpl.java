package com.netikras.studies.studentbuddy.api.client.android.pieces.location.data.impl;

import com.netikras.studies.studentbuddy.api.client.android.data.stores.ApiBasedDataStore;
import com.netikras.studies.studentbuddy.api.client.android.pieces.location.data.BuildingDataStore;
import com.netikras.studies.studentbuddy.api.client.android.service.ServiceRequest;
import com.netikras.studies.studentbuddy.api.client.android.service.ServiceRequest.Subscriber;
import com.netikras.studies.studentbuddy.api.location.generated.LocationApiConsumer;
import com.netikras.studies.studentbuddy.core.data.api.dto.location.BuildingDto;

import java.util.Collection;

import javax.inject.Inject;

/**
 * Created by netikras on 17.11.11.
 */

public class BuildingDataStoreApiImpl extends ApiBasedDataStore<String, BuildingDto> implements BuildingDataStore {

    @Inject
    LocationApiConsumer locationApiConsumer;

    @Inject
    public BuildingDataStoreApiImpl() {}

    @Override
    public void getById(String id, Subscriber<BuildingDto>... subscribers) {
        orderData(new ServiceRequest<BuildingDto>(){
            @Override
            public BuildingDto request() {
                return locationApiConsumer.retrieveBuildingDto(id);
            }
        }, subscribers);
    }

    @Override
    public void create(BuildingDto item, Subscriber<BuildingDto>... subscribers) {
        orderData(new ServiceRequest<BuildingDto>(){
            @Override
            public BuildingDto request() {
                return locationApiConsumer.createBuildingDto(item);
            }
        }, subscribers);
    }

    @Override
    public void update(BuildingDto item, Subscriber<BuildingDto>... subscribers) {
        orderData(new ServiceRequest<BuildingDto>(){
            @Override
            public BuildingDto request() {
                return locationApiConsumer.updateBuildingDto(item);
            }
        }, subscribers);
    }

    @Override
    public void purge(String id, Subscriber<Boolean>... subscribers) {
        orderData(new ServiceRequest<Boolean>(){
            @Override
            public Boolean request() {
                locationApiConsumer.purgeBuildingDto(id);
                return Boolean.TRUE;
            }
        }, subscribers);
    }

    @Override
    public void delete(String id, Subscriber... subscribers) {
        orderData(new ServiceRequest<Boolean>(){
            @Override
            public Boolean request() {
                locationApiConsumer.deleteBuildingDto(id);
                return Boolean.TRUE;
            }
        }, subscribers);
    }

    @Override
    public void getAll(Subscriber<Collection<BuildingDto>>... subscribers) {
        notifyNotImplemented(subscribers);
    }
}
