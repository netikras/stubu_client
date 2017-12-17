package com.netikras.studies.studentbuddy.api.client.android.pieces.location.data.impl;

import com.netikras.studies.studentbuddy.api.client.android.data.cache.CacheManager;
import com.netikras.studies.studentbuddy.api.client.android.pieces.location.data.cahe.AddressDao;
import com.netikras.studies.studentbuddy.api.client.android.pieces.location.data.cahe.BuildingDao;
import com.netikras.studies.studentbuddy.api.client.android.pieces.location.data.cahe.FloorDao;
import com.netikras.studies.studentbuddy.api.client.android.pieces.location.data.cahe.LayoutDao;
import com.netikras.studies.studentbuddy.api.client.android.pieces.location.data.cahe.RoomDao;
import com.netikras.studies.studentbuddy.api.client.android.pieces.school.data.cahe.SchoolDao;
import com.netikras.studies.studentbuddy.api.client.android.pieces.location.data.cahe.SectionDao;
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


    SchoolDao schoolCache;
    FloorDao floorCache;
    LayoutDao layoutCache;
    BuildingDao buildingCache;
    SectionDao sectionCache;
    AddressDao addressCache;

    @Inject
    public RoomDataStoreApiImpl(CacheManager cacheManager) {
        setCache(cacheManager.getDao(RoomDao.class));
        schoolCache = cacheManager.getDao(SchoolDao.class);
        floorCache = cacheManager.getDao(FloorDao.class);
        layoutCache = cacheManager.getDao(LayoutDao.class);
        buildingCache = cacheManager.getDao(BuildingDao.class);
        sectionCache = cacheManager.getDao(SectionDao.class);
        addressCache = cacheManager.getDao(AddressDao.class);
    }


    @Override
    protected LectureRoomDto fillFromCache(LectureRoomDto item) {
        super.fillFromCache(item);
        if (item == null) {
            return item;
        }

        BuildingFloorDto floor = item.getFloor();

        if (item.getSchool() != null) {
            item.setSchool(schoolCache.fill(schoolCache.get(item.getSchool().getId())));
        }

        if (floor != null) {
            item.setFloor(floorCache.fill(floorCache.get(floor.getId())));
        }

        floor = item.getFloor();

        if (floor != null) {
            BuildingDto building = floor.getBuilding();
            BuildingSectionDto section = floor.getBuildingSection();

            if (floor.getLayouts() != null) {
                floor.setLayouts(layoutCache.fillAll(layoutCache.getAllByIds(floor.getLayouts())));
            }

            if (building != null) {
                floor.setBuilding(buildingCache.fill(buildingCache.get(building.getId())));
            }
            if (section != null) {
                floor.setBuildingSection(sectionCache.fill(sectionCache.get(section.getId())));
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
