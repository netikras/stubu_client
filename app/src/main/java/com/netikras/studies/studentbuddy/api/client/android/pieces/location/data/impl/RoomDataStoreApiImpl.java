package com.netikras.studies.studentbuddy.api.client.android.pieces.location.data.impl;

import com.netikras.studies.studentbuddy.api.client.android.data.stores.ApiBasedDataStore;
import com.netikras.studies.studentbuddy.api.client.android.pieces.location.data.RoomDataStore;
import com.netikras.studies.studentbuddy.api.client.android.service.ServiceRequest;
import com.netikras.studies.studentbuddy.core.data.api.dto.location.LectureRoomDto;

import java.util.Collection;

/**
 * Created by netikras on 17.11.11.
 */

public class RoomDataStoreApiImpl extends ApiBasedDataStore<String, LectureRoomDto> implements RoomDataStore {
    @Override
    public void getById(String id, ServiceRequest.Subscriber<LectureRoomDto>[] subscribers) {

    }

    @Override
    public void create(LectureRoomDto item, ServiceRequest.Subscriber<LectureRoomDto>[] subscribers) {

    }

    @Override
    public void update(LectureRoomDto item, ServiceRequest.Subscriber<LectureRoomDto>[] subscribers) {

    }

    @Override
    public void purge(String id, ServiceRequest.Subscriber<Boolean>[] subscribers) {

    }

    @Override
    public void delete(String id, ServiceRequest.Subscriber[] subscribers) {

    }

    @Override
    public void getAll(ServiceRequest.Subscriber<Collection<LectureRoomDto>>[] subscribers) {

    }
}
