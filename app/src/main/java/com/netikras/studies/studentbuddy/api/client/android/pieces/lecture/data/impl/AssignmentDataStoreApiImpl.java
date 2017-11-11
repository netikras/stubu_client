package com.netikras.studies.studentbuddy.api.client.android.pieces.lecture.data.impl;

import com.netikras.studies.studentbuddy.api.client.android.data.stores.ApiBasedDataStore;
import com.netikras.studies.studentbuddy.api.client.android.pieces.lecture.data.AssingmentDataStore;
import com.netikras.studies.studentbuddy.api.client.android.service.ServiceRequest;
import com.netikras.studies.studentbuddy.core.data.api.dto.school.AssignmentDto;

import java.util.Collection;

/**
 * Created by netikras on 17.11.11.
 */

public class AssignmentDataStoreApiImpl extends ApiBasedDataStore<String, AssignmentDto> implements AssingmentDataStore {
    @Override
    public void getById(String id, ServiceRequest.Subscriber<AssignmentDto>[] subscribers) {

    }

    @Override
    public void create(AssignmentDto item, ServiceRequest.Subscriber<AssignmentDto>[] subscribers) {

    }

    @Override
    public void update(AssignmentDto item, ServiceRequest.Subscriber<AssignmentDto>[] subscribers) {

    }

    @Override
    public void purge(String id, ServiceRequest.Subscriber<Boolean>[] subscribers) {

    }

    @Override
    public void delete(String id, ServiceRequest.Subscriber[] subscribers) {

    }

    @Override
    public void getAll(ServiceRequest.Subscriber<Collection<AssignmentDto>>[] subscribers) {

    }
}
