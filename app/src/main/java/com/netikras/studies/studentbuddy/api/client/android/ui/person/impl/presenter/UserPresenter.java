package com.netikras.studies.studentbuddy.api.client.android.ui.person.impl.presenter;

import android.content.Context;
import android.content.Intent;

import com.netikras.studies.studentbuddy.api.client.android.R;
import com.netikras.studies.studentbuddy.api.client.android.data.DataManager;
import com.netikras.studies.studentbuddy.api.client.android.data.stores.user.UserDataStore;
import com.netikras.studies.studentbuddy.api.client.android.ui.base.BasePresenter;
import com.netikras.studies.studentbuddy.api.client.android.ui.person.presenter.PersonMvpPresenter;
import com.netikras.studies.studentbuddy.api.client.android.ui.person.presenter.UserMvpPresenter;
import com.netikras.studies.studentbuddy.api.client.android.ui.person.view.PersonMvpView;
import com.netikras.studies.studentbuddy.api.client.android.ui.person.impl.view.UserInfoActivity;
import com.netikras.studies.studentbuddy.api.client.android.ui.person.view.UserMvpView;
import com.netikras.studies.studentbuddy.core.data.api.dto.meta.UserDto;

import javax.inject.Inject;

import static com.netikras.tools.common.security.IntegrityUtils.isNullOrEmpty;

/**
 * Created by netikras on 17.10.30.
 */

public class UserPresenter<V extends UserMvpView> extends BasePresenter<V> implements UserMvpPresenter<V> {

    @Inject
    PersonMvpPresenter<PersonMvpView> personPresenter;

    @Inject
    public UserPresenter(DataManager dataManager) {
        super(dataManager);
    }

    @Override
    public void startView(Context fromContext) {
        startView(fromContext, UserInfoActivity.class);
    }

    @Override
    public void showUser(String id) {
        getDataStore().getById(id, new ErrorsAwareSubscriber<UserDto>() {
            @Override
            public void onSuccess(UserDto response) {
                showUser(response);

            }
        });
    }

    @Override
    public void showUser(UserDto userDto) {
        getMvpView().showUser(userDto);
    }

    @Override
    public void showPersonForUser(UserDto userDto) {
        if (userDto.getPerson() == null) {
            getMvpView().onError(R.string.txt_msg_user_missing);
        }

        if (isNullOrEmpty(userDto.getPerson().getIdentification())) {
            getMvpView().onError(R.string.txt_msg_user_has_no_person);
            return;
        }

        personPresenter.showPersonByIdentifier(userDto.getPerson().getIdentification());
        personPresenter.startView((Context) getMvpView());
    }

    @Override
    public void delete(String id) {
        getDataStore().delete(id, new ErrorsAwareSubscriber<Boolean>() {
            @Override
            public void onSuccess(Boolean response) {
                showUser((UserDto) null);
            }
        });
    }

    @Override
    public void create(UserDto collect) {
        getDataStore().create(collect, new ErrorsAwareSubscriber<UserDto>() {
            @Override
            public void onSuccess(UserDto response) {
                showUser(response);
            }
        });
    }

    @Override
    public void update(UserDto collect) {
        getDataStore().update(collect, new ErrorsAwareSubscriber<UserDto>() {
            @Override
            public void onSuccess(UserDto response) {
                showUser(response);
            }
        });
    }


    UserDataStore getDataStore() {
        return getDataManager().getStore(UserDataStore.class);
    }

}
