package com.netikras.studies.studentbuddy.api.client.android.ui.lecture.impl.presenter;

import android.content.Context;
import android.content.Intent;

import com.netikras.studies.studentbuddy.api.client.android.data.DataManager;
import com.netikras.studies.studentbuddy.api.client.android.ui.base.BasePresenter;
import com.netikras.studies.studentbuddy.api.client.android.ui.lecture.impl.view.AssignmentActivity;
import com.netikras.studies.studentbuddy.api.client.android.ui.lecture.presenter.AssignmentMvpPresenter;
import com.netikras.studies.studentbuddy.api.client.android.ui.lecture.view.AssignmentMvpView;

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
