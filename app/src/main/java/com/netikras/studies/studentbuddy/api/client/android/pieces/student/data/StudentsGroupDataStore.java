package com.netikras.studies.studentbuddy.api.client.android.pieces.student.data;

import com.netikras.studies.studentbuddy.api.client.android.data.stores.CrudDataStore;
import com.netikras.studies.studentbuddy.api.client.android.service.ServiceRequest;
import com.netikras.studies.studentbuddy.core.data.api.dto.school.StudentsGroupDto;

import java.util.List;

/**
 * Created by netikras on 17.11.11.
 */

public interface StudentsGroupDataStore extends CrudDataStore<String, StudentsGroupDto> {
    void getByTitle(String name, ServiceRequest.Subscriber<StudentsGroupDto>... subscribers);

    void addStudentToGroup(String groupId, String studentId, ServiceRequest.Subscriber<Boolean>... subscribers);

    void removeStudentFromGroup(String groupId, String studentId, ServiceRequest.Subscriber<Boolean>... subscribers);

    void addAllStudentsToGroup(String groupId, List<String> studentIds, ServiceRequest.Subscriber<Boolean>... subscribers);

    void removeAllStudentsFromGroup(String groupId, List<String> studentIds, ServiceRequest.Subscriber<Boolean>... subscribers);
}
