package com.netikras.studies.studentbuddy.api.client.android.pieces.lecture.data.impl;

import com.netikras.studies.studentbuddy.api.client.android.data.stores.ApiBasedDataStore;
import com.netikras.studies.studentbuddy.api.client.android.pieces.lecture.data.TestDataStore;
import com.netikras.studies.studentbuddy.api.client.android.service.ServiceRequest;
import com.netikras.studies.studentbuddy.core.data.api.dto.school.DisciplineTestDto;

import java.util.Collection;

/**
 * Created by netikras on 17.11.11.
 */

public class TestDataStoreApiImpl extends ApiBasedDataStore<String, DisciplineTestDto> implements TestDataStore {

    @Override
    public void getById(String id, ServiceRequest.Subscriber<DisciplineTestDto>[] subscribers) {

    }

    @Override
    public void create(DisciplineTestDto item, ServiceRequest.Subscriber<DisciplineTestDto>[] subscribers) {

    }

    @Override
    public void update(DisciplineTestDto item, ServiceRequest.Subscriber<DisciplineTestDto>[] subscribers) {

    }

    @Override
    public void purge(String id, ServiceRequest.Subscriber<Boolean>[] subscribers) {

    }

    @Override
    public void delete(String id, ServiceRequest.Subscriber[] subscribers) {

    }

    @Override
    public void getAll(ServiceRequest.Subscriber<Collection<DisciplineTestDto>>[] subscribers) {

    }
}
