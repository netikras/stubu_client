package com.netikras.studies.studentbuddy.api.client.android.data.cache.db.dao;

import android.content.ContentValues;
import android.icu.util.BuddhistCalendar;

import com.netikras.studies.studentbuddy.api.client.android.data.cache.CacheManager;
import com.netikras.studies.studentbuddy.api.client.android.pieces.location.data.BuildingDataStore;
import com.netikras.studies.studentbuddy.core.data.api.dto.location.AddressDto;
import com.netikras.studies.studentbuddy.core.data.api.dto.location.BuildingDto;
import com.netikras.studies.studentbuddy.core.data.api.dto.location.BuildingFloorDto;
import com.netikras.studies.studentbuddy.core.data.api.dto.location.BuildingSectionDto;

import java.util.List;

import static com.netikras.tools.common.security.IntegrityUtils.isNullOrEmpty;

/**
 * Created by netikras on 17.12.8.
 */

public class SectionDao extends GenericDao<BuildingSectionDto> {

    AddressDao addressCache;
    BuildingDao buildingCache;
    FloorDao floorCache;

    public SectionDao(CacheManager cacheManager) {
        super(cacheManager, "section");

        addressCache = cacheManager.getDao(AddressDao.class);
        buildingCache = cacheManager.getDao(BuildingDao.class);
        floorCache = cacheManager.getDao(FloorDao.class);
    }

    @Override
    protected String getId(BuildingSectionDto entity) {
        return entity.getId();
    }

    @Override
    protected String getCreateQuery() {
        return "create table if not exists " + getTableName() + " (id text, title text, building_id text, address_id text)";
    }

    @Override
    protected void persist(BuildingSectionDto entity, ContentValues values) {
        values.put("id", entity.getId());
        values.put("title", entity.getTitle());

        if (entity.getBuilding() != null) {
            values.put("building_id", entity.getBuilding().getId());
        }
        if (entity.getAddress() != null) {
            values.put("address_id", entity.getAddress().getId());
        }
    }

    @Override
    protected BuildingSectionDto restore(DBResults results) {
        BuildingSectionDto section = new BuildingSectionDto();

        section.setId(results.getString("id"));
        section.setTitle(results.getString("title"));

        String id = results.getString("building_id");
        if (!isNullOrEmpty(id)) {
            section.setBuilding(new BuildingDto());
            section.getBuilding().setId(id);
        }

        id = results.getString("address_id");
        if (!isNullOrEmpty(id)) {
            section.setAddress(new AddressDto());
            section.getAddress().setId(id);
        }

        return section;
    }

    @Override
    public BuildingSectionDto fill(BuildingSectionDto entity) {
        if (entity == null) {
            return entity;
        }

        entity.setAddress(prefill(entity.getAddress(), addressCache));
        entity.setBuilding(prefill(entity.getBuilding(), buildingCache));

        return entity;
    }

    @Override
    public BuildingSectionDto putWithImmediates(BuildingSectionDto entity) {
        if (entity == null) {
            return entity;
        }

        super.putWithImmediates(entity);

        addressCache.put(entity.getAddress());
        buildingCache.put(entity.getBuilding());
        floorCache.putAll(entity.getFloors());

        return entity;
    }

    public List<BuildingSectionDto> getAllByBuildingId(String id) {
        return getAllWhere("building_id = ?", id);
    }
}
