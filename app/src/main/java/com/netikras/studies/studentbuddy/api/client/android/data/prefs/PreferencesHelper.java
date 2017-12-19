package com.netikras.studies.studentbuddy.api.client.android.data.prefs;


import com.netikras.studies.studentbuddy.core.data.api.dto.meta.UserDto;

import java.util.List;

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


    boolean isLectureNotificationsEnabled();

    void setLectureNotificationsEnabled(boolean enabled);

    void setLoginUsername(String username);

    String getLoginUsername();

    void setCookies(List<String> cookies);

    List<String> getCookies();

    long getFetchLecturesAheadHours();

    void setFetchLecturesAheadHours(Long hours);

    long getUpdatePeriod();

    long getNotifyBeforePeriod();

    boolean isCommentNotificationsEnabled();

    void setUpdatePeriod(long period);

    void setNotifyBeforePeriod(long notifyEventsBeforePeriod);

    void setCommentNotificationsEnabled(boolean enabled);

    boolean isAutostartEnabled();

    void setAutostartEnabled(boolean enabled);

    boolean isNotificationsEnabled();

    void setNotificationsEnabled(boolean enabled);
}
