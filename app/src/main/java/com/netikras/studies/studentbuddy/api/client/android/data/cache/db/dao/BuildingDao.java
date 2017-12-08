package com.netikras.studies.studentbuddy.api.client.android.data.cache.db.dao;

import android.content.ContentValues;

import com.netikras.studies.studentbuddy.api.client.android.data.cache.CacheManager;
import com.netikras.studies.studentbuddy.core.data.api.dto.location.AddressDto;
import com.netikras.studies.studentbuddy.core.data.api.dto.location.BuildingDto;

import static com.netikras.tools.common.security.IntegrityUtils.isNullOrEmpty;

/**
 * Created by netikras on 17.12.8.
 */

public class BuildingDao extends GenericDao<BuildingDto> {

    public BuildingDao(CacheManager cacheManager) {
        super(cacheManager, "building");
    }

    @Override
    protected String getId(BuildingDto entity) {
        return entity.getId();
    }

    @Override
    protected String getCreateQuery() {
        return "create table " + getTableName() + " (id text, title text, updated_on integer, address_id text)";
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
}
