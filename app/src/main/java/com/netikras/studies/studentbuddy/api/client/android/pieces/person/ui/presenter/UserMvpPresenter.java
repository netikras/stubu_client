package com.netikras.studies.studentbuddy.api.client.android.pieces.person.ui.presenter;

import android.content.Context;

import com.netikras.studies.studentbuddy.api.client.android.pieces.base.MvpPresenter;
import com.netikras.studies.studentbuddy.api.client.android.pieces.person.ui.view.UserMvpView;
import com.netikras.studies.studentbuddy.core.data.api.dto.meta.UserDto;

/**
 * Created by netikras on 17.10.30.
 */

public interface UserMvpPresenter <V extends UserMvpView> extends MvpPresenter <V> {

    void showUser(Context context, String id);

    void showUser(Context context, UserDto userDto);

    void showPersonForUser(Context context, UserDto userDto);

    void delete(Context context, String id);

    void create(Context context, UserDto collect);

    void update(Context context, UserDto collect);
}
