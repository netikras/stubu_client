package com.netikras.studies.studentbuddy.api.client.android.pieces.location.data.cahe;

import android.content.ContentValues;

import com.netikras.studies.studentbuddy.api.client.android.data.cache.CacheManager;
import com.netikras.studies.studentbuddy.api.client.android.data.cache.GenericDao;
import com.netikras.studies.studentbuddy.core.data.api.dto.location.AddressDto;
import com.netikras.studies.studentbuddy.core.data.api.dto.location.BuildingDto;
import com.netikras.studies.studentbuddy.core.data.api.dto.location.BuildingFloorDto;
import com.netikras.studies.studentbuddy.core.data.api.dto.location.BuildingSectionDto;

import java.util.List;

import static com.netikras.tools.common.security.IntegrityUtils.isNullOrEmpty;

/**
 * Created by netikras on 17.12.8.
 */

public class BuildingDao extends GenericDao<BuildingDto> {


    AddressDao addressCache;
    SectionDao sectionCache;
    FloorDao floorCache;

    public BuildingDao(CacheManager cacheManager) {
        super(cacheManager, "building");

        addressCache = cacheManager.getDao(AddressDao.class);
        sectionCache = cacheManager.getDao(SectionDao.class);
        floorCache = cacheManager.getDao(FloorDao.class);
    }

    @Override
    public String getId(BuildingDto entity) {
        return entity.getId();
    }

    @Override
    protected String getCreateQuery() {
        return "create table if not exists " + getTableName() + " (id text, title text, updated_on integer, address_id text)";
    }

    @Override
    protected void persist(BuildingDto entity, ContentValues values) {
        values.put("id", entity.getId());
        values.put("title", entity.getTitle());
        values.put("updated_on", toTimestamp(entity.getUpdatedOn()));
        if (entity.getAddress() != null) {
            values.put("address_id", entity.getAddress().getId());
        }
    }

    @Override
    protected BuildingDto restore(DBResults results) {
        BuildingDto building = new BuildingDto();

        building.setId(results.getString("id"));
        building.setTitle(results.getString("title"));
        building.setUpdatedOn(fromTimestamp(results.getLong("updated_on")));

        String id = results.getString("address_id");
        if (!isNullOrEmpty(id)) {
            building.setAddress(new AddressDto());
            building.getAddress().setId(id);
        }

        return building;
    }

    @Override
    public BuildingDto fill(BuildingDto entity) {

        if (entity == null) {
            return entity;
        }

        entity.setAddress(prefill(entity.getAddress(), addressCache));

        if (!isNullOrEmpty(entity.getId())) {
            List<BuildingFloorDto> floors = floorCache.getAllByBuildingId(entity.getId());
            entity.setFloors(floors);

            List<BuildingSectionDto> sections = sectionCache.getAllByBuildingId(entity.getId());
            entity.setBuildingSections(sections);
        }

        return entity;
    }

    @Override
    public BuildingDto putWithImmediates(BuildingDto entity) {
        if (entity == null) {
            return entity;
        }

        super.putWithImmediates(entity);

        addressCache.put(entity.getAddress());
        floorCache.putAll(entity.getFloors());
        sectionCache.putAll(entity.getBuildingSections());
        return entity;
    }

}
