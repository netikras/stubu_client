package com.netikras.studies.studentbuddy.api.client.android.data.cache.db.dao;

import android.content.ContentValues;

import com.netikras.studies.studentbuddy.api.client.android.data.cache.CacheManager;
import com.netikras.studies.studentbuddy.core.data.api.dto.location.AddressDto;

/**
 * Created by netikras on 17.12.8.
 */

public class AddressDao extends GenericDao<AddressDto> {


    public AddressDao(CacheManager cacheManager) {
        super(cacheManager, "address");
    }

    @Override
    public String getId(AddressDto entity) {
        return entity.getId();
    }

    @Override
    protected String getCreateQuery() {
        return "create table if not exists " + getTableName() + " (id text, country text, city text, street text, building text, postal_code text, longitude text, latitude text)";
    }

    @Override
    protected void persist(AddressDto entity, ContentValues values) {
        values.put("id", entity.getId());
        values.put("country", entity.getCountry());
        values.put("city", entity.getCity());
        values.put("street", entity.getStreet());
        values.put("building", entity.getBuildingNo());
        values.put("postal_code", entity.getPostalCode());
        values.put("longitude", entity.getLongitude());
        values.put("latitude", entity.getLatitude());
    }

    @Override
    protected AddressDto restore(DBResults results) {
        AddressDto address = new AddressDto();

        address.setId(results.getString("id"));
        address.setCountry(results.getString("country"));
        address.setCity(results.getString("city"));
        address.setStreet(results.getString("street"));
        address.setBuildingNo(results.getString("building"));
        address.setPostalCode(results.getString("postal_code"));
        address.setLongitude(results.getString("longitude"));
        address.setLatitude(results.getString("latitude"));

        return address;
    }


}
