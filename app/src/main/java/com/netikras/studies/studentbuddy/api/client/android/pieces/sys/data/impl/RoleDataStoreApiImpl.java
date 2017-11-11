package com.netikras.studies.studentbuddy.api.client.android.pieces.sys.data.impl;

import com.netikras.studies.studentbuddy.api.client.android.data.stores.ApiBasedDataStore;
import com.netikras.studies.studentbuddy.api.client.android.pieces.sys.data.RoleDataStore;
import com.netikras.studies.studentbuddy.api.client.android.service.ServiceRequest;
import com.netikras.studies.studentbuddy.core.data.api.dto.meta.RoleDto;

import java.util.Collection;

/**
 * Created by netikras on 17.11.11.
 */

public class RoleDataStoreApiImpl extends ApiBasedDataStore<String, RoleDto> implements RoleDataStore {
    @Override
    public void getById(String id, ServiceRequest.Subscriber<RoleDto>[] subscribers) {

    }

    @Override
    public void create(RoleDto item, ServiceRequest.Subscriber<RoleDto>[] subscribers) {

    }

    @Override
    public void update(RoleDto item, ServiceRequest.Subscriber<RoleDto>[] subscribers) {

    }

    @Override
    public void purge(String id, ServiceRequest.Subscriber<Boolean>[] subscribers) {

    }

    @Override
    public void delete(String id, ServiceRequest.Subscriber[] subscribers) {

    }

    @Override
    public void getAll(ServiceRequest.Subscriber<Collection<RoleDto>>[] subscribers) {

    }
}
