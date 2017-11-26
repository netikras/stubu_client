package com.netikras.studies.studentbuddy.api.client.android.pieces.student.ui.impl.presenter;

import android.content.Context;

import com.netikras.studies.studentbuddy.api.client.android.data.DataManager;
import com.netikras.studies.studentbuddy.api.client.android.pieces.base.BaseActivity;
import com.netikras.studies.studentbuddy.api.client.android.pieces.base.BaseActivity.ViewTask;
import com.netikras.studies.studentbuddy.api.client.android.pieces.base.BasePresenter;
import com.netikras.studies.studentbuddy.api.client.android.pieces.student.data.GuestDataStore;
import com.netikras.studies.studentbuddy.api.client.android.pieces.student.ui.impl.view.GuestActivity;
import com.netikras.studies.studentbuddy.api.client.android.pieces.student.ui.presenter.GuestMvpPresenter;
import com.netikras.studies.studentbuddy.api.client.android.pieces.student.ui.view.GuestMvpView;
import com.netikras.studies.studentbuddy.core.data.api.dto.school.LectureGuestDto;

import javax.inject.Inject;

/**
 * Created by netikras on 17.11.11.
 */

public class GuestPresenter<V extends GuestMvpView> extends BasePresenter<V> implements GuestMvpPresenter<V> {

    @Inject
    public GuestPresenter(DataManager dataManager) {
        super(dataManager);
    }

    private GuestDataStore getDataStore() {
        return getDataManager().getStore(GuestDataStore.class);
    }

    @Override
    public void startView(Context fromContext) {
        startView(fromContext, GuestActivity.class);
    }

    @Override
    public void show(Context context, LectureGuestDto item) {
        startView(context, GuestActivity.class, new ViewTask<GuestActivity>() {
            @Override
            public void execute() {
                getActivity().show(item);
            }
        });
    }
}
