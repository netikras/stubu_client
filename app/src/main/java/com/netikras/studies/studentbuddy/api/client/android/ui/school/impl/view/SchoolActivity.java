package com.netikras.studies.studentbuddy.api.client.android.ui.school.impl.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.netikras.studies.studentbuddy.api.client.android.R;
import com.netikras.studies.studentbuddy.api.client.android.conf.di.DepInjector;
import com.netikras.studies.studentbuddy.api.client.android.ui.base.BaseActivity;
import com.netikras.studies.studentbuddy.api.client.android.ui.base.BaseViewFields;
import com.netikras.studies.studentbuddy.api.client.android.ui.school.presenter.SchoolMvpPresenter;
import com.netikras.studies.studentbuddy.api.client.android.ui.school.view.SchoolMvpView;

import java.util.Arrays;
import java.util.Collection;

import javax.inject.Inject;

/**
 * Created by netikras on 17.11.10.
 */

public class SchoolActivity extends BaseActivity implements SchoolMvpView {

    private ViewFields fields;

    @Inject
    SchoolMvpPresenter<SchoolMvpView> presenter;

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
        addMenu(R.id.btn_school_main_menu);
    }

    class ViewFields extends BaseViewFields {

        @Override
        protected Collection<TextView> getAllFields() {
            return Arrays.asList();
        }
    }

}
