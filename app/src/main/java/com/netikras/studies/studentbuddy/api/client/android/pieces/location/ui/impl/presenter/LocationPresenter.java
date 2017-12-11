package com.netikras.studies.studentbuddy.api.client.android.pieces.location.ui.impl.presenter;

import android.content.Context;

import com.netikras.studies.studentbuddy.api.client.android.data.DataManager;
import com.netikras.studies.studentbuddy.api.client.android.pieces.base.BaseActivity;
import com.netikras.studies.studentbuddy.api.client.android.pieces.base.BaseActivity.ViewTask;
import com.netikras.studies.studentbuddy.api.client.android.pieces.base.BasePresenter;
import com.netikras.studies.studentbuddy.api.client.android.pieces.location.data.RoomDataStore;
import com.netikras.studies.studentbuddy.api.client.android.pieces.location.ui.impl.view.LocationInfoActivity;
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
import com.netikras.studies.studentbuddy.api.client.android.service.ServiceRequest;
import com.netikras.studies.studentbuddy.api.client.android.service.ServiceRequest.Subscriber;
import com.netikras.studies.studentbuddy.core.data.api.dto.location.AddressDto;
import com.netikras.studies.studentbuddy.core.data.api.dto.location.BuildingDto;
import com.netikras.studies.studentbuddy.core.data.api.dto.location.BuildingFloorDto;
import com.netikras.studies.studentbuddy.core.data.api.dto.location.BuildingSectionDto;
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
    public void show(Context context, AddressDto addressDto, BuildingDto buildingDto, BuildingSectionDto sectionDto, BuildingFloorDto floorDto, LectureRoomDto roomDto) {
        startView(context, LocationInfoActivity.class, new ViewTask<LocationInfoActivity>() {
            @Override
            public void execute() {
                getActivity().show(addressDto, buildingDto, sectionDto, floorDto, roomDto);
            }
        });
    }

    @Override
    public void showAddress(Context context, AddressDto address) {
        addressPresenter.show(context, address);
    }

    @Override
    public void showBuilding(Context context, BuildingDto building) {
        buildingPresenter.show(context, building);
    }

    @Override
    public void showSection(Context context, BuildingSectionDto section) {
        sectionPresenter.show(context, section);
    }

    @Override
    public void showFloor(Context context, BuildingFloorDto floor) {
        floorPresenter.show(context, floor);
    }

    @Override
    public void showRoom(Context context, LectureRoomDto room) {
        roomPresenter.show(context, room);
    }

    @Override
    public void getRoom(Subscriber<LectureRoomDto> subscriber, String id) {
        getDataManager().getStore(RoomDataStore.class).getById(id);
        getDataManager().getStore(RoomDataStore.class).processOrders(getContext());
    }
}
