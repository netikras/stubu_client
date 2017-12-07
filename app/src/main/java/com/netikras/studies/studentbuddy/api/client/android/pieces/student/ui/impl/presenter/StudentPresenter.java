package com.netikras.studies.studentbuddy.api.client.android.pieces.student.ui.impl.presenter;

import com.netikras.studies.studentbuddy.api.client.android.data.DataManager;
import com.netikras.studies.studentbuddy.api.client.android.pieces.base.BasePresenter;
import com.netikras.studies.studentbuddy.api.client.android.pieces.person.ui.presenter.PersonMvpPresenter;
import com.netikras.studies.studentbuddy.api.client.android.pieces.person.ui.view.PersonMvpView;
import com.netikras.studies.studentbuddy.api.client.android.pieces.student.data.StudentDataStore;
import com.netikras.studies.studentbuddy.api.client.android.pieces.student.ui.presenter.StudentMvpPresenter;
import com.netikras.studies.studentbuddy.api.client.android.pieces.student.ui.view.StudentMvpView;
import com.netikras.studies.studentbuddy.api.client.android.service.ServiceRequest;
import com.netikras.studies.studentbuddy.api.client.android.service.ServiceRequest.Subscriber;
import com.netikras.studies.studentbuddy.core.data.api.dto.school.StudentDto;

import java.util.concurrent.Future;

import javax.inject.Inject;

/**
 * Created by netikras on 17.11.10.
 */

public class StudentPresenter<V extends StudentMvpView> extends BasePresenter<V> implements StudentMvpPresenter<V> {

    @Inject
    PersonMvpPresenter<PersonMvpView> personPresenter;

    @Inject
    public StudentPresenter(DataManager dataManager) {
        super(dataManager);
    }

    private StudentDataStore getDataStore() {
        return getDataManager().getStore(StudentDataStore.class);
    }

    @Override
    public void getById(Subscriber<StudentDto> subscriber, String id) {
        getDataStore().getById(id, subscriber);
        getDataStore().processOrders(getContext());
    }
}
