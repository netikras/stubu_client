package com.netikras.studies.studentbuddy.api.client.android.pieces.student.ui.impl.presenter;

import android.content.Context;

import com.netikras.studies.studentbuddy.api.client.android.data.DataManager;
import com.netikras.studies.studentbuddy.api.client.android.pieces.base.BaseActivity;
import com.netikras.studies.studentbuddy.api.client.android.pieces.base.BaseActivity.ViewTask;
import com.netikras.studies.studentbuddy.api.client.android.pieces.base.BasePresenter;
import com.netikras.studies.studentbuddy.api.client.android.pieces.person.ui.impl.presenter.PersonPresenter;
import com.netikras.studies.studentbuddy.api.client.android.pieces.person.ui.presenter.PersonMvpPresenter;
import com.netikras.studies.studentbuddy.api.client.android.pieces.person.ui.view.PersonMvpView;
import com.netikras.studies.studentbuddy.api.client.android.pieces.student.data.StudentDataStore;
import com.netikras.studies.studentbuddy.api.client.android.pieces.student.ui.impl.view.StudentInfoActivity;
import com.netikras.studies.studentbuddy.api.client.android.pieces.student.ui.presenter.StudentMvpPresenter;
import com.netikras.studies.studentbuddy.api.client.android.pieces.student.ui.view.StudentMvpView;
import com.netikras.studies.studentbuddy.core.data.api.dto.PersonDto;
import com.netikras.studies.studentbuddy.core.data.api.dto.school.StudentDto;

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
    public void startView(Context fromContext) {
        startView(fromContext, StudentInfoActivity.class);
    }

    @Override
    public void showPerson(Context context, PersonDto person) {
        personPresenter.showPerson(context, person);
    }

    @Override
    public void showStudent(Context context, StudentDto dto) {
        startView(context, StudentInfoActivity.class, new ViewTask<StudentInfoActivity>() {
            @Override
            public void execute() {
                getActivity().show(dto);
            }
        });
    }
}
