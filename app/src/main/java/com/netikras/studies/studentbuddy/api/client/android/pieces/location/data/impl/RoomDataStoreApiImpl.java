package com.netikras.studies.studentbuddy.api.client.android.pieces.location.data.impl;

import com.netikras.studies.studentbuddy.api.client.android.data.cache.CacheManager;
import com.netikras.studies.studentbuddy.api.client.android.data.cache.db.dao.GenericDao;
import com.netikras.studies.studentbuddy.api.client.android.data.cache.db.dao.RoomDao;
import com.netikras.studies.studentbuddy.api.client.android.data.stores.ApiBasedDataStore;
import com.netikras.studies.studentbuddy.api.client.android.pieces.location.data.RoomDataStore;
import com.netikras.studies.studentbuddy.api.client.android.service.ServiceRequest;
import com.netikras.studies.studentbuddy.api.client.android.service.ServiceRequest.Subscriber;
import com.netikras.studies.studentbuddy.api.location.generated.FloorApiConsumer;
import com.netikras.studies.studentbuddy.core.data.api.dto.location.BuildingDto;
import com.netikras.studies.studentbuddy.core.data.api.dto.location.BuildingFloorDto;
import com.netikras.studies.studentbuddy.core.data.api.dto.location.BuildingSectionDto;
import com.netikras.studies.studentbuddy.core.data.api.dto.location.LectureRoomDto;

import java.util.Collection;

import javax.inject.Inject;

/**
 * Created by netikras on 17.11.11.
 */

public class RoomDataStoreApiImpl extends ApiBasedDataStore<String, LectureRoomDto> implements RoomDataStore {

    @Inject
    FloorApiConsumer floorApiConsumer;

    @Inject
    public RoomDataStoreApiImpl(CacheManager cacheManager) {
        setCache(cacheManager.getDao(RoomDao.class));


    }

    @Override
    protected RoomDao getCache() {
        return super.getCache();
    }


    @Override
    protected LectureRoomDto fillFromCache(LectureRoomDto item) {
        super.fillFromCache(item);
        if (item == null) {
            return item;
        }

        if (item.getSchool() != null) {

        }

        if (item.getFloor() != null) {
            BuildingFloorDto floor = item.getFloor();



            if (floor.getLayouts() == null) {

            }

            if (floor.getBuilding() == null) {


                BuildingDto building = floor.getBuilding();
                if (building.getAddress() == null) {

                }
            }

            if (floor.getBuildingSection() == null) {
                BuildingSectionDto section = floor.getBuildingSection();

                if (section.getAddress() == null) {

                }
                if (section.getBuilding() == null) {
                    
                }
            }
        }




        return item;
    }

    @Override
    public void getById(String id, Subscriber<LectureRoomDto>... subscribers) {

        if (isCacheEnabled()) {
            LectureRoomDto dto = getCached(id);
            if (dto != null) {
                fillFromCache(dto);
                respondCacheHit(dto);
            }
        }

        orderData(new ServiceRequest<LectureRoomDto>() {
            @Override
            public LectureRoomDto request() {
                return updateCache(floorApiConsumer.retrieveLectureRoomDto(id));
            }
        }, subscribers);
    }

    @Override
    public void create(LectureRoomDto item, Subscriber<LectureRoomDto>... subscribers) {
        orderData(new ServiceRequest<LectureRoomDto>() {
            @Override
            public LectureRoomDto request() {
                return updateCache(floorApiConsumer.createLectureRoomDto(item));
            }
        }, subscribers);
    }

    @Override
    public void update(LectureRoomDto item, Subscriber<LectureRoomDto>... subscribers) {
        orderData(new ServiceRequest<LectureRoomDto>() {
            @Override
            public LectureRoomDto request() {
                return updateCache(floorApiConsumer.updateLectureRoomDto(item));
            }
        }, subscribers);
    }

    @Override
    public void purge(String id, Subscriber<Boolean>... subscribers) {
        orderData(new ServiceRequest<Boolean>() {
            @Override
            public Boolean request() {
                floorApiConsumer.purgeLectureRoomDto(id);
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
                floorApiConsumer.deleteLectureRoomDto(id);
                evict(id);
                return Boolean.TRUE;
            }
        }, subscribers);
    }

    @Override
    public void getAll(Subscriber<Collection<LectureRoomDto>>... subscribers) {
        notifyNotImplemented(subscribers);
    }
}
