package com.netikras.studies.studentbuddy.api.client.android.data.cache.db.dao;

import android.content.ContentValues;

import com.netikras.studies.studentbuddy.api.client.android.data.cache.CacheManager;
import com.netikras.studies.studentbuddy.core.data.api.dto.school.AssignmentDto;
import com.netikras.studies.studentbuddy.core.data.api.dto.school.DisciplineDto;

import static com.netikras.tools.common.security.IntegrityUtils.isNullOrEmpty;

/**
 * Created by netikras on 17.12.8.
 */

public class AssignmentDao extends GenericDao<AssignmentDto> {

    public AssignmentDao(CacheManager cacheManager, String tableName) {
        super(cacheManager, tableName);
    }

    @Override
    protected String getId(AssignmentDto entity) {
        return null;
    }

    @Override
    protected String getCreateQuery() {
        return "create table " + getTableName() + " (id text, description text, created_on integer, updated_on integer, due integer, discipline_id text)";
    }

    @Override
    protected void persist(AssignmentDto entity, ContentValues values) {
        values.put("id", entity.getId());
        values.put("description", entity.getDescription());
        values.put("created_on", toTimestamp(entity.getCreatedOn()));
        values.put("updated_on", toTimestamp(entity.getUpdatedOn()));
        values.put("due", toTimestamp(entity.getDue()));

        if (entity.getDiscipline() != null) {
            values.put("discipline_id", entity.getDiscipline().getId());
        }
    }

    @Override
    protected AssignmentDto restore(DBResults results) {

        AssignmentDto assignment = new AssignmentDto();

        assignment.setId(results.getString("id"));
        assignment.setDescription(results.getString("description"));
        assignment.setCreatedOn(fromTimestamp(results.getLong("created_on")));
        assignment.setUpdatedOn(fromTimestamp(results.getLong("updated_on")));
        assignment.setDue(fromTimestamp(results.getLong("due")));

        String id = results.getString("discipline_id");
        if (!isNullOrEmpty(id)) {
            assignment.setDiscipline(new DisciplineDto());
            assignment.getDiscipline().setId(id);
        }

        return assignment;
    }
}
