package com.netikras.studies.studentbuddy.api.client.android.pieces.location.ui.presenter;

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

    void getRoom(Subscriber<LectureRoomDto> subscriber, String id);

    void getFloor(Subscriber<BuildingFloorDto> subscriber, String floorId);

    void getSection(Subscriber<BuildingSectionDto> subscriber, String id);

    void getBuilding(Subscriber<BuildingDto> subscriber, String id);

    void getAddress(Subscriber<AddressDto> subscriber, String id);
}
