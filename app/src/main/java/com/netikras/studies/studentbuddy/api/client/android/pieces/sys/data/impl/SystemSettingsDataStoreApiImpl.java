package com.netikras.studies.studentbuddy.api.client.android.pieces.sys.data.impl;

import com.netikras.studies.studentbuddy.api.client.android.data.stores.ApiBasedDataStore;
import com.netikras.studies.studentbuddy.api.client.android.pieces.sys.data.SystemSettingsDataStore;
import com.netikras.studies.studentbuddy.api.client.android.service.ServiceRequest;
import com.netikras.studies.studentbuddy.api.sys.generated.AdminApiConsumer;
import com.netikras.studies.studentbuddy.core.data.api.dto.meta.SystemSettingDto;

import java.util.Collection;

import javax.inject.Inject;

/**
 * Created by netikras on 17.11.11.
 */

public class SystemSettingsDataStoreApiImpl extends ApiBasedDataStore<String, SystemSettingDto> implements SystemSettingsDataStore {

    @Inject
    AdminApiConsumer adminApiConsumer;

    @Override
    public void getById(String id, ServiceRequest.Subscriber<SystemSettingDto>... subscribers) {
        orderData(new ServiceRequest<SystemSettingDto>() {
            @Override
            public SystemSettingDto request() {
                return adminApiConsumer.retrieveSystemSettingDto(id);
            }
        }, subscribers);
    }

    @Override
    public void getStoredByName(String name, ServiceRequest.Subscriber<SystemSettingDto>... subscribers) {
        orderData(new ServiceRequest<SystemSettingDto>() {
            @Override
            public SystemSettingDto request() {
                return adminApiConsumer.getSystemSettingDtoStored(name);
            }
        }, subscribers);
    }

    @Override
    public void getLiveByName(String name, ServiceRequest.Subscriber<SystemSettingDto>... subscribers) {
        orderData(new ServiceRequest<SystemSettingDto>() {
            @Override
            public SystemSettingDto request() {
                return adminApiConsumer.getSystemSettingDtoLive(name);
            }
        }, subscribers);
    }

    @Override
    public void create(SystemSettingDto item, ServiceRequest.Subscriber<SystemSettingDto>... subscribers) {
        orderData(new ServiceRequest<SystemSettingDto>() {
            @Override
            public SystemSettingDto request() {
                return adminApiConsumer.createSystemSettingDto(item);
            }
        }, subscribers);
    }

    @Override
    public void update(SystemSettingDto item, ServiceRequest.Subscriber<SystemSettingDto>... subscribers) {
        orderData(new ServiceRequest<SystemSettingDto>() {
            @Override
            public SystemSettingDto request() {
                return adminApiConsumer.updateSystemSettingDto(item);
            }
        }, subscribers);
    }

    @Override
    public void purge(String id, ServiceRequest.Subscriber<Boolean>... subscribers) {
        delete(id, subscribers);
    }

    @Override
    public void delete(String id, ServiceRequest.Subscriber... subscribers) {
        orderData(new ServiceRequest<Boolean>() {
            @Override
            public Boolean request() {
                adminApiConsumer.deleteSystemSettingDto(id);
                return Boolean.TRUE;
            }
        }, subscribers);
    }

    @Override
    public void deleteStoredByName(String name, ServiceRequest.Subscriber... subscribers) {
        orderData(new ServiceRequest<Boolean>() {
            @Override
            public Boolean request() {
                adminApiConsumer.deleteSystemSettingDtoStoredByName(name);
                return Boolean.TRUE;
            }
        }, subscribers);
    }


    @Override
    public void refresh(ServiceRequest.Subscriber... subscribers) {
        orderData(new ServiceRequest<Boolean>() {
            @Override
            public Boolean request() {
                adminApiConsumer.refreshSystemSettingDtoLive();
                return Boolean.TRUE;
            }
        }, subscribers);
    }




    @Override
    public void getAllLive(ServiceRequest.Subscriber<Collection<SystemSettingDto>>... subscribers) {
        orderData(new ServiceRequest<Collection<SystemSettingDto>>() {
            @Override
            public Collection<SystemSettingDto> request() {
                return adminApiConsumer.getSystemSettingDtoAllLive();
            }
        }, subscribers);
    }

    @Override
    public void getAllStored(ServiceRequest.Subscriber<Collection<SystemSettingDto>>... subscribers) {
        orderData(new ServiceRequest<Collection<SystemSettingDto>>() {
            @Override
            public Collection<SystemSettingDto> request() {
                return adminApiConsumer.getSystemSettingDtoAllStored();
            }
        }, subscribers);
    }

    @Override
    public void getAll(ServiceRequest.Subscriber<Collection<SystemSettingDto>>... subscribers) {
        getAllStored(subscribers);
    }
}
