package com.netikras.studies.studentbuddy.api.client.android.ui.lecturer.impl.view;

import android.os.Bundle;
import android.widget.TextView;

import com.netikras.studies.studentbuddy.api.client.android.R;
import com.netikras.studies.studentbuddy.api.client.android.conf.di.DepInjector;
import com.netikras.studies.studentbuddy.api.client.android.ui.base.BaseActivity;
import com.netikras.studies.studentbuddy.api.client.android.ui.base.BaseViewFields;
import com.netikras.studies.studentbuddy.api.client.android.ui.lecturer.presenter.LecturerMvpPresenter;
import com.netikras.studies.studentbuddy.api.client.android.ui.lecturer.view.LecturerMvpView;
import com.netikras.studies.studentbuddy.api.client.android.ui.person.impl.view.UserInfoActivity;

import java.util.Collection;

import javax.inject.Inject;

public class LecturerInfoActivity extends BaseActivity implements LecturerMvpView {


    private ViewFields fields;

    @Inject
    LecturerMvpPresenter<LecturerMvpView> presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lecturer_info);
    }

    @Override
    protected void setUp() {
        DepInjector.inject(this);
        onAttach(this);
        presenter.onAttach(this);
        fields = initFields(new ViewFields());
        addMenu(R.id.btn_lecturer_main_menu);
    }


    class ViewFields extends BaseViewFields {

        @Override
        protected Collection<TextView> getAllFields() {
            return null;
        }
    }
}
