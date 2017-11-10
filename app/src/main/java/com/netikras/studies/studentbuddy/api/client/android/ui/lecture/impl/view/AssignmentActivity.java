package com.netikras.studies.studentbuddy.api.client.android.ui.lecture.impl.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.netikras.studies.studentbuddy.api.client.android.R;
import com.netikras.studies.studentbuddy.api.client.android.conf.di.DepInjector;
import com.netikras.studies.studentbuddy.api.client.android.ui.base.BaseActivity;
import com.netikras.studies.studentbuddy.api.client.android.ui.base.BaseViewFields;
import com.netikras.studies.studentbuddy.api.client.android.ui.lecture.presenter.AssignmentMvpPresenter;
import com.netikras.studies.studentbuddy.api.client.android.ui.lecture.view.AssignmentMvpView;
import com.netikras.studies.studentbuddy.api.client.android.ui.person.impl.view.UserInfoActivity;

import java.util.Collection;

import javax.inject.Inject;

/**
 * Created by netikras on 17.11.10.
 */

public class AssignmentActivity extends BaseActivity implements AssignmentMvpView {

    private ViewFields fields;

    @Inject
    AssignmentMvpPresenter<AssignmentMvpView> presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setUp();
    }

    @Override
    protected void setUp() {
        DepInjector.inject(this);
        onAttach(this);
        presenter.onAttach(this);
        fields = initFields(new ViewFields());
        addMenu(R.id.btn_assignment_main_menu);
    }


    class ViewFields extends BaseViewFields {

        @Override
        protected Collection<TextView> getAllFields() {
            return null;
        }
    }
}
