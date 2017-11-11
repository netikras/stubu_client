package com.netikras.studies.studentbuddy.api.client.android.pieces.sys.data.impl;

import com.netikras.studies.studentbuddy.api.client.android.data.stores.ApiBasedDataStore;
import com.netikras.studies.studentbuddy.api.client.android.pieces.sys.data.RolePermissionsDataStore;
import com.netikras.studies.studentbuddy.api.client.android.service.ServiceRequest;
import com.netikras.studies.studentbuddy.core.data.api.dto.meta.RolePermissionDto;

import java.util.Collection;

/**
 * Created by netikras on 17.11.11.
 */

public class RolePermissionsDataStoreApiImpl extends ApiBasedDataStore<String, RolePermissionDto> implements RolePermissionsDataStore {
    @Override
    public void getById(String id, ServiceRequest.Subscriber<RolePermissionDto>[] subscribers) {

    }

    @Override
    public void create(RolePermissionDto item, ServiceRequest.Subscriber<RolePermissionDto>[] subscribers) {

    }

    @Override
    public void update(RolePermissionDto item, ServiceRequest.Subscriber<RolePermissionDto>[] subscribers) {

    }

    @Override
    public void purge(String id, ServiceRequest.Subscriber<Boolean>[] subscribers) {

    }

    @Override
    public void delete(String id, ServiceRequest.Subscriber[] subscribers) {

    }

    @Override
    public void getAll(ServiceRequest.Subscriber<Collection<RolePermissionDto>>[] subscribers) {

    }
}
