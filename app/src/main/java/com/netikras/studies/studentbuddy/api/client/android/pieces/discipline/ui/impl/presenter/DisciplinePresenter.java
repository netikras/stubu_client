package com.netikras.studies.studentbuddy.api.client.android.pieces.discipline.ui.impl.presenter;

import android.content.Context;

import com.netikras.studies.studentbuddy.api.client.android.data.DataManager;
import com.netikras.studies.studentbuddy.api.client.android.data.stores.BaseDataStore;
import com.netikras.studies.studentbuddy.api.client.android.pieces.base.BaseActivity;
import com.netikras.studies.studentbuddy.api.client.android.pieces.base.BaseActivity.ViewTask;
import com.netikras.studies.studentbuddy.api.client.android.pieces.base.BasePresenter;
import com.netikras.studies.studentbuddy.api.client.android.pieces.discipline.data.DisciplineDataStore;
import com.netikras.studies.studentbuddy.api.client.android.pieces.discipline.ui.impl.view.DisciplineInfoActivity;
import com.netikras.studies.studentbuddy.api.client.android.pieces.discipline.ui.presenter.DisciplineMvpPresenter;
import com.netikras.studies.studentbuddy.api.client.android.pieces.discipline.ui.view.DisciplineMvpView;
import com.netikras.studies.studentbuddy.api.client.android.pieces.school.ui.presenter.SchoolMvpPresenter;
import com.netikras.studies.studentbuddy.api.client.android.pieces.school.ui.view.SchoolMvpView;
import com.netikras.studies.studentbuddy.core.data.api.dto.school.DisciplineDto;
import com.netikras.studies.studentbuddy.core.data.api.dto.school.SchoolDto;

import javax.inject.Inject;

/**
 * Created by netikras on 17.11.9.
 */

public class DisciplinePresenter<V extends DisciplineMvpView> extends BasePresenter<V> implements DisciplineMvpPresenter<V> {

    @Inject
    SchoolMvpPresenter<SchoolMvpView> schoolPresenter;

    @Inject
    public DisciplinePresenter(DataManager dataManager) {
        super(dataManager);
    }

    private DisciplineDataStore getDataStore() {
        return getDataManager().getStore(DisciplineDataStore.class);
    }

    @Override
    public void startView(Context fromContext) {
        startView(fromContext, DisciplineInfoActivity.class);
    }


    @Override
    public void show(Context context, DisciplineDto discipline) {
        startView(context, DisciplineInfoActivity.class, new ViewTask<DisciplineInfoActivity>() {
            @Override
            public void execute() {
                getActivity().show(discipline);
            }
        });
    }

    @Override
    public void showSchool(Context context, SchoolDto school) {
        schoolPresenter.showSchool(context, school);
    }
}
