package com.netikras.studies.studentbuddy.api.client.android.pieces.location.ui.impl.presenter;

import android.content.Context;

import com.netikras.studies.studentbuddy.api.client.android.data.DataManager;
import com.netikras.studies.studentbuddy.api.client.android.pieces.base.BasePresenter;
import com.netikras.studies.studentbuddy.api.client.android.pieces.location.data.LayoutDataStore;
import com.netikras.studies.studentbuddy.api.client.android.pieces.location.ui.impl.view.LayoutActivity;
import com.netikras.studies.studentbuddy.api.client.android.pieces.location.ui.presenter.LayoutMvpPresenter;
import com.netikras.studies.studentbuddy.api.client.android.pieces.location.ui.view.LayoutMvpView;

import javax.inject.Inject;

/**
 * Created by netikras on 17.11.11.
 */

public class LayoutPresenter<V extends LayoutMvpView> extends BasePresenter<V> implements LayoutMvpPresenter<V> {
    @Inject
    public LayoutPresenter(DataManager dataManager) {
        super(dataManager);
    }

    private LayoutDataStore getDataStore() {
        return getDataManager().getStore(LayoutDataStore.class);
    }

    @Override
    public void startView(Context fromContext) {
        super.startView(fromContext, LayoutActivity.class);
    }
}
