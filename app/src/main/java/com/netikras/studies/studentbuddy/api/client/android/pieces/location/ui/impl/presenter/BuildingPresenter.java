package com.netikras.studies.studentbuddy.api.client.android.pieces.location.ui.impl.presenter;

import android.content.Context;

import com.netikras.studies.studentbuddy.api.client.android.data.DataManager;
import com.netikras.studies.studentbuddy.api.client.android.pieces.base.BaseActivity;
import com.netikras.studies.studentbuddy.api.client.android.pieces.base.BaseActivity.ViewTask;
import com.netikras.studies.studentbuddy.api.client.android.pieces.base.BasePresenter;
import com.netikras.studies.studentbuddy.api.client.android.pieces.location.data.BuildingDataStore;
import com.netikras.studies.studentbuddy.api.client.android.pieces.location.ui.impl.view.BuildingActivity;
import com.netikras.studies.studentbuddy.api.client.android.pieces.location.ui.presenter.BuildingMvpPresenter;
import com.netikras.studies.studentbuddy.api.client.android.pieces.location.ui.view.BuildingMvpView;
import com.netikras.studies.studentbuddy.core.data.api.dto.location.BuildingDto;

import javax.inject.Inject;

/**
 * Created by netikras on 17.11.11.
 */

public class BuildingPresenter<V extends BuildingMvpView> extends BasePresenter<V> implements BuildingMvpPresenter<V> {
    @Inject
    public BuildingPresenter(DataManager dataManager) {
        super(dataManager);
    }

    private BuildingDataStore getDataStore() {
        return getDataManager().getStore(BuildingDataStore.class);
    }

    @Override
    public void startView(Context fromContext) {
        super.startView(fromContext, BuildingActivity.class);
    }

    @Override
    public void show(Context context, BuildingDto building) {
        startView(context, BuildingActivity.class, new ViewTask<BuildingActivity>() {
            @Override
            public void execute() {
                getActivity().show(building);
            }
        });
    }
}
