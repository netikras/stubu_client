package com.netikras.studies.studentbuddy.api.client.android.pieces.sys.ui.impl.presenter;

import android.content.Context;

import com.netikras.studies.studentbuddy.api.client.android.data.DataManager;
import com.netikras.studies.studentbuddy.api.client.android.pieces.base.BasePresenter;
import com.netikras.studies.studentbuddy.api.client.android.pieces.sys.ui.impl.view.SystemSettingsActivity;
import com.netikras.studies.studentbuddy.api.client.android.pieces.sys.ui.presenter.SystemSettingMvpPresenter;
import com.netikras.studies.studentbuddy.api.client.android.pieces.sys.ui.view.SystemSettingMvpView;

import javax.inject.Inject;

/**
 * Created by netikras on 17.11.11.
 */

public class SystemSettingPresenter<V extends SystemSettingMvpView> extends BasePresenter<V> implements SystemSettingMvpPresenter<V> {
    @Inject
    public SystemSettingPresenter(DataManager dataManager) {
        super(dataManager);
    }

    private Object getDatastore() {
        return getDataManager().getStore(null); // FIXME
    }

    @Override
    public void startView(Context fromContext) {
        super.startView(fromContext, SystemSettingsActivity.class);
    }
}
