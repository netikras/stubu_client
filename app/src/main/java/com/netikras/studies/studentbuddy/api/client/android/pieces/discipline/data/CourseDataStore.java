package com.netikras.studies.studentbuddy.api.client.android.pieces.discipline.data;

import com.netikras.studies.studentbuddy.api.client.android.data.stores.CrudDataStore;
import com.netikras.studies.studentbuddy.api.client.android.service.ServiceRequest;
import com.netikras.studies.studentbuddy.core.data.api.dto.school.CourseDto;

import java.util.Collection;

/**
 * Created by netikras on 17.11.11.
 */

public interface CourseDataStore extends CrudDataStore<String, CourseDto> {

    void getAllForSchool(String schoolId, ServiceRequest.Subscriber<Collection<CourseDto>>... subscribers);

    void getAllForDiscipline(String disciplineId, ServiceRequest.Subscriber<Collection<CourseDto>>... subscribers);
}
