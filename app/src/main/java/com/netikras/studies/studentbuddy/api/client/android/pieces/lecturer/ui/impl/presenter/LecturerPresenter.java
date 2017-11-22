package com.netikras.studies.studentbuddy.api.client.android.pieces.lecturer.ui.impl.presenter;

import android.content.Context;
import android.content.Intent;

import com.netikras.studies.studentbuddy.api.client.android.data.DataManager;
import com.netikras.studies.studentbuddy.api.client.android.pieces.base.BasePresenter;
import com.netikras.studies.studentbuddy.api.client.android.pieces.lecturer.data.LecturerDataStore;
import com.netikras.studies.studentbuddy.api.client.android.pieces.lecturer.ui.impl.view.LecturerInfoActivity;
import com.netikras.studies.studentbuddy.api.client.android.pieces.lecturer.ui.presenter.LecturerMvpPresenter;
import com.netikras.studies.studentbuddy.api.client.android.pieces.lecturer.ui.view.LecturerMvpView;

import javax.inject.Inject;

/**
 * Created by netikras on 17.11.10.
 */

public class LecturerPresenter<V extends LecturerMvpView> extends BasePresenter<V> implements LecturerMvpPresenter<V> {

    @Inject
    public LecturerPresenter(DataManager dataManager) {
        super(dataManager);
    }

    private LecturerDataStore getDataStore() {
        return getDataManager().getStore(LecturerDataStore.class);
    }

    @Override
    public void startView(Context fromContext) {
        Intent intent = new Intent(fromContext, LecturerInfoActivity.class);
        fromContext.startActivity(intent);
    }
}
