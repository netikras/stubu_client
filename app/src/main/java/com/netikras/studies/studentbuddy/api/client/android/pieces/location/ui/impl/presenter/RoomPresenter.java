package com.netikras.studies.studentbuddy.api.client.android.pieces.location.ui.impl.presenter;

import android.content.Context;

import com.netikras.studies.studentbuddy.api.client.android.data.DataManager;
import com.netikras.studies.studentbuddy.api.client.android.pieces.base.BasePresenter;
import com.netikras.studies.studentbuddy.api.client.android.pieces.location.data.RoomDataStore;
import com.netikras.studies.studentbuddy.api.client.android.pieces.location.ui.impl.view.RoomActivity;
import com.netikras.studies.studentbuddy.api.client.android.pieces.location.ui.presenter.RoomMvpPresenter;
import com.netikras.studies.studentbuddy.api.client.android.pieces.location.ui.view.RoomMvpView;

import javax.inject.Inject;

/**
 * Created by netikras on 17.11.11.
 */

public class RoomPresenter<V extends RoomMvpView> extends BasePresenter<V> implements RoomMvpPresenter<V> {
    @Inject
    public RoomPresenter(DataManager dataManager) {
        super(dataManager);
    }

    private RoomDataStore getDataStore() {
        return getDataManager().getStore(RoomDataStore.class);
    }

    @Override
    public void startView(Context fromContext) {
        super.startView(fromContext, RoomActivity.class);
    }
}
