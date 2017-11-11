package com.netikras.studies.studentbuddy.api.client.android.ui.sys.impl.presenter;

import android.content.Context;

import com.netikras.studies.studentbuddy.api.client.android.data.DataManager;
import com.netikras.studies.studentbuddy.api.client.android.ui.base.BasePresenter;
import com.netikras.studies.studentbuddy.api.client.android.ui.sys.impl.view.AdminActivity;
import com.netikras.studies.studentbuddy.api.client.android.ui.sys.impl.view.StatusActivity;
import com.netikras.studies.studentbuddy.api.client.android.ui.sys.presenter.StatusMvpPresenter;
import com.netikras.studies.studentbuddy.api.client.android.ui.sys.view.StatusMvpView;

import javax.inject.Inject;

/**
 * Created by netikras on 17.11.11.
 */

public class StatusPresenter<V extends StatusMvpView> extends BasePresenter<V> implements StatusMvpPresenter<V> {
    @Inject
    public StatusPresenter(DataManager dataManager) {
        super(dataManager);
    }

    private Object getDatastore() {
        return getDataManager().getStore(null); // FIXME
    }

    @Override
    public void startView(Context fromContext) {
        super.startView(fromContext, StatusActivity.class);
    }
}