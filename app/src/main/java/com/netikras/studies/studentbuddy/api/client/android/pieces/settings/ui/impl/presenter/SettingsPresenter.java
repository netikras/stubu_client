package com.netikras.studies.studentbuddy.api.client.android.pieces.settings.ui.impl.presenter;

import com.netikras.studies.studentbuddy.api.client.android.data.DataManager;
import com.netikras.studies.studentbuddy.api.client.android.data.prefs.PreferencesHelper;
import com.netikras.studies.studentbuddy.api.client.android.pieces.base.BasePresenter;
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
    public void saveLectureNotificationsEnabled(boolean enabled) {
        appPreferences.setLectureNotificationsEnabled(enabled);
    }

    @Override
    public String getApiUrl() {
        return appPreferences.getApiServerUrl();
    }

    @Override
    public boolean isLectureNotificationsEnabled() {
        return appPreferences.isLectureNotificationsEnabled();
    }

    @Override
    public long getUpdatePeriod() {
        return appPreferences.getUpdatePeriod();
    }

    @Override
    public long getNotifyEventBeforePeriod() {
        return appPreferences.getNotifyBeforePeriod();
    }

    @Override
    public long getLecturesAheadPeriod() {
        return appPreferences.getFetchLecturesAheadHours();
    }

    @Override
    public boolean isCommentNotificationsEnabled() {
        return appPreferences.isCommentNotificationsEnabled();
    }

    @Override
    public void saveUpdatePeriod(long updatePeriod) {
        appPreferences.setUpdatePeriod(updatePeriod);
    }

    @Override
    public void saveNotifyEventBeforePeriod(long notifyEventsBeforePeriod) {
        appPreferences.setNotifyBeforePeriod(notifyEventsBeforePeriod);
    }

    @Override
    public void saveLecturesAheadPeriod(long lecturesAheadPeriod) {
        appPreferences.setFetchLecturesAheadHours(lecturesAheadPeriod);
    }

    @Override
    public void saveCommentNotificationsSettings(boolean commentNotificationsEnabled) {
        appPreferences.setCommentNotificationsEnabled(commentNotificationsEnabled);
    }

    @Override
    public void saveAutostartEnabled(boolean autostartEnabled) {
        appPreferences.setAutostartEnabled(autostartEnabled);
    }

    @Override
    public boolean getAutostartEnabled() {
        return appPreferences.isAutostartEnabled();
    }

    @Override
    public boolean getNotificationsEnabled() {
        return appPreferences.isNotificationsEnabled();
    }

    @Override
    public void saveNotificationsEnabled(boolean notificationsEnabled) {
        appPreferences.setNotificationsEnabled(notificationsEnabled);
    }

}
