package com.netikras.studies.studentbuddy.api.client.android.pieces.sys.ui.impl.presenter;

import android.content.Context;

import com.netikras.studies.studentbuddy.api.client.android.data.DataManager;
import com.netikras.studies.studentbuddy.api.client.android.pieces.base.BasePresenter;
import com.netikras.studies.studentbuddy.api.client.android.pieces.sys.data.StatusDataStore;
import com.netikras.studies.studentbuddy.api.client.android.pieces.sys.ui.impl.view.StatusActivity;
import com.netikras.studies.studentbuddy.api.client.android.pieces.sys.ui.presenter.StatusMvpPresenter;
import com.netikras.studies.studentbuddy.api.client.android.pieces.sys.ui.view.StatusMvpView;

import javax.inject.Inject;

/**
 * Created by netikras on 17.11.11.
 */

public class StatusPresenter<V extends StatusMvpView> extends BasePresenter<V> implements StatusMvpPresenter<V> {
    @Inject
    public StatusPresenter(DataManager dataManager) {
        super(dataManager);
    }

    private StatusDataStore getDataStore() {
        return getDataManager().getStore(StatusDataStore.class);
    }

    @Override
    public void startView(Context fromContext) {
        super.startView(fromContext, StatusActivity.class);
    }
}
