package com.netikras.studies.studentbuddy.api.client.android.pieces.lecture.ui.impl.presenter;

import android.content.Context;

import com.netikras.studies.studentbuddy.api.client.android.data.DataManager;
import com.netikras.studies.studentbuddy.api.client.android.pieces.base.BasePresenter;
import com.netikras.studies.studentbuddy.api.client.android.pieces.lecture.ui.impl.view.LectureInfoActivity;
import com.netikras.studies.studentbuddy.api.client.android.pieces.lecture.ui.presenter.LectureMvpPresenter;
import com.netikras.studies.studentbuddy.api.client.android.pieces.lecture.ui.view.LectureMvpView;

import javax.inject.Inject;

/**
 * Created by netikras on 17.11.10.
 */

public class LecturePresenter<V extends LectureMvpView> extends BasePresenter<V> implements LectureMvpPresenter<V> {


    @Inject
    public LecturePresenter(DataManager dataManager) {
        super(dataManager);
    }

    private Object getDatastore() {
        return getDataManager().getStore(null); // FIXME
    }

    @Override
    public void startView(Context fromContext) {
        startView(fromContext, LectureInfoActivity.class);
    }
}