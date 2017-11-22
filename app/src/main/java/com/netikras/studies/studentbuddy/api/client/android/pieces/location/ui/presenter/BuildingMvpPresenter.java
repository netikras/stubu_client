package com.netikras.studies.studentbuddy.api.client.android.pieces.location.ui.presenter;

import android.content.Context;

import com.netikras.studies.studentbuddy.api.client.android.pieces.base.MvpPresenter;
import com.netikras.studies.studentbuddy.api.client.android.pieces.location.ui.view.BuildingMvpView;
import com.netikras.studies.studentbuddy.core.data.api.dto.location.BuildingDto;

/**
 * Created by netikras on 17.11.10.
 */

public interface BuildingMvpPresenter<V extends BuildingMvpView> extends MvpPresenter<V> {
    void show(Context context, BuildingDto building);
}
