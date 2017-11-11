package com.netikras.studies.studentbuddy.api.client.android.ui.location.impl.presenter;

import android.content.Context;

import com.netikras.studies.studentbuddy.api.client.android.data.DataManager;
import com.netikras.studies.studentbuddy.api.client.android.ui.base.BasePresenter;
import com.netikras.studies.studentbuddy.api.client.android.ui.location.impl.view.AddressActivity;
import com.netikras.studies.studentbuddy.api.client.android.ui.location.presenter.AddressMvpPresenter;
import com.netikras.studies.studentbuddy.api.client.android.ui.location.view.AddressMvpView;
import com.netikras.studies.studentbuddy.api.client.android.ui.sys.impl.view.AdminActivity;

import javax.inject.Inject;

/**
 * Created by netikras on 17.11.11.
 */

public class AddressPresenter<V extends AddressMvpView> extends BasePresenter<V> implements AddressMvpPresenter<V> {
    @Inject
    public AddressPresenter(DataManager dataManager) {
        super(dataManager);
    }

    private Object getDatastore() {
        return getDataManager().getStore(null); // FIXME
    }

    @Override
    public void startView(Context fromContext) {
        super.startView(fromContext, AddressActivity.class);
    }
}
