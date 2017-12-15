package com.netikras.studies.studentbuddy.api.client.android.pieces.lecture.ui.impl.presenter;

import com.netikras.studies.studentbuddy.api.client.android.data.DataManager;
import com.netikras.studies.studentbuddy.api.client.android.pieces.base.BasePresenter;
import com.netikras.studies.studentbuddy.api.client.android.pieces.lecture.data.TestDataStore;
import com.netikras.studies.studentbuddy.api.client.android.pieces.lecture.ui.presenter.TestMvpPresenter;
import com.netikras.studies.studentbuddy.api.client.android.pieces.lecture.ui.view.TestMvpView;
import com.netikras.studies.studentbuddy.api.client.android.service.ServiceRequest;
import com.netikras.studies.studentbuddy.api.client.android.service.ServiceRequest.Subscriber;
import com.netikras.studies.studentbuddy.core.data.api.dto.school.DisciplineTestDto;

import javax.inject.Inject;

/**
 * Created by netikras on 17.11.10.
 */

public class TestsPresenter<V extends TestMvpView> extends BasePresenter<V> implements TestMvpPresenter<V> {

    @Inject
    public TestsPresenter(DataManager dataManager) {
        super(dataManager);
    }

    private TestDataStore getDataStore() {
        return getDataManager().getStore(TestDataStore.class);
    }

    @Override
    public void getById(Subscriber<DisciplineTestDto> subscriber, String id) {
        getDataStore().getById(id, subscriber);
        getDataStore().processOrders(getContext());
    }

    @Override
    public void create(Subscriber<DisciplineTestDto> subscriber, DisciplineTestDto dto) {
        getDataStore().create(dto, subscriber);
        getDataStore().processOrders(getContext());
    }

    @Override
    public void update(Subscriber<DisciplineTestDto> subscriber, DisciplineTestDto dto) {
        getDataStore().update(dto, subscriber);
        getDataStore().processOrders(getContext());
    }

}
