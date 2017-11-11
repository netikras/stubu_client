package com.netikras.studies.studentbuddy.api.client.android.ui.school.impl.presenter;

import android.content.Context;

import com.netikras.studies.studentbuddy.api.client.android.data.DataManager;
import com.netikras.studies.studentbuddy.api.client.android.ui.base.BasePresenter;
import com.netikras.studies.studentbuddy.api.client.android.ui.school.impl.view.SchoolDepartmentActivity;
import com.netikras.studies.studentbuddy.api.client.android.ui.school.presenter.SchoolDepartmentMvpPresenter;
import com.netikras.studies.studentbuddy.api.client.android.ui.school.view.SchoolDepartmentMvpView;
import com.netikras.studies.studentbuddy.api.client.android.ui.sys.impl.view.AdminActivity;

import javax.inject.Inject;

/**
 * Created by netikras on 17.11.11.
 */

public class SchoolDepartmentPresenter<V extends SchoolDepartmentMvpView> extends BasePresenter<V> implements SchoolDepartmentMvpPresenter<V> {
    @Inject
    public SchoolDepartmentPresenter(DataManager dataManager) {
        super(dataManager);
    }

    private Object getDatastore() {
        return getDataManager().getStore(null); // FIXME
    }

    @Override
    public void startView(Context fromContext) {
        super.startView(fromContext, SchoolDepartmentActivity.class);
    }
}
