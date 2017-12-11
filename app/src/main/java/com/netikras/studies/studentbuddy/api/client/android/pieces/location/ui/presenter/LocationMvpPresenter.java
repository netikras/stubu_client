package com.netikras.studies.studentbuddy.api.client.android.pieces.location.ui.presenter;

import android.content.Context;

import com.netikras.studies.studentbuddy.api.client.android.pieces.base.MvpPresenter;
import com.netikras.studies.studentbuddy.api.client.android.pieces.location.ui.view.LocationMvpView;
import com.netikras.studies.studentbuddy.api.client.android.service.ServiceRequest.Subscriber;
import com.netikras.studies.studentbuddy.core.data.api.dto.location.AddressDto;
import com.netikras.studies.studentbuddy.core.data.api.dto.location.BuildingDto;
import com.netikras.studies.studentbuddy.core.data.api.dto.location.BuildingFloorDto;
import com.netikras.studies.studentbuddy.core.data.api.dto.location.BuildingSectionDto;
import com.netikras.studies.studentbuddy.core.data.api.dto.location.LectureRoomDto;

/**
 * Created by netikras on 17.11.22.
 */

public interface LocationMvpPresenter<V extends LocationMvpView> extends MvpPresenter<V> {


    void show(Context context, AddressDto addressDto, BuildingDto buildingDto, BuildingSectionDto sectionDto, BuildingFloorDto floorDto, LectureRoomDto roomDto);

    void showAddress(Context context, AddressDto address);

    void showBuilding(Context context, BuildingDto building);

    void showSection(Context context, BuildingSectionDto section);

    void showFloor(Context context, BuildingFloorDto floor);

    void showRoom(Context context, LectureRoomDto room);

    void getRoom(Subscriber<LectureRoomDto> subscriber, String id);
}
