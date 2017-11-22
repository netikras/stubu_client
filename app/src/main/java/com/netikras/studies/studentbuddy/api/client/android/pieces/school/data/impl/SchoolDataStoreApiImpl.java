package com.netikras.studies.studentbuddy.api.client.android.pieces.school.data.impl;

import com.netikras.studies.studentbuddy.api.client.android.data.stores.ApiBasedDataStore;
import com.netikras.studies.studentbuddy.api.client.android.pieces.school.data.SchoolDataStore;
import com.netikras.studies.studentbuddy.api.client.android.service.ServiceRequest;
import com.netikras.studies.studentbuddy.api.client.android.service.ServiceRequest.Subscriber;
import com.netikras.studies.studentbuddy.api.location.generated.SchoolApiConsumer;
import com.netikras.studies.studentbuddy.core.data.api.dto.school.SchoolDto;

import java.util.Collection;

import javax.inject.Inject;

/**
 * Created by netikras on 17.11.11.
 */

public class SchoolDataStoreApiImpl extends ApiBasedDataStore<String, SchoolDto> implements SchoolDataStore {
    
    @Inject
    SchoolApiConsumer schoolApiConsumer;

    @Inject
    public SchoolDataStoreApiImpl() {}
    
    @Override
    public void getById(String id, Subscriber<SchoolDto>... subscribers) {
        orderData(new ServiceRequest<SchoolDto>() {
            @Override
            public SchoolDto request() {
                return schoolApiConsumer.retrieveSchoolDto(id);
            }
        }, subscribers);
    }

    @Override
    public void create(SchoolDto item, Subscriber<SchoolDto>... subscribers) {
        orderData(new ServiceRequest<SchoolDto>() {
            @Override
            public SchoolDto request() {
                return schoolApiConsumer.createSchoolDto(item);
            }
        }, subscribers);
    }

    @Override
    public void update(SchoolDto item, Subscriber<SchoolDto>... subscribers) {
        orderData(new ServiceRequest<SchoolDto>() {
            @Override
            public SchoolDto request() {
                return schoolApiConsumer.updateSchoolDto(item);
            }
        }, subscribers);
    }

    @Override
    public void purge(String id, Subscriber<Boolean>... subscribers) {
        orderData(new ServiceRequest<Boolean>() {
            @Override
            public Boolean request() {
                schoolApiConsumer.purgeSchoolDto(id);
                return Boolean.TRUE;
            }
        }, subscribers);
    }

    @Override
    public void delete(String id, Subscriber<Boolean>... subscribers) {
        orderData(new ServiceRequest<Boolean>() {
            @Override
            public Boolean request() {
                schoolApiConsumer.retrieveSchoolDto(id);
                return Boolean.TRUE;
            }
        }, subscribers);
    }

    @Override
    public void getAll(Subscriber<Collection<SchoolDto>>... subscribers) {
        orderData(new ServiceRequest<Collection<SchoolDto>>() {
            @Override
            public Collection<SchoolDto> request() {
                return schoolApiConsumer.getSchoolDtoAll();
            }
        }, subscribers);
    }
}
