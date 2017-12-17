package com.netikras.studies.studentbuddy.api.client.android.pieces.discipline.ui.presenter;

import com.netikras.studies.studentbuddy.api.client.android.pieces.base.MvpPresenter;
import com.netikras.studies.studentbuddy.api.client.android.pieces.discipline.ui.view.DisciplineMvpView;
import com.netikras.studies.studentbuddy.api.client.android.service.ServiceRequest.Subscriber;
import com.netikras.studies.studentbuddy.core.data.api.dto.school.DisciplineDto;

/**
 * Created by netikras on 17.11.9.
 */

public interface DisciplineMvpPresenter<V extends DisciplineMvpView> extends MvpPresenter<V> {

    void getById(Subscriber<DisciplineDto> subscriber, String id);

    void update(Subscriber<DisciplineDto> subscriber, DisciplineDto dto);
}
