package com.netikras.studies.studentbuddy.api.client.android.pieces.lecture.ui.view;

import com.netikras.studies.studentbuddy.api.client.android.pieces.base.MvpView;
import com.netikras.studies.studentbuddy.api.client.android.pieces.lecture.ui.impl.view.LectureInfoActivity;
import com.netikras.studies.studentbuddy.core.data.api.dto.school.LectureDto;

/**
 * Created by netikras on 17.11.10.
 */

public interface LectureMvpView extends MvpView {
    LectureInfoActivity.ViewFields getFields();

    void show(LectureDto lectureDto);
}
