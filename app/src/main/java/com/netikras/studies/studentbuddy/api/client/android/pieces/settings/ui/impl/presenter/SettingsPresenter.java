package com.netikras.studies.studentbuddy.api.client.android.pieces.settings.ui.impl.presenter;

import android.content.Context;

import com.netikras.studies.studentbuddy.api.client.android.data.DataManager;
import com.netikras.studies.studentbuddy.api.client.android.data.prefs.PreferencesHelper;
import com.netikras.studies.studentbuddy.api.client.android.pieces.base.BasePresenter;
import com.netikras.studies.studentbuddy.api.client.android.pieces.settings.ui.impl.view.SettingsActivity;
import com.netikras.studies.studentbuddy.api.client.android.pieces.settings.ui.presenter.SettingsMvpPresenter;
import com.netikras.studies.studentbuddy.api.client.android.pieces.settings.ui.view.SettingsMvpView;

import javax.inject.Inject;

/**
 * Created by netikras on 17.11.5.
 */

public class SettingsPresenter<V extends SettingsMvpView> extends BasePresenter<V> implements SettingsMvpPresenter<V> {

    @Inject
    PreferencesHelper appPreferences;

    @Inject
    public SettingsPresenter(DataManager dataManager) {
        super(dataManager);
    }

    @Override
    public void saveApiUrl(String url) {
        appPreferences.setApiServerUrl(url);
    }

    @Override
    public void saveNotificationsSettings(boolean enabled) {
        appPreferences.setNotificationsEnabled(enabled);
    }

    @Override
    public String getApiUrl() {
        return appPreferences.getApiServerUrl();
    }

    @Override
    public boolean isNotificationsEnabled() {
        return appPreferences.isNotificationsEnabled();
    }


    @Override
    public void startView(Context fromContext) {
        startView(fromContext, SettingsActivity.class);
    }
}
