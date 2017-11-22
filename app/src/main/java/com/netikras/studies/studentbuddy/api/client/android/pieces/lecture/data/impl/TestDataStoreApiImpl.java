package com.netikras.studies.studentbuddy.api.client.android.pieces.lecture.data.impl;

import com.netikras.studies.studentbuddy.api.client.android.data.stores.ApiBasedDataStore;
import com.netikras.studies.studentbuddy.api.client.android.pieces.lecture.data.TestDataStore;
import com.netikras.studies.studentbuddy.api.client.android.service.ServiceRequest;
import com.netikras.studies.studentbuddy.api.client.android.service.ServiceRequest.Subscriber;
import com.netikras.studies.studentbuddy.api.timetable.controller.generated.TestsApiConsumer;
import com.netikras.studies.studentbuddy.core.data.api.dto.school.DisciplineTestDto;
import com.netikras.studies.studentbuddy.core.data.api.dto.school.LectureDto;

import java.util.Collection;

import javax.inject.Inject;

/**
 * Created by netikras on 17.11.11.
 */

public class TestDataStoreApiImpl extends ApiBasedDataStore<String, DisciplineTestDto> implements TestDataStore {

    @Inject
    TestsApiConsumer testsApiConsumer;

    @Inject
    public TestDataStoreApiImpl() {}
    
    @Override
    public void getById(String id, Subscriber<DisciplineTestDto>... subscribers) {
        orderData(new ServiceRequest<DisciplineTestDto>() {
            @Override
            public DisciplineTestDto request() {
                return testsApiConsumer.retrieveDisciplineTestDto(id);
            }
        }, subscribers);
    }

    @Override
    public void create(DisciplineTestDto item, Subscriber<DisciplineTestDto>... subscribers) {
        orderData(new ServiceRequest<DisciplineTestDto>() {
            @Override
            public DisciplineTestDto request() {
                return testsApiConsumer.createDisciplineTestDto(item);
            }
        }, subscribers);
    }

    @Override
    public void createForDiscipline(String id, String description, Subscriber<DisciplineTestDto>... subscribers) {
        orderData(new ServiceRequest<DisciplineTestDto>() {
            @Override
            public DisciplineTestDto request() {
                return testsApiConsumer.createDisciplineTestDtoNew(id, description);
            }
        }, subscribers);
    }

    @Override
    public void update(DisciplineTestDto item, Subscriber<DisciplineTestDto>... subscribers) {
        orderData(new ServiceRequest<DisciplineTestDto>() {
            @Override
            public DisciplineTestDto request() {
                return testsApiConsumer.updateDisciplineTestDto(item);
            }
        }, subscribers);
    }

    @Override
    public void purge(String id, Subscriber<Boolean>... subscribers) {
        orderData(new ServiceRequest<Boolean>() {
            @Override
            public Boolean request() {
                testsApiConsumer.purgeDisciplineTestDto(id);
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
                return Boolean.TRUE;
            }
        }, subscribers);
    }

    @Override
    @Deprecated
    public void getAll(Subscriber<Collection<DisciplineTestDto>>[] subscribers) {

    }

    @Override
    public void getAllByDiscipline(String id, Subscriber<Collection<DisciplineTestDto>>... subscribers) {
        orderData(new ServiceRequest<Collection<DisciplineTestDto>>() {
            @Override
            public Collection<DisciplineTestDto> request() {
                return testsApiConsumer.getDisciplineTestDtoAllForDiscipline(id);
            }
        }, subscribers);
    }

    @Override
    public void getAllByDisciplineAndGroup(String disciplineId, String groupId, Subscriber<Collection<DisciplineTestDto>>... subscribers) {
        orderData(new ServiceRequest<Collection<DisciplineTestDto>>() {
            @Override
            public Collection<DisciplineTestDto> request() {
                return testsApiConsumer.getDisciplineTestDtoAllForGroup(disciplineId, groupId);
            }
        }, subscribers);
    }

    @Override
    public void getAllByDiscipline(String id, Long after, Long before, Subscriber<Collection<DisciplineTestDto>>... subscribers) {
        orderData(new ServiceRequest<Collection<DisciplineTestDto>>() {
            @Override
            public Collection<DisciplineTestDto> request() {
                return testsApiConsumer.getDisciplineTestDtoAllForDisciplineInTimeframe(id, after, before);
            }
        }, subscribers);
    }


    @Override
    public void getAllByDisciplineAndGroup(String disciplineId, String groupId, Long after, Long before, Subscriber<Collection<DisciplineTestDto>>... subscribers) {
        orderData(new ServiceRequest<Collection<DisciplineTestDto>>() {
            @Override
            public Collection<DisciplineTestDto> request() {
                return testsApiConsumer.getDisciplineTestDtoAllForGroupInTimeframe(disciplineId, groupId, after, before);
            }
        }, subscribers);
    }


}
