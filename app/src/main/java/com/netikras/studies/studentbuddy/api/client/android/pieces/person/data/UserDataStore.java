package com.netikras.studies.studentbuddy.api.client.android.pieces.person.data;

import com.netikras.studies.studentbuddy.api.client.android.data.stores.CrudDataStore;
import com.netikras.studies.studentbuddy.api.client.android.service.ServiceRequest;
import com.netikras.studies.studentbuddy.api.client.android.service.ServiceRequest.Subscriber;
import com.netikras.studies.studentbuddy.core.data.api.dto.meta.UserDto;

/**
 * Created by netikras on 17.10.31.
 */

public interface UserDataStore extends CrudDataStore<String, UserDto> {

    void login(String username, String password, Subscriber<UserDto>... subscribers);

    void logout(Subscriber<Boolean>... subscribers);
}
