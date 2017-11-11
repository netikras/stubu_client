package com.netikras.studies.studentbuddy.api.client.android.pieces.sys.data.impl;

import com.netikras.studies.studentbuddy.api.client.android.data.stores.ApiBasedDataStore;
import com.netikras.studies.studentbuddy.api.client.android.pieces.sys.data.PasswordReqDataStore;
import com.netikras.studies.studentbuddy.api.client.android.service.ServiceRequest;
import com.netikras.studies.studentbuddy.core.data.api.dto.meta.PasswordRequirementDto;

import java.util.Collection;

/**
 * Created by netikras on 17.11.11.
 */

public class PasswordReqDataStoreApiImpl extends ApiBasedDataStore<String, PasswordRequirementDto> implements PasswordReqDataStore {
    @Override
    public void getById(String id, ServiceRequest.Subscriber<PasswordRequirementDto>[] subscribers) {

    }

    @Override
    public void create(PasswordRequirementDto item, ServiceRequest.Subscriber<PasswordRequirementDto>[] subscribers) {

    }

    @Override
    public void update(PasswordRequirementDto item, ServiceRequest.Subscriber<PasswordRequirementDto>[] subscribers) {

    }

    @Override
    public void purge(String id, ServiceRequest.Subscriber<Boolean>[] subscribers) {

    }

    @Override
    public void delete(String id, ServiceRequest.Subscriber[] subscribers) {

    }

    @Override
    public void getAll(ServiceRequest.Subscriber<Collection<PasswordRequirementDto>>[] subscribers) {

    }
}
