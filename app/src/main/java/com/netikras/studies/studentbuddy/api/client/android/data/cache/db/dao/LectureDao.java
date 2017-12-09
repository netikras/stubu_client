package com.netikras.studies.studentbuddy.api.client.android.data.cache.db.dao;

import android.content.ContentValues;

import com.netikras.studies.studentbuddy.api.client.android.data.cache.CacheManager;
import com.netikras.studies.studentbuddy.core.data.api.dto.location.LectureRoomDto;
import com.netikras.studies.studentbuddy.core.data.api.dto.school.AssignmentDto;
import com.netikras.studies.studentbuddy.core.data.api.dto.school.CourseDto;
import com.netikras.studies.studentbuddy.core.data.api.dto.school.DisciplineDto;
import com.netikras.studies.studentbuddy.core.data.api.dto.school.DisciplineTestDto;
import com.netikras.studies.studentbuddy.core.data.api.dto.school.LectureDto;
import com.netikras.studies.studentbuddy.core.data.api.dto.school.LecturerDto;
import com.netikras.studies.studentbuddy.core.data.api.dto.school.StudentsGroupDto;

import java.util.List;

import static com.netikras.tools.common.security.IntegrityUtils.isNullOrEmpty;

/**
 * Created by netikras on 17.12.8.
 */

public class LectureDao extends GenericDao<LectureDto> {

    private StudentDao studentCache;
    private DisciplineDao disciplineCache;
    private LecturerDao lecturerCache;
    private RoomDao roomCache;
    private GroupDao groupsCache;
    private CourseDao courseCache;
    private CommentDao commentCache;
    private AssignmentDao assignmentCache;
    private TestDao testsCache;

    public LectureDao(CacheManager cacheManager) {
        super(cacheManager, "lecture");

        studentCache = cacheManager.getDao(StudentDao.class);
        disciplineCache = cacheManager.getDao(DisciplineDao.class);
        lecturerCache = cacheManager.getDao(LecturerDao.class);
        roomCache = cacheManager.getDao(RoomDao.class);
        groupsCache = cacheManager.getDao(GroupDao.class);
        courseCache = cacheManager.getDao(CourseDao.class);
        commentCache = cacheManager.getDao(CommentDao.class);
        assignmentCache = cacheManager.getDao(AssignmentDao.class);
        testsCache = cacheManager.getDao(TestDao.class);
    }

    @Override
    protected String getId(LectureDto entity) {
        return entity.getId();
    }

    @Override
    protected String getCreateQuery() {
        return "create table if not exists " + getTableName() + " (id text, starts_on integer, ends_on integer, group_id text, room_id text, lecturer_id text, discipline_id text, course_id text)";
    }

    @Override
    protected void persist(LectureDto entity, ContentValues values) {
        values.put("id", entity.getId());
        values.put("starts_on", toTimestamp(entity.getStartsOn()));
        values.put("ends_on", toTimestamp(entity.getEndsOn()));

        if (entity.getStudentsGroup() != null) {
            values.put("group_id", entity.getStudentsGroup().getId());
        }

        if (entity.getRoom() != null) {
            values.put("room_id", entity.getRoom().getId());
        }

        if (entity.getLecturer() != null) {
            values.put("lecturer_id", entity.getLecturer().getId());
        }

        if (entity.getDiscipline() != null) {
            values.put("discipline_id", entity.getDiscipline().getId());
        }

        if (entity.getCourse() != null) {
            values.put("course_id", entity.getCourse().getId());
        }

    }

    @Override
    protected LectureDto restore(DBResults results) {

        LectureDto lecture = new LectureDto();

        lecture.setId(results.getString("id"));
        lecture.setStartsOn(fromTimestamp(results.getLong("starts_on")));
        lecture.setEndsOn(fromTimestamp(results.getLong("ends_on")));

        String id = results.getString("group_id");
        if (!isNullOrEmpty(id)) {
            lecture.setStudentsGroup(new StudentsGroupDto());
            lecture.getStudentsGroup().setId(id);
        }

        id = results.getString("room_id");
        if (!isNullOrEmpty(id)) {
            lecture.setRoom(new LectureRoomDto());
            lecture.getRoom().setId(id);
        }

        id = results.getString("lecturer_id");
        if (!isNullOrEmpty(id)) {
            lecture.setLecturer(new LecturerDto());
            lecture.getLecturer().setId(id);
        }

        id = results.getString("discipline_id");
        if (!isNullOrEmpty(id)) {
            lecture.setDiscipline(new DisciplineDto());
            lecture.getDiscipline().setId(id);
        }

        id = results.getString("course_id");
        if (!isNullOrEmpty(id)) {
            lecture.setCourse(new CourseDto());
            lecture.getCourse().setId(id);
        }

        return lecture;
    }

    @Override
    public LectureDto fill(LectureDto entity) {
        if (entity == null) {
            return entity;
        }

        entity.setCourse(prefill(entity.getCourse(), courseCache));
        entity.setDiscipline(prefill(entity.getDiscipline(), disciplineCache));
        entity.setLecturer(prefill(entity.getLecturer(), lecturerCache));
        entity.setRoom(prefill(entity.getRoom(), roomCache));

        if (!isNullOrEmpty(entity.getId())) {
            List<DisciplineTestDto> tests = testsCache.getAllByLectureId(entity.getId());
            entity.setTests(tests);

            List<AssignmentDto> assignments = assignmentCache.getAllByLectureId(entity.getId());
            entity.setAssignments(assignments);
        }

        return entity;
    }

    @Override
    public LectureDto putWithImmediates(LectureDto entity) {

        if (entity == null) {
            return entity;
        }

        super.putWithImmediates(entity);

        courseCache.put(entity.getCourse());
        disciplineCache.put(entity.getDiscipline());
        lecturerCache.put(entity.getLecturer());
        roomCache.put(entity.getRoom());
        testsCache.putAll(entity.getTests());
        assignmentCache.putAll(entity.getAssignments());

        return entity;
    }

    public List<LectureDto> getAllByCourseId(String id) {
        return getAllWhere("course_id = ?", id);
    }
}
