package com.netikras.studies.studentbuddy.api.client.android.pieces.sys.data.impl;

import com.netikras.studies.studentbuddy.api.client.android.data.stores.ApiBasedDataStore;
import com.netikras.studies.studentbuddy.api.client.android.pieces.sys.data.RoleDataStore;
import com.netikras.studies.studentbuddy.api.client.android.service.ServiceRequest;
import com.netikras.studies.studentbuddy.api.client.android.service.ServiceRequest.Subscriber;
import com.netikras.studies.studentbuddy.api.sys.generated.AdminApiConsumer;
import com.netikras.studies.studentbuddy.core.data.api.dto.meta.RoleDto;

import java.util.Collection;

import javax.inject.Inject;

/**
 * Created by netikras on 17.11.11.
 */

public class RoleDataStoreApiImpl extends ApiBasedDataStore<String, RoleDto> implements RoleDataStore {

    @Inject
    AdminApiConsumer adminApiConsumer;

    @Inject
    public RoleDataStoreApiImpl() {}

    @Override
    public void getById(String id, Subscriber<RoleDto>... subscribers) {
        orderData(new ServiceRequest<RoleDto>() {
            @Override
            public RoleDto request() {
                return adminApiConsumer.retrieveRoleDto(id);
            }
        }, subscribers);
    }

    @Override
    public void create(RoleDto item, Subscriber<RoleDto>... subscribers) {
        create(item.getName(), subscribers);
    }

    @Override
    public void create(String name, Subscriber<RoleDto>... subscribers) {
        orderData(new ServiceRequest<RoleDto>() {
            @Override
            public RoleDto request() {
                return adminApiConsumer.createRoleDto(name);
            }
        }, subscribers);
    }

    @Override
    public void update(RoleDto item, Subscriber<RoleDto>... subscribers) {
        notifyNotImplemented(subscribers);
    }

    @Override
    public void purge(String id, Subscriber<Boolean>... subscribers) {
        delete(id, subscribers);
    }

    @Override
    public void delete(String id, Subscriber... subscribers) {
        orderData(new ServiceRequest<Boolean>() {
            @Override
            public Boolean request() {
                adminApiConsumer.deleteRoleDto(id);
                return Boolean.TRUE;
            }
        }, subscribers);
    }

    @Override
    public void getAll(Subscriber<Collection<RoleDto>>... subscribers) {
        orderData(new ServiceRequest<Collection<RoleDto>>() {
            @Override
            public Collection<RoleDto> request() {
                return adminApiConsumer.getRoleDtoAll();
            }
        }, subscribers);
    }
}
