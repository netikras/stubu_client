package com.netikras.studies.studentbuddy.api.client.android.pieces.student.ui.presenter;

import android.content.Context;

import com.netikras.studies.studentbuddy.api.client.android.pieces.base.MvpPresenter;
import com.netikras.studies.studentbuddy.api.client.android.pieces.student.ui.view.StudentMvpView;
import com.netikras.studies.studentbuddy.core.data.api.dto.PersonDto;
import com.netikras.studies.studentbuddy.core.data.api.dto.school.StudentDto;

/**
 * Created by netikras on 17.11.10.
 */

public interface StudentMvpPresenter<V extends StudentMvpView> extends MvpPresenter<V> {
    void showPerson(Context context, PersonDto person);

    void showStudent(Context context, StudentDto dto);
}
