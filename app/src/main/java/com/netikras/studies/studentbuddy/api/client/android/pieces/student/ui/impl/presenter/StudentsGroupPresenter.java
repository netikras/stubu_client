package com.netikras.studies.studentbuddy.api.client.android.pieces.student.ui.impl.presenter;

import com.netikras.studies.studentbuddy.api.client.android.data.DataManager;
import com.netikras.studies.studentbuddy.api.client.android.pieces.base.BasePresenter;
import com.netikras.studies.studentbuddy.api.client.android.pieces.school.ui.presenter.SchoolMvpPresenter;
import com.netikras.studies.studentbuddy.api.client.android.pieces.school.ui.view.SchoolMvpView;
import com.netikras.studies.studentbuddy.api.client.android.pieces.student.data.StudentsGroupDataStore;
import com.netikras.studies.studentbuddy.api.client.android.pieces.student.ui.presenter.StudentsGroupMvpPresenter;
import com.netikras.studies.studentbuddy.api.client.android.pieces.student.ui.view.StudentsGroupMvpView;
import com.netikras.studies.studentbuddy.api.client.android.service.ServiceRequest;
import com.netikras.studies.studentbuddy.api.client.android.service.ServiceRequest.Subscriber;
import com.netikras.studies.studentbuddy.core.data.api.dto.school.StudentsGroupDto;

import javax.inject.Inject;

/**
 * Created by netikras on 17.11.10.
 */

public class StudentsGroupPresenter<V extends StudentsGroupMvpView> extends BasePresenter<V> implements StudentsGroupMvpPresenter<V> {

    @Inject
    SchoolMvpPresenter<SchoolMvpView> schoolPresenter;

    @Inject
    public StudentsGroupPresenter(DataManager dataManager) {
        super(dataManager);
    }

    private StudentsGroupDataStore getDataStore() {
        return getDataManager().getStore(StudentsGroupDataStore.class);
    }

    @Override
    public void getById(Subscriber<StudentsGroupDto> subscriber, String id) {
        getDataStore().getById(id, subscriber);
        getDataStore().processOrders(getContext());
    }
}
