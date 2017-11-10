package com.netikras.studies.studentbuddy.api.client.android.ui.location.impl.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.netikras.studies.studentbuddy.api.client.android.R;
import com.netikras.studies.studentbuddy.api.client.android.conf.di.DepInjector;
import com.netikras.studies.studentbuddy.api.client.android.ui.base.BaseActivity;
import com.netikras.studies.studentbuddy.api.client.android.ui.base.BaseViewFields;
import com.netikras.studies.studentbuddy.api.client.android.ui.location.presenter.AddressMvpPresenter;
import com.netikras.studies.studentbuddy.api.client.android.ui.location.view.AddressMvpView;
import com.netikras.studies.studentbuddy.api.client.android.ui.school.impl.view.CourseActivity;
import com.netikras.studies.studentbuddy.api.client.android.ui.school.presenter.CourseMvpPresenter;
import com.netikras.studies.studentbuddy.api.client.android.ui.school.view.CourseMvpView;

import java.util.Arrays;
import java.util.Collection;

import javax.inject.Inject;

/**
 * Created by netikras on 17.11.10.
 */

public class AddressActivity extends BaseActivity implements AddressMvpView {

    private ViewFields fields;

    @Inject
    AddressMvpPresenter<AddressMvpView> presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.);
        setUp();
    }

    @Override
    protected void setUp() {
        DepInjector.inject(this);
        onAttach(this);
        presenter.onAttach(this);
        fields = initFields(new ViewFields());
        addMenu(R.id.btn_address_main_menu);
    }

    class ViewFields extends BaseViewFields {

        @Override
        protected Collection<TextView> getAllFields() {
            return Arrays.asList();
        }
    }

}
