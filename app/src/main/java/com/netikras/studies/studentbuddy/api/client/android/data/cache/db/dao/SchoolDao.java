package com.netikras.studies.studentbuddy.api.client.android.data.cache.db.dao;

import android.content.ContentValues;

import com.netikras.studies.studentbuddy.api.client.android.data.cache.CacheManager;
import com.netikras.studies.studentbuddy.core.data.api.dto.school.SchoolDepartmentDto;
import com.netikras.studies.studentbuddy.core.data.api.dto.school.SchoolDto;

import java.util.ArrayList;
import java.util.List;

import static com.netikras.tools.common.security.IntegrityUtils.isNullOrEmpty;

/**
 * Created by netikras on 17.12.8.
 */

public class SchoolDao extends GenericDao<SchoolDto> {

    public SchoolDao(CacheManager cacheManager) {
        super(cacheManager, "school");
    }

    @Override
    protected String getId(SchoolDto entity) {
        return entity.getId();
    }

    @Override
    protected String getCreateQuery() {
        return "create table if not exists " + getTableName() + " (id text, title text, department_ids text)";
    }

    @Override
    protected void persist(SchoolDto entity, ContentValues values) {
        values.put("id", entity.getId());
        values.put("title", entity.getTitle());

        if (!isNullOrEmpty(entity.getDepartments())) {
            List<String> depIds = new ArrayList<>();
            for (SchoolDepartmentDto dto : entity.getDepartments()) {
                depIds.add(dto.getId());
            }
            values.put("department_ids", joinCollection(depIds));
        }
    }

    @Override
    protected SchoolDto restore(DBResults results) {
        SchoolDto school = new SchoolDto();

        school.setId(results.getString("id"));
        school.setTitle(results.getString("title"));

        List<String> depIds = toCollection(results.getString("department_ids"));
        if (!isNullOrEmpty(depIds)) {
            school.setDepartments(new ArrayList<>());
            for (String depId : depIds) {
                SchoolDepartmentDto department = new SchoolDepartmentDto();
                department.setId(depId);
                school.getDepartments().add(department);
            }
        }

        return school;
    }

    @Override
    public SchoolDto fill(SchoolDto entity) {
        if (entity == null) {
            return entity;
        }

        return entity;
    }

    @Override
    public SchoolDto putWithImmediates(SchoolDto entity) {
        if (entity == null) {
            return entity;
        }

        super.putWithImmediates(entity);

        return entity;
    }
}
