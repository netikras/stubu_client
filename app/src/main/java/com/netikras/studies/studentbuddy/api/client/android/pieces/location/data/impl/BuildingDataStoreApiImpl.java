package com.netikras.studies.studentbuddy.api.client.android.pieces.location.data.impl;

import com.netikras.studies.studentbuddy.api.client.android.data.cache.CacheManager;
import com.netikras.studies.studentbuddy.api.client.android.data.cache.db.dao.BuildingDao;
import com.netikras.studies.studentbuddy.api.client.android.data.cache.db.dao.GenericDao;
import com.netikras.studies.studentbuddy.api.client.android.data.stores.ApiBasedDataStore;
import com.netikras.studies.studentbuddy.api.client.android.pieces.location.data.BuildingDataStore;
import com.netikras.studies.studentbuddy.api.client.android.service.ServiceRequest;
import com.netikras.studies.studentbuddy.api.client.android.service.ServiceRequest.Subscriber;
import com.netikras.studies.studentbuddy.api.location.generated.LocationApiConsumer;
import com.netikras.studies.studentbuddy.core.data.api.dto.location.BuildingDto;

import java.util.Collection;

import javax.inject.Inject;

/**
 * Created by netikras on 17.11.11.
 */

public class BuildingDataStoreApiImpl extends ApiBasedDataStore<String, BuildingDto> implements BuildingDataStore {

    @Inject
    LocationApiConsumer locationApiConsumer;

    @Inject
    public BuildingDataStoreApiImpl(CacheManager cacheManager) {
        setCache(cacheManager.getDao(BuildingDao.class));
    }

    @Override
    public void getById(String id, Subscriber<BuildingDto>... subscribers) {
        if (isCacheEnabled()) {
            BuildingDto dto = getCached(id);
            if (dto != null) {
                fillFromCache(dto);
                respondCacheHit(dto, subscribers);
            }
        }
        orderData(new ServiceRequest<BuildingDto>(){
            @Override
            public BuildingDto request() {
                return updateCache(locationApiConsumer.retrieveBuildingDto(id));
            }
        }, subscribers);
    }

    @Override
    public void create(BuildingDto item, Subscriber<BuildingDto>... subscribers) {
        orderData(new ServiceRequest<BuildingDto>(){
            @Override
            public BuildingDto request() {
                return updateCache(locationApiConsumer.createBuildingDto(item));
            }
        }, subscribers);
    }

    @Override
    public void update(BuildingDto item, Subscriber<BuildingDto>... subscribers) {
        orderData(new ServiceRequest<BuildingDto>(){
            @Override
            public BuildingDto request() {
                return updateCache(locationApiConsumer.updateBuildingDto(item));
            }
        }, subscribers);
    }

    @Override
    public void purge(String id, Subscriber<Boolean>... subscribers) {
        orderData(new ServiceRequest<Boolean>(){
            @Override
            public Boolean request() {
                locationApiConsumer.purgeBuildingDto(id);
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
                locationApiConsumer.deleteBuildingDto(id);
                evict(id);
                return Boolean.TRUE;
            }
        }, subscribers);
    }

    @Override
    public void getAll(Subscriber<Collection<BuildingDto>>... subscribers) {
        notifyNotImplemented(subscribers);
    }
}
