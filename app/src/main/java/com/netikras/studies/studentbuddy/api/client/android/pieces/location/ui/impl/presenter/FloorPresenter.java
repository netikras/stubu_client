package com.netikras.studies.studentbuddy.api.client.android.pieces.location.ui.impl.presenter;

import android.content.Context;

import com.netikras.studies.studentbuddy.api.client.android.data.DataManager;
import com.netikras.studies.studentbuddy.api.client.android.pieces.base.BasePresenter;
import com.netikras.studies.studentbuddy.api.client.android.pieces.location.data.FloorDataStore;
import com.netikras.studies.studentbuddy.api.client.android.pieces.location.ui.impl.view.FloorActivity;
import com.netikras.studies.studentbuddy.api.client.android.pieces.location.ui.presenter.FloorMvpPresenter;
import com.netikras.studies.studentbuddy.api.client.android.pieces.location.ui.view.FloorMvpView;

import javax.inject.Inject;

/**
 * Created by netikras on 17.11.11.
 */

public class FloorPresenter<V extends FloorMvpView> extends BasePresenter<V> implements FloorMvpPresenter<V> {
    @Inject
    public FloorPresenter(DataManager dataManager) {
        super(dataManager);
    }

    private FloorDataStore getDataStore() {
        return getDataManager().getStore(FloorDataStore.class);
    }

    @Override
    public void startView(Context fromContext) {
        super.startView(fromContext, FloorActivity.class);
    }
}
