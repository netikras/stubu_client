package com.netikras.studies.studentbuddy.api.client.android.pieces.lecture.ui.view;

import com.netikras.studies.studentbuddy.api.client.android.pieces.base.MvpView;
import com.netikras.studies.studentbuddy.api.client.android.pieces.lecture.ui.impl.view.AssignmentActivity;
import com.netikras.studies.studentbuddy.core.data.api.dto.school.AssignmentDto;

/**
 * Created by netikras on 17.11.10.
 */

public interface AssignmentMvpView extends MvpView {

    AssignmentActivity.ViewFields getFields();

    void show(AssignmentDto dto);

    void show(AssignmentDto dto, boolean createNew);
}
