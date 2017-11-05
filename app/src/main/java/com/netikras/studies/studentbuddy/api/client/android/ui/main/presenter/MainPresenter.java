package com.netikras.studies.studentbuddy.api.client.android.ui.main.presenter;

import com.netikras.studies.studentbuddy.api.client.android.data.DataManager;
import com.netikras.studies.studentbuddy.api.client.android.ui.main.view.MainActivity;
import com.netikras.studies.studentbuddy.api.client.android.ui.base.BasePresenter;
import com.netikras.studies.studentbuddy.api.client.android.ui.main.view.MainMvpView;

import javax.inject.Inject;

/**
 * Created by netikras on 17.11.1.
 */

public class MainPresenter<V extends MainMvpView> extends BasePresenter<V> implements MainMvpPresenter<V> {

    @Inject
    public MainPresenter(DataManager dataManager) {
        super(dataManager);
    }
}
