package com.netikras.studies.studentbuddy.api.client.android.pieces.student.data.impl;

import com.netikras.studies.studentbuddy.api.client.android.data.cache.CacheManager;
import com.netikras.studies.studentbuddy.api.client.android.pieces.student.data.cahe.StudentDao;
import com.netikras.studies.studentbuddy.api.client.android.data.stores.ApiBasedDataStore;
import com.netikras.studies.studentbuddy.api.client.android.pieces.student.data.StudentDataStore;
import com.netikras.studies.studentbuddy.api.client.android.service.ServiceRequest;
import com.netikras.studies.studentbuddy.api.client.android.service.ServiceRequest.Subscriber;
import com.netikras.studies.studentbuddy.api.user.generated.StudentApiConsumer;
import com.netikras.studies.studentbuddy.api.user.mgmt.generated.AdminStudentApiConsumer;
import com.netikras.studies.studentbuddy.core.data.api.dto.school.StudentDto;

import java.util.Collection;
import java.util.List;

import javax.inject.Inject;

import static com.netikras.tools.common.security.IntegrityUtils.isNullOrEmpty;

/**
 * Created by netikras on 17.11.11.
 */

public class StudentDataStoreApiImpl extends ApiBasedDataStore<String, StudentDto> implements StudentDataStore {
    
    @Inject
    StudentApiConsumer studentApiConsumer;
    @Inject
    AdminStudentApiConsumer adminStudentApiConsumer;

    @Inject
    public StudentDataStoreApiImpl(CacheManager cacheManager) {
        setCache(new StudentDao(cacheManager));
    }

    @Override
    protected StudentDao getCache() {
        return super.getCache();
    }

    @Override
    public void getById(String id, Subscriber<StudentDto>... subscribers) {

        if (isCacheEnabled()) {
            StudentDto studentDto = getCached(id);
            if (studentDto != null) {
                fillFromCache(studentDto);
                respondCacheHit(studentDto, subscribers);
            }
        }

        orderData(new ServiceRequest<StudentDto>() {
            @Override
            public StudentDto request() {
                return updateCache(studentApiConsumer.retrieveStudentDto(id));
            }
        }, subscribers);
    }

    @Override
    public void create(StudentDto item, Subscriber<StudentDto>... subscribers) {
        orderData(new ServiceRequest<StudentDto>() {
            @Override
            public StudentDto request() {
                return updateCache(adminStudentApiConsumer.createStudentDto(item));
            }
        }, subscribers);
    }

    @Override
    public void update(StudentDto item, Subscriber<StudentDto>... subscribers) {
        orderData(new ServiceRequest<StudentDto>() {
            @Override
            public StudentDto request() {
                return updateCache(studentApiConsumer.updateStudentDto(item));
            }
        }, subscribers);
    }

    @Override
    public void purge(String id, Subscriber<Boolean>... subscribers) {
        orderData(new ServiceRequest<Boolean>() {
            @Override
            public Boolean request() {
                adminStudentApiConsumer.purgeStudentDto(id);
                evict(id);
                return Boolean.TRUE;
            }
        }, subscribers);
    }

    @Override
    public void getAll(Subscriber<Collection<StudentDto>>... subscribers) {
        notifyNotImplemented(subscribers);
    }

    @Override
    public void delete(String id, Subscriber... subscribers) {
        orderData(new ServiceRequest<Boolean>() {
            @Override
            public Boolean request() {
                adminStudentApiConsumer.deleteStudentDto(id);
                evict(id);
                return Boolean.TRUE;
            }
        }, subscribers);
    }

    @Override
    public void getAllByGroup(String id, Subscriber<Collection<StudentDto>>... subscribers) {
        if (isCacheEnabled()) {
            List<StudentDto> studentDtos = getCache().getAllByGroupId(id);
            if (!isNullOrEmpty(studentDtos)) {
                fillFromCache(studentDtos);
                respondCacheHit(studentDtos, subscribers);
            }
        }

        orderData(new ServiceRequest<Collection<StudentDto>>() {
            @Override
            public Collection<StudentDto> request() {
                return updateCache(studentApiConsumer.getStudentDtoAllByGroup(id));
            }
        }, subscribers);
    }


    @Override
    public void getAllByPerson(String id, Subscriber<Collection<StudentDto>>... subscribers) {

        List<StudentDto> cached = getCache().getAllByPersonId(id);
        if (!isNullOrEmpty(cached)) {
            fillFromCache(cached);
            respondCacheHit(cached, subscribers);
        }

        orderData(new ServiceRequest<Collection<StudentDto>>() {
            @Override
            public Collection<StudentDto> request() {
                return updateCache(studentApiConsumer.getStudentDtoAllByPersonId(id));
            }
        }, subscribers);
    }
}
