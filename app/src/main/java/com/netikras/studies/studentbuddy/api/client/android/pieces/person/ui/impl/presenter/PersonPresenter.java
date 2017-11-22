package com.netikras.studies.studentbuddy.api.client.android.pieces.person.ui.impl.presenter;

import android.content.Context;

import com.netikras.studies.studentbuddy.api.client.android.data.DataManager;
import com.netikras.studies.studentbuddy.api.client.android.pieces.base.BaseActivity;
import com.netikras.studies.studentbuddy.api.client.android.pieces.base.BaseActivity.ViewTask;
import com.netikras.studies.studentbuddy.api.client.android.pieces.person.data.PersonDataStore;
import com.netikras.studies.studentbuddy.api.client.android.pieces.base.BasePresenter;
import com.netikras.studies.studentbuddy.api.client.android.pieces.person.ui.impl.view.PersonInfoActivity;
import com.netikras.studies.studentbuddy.api.client.android.pieces.person.ui.impl.view.UserInfoActivity;
import com.netikras.studies.studentbuddy.api.client.android.pieces.person.ui.presenter.PersonMvpPresenter;
import com.netikras.studies.studentbuddy.api.client.android.pieces.person.ui.view.PersonMvpView;
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
        startView(fromContext, PersonInfoActivity.class);
    }

    public void startView(Context fromContext, ViewTask<PersonInfoActivity> task) {
        startView(fromContext, PersonInfoActivity.class, task);
    }

    @Override
    public void showPerson(Context context, PersonDto personDto) {
        startView(context, new ViewTask<PersonInfoActivity>() {
            @Override
            public void execute() {
                getActivity().getFields().reset();
                getActivity().showPerson(personDto);
            }
        });
    }

    @Override
    public void showPerson(Context context, String id) {
        getDataStore().getById(id, new ErrorsAwareSubscriber<PersonDto>() {
            @Override
            public void onSuccess(PersonDto response) {
                showPerson(context, response);
            }
        });
        getDataStore().processOrders(getContext());
    }

    @Override
    public void updatePerson(Context context, PersonDto personDto) {
        getDataStore().update(personDto, new ErrorsAwareSubscriber<PersonDto>() {
            @Override
            public void onSuccess(PersonDto response) {
                showPerson(context, response);
            }
        });
        getDataStore().processOrders(getContext());
    }

    @Override
    public void createPerson(Context context, PersonDto personDto) {
        getDataStore().create(personDto, new ErrorsAwareSubscriber<PersonDto>() {
            @Override
            public void onSuccess(PersonDto response) {
                showPerson(context, response);
            }
        });
        getDataStore().processOrders(getContext());
    }

    @Override
    public void showPersonByIdentifier(Context context, String identification) {
        getDataStore().getByIdentifier(identification, new ErrorsAwareSubscriber<PersonDto>() {
            @Override
            public void onSuccess(PersonDto response) {
                showPerson(context, response);
            }
        });
        getDataStore().processOrders(getContext());
    }


    private PersonDataStore getDataStore() {
        return getDataManager().getStore(PersonDataStore.class);
    }


}
