package com.netikras.studies.studentbuddy.api.client.android.pieces.lecture.ui.impl.presenter;

import android.content.Context;

import com.netikras.studies.studentbuddy.api.client.android.data.DataManager;
import com.netikras.studies.studentbuddy.api.client.android.pieces.base.BaseActivity;
import com.netikras.studies.studentbuddy.api.client.android.pieces.base.BaseActivity.ViewTask;
import com.netikras.studies.studentbuddy.api.client.android.pieces.base.BasePresenter;
import com.netikras.studies.studentbuddy.api.client.android.pieces.lecture.data.AssignmentDataStore;
import com.netikras.studies.studentbuddy.api.client.android.pieces.lecture.ui.impl.view.AssignmentActivity;
import com.netikras.studies.studentbuddy.api.client.android.pieces.lecture.ui.presenter.AssignmentMvpPresenter;
import com.netikras.studies.studentbuddy.api.client.android.pieces.lecture.ui.view.AssignmentMvpView;
import com.netikras.studies.studentbuddy.core.data.api.dto.school.AssignmentDto;

import javax.inject.Inject;

/**
 * Created by netikras on 17.11.10.
 */

public class AssignmentPresenter<V extends AssignmentMvpView> extends BasePresenter<V> implements AssignmentMvpPresenter<V> {


    @Inject
    public AssignmentPresenter(DataManager dataManager) {
        super(dataManager);
    }

    private AssignmentDataStore getDataStore() {
        return getDataManager().getStore(AssignmentDataStore.class);
    }

    @Override
    public void startView(Context fromContext) {
        startView(fromContext, AssignmentActivity.class);
    }

    @Override
    public void show(Context context, AssignmentDto item) {
        startView(context, AssignmentActivity.class, new ViewTask<AssignmentActivity>() {
            @Override
            public void execute() {
                getActivity().show(item);
            }
        });
    }
}
