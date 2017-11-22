package com.netikras.studies.studentbuddy.api.client.android.pieces.location.ui.view;

import com.netikras.studies.studentbuddy.api.client.android.pieces.base.MvpView;
import com.netikras.studies.studentbuddy.api.client.android.pieces.location.ui.impl.view.LocationInfoActivity;
import com.netikras.studies.studentbuddy.core.data.api.dto.location.AddressDto;
import com.netikras.studies.studentbuddy.core.data.api.dto.location.BuildingDto;
import com.netikras.studies.studentbuddy.core.data.api.dto.location.BuildingFloorDto;
import com.netikras.studies.studentbuddy.core.data.api.dto.location.BuildingSectionDto;
import com.netikras.studies.studentbuddy.core.data.api.dto.location.LectureRoomDto;

/**
 * Created by netikras on 17.11.22.
 */

public interface LocationMvpView extends MvpView {
    LocationInfoActivity.ViewFields getFields();

    void show(AddressDto addressDto, BuildingDto buildingDto, BuildingSectionDto sectionDto, BuildingFloorDto floorDto, LectureRoomDto roomDto);

    void showClean(AddressDto addressDto, BuildingDto buildingDto, BuildingSectionDto sectionDto, BuildingFloorDto floorDto, LectureRoomDto roomDto);
}
