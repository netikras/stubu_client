package com.netikras.studies.studentbuddy.api.client.android.pieces.school.data.impl;

import com.netikras.studies.studentbuddy.api.client.android.data.stores.ApiBasedDataStore;
import com.netikras.studies.studentbuddy.api.client.android.pieces.school.data.PersonnelDataStore;
import com.netikras.studies.studentbuddy.api.client.android.service.ServiceRequest;
import com.netikras.studies.studentbuddy.core.data.api.dto.school.PersonnelMemberDto;

import java.util.Collection;

/**
 * Created by netikras on 17.11.11.
 */

public class PersonnelDataStoreApiImpl extends ApiBasedDataStore<String, PersonnelMemberDto> implements PersonnelDataStore {
    @Override
    public void getById(String id, ServiceRequest.Subscriber<PersonnelMemberDto>[] subscribers) {

    }

    @Override
    public void create(PersonnelMemberDto item, ServiceRequest.Subscriber<PersonnelMemberDto>[] subscribers) {

    }

    @Override
    public void update(PersonnelMemberDto item, ServiceRequest.Subscriber<PersonnelMemberDto>[] subscribers) {

    }

    @Override
    public void purge(String id, ServiceRequest.Subscriber<Boolean>[] subscribers) {

    }

    @Override
    public void delete(String id, ServiceRequest.Subscriber[] subscribers) {

    }

    @Override
    public void getAll(ServiceRequest.Subscriber<Collection<PersonnelMemberDto>>[] subscribers) {

    }
}
