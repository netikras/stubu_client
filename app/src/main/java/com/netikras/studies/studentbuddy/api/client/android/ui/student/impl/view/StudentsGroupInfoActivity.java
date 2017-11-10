package com.netikras.studies.studentbuddy.api.client.android.ui.student.impl.view;

import android.os.Bundle;
import android.widget.TextView;

import com.netikras.studies.studentbuddy.api.client.android.R;
import com.netikras.studies.studentbuddy.api.client.android.conf.di.DepInjector;
import com.netikras.studies.studentbuddy.api.client.android.ui.base.BaseActivity;
import com.netikras.studies.studentbuddy.api.client.android.ui.base.BaseViewFields;
import com.netikras.studies.studentbuddy.api.client.android.ui.student.presenter.StudentsGroupMvpPresenter;
import com.netikras.studies.studentbuddy.api.client.android.ui.student.view.StudentsGroupMvpView;

import java.util.Collection;

import javax.inject.Inject;

public class StudentsGroupInfoActivity extends BaseActivity implements StudentsGroupMvpView {


    private ViewFields fields;

    @Inject
    StudentsGroupMvpPresenter<StudentsGroupMvpView> presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_students_group_info);
    }

    @Override
    protected void setUp() {
        DepInjector.inject(this);
        onAttach(this);
        presenter.onAttach(this);
        fields = initFields(new ViewFields());
        addMenu(R.id.btn_user_main_menu);
    }


    class ViewFields extends BaseViewFields {

        @Override
        protected Collection<TextView> getAllFields() {
            return null;
        }
    }

}
