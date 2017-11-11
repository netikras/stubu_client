package com.netikras.studies.studentbuddy.api.client.android.pieces.lecturer.data.impl;

import com.netikras.studies.studentbuddy.api.client.android.data.stores.ApiBasedDataStore;
import com.netikras.studies.studentbuddy.api.client.android.pieces.lecturer.data.LecturerDataStore;
import com.netikras.studies.studentbuddy.api.client.android.service.ServiceRequest;
import com.netikras.studies.studentbuddy.core.data.api.dto.school.LecturerDto;

import java.util.Collection;

/**
 * Created by netikras on 17.11.11.
 */

public class LecturerDataStoreApiImpl extends ApiBasedDataStore<String, LecturerDto> implements LecturerDataStore {
    @Override
    public void getById(String id, ServiceRequest.Subscriber<LecturerDto>[] subscribers) {

    }

    @Override
    public void create(LecturerDto item, ServiceRequest.Subscriber<LecturerDto>[] subscribers) {

    }

    @Override
    public void update(LecturerDto item, ServiceRequest.Subscriber<LecturerDto>[] subscribers) {

    }

    @Override
    public void purge(String id, ServiceRequest.Subscriber<Boolean>[] subscribers) {

    }

    @Override
    public void delete(String id, ServiceRequest.Subscriber[] subscribers) {

    }

    @Override
    public void getAll(ServiceRequest.Subscriber<Collection<LecturerDto>>[] subscribers) {

    }
}
