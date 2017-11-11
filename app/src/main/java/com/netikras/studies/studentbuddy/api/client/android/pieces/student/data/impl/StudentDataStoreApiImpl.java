package com.netikras.studies.studentbuddy.api.client.android.pieces.student.data.impl;

import com.netikras.studies.studentbuddy.api.client.android.data.stores.ApiBasedDataStore;
import com.netikras.studies.studentbuddy.api.client.android.pieces.student.data.StudentDataStore;
import com.netikras.studies.studentbuddy.api.client.android.service.ServiceRequest;
import com.netikras.studies.studentbuddy.api.client.android.service.ServiceRequest.Subscriber;
import com.netikras.studies.studentbuddy.api.user.generated.StudentApiConsumer;
import com.netikras.studies.studentbuddy.api.user.mgmt.generated.AdminStudentApiConsumer;
import com.netikras.studies.studentbuddy.core.data.api.dto.school.StudentDto;

import java.util.Collection;

import javax.inject.Inject;

/**
 * Created by netikras on 17.11.11.
 */

public class StudentDataStoreApiImpl extends ApiBasedDataStore<String, StudentDto> implements StudentDataStore {
    
    @Inject
    StudentApiConsumer studentApiConsumer;
    @Inject
    AdminStudentApiConsumer adminStudentApiConsumer;
    
    @Override
    public void getById(String id, Subscriber<StudentDto>... subscribers) {
        orderData(new ServiceRequest<StudentDto>() {
            @Override
            public StudentDto request() {
                return studentApiConsumer.retrieveStudentDto(id);
            }
        }, subscribers);
    }

    @Override
    public void create(StudentDto item, Subscriber<StudentDto>... subscribers) {
        orderData(new ServiceRequest<StudentDto>() {
            @Override
            public StudentDto request() {
                return adminStudentApiConsumer.createStudentDto(item);
            }
        }, subscribers);
    }

    @Override
    public void update(StudentDto item, Subscriber<StudentDto>... subscribers) {
        orderData(new ServiceRequest<StudentDto>() {
            @Override
            public StudentDto request() {
                return studentApiConsumer.updateStudentDto(item);
            }
        }, subscribers);
    }

    @Override
    public void purge(String id, Subscriber<Boolean>... subscribers) {
        orderData(new ServiceRequest<Boolean>() {
            @Override
            public Boolean request() {
                adminStudentApiConsumer.purgeStudentDto(id);
                return Boolean.TRUE;
            }
        }, subscribers);
    }

    @Override
    public void getAll(Subscriber<Collection<StudentDto>>[] subscribers) {
        notifyNotImplemented(subscribers);
    }

    @Override
    public void delete(String id, Subscriber... subscribers) {
        orderData(new ServiceRequest<Boolean>() {
            @Override
            public Boolean request() {
                adminStudentApiConsumer.deleteStudentDto(id);
                return Boolean.TRUE;
            }
        }, subscribers);
    }

    @Override
    public void getAllByGroup(String id, Subscriber<Collection<StudentDto>>... subscribers) {
        orderData(new ServiceRequest<Collection<StudentDto>>() {
            @Override
            public Collection<StudentDto> request() {
                return studentApiConsumer.getStudentDtoAllByGroup(id);
            }
        }, subscribers);
    }


    @Override
    public void getAllByPerson(String id, Subscriber<Collection<StudentDto>>... subscribers) {
        orderData(new ServiceRequest<Collection<StudentDto>>() {
            @Override
            public Collection<StudentDto> request() {
                return studentApiConsumer.getStudentDtoAllByPersonId(id);
            }
        }, subscribers);
    }
}
