package com.netikras.studies.studentbuddy.api.client.android.ui.login.presenter;

import com.netikras.studies.studentbuddy.api.client.android.ui.base.MvpPresenter;
import com.netikras.studies.studentbuddy.api.client.android.ui.login.view.LoginMvpView;

/**
 * Created by netikras on 17.11.1.
 */

public interface LoginMvpPresenter<V extends LoginMvpView> extends MvpPresenter<V> {

    void proceedLogin(String username, String password);
}
