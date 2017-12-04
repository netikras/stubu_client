package com.netikras.studies.studentbuddy.api.client.android.pieces.student.ui.impl.view;

import android.os.Bundle;
import android.widget.TextView;

import com.netikras.studies.studentbuddy.api.client.android.R;
import com.netikras.studies.studentbuddy.api.client.android.conf.di.DepInjector;
import com.netikras.studies.studentbuddy.api.client.android.pieces.base.BaseActivity;
import com.netikras.studies.studentbuddy.api.client.android.pieces.base.BaseViewFields;
import com.netikras.studies.studentbuddy.api.client.android.pieces.student.ui.presenter.GuestMvpPresenter;
import com.netikras.studies.studentbuddy.api.client.android.pieces.student.ui.presenter.StudentMvpPresenter;
import com.netikras.studies.studentbuddy.api.client.android.pieces.student.ui.view.GuestMvpView;
import com.netikras.studies.studentbuddy.api.client.android.pieces.student.ui.view.StudentMvpView;
import com.netikras.studies.studentbuddy.core.data.api.dto.school.LectureGuestDto;

import java.util.Collection;

import javax.inject.Inject;

/**
 * Created by netikras on 17.11.11.
 */

public class GuestActivity extends BaseActivity implements GuestMvpView {

    private ViewFields fields;

    @Inject
    GuestMvpPresenter<GuestMvpView> presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_);
    }

    @Override
    protected void setUp() {
        DepInjector.inject(this);
        onAttach(this);
        presenter.onAttach(this);
        fields = initFields(new ViewFields());
        addMenu();
        executeTask();
    }

    @Override
    public void show(LectureGuestDto item) {

    }


    class ViewFields extends BaseViewFields {

        @Override
        protected Collection<TextView> getAllFields() {
            return null;
        }
    }

}
