package com.netikras.studies.studentbuddy.api.client.android.pieces.sys.data;

import com.netikras.studies.studentbuddy.api.client.android.data.stores.CrudDataStore;
import com.netikras.studies.studentbuddy.api.client.android.service.ServiceRequest;
import com.netikras.studies.studentbuddy.core.data.api.dto.meta.SystemSettingDto;

import java.util.Collection;

/**
 * Created by netikras on 17.11.11.
 */

public interface SystemSettingsDataStore extends CrudDataStore<String, SystemSettingDto> {
    void getStoredByName(String name, ServiceRequest.Subscriber<SystemSettingDto>... subscribers);

    void getLiveByName(String name, ServiceRequest.Subscriber<SystemSettingDto>... subscribers);

    void deleteStoredByName(String name, ServiceRequest.Subscriber... subscribers);

    void refresh(ServiceRequest.Subscriber... subscribers);

    void getAllLive(ServiceRequest.Subscriber<Collection<SystemSettingDto>>... subscribers);

    void getAllStored(ServiceRequest.Subscriber<Collection<SystemSettingDto>>... subscribers);
}
