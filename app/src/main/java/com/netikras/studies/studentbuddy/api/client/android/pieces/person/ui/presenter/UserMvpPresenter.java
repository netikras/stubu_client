package com.netikras.studies.studentbuddy.api.client.android.pieces.person.ui.presenter;

import com.netikras.studies.studentbuddy.api.client.android.pieces.base.MvpPresenter;
import com.netikras.studies.studentbuddy.api.client.android.pieces.person.ui.view.UserMvpView;
import com.netikras.studies.studentbuddy.core.data.api.dto.meta.UserDto;

/**
 * Created by netikras on 17.10.30.
 */

public interface UserMvpPresenter <V extends UserMvpView> extends MvpPresenter <V> {

    void showUser(String id);

    void showUser(UserDto userDto);

    void showPersonForUser(UserDto userDto);

    void delete(String id);

    void create(UserDto collect);

    void update(UserDto collect);
}
