package com.netikras.studies.studentbuddy.api.client.android.pieces.student.ui.impl.presenter;

import com.netikras.studies.studentbuddy.api.client.android.data.DataManager;
import com.netikras.studies.studentbuddy.api.client.android.pieces.base.BasePresenter;
import com.netikras.studies.studentbuddy.api.client.android.pieces.person.ui.presenter.PersonMvpPresenter;
import com.netikras.studies.studentbuddy.api.client.android.pieces.person.ui.view.PersonMvpView;
import com.netikras.studies.studentbuddy.api.client.android.pieces.student.data.StudentDataStore;
import com.netikras.studies.studentbuddy.api.client.android.pieces.student.ui.presenter.StudentMvpPresenter;
import com.netikras.studies.studentbuddy.api.client.android.pieces.student.ui.view.StudentMvpView;

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
}
