package com.netikras.studies.studentbuddy.api.client.android.pieces.lecture.ui.view;

import com.netikras.studies.studentbuddy.api.client.android.pieces.base.MvpView;
import com.netikras.studies.studentbuddy.core.data.api.dto.school.AssignmentDto;

/**
 * Created by netikras on 17.11.10.
 */

public interface AssignmentMvpView extends MvpView {

    void show(AssignmentDto dto);
}
