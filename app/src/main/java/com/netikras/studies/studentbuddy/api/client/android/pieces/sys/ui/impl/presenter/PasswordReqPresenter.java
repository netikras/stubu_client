package com.netikras.studies.studentbuddy.api.client.android.pieces.sys.ui.impl.presenter;

import android.content.Context;

import com.netikras.studies.studentbuddy.api.client.android.data.DataManager;
import com.netikras.studies.studentbuddy.api.client.android.pieces.base.BasePresenter;
import com.netikras.studies.studentbuddy.api.client.android.pieces.sys.ui.impl.view.PasswordReqActivity;
import com.netikras.studies.studentbuddy.api.client.android.pieces.sys.ui.presenter.PasswordReqMvpPresenter;
import com.netikras.studies.studentbuddy.api.client.android.pieces.sys.ui.view.PasswordReqMvpView;

import javax.inject.Inject;

/**
 * Created by netikras on 17.11.11.
 */

public class PasswordReqPresenter<V extends PasswordReqMvpView> extends BasePresenter<V> implements PasswordReqMvpPresenter<V> {
    @Inject
    public PasswordReqPresenter(DataManager dataManager) {
        super(dataManager);
    }

    private Object getDatastore() {
        return getDataManager().getStore(null); // FIXME
    }

    @Override
    public void startView(Context fromContext) {
        super.startView(fromContext, PasswordReqActivity.class);
    }
}
