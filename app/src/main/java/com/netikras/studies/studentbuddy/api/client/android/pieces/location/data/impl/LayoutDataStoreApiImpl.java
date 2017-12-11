package com.netikras.studies.studentbuddy.api.client.android.pieces.location.data.impl;

import com.netikras.studies.studentbuddy.api.client.android.data.cache.CacheManager;
import com.netikras.studies.studentbuddy.api.client.android.data.cache.db.dao.GenericDao;
import com.netikras.studies.studentbuddy.api.client.android.data.cache.db.dao.LayoutDao;
import com.netikras.studies.studentbuddy.api.client.android.data.stores.ApiBasedDataStore;
import com.netikras.studies.studentbuddy.api.client.android.pieces.location.data.LayoutDataStore;
import com.netikras.studies.studentbuddy.api.client.android.service.ServiceRequest;
import com.netikras.studies.studentbuddy.api.client.android.service.ServiceRequest.Subscriber;
import com.netikras.studies.studentbuddy.api.location.generated.FloorApiConsumer;
import com.netikras.studies.studentbuddy.core.data.api.dto.location.FloorLayoutDto;

import java.util.Collection;

import javax.inject.Inject;

/**
 * Created by netikras on 17.11.11.
 */

public class LayoutDataStoreApiImpl extends ApiBasedDataStore<String, FloorLayoutDto> implements LayoutDataStore {

    @Inject
    FloorApiConsumer floorApiConsumer;

    @Inject
    public LayoutDataStoreApiImpl(CacheManager cacheManager) {
        setCache(cacheManager.getDao(LayoutDao.class));
    }

    @Override
    public void getById(String id, Subscriber<FloorLayoutDto>... subscribers) {

        if (isCacheEnabled()) {
            FloorLayoutDto dto = getCached(id);
            if (dto != null) {
                fillFromCache(dto);
                respondCacheHit(dto, subscribers);
            }
        }

        orderData(new ServiceRequest<FloorLayoutDto>() {
            @Override
            public FloorLayoutDto request() {
                return updateCache(floorApiConsumer.retrieveFloorLayoutDto(id));
            }
        }, subscribers);
    }

    @Override
    public void create(FloorLayoutDto item, Subscriber<FloorLayoutDto>... subscribers) {
        orderData(new ServiceRequest<FloorLayoutDto>() {
            @Override
            public FloorLayoutDto request() {
                return updateCache(floorApiConsumer.createFloorLayoutDto(item));
            }
        }, subscribers);
    }

    @Override
    public void update(FloorLayoutDto item, Subscriber<FloorLayoutDto>... subscribers) {
        orderData(new ServiceRequest<FloorLayoutDto>() {
            @Override
            public FloorLayoutDto request() {
                return updateCache(floorApiConsumer.updateFloorLayoutDto(item));
            }
        }, subscribers);
    }

    @Override
    public void purge(String id, Subscriber<Boolean>... subscribers) {
        orderData(new ServiceRequest<Boolean>(){
            @Override
            public Boolean request() {
                floorApiConsumer.purgeFloorLayoutDto(id);
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
                floorApiConsumer.deleteFloorLayoutDto(id);
                evict(id);
                return Boolean.TRUE;
            }
        }, subscribers);
    }

    @Override
    public void getAll(Subscriber<Collection<FloorLayoutDto>>... subscribers) {
        notifyNotImplemented(subscribers);
    }
}
