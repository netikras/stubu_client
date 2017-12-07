package com.netikras.studies.studentbuddy.api.client.android.pieces.school.ui.impl.presenter;

import com.netikras.studies.studentbuddy.api.client.android.data.DataManager;
import com.netikras.studies.studentbuddy.api.client.android.pieces.base.BasePresenter;
import com.netikras.studies.studentbuddy.api.client.android.pieces.school.data.SchoolDepartmentDataStore;
import com.netikras.studies.studentbuddy.api.client.android.pieces.school.ui.presenter.SchoolDepartmentMvpPresenter;
import com.netikras.studies.studentbuddy.api.client.android.pieces.school.ui.view.SchoolDepartmentMvpView;
import com.netikras.studies.studentbuddy.api.client.android.service.ServiceRequest;
import com.netikras.studies.studentbuddy.api.client.android.service.ServiceRequest.Subscriber;
import com.netikras.studies.studentbuddy.core.data.api.dto.school.SchoolDepartmentDto;

import javax.inject.Inject;

/**
 * Created by netikras on 17.11.11.
 */

public class SchoolDepartmentPresenter<V extends SchoolDepartmentMvpView> extends BasePresenter<V> implements SchoolDepartmentMvpPresenter<V> {

    @Inject
    public SchoolDepartmentPresenter(DataManager dataManager) {
        super(dataManager);
    }

    private SchoolDepartmentDataStore getDataStore() {
        return getDataManager().getStore(SchoolDepartmentDataStore.class);
    }

    @Override
    public void getById(Subscriber<SchoolDepartmentDto> subscriber, String id) {
        getDataStore().getById(id, subscriber);
        getDataStore().processOrders(getContext());
    }
}
