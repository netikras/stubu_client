package com.netikras.studies.studentbuddy.api.client.android.ui.sys.impl.presenter;

import android.content.Context;

import com.netikras.studies.studentbuddy.api.client.android.data.DataManager;
import com.netikras.studies.studentbuddy.api.client.android.ui.base.BasePresenter;
import com.netikras.studies.studentbuddy.api.client.android.ui.sys.impl.view.AdminActivity;
import com.netikras.studies.studentbuddy.api.client.android.ui.sys.impl.view.RoleActivity;
import com.netikras.studies.studentbuddy.api.client.android.ui.sys.presenter.RoleMvpPresenter;
import com.netikras.studies.studentbuddy.api.client.android.ui.sys.view.RoleMvpView;

import javax.inject.Inject;

/**
 * Created by netikras on 17.11.11.
 */

public class RolePresenter<V extends RoleMvpView> extends BasePresenter<V> implements RoleMvpPresenter<V> {
    @Inject
    public RolePresenter(DataManager dataManager) {
        super(dataManager);
    }

    private Object getDatastore() {
        return getDataManager().getStore(null); // FIXME
    }

    @Override
    public void startView(Context fromContext) {
        super.startView(fromContext, RoleActivity.class);
    }
}
