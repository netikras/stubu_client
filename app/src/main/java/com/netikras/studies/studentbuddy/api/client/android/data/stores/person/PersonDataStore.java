package com.netikras.studies.studentbuddy.api.client.android.data.stores.person;

import com.netikras.studies.studentbuddy.api.client.android.data.stores.CrudDataStore;
import com.netikras.studies.studentbuddy.api.client.android.service.ServiceRequest;
import com.netikras.studies.studentbuddy.core.data.api.dto.PersonDto;

/**
 * Created by netikras on 17.10.31.
 */

public interface PersonDataStore extends CrudDataStore<String, PersonDto> {


    void getByIdentifier(String id, ServiceRequest.Subscriber<PersonDto>... subscribers);
}
