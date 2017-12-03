package com.netikras.studies.studentbuddy.api.client.android.pieces.discipline.ui.impl.presenter;

import com.netikras.studies.studentbuddy.api.client.android.data.DataManager;
import com.netikras.studies.studentbuddy.api.client.android.pieces.base.BasePresenter;
import com.netikras.studies.studentbuddy.api.client.android.pieces.discipline.data.DisciplineDataStore;
import com.netikras.studies.studentbuddy.api.client.android.pieces.discipline.ui.presenter.DisciplineMvpPresenter;
import com.netikras.studies.studentbuddy.api.client.android.pieces.discipline.ui.view.DisciplineMvpView;
import com.netikras.studies.studentbuddy.api.client.android.pieces.school.ui.presenter.SchoolMvpPresenter;
import com.netikras.studies.studentbuddy.api.client.android.pieces.school.ui.view.SchoolMvpView;

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

}
