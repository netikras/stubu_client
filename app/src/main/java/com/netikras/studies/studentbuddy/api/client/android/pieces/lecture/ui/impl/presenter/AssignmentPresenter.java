package com.netikras.studies.studentbuddy.api.client.android.pieces.lecture.ui.impl.presenter;

import com.netikras.studies.studentbuddy.api.client.android.data.DataManager;
import com.netikras.studies.studentbuddy.api.client.android.pieces.base.BasePresenter;
import com.netikras.studies.studentbuddy.api.client.android.pieces.lecture.data.AssignmentDataStore;
import com.netikras.studies.studentbuddy.api.client.android.pieces.lecture.ui.presenter.AssignmentMvpPresenter;
import com.netikras.studies.studentbuddy.api.client.android.pieces.lecture.ui.view.AssignmentMvpView;
import com.netikras.studies.studentbuddy.api.client.android.service.ServiceRequest;
import com.netikras.studies.studentbuddy.api.client.android.service.ServiceRequest.Subscriber;
import com.netikras.studies.studentbuddy.core.data.api.dto.school.AssignmentDto;

import javax.inject.Inject;

/**
 * Created by netikras on 17.11.10.
 */

public class AssignmentPresenter<V extends AssignmentMvpView> extends BasePresenter<V> implements AssignmentMvpPresenter<V> {


    @Inject
    public AssignmentPresenter(DataManager dataManager) {
        super(dataManager);
    }

    private AssignmentDataStore getDataStore() {
        return getDataManager().getStore(AssignmentDataStore.class);
    }

    @Override
    public void getById(Subscriber<AssignmentDto> subscriber, String id) {
        getDataStore().getById(id, subscriber);
        getDataStore().processOrders(getContext());
    }

    @Override
    public void create(Subscriber<AssignmentDto> subscriber, AssignmentDto assignmentDto) {
        getDataStore().create(assignmentDto, subscriber);
        getDataStore().processOrders(getContext());
    }

    @Override
    public void update(Subscriber<AssignmentDto> subscriber, AssignmentDto assignmentDto) {
        getDataStore().update(assignmentDto, subscriber);
        getDataStore().processOrders(getContext());
    }

    @Override
    public void delete(Subscriber<Boolean> subscriber, String id) {
        getDataStore().delete(id, subscriber);
        getDataStore().processOrders(getContext());
    }


}
