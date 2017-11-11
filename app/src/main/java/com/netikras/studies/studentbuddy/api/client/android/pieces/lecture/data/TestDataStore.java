package com.netikras.studies.studentbuddy.api.client.android.pieces.lecture.data;

import com.netikras.studies.studentbuddy.api.client.android.data.stores.CrudDataStore;
import com.netikras.studies.studentbuddy.api.client.android.service.ServiceRequest;
import com.netikras.studies.studentbuddy.core.data.api.dto.school.DisciplineTestDto;

import java.util.Collection;

/**
 * Created by netikras on 17.11.11.
 */

public interface TestDataStore extends CrudDataStore<String, DisciplineTestDto> {
    void createForDiscipline(String id, String description, ServiceRequest.Subscriber<DisciplineTestDto>... subscribers);

    void getAllByDiscipline(String id, ServiceRequest.Subscriber<Collection<DisciplineTestDto>>... subscribers);

    void getAllByDisciplineAndGroup(String disciplineId, String groupId, ServiceRequest.Subscriber<Collection<DisciplineTestDto>>... subscribers);

    void getAllByDiscipline(String id, Long after, Long before, ServiceRequest.Subscriber<Collection<DisciplineTestDto>>... subscribers);

    void getAllByDisciplineAndGroup(String disciplineId, String groupId, Long after, Long before, ServiceRequest.Subscriber<Collection<DisciplineTestDto>>... subscribers);
}
