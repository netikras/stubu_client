package com.netikras.studies.studentbuddy.api.client.android.ui.base;

import android.support.annotation.StringRes;

/**
 * Created by netikras on 17.10.30.
 */

public interface MvpView {

    void showLoading();

    void hideLoading();

    void openActivityOnTokenExpire();

    void onError(@StringRes int resId);

    void onError(String message);

    void showMessage(String message);

    void showMessage(@StringRes int resId);

    boolean isNetworkConnected();

    void hideKeyboard();

}
