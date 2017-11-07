package com.netikras.studies.studentbuddy.api.client.android.ui.settings.presenter;

import com.netikras.studies.studentbuddy.api.client.android.data.DataManager;
import com.netikras.studies.studentbuddy.api.client.android.data.prefs.PreferencesHelper;
import com.netikras.studies.studentbuddy.api.client.android.ui.base.BasePresenter;
import com.netikras.studies.studentbuddy.api.client.android.ui.settings.view.SettingsMvpView;
import com.netikras.tools.common.remote.RemoteEndpointServer;

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

}
