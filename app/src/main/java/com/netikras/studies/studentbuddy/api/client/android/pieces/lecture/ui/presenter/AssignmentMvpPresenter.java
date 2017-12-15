package com.netikras.studies.studentbuddy.api.client.android.pieces.lecture.ui.presenter;

import com.netikras.studies.studentbuddy.api.client.android.pieces.base.MvpPresenter;
import com.netikras.studies.studentbuddy.api.client.android.pieces.lecture.ui.view.AssignmentMvpView;
import com.netikras.studies.studentbuddy.api.client.android.service.ServiceRequest.Subscriber;
import com.netikras.studies.studentbuddy.core.data.api.dto.school.AssignmentDto;

/**
 * Created by netikras on 17.11.10.
 */

public interface AssignmentMvpPresenter<V extends AssignmentMvpView> extends MvpPresenter<V> {

    void getById(Subscriber<AssignmentDto> subscriber, String id);

    void create(Subscriber<AssignmentDto> subscriber, AssignmentDto assignmentDto);

    void update(Subscriber<AssignmentDto> subscriber, AssignmentDto assignmentDto);
}
