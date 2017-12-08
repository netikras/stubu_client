package com.netikras.studies.studentbuddy.api.client.android.data.cache.db.dao;

import android.content.ContentValues;

import com.netikras.studies.studentbuddy.api.client.android.data.cache.CacheManager;
import com.netikras.studies.studentbuddy.core.data.api.dto.location.AddressDto;
import com.netikras.studies.studentbuddy.core.data.api.dto.location.BuildingDto;
import com.netikras.studies.studentbuddy.core.data.api.dto.location.BuildingSectionDto;

import static com.netikras.tools.common.security.IntegrityUtils.isNullOrEmpty;

/**
 * Created by netikras on 17.12.8.
 */

public class SectionDao extends GenericDao<BuildingSectionDto> {

    public SectionDao(CacheManager cacheManager) {
        super(cacheManager, "section");
    }

    @Override
    protected String getId(BuildingSectionDto entity) {
        return entity.getId();
    }

    @Override
    protected String getCreateQuery() {
        return "create table " + getTableName() + " (id text, title text, building_id text, address_id text)";
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
}
