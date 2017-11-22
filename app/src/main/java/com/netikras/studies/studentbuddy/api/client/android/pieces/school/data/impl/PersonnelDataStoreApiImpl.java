package com.netikras.studies.studentbuddy.api.client.android.pieces.school.data.impl;

import com.netikras.studies.studentbuddy.api.client.android.data.stores.ApiBasedDataStore;
import com.netikras.studies.studentbuddy.api.client.android.pieces.school.data.PersonnelDataStore;
import com.netikras.studies.studentbuddy.api.client.android.service.ServiceRequest;
import com.netikras.studies.studentbuddy.api.client.android.service.ServiceRequest.Subscriber;
import com.netikras.studies.studentbuddy.api.location.generated.SchoolApiConsumer;
import com.netikras.studies.studentbuddy.core.data.api.dto.school.PersonnelMemberDto;

import java.util.Collection;

import javax.inject.Inject;

/**
 * Created by netikras on 17.11.11.
 */

public class PersonnelDataStoreApiImpl extends ApiBasedDataStore<String, PersonnelMemberDto> implements PersonnelDataStore {
    
    @Inject
    SchoolApiConsumer schoolApiConsumer;

    @Inject
    public PersonnelDataStoreApiImpl() {}
    
    @Override
    public void getById(String id, Subscriber<PersonnelMemberDto>... subscribers) {
        orderData(new ServiceRequest<PersonnelMemberDto>() {
            @Override
            public PersonnelMemberDto request() {
                return schoolApiConsumer.retrievePersonnelMemberDto(id);
            }
        }, subscribers);
    }

    @Override
    public void create(PersonnelMemberDto item, Subscriber<PersonnelMemberDto>... subscribers) {
        orderData(new ServiceRequest<PersonnelMemberDto>() {
            @Override
            public PersonnelMemberDto request() {
                return schoolApiConsumer.createPersonnelMemberDto(item);
            }
        }, subscribers);
    }

    @Override
    public void update(PersonnelMemberDto item, Subscriber<PersonnelMemberDto>... subscribers) {
        orderData(new ServiceRequest<PersonnelMemberDto>() {
            @Override
            public PersonnelMemberDto request() {
                return schoolApiConsumer.updatePersonnelMemberDto(item);
            }
        }, subscribers);
    }

    @Override
    public void purge(String id, Subscriber<Boolean>... subscribers) {
        orderData(new ServiceRequest<Boolean>() {
            @Override
            public Boolean request() {
                schoolApiConsumer.purgePersonnelMemberDto(id);
                return Boolean.TRUE;
            }
        }, subscribers);
    }

    @Override
    public void delete(String id, Subscriber... subscribers) {
        orderData(new ServiceRequest<Boolean>() {
            @Override
            public Boolean request() {
                schoolApiConsumer.deletePersonnelMemberDto(id);
                return Boolean.TRUE;
            }
        }, subscribers);
    }

    @Override
    public void getAll(Subscriber<Collection<PersonnelMemberDto>>... subscribers) {
        notifyNotImplemented(subscribers);
    }
}
