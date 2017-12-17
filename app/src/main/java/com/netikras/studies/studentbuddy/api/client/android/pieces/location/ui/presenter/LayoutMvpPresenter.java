package com.netikras.studies.studentbuddy.api.client.android.pieces.location.ui.presenter;

import com.netikras.studies.studentbuddy.api.client.android.pieces.base.MvpPresenter;
import com.netikras.studies.studentbuddy.api.client.android.pieces.location.ui.view.LayoutMvpView;
import com.netikras.studies.studentbuddy.api.client.android.service.ServiceRequest.Subscriber;
import com.netikras.studies.studentbuddy.core.data.api.dto.location.FloorLayoutDto;

/**
 * Created by netikras on 17.11.10.
 */

public interface LayoutMvpPresenter<V extends LayoutMvpView> extends MvpPresenter<V> {
    void getById(Subscriber<FloorLayoutDto> subscriber, String id);
}
