package com.netikras.studies.studentbuddy.api.client.android.pieces.discipline.data.impl;

import com.netikras.studies.studentbuddy.api.client.android.data.stores.ApiBasedDataStore;
import com.netikras.studies.studentbuddy.api.client.android.pieces.discipline.data.CourseDataStore;
import com.netikras.studies.studentbuddy.api.client.android.service.ServiceRequest;
import com.netikras.studies.studentbuddy.api.client.android.service.ServiceRequest.Subscriber;
import com.netikras.studies.studentbuddy.api.location.generated.SchoolApiConsumer;
import com.netikras.studies.studentbuddy.core.data.api.dto.PersonDto;
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
    public void getById(String id, Subscriber<CourseDto>... subscribers) {
        orderData(new ServiceRequest<CourseDto>(){
            @Override
            public CourseDto request() {
                return schoolApiConsumer.retrieveCourseDto(id);
            }
        }, subscribers);
    }

    @Override
    public void create(CourseDto item, Subscriber<CourseDto>... subscribers) {
        orderData(new ServiceRequest<CourseDto>(){
            @Override
            public CourseDto request() {
                return schoolApiConsumer.createCourseDto(item);
            }
        }, subscribers);
    }

    @Override
    public void update(CourseDto item, Subscriber<CourseDto>... subscribers) {
        orderData(new ServiceRequest<CourseDto>(){
            @Override
            public CourseDto request() {
                return schoolApiConsumer.updateCourseDto(item);
            }
        }, subscribers);
    }

    @Override
    public void purge(String id, Subscriber<Boolean>... subscribers) {
        orderData(new ServiceRequest<Boolean>(){
            @Override
            public Boolean request() {
                schoolApiConsumer.purgeCourseDto(id);
                return Boolean.TRUE;
            }
        }, subscribers);
    }

    @Override
    public void delete(String id, Subscriber... subscribers) {
        orderData(new ServiceRequest<Boolean>(){
            @Override
            public Boolean request() {
                schoolApiConsumer.deleteCourseDto(id);
                return Boolean.TRUE;
            }
        }, subscribers);
    }

    @Override
    public void getAll(Subscriber<Collection<CourseDto>>... subscribers) {
        orderData(new ServiceRequest<Collection<CourseDto>>(){
            @Override
            public Collection<CourseDto> request() {
                return schoolApiConsumer.getCourseDtoAll();
            }
        }, subscribers);
    }

    @Override
    public void getAllForSchool(String schoolId, Subscriber<Collection<CourseDto>>... subscribers) {
        orderData(new ServiceRequest<Collection<CourseDto>>(){
            @Override
            public Collection<CourseDto> request() {
                return schoolApiConsumer.getCourseDtoAllBySchoolId(schoolId);
            }
        }, subscribers);
    }

    @Override
    public void getAllForDiscipline(String disciplineId, Subscriber<Collection<CourseDto>>... subscribers) {
        orderData(new ServiceRequest<Collection<CourseDto>>(){
            @Override
            public Collection<CourseDto> request() {
                return schoolApiConsumer.getCourseDtoAllByDisciplineId(disciplineId);
            }
        }, subscribers);
    }
}
