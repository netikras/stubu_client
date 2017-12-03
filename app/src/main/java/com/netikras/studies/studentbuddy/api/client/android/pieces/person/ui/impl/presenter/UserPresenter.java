package com.netikras.studies.studentbuddy.api.client.android.pieces.person.ui.impl.presenter;

import com.netikras.studies.studentbuddy.api.client.android.data.DataManager;
import com.netikras.studies.studentbuddy.api.client.android.pieces.base.BasePresenter;
import com.netikras.studies.studentbuddy.api.client.android.pieces.person.data.UserDataStore;
import com.netikras.studies.studentbuddy.api.client.android.pieces.person.ui.presenter.UserMvpPresenter;
import com.netikras.studies.studentbuddy.api.client.android.pieces.person.ui.view.UserMvpView;
import com.netikras.studies.studentbuddy.api.client.android.service.ServiceRequest.Subscriber;
import com.netikras.studies.studentbuddy.core.data.api.dto.meta.UserDto;

import javax.inject.Inject;

/**
 * Created by netikras on 17.10.30.
 */

public class UserPresenter<V extends UserMvpView> extends BasePresenter<V> implements UserMvpPresenter<V> {

    @Inject
    public UserPresenter(DataManager dataManager) {
        super(dataManager);
    }

    @Override
    public void delete(Subscriber<Boolean> subscriber, String id) {
        getDataStore().delete(id, subscriber);
    }

    @Override
    public void create(Subscriber<UserDto> subscriber, UserDto collect) {
        getDataStore().create(collect, subscriber);
    }

    @Override
    public void update(Subscriber<UserDto> subscriber, UserDto collect) {
        getDataStore().update(collect, subscriber);
    }


    UserDataStore getDataStore() {
        return getDataManager().getStore(UserDataStore.class);
    }

}
