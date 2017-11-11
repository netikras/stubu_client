package com.netikras.studies.studentbuddy.api.client.android.pieces.location.data.impl;

import com.netikras.studies.studentbuddy.api.client.android.data.stores.ApiBasedDataStore;
import com.netikras.studies.studentbuddy.api.client.android.pieces.location.data.BuildingSectionDataStore;
import com.netikras.studies.studentbuddy.api.client.android.service.ServiceRequest;
import com.netikras.studies.studentbuddy.core.data.api.dto.location.BuildingSectionDto;

import java.util.Collection;

/**
 * Created by netikras on 17.11.11.
 */

public class BuildingSectionDataStoreApiImpl extends ApiBasedDataStore<String, BuildingSectionDto> implements BuildingSectionDataStore {
    @Override
    public void getById(String id, ServiceRequest.Subscriber<BuildingSectionDto>[] subscribers) {

    }

    @Override
    public void create(BuildingSectionDto item, ServiceRequest.Subscriber<BuildingSectionDto>[] subscribers) {

    }

    @Override
    public void update(BuildingSectionDto item, ServiceRequest.Subscriber<BuildingSectionDto>[] subscribers) {

    }

    @Override
    public void purge(String id, ServiceRequest.Subscriber<Boolean>[] subscribers) {

    }

    @Override
    public void delete(String id, ServiceRequest.Subscriber[] subscribers) {

    }

    @Override
    public void getAll(ServiceRequest.Subscriber<Collection<BuildingSectionDto>>[] subscribers) {

    }
}
