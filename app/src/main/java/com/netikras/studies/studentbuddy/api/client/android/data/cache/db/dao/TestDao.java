package com.netikras.studies.studentbuddy.api.client.android.data.cache.db.dao;

import android.content.ContentValues;

import com.netikras.studies.studentbuddy.api.client.android.data.cache.CacheManager;
import com.netikras.studies.studentbuddy.core.data.api.dto.school.DisciplineDto;
import com.netikras.studies.studentbuddy.core.data.api.dto.school.DisciplineTestDto;
import com.netikras.studies.studentbuddy.core.data.api.dto.school.LectureDto;

import java.util.List;

import static com.netikras.tools.common.security.IntegrityUtils.isNullOrEmpty;

/**
 * Created by netikras on 17.12.8.
 */

public class TestDao extends GenericDao<DisciplineTestDto> {

    LectureDao lectureCache;
    DisciplineDao disciplineCache;

    public TestDao(CacheManager cacheManager) {
        super(cacheManager, "test");

        lectureCache = cacheManager.getDao(LectureDao.class);
        disciplineCache = cacheManager.getDao(DisciplineDao.class);
    }

    @Override
    protected String getId(DisciplineTestDto entity) {
        return entity.getId();
    }

    @Override
    protected String getCreateQuery() {
        return "create table if not exists " + getTableName() + " (id text, description text, exam integer, starts_on integer, updated_on integer, discipline_id text, lecture_id text)";
    }

    @Override
    protected void persist(DisciplineTestDto entity, ContentValues values) {
        values.put("id", entity.getId());
        values.put("description", entity.getDescription());
        values.put("exam", entity.isExam() ? 1 : 0);
        values.put("starts_on", toTimestamp(entity.getStartsOn()));
        values.put("updated_on", toTimestamp(entity.getUpdatedOn()));

        if (entity.getDiscipline() != null) {
            values.put("discipline_id", entity.getDiscipline().getId());
        }

        if (entity.getLecture() != null) {
            values.put("lecture_id", entity.getLecture().getId());
        }
    }

    @Override
    protected DisciplineTestDto restore(DBResults results) {

        DisciplineTestDto testDto = new DisciplineTestDto();

        testDto.setId(results.getString("id"));
        testDto.setDescription(results.getString("description"));
        testDto.setExam(results.getInt("exam") == 1);
        testDto.setStartsOn(fromTimestamp(results.getLong("starts_on")));
        testDto.setUpdatedOn(fromTimestamp(results.getLong("updated_on")));

        String id = results.getString("discipline_id");
        if (!isNullOrEmpty(id)) {
            testDto.setDiscipline(new DisciplineDto());
            testDto.getDiscipline().setId(id);
        }

        id = results.getString("lecture_id");
        if (!isNullOrEmpty(id)) {
            testDto.setLecture(new LectureDto());
            testDto.getLecture().setId(id);
        }

        return testDto;
    }

    @Override
    public DisciplineTestDto fill(DisciplineTestDto entity) {
        if (entity == null) {
            return entity;
        }

        entity.setLecture(prefill(entity.getLecture(), lectureCache));
        entity.setDiscipline(prefill(entity.getDiscipline(), disciplineCache));

        return entity;
    }

    @Override
    public DisciplineTestDto putWithImmediates(DisciplineTestDto entity) {
        if (entity == null) {
            return entity;
        }

        super.putWithImmediates(entity);

        lectureCache.put(entity.getLecture());
        disciplineCache.put(entity.getDiscipline());

        return entity;
    }

    public List<DisciplineTestDto> getAllByLectureId(String id) {
        return getAllWhere("lecture_id = ?", id);
    }
}
