package com.netikras.studies.studentbuddy.api.client.android.pieces.location.data.impl;

import com.netikras.studies.studentbuddy.api.client.android.data.cache.CacheManager;
import com.netikras.studies.studentbuddy.api.client.android.data.cache.db.dao.FloorDao;
import com.netikras.studies.studentbuddy.api.client.android.data.stores.ApiBasedDataStore;
import com.netikras.studies.studentbuddy.api.client.android.pieces.location.data.FloorDataStore;
import com.netikras.studies.studentbuddy.api.client.android.service.ServiceRequest;
import com.netikras.studies.studentbuddy.api.client.android.service.ServiceRequest.Subscriber;
import com.netikras.studies.studentbuddy.api.location.generated.FloorApiConsumer;
import com.netikras.studies.studentbuddy.api.location.generated.LocationApiConsumer;
import com.netikras.studies.studentbuddy.core.data.api.dto.location.BuildingFloorDto;

import java.util.Collection;

import javax.inject.Inject;

/**
 * Created by netikras on 17.11.11.
 */

public class FloorDataStoreApiImpl extends ApiBasedDataStore<String, BuildingFloorDto> implements FloorDataStore {

    @Inject
    FloorApiConsumer floorApiConsumer;
    @Inject
    LocationApiConsumer locationApiConsumer;

    @Inject
    public FloorDataStoreApiImpl(CacheManager cacheManager) {
        setCache(cacheManager.getDao(FloorDao.class));
    }

    @Override
    public void getById(String id, Subscriber<BuildingFloorDto>... subscribers) {
        if (isCacheEnabled()) {
            BuildingFloorDto dto = getCached(id);
            if (dto != null) {
                fillFromCache(dto);
                respondCacheHit(dto, subscribers);
            }
        }

        orderData(new ServiceRequest<BuildingFloorDto>() {
            @Override
            public BuildingFloorDto request() {
                return updateCache(floorApiConsumer.retrieveBuildingFloorDto(id));
            }
        }, subscribers);
    }

    @Override
    public void create(BuildingFloorDto item, Subscriber<BuildingFloorDto>... subscribers) {
        orderData(new ServiceRequest<BuildingFloorDto>() {
            @Override
            public BuildingFloorDto request() {
                return updateCache(floorApiConsumer.createBuildingFloorDto(item));
            }
        }, subscribers);
    }

    @Override
    public void update(BuildingFloorDto item, Subscriber<BuildingFloorDto>... subscribers) {
        orderData(new ServiceRequest<BuildingFloorDto>() {
            @Override
            public BuildingFloorDto request() {
                return updateCache(floorApiConsumer.updateBuildingFloorDto(item));
            }
        }, subscribers);
    }

    @Override
    public void purge(String id, Subscriber<Boolean>... subscribers) {
        orderData(new ServiceRequest<Boolean>() {
            @Override
            public Boolean request() {
                floorApiConsumer.purgeBuildingFloorDto(id);
                evict(id);
                return Boolean.TRUE;
            }
        }, subscribers);
    }

    @Override
    public void delete(String id, Subscriber... subscribers) {
        orderData(new ServiceRequest<Boolean>() {
            @Override
            public Boolean request() {
                floorApiConsumer.deleteBuildingFloorDto(id);
                evict(id);
                return Boolean.TRUE;
            }
        }, subscribers);
    }

    @Override
    public void getAll(Subscriber<Collection<BuildingFloorDto>>... subscribers) {
        notifyNotImplemented(subscribers);
    }
}
