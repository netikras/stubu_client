package com.netikras.studies.studentbuddy.api.client.android.ui.location.impl.presenter;

import android.content.Context;

import com.netikras.studies.studentbuddy.api.client.android.data.DataManager;
import com.netikras.studies.studentbuddy.api.client.android.ui.base.BasePresenter;
import com.netikras.studies.studentbuddy.api.client.android.ui.location.impl.view.FloorActivity;
import com.netikras.studies.studentbuddy.api.client.android.ui.location.presenter.FloorMvpPresenter;
import com.netikras.studies.studentbuddy.api.client.android.ui.location.view.FloorMvpView;

import javax.inject.Inject;

/**
 * Created by netikras on 17.11.11.
 */

public class FloorPresenter<V extends FloorMvpView> extends BasePresenter<V> implements FloorMvpPresenter<V> {
    @Inject
    public FloorPresenter(DataManager dataManager) {
        super(dataManager);
    }

    private Object getDatastore() {
        return getDataManager().getStore(null); // FIXME
    }

    @Override
    public void startView(Context fromContext) {
        super.startView(fromContext, FloorActivity.class);
    }
}