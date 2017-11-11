package com.netikras.studies.studentbuddy.api.client.android.pieces.lecturer.data;

import com.netikras.studies.studentbuddy.api.client.android.data.stores.CrudDataStore;
import com.netikras.studies.studentbuddy.api.client.android.service.ServiceRequest;
import com.netikras.studies.studentbuddy.core.data.api.dto.school.LecturerDto;

import java.util.Collection;

/**
 * Created by netikras on 17.11.11.
 */

public interface LecturerDataStore extends CrudDataStore<String, LecturerDto> {

    void assignToDiscipline(String lecturerId, String disciplineId, ServiceRequest.Subscriber... subscribers);

    void unassignFromDiscipline(String lecturerId, String disciplineId, ServiceRequest.Subscriber... subscribers);

    void getAllByPerson(String id, ServiceRequest.Subscriber<Collection<LecturerDto>>... subscribers);

    void getAllByDiscipline(String id, ServiceRequest.Subscriber<Collection<LecturerDto>>... subscribers);
}
