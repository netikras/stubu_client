package com.netikras.studies.studentbuddy.api.client.android.pieces.lecturer.ui.view;

import com.netikras.studies.studentbuddy.api.client.android.pieces.base.MvpView;
import com.netikras.studies.studentbuddy.api.client.android.pieces.lecturer.ui.impl.view.LecturerInfoActivity;
import com.netikras.studies.studentbuddy.core.data.api.dto.school.LecturerDto;

/**
 * Created by netikras on 17.11.10.
 */

public interface LecturerMvpView extends MvpView {
    LecturerInfoActivity.ViewFields getFields();

    void show(LecturerDto lecturer);
}
