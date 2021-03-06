package com.netikras.studies.studentbuddy.api.client.android.pieces.main.ui.presenter;

import com.netikras.studies.studentbuddy.api.client.android.pieces.base.MvpPresenter;
import com.netikras.studies.studentbuddy.api.client.android.pieces.main.ui.view.MainMvpView;
import com.netikras.studies.studentbuddy.api.client.android.service.ServiceRequest.Subscriber;
import com.netikras.studies.studentbuddy.core.data.api.dto.PersonDto;
import com.netikras.studies.studentbuddy.core.data.api.dto.meta.UserDto;
import com.netikras.studies.studentbuddy.core.data.api.dto.school.LectureDto;

import java.util.Collection;

/**
 * Created by netikras on 17.11.1.
 */

public interface MainMvpPresenter<V extends MainMvpView> extends MvpPresenter<V> {
    void fetchLecturesForStudent(Subscriber<Collection<LectureDto>> subscriber, PersonDto personDto);

    void fetchLecturesForLecturer(Subscriber<Collection<LectureDto>> subscriber, PersonDto personDto);

    void fetchLecturesForGuest(Subscriber<Collection<LectureDto>> subscriber, PersonDto personDto);

    void getCurrentUser(Subscriber<UserDto> errorsAwareSubscriber);
}
