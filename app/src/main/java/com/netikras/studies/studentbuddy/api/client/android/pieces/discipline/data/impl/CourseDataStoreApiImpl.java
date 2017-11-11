package com.netikras.studies.studentbuddy.api.client.android.pieces.discipline.data.impl;

import com.netikras.studies.studentbuddy.api.client.android.data.stores.ApiBasedDataStore;
import com.netikras.studies.studentbuddy.api.client.android.pieces.discipline.data.CourseDataStore;
import com.netikras.studies.studentbuddy.api.client.android.service.ServiceRequest;
import com.netikras.studies.studentbuddy.api.location.generated.SchoolApiConsumer;
import com.netikras.studies.studentbuddy.core.data.api.dto.school.CourseDto;

import java.util.Collection;

import javax.inject.Inject;

/**
 * Created by netikras on 17.11.11.
 */

public class CourseDataStoreApiImpl extends ApiBasedDataStore<String, CourseDto> implements CourseDataStore {

    @Inject
    SchoolApiConsumer schoolApiConsumer;


    @Override
    public void getById(String id, ServiceRequest.Subscriber<CourseDto>[] subscribers) {

    }

    @Override
    public void create(CourseDto item, ServiceRequest.Subscriber<CourseDto>[] subscribers) {

    }

    @Override
    public void update(CourseDto item, ServiceRequest.Subscriber<CourseDto>[] subscribers) {

    }

    @Override
    public void purge(String id, ServiceRequest.Subscriber<Boolean>[] subscribers) {

    }

    @Override
    public void delete(String id, ServiceRequest.Subscriber[] subscribers) {

    }

    @Override
    public void getAll(ServiceRequest.Subscriber<Collection<CourseDto>>[] subscribers) {

    }
}
