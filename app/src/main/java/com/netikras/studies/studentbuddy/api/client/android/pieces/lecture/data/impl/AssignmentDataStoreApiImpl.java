package com.netikras.studies.studentbuddy.api.client.android.pieces.lecture.data.impl;

import com.netikras.studies.studentbuddy.api.client.android.data.cache.CacheManager;
import com.netikras.studies.studentbuddy.api.client.android.data.cache.db.dao.AssignmentDao;
import com.netikras.studies.studentbuddy.api.client.android.data.stores.ApiBasedDataStore;
import com.netikras.studies.studentbuddy.api.client.android.pieces.lecture.data.AssignmentDataStore;
import com.netikras.studies.studentbuddy.api.client.android.service.ServiceRequest;
import com.netikras.studies.studentbuddy.api.client.android.service.ServiceRequest.Subscriber;
import com.netikras.studies.studentbuddy.api.timetable.controller.generated.AssignmentApiConsumer;
import com.netikras.studies.studentbuddy.core.data.api.dto.school.AssignmentDto;

import java.util.Collection;

import javax.inject.Inject;

import static com.netikras.tools.common.security.IntegrityUtils.isNullOrEmpty;

/**
 * Created by netikras on 17.11.11.
 */

public class AssignmentDataStoreApiImpl extends ApiBasedDataStore<String, AssignmentDto> implements AssignmentDataStore {

    @Inject
    AssignmentApiConsumer assignmentApiConsumer;

    @Inject
    public AssignmentDataStoreApiImpl(CacheManager cacheManager) {
        setCache(cacheManager.getDao(AssignmentDao.class));
    }

    @Override
    protected AssignmentDao getCache() {
        return super.getCache();
    }

    @Override
    public void getById(String id, Subscriber<AssignmentDto>... subscribers) {
        if (isCacheEnabled()) {
            AssignmentDto dto = getCached(id);
            if (dto != null) {
                fillFromCache(dto);
                respondCacheHit(dto, subscribers);
            }
        }

        orderData(new ServiceRequest<AssignmentDto>() {
            @Override
            public AssignmentDto request() {
                return updateCache(assignmentApiConsumer.retrieveAssignmentDto(id));
            }
        }, subscribers);
    }

    @Override
    public void create(AssignmentDto item, Subscriber<AssignmentDto>... subscribers) {
        orderData(new ServiceRequest<AssignmentDto>() {
            @Override
            public AssignmentDto request() {
                return updateCache(assignmentApiConsumer.createAssignmentDto(item));
            }
        }, subscribers);
    }

    @Override
    public void create(String lectureId, String description, Subscriber<AssignmentDto>... subscribers) {
        orderData(new ServiceRequest<AssignmentDto>() {
            @Override
            public AssignmentDto request() {
                return updateCache(assignmentApiConsumer.createAssignmentDtoNew(lectureId, description));
            }
        }, subscribers);
    }

    @Override
    public void update(AssignmentDto item, Subscriber<AssignmentDto>... subscribers) {
        orderData(new ServiceRequest<AssignmentDto>() {
            @Override
            public AssignmentDto request() {
                return updateCache(assignmentApiConsumer.updateAssignmentDto(item));
            }
        }, subscribers);
    }

    @Override
    public void purge(String id, Subscriber<Boolean>... subscribers) {
        orderData(new ServiceRequest<Boolean>() {
            @Override
            public Boolean request() {
                assignmentApiConsumer.purgeAssignmentDto(id);
                evict(id);
                return Boolean.TRUE;
            }
        }, subscribers);
    }

    @Override
    public void delete(String id, Subscriber... subscribers) {
        orderData(new ServiceRequest<Boolean>() {
            @Override
            public Boolean request() {
                assignmentApiConsumer.deleteAssignmentDto(id);
                evict(id);
                return Boolean.TRUE;
            }
        }, subscribers);
    }

    @Override
    public void getAllByDiscipline(String id, Long after, Long before, Subscriber<Collection<AssignmentDto>>... subscribers) {
        if (isCacheEnabled()) {
            Collection<AssignmentDto> dtos = getCache().getAllByDisciplineDueBetween(id, after, before);
            if (!isNullOrEmpty(dtos)) {
                fillFromCache(dtos);
                respondCacheHit(dtos, subscribers);
            }
        }

        orderData(new ServiceRequest<Collection<AssignmentDto>>() {
            @Override
            public Collection<AssignmentDto> request() {
                return updateCache(assignmentApiConsumer.getAssignmentDtoAllByDisciplineId(id, after, before));
            }
        }, subscribers);
    }

    @Override
    public void getAllByStudentsGroup(String id, Long after, Long before, Subscriber<Collection<AssignmentDto>>... subscribers) {
        if (isCacheEnabled()) {
            Collection<AssignmentDto> dtos = getCache().getAllByStudentsGroupDueBetween(id, after, before);
            if (!isNullOrEmpty(dtos)) {
                fillFromCache(dtos);
                respondCacheHit(dtos, subscribers);
            }
        }

        orderData(new ServiceRequest<Collection<AssignmentDto>>() {
            @Override
            public Collection<AssignmentDto> request() {
                return updateCache(assignmentApiConsumer.getAssignmentDtoAllByGroupId(id, after, before));
            }
        }, subscribers);
    }

    @Override
    public void getAllByStudent(String id, Long after, Long before, Subscriber<Collection<AssignmentDto>>... subscribers) {
        if (isCacheEnabled()) {
            Collection<AssignmentDto> dtos = getCache().getAllByStudentDueBetween(id, after, before);
            if (!isNullOrEmpty(dtos)) {
                fillFromCache(dtos);
                respondCacheHit(dtos, subscribers);
            }
        }

        orderData(new ServiceRequest<Collection<AssignmentDto>>() {
            @Override
            public Collection<AssignmentDto> request() {
                return updateCache(assignmentApiConsumer.getAssignmentDtoAllByStudentId(id, after, before));
            }
        }, subscribers);
    }

    @Override
    public void getAllByDisciplineAndGroup(String disciplineId, String groupId, Long after, Long before, Subscriber<Collection<AssignmentDto>>... subscribers) {
        if (isCacheEnabled()) {
            Collection<AssignmentDto> dtos = getCache().getAllByDisciplineAndGroupDueBetween(disciplineId, groupId, after, before);
            if (!isNullOrEmpty(dtos)) {
                fillFromCache(dtos);
                respondCacheHit(dtos, subscribers);
            }
        }
        orderData(new ServiceRequest<Collection<AssignmentDto>>() {
            @Override
            public Collection<AssignmentDto> request() {
                return updateCache(assignmentApiConsumer.getAssignmentDtoAllByDisciplineIdAndGroupId(disciplineId, groupId, after, before));
            }
        }, subscribers);
    }

    @Override
    public void getAllByDisciplineAndStudent(String disciplineId, String studentId, Long after, Long before, Subscriber<Collection<AssignmentDto>>... subscribers) {
        orderData(new ServiceRequest<Collection<AssignmentDto>>() {
            @Override
            public Collection<AssignmentDto> request() {
                return updateCache(assignmentApiConsumer.getAssignmentDtoAllByDisciplineIdAndStudentId(disciplineId, studentId, after, before));
            }
        }, subscribers);
    }

    @Override
    public void getAllByLecture(String id, Subscriber<Collection<AssignmentDto>>... subscribers) {
        if (isCacheEnabled()) {
            Collection<AssignmentDto> dtos = getCache().getAllByLectureId(id);
            if (!isNullOrEmpty(dtos)) {
                fillFromCache(dtos);
                respondCacheHit(dtos, subscribers);
            }
        }
        orderData(new ServiceRequest<Collection<AssignmentDto>>() {
            @Override
            public Collection<AssignmentDto> request() {
                return updateCache(assignmentApiConsumer.getAssignmentDtoAllByLectureId(id));
            }
        }, subscribers);
    }


    @Override
    @Deprecated
    public void getAll(Subscriber<Collection<AssignmentDto>>... subscribers) {
        notifyNotImplemented(subscribers);
    }
}
