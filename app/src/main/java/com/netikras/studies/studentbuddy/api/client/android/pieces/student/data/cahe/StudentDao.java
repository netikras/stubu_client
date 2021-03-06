package com.netikras.studies.studentbuddy.api.client.android.pieces.student.data.cahe;

import android.content.ContentValues;

import com.netikras.studies.studentbuddy.api.client.android.data.cache.CacheManager;
import com.netikras.studies.studentbuddy.api.client.android.data.cache.GenericDao;
import com.netikras.studies.studentbuddy.api.client.android.pieces.person.data.cahe.PersonDao;
import com.netikras.studies.studentbuddy.api.client.android.pieces.school.data.cahe.SchoolDao;
import com.netikras.studies.studentbuddy.core.data.api.dto.PersonDto;
import com.netikras.studies.studentbuddy.core.data.api.dto.school.SchoolDepartmentDto;
import com.netikras.studies.studentbuddy.core.data.api.dto.school.SchoolDto;
import com.netikras.studies.studentbuddy.core.data.api.dto.school.StudentDto;
import com.netikras.studies.studentbuddy.core.data.api.dto.school.StudentsGroupDto;

import java.util.List;

import static com.netikras.tools.common.security.IntegrityUtils.isNullOrEmpty;

/**
 * Created by netikras on 17.12.8.
 */

public class StudentDao extends GenericDao<StudentDto> {


    GroupDao groupCache;
    SchoolDao schoolCache;
    PersonDao personCache;

    public StudentDao(CacheManager cacheManager) {
        super(cacheManager, "student");

        groupCache = cacheManager.getDao(GroupDao.class);
        schoolCache = cacheManager.getDao(SchoolDao.class);
        personCache = cacheManager.getDao(PersonDao.class);

    }

    @Override
    public String getId(StudentDto entity) {
        return entity.getId();
    }

    @Override
    protected String getCreateQuery() {
        return "create table if not exists " + getTableName() + " (id text, created_on integer, updated_on integer, group_id text, person_id text, school_id text, department_id text)";
    }

    @Override
    protected void persist(StudentDto entity, ContentValues values) {

        values.put("id", entity.getId());
        values.put("created_on", toTimestamp(entity.getCreatedOn()));
        values.put("updated_on", toTimestamp(entity.getUpdatedOn()));

        if (entity.getGroup() != null) {
            values.put("group_id", entity.getGroup().getId());
        }

        if (entity.getPerson() != null) {
            values.put("person_id", entity.getPerson().getId());
        }

        if (entity.getSchool() != null) {
            values.put("school_id", entity.getSchool().getId());
        }

        if (entity.getDepartment() != null) {
            values.put("department_id", entity.getDepartment().getId());
        }

    }

    @Override
    protected StudentDto restore(DBResults results) {
        StudentDto student = new StudentDto();

        student.setId(results.getString("id"));
        student.setCreatedOn(fromTimestamp(results.getLong("created_on")));
        student.setUpdatedOn(fromTimestamp(results.getLong("updated_on")));

        String id = results.getString("group_id");
        if (!isNullOrEmpty(id)) {
            student.setGroup(new StudentsGroupDto());
            student.getGroup().setId(id);
        }

        id = results.getString("person_id");
        if (!isNullOrEmpty(id)) {
            student.setPerson(new PersonDto());
            student.getPerson().setId(id);
        }

        id = results.getString("school_id");
        if (!isNullOrEmpty(id)) {
            student.setSchool(new SchoolDto());
            student.getSchool().setId(id);
        }

        id = results.getString("department_id");
        if (!isNullOrEmpty(id)) {
            student.setDepartment(new SchoolDepartmentDto());
            student.getDepartment().setId(id);
        }

        return student;
    }

    @Override
    public StudentDto fill(StudentDto entity) {
        if (entity == null) {
            return entity;
        }

        entity.setGroup(prefill(entity.getGroup(), groupCache));
//        entity.setDepartment(prefill(entity.getDepartment(), ));
        entity.setSchool(prefill(entity.getSchool(), schoolCache));
        entity.setPerson(prefill(entity.getPerson(), personCache));

        return entity;
    }

    @Override
    public StudentDto putWithImmediates(StudentDto entity) {
        if (entity == null) {
            return entity;
        }

        super.putWithImmediates(entity);

        groupCache.put(entity.getGroup());
//        entity.getDepartment());
        schoolCache.put(entity.getSchool());
        personCache.put(entity.getPerson());

        return entity;
    }

    public List<StudentDto> getAllByPersonId(String personId) {
        return getAllWhere("person_id = ?", personId);
    }

    public List<StudentDto> getAllByGroupId(String id) {
        return getAllWhere("group_id = ?", id);
    }

}
