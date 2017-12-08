package com.netikras.studies.studentbuddy.api.client.android.data.cache.db.dao;

import android.content.ContentValues;

import com.netikras.studies.studentbuddy.api.client.android.data.cache.CacheManager;
import com.netikras.studies.studentbuddy.core.data.api.dto.school.DisciplineDto;
import com.netikras.studies.studentbuddy.core.data.api.dto.school.SchoolDto;

import static com.netikras.tools.common.security.IntegrityUtils.isNullOrEmpty;

/**
 * Created by netikras on 17.12.8.
 */

public class DisciplineDao extends GenericDao<DisciplineDto> {

    public DisciplineDao(CacheManager cacheManager) {
        super(cacheManager, "discipline");
    }

    @Override
    protected String getId(DisciplineDto entity) {
        return entity.getId();
    }

    @Override
    protected String getCreateQuery() {
        return "create table " + getTableName() + " (id text, title text, description text, updated_on integer, school_id text)";
    }

    @Override
    protected void persist(DisciplineDto entity, ContentValues values) {
        values.put("id", entity.getId());
        values.put("title", entity.getTitle());
        values.put("description", entity.getDescription());
        values.put("updated_on", toTimestamp(entity.getUpdatedOn()));

        if (entity.getSchool() != null) {
            values.put("school_id", entity.getSchool().getId());
        }
    }

    @Override
    protected DisciplineDto restore(DBResults results) {

        DisciplineDto discipline = new DisciplineDto();

        discipline.setId(results.getString("id"));
        discipline.setTitle(results.getString("title"));
        discipline.setUpdatedOn(fromTimestamp(results.getLong("updated_on")));
        discipline.setDescription(results.getString("description"));

        String id = results.getString("school_id");
        if (!isNullOrEmpty(id)) {
            discipline.setSchool(new SchoolDto());
            discipline.getSchool().setId(id);
        }

        return discipline;
    }
}
