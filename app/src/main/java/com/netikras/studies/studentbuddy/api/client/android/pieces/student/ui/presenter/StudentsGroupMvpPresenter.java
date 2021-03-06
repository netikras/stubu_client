package com.netikras.studies.studentbuddy.api.client.android.pieces.student.ui.presenter;

import com.netikras.studies.studentbuddy.api.client.android.pieces.base.MvpPresenter;
import com.netikras.studies.studentbuddy.api.client.android.pieces.student.ui.view.StudentsGroupMvpView;
import com.netikras.studies.studentbuddy.api.client.android.service.ServiceRequest.Subscriber;
import com.netikras.studies.studentbuddy.core.data.api.dto.school.StudentDto;
import com.netikras.studies.studentbuddy.core.data.api.dto.school.StudentsGroupDto;

import java.util.Collection;

/**
 * Created by netikras on 17.11.10.
 */

public interface StudentsGroupMvpPresenter<V extends StudentsGroupMvpView> extends MvpPresenter<V> {

    void getById(Subscriber<StudentsGroupDto> subscriber, String id);

    void getStudentsByGroupId(Subscriber<Collection<StudentDto>> subscriber, String id);

    void create(Subscriber<StudentsGroupDto> subscriber, StudentsGroupDto dto);

    void update(Subscriber<StudentsGroupDto> subscriber, StudentsGroupDto dto);
}
