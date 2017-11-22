package com.netikras.studies.studentbuddy.api.client.android.pieces.sys.ui.impl.presenter;

import android.content.Context;

import com.netikras.studies.studentbuddy.api.client.android.data.DataManager;
import com.netikras.studies.studentbuddy.api.client.android.pieces.base.BasePresenter;
import com.netikras.studies.studentbuddy.api.client.android.pieces.location.data.RoomDataStore;
import com.netikras.studies.studentbuddy.api.client.android.pieces.sys.data.RoleDataStore;
import com.netikras.studies.studentbuddy.api.client.android.pieces.sys.ui.impl.view.RoleActivity;
import com.netikras.studies.studentbuddy.api.client.android.pieces.sys.ui.presenter.RoleMvpPresenter;
import com.netikras.studies.studentbuddy.api.client.android.pieces.sys.ui.view.RoleMvpView;

import javax.inject.Inject;

/**
 * Created by netikras on 17.11.11.
 */

public class RolePresenter<V extends RoleMvpView> extends BasePresenter<V> implements RoleMvpPresenter<V> {
    @Inject
    public RolePresenter(DataManager dataManager) {
        super(dataManager);
    }

    private RoleDataStore getDataStore() {
        return getDataManager().getStore(RoleDataStore.class);
    }

    @Override
    public void startView(Context fromContext) {
        super.startView(fromContext, RoleActivity.class);
    }
}
