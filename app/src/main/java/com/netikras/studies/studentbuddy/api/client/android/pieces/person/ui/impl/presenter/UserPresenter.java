package com.netikras.studies.studentbuddy.api.client.android.pieces.person.ui.impl.presenter;

import com.netikras.studies.studentbuddy.api.client.android.data.DataManager;
import com.netikras.studies.studentbuddy.api.client.android.pieces.base.BasePresenter;
import com.netikras.studies.studentbuddy.api.client.android.pieces.person.data.UserDataStore;
import com.netikras.studies.studentbuddy.api.client.android.pieces.person.ui.presenter.UserMvpPresenter;
import com.netikras.studies.studentbuddy.api.client.android.pieces.person.ui.view.UserMvpView;
import com.netikras.studies.studentbuddy.api.client.android.service.ServiceRequest;
import com.netikras.studies.studentbuddy.api.client.android.service.ServiceRequest.Subscriber;
import com.netikras.studies.studentbuddy.core.data.api.dto.meta.UserDto;
import com.netikras.tools.common.exception.ErrorBody;
import com.netikras.tools.common.exception.ErrorsCollection;

import javax.inject.Inject;

import static com.netikras.tools.common.security.IntegrityUtils.isNullOrEmpty;

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
        getDataStore().processOrders(getContext());
    }

    @Override
    public void create(Subscriber<UserDto> subscriber, UserDto collect) {
        getDataStore().create(collect, subscriber);
        getDataStore().processOrders(getContext());
    }

    @Override
    public void update(Subscriber<UserDto> subscriber, UserDto collect) {
        getDataStore().update(collect, subscriber);
        getDataStore().processOrders(getContext());
    }

    @Override
    public void changePassword(Subscriber<Boolean> subscriber, UserDto collect) {
        getDataStore().changePassword(collect.getId(), collect.getPassword(), subscriber);
        getDataStore().processOrders(getContext());
    }

    @Override
    public void fetchPerson(Subscriber<UserDto> subscriber, UserDto userDto) {
        if (userDto != null) {
            if (subscriber != null) {
                if (userDto.getPerson() != null) {
                    subscriber.executeOnSuccess(userDto);
                    return;
                }
                if (!isNullOrEmpty(userDto.getId())) {
                    getDataStore().getById(userDto.getId(), subscriber);
                    getDataStore().processOrders(getContext());
                    return;
                }
                if (!isNullOrEmpty(userDto.getName())) {
                    getDataStore().getByName(userDto.getName(), subscriber);
                    getDataStore().processOrders(getContext());
                    return;
                }
            }
        }

        if (subscriber != null) {
            subscriber.executeOnError(new ErrorBody().setMessage1("Unable to fetch User's person"));
        }
    }

    @Override
    public void getById(Subscriber<UserDto> subscriber, String id) {
        getDataStore().getById(id, subscriber);
        getDataStore().processOrders(getContext());
    }


    UserDataStore getDataStore() {
        return getDataManager().getStore(UserDataStore.class);
    }

}
