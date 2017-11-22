package com.netikras.studies.studentbuddy.api.client.android.pieces.location.ui.impl.presenter;

import android.content.Context;

import com.netikras.studies.studentbuddy.api.client.android.data.DataManager;
import com.netikras.studies.studentbuddy.api.client.android.pieces.base.BaseActivity;
import com.netikras.studies.studentbuddy.api.client.android.pieces.base.BaseActivity.ViewTask;
import com.netikras.studies.studentbuddy.api.client.android.pieces.base.BasePresenter;
import com.netikras.studies.studentbuddy.api.client.android.pieces.location.data.RoomDataStore;
import com.netikras.studies.studentbuddy.api.client.android.pieces.location.ui.impl.view.RoomActivity;
import com.netikras.studies.studentbuddy.api.client.android.pieces.location.ui.presenter.RoomMvpPresenter;
import com.netikras.studies.studentbuddy.api.client.android.pieces.location.ui.view.RoomMvpView;
import com.netikras.studies.studentbuddy.core.data.api.dto.location.LectureRoomDto;

import javax.inject.Inject;

/**
 * Created by netikras on 17.11.11.
 */

public class RoomPresenter<V extends RoomMvpView> extends BasePresenter<V> implements RoomMvpPresenter<V> {
    @Inject
    public RoomPresenter(DataManager dataManager) {
        super(dataManager);
    }

    private RoomDataStore getDataStore() {
        return getDataManager().getStore(RoomDataStore.class);
    }

    @Override
    public void startView(Context fromContext) {
        super.startView(fromContext, RoomActivity.class);
    }

    @Override
    public void show(Context context, LectureRoomDto room) {
        startView(context, RoomActivity.class, new ViewTask<RoomActivity>() {
            @Override
            public void execute() {
                getActivity().show(room);
            }
        });
    }
}
