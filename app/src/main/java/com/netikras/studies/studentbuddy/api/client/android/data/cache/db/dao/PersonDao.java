package com.netikras.studies.studentbuddy.api.client.android.data.cache.db.dao;

import android.content.ContentValues;

import com.netikras.studies.studentbuddy.api.client.android.data.cache.CacheManager;
import com.netikras.studies.studentbuddy.core.data.api.dto.PersonDto;

/**
 * Created by netikras on 17.12.8.
 */

public class PersonDao extends GenericDao<PersonDto> {

    public PersonDao(CacheManager cacheManager) {
        super(cacheManager, "person");
    }


    @Override
    protected String getId(PersonDto entity) {
        return entity.getId();
    }

    @Override
    protected String getCreateQuery() {
        return "create table if not exists " + getTableName() + " (id text, id2 text, first_name text, last_name text, email text, phone text, code text, created_on integer, updated_on integer)";
    }

    @Override
    protected void persist(PersonDto entity, ContentValues values) {
        values.put("id", entity.getId());
        values.put("id2", entity.getIdentification());
        values.put("first_name", entity.getFirstName());
        values.put("last_name", entity.getLastName());
        values.put("code", entity.getPersonalCode());
        values.put("email", entity.getEmail());
        values.put("phone", entity.getPhoneNo());
        values.put("created_on", toTimestamp(entity.getCreatedOn()));
        values.put("updated_on", toTimestamp(entity.getUpdatedOn()));
    }

    @Override
    protected PersonDto restore(DBResults results) {
        PersonDto person = new PersonDto();

        person.setId(results.getString("id"));
        person.setIdentification(results.getString("id2"));
        person.setFirstName(results.getString("first_name"));
        person.setLastName(results.getString("last_name"));
        person.setEmail(results.getString("email"));
        person.setPhoneNo(results.getString("phone"));
        person.setPersonalCode(results.getString("code"));
        person.setCreatedOn(fromTimestamp(results.getLong("created_on")));
        person.setUpdatedOn(fromTimestamp(results.getLong("updated_on")));

        return person;
    }
}
