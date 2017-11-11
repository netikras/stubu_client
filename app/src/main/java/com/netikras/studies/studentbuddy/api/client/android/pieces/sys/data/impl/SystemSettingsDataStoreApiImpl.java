package com.netikras.studies.studentbuddy.api.client.android.pieces.sys.data.impl;

import com.netikras.studies.studentbuddy.api.client.android.data.stores.ApiBasedDataStore;
import com.netikras.studies.studentbuddy.api.client.android.pieces.sys.data.SystemSettingsDataStore;
import com.netikras.studies.studentbuddy.api.client.android.service.ServiceRequest;
import com.netikras.studies.studentbuddy.core.data.api.dto.meta.SystemSettingDto;

import java.util.Collection;

/**
 * Created by netikras on 17.11.11.
 */

public class SystemSettingsDataStoreApiImpl extends ApiBasedDataStore<String, SystemSettingDto> implements SystemSettingsDataStore {
    @Override
    public void getById(String id, ServiceRequest.Subscriber<SystemSettingDto>[] subscribers) {

    }

    @Override
    public void create(SystemSettingDto item, ServiceRequest.Subscriber<SystemSettingDto>[] subscribers) {

    }

    @Override
    public void update(SystemSettingDto item, ServiceRequest.Subscriber<SystemSettingDto>[] subscribers) {

    }

    @Override
    public void purge(String id, ServiceRequest.Subscriber<Boolean>[] subscribers) {

    }

    @Override
    public void delete(String id, ServiceRequest.Subscriber[] subscribers) {

    }

    @Override
    public void getAll(ServiceRequest.Subscriber<Collection<SystemSettingDto>>[] subscribers) {

    }
}
