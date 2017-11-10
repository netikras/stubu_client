package com.netikras.studies.studentbuddy.api.client.android.ui.person.impl.presenter;

import android.content.Context;
import android.content.Intent;

import com.netikras.studies.studentbuddy.api.client.android.data.DataManager;
import com.netikras.studies.studentbuddy.api.client.android.data.stores.person.PersonDataStore;
import com.netikras.studies.studentbuddy.api.client.android.ui.base.BasePresenter;
import com.netikras.studies.studentbuddy.api.client.android.ui.person.impl.view.UserInfoActivity;
import com.netikras.studies.studentbuddy.api.client.android.ui.person.presenter.PersonMvpPresenter;
import com.netikras.studies.studentbuddy.api.client.android.ui.person.impl.view.PersonInfoActivity;
import com.netikras.studies.studentbuddy.api.client.android.ui.person.view.PersonMvpView;
import com.netikras.studies.studentbuddy.core.data.api.dto.PersonDto;

import javax.inject.Inject;

/**
 * Created by netikras on 17.10.31.
 */

public class PersonPresenter<V extends PersonMvpView> extends BasePresenter<V> implements PersonMvpPresenter<V> {

    @Inject
    public PersonPresenter(DataManager dataManager) {
        super(dataManager);
    }

    @Override
    public void startView(Context fromContext) {
        startView(fromContext, UserInfoActivity.class);
    }


    @Override
    public void showPerson(String id) {
        getDataStore().getById(id, new ErrorsAwareSubscriber<PersonDto>() {
            @Override
            public void onSuccess(PersonDto response) {
                getMvpView().getFields().enableEdit(false);
                getMvpView().showPerson(response);
            }
        });
        getDataStore().processOrders(getContext());
    }

    @Override
    public void updatePerson(PersonDto personDto) {
        getDataStore().update(personDto, new ErrorsAwareSubscriber<PersonDto>() {
            @Override
            public void onSuccess(PersonDto response) {
                getMvpView().showPerson(response);
                getMvpView().getFields().enableEdit(false);
            }
        });
        getDataStore().processOrders(getContext());
    }

    @Override
    public void createPerson(PersonDto personDto) {
        getDataStore().create(personDto, new ErrorsAwareSubscriber<PersonDto>() {
            @Override
            public void onSuccess(PersonDto response) {
                getMvpView().showPerson(response);
                getMvpView().getFields().enableEdit(false);
            }
        });
        getDataStore().processOrders(getContext());
    }

    @Override
    public void showPersonByIdentifier(String identification) {
        getDataStore().getByIdentifier(identification, new ErrorsAwareSubscriber<PersonDto>() {
            @Override
            public void onSuccess(PersonDto response) {
//                getMvpView().getFields().enableEdit(false);
                getMvpView().showPerson(response);
            }
        });
        getDataStore().processOrders(getContext());
    }


    private PersonDataStore getDataStore() {
        return getDataManager().getStore(PersonDataStore.class);
    }


}
