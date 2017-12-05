package com.netikras.studies.studentbuddy.api.client.android.pieces.sys.data.impl;

import com.netikras.studies.studentbuddy.api.client.android.data.stores.ApiBasedDataStore;
import com.netikras.studies.studentbuddy.api.client.android.pieces.sys.data.RolePermissionsDataStore;
import com.netikras.studies.studentbuddy.api.client.android.service.ServiceRequest;
import com.netikras.studies.studentbuddy.api.client.android.service.ServiceRequest.Subscriber;
import com.netikras.studies.studentbuddy.api.sys.generated.AdminApiConsumer;
import com.netikras.studies.studentbuddy.core.data.api.dto.meta.RoleDto;
import com.netikras.studies.studentbuddy.core.data.api.dto.meta.RolePermissionDto;

import java.util.Collection;

import javax.inject.Inject;

/**
 * Created by netikras on 17.11.11.
 */

public class RolePermissionsDataStoreApiImpl extends ApiBasedDataStore<String, RolePermissionDto> implements RolePermissionsDataStore {

    @Inject
    AdminApiConsumer adminApiConsumer;

    @Inject
    public RolePermissionsDataStoreApiImpl() {}

    @Override
    @Deprecated
    public void getById(String id, Subscriber<RolePermissionDto>... subscribers) {
        notifyNotImplemented(subscribers);
    }


    @Override
    public void create(RolePermissionDto item, Subscriber<RolePermissionDto>... subscribers) {
        create(item, null, subscribers);
    }

    @Override
    public void create(RolePermissionDto item, String resourceId, Subscriber<RolePermissionDto>... subscribers) {
        create(item.getRole(), item.getResource(), item.getAction(), resourceId, item.isStrict(), subscribers);
    }

    @Override
    public void create(String roleName, String resourceName, String actionName, String resourceId, Boolean strict, Subscriber<RolePermissionDto>... subscribers) {
        orderData(new ServiceRequest<RolePermissionDto>() {
            @Override
            public RolePermissionDto request() {
                return adminApiConsumer.createRolePermissionDto(roleName, resourceName, actionName, resourceId, strict);
            }
        }, subscribers);
    }

    @Override
    @Deprecated
    public void update(RolePermissionDto item, Subscriber<RolePermissionDto>... subscribers) {
        notifyNotImplemented(subscribers);
    }

    @Override
    public void purge(String id, Subscriber<Boolean>... subscribers) {
        delete(id, subscribers);
    }

    @Override
    @Deprecated
    public void delete(String id, Subscriber<Boolean>... subscribers) {
        notifyNotImplemented(subscribers);
    }

    @Override
    public void deleteById(String rolename, String id, Subscriber<Boolean>... subscribers) {
        orderData(new ServiceRequest<Boolean>() {
            @Override
            public Boolean request() {
                adminApiConsumer.deleteRolePermissionDtoById(rolename, id);
                return Boolean.TRUE;
            }
        }, subscribers);
    }

    @Override
    public void delete(String roleName, String resourceName, String actionName, String resourceId, Boolean strict, Subscriber<Boolean>... subscribers) {
        orderData(new ServiceRequest<Boolean>() {
            @Override
            public Boolean request() {
                adminApiConsumer.deleteRolePermissionDto(roleName, resourceName, actionName, resourceId, strict);
                return Boolean.TRUE;
            }
        }, subscribers);
    }

    @Override
    @Deprecated
    public void getAll(Subscriber<Collection<RolePermissionDto>>... subscribers) {
        notifyNotImplemented(subscribers);
    }

    @Override
    public void refresh(Subscriber<Boolean>... subscribers) {
        orderData(new ServiceRequest<Boolean>() {
            @Override
            public Boolean request() {
                adminApiConsumer.refreshVoidLiveRolePermissions();
                return Boolean.TRUE;
            }
        }, subscribers);
    }
}
