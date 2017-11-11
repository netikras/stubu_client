package com.netikras.studies.studentbuddy.api.client.android.pieces.student.data.impl;

import com.netikras.studies.studentbuddy.api.client.android.data.stores.ApiBasedDataStore;
import com.netikras.studies.studentbuddy.api.client.android.pieces.student.data.StudentsGroupDataStore;
import com.netikras.studies.studentbuddy.api.client.android.service.ServiceRequest;
import com.netikras.studies.studentbuddy.core.data.api.dto.school.StudentsGroupDto;

import java.util.Collection;

/**
 * Created by netikras on 17.11.11.
 */

public class StudentsGroupDataStoreApiImpl extends ApiBasedDataStore<String, StudentsGroupDto> implements StudentsGroupDataStore {
    @Override
    public void getById(String id, ServiceRequest.Subscriber<StudentsGroupDto>[] subscribers) {

    }

    @Override
    public void create(StudentsGroupDto item, ServiceRequest.Subscriber<StudentsGroupDto>[] subscribers) {

    }

    @Override
    public void update(StudentsGroupDto item, ServiceRequest.Subscriber<StudentsGroupDto>[] subscribers) {

    }

    @Override
    public void purge(String id, ServiceRequest.Subscriber<Boolean>[] subscribers) {

    }

    @Override
    public void delete(String id, ServiceRequest.Subscriber[] subscribers) {

    }

    @Override
    public void getAll(ServiceRequest.Subscriber<Collection<StudentsGroupDto>>[] subscribers) {

    }
}
