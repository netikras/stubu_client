package com.netikras.studies.studentbuddy.api.client.android.pieces.sys.data;

import com.netikras.studies.studentbuddy.api.client.android.data.stores.CrudDataStore;
import com.netikras.studies.studentbuddy.api.client.android.service.ServiceRequest;
import com.netikras.studies.studentbuddy.core.data.api.dto.meta.PasswordRequirementDto;

import java.util.Collection;

/**
 * Created by netikras on 17.11.11.
 */

public interface PasswordReqDataStore extends CrudDataStore<String, PasswordRequirementDto> {
    void refresh(ServiceRequest.Subscriber<Boolean>... subscribers);

    void getAllLive(ServiceRequest.Subscriber<Collection<PasswordRequirementDto>>... subscribers);

    void getAllStored(ServiceRequest.Subscriber<Collection<PasswordRequirementDto>>... subscribers);
}
