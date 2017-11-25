package com.netikras.studies.studentbuddy.api.client.android.pieces.school.ui.impl.presenter;

import android.content.Context;

import com.netikras.studies.studentbuddy.api.client.android.data.DataManager;
import com.netikras.studies.studentbuddy.api.client.android.pieces.base.BaseActivity;
import com.netikras.studies.studentbuddy.api.client.android.pieces.base.BaseActivity.ViewTask;
import com.netikras.studies.studentbuddy.api.client.android.pieces.base.BasePresenter;
import com.netikras.studies.studentbuddy.api.client.android.pieces.person.ui.impl.view.UserInfoActivity;
import com.netikras.studies.studentbuddy.api.client.android.pieces.school.data.SchoolDataStore;
import com.netikras.studies.studentbuddy.api.client.android.pieces.school.ui.impl.view.SchoolActivity;
import com.netikras.studies.studentbuddy.api.client.android.pieces.school.ui.presenter.SchoolDepartmentMvpPresenter;
import com.netikras.studies.studentbuddy.api.client.android.pieces.school.ui.presenter.SchoolMvpPresenter;
import com.netikras.studies.studentbuddy.api.client.android.pieces.school.ui.view.SchoolDepartmentMvpView;
import com.netikras.studies.studentbuddy.api.client.android.pieces.school.ui.view.SchoolMvpView;
import com.netikras.studies.studentbuddy.core.data.api.dto.school.SchoolDepartmentDto;
import com.netikras.studies.studentbuddy.core.data.api.dto.school.SchoolDto;

import javax.inject.Inject;

/**
 * Created by netikras on 17.11.11.
 */

public class SchoolPresenter<V extends SchoolMvpView> extends BasePresenter<V> implements SchoolMvpPresenter<V> {

    @Inject
    SchoolDepartmentMvpPresenter<SchoolDepartmentMvpView> departmentPresenter;

    @Inject
    public SchoolPresenter(DataManager dataManager) {
        super(dataManager);
    }

    private SchoolDataStore getDataStore() {
        return getDataManager().getStore(SchoolDataStore.class);
    }

    @Override
    public void startView(Context fromContext) {
        super.startView(fromContext, SchoolActivity.class);
    }


    @Override
    public void showSchool(Context context, SchoolDto schoolDto) {
        startView(context, SchoolActivity.class, new ViewTask<SchoolActivity>() {
            @Override
            public void execute() {
                getActivity().show(schoolDto);
            }
        });
    }

    @Override
    public void showDepartment(Context listContext, SchoolDepartmentDto item) {
        departmentPresenter.showDepartment(listContext, item);
    }
}
