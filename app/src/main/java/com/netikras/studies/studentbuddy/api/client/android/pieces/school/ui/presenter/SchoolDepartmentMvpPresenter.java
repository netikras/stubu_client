package com.netikras.studies.studentbuddy.api.client.android.pieces.school.ui.presenter;

import android.content.Context;

import com.netikras.studies.studentbuddy.api.client.android.pieces.base.MvpPresenter;
import com.netikras.studies.studentbuddy.api.client.android.pieces.school.ui.impl.view.SchoolDepartmentActivity;
import com.netikras.studies.studentbuddy.api.client.android.pieces.school.ui.view.SchoolDepartmentMvpView;
import com.netikras.studies.studentbuddy.core.data.api.dto.school.SchoolDepartmentDto;
import com.netikras.studies.studentbuddy.core.data.api.dto.school.SchoolDto;

/**
 * Created by netikras on 17.11.9.
 */

public interface SchoolDepartmentMvpPresenter<V extends SchoolDepartmentMvpView> extends MvpPresenter<V> {

    void showSchool(Context ctx, SchoolDto schoolDto);

    void showDepartment(Context context, SchoolDepartmentDto dto);
}
