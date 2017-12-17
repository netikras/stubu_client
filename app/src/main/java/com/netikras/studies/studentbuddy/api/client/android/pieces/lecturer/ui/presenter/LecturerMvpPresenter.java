package com.netikras.studies.studentbuddy.api.client.android.pieces.lecturer.ui.presenter;

import com.netikras.studies.studentbuddy.api.client.android.pieces.base.MvpPresenter;
import com.netikras.studies.studentbuddy.api.client.android.pieces.lecturer.ui.view.LecturerMvpView;
import com.netikras.studies.studentbuddy.api.client.android.service.ServiceRequest.Subscriber;
import com.netikras.studies.studentbuddy.core.data.api.dto.school.LectureDto;
import com.netikras.studies.studentbuddy.core.data.api.dto.school.LecturerDto;

import java.util.Collection;
import java.util.List;

/**
 * Created by netikras on 17.11.10.
 */

public interface LecturerMvpPresenter<V extends LecturerMvpView> extends MvpPresenter<V> {

    void getById(Subscriber<LecturerDto> subscriber, String id);

    void update(Subscriber<LecturerDto> subscriber, LecturerDto dto);

    void getUpcomingLectures(Subscriber<Collection<LectureDto>> subscriber, String id);
}
