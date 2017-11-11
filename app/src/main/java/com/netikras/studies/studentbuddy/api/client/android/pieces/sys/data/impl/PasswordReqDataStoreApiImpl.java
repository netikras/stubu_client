package com.netikras.studies.studentbuddy.api.client.android.pieces.sys.data.impl;

import com.netikras.studies.studentbuddy.api.client.android.data.stores.ApiBasedDataStore;
import com.netikras.studies.studentbuddy.api.client.android.pieces.sys.data.PasswordReqDataStore;
import com.netikras.studies.studentbuddy.api.client.android.service.ServiceRequest;
import com.netikras.studies.studentbuddy.api.client.android.service.ServiceRequest.Subscriber;
import com.netikras.studies.studentbuddy.api.sys.generated.AdminApiConsumer;
import com.netikras.studies.studentbuddy.core.data.api.dto.meta.PasswordRequirementDto;

import java.util.Collection;

import javax.inject.Inject;

/**
 * Created by netikras on 17.11.11.
 */

public class PasswordReqDataStoreApiImpl extends ApiBasedDataStore<String, PasswordRequirementDto> implements PasswordReqDataStore {
    
    @Inject
    AdminApiConsumer adminApiConsumer;
    
    @Override
    public void getById(String id, Subscriber<PasswordRequirementDto>... subscribers) {
        orderData(new ServiceRequest<PasswordRequirementDto>() {
            @Override
            public PasswordRequirementDto request() {
                return adminApiConsumer.retrievePasswordRequirementDto(id);
            }
        }, subscribers);
    }

    @Override
    public void create(PasswordRequirementDto item, Subscriber<PasswordRequirementDto>... subscribers) {
        orderData(new ServiceRequest<PasswordRequirementDto>() {
            @Override
            public PasswordRequirementDto request() {
                return adminApiConsumer.createPasswordRequirementDto(item);
            }
        }, subscribers);
    }

    @Override
    public void update(PasswordRequirementDto item, Subscriber<PasswordRequirementDto>... subscribers) {
        orderData(new ServiceRequest<PasswordRequirementDto>() {
            @Override
            public PasswordRequirementDto request() {
                return adminApiConsumer.updatePasswordRequirementDto(item);
            }
        }, subscribers);
    }

    @Override
    public void purge(String id, Subscriber<Boolean>... subscribers) {
        delete(id, subscribers);
    }

    @Override
    public void delete(String id, Subscriber<Boolean>... subscribers) {
        orderData(new ServiceRequest<Boolean>() {
            @Override
            public Boolean request() {
                adminApiConsumer.deletePasswordRequirementDto(id);
                return Boolean.TRUE;
            }
        }, subscribers);
    }

    @Override
    public void getAll(Subscriber<Collection<PasswordRequirementDto>>... subscribers) {
        getAllStored(subscribers);
    }

    @Override
    public void refresh(Subscriber<Boolean>... subscribers) {
        orderData(new ServiceRequest<Boolean>() {
            @Override
            public Boolean request() {
                adminApiConsumer.refreshPasswordRequirementDtoLive();
                return Boolean.TRUE;
            }
        }, subscribers);
    }

    @Override
    public void getAllLive(Subscriber<Collection<PasswordRequirementDto>>... subscribers) {
        orderData(new ServiceRequest<Collection<PasswordRequirementDto>>() {
            @Override
            public Collection<PasswordRequirementDto> request() {
                return adminApiConsumer.getPasswordRequirementDtoAllLive();
            }
        }, subscribers);
    }

    @Override
    public void getAllStored(Subscriber<Collection<PasswordRequirementDto>>... subscribers) {
        orderData(new ServiceRequest<Collection<PasswordRequirementDto>>() {
            @Override
            public Collection<PasswordRequirementDto> request() {
                return adminApiConsumer.getPasswordRequirementDtoAllStored();
            }
        }, subscribers);
    }
}
