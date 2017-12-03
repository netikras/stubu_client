package com.netikras.studies.studentbuddy.api.client.android.pieces.school.ui.impl.presenter;

import com.netikras.studies.studentbuddy.api.client.android.data.DataManager;
import com.netikras.studies.studentbuddy.api.client.android.pieces.base.BasePresenter;
import com.netikras.studies.studentbuddy.api.client.android.pieces.school.data.SchoolDataStore;
import com.netikras.studies.studentbuddy.api.client.android.pieces.school.ui.presenter.SchoolMvpPresenter;
import com.netikras.studies.studentbuddy.api.client.android.pieces.school.ui.view.SchoolMvpView;

import javax.inject.Inject;

/**
 * Created by netikras on 17.11.11.
 */

public class SchoolPresenter<V extends SchoolMvpView> extends BasePresenter<V> implements SchoolMvpPresenter<V> {

    @Inject
    public SchoolPresenter(DataManager dataManager) {
        super(dataManager);
    }

    private SchoolDataStore getDataStore() {
        return getDataManager().getStore(SchoolDataStore.class);
    }

}
