package com.netikras.studies.studentbuddy.api.client.android.pieces.school.ui.impl.presenter;

import android.content.Context;

import com.netikras.studies.studentbuddy.api.client.android.data.DataManager;
import com.netikras.studies.studentbuddy.api.client.android.pieces.base.BasePresenter;
import com.netikras.studies.studentbuddy.api.client.android.pieces.school.data.PersonnelDataStore;
import com.netikras.studies.studentbuddy.api.client.android.pieces.school.ui.impl.view.PersonnelMemberActivity;
import com.netikras.studies.studentbuddy.api.client.android.pieces.school.ui.presenter.PersonnelMemberMvpPresenter;
import com.netikras.studies.studentbuddy.api.client.android.pieces.school.ui.view.PersonnelMemberMvpView;

import javax.inject.Inject;

/**
 * Created by netikras on 17.11.11.
 */

public class PersonnelMemberPresenter<V extends PersonnelMemberMvpView> extends BasePresenter<V> implements PersonnelMemberMvpPresenter<V> {
    @Inject
    public PersonnelMemberPresenter(DataManager dataManager) {
        super(dataManager);
    }

    private PersonnelDataStore getDataStore() {
        return getDataManager().getStore(PersonnelDataStore.class);
    }

    @Override
    public void startView(Context fromContext) {
        super.startView(fromContext, PersonnelMemberActivity.class);
    }
}
