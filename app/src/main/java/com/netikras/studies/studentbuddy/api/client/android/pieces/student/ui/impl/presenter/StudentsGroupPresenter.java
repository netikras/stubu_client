package com.netikras.studies.studentbuddy.api.client.android.pieces.student.ui.impl.presenter;

import android.content.Context;

import com.netikras.studies.studentbuddy.api.client.android.data.DataManager;
import com.netikras.studies.studentbuddy.api.client.android.pieces.base.BaseActivity;
import com.netikras.studies.studentbuddy.api.client.android.pieces.base.BaseActivity.ViewTask;
import com.netikras.studies.studentbuddy.api.client.android.pieces.base.BasePresenter;
import com.netikras.studies.studentbuddy.api.client.android.pieces.school.ui.presenter.SchoolMvpPresenter;
import com.netikras.studies.studentbuddy.api.client.android.pieces.school.ui.view.SchoolMvpView;
import com.netikras.studies.studentbuddy.api.client.android.pieces.student.data.StudentsGroupDataStore;
import com.netikras.studies.studentbuddy.api.client.android.pieces.student.ui.impl.view.StudentsGroupInfoActivity;
import com.netikras.studies.studentbuddy.api.client.android.pieces.student.ui.presenter.StudentsGroupMvpPresenter;
import com.netikras.studies.studentbuddy.api.client.android.pieces.student.ui.view.StudentsGroupMvpView;
import com.netikras.studies.studentbuddy.core.data.api.dto.school.SchoolDto;
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
    public void startView(Context fromContext) {
        startView(fromContext, StudentsGroupInfoActivity.class);
    }

    @Override
    public void show(Context context, StudentsGroupDto studentsGroup) {
        startView(context, StudentsGroupInfoActivity.class, new ViewTask<StudentsGroupInfoActivity>() {
            @Override
            public void execute() {
                getActivity().show(studentsGroup);
            }
        });
    }

    @Override
    public void showSchool(Context context, SchoolDto school) {
        schoolPresenter.showSchool(context, school);
    }
}
