package com.netikras.studies.studentbuddy.api.client.android.pieces.lecture.data.impl;

import com.netikras.studies.studentbuddy.api.client.android.data.stores.ApiBasedDataStore;
import com.netikras.studies.studentbuddy.api.client.android.pieces.lecture.data.LectureDataStore;
import com.netikras.studies.studentbuddy.api.client.android.service.ServiceRequest;
import com.netikras.studies.studentbuddy.api.client.android.service.ServiceRequest.Subscriber;
import com.netikras.studies.studentbuddy.api.misc.TimeUnit;
import com.netikras.studies.studentbuddy.api.timetable.controller.generated.LecturesApiConsumer;
import com.netikras.studies.studentbuddy.core.data.api.dto.school.LectureDto;

import java.util.Collection;

import javax.inject.Inject;

/**
 * Created by netikras on 17.11.11.
 */

public class LectureDataStoreApiImpl extends ApiBasedDataStore<String, LectureDto> implements LectureDataStore {


    @Inject
    LecturesApiConsumer lecturesApiConsumer;

    @Inject
    public LectureDataStoreApiImpl() {}

    @Override
    public void getById(String id, Subscriber<LectureDto>... subscribers) {
        orderData(new ServiceRequest<LectureDto>() {
            @Override
            public LectureDto request() {
                return lecturesApiConsumer.retrieveLectureDto(id);
            }
        }, subscribers);
    }

    @Override
    public void create(LectureDto item, Subscriber<LectureDto>... subscribers) {
        orderData(new ServiceRequest<LectureDto>() {
            @Override
            public LectureDto request() {
                return lecturesApiConsumer.createLectureDto(item);
            }
        }, subscribers);
    }

    @Override
    public void update(LectureDto item, Subscriber<LectureDto>... subscribers) {
        orderData(new ServiceRequest<LectureDto>() {
            @Override
            public LectureDto request() {
                return lecturesApiConsumer.updateLectureDto(item);
            }
        }, subscribers);
    }

    @Override
    public void purge(String id, Subscriber<Boolean>... subscribers) {
        orderData(new ServiceRequest<Boolean>() {
            @Override
            public Boolean request() {
                lecturesApiConsumer.purgeLectureDto(id);
                return Boolean.TRUE;
            }
        }, subscribers);
    }

    @Override
    public void delete(String id, Subscriber... subscribers) {
        orderData(new ServiceRequest<Boolean>() {
            @Override
            public Boolean request() {
                lecturesApiConsumer.deleteLectureDto(id);
                return Boolean.TRUE;
            }
        }, subscribers);
    }

    @Override
    @Deprecated
    public void getAll(Subscriber<Collection<LectureDto>>[] subscribers) {

    }

    @Override
    public void getAllByGroup(String id, Long after, Long before, Subscriber<Collection<LectureDto>>... subscribers) {
        orderData(new ServiceRequest<Collection<LectureDto>>() {
            @Override
            public Collection<LectureDto> request() {
                return lecturesApiConsumer.getLectureDtoAllByGroupIdStartingBetween(id, after, before);
            }
        }, subscribers);
    }

    @Override
    public void getAllByLecturer(String id, Long after, Long before, Subscriber<Collection<LectureDto>>... subscribers) {
        orderData(new ServiceRequest<Collection<LectureDto>>() {
            @Override
            public Collection<LectureDto> request() {
                return lecturesApiConsumer.getLectureDtoAllByLecturerIdStartingBetween(id, after, before);
            }
        }, subscribers);
    }

    @Override
    public void getAllByRoom(String id, Long after, Long before, Subscriber<Collection<LectureDto>>... subscribers) {
        orderData(new ServiceRequest<Collection<LectureDto>>() {
            @Override
            public Collection<LectureDto> request() {
                return lecturesApiConsumer.getLectureDtoAllByRoomIdStartingBetween(id, after, before);
            }
        }, subscribers);
    }

    @Override
    public void getAllByStudent(String id, Long after, Long before, Subscriber<Collection<LectureDto>>... subscribers) {
        orderData(new ServiceRequest<Collection<LectureDto>>() {
            @Override
            public Collection<LectureDto> request() {
                return lecturesApiConsumer.getLectureDtoAllByStudentIdStartingBetween(id, after, before);
            }
        }, subscribers);
    }



    @Override
    public void getAllByGroup(String id, TimeUnit unit, Long amount, Subscriber<Collection<LectureDto>>... subscribers) {
        orderData(new ServiceRequest<Collection<LectureDto>>() {
            @Override
            public Collection<LectureDto> request() {
                return lecturesApiConsumer.getLectureDtoAllByGroupIdStartingIn(id, unit.getText(), amount);
            }
        }, subscribers);
    }

    @Override
    public void getAllByLecturer(String id, TimeUnit unit, Long amount, Subscriber<Collection<LectureDto>>... subscribers) {
        orderData(new ServiceRequest<Collection<LectureDto>>() {
            @Override
            public Collection<LectureDto> request() {
                return lecturesApiConsumer.getLectureDtoAllByLecturerIdStartingIn(id, unit.getText(), amount);
            }
        }, subscribers);
    }

    @Override
    public void getAllByRoom(String id, TimeUnit unit, Long amount, Subscriber<Collection<LectureDto>>... subscribers) {
        orderData(new ServiceRequest<Collection<LectureDto>>() {
            @Override
            public Collection<LectureDto> request() {
                return lecturesApiConsumer.getLectureDtoAllByRoomIdStartingIn(id, unit.getText(), amount);
            }
        }, subscribers);
    }

    @Override
    public void getAllByStudent(String id, TimeUnit unit, Long amount, Subscriber<Collection<LectureDto>>... subscribers) {
        orderData(new ServiceRequest<Collection<LectureDto>>() {
            @Override
            public Collection<LectureDto> request() {
                return lecturesApiConsumer.getLectureDtoAllByStudentIdStartingIn(id, unit.getText(), amount);
            }
        }, subscribers);
    }
}
