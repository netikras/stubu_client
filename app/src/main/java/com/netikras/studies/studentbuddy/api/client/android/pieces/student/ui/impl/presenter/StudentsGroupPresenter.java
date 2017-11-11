package com.netikras.studies.studentbuddy.api.client.android.pieces.student.ui.impl.presenter;

import android.content.Context;

import com.netikras.studies.studentbuddy.api.client.android.data.DataManager;
import com.netikras.studies.studentbuddy.api.client.android.pieces.base.BasePresenter;
import com.netikras.studies.studentbuddy.api.client.android.pieces.student.ui.impl.view.StudentsGroupInfoActivity;
import com.netikras.studies.studentbuddy.api.client.android.pieces.student.ui.presenter.StudentsGroupMvpPresenter;
import com.netikras.studies.studentbuddy.api.client.android.pieces.student.ui.view.StudentsGroupMvpView;

/**
 * Created by netikras on 17.11.10.
 */

public class StudentsGroupPresenter<V extends StudentsGroupMvpView> extends BasePresenter<V> implements StudentsGroupMvpPresenter<V> {


    public StudentsGroupPresenter(DataManager dataManager) {
        super(dataManager);
    }

    private Object getDatastore() {
        return getDataManager().getStore(null); // FIXME
    }

    @Override
    public void startView(Context fromContext) {
        startView(fromContext, StudentsGroupInfoActivity.class);
    }
}
