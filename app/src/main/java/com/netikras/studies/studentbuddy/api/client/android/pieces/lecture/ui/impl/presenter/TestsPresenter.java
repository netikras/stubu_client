package com.netikras.studies.studentbuddy.api.client.android.pieces.lecture.ui.impl.presenter;

import android.content.Context;

import com.netikras.studies.studentbuddy.api.client.android.data.DataManager;
import com.netikras.studies.studentbuddy.api.client.android.pieces.base.BasePresenter;
import com.netikras.studies.studentbuddy.api.client.android.pieces.lecture.ui.impl.view.TestInfoActivity;
import com.netikras.studies.studentbuddy.api.client.android.pieces.lecture.ui.presenter.TestMvpPresenter;
import com.netikras.studies.studentbuddy.api.client.android.pieces.lecture.ui.view.TestMvpView;

import javax.inject.Inject;

/**
 * Created by netikras on 17.11.10.
 */

public class TestsPresenter<V extends TestMvpView> extends BasePresenter<V> implements TestMvpPresenter<V> {


    @Inject
    public TestsPresenter(DataManager dataManager) {
        super(dataManager);
    }

    private Object getDatastore() {
        return getDataManager().getStore(null); // FIXME
    }

    @Override
    public void startView(Context fromContext) {
        startView(fromContext, TestInfoActivity.class);
    }
}
