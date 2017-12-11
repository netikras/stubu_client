package com.netikras.studies.studentbuddy.api.client.android.data.cache.db.dao;

import android.content.ContentValues;

import com.netikras.studies.studentbuddy.api.client.android.data.cache.CacheManager;
import com.netikras.studies.studentbuddy.core.data.api.dto.location.BuildingFloorDto;
import com.netikras.studies.studentbuddy.core.data.api.dto.location.LectureRoomDto;
import com.netikras.studies.studentbuddy.core.data.api.dto.school.SchoolDto;

import java.util.List;

import static com.netikras.tools.common.security.IntegrityUtils.isNullOrEmpty;

/**
 * Created by netikras on 17.12.8.
 */

public class RoomDao extends GenericDao<LectureRoomDto> {

    FloorDao floorCache;
    SchoolDao schoolCache;
    public RoomDao(CacheManager cacheManager) {
        super(cacheManager, "room");

        floorCache = cacheManager.getDao(FloorDao.class);
        schoolCache = cacheManager.getDao(SchoolDao.class);

    }

    @Override
    public String getId(LectureRoomDto entity) {
        return entity.getId();
    }

    @Override
    protected String getCreateQuery() {
        return "create table if not exists " + getTableName() + " (id text, number text, title text, created_on integer, updated_on integer, school_id text, floor_id text)";
    }

    @Override
    protected void persist(LectureRoomDto entity, ContentValues values) {
        values.put("id", entity.getId());
        values.put("number", entity.getNumber());
        values.put("title", entity.getTitle());
        values.put("created_on", toTimestamp(entity.getCreatedOn()));
        values.put("updated_on", toTimestamp(entity.getUpdatedOn()));

        if (entity.getSchool() != null) {
            values.put("school_id", entity.getSchool().getId());
        }
        if (entity.getFloor() != null) {
            values.put("floor_id", entity.getFloor().getId());
        }
    }

    @Override
    protected LectureRoomDto restore(DBResults results) {
        LectureRoomDto roomDto = new LectureRoomDto();

        roomDto.setId(results.getString("id"));
        roomDto.setNumber(results.getString("number"));
        roomDto.setTitle(results.getString("title"));
        roomDto.setCreatedOn(fromTimestamp(results.getLong("created_on")));
        roomDto.setUpdatedOn(fromTimestamp(results.getLong("updated_on")));

        String id = results.getString("floor_id");
        if (!isNullOrEmpty(id)) {
            roomDto.setFloor(new BuildingFloorDto());
            roomDto.getFloor().setId(id);
        }

        id = results.getString("school_id");
        if (!isNullOrEmpty(id)) {
            roomDto.setSchool(new SchoolDto());
            roomDto.getSchool().setId(id);
        }

        return roomDto;
    }

    @Override
    public LectureRoomDto fill(LectureRoomDto entity) {
        if (entity == null) {
            return entity;
        }

        entity.setFloor(prefill(entity.getFloor(), floorCache));
        entity.setSchool(prefill(entity.getSchool(), schoolCache));

        return entity;
    }

    @Override
    public LectureRoomDto putWithImmediates(LectureRoomDto entity) {
        if (entity == null) {
            return entity;
        }

        super.putWithImmediates(entity);

        floorCache.put(entity.getFloor());
        schoolCache.put(entity.getSchool());

        return entity;
    }

    public List<LectureRoomDto> getAllByFloorId(String id) {
        return getAllWhere("floor_id = ?", id);
    }
}
