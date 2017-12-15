package com.netikras.studies.studentbuddy.api.client.android.pieces.lecture.ui.presenter;

import com.netikras.studies.studentbuddy.api.client.android.pieces.base.MvpPresenter;
import com.netikras.studies.studentbuddy.api.client.android.pieces.lecture.ui.view.TestMvpView;
import com.netikras.studies.studentbuddy.api.client.android.service.ServiceRequest.Subscriber;
import com.netikras.studies.studentbuddy.core.data.api.dto.school.DisciplineTestDto;

/**
 * Created by netikras on 17.11.10.
 */

public interface TestMvpPresenter<V extends TestMvpView> extends MvpPresenter<V> {

    void getById(Subscriber<DisciplineTestDto> subscriber, String id);

    void create(Subscriber<DisciplineTestDto> subscriber, DisciplineTestDto dto);

    void update(Subscriber<DisciplineTestDto> subscriber, DisciplineTestDto dto);
}
