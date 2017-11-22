package com.netikras.studies.studentbuddy.api.client.android.pieces.discipline.data.impl;

import com.netikras.studies.studentbuddy.api.client.android.data.stores.ApiBasedDataStore;
import com.netikras.studies.studentbuddy.api.client.android.pieces.discipline.data.DisciplineDataStore;
import com.netikras.studies.studentbuddy.api.client.android.service.ServiceRequest;
import com.netikras.studies.studentbuddy.api.client.android.service.ServiceRequest.Subscriber;
import com.netikras.studies.studentbuddy.api.location.generated.SchoolApiConsumer;
import com.netikras.studies.studentbuddy.core.data.api.dto.school.DisciplineDto;

import java.util.Collection;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by netikras on 17.11.9.
 */

public class DisciplineDataStoreApiImpl extends ApiBasedDataStore<String, DisciplineDto> implements DisciplineDataStore {

    @Inject
    SchoolApiConsumer schoolApiConsumer;

    @Inject
    public DisciplineDataStoreApiImpl() {}

    @Override
    public void getById(String id, Subscriber<DisciplineDto>... subscribers) {
        orderData(new ServiceRequest() {
            @Override
            public DisciplineDto request() {
                return schoolApiConsumer.retrieveDisciplineDto(id);
            }
        }, subscribers);
    }

    @Override
    public void create(DisciplineDto item, Subscriber<DisciplineDto>... subscribers) {
        orderData(new ServiceRequest() {
            @Override
            public DisciplineDto request() {
                return schoolApiConsumer.createDisciplineDto(item);
            }
        }, subscribers);
    }

    @Override
    public void update(DisciplineDto item, Subscriber<DisciplineDto>... subscribers) {
        orderData(new ServiceRequest() {
            @Override
            public DisciplineDto request() {
                return schoolApiConsumer.updateDisciplineDto(item);
            }
        }, subscribers);
    }

    @Override
    public void purge(String id, Subscriber<Boolean>... subscribers) {
        orderData(new ServiceRequest() {
            @Override
            public Boolean request() {
                schoolApiConsumer.purgeDisciplineDto(id);
                return Boolean.TRUE;
            }
        }, subscribers);
    }

    @Override
    public void delete(String id, Subscriber... subscribers) {
        orderData(new ServiceRequest() {
            @Override
            public Boolean request() {
                schoolApiConsumer.deleteDisciplineDto(id);
                return Boolean.TRUE;
            }
        }, subscribers);
    }

    public void getAllForSchool(String id, Subscriber<Collection<DisciplineDto>>... subscribers) {
        orderData(new ServiceRequest() {
            @Override
            public List<DisciplineDto> request() {
                return schoolApiConsumer.getDisciplineDtoAllBySchoolId(id);
            }
        }, subscribers);
    }



    @Override
    public void getAll(Subscriber<Collection<DisciplineDto>>... subscribers) {

    }




}
