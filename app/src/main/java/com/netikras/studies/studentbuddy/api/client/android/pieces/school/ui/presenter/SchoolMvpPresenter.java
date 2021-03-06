package com.netikras.studies.studentbuddy.api.client.android.pieces.school.ui.presenter;

import com.netikras.studies.studentbuddy.api.client.android.pieces.base.MvpPresenter;
import com.netikras.studies.studentbuddy.api.client.android.pieces.school.ui.view.SchoolMvpView;
import com.netikras.studies.studentbuddy.api.client.android.service.ServiceRequest.Subscriber;
import com.netikras.studies.studentbuddy.core.data.api.dto.school.SchoolDto;

/**
 * Created by netikras on 17.11.9.
 */

public interface SchoolMvpPresenter<V extends SchoolMvpView> extends MvpPresenter<V> {

    void getById(Subscriber<SchoolDto> subscriber, String id);

    void update(Subscriber<SchoolDto> subscriber, SchoolDto dto);
}
