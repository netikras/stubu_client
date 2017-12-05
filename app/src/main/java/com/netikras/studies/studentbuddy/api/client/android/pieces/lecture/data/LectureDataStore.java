package com.netikras.studies.studentbuddy.api.client.android.pieces.lecture.data;

import com.netikras.studies.studentbuddy.api.client.android.data.stores.CrudDataStore;
import com.netikras.studies.studentbuddy.api.client.android.service.ServiceRequest;
import com.netikras.studies.studentbuddy.api.misc.TimeUnit;
import com.netikras.studies.studentbuddy.core.data.api.dto.school.LectureDto;

import java.util.Collection;

/**
 * Created by netikras on 17.11.11.
 */

public interface LectureDataStore extends CrudDataStore<String, LectureDto> {

    void getAllByGroup(String id, Long after, Long before, ServiceRequest.Subscriber<Collection<LectureDto>>... subscribers);

    void getAllByLecturer(String id, Long after, Long before, ServiceRequest.Subscriber<Collection<LectureDto>>... subscribers);

    void getAllByRoom(String id, Long after, Long before, ServiceRequest.Subscriber<Collection<LectureDto>>... subscribers);

    void getAllByStudent(String id, Long after, Long before, ServiceRequest.Subscriber<Collection<LectureDto>>... subscribers);

    void getAllByGuest(String id, Long after, Long before, ServiceRequest.Subscriber<Collection<LectureDto>>... subscribers);

    void getAllByGroup(String id, TimeUnit unit, Long amount, ServiceRequest.Subscriber<Collection<LectureDto>>... subscribers);

    void getAllByLecturer(String id, TimeUnit unit, Long amount, ServiceRequest.Subscriber<Collection<LectureDto>>... subscribers);

    void getAllByRoom(String id, TimeUnit unit, Long amount, ServiceRequest.Subscriber<Collection<LectureDto>>... subscribers);

    void getAllByStudent(String id, TimeUnit unit, Long amount, ServiceRequest.Subscriber<Collection<LectureDto>>... subscribers);

    void getAllByGuest(String id, TimeUnit hours, long amount, ServiceRequest.Subscriber<Collection<LectureDto>>... subscribers);
}
