package com.netikras.studies.studentbuddy.api.client.android.pieces.login.ui.presenter;

import com.netikras.studies.studentbuddy.api.client.android.pieces.base.MvpPresenter;
import com.netikras.studies.studentbuddy.api.client.android.pieces.login.ui.view.LoginMvpView;
import com.netikras.studies.studentbuddy.api.client.android.service.ServiceRequest.Subscriber;
import com.netikras.studies.studentbuddy.core.data.api.dto.meta.UserDto;

/**
 * Created by netikras on 17.11.1.
 */

public interface LoginMvpPresenter<V extends LoginMvpView> extends MvpPresenter<V> {

    void proceedLogin(String username, String password, Subscriber<UserDto> subscriber);

    void logout(Subscriber<Boolean> subscriber, UserDto userDto);

    String getLastLoginUsername();

    void setLastLoginUsername(String username);
}
