package com.netikras.studies.studentbuddy.api.client.android.pieces.login.ui.presenter;

import com.netikras.studies.studentbuddy.api.client.android.pieces.base.MvpPresenter;
import com.netikras.studies.studentbuddy.api.client.android.pieces.login.ui.view.LoginMvpView;

/**
 * Created by netikras on 17.11.1.
 */

public interface LoginMvpPresenter<V extends LoginMvpView> extends MvpPresenter<V> {

    void proceedLogin(String username, String password);

    String getLastLoginUsername();

    void setLastLoginUsername(String username);
}
