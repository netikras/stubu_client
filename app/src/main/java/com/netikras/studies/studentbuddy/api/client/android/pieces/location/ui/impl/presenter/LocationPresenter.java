package com.netikras.studies.studentbuddy.api.client.android.pieces.location.ui.impl.presenter;

import com.netikras.studies.studentbuddy.api.client.android.data.DataManager;
import com.netikras.studies.studentbuddy.api.client.android.pieces.base.BasePresenter;
import com.netikras.studies.studentbuddy.api.client.android.pieces.location.data.FloorDataStore;
import com.netikras.studies.studentbuddy.api.client.android.pieces.location.data.RoomDataStore;
import com.netikras.studies.studentbuddy.api.client.android.pieces.location.ui.presenter.AddressMvpPresenter;
import com.netikras.studies.studentbuddy.api.client.android.pieces.location.ui.presenter.BuildingMvpPresenter;
import com.netikras.studies.studentbuddy.api.client.android.pieces.location.ui.presenter.BuildingSectionMvpPresenter;
import com.netikras.studies.studentbuddy.api.client.android.pieces.location.ui.presenter.FloorMvpPresenter;
import com.netikras.studies.studentbuddy.api.client.android.pieces.location.ui.presenter.LocationMvpPresenter;
import com.netikras.studies.studentbuddy.api.client.android.pieces.location.ui.presenter.RoomMvpPresenter;
import com.netikras.studies.studentbuddy.api.client.android.pieces.location.ui.view.AddressMvpView;
import com.netikras.studies.studentbuddy.api.client.android.pieces.location.ui.view.BuildingMvpView;
import com.netikras.studies.studentbuddy.api.client.android.pieces.location.ui.view.BuildingSectionMvpView;
import com.netikras.studies.studentbuddy.api.client.android.pieces.location.ui.view.FloorMvpView;
import com.netikras.studies.studentbuddy.api.client.android.pieces.location.ui.view.LocationMvpView;
import com.netikras.studies.studentbuddy.api.client.android.pieces.location.ui.view.RoomMvpView;
import com.netikras.studies.studentbuddy.api.client.android.service.ServiceRequest.Subscriber;
import com.netikras.studies.studentbuddy.core.data.api.dto.location.BuildingFloorDto;
import com.netikras.studies.studentbuddy.core.data.api.dto.location.LectureRoomDto;

import javax.inject.Inject;

/**
 * Created by netikras on 17.11.22.
 */

public class LocationPresenter<V extends LocationMvpView> extends BasePresenter<V> implements LocationMvpPresenter<V> {


    @Inject
    AddressMvpPresenter<AddressMvpView> addressPresenter;
    @Inject
    BuildingMvpPresenter<BuildingMvpView> buildingPresenter;
    @Inject
    BuildingSectionMvpPresenter<BuildingSectionMvpView> sectionPresenter;
    @Inject
    FloorMvpPresenter<FloorMvpView> floorPresenter;
    @Inject
    RoomMvpPresenter<RoomMvpView> roomPresenter;

    @Inject
    public LocationPresenter(DataManager dataManager) {
        super(dataManager);
    }

    @Override
    public void getRoom(Subscriber<LectureRoomDto> subscriber, String id) {
        getDataManager().getStore(RoomDataStore.class).getById(id, subscriber);
        getDataManager().getStore(RoomDataStore.class).processOrders(getContext());
    }

    @Override
    public void getFloor(Subscriber<BuildingFloorDto> subscriber, String floorId) {
        getDataManager().getStore(FloorDataStore.class).getById(floorId, subscriber);
        getDataManager().getStore(FloorDataStore.class).processOrders(getContext());
    }
}
