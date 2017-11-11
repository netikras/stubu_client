package com.netikras.studies.studentbuddy.api.client.android.ui.school.impl.presenter;

import android.content.Context;

import com.netikras.studies.studentbuddy.api.client.android.data.DataManager;
import com.netikras.studies.studentbuddy.api.client.android.ui.base.BasePresenter;
import com.netikras.studies.studentbuddy.api.client.android.ui.school.impl.view.SchoolActivity;
import com.netikras.studies.studentbuddy.api.client.android.ui.school.presenter.SchoolMvpPresenter;
import com.netikras.studies.studentbuddy.api.client.android.ui.school.view.SchoolMvpView;
import com.netikras.studies.studentbuddy.api.client.android.ui.sys.impl.view.AdminActivity;

import javax.inject.Inject;

/**
 * Created by netikras on 17.11.11.
 */

public class SchoolPresenter<V extends SchoolMvpView> extends BasePresenter<V> implements SchoolMvpPresenter<V> {
    @Inject
    public SchoolPresenter(DataManager dataManager) {
        super(dataManager);
    }

    private Object getDatastore() {
        return getDataManager().getStore(null); // FIXME
    }

    @Override
    public void startView(Context fromContext) {
        super.startView(fromContext, SchoolActivity.class);
    }
}
