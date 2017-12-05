package com.netikras.studies.studentbuddy.api.client.android.pieces.student.ui.view;

import com.netikras.studies.studentbuddy.api.client.android.pieces.base.MvpView;
import com.netikras.studies.studentbuddy.core.data.api.dto.school.StudentsGroupDto;

/**
 * Created by netikras on 17.11.10.
 */

public interface StudentsGroupMvpView extends MvpView {
    void show(StudentsGroupDto dto);
}
