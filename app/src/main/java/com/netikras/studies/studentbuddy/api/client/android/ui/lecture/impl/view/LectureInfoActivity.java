package com.netikras.studies.studentbuddy.api.client.android.ui.lecture.impl.view;

import android.os.Bundle;
import android.widget.TextView;

import com.netikras.studies.studentbuddy.api.client.android.R;
import com.netikras.studies.studentbuddy.api.client.android.conf.di.DepInjector;
import com.netikras.studies.studentbuddy.api.client.android.ui.base.BaseActivity;
import com.netikras.studies.studentbuddy.api.client.android.ui.base.BaseViewFields;
import com.netikras.studies.studentbuddy.api.client.android.ui.lecture.impl.presenter.LecturePresenter;
import com.netikras.studies.studentbuddy.api.client.android.ui.lecture.presenter.LectureMvpPresenter;
import com.netikras.studies.studentbuddy.api.client.android.ui.lecture.view.LectureMvpView;
import com.netikras.studies.studentbuddy.api.client.android.ui.person.impl.view.UserInfoActivity;

import java.util.Collection;

import javax.inject.Inject;

public class LectureInfoActivity extends BaseActivity implements LectureMvpView {


    private ViewFields fields;

    @Inject
    LectureMvpPresenter<LectureMvpView> presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lecture_info);
        setUp();
    }

    @Override
    protected void setUp() {
        DepInjector.inject(this);
        onAttach(this);
        presenter.onAttach(this);
        fields = initFields(new ViewFields());
        addMenu(R.id.btn_lecture_main_menu);
    }

    class ViewFields extends BaseViewFields {

        @Override
        protected Collection<TextView> getAllFields() {
            return null;
        }
    }

}
