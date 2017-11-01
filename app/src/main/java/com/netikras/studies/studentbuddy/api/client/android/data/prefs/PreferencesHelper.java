package com.netikras.studies.studentbuddy.api.client.android.data.prefs;


import com.netikras.studies.studentbuddy.core.data.api.dto.meta.UserDto;

public interface PreferencesHelper {

    String getCurrentUserId();

    void setCurrentUserId(String userId);

    String getCurrentUserName();

    void setCurrentUserName(String userName);

    String getCurrentUserEmail();

    void setCurrentUserEmail(String email);

    String getAccessToken();

    void setAccessToken(String accessToken);

    String getCurrentUserPersonId();

    void setCurrentUserPersonId(String personId);

    void setCurrentUser(UserDto user);

    UserDto getCurrentUser();

}
