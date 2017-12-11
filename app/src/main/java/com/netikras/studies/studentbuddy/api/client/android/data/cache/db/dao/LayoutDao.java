package com.netikras.studies.studentbuddy.api.client.android.data.cache.db.dao;

import android.content.ContentValues;

import com.netikras.studies.studentbuddy.api.client.android.data.cache.CacheManager;
import com.netikras.studies.studentbuddy.core.data.api.dto.location.BuildingFloorDto;
import com.netikras.studies.studentbuddy.core.data.api.dto.location.FloorLayoutDto;

import java.util.List;

import static com.netikras.tools.common.security.IntegrityUtils.isNullOrEmpty;

/**
 * Created by netikras on 17.12.8.
 */

public class LayoutDao extends GenericDao<FloorLayoutDto> {

    FloorDao floorCache;

    public LayoutDao(CacheManager cacheManager) {
        super(cacheManager, "floor_layout");

        floorCache = cacheManager.getDao(FloorDao.class);
    }

    @Override
    public String getId(FloorLayoutDto entity) {
        return entity.getId();
    }

    @Override
    protected String getCreateQuery() {
        return "create table if not exists " + getTableName() + " (id text, active integer, updated_on integer, map blob, floor_id text)";
    }

    @Override
    protected void persist(FloorLayoutDto entity, ContentValues values) {
        values.put("id", entity.getId());
        values.put("active", entity.isActive() ? 1 : 0);
        values.put("updated_on", toTimestamp(entity.getUpdatedOn()));
        values.put("map", entity.getFloorMap());
        if (entity.getFloor() != null) {
            values.put("floor_id", entity.getFloor().getId());
        }
    }

    @Override
    protected FloorLayoutDto restore(DBResults results) {
        FloorLayoutDto layoutDto = new FloorLayoutDto();

        layoutDto.setId(results.getString("id"));
        layoutDto.setActive(results.getInt("active") == 1);
        layoutDto.setUpdatedOn(fromTimestamp(results.getLong("updated_on")));
        layoutDto.setFloorMap(results.getBlob("map"));

        String id = results.getString("floor_id");
        if (!isNullOrEmpty(id)) {
            layoutDto.setFloor(new BuildingFloorDto());
            layoutDto.getFloor().setId(id);
        }

        return layoutDto;
    }

    @Override
    public FloorLayoutDto fill(FloorLayoutDto entity) {
        if (entity == null) {
            return entity;
        }

        entity.setFloor(prefill(entity.getFloor(), floorCache));

        return entity;
    }

    @Override
    public FloorLayoutDto putWithImmediates(FloorLayoutDto entity) {
        if (entity == null) {
            return entity;
        }

        super.putWithImmediates(entity);

        floorCache.put(entity.getFloor());

        return entity;
    }

    public List<FloorLayoutDto> getAllByFloorId(String id) {
        return getAllWhere("floor_id = ?", id);
    }
}
