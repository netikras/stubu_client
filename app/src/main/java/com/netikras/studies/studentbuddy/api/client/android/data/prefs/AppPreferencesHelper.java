package com.netikras.studies.studentbuddy.api.client.android.data.prefs;

import android.content.Context;
import android.content.SharedPreferences;

import com.netikras.studies.studentbuddy.api.client.android.conf.di.ApplicationCtx;
import com.netikras.studies.studentbuddy.api.client.android.conf.di.PreferenceInfo;
import com.netikras.studies.studentbuddy.api.client.android.util.AppConstants;
import com.netikras.studies.studentbuddy.core.data.api.dto.PersonDto;
import com.netikras.studies.studentbuddy.core.data.api.dto.meta.UserDto;

import javax.inject.Inject;
import javax.inject.Singleton;

import static com.netikras.tools.common.security.IntegrityUtils.isNullOrEmpty;

/**
 * Created by janisharali on 27/01/17.
 */

@Singleton
public class AppPreferencesHelper implements PreferencesHelper {

    private static final String PREF_KEY_CURRENT_USER_ID = "PREF_KEY_CURRENT_USER_ID";
    private static final String PREF_KEY_CURRENT_USER_PERSON_ID = "PREF_KEY_CURRENT_USER_PERSON_ID";
    private static final String PREF_KEY_CURRENT_USER_NAME = "PREF_KEY_CURRENT_USER_NAME";
    private static final String PREF_KEY_CURRENT_USER_EMAIL = "PREF_KEY_CURRENT_USER_EMAIL";
    private static final String PREF_KEY_ACCESS_TOKEN = "PREF_KEY_ACCESS_TOKEN";

    private final SharedPreferences mPrefs;

    private UserDto cached = null;

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
        return getPreference(PREF_KEY_CURRENT_USER_NAME, null);
    }

    @Override
    public void setCurrentUserName(String userName) {
        setPreference(PREF_KEY_CURRENT_USER_NAME, userName);
    }

    @Override
    public String getCurrentUserEmail() {
        return getPreference(PREF_KEY_CURRENT_USER_EMAIL, null);
    }

    @Override
    public void setCurrentUserEmail(String email) {
        setPreference(PREF_KEY_CURRENT_USER_EMAIL, email);
    }

    @Override
    public String getAccessToken() {
        return getPreference(PREF_KEY_ACCESS_TOKEN, null);
    }

    @Override
    public void setAccessToken(String accessToken) {
        setPreference(PREF_KEY_ACCESS_TOKEN, accessToken);
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

        cached = user;
    }

    @Override
    public UserDto getCurrentUser() {
        if (cached == null) {
            UserDto userDto = new UserDto();
            userDto.setId(getCurrentUserId());
            if (!isNullOrEmpty(userDto.getId())) {
                userDto.setName(getCurrentUserName());
                userDto.setPerson(new PersonDto());
                userDto.getPerson().setId(getCurrentUserPersonId());
                cached = userDto;
            }
        }

        return cached;
    }

    private void setPreference(String key, String value) {
        mPrefs.edit().putString(key, value).apply();
    }

    private String getPreference(String key, String defaultValue) {
        String value = mPrefs.getString(key, defaultValue);
        return value;
    }
}
