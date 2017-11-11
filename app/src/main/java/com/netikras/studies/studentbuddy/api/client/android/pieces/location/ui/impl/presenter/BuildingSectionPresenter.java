package com.netikras.studies.studentbuddy.api.client.android.pieces.location.ui.impl.presenter;

import android.content.Context;

import com.netikras.studies.studentbuddy.api.client.android.data.DataManager;
import com.netikras.studies.studentbuddy.api.client.android.pieces.base.BasePresenter;
import com.netikras.studies.studentbuddy.api.client.android.pieces.location.ui.impl.view.BuildingSectionActivity;
import com.netikras.studies.studentbuddy.api.client.android.pieces.location.ui.presenter.BuildingSectionMvpPresenter;
import com.netikras.studies.studentbuddy.api.client.android.pieces.location.ui.view.BuildingSectionMvpView;

import javax.inject.Inject;

/**
 * Created by netikras on 17.11.11.
 */

public class BuildingSectionPresenter<V extends BuildingSectionMvpView> extends BasePresenter<V> implements BuildingSectionMvpPresenter<V> {
    @Inject
    public BuildingSectionPresenter(DataManager dataManager) {
        super(dataManager);
    }

    private Object getDatastore() {
        return getDataManager().getStore(null); // FIXME
    }

    @Override
    public void startView(Context fromContext) {
        super.startView(fromContext, BuildingSectionActivity.class);
    }
}
