package com.netikras.studies.studentbuddy.api.client.android.data.cache.db.dao;

import android.content.ContentValues;

import com.netikras.studies.studentbuddy.api.client.android.data.cache.CacheManager;
import com.netikras.studies.studentbuddy.core.data.api.dto.school.CourseDto;
import com.netikras.studies.studentbuddy.core.data.api.dto.school.DisciplineDto;
import com.netikras.studies.studentbuddy.core.data.api.dto.school.StudentsGroupDto;

import static com.netikras.tools.common.security.IntegrityUtils.isNullOrEmpty;

/**
 * Created by netikras on 17.12.8.
 */

public class CourseDao extends GenericDao<CourseDto> {

    public CourseDao(CacheManager cacheManager) {
        super(cacheManager, "course");
    }

    @Override
    protected String getId(CourseDto entity) {
        return entity.getId();
    }

    @Override
    protected String getCreateQuery() {
        return "create table " + getTableName() + " (id text, title text, created_on integer, updated_on integer, discipline_id text, group_id text)";
    }

    @Override
    protected void persist(CourseDto entity, ContentValues values) {
        values.put("id", entity.getId());
        values.put("title", entity.getTitle());
        values.put("created_on", toTimestamp(entity.getCreatedOn()));
        values.put("updated_on", toTimestamp(entity.getUpdatedOn()));

        if (entity.getDiscipline() != null) {
            values.put("discipline_id", entity.getDiscipline().getId());
        }

        if (entity.getGroup() != null) {
            values.put("group_id", entity.getGroup().getId());
        }
    }

    @Override
    protected CourseDto restore(DBResults results) {

        CourseDto course = new CourseDto();

        course.setId(results.getString("id"));
        course.setTitle(results.getString("title"));
        course.setCreatedOn(fromTimestamp(results.getLong("created_on")));
        course.setUpdatedOn(fromTimestamp(results.getLong("updated_on")));

        String id = results.getString("discipline_id");
        if (!isNullOrEmpty(id)) {
            course.setDiscipline(new DisciplineDto());
            course.getDiscipline().setId(id);
        }

        id = results.getString("group_id");
        if (!isNullOrEmpty(id)) {
            course.setGroup(new StudentsGroupDto());
            course.getGroup().setId(id);
        }


        return course;
    }
}
