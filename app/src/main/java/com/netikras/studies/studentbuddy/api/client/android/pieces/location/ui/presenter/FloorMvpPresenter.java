package com.netikras.studies.studentbuddy.api.client.android.pieces.location.ui.presenter;

import android.content.Context;

import com.netikras.studies.studentbuddy.api.client.android.pieces.base.MvpPresenter;
import com.netikras.studies.studentbuddy.api.client.android.pieces.location.ui.view.FloorMvpView;
import com.netikras.studies.studentbuddy.core.data.api.dto.location.BuildingFloorDto;

/**
 * Created by netikras on 17.11.10.
 */

public interface FloorMvpPresenter<V extends FloorMvpView> extends MvpPresenter<V> {
    void show(Context context, BuildingFloorDto floor);
}
