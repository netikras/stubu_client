package com.netikras.studies.studentbuddy.api.client.android.pieces.location.data.impl;

import com.netikras.studies.studentbuddy.api.client.android.data.stores.ApiBasedDataStore;
import com.netikras.studies.studentbuddy.api.client.android.pieces.location.data.RoomDataStore;
import com.netikras.studies.studentbuddy.api.client.android.service.ServiceRequest;
import com.netikras.studies.studentbuddy.api.client.android.service.ServiceRequest.Subscriber;
import com.netikras.studies.studentbuddy.api.location.generated.FloorApiConsumer;
import com.netikras.studies.studentbuddy.core.data.api.dto.location.LectureRoomDto;

import java.util.Collection;

import javax.inject.Inject;

/**
 * Created by netikras on 17.11.11.
 */

public class RoomDataStoreApiImpl extends ApiBasedDataStore<String, LectureRoomDto> implements RoomDataStore {

    @Inject
    FloorApiConsumer floorApiConsumer;

    @Inject
    public RoomDataStoreApiImpl() {}

    @Override
    public void getById(String id, Subscriber<LectureRoomDto>... subscribers) {
        orderData(new ServiceRequest<LectureRoomDto>() {
            @Override
            public LectureRoomDto request() {
                return floorApiConsumer.retrieveLectureRoomDto(id);
            }
        }, subscribers);
    }

    @Override
    public void create(LectureRoomDto item, Subscriber<LectureRoomDto>... subscribers) {
        orderData(new ServiceRequest<LectureRoomDto>() {
            @Override
            public LectureRoomDto request() {
                return floorApiConsumer.createLectureRoomDto(item);
            }
        }, subscribers);
    }

    @Override
    public void update(LectureRoomDto item, Subscriber<LectureRoomDto>... subscribers) {
        orderData(new ServiceRequest<LectureRoomDto>() {
            @Override
            public LectureRoomDto request() {
                return floorApiConsumer.updateLectureRoomDto(item);
            }
        }, subscribers);
    }

    @Override
    public void purge(String id, Subscriber<Boolean>... subscribers) {
        orderData(new ServiceRequest<Boolean>() {
            @Override
            public Boolean request() {
                floorApiConsumer.purgeLectureRoomDto(id);
                return Boolean.TRUE;
            }
        }, subscribers);
    }

    @Override
    public void delete(String id, Subscriber... subscribers) {
        orderData(new ServiceRequest<Boolean>() {
            @Override
            public Boolean request() {
                floorApiConsumer.deleteLectureRoomDto(id);
                return Boolean.TRUE;
            }
        }, subscribers);
    }

    @Override
    public void getAll(Subscriber<Collection<LectureRoomDto>>... subscribers) {
        notifyNotImplemented(subscribers);
    }
}
