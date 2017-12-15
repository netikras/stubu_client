package com.netikras.studies.studentbuddy.api.client.android.pieces.settings.ui.presenter;

import com.netikras.studies.studentbuddy.api.client.android.pieces.base.MvpPresenter;
import com.netikras.studies.studentbuddy.api.client.android.pieces.settings.ui.view.SettingsMvpView;

/**
 * Created by netikras on 17.11.5.
 */

public interface SettingsMvpPresenter<V extends SettingsMvpView> extends MvpPresenter<V> {
    void saveApiUrl(String url);

    void saveNotificationsSettings(boolean enabled);

    String getApiUrl();

    boolean isNotificationsEnabled();

    long getUpdatePeriod();

    long getNotifyEventBeforePeriod();

    long getLecturesAheadPeriod();

    boolean isCommentNotificationsEnabled();

    void saveUpdatePeriod(long updatePeriod);

    void saveNotifyEventBeforePeriod(long notifyEventsBeforePeriod);

    void saveLecturesAheadPeriod(long lecturesAheadPeriod);

    void saveCommentNotificationsSettings(boolean commentNotificationsEnabled);

    void saveAutostartEnabled(boolean autostartEnabled);

    boolean getAutostartEnabled();
}
