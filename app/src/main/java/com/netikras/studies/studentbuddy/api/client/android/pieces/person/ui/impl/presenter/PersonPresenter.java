package com.netikras.studies.studentbuddy.api.client.android.pieces.person.ui.impl.presenter;

import com.netikras.studies.studentbuddy.api.client.android.data.DataManager;
import com.netikras.studies.studentbuddy.api.client.android.pieces.base.BasePresenter;
import com.netikras.studies.studentbuddy.api.client.android.pieces.person.data.PersonDataStore;
import com.netikras.studies.studentbuddy.api.client.android.pieces.person.ui.presenter.PersonMvpPresenter;
import com.netikras.studies.studentbuddy.api.client.android.pieces.person.ui.view.PersonMvpView;
import com.netikras.studies.studentbuddy.api.client.android.service.ServiceRequest;
import com.netikras.studies.studentbuddy.api.client.android.service.ServiceRequest.Subscriber;
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
    public void updatePerson(Subscriber<PersonDto> subscriber, PersonDto personDto) {
        getDataStore().update(personDto, subscriber);
        getDataStore().processOrders(getContext());
    }

    @Override
    public void createPerson(Subscriber<PersonDto> subscriber, PersonDto personDto) {
        getDataStore().create(personDto, subscriber);
        getDataStore().processOrders(getContext());
    }

    @Override
    public void getById(Subscriber<PersonDto> subscriber, String id) {
        getDataStore().getById(id, subscriber);
        getDataStore().processOrders(getContext());
    }

    private PersonDataStore getDataStore() {
        return getDataManager().getStore(PersonDataStore.class);
    }


}
