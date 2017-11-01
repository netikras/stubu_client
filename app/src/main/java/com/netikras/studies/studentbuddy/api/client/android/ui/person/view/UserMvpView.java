package com.netikras.studies.studentbuddy.api.client.android.ui.person.view;

import com.netikras.studies.studentbuddy.api.client.android.ui.base.MvpView;
import com.netikras.studies.studentbuddy.core.data.api.dto.meta.UserDto;

/**
 * Created by netikras on 17.10.30.
 */

public interface UserMvpView extends MvpView {
    void showUser(UserDto userDto);
}
