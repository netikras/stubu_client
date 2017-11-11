package com.netikras.studies.studentbuddy.api.client.android.pieces.lecture.data.impl;

import com.netikras.studies.studentbuddy.api.client.android.data.stores.ApiBasedDataStore;
import com.netikras.studies.studentbuddy.api.client.android.pieces.lecture.data.LectureDataStore;
import com.netikras.studies.studentbuddy.api.client.android.service.ServiceRequest;
import com.netikras.studies.studentbuddy.core.data.api.dto.school.LectureDto;

import java.util.Collection;

/**
 * Created by netikras on 17.11.11.
 */

public class LectureDataStoreApiImpl extends ApiBasedDataStore<String, LectureDto> implements LectureDataStore {



    @Override
    public void getById(String id, ServiceRequest.Subscriber<LectureDto>[] subscribers) {

    }

    @Override
    public void create(LectureDto item, ServiceRequest.Subscriber<LectureDto>[] subscribers) {

    }

    @Override
    public void update(LectureDto item, ServiceRequest.Subscriber<LectureDto>[] subscribers) {

    }

    @Override
    public void purge(String id, ServiceRequest.Subscriber<Boolean>[] subscribers) {

    }

    @Override
    public void delete(String id, ServiceRequest.Subscriber[] subscribers) {

    }

    @Override
    public void getAll(ServiceRequest.Subscriber<Collection<LectureDto>>[] subscribers) {

    }
}
