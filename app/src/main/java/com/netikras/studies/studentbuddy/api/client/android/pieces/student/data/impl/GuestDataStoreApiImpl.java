package com.netikras.studies.studentbuddy.api.client.android.pieces.student.data.impl;

import com.netikras.studies.studentbuddy.api.client.android.data.stores.ApiBasedDataStore;
import com.netikras.studies.studentbuddy.api.client.android.pieces.student.data.GuestDataStore;
import com.netikras.studies.studentbuddy.api.client.android.service.ServiceRequest;
import com.netikras.studies.studentbuddy.api.client.android.service.ServiceRequest.Subscriber;
import com.netikras.studies.studentbuddy.api.user.generated.StudentApiConsumer;
import com.netikras.studies.studentbuddy.api.user.mgmt.generated.AdminStudentApiConsumer;
import com.netikras.studies.studentbuddy.core.data.api.dto.school.LectureGuestDto;

import java.util.Collection;

import javax.inject.Inject;

/**
 * Created by netikras on 17.11.11.
 */

public class GuestDataStoreApiImpl extends ApiBasedDataStore<String, LectureGuestDto> implements GuestDataStore {

    @Inject
    StudentApiConsumer studentApiConsumer;
    @Inject
    AdminStudentApiConsumer adminStudentApiConsumer;

    @Inject
    public GuestDataStoreApiImpl() {}

    @Override
    public void getById(String id, Subscriber<LectureGuestDto>... subscribers) {
        orderData(new ServiceRequest<LectureGuestDto>() {
            @Override
            public LectureGuestDto request() {
                return studentApiConsumer.retrieveLectureGuestDto(id);
            }
        }, subscribers);
    }

    @Override
    public void create(LectureGuestDto item, Subscriber<LectureGuestDto>... subscribers) {
        orderData(new ServiceRequest<LectureGuestDto>() {
            @Override
            public LectureGuestDto request() {
                return adminStudentApiConsumer.createLectureGuestDto(item);
            }
        }, subscribers);
    }

    @Override
    public void update(LectureGuestDto item, Subscriber<LectureGuestDto>... subscribers) {
        orderData(new ServiceRequest<LectureGuestDto>() {
            @Override
            public LectureGuestDto request() {
                return studentApiConsumer.updateLectureGuestDto(item);
            }
        }, subscribers);
    }

    @Override
    public void purge(String id, Subscriber<Boolean>... subscribers) {
        orderData(new ServiceRequest<Boolean>() {
            @Override
            public Boolean request() {
                adminStudentApiConsumer.purgeLectureGuestDto(id);
                return Boolean.TRUE;
            }
        }, subscribers);
    }

    @Override
    public void delete(String id, Subscriber<Boolean>... subscribers) {
        orderData(new ServiceRequest<Boolean>() {
            @Override
            public Boolean request() {
                adminStudentApiConsumer.deleteLectureGuestDto(id);
                return Boolean.TRUE;
            }
        }, subscribers);
    }

    @Override
    public void getAll(Subscriber<Collection<LectureGuestDto>>... subscribers) {
        notifyNotImplemented(subscribers);
    }

    @Override
    public void getAllByPerson(String id, Subscriber<Collection<LectureGuestDto>>... subscribers) {
        orderData(new ServiceRequest<Collection<LectureGuestDto>>() {
            @Override
            public Collection<LectureGuestDto> request() {
                return studentApiConsumer.getLectureGuestDtoAllGuestsByPerson(id);
            }
        }, subscribers);
    }
}
