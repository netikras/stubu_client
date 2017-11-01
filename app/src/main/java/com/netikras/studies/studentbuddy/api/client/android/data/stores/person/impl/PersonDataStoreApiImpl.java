package com.netikras.studies.studentbuddy.api.client.android.data.stores.person.impl;

import com.netikras.studies.studentbuddy.api.client.android.data.stores.ApiBasedDataStore;
import com.netikras.studies.studentbuddy.api.client.android.data.stores.person.PersonDataStore;
import com.netikras.studies.studentbuddy.api.client.android.service.ServiceRequest;
import com.netikras.studies.studentbuddy.api.client.android.service.ServiceRequest.Subscriber;
import com.netikras.studies.studentbuddy.api.user.generated.PersonApiConsumer;
import com.netikras.studies.studentbuddy.api.user.mgmt.generated.AdminPersonApiConsumer;
import com.netikras.studies.studentbuddy.core.data.api.dto.PersonDto;

import java.util.Collection;

import javax.inject.Inject;

/**
 * Created by netikras on 17.10.31.
 */

public class PersonDataStoreApiImpl extends ApiBasedDataStore<String, PersonDto> implements PersonDataStore {

    @Inject
    PersonApiConsumer apiConsumer;
    @Inject
    AdminPersonApiConsumer adminApiConsumer;

    @Override
    public void getById(String id, Subscriber<PersonDto>... subscribers) {
        orderData(new ServiceRequest<PersonDto>(){
            @Override
            public PersonDto request() {
                return apiConsumer.retrievePersonDto(id);
            }
        }, subscribers);
    }

    @Override
    public void create(PersonDto item, Subscriber<PersonDto>... subscribers) {
        orderData(new ServiceRequest<PersonDto>(){
            @Override
            public PersonDto request() {
                return adminApiConsumer.createPersonDto(item);
            }
        }, subscribers);
    }

    @Override
    public void update(PersonDto item, Subscriber<PersonDto>... subscribers) {
        orderData(new ServiceRequest<PersonDto>(){
            @Override
            public PersonDto request() {
                return adminApiConsumer.updatePersonDto(item);
            }
        }, subscribers);
    }

    @Override
    public void purge(String id, Subscriber<Boolean>... subscribers) {
        orderData(new ServiceRequest<Boolean>(){
            @Override
            public Boolean request() {
                adminApiConsumer.deletePersonDto(id);
                return Boolean.TRUE;
            }
        }, subscribers);
    }

    @Override
    public void delete(String id, Subscriber... subscribers) {
        orderData(new ServiceRequest<Boolean>(){
            @Override
            public Boolean request() {
                adminApiConsumer.purgePersonDto(id);
                return Boolean.TRUE;
            }
        }, subscribers);
    }

    @Override
    public void getAll(Subscriber<Collection<PersonDto>>... subscribers) {
        orderData(new ServiceRequest<Collection<PersonDto>>(){
            @Override
            public Collection<PersonDto> request() {
                return apiConsumer.getPersonDtoAll();
            }
        }, subscribers);
    }

    @Override
    public void getByIdentifier(String identifier, Subscriber<PersonDto>... subscribers) {
        orderData(new ServiceRequest<PersonDto>(){
            @Override
            public PersonDto request() {
                return apiConsumer.getPersonDtoByIdentifier(identifier);
            }
        }, subscribers);
    }
}
