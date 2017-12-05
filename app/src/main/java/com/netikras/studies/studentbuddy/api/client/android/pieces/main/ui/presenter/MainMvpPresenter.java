package com.netikras.studies.studentbuddy.api.client.android.pieces.main.ui.presenter;

import com.netikras.studies.studentbuddy.api.client.android.pieces.base.MvpPresenter;
import com.netikras.studies.studentbuddy.api.client.android.pieces.main.ui.view.MainMvpView;
import com.netikras.studies.studentbuddy.api.client.android.service.ServiceRequest;
import com.netikras.studies.studentbuddy.core.data.api.dto.PersonDto;
import com.netikras.studies.studentbuddy.core.data.api.dto.school.LectureDto;

import java.util.Collection;

/**
 * Created by netikras on 17.11.1.
 */

public interface MainMvpPresenter<V extends MainMvpView> extends MvpPresenter<V> {
    void fetchLecturesForStudent(ServiceRequest.Subscriber<Collection<LectureDto>> subscriber, PersonDto personDto);

    void fetchLecturesForLecturer(ServiceRequest.Subscriber<Collection<LectureDto>> subscriber, PersonDto personDto);

    void fetchLecturesForGuest(ServiceRequest.Subscriber<Collection<LectureDto>> subscriber, PersonDto personDto);
}
