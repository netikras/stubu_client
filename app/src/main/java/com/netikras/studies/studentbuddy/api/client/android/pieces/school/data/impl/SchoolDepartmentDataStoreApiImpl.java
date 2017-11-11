package com.netikras.studies.studentbuddy.api.client.android.pieces.school.data.impl;

import com.netikras.studies.studentbuddy.api.client.android.data.stores.ApiBasedDataStore;
import com.netikras.studies.studentbuddy.api.client.android.pieces.school.data.SchoolDepartmentDataStore;
import com.netikras.studies.studentbuddy.api.client.android.service.ServiceRequest;
import com.netikras.studies.studentbuddy.core.data.api.dto.school.SchoolDepartmentDto;

import java.util.Collection;

/**
 * Created by netikras on 17.11.11.
 */

public class SchoolDepartmentDataStoreApiImpl extends ApiBasedDataStore<String, SchoolDepartmentDto> implements SchoolDepartmentDataStore {
    @Override
    public void getById(String id, ServiceRequest.Subscriber<SchoolDepartmentDto>[] subscribers) {

    }

    @Override
    public void create(SchoolDepartmentDto item, ServiceRequest.Subscriber<SchoolDepartmentDto>[] subscribers) {

    }

    @Override
    public void update(SchoolDepartmentDto item, ServiceRequest.Subscriber<SchoolDepartmentDto>[] subscribers) {

    }

    @Override
    public void purge(String id, ServiceRequest.Subscriber<Boolean>[] subscribers) {

    }

    @Override
    public void delete(String id, ServiceRequest.Subscriber[] subscribers) {

    }

    @Override
    public void getAll(ServiceRequest.Subscriber<Collection<SchoolDepartmentDto>>[] subscribers) {

    }
}
