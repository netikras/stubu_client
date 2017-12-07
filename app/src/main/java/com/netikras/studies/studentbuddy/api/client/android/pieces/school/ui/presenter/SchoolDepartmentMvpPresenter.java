package com.netikras.studies.studentbuddy.api.client.android.pieces.school.ui.presenter;

import com.netikras.studies.studentbuddy.api.client.android.pieces.base.MvpPresenter;
import com.netikras.studies.studentbuddy.api.client.android.pieces.school.ui.view.SchoolDepartmentMvpView;
import com.netikras.studies.studentbuddy.api.client.android.service.ServiceRequest.Subscriber;
import com.netikras.studies.studentbuddy.core.data.api.dto.school.SchoolDepartmentDto;

/**
 * Created by netikras on 17.11.9.
 */

public interface SchoolDepartmentMvpPresenter<V extends SchoolDepartmentMvpView> extends MvpPresenter<V> {

    void getById(Subscriber<SchoolDepartmentDto> subscriber, String id);
}
