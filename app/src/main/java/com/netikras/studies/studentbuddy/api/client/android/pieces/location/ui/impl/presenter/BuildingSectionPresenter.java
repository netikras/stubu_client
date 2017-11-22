package com.netikras.studies.studentbuddy.api.client.android.pieces.location.ui.impl.presenter;

import android.content.Context;

import com.netikras.studies.studentbuddy.api.client.android.data.DataManager;
import com.netikras.studies.studentbuddy.api.client.android.pieces.base.BaseActivity;
import com.netikras.studies.studentbuddy.api.client.android.pieces.base.BaseActivity.ViewTask;
import com.netikras.studies.studentbuddy.api.client.android.pieces.base.BasePresenter;
import com.netikras.studies.studentbuddy.api.client.android.pieces.location.data.BuildingSectionDataStore;
import com.netikras.studies.studentbuddy.api.client.android.pieces.location.ui.impl.view.BuildingSectionActivity;
import com.netikras.studies.studentbuddy.api.client.android.pieces.location.ui.presenter.BuildingSectionMvpPresenter;
import com.netikras.studies.studentbuddy.api.client.android.pieces.location.ui.view.BuildingSectionMvpView;
import com.netikras.studies.studentbuddy.core.data.api.dto.location.BuildingSectionDto;

import javax.inject.Inject;

/**
 * Created by netikras on 17.11.11.
 */

public class BuildingSectionPresenter<V extends BuildingSectionMvpView> extends BasePresenter<V> implements BuildingSectionMvpPresenter<V> {
    @Inject
    public BuildingSectionPresenter(DataManager dataManager) {
        super(dataManager);
    }

    private BuildingSectionDataStore getDataStore() {
        return getDataManager().getStore(BuildingSectionDataStore.class);
    }

    @Override
    public void startView(Context fromContext) {
        super.startView(fromContext, BuildingSectionActivity.class);
    }

    @Override
    public void show(Context context, BuildingSectionDto section) {
        startView(context, BuildingSectionActivity.class, new ViewTask<BuildingSectionActivity>() {
            @Override
            public void execute() {
                getActivity().show(section);
            }
        });
    }
}
