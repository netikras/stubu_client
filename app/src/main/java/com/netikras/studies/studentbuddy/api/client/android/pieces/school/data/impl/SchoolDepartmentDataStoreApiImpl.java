package com.netikras.studies.studentbuddy.api.client.android.pieces.school.data.impl;

import com.netikras.studies.studentbuddy.api.client.android.data.stores.ApiBasedDataStore;
import com.netikras.studies.studentbuddy.api.client.android.pieces.school.data.SchoolDepartmentDataStore;
import com.netikras.studies.studentbuddy.api.client.android.service.ServiceRequest;
import com.netikras.studies.studentbuddy.api.client.android.service.ServiceRequest.Subscriber;
import com.netikras.studies.studentbuddy.api.location.generated.SchoolApiConsumer;
import com.netikras.studies.studentbuddy.core.data.api.dto.school.SchoolDepartmentDto;
import com.netikras.studies.studentbuddy.core.data.api.dto.school.SchoolDto;

import java.util.Collection;

import javax.inject.Inject;

/**
 * Created by netikras on 17.11.11.
 */

public class SchoolDepartmentDataStoreApiImpl extends ApiBasedDataStore<String, SchoolDepartmentDto> implements SchoolDepartmentDataStore {
    
    @Inject
    SchoolApiConsumer schoolApiConsumer;
    
    @Override
    public void getById(String id, Subscriber<SchoolDepartmentDto>... subscribers) {
        orderData(new ServiceRequest<SchoolDepartmentDto>() {
            @Override
            public SchoolDepartmentDto request() {
                return schoolApiConsumer.retrieveSchoolDepartmentDto(id);
            }
        }, subscribers);
    }

    @Override
    public void create(SchoolDepartmentDto item, Subscriber<SchoolDepartmentDto>... subscribers) {
        orderData(new ServiceRequest<SchoolDepartmentDto>() {
            @Override
            public SchoolDepartmentDto request() {
                return schoolApiConsumer.createSchoolDepartmentDto(item);
            }
        }, subscribers);
    }

    @Override
    public void update(SchoolDepartmentDto item, Subscriber<SchoolDepartmentDto>... subscribers) {
        orderData(new ServiceRequest<SchoolDepartmentDto>() {
            @Override
            public SchoolDepartmentDto request() {
                return schoolApiConsumer.updateSchoolDepartmentDto(item);
            }
        }, subscribers);
    }

    @Override
    public void purge(String id, Subscriber<Boolean>... subscribers) {
        orderData(new ServiceRequest<Boolean>() {
            @Override
            public Boolean request() {
                schoolApiConsumer.purgeSchoolDepartmentDto(id);
                return Boolean.TRUE;
            }
        }, subscribers);
    }

    @Override
    public void delete(String id, Subscriber<Boolean>... subscribers) {
        orderData(new ServiceRequest<Boolean>() {
            @Override
            public Boolean request() {
                schoolApiConsumer.deleteSchoolDepartmentDto(id);
                return Boolean.TRUE;
            }
        }, subscribers);
    }

    @Override
    @Deprecated
    public void getAll(Subscriber<Collection<SchoolDepartmentDto>>... subscribers) {
        notifyNotImplemented(subscribers);
    }
}
