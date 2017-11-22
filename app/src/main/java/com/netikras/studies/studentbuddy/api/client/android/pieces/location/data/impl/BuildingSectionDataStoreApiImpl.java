package com.netikras.studies.studentbuddy.api.client.android.pieces.location.data.impl;

import com.netikras.studies.studentbuddy.api.client.android.data.stores.ApiBasedDataStore;
import com.netikras.studies.studentbuddy.api.client.android.pieces.location.data.BuildingSectionDataStore;
import com.netikras.studies.studentbuddy.api.client.android.service.ServiceRequest;
import com.netikras.studies.studentbuddy.api.client.android.service.ServiceRequest.Subscriber;
import com.netikras.studies.studentbuddy.api.location.generated.LocationApiConsumer;
import com.netikras.studies.studentbuddy.core.data.api.dto.location.BuildingSectionDto;

import java.util.Collection;

import javax.inject.Inject;

/**
 * Created by netikras on 17.11.11.
 */

public class BuildingSectionDataStoreApiImpl extends ApiBasedDataStore<String, BuildingSectionDto> implements BuildingSectionDataStore {

    @Inject
    LocationApiConsumer locationApiConsumer;

    @Inject
    public BuildingSectionDataStoreApiImpl() {}

    @Override
    public void getById(String id, Subscriber<BuildingSectionDto>... subscribers) {
        orderData(new ServiceRequest<BuildingSectionDto>(){
            @Override
            public BuildingSectionDto request() {
                return locationApiConsumer.retrieveBuildingSectionDto(id);
            }
        }, subscribers);
    }

    @Override
    public void create(BuildingSectionDto item, Subscriber<BuildingSectionDto>... subscribers) {
        orderData(new ServiceRequest<BuildingSectionDto>(){
            @Override
            public BuildingSectionDto request() {
                return locationApiConsumer.createBuildingSectionDto(item);
            }
        }, subscribers);
    }

    @Override
    public void update(BuildingSectionDto item, Subscriber<BuildingSectionDto>... subscribers) {
        orderData(new ServiceRequest<BuildingSectionDto>(){
            @Override
            public BuildingSectionDto request() {
                return locationApiConsumer.updateBuildingSectionDto(item);
            }
        }, subscribers);
    }

    @Override
    public void purge(String id, Subscriber<Boolean>... subscribers) {
        orderData(new ServiceRequest<Boolean>(){
            @Override
            public Boolean request() {
                locationApiConsumer.purgeBuildingSectionDto(id);
                return Boolean.TRUE;
            }
        }, subscribers);
    }

    @Override
    public void delete(String id, Subscriber... subscribers) {
        orderData(new ServiceRequest<Boolean>(){
            @Override
            public Boolean request() {
                locationApiConsumer.deleteBuildingSectionDto(id);
                return Boolean.TRUE;
            }
        }, subscribers);
    }

    @Override
    public void getAll(Subscriber<Collection<BuildingSectionDto>>... subscribers) {
        notifyNotImplemented(subscribers);
    }
}
