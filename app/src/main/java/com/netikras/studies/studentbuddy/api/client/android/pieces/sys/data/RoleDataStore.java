package com.netikras.studies.studentbuddy.api.client.android.pieces.sys.data;

import com.netikras.studies.studentbuddy.api.client.android.data.stores.CrudDataStore;
import com.netikras.studies.studentbuddy.api.client.android.service.ServiceRequest;
import com.netikras.studies.studentbuddy.core.data.api.dto.meta.RoleDto;

/**
 * Created by netikras on 17.11.11.
 */

public interface RoleDataStore extends CrudDataStore<String, RoleDto> {
    void create(String name, ServiceRequest.Subscriber<RoleDto>... subscribers);
}
