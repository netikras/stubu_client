package com.netikras.studies.studentbuddy.api.client.android.ui.base;

import android.content.Context;

import com.netikras.tools.common.exception.ErrorBody;

/**
 * Created by netikras on 17.10.30.
 */

public interface MvpPresenter <V extends MvpView> {

    void onAttach(V mvpView);

    void onDetach();

    void handleApiError(ErrorBody error);

    void setUserAsLoggedOut();

    void startView(Context fromContext);

}