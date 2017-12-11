package com.netikras.studies.studentbuddy.api.client.android.pieces.student.data.impl;

import com.netikras.studies.studentbuddy.api.client.android.data.cache.CacheManager;
import com.netikras.studies.studentbuddy.api.client.android.data.cache.db.dao.GenericDao;
import com.netikras.studies.studentbuddy.api.client.android.data.cache.db.dao.GroupDao;
import com.netikras.studies.studentbuddy.api.client.android.data.stores.ApiBasedDataStore;
import com.netikras.studies.studentbuddy.api.client.android.pieces.student.data.StudentsGroupDataStore;
import com.netikras.studies.studentbuddy.api.client.android.service.ServiceRequest;
import com.netikras.studies.studentbuddy.api.client.android.service.ServiceRequest.Subscriber;
import com.netikras.studies.studentbuddy.api.user.generated.StudentApiConsumer;
import com.netikras.studies.studentbuddy.api.user.mgmt.generated.AdminStudentApiConsumer;
import com.netikras.studies.studentbuddy.core.data.api.dto.school.StudentsGroupDto;

import java.util.Collection;
import java.util.List;

import javax.inject.Inject;

import static com.netikras.tools.common.security.IntegrityUtils.isNullOrEmpty;

/**
 * Created by netikras on 17.11.11.
 */

public class StudentsGroupDataStoreApiImpl extends ApiBasedDataStore<String, StudentsGroupDto> implements StudentsGroupDataStore {
    
    @Inject
    StudentApiConsumer studentApiConsumer;
    @Inject
    AdminStudentApiConsumer adminStudentApiConsumer;

    @Inject
    public StudentsGroupDataStoreApiImpl(CacheManager cacheManager) {
        setCache(cacheManager.getDao(GroupDao.class));
    }

    @Override
    protected GroupDao getCache() {
        return super.getCache();
    }

    @Override
    public void getById(String id, Subscriber<StudentsGroupDto>... subscribers) {
        if (isCacheEnabled()) {
            StudentsGroupDto dto = getCached(id);
            if (dto != null) {
                fillFromCache(dto);
                respondCacheHit(dto, subscribers);
            }
        }
        orderData(new ServiceRequest<StudentsGroupDto>() {
            @Override
            public StudentsGroupDto request() {
                return updateCache(studentApiConsumer.retrieveStudentsGroupDto(id));
            }
        }, subscribers);
    }

    @Override
    public void getByTitle(String name, Subscriber<StudentsGroupDto>... subscribers) {

        if (isCacheEnabled()) {
            StudentsGroupDto dto = getCache().getByTitle(name);
            if (dto != null) {
                fillFromCache(dto);
                respondCacheHit(dto, subscribers);
            }
        }

        orderData(new ServiceRequest<StudentsGroupDto>() {
            @Override
            public StudentsGroupDto request() {
                return updateCache(studentApiConsumer.getStudentsGroupDtoByTitle(name));
            }
        }, subscribers);
    }

    @Override
    public void create(StudentsGroupDto item, Subscriber<StudentsGroupDto>... subscribers) {
        orderData(new ServiceRequest<StudentsGroupDto>() {
            @Override
            public StudentsGroupDto request() {
                return updateCache(adminStudentApiConsumer.createStudentsGroupDto(item));
            }
        }, subscribers);
    }

    @Override
    public void update(StudentsGroupDto item, Subscriber<StudentsGroupDto>... subscribers) {
        notifyNotImplemented(subscribers);
    }

    @Override
    public void purge(String id, Subscriber<Boolean>... subscribers) {
        orderData(new ServiceRequest<Boolean>() {
            @Override
            public Boolean request() {
                adminStudentApiConsumer.purgeStudentsGroupDto(id);
                evict(id);
                return Boolean.TRUE;
            }
        }, subscribers);
    }

    @Override
    public void delete(String id, Subscriber<Boolean>... subscribers) {
        orderData(new ServiceRequest<Boolean>() {
            @Override
            public Boolean request() {
                adminStudentApiConsumer.purgeStudentsGroupDto(id);
                evict(id);
                return Boolean.TRUE;
            }
        }, subscribers);
    }

    @Override
    public void getAll(Subscriber<Collection<StudentsGroupDto>>... subscribers) {

        if (isCacheEnabled()) {
            Collection<StudentsGroupDto> groupDtos = getCache().getAll();
            if (!isNullOrEmpty(groupDtos)) {
                respondCacheHit(groupDtos, subscribers);
            }
        }

        orderData(new ServiceRequest<Collection<StudentsGroupDto>>() {
            @Override
            public Collection<StudentsGroupDto> request() {
                return updateCache(studentApiConsumer.getStudentsGroupDtoAll());
            }
        }, subscribers);
    }



    @Override
    public void addStudentToGroup(String groupId, String studentId, Subscriber<Boolean>... subscribers) {
        orderData(new ServiceRequest<Boolean>() {
            @Override
            public Boolean request() {
                adminStudentApiConsumer.addStudentDtoToGroup(groupId, studentId);
                return Boolean.TRUE;
            }
        }, subscribers);
    }

    @Override
    public void removeStudentFromGroup(String groupId, String studentId, Subscriber<Boolean>... subscribers) {
        orderData(new ServiceRequest<Boolean>() {
            @Override
            public Boolean request() {
                adminStudentApiConsumer.removeStudentDtoFromGroup(groupId, studentId);
                return Boolean.TRUE;
            }
        }, subscribers);
    }


    @Override
    public void addAllStudentsToGroup(String groupId, List<String> studentIds, Subscriber<Boolean>... subscribers) {
        orderData(new ServiceRequest<Boolean>() {
            @Override
            public Boolean request() {
                adminStudentApiConsumer.addStudentDtoAllToGroup(groupId, studentIds);
                return Boolean.TRUE;
            }
        }, subscribers);
    }

    @Override
    public void removeAllStudentsFromGroup(String groupId, List<String> studentIds, Subscriber<Boolean>... subscribers) {
        orderData(new ServiceRequest<Boolean>() {
            @Override
            public Boolean request() {
                adminStudentApiConsumer.removeStudentDtoAllFromGroup(groupId, studentIds);
                return Boolean.TRUE;
            }
        }, subscribers);
    }
}
