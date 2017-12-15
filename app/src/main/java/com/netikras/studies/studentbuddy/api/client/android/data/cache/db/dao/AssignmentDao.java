package com.netikras.studies.studentbuddy.api.client.android.data.cache.db.dao;

import android.content.ContentValues;

import com.netikras.studies.studentbuddy.api.client.android.data.cache.CacheManager;
import com.netikras.studies.studentbuddy.core.data.api.dto.school.AssignmentDto;
import com.netikras.studies.studentbuddy.core.data.api.dto.school.DisciplineDto;
import com.netikras.studies.studentbuddy.core.data.api.dto.school.LectureDto;
import com.netikras.studies.studentbuddy.core.data.api.dto.school.StudentDto;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import static com.netikras.tools.common.security.IntegrityUtils.isNullOrEmpty;

/**
 * Created by netikras on 17.12.8.
 */

public class AssignmentDao extends GenericDao<AssignmentDto> {


    LectureDao lectureCache;
    StudentDao studentCache;

    public AssignmentDao(CacheManager cacheManager) {
        super(cacheManager, "assignment");

        lectureCache = cacheManager.getDao(LectureDao.class);
        studentCache = cacheManager.getDao(StudentDao.class);
    }

    @Override
    public String getId(AssignmentDto entity) {
        return entity.getId();
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

    public List<AssignmentDto> getAllByDisciplineDueBetween(String id, Long after, Long before) {
        return getAllWhere("discipline_id = ? and due > ? and due < ?", id, "" + after, "" + before);
    }

    public List<AssignmentDto> getAllStartingBtween(long after, long before) {
        return getAllWhere("due > ? and due < ?", "" + after, "" + before);
    }

    public List<AssignmentDto> getAllByStudentsGroupDueBetween(String id, Long after, Long before) {
        Set<AssignmentDto> assignments = null;

        List<LectureDto> lectures = lectureCache.getAllByGroupStartingBetween(id, "" + after, "" + before);
        if (!isNullOrEmpty(lectures)) {
            assignments = new HashSet<>();

            for (LectureDto lecture : lectures) {
                List<AssignmentDto> dtos = getAllByLectureId(lecture.getId());
                if (!isNullOrEmpty(dtos)) {
                    assignments.addAll(dtos);
                }
            }
            return new ArrayList<>(assignments);
        }

        return null;
    }

    public List<AssignmentDto> getAllByStudentDueBetween(String id, Long after, Long before) {
        List<AssignmentDto> dtos = null;
        StudentDto student = studentCache.get(id);
        if (student != null && student.getGroup() != null) {
            dtos = new ArrayList<>();
            dtos.addAll(getAllByStudentsGroupDueBetween(student.getGroup().getId(), after, before));
            return dtos;
        }
        return null;
    }

    public List<AssignmentDto> getAllByDisciplineAndGroupDueBetween(String disciplineId, String groupId, Long after, Long before) {
        if (isNullOrEmpty(disciplineId)) {
            return null;
        }

        List<AssignmentDto> dtos = getAllByStudentsGroupDueBetween(groupId, after, before);
        if (!isNullOrEmpty(dtos)) {
            for (Iterator<AssignmentDto> iterator = dtos.iterator(); iterator.hasNext();) {
                AssignmentDto dto = iterator.next();
                if (dto == null
                        || dto.getDiscipline() == null
                        || !disciplineId.equals(dto.getDiscipline().getId())) {
                    iterator.remove();
                    continue;
                }
            }
            return dtos;
        }

        return null;
    }
}
