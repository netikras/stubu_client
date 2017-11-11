package com.netikras.studies.studentbuddy.api.client.android.pieces.person.data;

import com.netikras.studies.studentbuddy.api.client.android.data.stores.CrudDataStore;
import com.netikras.studies.studentbuddy.api.client.android.service.ServiceRequest;
import com.netikras.studies.studentbuddy.core.data.api.dto.PersonDto;

import java.util.Collection;

/**
 * Created by netikras on 17.10.31.
 */

public interface PersonDataStore extends CrudDataStore<String, PersonDto> {


    void getByIdentifier(String id, ServiceRequest.Subscriber<PersonDto>... subscribers);

    void getByCode(String value, ServiceRequest.Subscriber<PersonDto>... subscribers);

    void getByFirstName(String fname, ServiceRequest.Subscriber<Collection<PersonDto>>... subscribers);

    void getByLastName(String lname, ServiceRequest.Subscriber<Collection<PersonDto>>... subscribers);

    void getByFirstAndLastName(String fname, String lname, ServiceRequest.Subscriber<Collection<PersonDto>>... subscribers);
}
