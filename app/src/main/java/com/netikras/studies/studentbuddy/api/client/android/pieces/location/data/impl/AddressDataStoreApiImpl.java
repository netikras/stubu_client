package com.netikras.studies.studentbuddy.api.client.android.pieces.location.data.impl;

import com.netikras.studies.studentbuddy.api.client.android.data.stores.ApiBasedDataStore;
import com.netikras.studies.studentbuddy.api.client.android.pieces.location.data.AddressDataStore;
import com.netikras.studies.studentbuddy.api.client.android.service.ServiceRequest;
import com.netikras.studies.studentbuddy.core.data.api.dto.location.AddressDto;

import java.util.Collection;

/**
 * Created by netikras on 17.11.11.
 */

public class AddressDataStoreApiImpl extends ApiBasedDataStore<String, AddressDto> implements AddressDataStore {
    @Override
    public void getById(String id, ServiceRequest.Subscriber<AddressDto>[] subscribers) {

    }

    @Override
    public void create(AddressDto item, ServiceRequest.Subscriber<AddressDto>[] subscribers) {

    }

    @Override
    public void update(AddressDto item, ServiceRequest.Subscriber<AddressDto>[] subscribers) {

    }

    @Override
    public void purge(String id, ServiceRequest.Subscriber<Boolean>[] subscribers) {

    }

    @Override
    public void delete(String id, ServiceRequest.Subscriber[] subscribers) {

    }

    @Override
    public void getAll(ServiceRequest.Subscriber<Collection<AddressDto>>[] subscribers) {

    }
}
