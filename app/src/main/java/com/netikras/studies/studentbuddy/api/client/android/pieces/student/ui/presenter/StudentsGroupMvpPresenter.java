package com.netikras.studies.studentbuddy.api.client.android.pieces.student.ui.presenter;

import android.content.Context;

import com.netikras.studies.studentbuddy.api.client.android.pieces.base.MvpPresenter;
import com.netikras.studies.studentbuddy.api.client.android.pieces.student.ui.impl.view.StudentsGroupInfoActivity;
import com.netikras.studies.studentbuddy.api.client.android.pieces.student.ui.view.StudentsGroupMvpView;
import com.netikras.studies.studentbuddy.core.data.api.dto.school.SchoolDto;
import com.netikras.studies.studentbuddy.core.data.api.dto.school.StudentsGroupDto;

/**
 * Created by netikras on 17.11.10.
 */

public interface StudentsGroupMvpPresenter<V extends StudentsGroupMvpView> extends MvpPresenter<V> {

    void show(Context context, StudentsGroupDto studentsGroup);

    void showSchool(Context context, SchoolDto school);
}
