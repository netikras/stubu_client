package com.netikras.studies.studentbuddy.api.client.android.data.cache.db.dao;

import android.content.ContentValues;

import com.netikras.studies.studentbuddy.api.client.android.data.cache.CacheManager;
import com.netikras.studies.studentbuddy.core.data.api.dto.meta.UserDto;

import static com.netikras.tools.common.security.IntegrityUtils.isNullOrEmpty;

/**
 * Created by netikras on 17.12.8.
 */

public class UserDao extends GenericDao<UserDto> {

    PersonDao personCache;

    public UserDao(CacheManager cacheManager) {
        super(cacheManager, "user_");

        personCache = cacheManager.getDao(PersonDao.class);
    }

    @Override
    public String getId(UserDto entity) {
        return entity.getId();
    }

    @Override
    protected String getCreateQuery() {
        return "create table if not exists " + getTableName() + " (id text, name text, created_on integer, updated_on integer, person_id text, roles text)";
    }

    @Override
    protected void persist(UserDto entity, ContentValues values) {
        values.put("id", entity.getId());
        values.put("name", entity.getName());
        values.put("created_on", toTimestamp(entity.getCreatedOn()));
        values.put("updated_on", toTimestamp(entity.getUpdatedOn()));

        if (entity.getPerson() != null) {
            values.put("person_id", entity.getPerson().getId());
            getDao(PersonDao.class).create(entity.getPerson());
        }

        if (!isNullOrEmpty(entity.getRoles())) {
            values.put("roles", joinCollection(entity.getRoles()));
        }

    }

    @Override
    protected UserDto restore(DBResults results) {
        UserDto user = new UserDto();

        user.setId(results.getString("id"));
        user.setName(results.getString("name"));
        user.setCreatedOn(fromTimestamp(results.getLong("created_on")));
        user.setUpdatedOn(fromTimestamp(results.getLong("updated_on")));

        String id = results.getString("person_id");

        if (!isNullOrEmpty(id)) {
            user.setPerson(getDao(PersonDao.class).get(id));
        }

        user.setRoles(toCollection(results.getString("roles")));

        return user;
    }

    @Override
    public UserDto fill(UserDto entity) {
        if (entity == null) {
            return entity;
        }

        entity.setPerson(prefill(entity.getPerson(), personCache));

        return entity;
    }

    @Override
    public UserDto putWithImmediates(UserDto entity) {
        if (entity == null) {
            return entity;
        }

        super.putWithImmediates(entity);
        personCache.put(entity.getPerson());

        return entity;
    }
}
