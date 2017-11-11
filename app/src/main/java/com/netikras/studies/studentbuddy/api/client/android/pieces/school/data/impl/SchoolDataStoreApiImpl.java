package com.netikras.studies.studentbuddy.api.client.android.pieces.school.data.impl;

import com.netikras.studies.studentbuddy.api.client.android.data.stores.ApiBasedDataStore;
import com.netikras.studies.studentbuddy.api.client.android.pieces.school.data.SchoolDataStore;
import com.netikras.studies.studentbuddy.api.client.android.service.ServiceRequest;
import com.netikras.studies.studentbuddy.core.data.api.dto.school.SchoolDto;

import java.util.Collection;

/**
 * Created by netikras on 17.11.11.
 */

public class SchoolDataStoreApiImpl extends ApiBasedDataStore<String, SchoolDto> implements SchoolDataStore {
    @Override
    public void getById(String id, ServiceRequest.Subscriber<SchoolDto>[] subscribers) {

    }

    @Override
    public void create(SchoolDto item, ServiceRequest.Subscriber<SchoolDto>[] subscribers) {

    }

    @Override
    public void update(SchoolDto item, ServiceRequest.Subscriber<SchoolDto>[] subscribers) {

    }

    @Override
    public void purge(String id, ServiceRequest.Subscriber<Boolean>[] subscribers) {

    }

    @Override
    public void delete(String id, ServiceRequest.Subscriber[] subscribers) {

    }

    @Override
    public void getAll(ServiceRequest.Subscriber<Collection<SchoolDto>>[] subscribers) {

    }
}
