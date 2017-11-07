package com.netikras.studies.studentbuddy.api.client.android.data.prefs;


import com.netikras.studies.studentbuddy.core.data.api.dto.meta.UserDto;

public interface PreferencesHelper {

    String getCurrentUserId();

    void setCurrentUserId(String userId);

    String getCurrentUserName();

    void setCurrentUserName(String userName);

    String getCurrentUserPersonId();

    void setCurrentUserPersonId(String personId);

    void setCurrentUser(UserDto user);

    UserDto getCurrentUser();


    String getApiServerUrl();

    void setApiServerUrl(String url);


    boolean isNotificationsEnabled();

    void setNotificationsEnabled(boolean enabled);

    void setLoginUsername(String username);

    String getLoginUsername();


}
