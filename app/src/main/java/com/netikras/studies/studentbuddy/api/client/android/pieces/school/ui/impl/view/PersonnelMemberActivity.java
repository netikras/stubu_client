package com.netikras.studies.studentbuddy.api.client.android.pieces.school.ui.impl.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.netikras.studies.studentbuddy.api.client.android.conf.di.DepInjector;
import com.netikras.studies.studentbuddy.api.client.android.pieces.base.BaseActivity;
import com.netikras.studies.studentbuddy.api.client.android.pieces.base.BaseViewFields;
import com.netikras.studies.studentbuddy.api.client.android.pieces.school.ui.presenter.PersonnelMemberMvpPresenter;
import com.netikras.studies.studentbuddy.api.client.android.pieces.school.ui.view.PersonnelMemberMvpView;

import java.util.Arrays;
import java.util.Collection;

import javax.inject.Inject;

/**
 * Created by netikras on 17.11.10.
 */

public class PersonnelMemberActivity extends BaseActivity implements PersonnelMemberMvpView {

    private ViewFields fields;

    @Inject
    PersonnelMemberMvpPresenter<PersonnelMemberMvpView> presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.);
        setUp();
    }

    @Override
    protected void setUp() {
        DepInjector.inject(this);
        onAttach(this);
        presenter.onAttach(this);
        fields = initFields(new ViewFields());
//        addMenu(R.id.btn_personel_main_menu);
    }

    class ViewFields extends BaseViewFields {

        @Override
        protected Collection<TextView> getAllFields() {
            return Arrays.asList();
        }
    }

}