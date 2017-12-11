package com.netikras.studies.studentbuddy.api.client.android.data.cache.db.dao;

import android.content.ContentValues;

import com.netikras.studies.studentbuddy.api.client.android.data.cache.CacheManager;
import com.netikras.studies.studentbuddy.core.data.api.dto.PersonDto;
import com.netikras.studies.studentbuddy.core.data.api.dto.school.LecturerDto;
import com.netikras.studies.studentbuddy.core.data.api.dto.school.SchoolDto;

import java.util.List;

import static com.netikras.tools.common.security.IntegrityUtils.isNullOrEmpty;

/**
 * Created by netikras on 17.12.8.
 */

public class LecturerDao extends GenericDao<LecturerDto> {

    PersonDao personCache;
    SchoolDao schoolCache;

    public LecturerDao(CacheManager cacheManager) {
        super(cacheManager, "lecturer");

        personCache = cacheManager.getDao(PersonDao.class);
        schoolCache = cacheManager.getDao(SchoolDao.class);
    }

    @Override
    public String getId(LecturerDto entity) {
        return entity.getId();
    }

    @Override
    protected String getCreateQuery() {
        return "create table if not exists " + getTableName() + " (id text, degree text, school_id text, person_id text)";
    }

    @Override
    protected void persist(LecturerDto entity, ContentValues values) {
        values.put("id", entity.getId());
        values.put("degree", entity.getDegree());

        if (entity.getSchool() != null) {
            values.put("school_id", entity.getSchool().getId());
        }

        if (entity.getPerson() != null) {
            values.put("person_id", entity.getPerson().getId());
        }
    }

    @Override
    protected LecturerDto restore(DBResults results) {

        LecturerDto lecturer = new LecturerDto();

        lecturer.setId(results.getString("id"));
        lecturer.setDegree(results.getString("degree"));

        String id = results.getString("school_id");
        if (!isNullOrEmpty(id)) {
            lecturer.setSchool(new SchoolDto());
            lecturer.getSchool().setId(id);
        }

        id = results.getString("person_id");
        if (!isNullOrEmpty(id)) {
            lecturer.setPerson(new PersonDto());
            lecturer.getPerson().setId(id);
        }

        return lecturer;
    }

    @Override
    public LecturerDto fill(LecturerDto entity) {
        if (entity == null) {
            return entity;
        }

        entity.setPerson(prefill(entity.getPerson(), personCache));
        entity.setSchool(prefill(entity.getSchool(), schoolCache));

        return entity;
    }

    @Override
    public LecturerDto putWithImmediates(LecturerDto entity) {
        if (entity == null) {
            return entity;
        }

        super.putWithImmediates(entity);

        personCache.put(entity.getPerson());
        schoolCache.put(entity.getSchool());

        return entity;
    }

    public List<LecturerDto> getAllByPerson(String id) {
        return getAllWhere("person_id = ?", id);
    }
}
