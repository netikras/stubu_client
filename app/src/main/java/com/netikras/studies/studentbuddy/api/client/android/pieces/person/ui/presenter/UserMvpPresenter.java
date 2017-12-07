package com.netikras.studies.studentbuddy.api.client.android.pieces.person.ui.presenter;

import com.netikras.studies.studentbuddy.api.client.android.pieces.base.MvpPresenter;
import com.netikras.studies.studentbuddy.api.client.android.pieces.person.ui.view.UserMvpView;
import com.netikras.studies.studentbuddy.api.client.android.service.ServiceRequest.Subscriber;
import com.netikras.studies.studentbuddy.core.data.api.dto.meta.UserDto;

/**
 * Created by netikras on 17.10.30.
 */

public interface UserMvpPresenter<V extends UserMvpView> extends MvpPresenter<V> {

    void delete(Subscriber<Boolean> subscriber, String id);

    void create(Subscriber<UserDto> subscriber, UserDto collect);

    void update(Subscriber<UserDto> subscriber, UserDto collect);

    void changePassword(Subscriber<Boolean> subscriber, UserDto collect);

    void fetchPerson(Subscriber<UserDto> subscriber, UserDto userDto);

    void getById(Subscriber<UserDto> subscriber, String id);
}
