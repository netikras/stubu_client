package com.netikras.studies.studentbuddy.api.client.android.ui.school.impl.presenter;

import android.content.Context;

import com.netikras.studies.studentbuddy.api.client.android.data.DataManager;
import com.netikras.studies.studentbuddy.api.client.android.ui.base.BasePresenter;
import com.netikras.studies.studentbuddy.api.client.android.ui.school.impl.view.PersonnelMemberActivity;
import com.netikras.studies.studentbuddy.api.client.android.ui.school.presenter.PersonelMemberMvpPresenter;
import com.netikras.studies.studentbuddy.api.client.android.ui.school.view.PersonelMemberMvpView;
import com.netikras.studies.studentbuddy.api.client.android.ui.sys.impl.view.AdminActivity;

import javax.inject.Inject;

/**
 * Created by netikras on 17.11.11.
 */

public class PersonelMemberPresenter<V extends PersonelMemberMvpView> extends BasePresenter<V> implements PersonelMemberMvpPresenter<V> {
    @Inject
    public PersonelMemberPresenter(DataManager dataManager) {
        super(dataManager);
    }

    private Object getDatastore() {
        return getDataManager().getStore(null); // FIXME
    }

    @Override
    public void startView(Context fromContext) {
        super.startView(fromContext, PersonnelMemberActivity.class);
    }
}
