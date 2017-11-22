package com.netikras.studies.studentbuddy.api.client.android.pieces.school.ui.impl.presenter;

import android.content.Context;

import com.netikras.studies.studentbuddy.api.client.android.data.DataManager;
import com.netikras.studies.studentbuddy.api.client.android.pieces.base.BaseActivity;
import com.netikras.studies.studentbuddy.api.client.android.pieces.base.BaseActivity.ViewTask;
import com.netikras.studies.studentbuddy.api.client.android.pieces.base.BasePresenter;
import com.netikras.studies.studentbuddy.api.client.android.pieces.person.ui.impl.view.UserInfoActivity;
import com.netikras.studies.studentbuddy.api.client.android.pieces.school.data.SchoolDepartmentDataStore;
import com.netikras.studies.studentbuddy.api.client.android.pieces.school.ui.impl.view.SchoolDepartmentActivity;
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

public class SchoolDepartmentPresenter<V extends SchoolDepartmentMvpView> extends BasePresenter<V> implements SchoolDepartmentMvpPresenter<V> {

    @Inject
    SchoolMvpPresenter<SchoolMvpView> schoolPresenter;

    @Inject
    public SchoolDepartmentPresenter(DataManager dataManager) {
        super(dataManager);
    }

    private SchoolDepartmentDataStore getDataStore() {
        return getDataManager().getStore(SchoolDepartmentDataStore.class);
    }

    @Override
    public void startView(Context fromContext) {
        super.startView(fromContext, SchoolDepartmentActivity.class);
    }

    @Override
    public void showSchool(Context context, SchoolDto schoolDto) {
        schoolPresenter.showSchool(context, schoolDto);
    }

    @Override
    public void showDepartment(Context context, SchoolDepartmentDto dto) {
        startView(context, SchoolDepartmentActivity.class, new ViewTask<SchoolDepartmentActivity>() {
            @Override
            public void execute() {
                getActivity().show(dto);
            }
        });
    }
}
