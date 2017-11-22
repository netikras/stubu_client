package com.netikras.studies.studentbuddy.api.client.android.pieces.lecturer.data.impl;

import com.netikras.studies.studentbuddy.api.client.android.data.stores.ApiBasedDataStore;
import com.netikras.studies.studentbuddy.api.client.android.pieces.lecturer.data.LecturerDataStore;
import com.netikras.studies.studentbuddy.api.client.android.service.ServiceRequest;
import com.netikras.studies.studentbuddy.api.client.android.service.ServiceRequest.Subscriber;
import com.netikras.studies.studentbuddy.api.user.generated.LecturerApiConsumer;
import com.netikras.studies.studentbuddy.api.user.mgmt.generated.AdminLecturerApiConsumer;
import com.netikras.studies.studentbuddy.core.data.api.dto.school.LecturerDto;

import java.util.Collection;

import javax.inject.Inject;

/**
 * Created by netikras on 17.11.11.
 */

public class LecturerDataStoreApiImpl extends ApiBasedDataStore<String, LecturerDto> implements LecturerDataStore {
    
    @Inject
    LecturerApiConsumer lecturerApiConsumer;

    @Inject
    AdminLecturerApiConsumer adminLecturerApiConsumer;

    @Inject
    public LecturerDataStoreApiImpl() {}
    
    @Override
    public void getById(String id, Subscriber<LecturerDto>... subscribers) {
        orderData(new ServiceRequest<LecturerDto>() {
            @Override
            public LecturerDto request() {
                return lecturerApiConsumer.retrieveLecturerDto(id);
            }
        }, subscribers);
    }

    @Override
    public void create(LecturerDto item, Subscriber<LecturerDto>... subscribers) {
        orderData(new ServiceRequest<LecturerDto>() {
            @Override
            public LecturerDto request() {
                return adminLecturerApiConsumer.createLecturerDto(item);
            }
        }, subscribers);
    }

    @Override
    public void update(LecturerDto item, Subscriber<LecturerDto>... subscribers) {
        orderData(new ServiceRequest<LecturerDto>() {
            @Override
            public LecturerDto request() {
                return lecturerApiConsumer.updateLecturerDto(item);
            }
        }, subscribers);
    }

    @Override
    public void purge(String id, Subscriber<Boolean>... subscribers) {
        orderData(new ServiceRequest<Boolean>() {
            @Override
            public Boolean request() {
                adminLecturerApiConsumer.purgeLecturerDto(id);
                return Boolean.TRUE;
            }
        }, subscribers);
    }

    @Override
    public void delete(String id, Subscriber... subscribers) {
        orderData(new ServiceRequest<Boolean>() {
            @Override
            public Boolean request() {
                adminLecturerApiConsumer.deleteLecturerDto(id);
                return Boolean.TRUE;
            }
        }, subscribers);
    }

    @Override
    @Deprecated
    public void getAll(Subscriber<Collection<LecturerDto>>[] subscribers) {

    }

    @Override
    public void assignToDiscipline(String lecturerId, String disciplineId, Subscriber... subscribers) {
        orderData(new ServiceRequest<Boolean>() {
            @Override
            public Boolean request() {
                adminLecturerApiConsumer.assignLecturerDtoToDiscipline(lecturerId, disciplineId);
                return Boolean.TRUE;
            }
        }, subscribers);
    }

    @Override
    public void unassignFromDiscipline(String lecturerId, String disciplineId, Subscriber... subscribers) {
        orderData(new ServiceRequest<Boolean>() {
            @Override
            public Boolean request() {
                adminLecturerApiConsumer.unassignLecturerDtoFromDiscipline(lecturerId, disciplineId);
                return Boolean.TRUE;
            }
        }, subscribers);
    }

    @Override
    public void getAllByPerson(String id, Subscriber<Collection<LecturerDto>>... subscribers) {
        orderData(new ServiceRequest<Collection<LecturerDto>>() {
            @Override
            public Collection<LecturerDto> request() {
                return lecturerApiConsumer.getLecturerDtoAllByPersonId(id);
            }
        }, subscribers);
    }

    @Override
    public void getAllByDiscipline(String id, Subscriber<Collection<LecturerDto>>... subscribers) {
        orderData(new ServiceRequest<Collection<LecturerDto>>() {
            @Override
            public Collection<LecturerDto> request() {
                return lecturerApiConsumer.getLecturerDtoByDiscipline(id);
            }
        }, subscribers);
    }


}
