package com.netikras.studies.studentbuddy.api.client.android.pieces.person.data;

import com.netikras.studies.studentbuddy.api.client.android.data.stores.CrudDataStore;
import com.netikras.studies.studentbuddy.api.client.android.service.ServiceRequest;
import com.netikras.studies.studentbuddy.api.client.android.service.ServiceRequest.Subscriber;
import com.netikras.studies.studentbuddy.core.data.api.dto.meta.UserDto;
import com.netikras.tools.common.remote.AuthenticationDetail;

/**
 * Created by netikras on 17.10.31.
 */

public interface UserDataStore extends CrudDataStore<String, UserDto> {

    void getByPerson(String id, Subscriber<UserDto>... subscribers);

    void getByName(String name, Subscriber<UserDto>... subscribers);

    void changePassword(String id, String newPassword, Subscriber<Boolean>... subscribers);

    void assignRole(String id, String role, Subscriber<UserDto>... subscribers);

    void unassignRole(String id, String role, Subscriber<UserDto>... subscribers);

    void login(String username, String password, Subscriber<UserDto>... subscribers);

    void login(AuthenticationDetail auth, Subscriber<UserDto>... subscribers);

    void logout(Subscriber<Boolean>... subscribers);
}
