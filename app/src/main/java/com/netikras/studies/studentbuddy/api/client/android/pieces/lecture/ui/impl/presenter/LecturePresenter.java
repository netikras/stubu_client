package com.netikras.studies.studentbuddy.api.client.android.pieces.lecture.ui.impl.presenter;

import com.netikras.studies.studentbuddy.api.client.android.data.DataManager;
import com.netikras.studies.studentbuddy.api.client.android.pieces.base.BasePresenter;
import com.netikras.studies.studentbuddy.api.client.android.pieces.lecture.data.LectureDataStore;
import com.netikras.studies.studentbuddy.api.client.android.pieces.lecture.ui.presenter.LectureMvpPresenter;
import com.netikras.studies.studentbuddy.api.client.android.pieces.lecture.ui.view.LectureMvpView;
import com.netikras.studies.studentbuddy.api.client.android.service.ServiceRequest;
import com.netikras.studies.studentbuddy.core.data.api.dto.school.LectureDto;

import javax.inject.Inject;

/**
 * Created by netikras on 17.11.10.
 */

public class LecturePresenter<V extends LectureMvpView> extends BasePresenter<V> implements LectureMvpPresenter<V> {

    @Inject
    public LecturePresenter(DataManager dataManager) {
        super(dataManager);
    }

    private LectureDataStore getDataStore() {
        return getDataManager().getStore(LectureDataStore.class);
    }

    @Override
    public void getById(ServiceRequest.Subscriber<LectureDto> subscriber, String id) {
        getDataStore().getById(id, subscriber);
        getDataStore().processOrders(getContext());
    }
}
