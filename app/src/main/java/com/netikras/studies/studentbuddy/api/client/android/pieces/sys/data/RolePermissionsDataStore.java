package com.netikras.studies.studentbuddy.api.client.android.pieces.sys.data;

import com.netikras.studies.studentbuddy.api.client.android.data.stores.CrudDataStore;
import com.netikras.studies.studentbuddy.api.client.android.service.ServiceRequest;
import com.netikras.studies.studentbuddy.core.data.api.dto.meta.RolePermissionDto;

/**
 * Created by netikras on 17.11.11.
 */

public interface RolePermissionsDataStore extends CrudDataStore<String, RolePermissionDto> {
    void create(RolePermissionDto item, String resourceId, ServiceRequest.Subscriber<RolePermissionDto>... subscribers);

    void create(String roleName, String resourceName, String actionName, String resourceId, Boolean strict, ServiceRequest.Subscriber<RolePermissionDto>... subscribers);

    void deleteById(String rolename, String id, ServiceRequest.Subscriber<Boolean>... subscribers);

    void delete(String roleName, String resourceName, String actionName, String resourceId, Boolean strict, ServiceRequest.Subscriber<Boolean>... subscribers);

    void refresh(ServiceRequest.Subscriber<Boolean>... subscribers);
}
