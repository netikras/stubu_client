package com.netikras.studies.studentbuddy.api.client.android.pieces.discipline.ui.impl.presenter;

import android.content.Context;

import com.netikras.studies.studentbuddy.api.client.android.data.DataManager;
import com.netikras.studies.studentbuddy.api.client.android.pieces.base.BasePresenter;
import com.netikras.studies.studentbuddy.api.client.android.pieces.discipline.data.CourseDataStore;
import com.netikras.studies.studentbuddy.api.client.android.pieces.discipline.ui.impl.view.CourseActivity;
import com.netikras.studies.studentbuddy.api.client.android.pieces.discipline.ui.presenter.CourseMvpPresenter;
import com.netikras.studies.studentbuddy.api.client.android.pieces.discipline.ui.view.CourseMvpView;

import javax.inject.Inject;

/**
 * Created by netikras on 17.11.11.
 */

public class CoursePresenter<V extends CourseMvpView> extends BasePresenter<V> implements CourseMvpPresenter<V> {
    @Inject
    public CoursePresenter(DataManager dataManager) {
        super(dataManager);
    }

    private CourseDataStore getDataStore() {
        return getDataManager().getStore(CourseDataStore.class);
    }

    @Override
    public void startView(Context fromContext) {
        super.startView(fromContext, CourseActivity.class);
    }
}
