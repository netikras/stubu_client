package com.netikras.studies.studentbuddy.api.client.android.pieces.location.ui.presenter;

import android.content.Context;

import com.netikras.studies.studentbuddy.api.client.android.pieces.base.MvpPresenter;
import com.netikras.studies.studentbuddy.api.client.android.pieces.location.ui.view.BuildingSectionMvpView;
import com.netikras.studies.studentbuddy.core.data.api.dto.location.BuildingSectionDto;

/**
 * Created by netikras on 17.11.10.
 */

public interface BuildingSectionMvpPresenter<V extends BuildingSectionMvpView> extends MvpPresenter<V> {
    void show(Context context, BuildingSectionDto section);
}
