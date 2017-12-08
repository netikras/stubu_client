package com.netikras.studies.studentbuddy.api.client.android.data.cache.db.dao;

import android.content.ContentValues;

import com.netikras.studies.studentbuddy.api.client.android.data.cache.CacheManager;
import com.netikras.studies.studentbuddy.core.data.api.dto.location.BuildingDto;
import com.netikras.studies.studentbuddy.core.data.api.dto.location.BuildingFloorDto;

import static com.netikras.tools.common.security.IntegrityUtils.isNullOrEmpty;

/**
 * Created by netikras on 17.12.8.
 */

public class FloorDao extends GenericDao<BuildingFloorDto> {

    public FloorDao(CacheManager cacheManager) {
        super(cacheManager, "floor");
    }

    @Override
    protected String getId(BuildingFloorDto entity) {
        return entity.getId();
    }

    @Override
    protected String getCreateQuery() {
        return "create table " + getTableName() + " (id text, title text, number text, building_id text)";
    }

    @Override
    protected void persist(BuildingFloorDto entity, ContentValues values) {
        values.put("id", entity.getId());
        values.put("title", entity.getTitle());
        values.put("number", entity.getNumber());

        if (entity.getBuilding() != null) {
            values.put("building_id", entity.getBuilding().getId());
        }
    }

    @Override
    protected BuildingFloorDto restore(DBResults results) {

        BuildingFloorDto floor = new BuildingFloorDto();

        floor.setId(results.getString("id"));
        floor.setTitle(results.getString("title"));
        floor.setNumber(results.getString("number"));

        String id = results.getString("building_id");
        if (!isNullOrEmpty(id)) {
            floor.setBuilding(new BuildingDto());
            floor.getBuilding().setId(id);
        }


        return floor;
    }
}
