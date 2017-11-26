package com.netikras.studies.studentbuddy.api.client.android.pieces.student.ui.view;

import com.netikras.studies.studentbuddy.api.client.android.pieces.base.MvpView;
import com.netikras.studies.studentbuddy.core.data.api.dto.school.LectureGuestDto;

/**
 * Created by netikras on 17.11.11.
 */

public interface GuestMvpView extends MvpView {
    void show(LectureGuestDto item);
}
