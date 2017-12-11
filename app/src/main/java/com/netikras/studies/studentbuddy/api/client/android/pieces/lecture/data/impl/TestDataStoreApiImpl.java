package com.netikras.studies.studentbuddy.api.client.android.pieces.lecture.data.impl;

import com.netikras.studies.studentbuddy.api.client.android.data.cache.CacheManager;
import com.netikras.studies.studentbuddy.api.client.android.data.cache.db.dao.TestDao;
import com.netikras.studies.studentbuddy.api.client.android.data.stores.ApiBasedDataStore;
import com.netikras.studies.studentbuddy.api.client.android.pieces.lecture.data.TestDataStore;
import com.netikras.studies.studentbuddy.api.client.android.service.ServiceRequest;
import com.netikras.studies.studentbuddy.api.client.android.service.ServiceRequest.Subscriber;
import com.netikras.studies.studentbuddy.api.timetable.controller.generated.TestsApiConsumer;
import com.netikras.studies.studentbuddy.core.data.api.dto.school.DisciplineTestDto;

import java.util.Collection;
import java.util.List;

import javax.inject.Inject;

import static com.netikras.tools.common.security.IntegrityUtils.isNullOrEmpty;

/**
 * Created by netikras on 17.11.11.
 */

public class TestDataStoreApiImpl extends ApiBasedDataStore<String, DisciplineTestDto> implements TestDataStore {

    @Inject
    TestsApiConsumer testsApiConsumer;

    @Inject
    public TestDataStoreApiImpl(CacheManager cacheManager) {
        setCache(cacheManager.getDao(TestDao.class));
    }

    @Override
    protected TestDao getCache() {
        return super.getCache();
    }

    @Override
    public void getById(String id, Subscriber<DisciplineTestDto>... subscribers) {
        if (isCacheEnabled()) {
            DisciplineTestDto dto = getCached(id);
            if (dto != null) {
                fillFromCache(dto);
                respondCacheHit(dto, subscribers);
            }
        }
        orderData(new ServiceRequest<DisciplineTestDto>() {
            @Override
            public DisciplineTestDto request() {
                return updateCache(testsApiConsumer.retrieveDisciplineTestDto(id));
            }
        }, subscribers);
    }

    @Override
    public void create(DisciplineTestDto item, Subscriber<DisciplineTestDto>... subscribers) {
        orderData(new ServiceRequest<DisciplineTestDto>() {
            @Override
            public DisciplineTestDto request() {
                return updateCache(testsApiConsumer.createDisciplineTestDto(item));
            }
        }, subscribers);
    }

    @Override
    public void createForDiscipline(String id, String description, Subscriber<DisciplineTestDto>... subscribers) {
        orderData(new ServiceRequest<DisciplineTestDto>() {
            @Override
            public DisciplineTestDto request() {
                return updateCache(testsApiConsumer.createDisciplineTestDtoNew(id, description));
            }
        }, subscribers);
    }

    @Override
    public void update(DisciplineTestDto item, Subscriber<DisciplineTestDto>... subscribers) {
        orderData(new ServiceRequest<DisciplineTestDto>() {
            @Override
            public DisciplineTestDto request() {
                return updateCache(testsApiConsumer.updateDisciplineTestDto(item));
            }
        }, subscribers);
    }

    @Override
    public void purge(String id, Subscriber<Boolean>... subscribers) {
        orderData(new ServiceRequest<Boolean>() {
            @Override
            public Boolean request() {
                testsApiConsumer.purgeDisciplineTestDto(id);
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
                testsApiConsumer.deleteDisciplineTestDto(id);
                evict(id);
                return Boolean.TRUE;
            }
        }, subscribers);
    }

    @Override
    @Deprecated
    public void getAll(Subscriber<Collection<DisciplineTestDto>>... subscribers) {
        notifyNotImplemented(subscribers);
    }

    @Override
    public void getAllByDiscipline(String id, Subscriber<Collection<DisciplineTestDto>>... subscribers) {
        if (isCacheEnabled()) {
            List<DisciplineTestDto> dtos = getCache().getAllByDiscipline(id);
            if (!isNullOrEmpty(dtos)) {
                fillFromCache(dtos);
                respondCacheHit(dtos, subscribers);
            }
        }
        orderData(new ServiceRequest<Collection<DisciplineTestDto>>() {
            @Override
            public Collection<DisciplineTestDto> request() {
                return updateCache(testsApiConsumer.getDisciplineTestDtoAllForDiscipline(id));
            }
        }, subscribers);
    }

    @Override
    public void getAllByDisciplineAndGroup(String disciplineId, String groupId, Subscriber<Collection<DisciplineTestDto>>... subscribers) {
        if (isCacheEnabled()) {
            List<DisciplineTestDto> dtos = getCache().getAllByDisciplineAndGroup(disciplineId, groupId);
            if (!isNullOrEmpty(dtos)) {
                fillFromCache(dtos);
                respondCacheHit(dtos, subscribers);
            }
        }
        orderData(new ServiceRequest<Collection<DisciplineTestDto>>() {
            @Override
            public Collection<DisciplineTestDto> request() {
                return updateCache(testsApiConsumer.getDisciplineTestDtoAllForGroup(disciplineId, groupId));
            }
        }, subscribers);
    }

    @Override
    public void getAllByDiscipline(String id, Long after, Long before, Subscriber<Collection<DisciplineTestDto>>... subscribers) {
        if (isCacheEnabled()) {
            List<DisciplineTestDto> dtos = getCache().getAllByDisciplineStartingBetween(id, after, before);
            if (!isNullOrEmpty(dtos)) {
                fillFromCache(dtos);
                respondCacheHit(dtos, subscribers);
            }
        }
        orderData(new ServiceRequest<Collection<DisciplineTestDto>>() {
            @Override
            public Collection<DisciplineTestDto> request() {
                return updateCache(testsApiConsumer.getDisciplineTestDtoAllForDisciplineInTimeframe(id, after, before));
            }
        }, subscribers);
    }


    @Override
    public void getAllByDisciplineAndGroup(String disciplineId, String groupId, Long after, Long before, Subscriber<Collection<DisciplineTestDto>>... subscribers) {
        if (isCacheEnabled()) {
            List<DisciplineTestDto> dtos = getCache().getAllByDisciplineAndGroupStartingBetween(disciplineId, groupId, after, before);
            if (!isNullOrEmpty(dtos)) {
                fillFromCache(dtos);
                respondCacheHit(dtos, subscribers);
            }
        }
        orderData(new ServiceRequest<Collection<DisciplineTestDto>>() {
            @Override
            public Collection<DisciplineTestDto> request() {
                return updateCache(testsApiConsumer.getDisciplineTestDtoAllForGroupInTimeframe(disciplineId, groupId, after, before));
            }
        }, subscribers);
    }


}
