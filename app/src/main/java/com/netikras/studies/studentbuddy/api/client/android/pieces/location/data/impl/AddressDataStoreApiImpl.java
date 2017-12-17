package com.netikras.studies.studentbuddy.api.client.android.pieces.location.data.impl;

import com.netikras.studies.studentbuddy.api.client.android.data.cache.CacheManager;
import com.netikras.studies.studentbuddy.api.client.android.pieces.location.data.cahe.AddressDao;
import com.netikras.studies.studentbuddy.api.client.android.data.stores.ApiBasedDataStore;
import com.netikras.studies.studentbuddy.api.client.android.pieces.location.data.AddressDataStore;
import com.netikras.studies.studentbuddy.api.client.android.service.ServiceRequest;
import com.netikras.studies.studentbuddy.api.client.android.service.ServiceRequest.Subscriber;
import com.netikras.studies.studentbuddy.api.location.generated.LocationApiConsumer;
import com.netikras.studies.studentbuddy.core.data.api.dto.location.AddressDto;

import java.util.Collection;

import javax.inject.Inject;

/**
 * Created by netikras on 17.11.11.
 */

public class AddressDataStoreApiImpl extends ApiBasedDataStore<String, AddressDto> implements AddressDataStore {

    @Inject
    LocationApiConsumer locationApiConsumer;

    @Inject
    public AddressDataStoreApiImpl(CacheManager cacheManager) {
        setCache(cacheManager.getDao(AddressDao.class));
    }

    @Override
    public void getById(String id, Subscriber<AddressDto>... subscribers) {
        if (isCacheEnabled()) {
            AddressDto dto = getCached(id);
            if (dto != null) {
                fillFromCache(dto);
                respondCacheHit(dto, subscribers);
            }
        }
        orderData(new ServiceRequest<AddressDto>(){
            @Override
            public AddressDto request() {
                return updateCache(locationApiConsumer.retrieveAddressDto(id));
            }
        }, subscribers);
    }

    @Override
    public void create(AddressDto item, Subscriber<AddressDto>... subscribers) {
        orderData(new ServiceRequest<AddressDto>(){
            @Override
            public AddressDto request() {
                return updateCache(locationApiConsumer.createAddressDto(item));
            }
        }, subscribers);
    }

    @Override
    public void update(AddressDto item, Subscriber<AddressDto>... subscribers) {
        orderData(new ServiceRequest<AddressDto>(){
            @Override
            public AddressDto request() {
                return updateCache(locationApiConsumer.updateAddressDto(item));
            }
        }, subscribers);
    }

    @Override
    public void purge(String id, Subscriber<Boolean>... subscribers) {
        orderData(new ServiceRequest<Boolean>(){
            @Override
            public Boolean request() {
                locationApiConsumer.purgeAddressDto(id);
                evict(id);
                return Boolean.TRUE;
            }
        }, subscribers);
    }

    @Override
    public void delete(String id, Subscriber... subscribers) {
        orderData(new ServiceRequest<Boolean>(){
            @Override
            public Boolean request() {
                locationApiConsumer.deleteAddressDto(id);
                evict(id);
                return Boolean.TRUE;
            }
        }, subscribers);
    }

    @Override
    public void getAll(Subscriber<Collection<AddressDto>>... subscribers) {
        notifyNotImplemented(subscribers);
    }
}
