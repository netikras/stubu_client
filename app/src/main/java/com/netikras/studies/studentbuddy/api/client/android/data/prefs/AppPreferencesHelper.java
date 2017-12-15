package com.netikras.studies.studentbuddy.api.client.android.data.prefs;

import android.content.Context;
import android.content.SharedPreferences;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.netikras.studies.studentbuddy.api.client.android.conf.di.ApplicationCtx;
import com.netikras.studies.studentbuddy.api.client.android.conf.di.PreferenceInfo;
import com.netikras.studies.studentbuddy.api.client.android.util.AppConstants;
import com.netikras.studies.studentbuddy.core.data.api.dto.PersonDto;
import com.netikras.studies.studentbuddy.core.data.api.dto.meta.UserDto;

import java.io.IOException;
import java.util.List;

import javax.inject.Inject;

import static com.netikras.tools.common.security.IntegrityUtils.isNullOrEmpty;

/**
 * Created by janisharali on 27/01/17.
 */

//@Singleton
public class AppPreferencesHelper implements PreferencesHelper {

    private ObjectMapper objectMapper = null;
    private TypeReference<List<String>> cookiesType = new TypeReference<List<String>>() {};

    private static final String PREF_KEY_CURRENT_USER_ID = "PREF_KEY_CURRENT_USER_ID";
    private static final String PREF_KEY_CURRENT_USER_PERSON_ID = "PREF_KEY_CURRENT_USER_PERSON_ID";
    private static final String PREF_KEY_CURRENT_USER_NAME = "PREF_KEY_CURRENT_USER_NAME";
    private static final String PREF_KEY_API_SERVER_URL = "PREF_KEY_API_SERVER_URL";
    private static final String PREF_KEY_NOTIFICATIONS_ENABLED = "PREF_KEY_NOTIFICATIONS_ENABLED";
    private static final String PREF_KEY_COMMENT_NOTIFICATIONS_ENABLED = "PREF_KEY_COMMENT_NOTIFICATIONS_ENABLED";
    private static final String PREF_AUTOSTART_ENABLED = "PREF_AUTOSTART_ENABLED";
    private static final String PREF_KEY_LOGIN_USERNAME = "PREF_KEY_LOGIN_USERNAME";
    private static final String PREF_API_COOKIES = "PREF_API_COOKIES";
    private static final String PREF_LECTURES_AHEAD_PERIOD_HOURS = "PREF_LECTURES_AHEAD_PERIOD_HOURS";
    private static final String PREF_NOTIFY_BEFORE_PERIOD = "PREF_NOTIFY_BEFORE_PERIOD";
    private static final String PREF_UPDATE_PERIOD = "PREF_UPDATE_PERIOD";


    private final SharedPreferences mPrefs;

    private UserDto cachedUser = null;

    private List<String> cachedCookies = null;

    @Inject
    public AppPreferencesHelper(@ApplicationCtx Context context,
                                @PreferenceInfo String prefFileName) {
        mPrefs = context.getSharedPreferences(prefFileName, Context.MODE_PRIVATE);
    }

    @Override
    public String getCurrentUserId() {
        String userId = getPreference(PREF_KEY_CURRENT_USER_ID, AppConstants.NULL_ID);
        return AppConstants.NULL_ID.equals(userId) ? null : userId;
    }

    @Override
    public void setCurrentUserId(String userId) {
        String id = userId == null ? AppConstants.NULL_ID : userId;
        setPreference(PREF_KEY_CURRENT_USER_ID, id);
    }

    @Override
    public String getCurrentUserName() {
        return getPreference(PREF_KEY_CURRENT_USER_NAME, (String) null);
    }

    @Override
    public void setCurrentUserName(String userName) {
        setPreference(PREF_KEY_CURRENT_USER_NAME, userName);
    }

    @Override
    public String getCurrentUserPersonId() {
        String userId = getPreference(PREF_KEY_CURRENT_USER_PERSON_ID, AppConstants.NULL_ID);
        return AppConstants.NULL_ID.equals(userId) ? null : userId;
    }

    @Override
    public void setCurrentUserPersonId(String personId) {
        String id = personId == null ? AppConstants.NULL_ID : personId;
        setPreference(PREF_KEY_CURRENT_USER_ID, id);
    }

    @Override
    public void setCurrentUser(UserDto user) {
        if (user == null || isNullOrEmpty(user.getId())) {
            return;
        }
        setCurrentUserId(user.getId());
        setCurrentUserName(user.getName());
        if (user.getPerson() != null && !isNullOrEmpty(user.getPerson().getId())) {
            setCurrentUserPersonId(user.getPerson().getId());
        }

        cachedUser = user;
    }

    @Override
    public UserDto getCurrentUser() {
        if (cachedUser == null) {
            UserDto userDto = new UserDto();
            userDto.setId(getCurrentUserId());
            if (!isNullOrEmpty(userDto.getId())) {
                userDto.setName(getCurrentUserName());
                userDto.setPerson(new PersonDto());
                userDto.getPerson().setId(getCurrentUserPersonId());
                cachedUser = userDto;
            }
        }

        return cachedUser;
    }

    @Override
    public String getApiServerUrl() {
        return getPreference(PREF_KEY_API_SERVER_URL, "");
    }

    @Override
    public void setApiServerUrl(String url) {
        setPreference(PREF_KEY_API_SERVER_URL, url);
    }

    @Override
    public boolean isNotificationsEnabled() {
        return getPreference(PREF_KEY_NOTIFICATIONS_ENABLED, false);
    }

    @Override
    public void setNotificationsEnabled(boolean enabled) {
        setPreference(PREF_KEY_NOTIFICATIONS_ENABLED, enabled);
    }

    @Override
    public void setLoginUsername(String username) {
        setPreference(PREF_KEY_LOGIN_USERNAME, username);
    }

    @Override
    public String getLoginUsername() {
        return getPreference(PREF_KEY_LOGIN_USERNAME, "");
    }

    @Override
    public void setCookies(List<String> cookies) {
        cachedCookies = cookies;

        setPreference(PREF_API_COOKIES, toJson(cookies));
    }

    @Override
    public List<String> getCookies() {
        if (!isNullOrEmpty(cachedCookies)) {
            return cachedCookies;
        }

        return fromJson(getPreference(PREF_API_COOKIES, ""), cookiesType);
    }

    @Override
    public long getFetchLecturesAheadHours() {
        return getPreference(PREF_LECTURES_AHEAD_PERIOD_HOURS, 24L);
    }

    @Override
    public void setFetchLecturesAheadHours(Long hours) {
        setPreference(PREF_LECTURES_AHEAD_PERIOD_HOURS, hours);
    }

    @Override
    public long getUpdatePeriod() {
        return getPreference(PREF_UPDATE_PERIOD, 15L);
    }

    @Override
    public long getNotifyBeforePeriod() {
        return getPreference(PREF_NOTIFY_BEFORE_PERIOD, 30L);
    }

    @Override
    public boolean isCommentNotificationsEnabled() {
        return getPreference(PREF_KEY_COMMENT_NOTIFICATIONS_ENABLED, true);
    }

    @Override
    public void setUpdatePeriod(long period) {
        setPreference(PREF_UPDATE_PERIOD, period);
    }

    @Override
    public void setNotifyBeforePeriod(long notifyEventsBeforePeriod) {
        setPreference(PREF_NOTIFY_BEFORE_PERIOD, notifyEventsBeforePeriod);
    }

    @Override
    public void setCommentNotificationsEnabled(boolean enabled) {
        setPreference(PREF_KEY_COMMENT_NOTIFICATIONS_ENABLED, enabled);
    }

    @Override
    public boolean isAutostartEnabled() {
        return getPreference(PREF_AUTOSTART_ENABLED, true);
    }

    @Override
    public void setAutostartEnabled(boolean enabled) {
        setPreference(PREF_AUTOSTART_ENABLED, enabled);
    }

    private void setPreference(String key, String value) {
        mPrefs.edit().putString(key, value).apply();
    }

    private String getPreference(String key, String defaultValue) {
        String value = mPrefs.getString(key, defaultValue);
        return value;
    }

    private void setPreference(String key, boolean value) {
        mPrefs.edit().putBoolean(key, value).apply();
    }

    private boolean getPreference(String key, boolean defaultValue) {
        boolean value = mPrefs.getBoolean(key, defaultValue);
        return value;
    }

    private void setPreference(String key, Long value) {
        mPrefs.edit().putLong(key, value).apply();
    }

    private Long getPreference(String key, Long defaultValue) {
        Long value = mPrefs.getLong(key, defaultValue);
        return value;
    }


    private synchronized ObjectMapper getObjectMapper() {
        if (objectMapper == null) {
            objectMapper = new ObjectMapper();
        }
        return objectMapper;
    }

    private String toJson(Object object) {
        try {
            return getObjectMapper().writeValueAsString(object);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private <T> T fromJson(String json, Class<T> type) {
        try {
            return getObjectMapper().readValue(json, type);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private <T> T fromJson(String json, TypeReference<T> type) {
        try {
            return getObjectMapper().readValue(json, type);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
