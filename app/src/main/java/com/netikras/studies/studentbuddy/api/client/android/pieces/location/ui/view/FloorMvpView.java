package com.netikras.studies.studentbuddy.api.client.android.pieces.location.ui.view;

import com.netikras.studies.studentbuddy.api.client.android.pieces.base.MvpView;
import com.netikras.studies.studentbuddy.core.data.api.dto.location.BuildingFloorDto;

/**
 * Created by netikras on 17.11.10.
 */

public interface FloorMvpView extends MvpView {
    void show(BuildingFloorDto floor);
}
