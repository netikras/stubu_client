package com.netikras.studies.studentbuddy.api.client.android.pieces.location.ui.impl.presenter;

import android.content.Context;

import com.netikras.studies.studentbuddy.api.client.android.data.DataManager;
import com.netikras.studies.studentbuddy.api.client.android.pieces.base.BaseActivity;
import com.netikras.studies.studentbuddy.api.client.android.pieces.base.BaseActivity.ViewTask;
import com.netikras.studies.studentbuddy.api.client.android.pieces.base.BasePresenter;
import com.netikras.studies.studentbuddy.api.client.android.pieces.location.data.AddressDataStore;
import com.netikras.studies.studentbuddy.api.client.android.pieces.location.ui.impl.view.AddressActivity;
import com.netikras.studies.studentbuddy.api.client.android.pieces.location.ui.presenter.AddressMvpPresenter;
import com.netikras.studies.studentbuddy.api.client.android.pieces.location.ui.view.AddressMvpView;
import com.netikras.studies.studentbuddy.core.data.api.dto.location.AddressDto;

import javax.inject.Inject;

/**
 * Created by netikras on 17.11.11.
 */

public class AddressPresenter<V extends AddressMvpView> extends BasePresenter<V> implements AddressMvpPresenter<V> {
    @Inject
    public AddressPresenter(DataManager dataManager) {
        super(dataManager);
    }

    private AddressDataStore getDataStore() {
        return getDataManager().getStore(AddressDataStore.class);
    }

    @Override
    public void startView(Context fromContext) {
        super.startView(fromContext, AddressActivity.class);
    }

    @Override
    public void show(Context context, AddressDto address) {
        startView(context, AddressActivity.class, new ViewTask<AddressActivity>() {
            @Override
            public void execute() {
                getActivity().show(address);
            }
        });
    }
}
