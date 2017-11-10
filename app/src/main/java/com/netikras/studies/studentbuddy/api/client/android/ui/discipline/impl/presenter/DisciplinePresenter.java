package com.netikras.studies.studentbuddy.api.client.android.ui.discipline.impl.presenter;

import android.content.Context;
import android.content.Intent;

import com.netikras.studies.studentbuddy.api.client.android.data.DataManager;
import com.netikras.studies.studentbuddy.api.client.android.data.stores.BaseDataStore;
import com.netikras.studies.studentbuddy.api.client.android.ui.base.BasePresenter;
import com.netikras.studies.studentbuddy.api.client.android.ui.discipline.impl.view.DisciplineInfoActivity;
import com.netikras.studies.studentbuddy.api.client.android.ui.discipline.presenter.DisciplineMvpPresenter;
import com.netikras.studies.studentbuddy.api.client.android.ui.discipline.view.DisciplineMvpView;

import javax.inject.Inject;

/**
 * Created by netikras on 17.11.9.
 */

public class DisciplinePresenter<V extends DisciplineMvpView> extends BasePresenter<V> implements DisciplineMvpPresenter<V> {


    @Inject
    public DisciplinePresenter(DataManager dataManager) {
        super(dataManager);
    }

    private <T extends BaseDataStore> T getDataStore() {
        return null; // FIXME
    }

    @Override
    public void startView(Context fromContext) {
        startView(fromContext, DisciplineInfoActivity.class);
    }




}
