package com.netikras.studies.studentbuddy.api.client.android.data.stores.discipline;

import com.netikras.studies.studentbuddy.api.client.android.data.stores.ApiBasedDataStore;
import com.netikras.studies.studentbuddy.api.client.android.service.ServiceRequest;
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

    @Override
    public void getById(String id, ServiceRequest.Subscriber<DisciplineDto>... subscribers) {
        orderData(new ServiceRequest() {
            @Override
            public DisciplineDto request() {
                return schoolApiConsumer.retrieveDisciplineDto(id);
            }
        }, subscribers);
    }

    @Override
    public void create(DisciplineDto item, ServiceRequest.Subscriber<DisciplineDto>... subscribers) {
        orderData(new ServiceRequest() {
            @Override
            public DisciplineDto request() {
                return schoolApiConsumer.createDisciplineDto(item);
            }
        }, subscribers);
    }

    @Override
    public void update(DisciplineDto item, ServiceRequest.Subscriber<DisciplineDto>... subscribers) {
        orderData(new ServiceRequest() {
            @Override
            public DisciplineDto request() {
                return schoolApiConsumer.updateDisciplineDto(item);
            }
        }, subscribers);
    }

    @Override
    public void purge(String id, ServiceRequest.Subscriber<Boolean>... subscribers) {
        orderData(new ServiceRequest() {
            @Override
            public Boolean request() {
                schoolApiConsumer.purgeDisciplineDto(id);
                return Boolean.TRUE;
            }
        }, subscribers);
    }

    @Override
    public void delete(String id, ServiceRequest.Subscriber... subscribers) {
        orderData(new ServiceRequest() {
            @Override
            public Boolean request() {
                schoolApiConsumer.deleteDisciplineDto(id);
                return Boolean.TRUE;
            }
        }, subscribers);
    }

    public void getAllForSchool(String id, ServiceRequest.Subscriber<Collection<DisciplineDto>>... subscribers) {
        orderData(new ServiceRequest() {
            @Override
            public List<DisciplineDto> request() {
                return schoolApiConsumer.getDisciplineDtoAllBySchoolId(id);
            }
        }, subscribers);
    }



    @Override
    public void getAll(ServiceRequest.Subscriber<Collection<DisciplineDto>>... subscribers) {

    }




}
