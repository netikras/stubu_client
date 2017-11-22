package com.netikras.studies.studentbuddy.api.client.android.pieces.student.ui.view;

import com.netikras.studies.studentbuddy.api.client.android.pieces.base.MvpView;
import com.netikras.studies.studentbuddy.api.client.android.pieces.student.ui.impl.view.StudentInfoActivity;
import com.netikras.studies.studentbuddy.core.data.api.dto.school.StudentDto;

/**
 * Created by netikras on 17.11.10.
 */

public interface StudentMvpView extends MvpView {
    StudentInfoActivity.ViewFields getFields();

    void show(StudentDto studentDto);
}
