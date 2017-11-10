package com.netikras.studies.studentbuddy.api.client.android.ui.student.impl.presenter;

import android.content.Context;
import android.content.Intent;

import com.netikras.studies.studentbuddy.api.client.android.data.DataManager;
import com.netikras.studies.studentbuddy.api.client.android.ui.base.BasePresenter;
import com.netikras.studies.studentbuddy.api.client.android.ui.student.impl.view.StudentInfoActivity;
import com.netikras.studies.studentbuddy.api.client.android.ui.student.presenter.StudentMvpPresenter;
import com.netikras.studies.studentbuddy.api.client.android.ui.student.view.StudentMvpView;

import javax.inject.Inject;

/**
 * Created by netikras on 17.11.10.
 */

public class StudentPresenter<V extends StudentMvpView> extends BasePresenter<V> implements StudentMvpPresenter<V> {


    @Inject
    public StudentPresenter(DataManager dataManager) {
        super(dataManager);
    }

    private Object getDatastore() {
        return getDataManager().getStore(null); // FIXME
    }

    @Override
    public void startView(Context fromContext) {
        startView(fromContext, StudentInfoActivity.class);
    }
}
