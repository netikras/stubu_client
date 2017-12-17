package com.netikras.studies.studentbuddy.api.client.android.pieces.student.data.cahe;

import android.content.ContentValues;

import com.netikras.studies.studentbuddy.api.client.android.data.cache.CacheManager;
import com.netikras.studies.studentbuddy.api.client.android.data.cache.GenericDao;
import com.netikras.studies.studentbuddy.api.client.android.pieces.school.data.cahe.SchoolDao;
import com.netikras.studies.studentbuddy.core.data.api.dto.school.SchoolDto;
import com.netikras.studies.studentbuddy.core.data.api.dto.school.StudentDto;
import com.netikras.studies.studentbuddy.core.data.api.dto.school.StudentsGroupDto;

import java.util.List;

import static com.netikras.tools.common.security.IntegrityUtils.isNullOrEmpty;

/**
 * Created by netikras on 17.12.8.
 */

public class GroupDao extends GenericDao<StudentsGroupDto> {

    SchoolDao schoolCache;
    StudentDao studentCache;

    public GroupDao(CacheManager cacheManager) {
        super(cacheManager, "student_group");

        schoolCache = cacheManager.getDao(SchoolDao.class);
        studentCache = cacheManager.getDao(StudentDao.class);
    }

    @Override
    public String getId(StudentsGroupDto entity) {
        return entity.getId();
    }

    @Override
    protected String getCreateQuery() {
        return "create table if not exists " + getTableName() + " (id text, title text, email text, created_on integer, updated_on integer, school_id text)";
    }

    @Override
    protected void persist(StudentsGroupDto entity, ContentValues values) {
        values.put("id", entity.getId());
        values.put("title", entity.getTitle());
        values.put("email", entity.getEmail());
        values.put("created_on", toTimestamp(entity.getCreatedOn()));
        values.put("updated_on", toTimestamp(entity.getUpdatedOn()));

        if (entity.getSchool() != null) {
            values.put("school_id", entity.getSchool().getId());
        }
    }

    @Override
    protected StudentsGroupDto restore(DBResults results) {

        StudentsGroupDto group = new StudentsGroupDto();

        group.setId(results.getString("id"));
        group.setTitle(results.getString("title"));
        group.setEmail(results.getString("email"));
        group.setCreatedOn(fromTimestamp(results.getLong("created_on")));
        group.setUpdatedOn(fromTimestamp(results.getLong("updated_on")));

        String id = results.getString("school_id");
        if (!isNullOrEmpty(id)) {
            group.setSchool(new SchoolDto());
            group.getSchool().setId(id);
        }

        return group;
    }

    @Override
    public StudentsGroupDto fill(StudentsGroupDto entity) {
        if (entity == null) {
            return entity;
        }

        entity.setSchool(prefill(entity.getSchool(), schoolCache));

        if (!isNullOrEmpty(entity.getId())) {
            List<StudentDto> members = studentCache.getAllByGroupId(entity.getId());
            entity.setMembers(members);
        }

        return entity;
    }

    @Override
    public StudentsGroupDto putWithImmediates(StudentsGroupDto entity) {
        if (entity == null) {
            return entity;
        }

        super.putWithImmediates(entity);

        schoolCache.put(entity.getSchool());
        studentCache.putAll(entity.getMembers());

        return entity;
    }

    public StudentsGroupDto getByTitle(String name) {
        return getFirstWhere("title = ?", name);
    }
}
