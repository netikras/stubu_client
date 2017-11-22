package com.netikras.studies.studentbuddy.api.client.android.pieces.lecture.ui.presenter;

import com.netikras.studies.studentbuddy.api.client.android.pieces.base.MvpPresenter;
import com.netikras.studies.studentbuddy.api.client.android.pieces.lecture.ui.view.TestMvpView;
import com.netikras.studies.studentbuddy.core.data.api.dto.school.LectureDto;

/**
 * Created by netikras on 17.11.10.
 */

public interface TestMvpPresenter<V extends TestMvpView> extends MvpPresenter<V> {
    void showLecture(LectureDto lecture);
}
