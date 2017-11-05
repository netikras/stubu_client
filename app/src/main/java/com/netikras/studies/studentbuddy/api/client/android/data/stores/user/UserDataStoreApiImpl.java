package com.netikras.studies.studentbuddy.api.client.android.data.stores.user;

import com.netikras.studies.studentbuddy.api.client.android.data.stores.ApiBasedDataStore;
import com.netikras.studies.studentbuddy.api.client.android.service.ServiceRequest;
import com.netikras.studies.studentbuddy.api.client.android.service.ServiceRequest.Subscriber;
import com.netikras.studies.studentbuddy.api.user.generated.UserApiConsumer;
import com.netikras.studies.studentbuddy.api.user.mgmt.generated.AdminUserApiConsumer;
import com.netikras.studies.studentbuddy.core.data.api.dto.meta.UserDto;
import com.netikras.tools.common.exception.ErrorBody;
import com.netikras.tools.common.exception.FriendlyUncheckedException;

import java.util.Collection;

import javax.inject.Inject;

/**
 * Created by netikras on 17.10.31.
 */

public class UserDataStoreApiImpl extends ApiBasedDataStore<String, UserDto> implements UserDataStore {

    @Inject
    UserApiConsumer apiConsumer;

    @Inject
    AdminUserApiConsumer adminApiConsumer;


    @Inject
    public UserDataStoreApiImpl() {
    }


    @Override
    public void getById(String id, Subscriber<UserDto>... subscribers) {
        orderData(new ServiceRequest() {
            @Override
            public UserDto request() {
                return apiConsumer.retrieveUserDto(id);
            }
        }, subscribers);
    }

    @Override
    public void create(UserDto item, Subscriber<UserDto>... subscribers) {
        orderData(new ServiceRequest() {
            @Override
            public UserDto request() {
                return adminApiConsumer.createUserDto(item);
            }
        }, subscribers);
    }

    @Override
    public void update(UserDto item, Subscriber<UserDto>... subscribers) {
        orderData(new ServiceRequest() {
            @Override
            public UserDto request() {
                return apiConsumer.updateUserDto(item);
            }
        }, subscribers);
    }

    @Override
    public void purge(String id, Subscriber<Boolean>... subscribers) {
        orderData(new ServiceRequest() {
            @Override
            public Boolean request() {
                adminApiConsumer.purgeUserDto(id);
                return Boolean.TRUE;
            }
        }, subscribers);
    }

    @Override
    public void delete(String id, Subscriber... subscribers) {
        orderData(new ServiceRequest() {
            @Override
            public Boolean request() {
                adminApiConsumer.deleteUserDto(id);
                return Boolean.TRUE;
            }
        }, subscribers);
    }

    @Override
    public void getAll(Subscriber<Collection<UserDto>>... subscribers) {
        orderData(new ServiceRequest() {
            @Override
            public UserDto request() {
                throw new FriendlyUncheckedException()
                        .addError(new ErrorBody().setMessage1("Not implemented"));
            }
        }, subscribers);
    }


    @Override
    public void login(String username, String password, Subscriber<UserDto>... subscribers) {
        orderData(new ServiceRequest<UserDto>() {
            @Override
            public UserDto request() {
                return apiConsumer.loginUserDto(username, password, null);
            }
        }, subscribers);
    }

    @Override
    public void logout(Subscriber<Boolean>... subscribers) {
        orderData(new ServiceRequest<Boolean>() {
            @Override
            public Boolean request() {
                apiConsumer.logoutUserDto();
                return Boolean.TRUE;
            }
        }, subscribers);
    }
}
