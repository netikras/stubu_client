package com.netikras.studies.studentbuddy.api.client.android.pieces.student.data;

import com.netikras.studies.studentbuddy.api.client.android.data.stores.CrudDataStore;
import com.netikras.studies.studentbuddy.api.client.android.service.ServiceRequest;
import com.netikras.studies.studentbuddy.core.data.api.dto.school.LectureGuestDto;

import java.util.Collection;

/**
 * Created by netikras on 17.11.11.
 */

public interface GuestDataStore extends CrudDataStore<String, LectureGuestDto> {

    void getAllByPerson(String id, ServiceRequest.Subscriber<Collection<LectureGuestDto>>... subscribers);

}
