package com.netikras.studies.studentbuddy.api.client.android.data.cache.db.dao;

import android.content.ContentValues;

import com.netikras.studies.studentbuddy.api.client.android.data.cache.CacheManager;
import com.netikras.studies.studentbuddy.core.data.api.dto.location.BuildingDto;
import com.netikras.studies.studentbuddy.core.data.api.dto.location.BuildingFloorDto;
import com.netikras.studies.studentbuddy.core.data.api.dto.location.FloorLayoutDto;
import com.netikras.studies.studentbuddy.core.data.api.dto.location.LectureRoomDto;

import java.util.List;

import static com.netikras.tools.common.security.IntegrityUtils.isNullOrEmpty;

/**
 * Created by netikras on 17.12.8.
 */

public class FloorDao extends GenericDao<BuildingFloorDto> {


    BuildingDao buildingCache;
    SectionDao sectionCache;
    LayoutDao layoutCache;
    RoomDao roomCache;
    public FloorDao(CacheManager cacheManager) {
        super(cacheManager, "floor");

        buildingCache = cacheManager.getDao(BuildingDao.class);
        sectionCache = cacheManager.getDao(SectionDao.class);
        layoutCache = cacheManager.getDao(LayoutDao.class);
        roomCache = cacheManager.getDao(RoomDao.class);
    }

    @Override
    public String getId(BuildingFloorDto entity) {
        return entity.getId();
    }

    @Override
    protected String getCreateQuery() {
        return "create table if not exists " + getTableName() + " (id text, title text, number text, building_id text)";
    }

    @Override
    protected void persist(BuildingFloorDto entity, ContentValues values) {
        values.put("id", entity.getId());
        values.put("title", entity.getTitle());
        values.put("number", entity.getNumber());

        if (entity.getBuilding() != null) {
            values.put("building_id", entity.getBuilding().getId());
        }
    }

    @Override
    protected BuildingFloorDto restore(DBResults results) {

        BuildingFloorDto floor = new BuildingFloorDto();

        floor.setId(results.getString("id"));
        floor.setTitle(results.getString("title"));
        floor.setNumber(results.getString("number"));

        String id = results.getString("building_id");
        if (!isNullOrEmpty(id)) {
            floor.setBuilding(new BuildingDto());
            floor.getBuilding().setId(id);
        }

        return floor;
    }

    @Override
    public BuildingFloorDto fill(BuildingFloorDto entity) {
        if (entity == null) {
            return entity;
        }

        entity.setBuilding(prefill(entity.getBuilding(), buildingCache));
        entity.setBuildingSection(prefill(entity.getBuildingSection(), sectionCache));

        if (!isNullOrEmpty(entity.getId())) {
            List<FloorLayoutDto> layouts = layoutCache.getAllByFloorId(entity.getId());
            entity.setLayouts(layouts);

            List<LectureRoomDto> rooms = roomCache.getAllByFloorId(entity.getId());
            entity.setLectureRooms(rooms);
        }

        return entity;
    }

    @Override
    public BuildingFloorDto putWithImmediates(BuildingFloorDto entity) {
        if (entity == null) {
            return entity;
        }

        super.putWithImmediates(entity);

        buildingCache.put(entity.getBuilding());
        sectionCache.put(entity.getBuildingSection());
        layoutCache.putAll(entity.getLayouts());
        roomCache.putAll(entity.getLectureRooms());

        return entity;
    }

    public List<BuildingFloorDto> getAllByBuildingId(String id) {
        return getAllWhere("building_id = ?", id);
    }
}
