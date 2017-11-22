package com.netikras.studies.studentbuddy.api.client.android.pieces.lecture.data;

import com.netikras.studies.studentbuddy.api.client.android.data.stores.CrudDataStore;
import com.netikras.studies.studentbuddy.api.client.android.service.ServiceRequest;
import com.netikras.studies.studentbuddy.core.data.api.dto.school.AssignmentDto;

import java.util.Collection;

/**
 * Created by netikras on 17.11.11.
 */

public interface AssignmentDataStore extends CrudDataStore<String, AssignmentDto> {
    void create(String lectureId, String description, ServiceRequest.Subscriber<AssignmentDto>... subscribers);

    void getAllByDiscipline(String id, Long after, Long before, ServiceRequest.Subscriber<Collection<AssignmentDto>>... subscribers);

    void getAllByStudentsGroup(String id, Long after, Long before, ServiceRequest.Subscriber<Collection<AssignmentDto>>... subscribers);

    void getAllByStudent(String id, Long after, Long before, ServiceRequest.Subscriber<Collection<AssignmentDto>>... subscribers);

    void getAllByDisciplineAndGroup(String disciplineId, String groupId, Long after, Long before, ServiceRequest.Subscriber<Collection<AssignmentDto>>... subscribers);

    void getAllByDisciplineAndStudent(String disciplineId, String studentId, Long after, Long before, ServiceRequest.Subscriber<Collection<AssignmentDto>>... subscribers);

    void getAllByLecture(String id, ServiceRequest.Subscriber<Collection<AssignmentDto>>... subscribers);
}
