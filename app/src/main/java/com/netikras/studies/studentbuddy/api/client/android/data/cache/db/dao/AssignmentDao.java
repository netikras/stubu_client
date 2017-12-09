package com.netikras.studies.studentbuddy.api.client.android.data.cache.db.dao;

import android.content.ContentValues;

import com.netikras.studies.studentbuddy.api.client.android.data.cache.CacheManager;
import com.netikras.studies.studentbuddy.core.data.api.dto.school.AssignmentDto;
import com.netikras.studies.studentbuddy.core.data.api.dto.school.DisciplineDto;
import com.netikras.studies.studentbuddy.core.data.api.dto.school.LectureDto;

import java.util.List;

import static com.netikras.tools.common.security.IntegrityUtils.isNullOrEmpty;

/**
 * Created by netikras on 17.12.8.
 */

public class AssignmentDao extends GenericDao<AssignmentDto> {


    LectureDao lectureCache;

    public AssignmentDao(CacheManager cacheManager) {
        super(cacheManager, "assignment");

        lectureCache = cacheManager.getDao(LectureDao.class);
    }

    @Override
    protected String getId(AssignmentDto entity) {
        return null;
    }

    @Override
    protected String getCreateQuery() {
        return "create table if not exists " + getTableName() + " (id text, description text, created_on integer, updated_on integer, due integer, discipline_id text, lecture_id text)";
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

        if (entity.getLectureDto() != null) {
            values.put("lecture_id", entity.getLectureDto().getId());
        }

        if (entity.getLectureDto() != null) {
            values.put("lecture_id", entity.getLectureDto().getId());
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

        id = results.getString("lecture_id");
        if (!isNullOrEmpty(id)) {
            assignment.setLectureDto(new LectureDto());
            assignment.getLectureDto().setId(id);
        }

        return assignment;
    }

    @Override
    public AssignmentDto fill(AssignmentDto entity) {
        if (entity == null) {
            return entity;
        }

        entity.setLectureDto(prefill(entity.getLectureDto(), lectureCache));

        return entity;
    }

    @Override
    public AssignmentDto putWithImmediates(AssignmentDto entity) {
        if (entity == null) {
            return entity;
        }
        super.putWithImmediates(entity);

        lectureCache.put(entity.getLectureDto());

        return entity;
    }

    public List<AssignmentDto> getAllByLectureId(String id) {
        return getAllWhere("lecture_id = ?", id);
    }
}
