package com.netikras.studies.studentbuddy.api.client.android.ui.settings.presenter;

import com.netikras.studies.studentbuddy.api.client.android.ui.base.MvpPresenter;
import com.netikras.studies.studentbuddy.api.client.android.ui.settings.view.SettingsMvpView;

/**
 * Created by netikras on 17.11.5.
 */

public interface SettingsMvpPresenter<V extends SettingsMvpView> extends MvpPresenter<V> {
    void saveApiUrl(String url);

    void saveNotificationsSettings(boolean enabled);

    String getApiUrl();

    boolean isNotificationsEnabled();
}
