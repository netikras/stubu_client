package com.netikras.studies.studentbuddy.api.client.android.pieces.lecture.ui.presenter;

import android.content.Context;

import com.netikras.studies.studentbuddy.api.client.android.pieces.base.MvpPresenter;
import com.netikras.studies.studentbuddy.api.client.android.pieces.lecture.ui.view.LectureMvpView;
import com.netikras.studies.studentbuddy.core.data.api.dto.school.LectureDto;

/**
 * Created by netikras on 17.11.10.
 */

public interface LectureMvpPresenter<V extends LectureMvpView> extends MvpPresenter<V> {
    void showLecture(LectureDto lecture, Context fromContext);
}
