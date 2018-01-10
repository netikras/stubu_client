package com.netikras.studies.studentbuddy.api.client.android.pieces.person.data.impl;

import com.netikras.studies.studentbuddy.api.client.android.App;
import com.netikras.studies.studentbuddy.api.client.android.data.cache.CacheManager;
import com.netikras.studies.studentbuddy.api.client.android.pieces.person.data.cahe.UserDao;
import com.netikras.studies.studentbuddy.api.client.android.data.stores.ApiBasedDataStore;
import com.netikras.studies.studentbuddy.api.client.android.pieces.person.data.UserDataStore;
import com.netikras.studies.studentbuddy.api.client.android.pieces.sys.data.RoleDataStore;
import com.netikras.studies.studentbuddy.api.client.android.service.ServiceRequest;
import com.netikras.studies.studentbuddy.api.client.android.service.ServiceRequest.Subscriber;
import com.netikras.studies.studentbuddy.api.user.generated.UserApiConsumer;
import com.netikras.studies.studentbuddy.api.user.mgmt.generated.AdminUserApiConsumer;
import com.netikras.studies.studentbuddy.core.data.api.dto.meta.RoleDto;
import com.netikras.studies.studentbuddy.core.data.api.dto.meta.UserDto;
import com.netikras.tools.common.remote.AuthenticationDetail;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.inject.Inject;

import static com.netikras.tools.common.security.IntegrityUtils.isNullOrEmpty;

/**
 * Created by netikras on 17.10.31.
 */

public class UserDataStoreApiImpl extends ApiBasedDataStore<String, UserDto> implements UserDataStore {

    @Inject
    UserApiConsumer apiConsumer;

    @Inject
    AdminUserApiConsumer adminApiConsumer;

    @Inject
    RoleDataStore roleDataStore;

    @Inject
    App app;


    @Inject
    public UserDataStoreApiImpl(CacheManager cacheManager) {
        setCache(cacheManager.getDao(UserDao.class));
    }

    @Override
    protected UserDao getCache() {
        return super.getCache();
    }

    @Override
    public void getCurrentUser(Subscriber<UserDto>... subscribers) {
        orderData(new ServiceRequest<UserDto>() {
            @Override
            public UserDto request() {
                UserDto userDto = updateCache(apiConsumer.getUserDtoCurrent());
                if (userDto != null) {
                    app.setCurrentUser(userDto);
                    pollUserRoles(userDto);
                } else {
                    app.setUserRoles(null);
                }
                return userDto;
            }
        }, subscribers);
    }

    private void pollUserRoles(UserDto userDto) {
        if (userDto == null || isNullOrEmpty(userDto.getRoles())) {
            return;
        }

        List<RoleDto> userRoles = new ArrayList<>();
        if (!isNullOrEmpty(userDto.getRoles())) {
            for (String roleName : userDto.getRoles()) {
                roleDataStore.getByName(roleName, new Subscriber<RoleDto>() {
                    @Override
                    public void onSuccess(RoleDto roleDto) {
                        userRoles.add(roleDto);
                        super.onSuccess(roleDto);
                    }
                });
            }
        }
        app.setUserRoles(userRoles);
    }

    @Override
    public void getById(String id, Subscriber<UserDto>... subscribers) {
        if (isCacheEnabled()) {
            UserDto dto = getCached(id);
            if (dto != null) {
                fillFromCache(dto);
                respondCacheHit(dto, subscribers);
            }
        }
        orderData(new ServiceRequest<UserDto>() {
            @Override
            public UserDto request() {
                return updateCache(apiConsumer.retrieveUserDto(id));
            }
        }, subscribers);
    }

    @Override
    public void getByPerson(String id, Subscriber<UserDto>... subscribers) {
        if (isCacheEnabled()) {
            UserDto dto = getCache().getByPerson(id);
            if (dto != null) {
                fillFromCache(dto);
                respondCacheHit(dto, subscribers);
            }
        }
        orderData(new ServiceRequest<UserDto>() {
            @Override
            public UserDto request() {
                return updateCache(apiConsumer.getUserDtoByPerson(id));
            }
        }, subscribers);
    }

    @Override
    public void getByName(String name, Subscriber<UserDto>... subscribers) {
        if (isCacheEnabled()) {
            UserDto dto = getCache().getByName(name);
            if (dto != null) {
                fillFromCache(dto);
                respondCacheHit(dto, subscribers);
            }
        }
        orderData(new ServiceRequest<UserDto>() {
            @Override
            public UserDto request() {
                return updateCache(apiConsumer.getUserDtoByName(name));
            }
        }, subscribers);
    }

    @Override
    public void changePassword(String id, String newPassword, Subscriber<Boolean>... subscribers) {
        orderData(new ServiceRequest<Boolean>() {
            @Override
            public Boolean request() {
                apiConsumer.changeUserDtoPassword(id, newPassword);
                return Boolean.TRUE;
            }
        }, subscribers);
    }

    @Override
    public void create(UserDto item, Subscriber<UserDto>... subscribers) {
        orderData(new ServiceRequest<UserDto>() {
            @Override
            public UserDto request() {
                return updateCache(adminApiConsumer.createUserDto(item));
            }
        }, subscribers);
    }

    @Override
    public void update(UserDto item, Subscriber<UserDto>... subscribers) {
        orderData(new ServiceRequest<UserDto>() {
            @Override
            public UserDto request() {
                return updateCache(apiConsumer.updateUserDto(item));
            }
        }, subscribers);
    }

    @Override
    public void purge(String id, Subscriber<Boolean>... subscribers) {
        orderData(new ServiceRequest<Boolean>() {
            @Override
            public Boolean request() {
                adminApiConsumer.purgeUserDto(id);
                evict(id);
                return Boolean.TRUE;
            }
        }, subscribers);
    }

    @Override
    public void delete(String id, Subscriber<Boolean>... subscribers) {
        orderData(new ServiceRequest<Boolean>() {
            @Override
            public Boolean request() {
                adminApiConsumer.deleteUserDto(id);
                evict(id);
                return Boolean.TRUE;
            }
        }, subscribers);
    }

    @Override
    @Deprecated
    public void getAll(Subscriber<Collection<UserDto>>... subscribers) {
        notifyNotImplemented(subscribers);
    }


    @Override
    public void assignRole(String id, String role, Subscriber<UserDto>... subscribers) {
        orderData(new ServiceRequest<UserDto>() {
            @Override
            public UserDto request() {
                return updateCache(adminApiConsumer.assignUserDtoRoleByName(id, role));
            }
        }, subscribers);
    }

    @Override
    public void unassignRole(String id, String role, Subscriber<UserDto>... subscribers) {
        orderData(new ServiceRequest<UserDto>() {
            @Override
            public UserDto request() {
                return updateCache(adminApiConsumer.unassignUserDtoRoleByName(id, role));
            }
        }, subscribers);
    }

    @Override
    public void login(String username, String password, Subscriber<UserDto>... subscribers) {
        orderData(new ServiceRequest<UserDto>() {
            @Override
            public UserDto request() {
                return updateCache(apiConsumer.loginUserDto(username, password, null));
            }
        }, subscribers);
    }

    @Override
    public void login(AuthenticationDetail auth, Subscriber<UserDto>... subscribers) {
        orderData(new ServiceRequest<UserDto>() {
            @Override
            public UserDto request() {
                return updateCache(apiConsumer.loginUserDtoAuth(auth));
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
