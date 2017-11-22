package com.netikras.studies.studentbuddy.api.client.android.pieces.person.ui.impl.presenter;

import android.content.Context;

import com.netikras.studies.studentbuddy.api.client.android.R;
import com.netikras.studies.studentbuddy.api.client.android.data.DataManager;
import com.netikras.studies.studentbuddy.api.client.android.pieces.base.BaseActivity;
import com.netikras.studies.studentbuddy.api.client.android.pieces.base.BaseActivity.ViewTask;
import com.netikras.studies.studentbuddy.api.client.android.pieces.person.data.UserDataStore;
import com.netikras.studies.studentbuddy.api.client.android.pieces.base.BasePresenter;
import com.netikras.studies.studentbuddy.api.client.android.pieces.person.ui.presenter.PersonMvpPresenter;
import com.netikras.studies.studentbuddy.api.client.android.pieces.person.ui.presenter.UserMvpPresenter;
import com.netikras.studies.studentbuddy.api.client.android.pieces.person.ui.view.PersonMvpView;
import com.netikras.studies.studentbuddy.api.client.android.pieces.person.ui.impl.view.UserInfoActivity;
import com.netikras.studies.studentbuddy.api.client.android.pieces.person.ui.view.UserMvpView;
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

    public void startView(Context fromContext, ViewTask<UserInfoActivity> task) {
        startView(fromContext, UserInfoActivity.class, task);
    }


    @Override
    public void showUser(Context context, UserDto userDto) {
        startView(context, new ViewTask<UserInfoActivity>() {
            @Override
            public void execute() {
                getActivity().getFields().reset();
                getActivity().showUser(userDto);
            }
        });
    }


    @Override
    public void showUser(Context context, String id) {
        getDataStore().getById(id, new ErrorsAwareSubscriber<UserDto>() {
            @Override
            public void onSuccess(UserDto response) {
                showUser(context, response);
            }
        });
    }

    @Override
    public void showPersonForUser(Context context, UserDto userDto) {
        if (userDto.getPerson() == null) {
            getMvpView().onError(R.string.txt_msg_user_missing);
            return;
        }

        if (isNullOrEmpty(userDto.getPerson().getIdentification())) {
            getMvpView().onError(R.string.txt_msg_user_has_no_person);
            return;
        }

        personPresenter.showPersonByIdentifier(getContext(), userDto.getPerson().getIdentification());
    }

    @Override
    public void delete(Context context, String id) {
        getDataStore().delete(id, new ErrorsAwareSubscriber<Boolean>() {
            @Override
            public void onSuccess(Boolean response) {
                showUser(context, (UserDto) null);
            }
        });
    }

    @Override
    public void create(Context context, UserDto collect) {
        getDataStore().create(collect, new ErrorsAwareSubscriber<UserDto>() {
            @Override
            public void onSuccess(UserDto response) {
                showUser(context, response);
            }
        });
    }

    @Override
    public void update(Context context, UserDto collect) {
        getDataStore().update(collect, new ErrorsAwareSubscriber<UserDto>() {
            @Override
            public void onSuccess(UserDto response) {
                showUser(context, response);
            }
        });
    }


    UserDataStore getDataStore() {
        return getDataManager().getStore(UserDataStore.class);
    }

}
