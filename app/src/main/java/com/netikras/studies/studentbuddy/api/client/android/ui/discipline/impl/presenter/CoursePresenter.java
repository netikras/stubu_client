package com.netikras.studies.studentbuddy.api.client.android.ui.discipline.impl.presenter;

import android.content.Context;

import com.netikras.studies.studentbuddy.api.client.android.data.DataManager;
import com.netikras.studies.studentbuddy.api.client.android.ui.base.BasePresenter;
import com.netikras.studies.studentbuddy.api.client.android.ui.discipline.impl.view.CourseActivity;
import com.netikras.studies.studentbuddy.api.client.android.ui.discipline.presenter.CourseMvpPresenter;
import com.netikras.studies.studentbuddy.api.client.android.ui.discipline.view.CourseMvpView;
import com.netikras.studies.studentbuddy.api.client.android.ui.sys.impl.view.AdminActivity;

import javax.inject.Inject;

/**
 * Created by netikras on 17.11.11.
 */

public class CoursePresenter<V extends CourseMvpView> extends BasePresenter<V> implements CourseMvpPresenter<V> {
    @Inject
    public CoursePresenter(DataManager dataManager) {
        super(dataManager);
    }

    private Object getDatastore() {
        return getDataManager().getStore(null); // FIXME
    }

    @Override
    public void startView(Context fromContext) {
        super.startView(fromContext, CourseActivity.class);
    }
}
