package com.netikras.studies.studentbuddy.api.client.android.ui.student.impl.presenter;

import android.content.Context;

import com.netikras.studies.studentbuddy.api.client.android.data.DataManager;
import com.netikras.studies.studentbuddy.api.client.android.ui.base.BasePresenter;
import com.netikras.studies.studentbuddy.api.client.android.ui.student.impl.view.StudentsGroupInfoActivity;
import com.netikras.studies.studentbuddy.api.client.android.ui.student.presenter.StudentsGroupMvpPresenter;
import com.netikras.studies.studentbuddy.api.client.android.ui.student.view.StudentsGroupMvpView;

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
