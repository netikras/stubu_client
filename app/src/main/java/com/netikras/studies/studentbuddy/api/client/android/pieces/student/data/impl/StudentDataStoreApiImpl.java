package com.netikras.studies.studentbuddy.api.client.android.pieces.student.data.impl;

import com.netikras.studies.studentbuddy.api.client.android.data.stores.ApiBasedDataStore;
import com.netikras.studies.studentbuddy.api.client.android.pieces.student.data.StudentDataStore;
import com.netikras.studies.studentbuddy.api.client.android.service.ServiceRequest;
import com.netikras.studies.studentbuddy.core.data.api.dto.school.StudentDto;

import java.util.Collection;

/**
 * Created by netikras on 17.11.11.
 */

public class StudentDataStoreApiImpl extends ApiBasedDataStore<String, StudentDto> implements StudentDataStore {
    @Override
    public void getById(String id, ServiceRequest.Subscriber<StudentDto>[] subscribers) {

    }

    @Override
    public void create(StudentDto item, ServiceRequest.Subscriber<StudentDto>[] subscribers) {

    }

    @Override
    public void update(StudentDto item, ServiceRequest.Subscriber<StudentDto>[] subscribers) {

    }

    @Override
    public void purge(String id, ServiceRequest.Subscriber<Boolean>[] subscribers) {

    }

    @Override
    public void delete(String id, ServiceRequest.Subscriber[] subscribers) {

    }

    @Override
    public void getAll(ServiceRequest.Subscriber<Collection<StudentDto>>[] subscribers) {

    }
}
