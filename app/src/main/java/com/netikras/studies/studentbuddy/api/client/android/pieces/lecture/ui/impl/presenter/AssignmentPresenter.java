package com.netikras.studies.studentbuddy.api.client.android.pieces.lecture.ui.impl.presenter;

import android.content.Context;

import com.netikras.studies.studentbuddy.api.client.android.data.DataManager;
import com.netikras.studies.studentbuddy.api.client.android.pieces.base.BasePresenter;
import com.netikras.studies.studentbuddy.api.client.android.pieces.lecture.ui.impl.view.AssignmentActivity;
import com.netikras.studies.studentbuddy.api.client.android.pieces.lecture.ui.presenter.AssignmentMvpPresenter;
import com.netikras.studies.studentbuddy.api.client.android.pieces.lecture.ui.view.AssignmentMvpView;

import javax.inject.Inject;

/**
 * Created by netikras on 17.11.10.
 */

public class AssignmentPresenter<V extends AssignmentMvpView> extends BasePresenter<V> implements AssignmentMvpPresenter<V> {


    @Inject
    public AssignmentPresenter(DataManager dataManager) {
        super(dataManager);
    }

    private Object getDatastore() {
        return getDataManager().getStore(null); // FIXME
    }

    @Override
    public void startView(Context fromContext) {
        startView(fromContext, AssignmentActivity.class);
    }
}
