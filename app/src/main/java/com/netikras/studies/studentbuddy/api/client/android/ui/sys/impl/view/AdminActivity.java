package com.netikras.studies.studentbuddy.api.client.android.ui.sys.impl.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.netikras.studies.studentbuddy.api.client.android.R;
import com.netikras.studies.studentbuddy.api.client.android.conf.di.DepInjector;
import com.netikras.studies.studentbuddy.api.client.android.ui.base.BaseActivity;
import com.netikras.studies.studentbuddy.api.client.android.ui.base.BaseViewFields;
import com.netikras.studies.studentbuddy.api.client.android.ui.location.impl.view.RoomActivity;
import com.netikras.studies.studentbuddy.api.client.android.ui.location.presenter.RoomMvpPresenter;
import com.netikras.studies.studentbuddy.api.client.android.ui.location.view.RoomMvpView;
import com.netikras.studies.studentbuddy.api.client.android.ui.sys.presenter.AdminMvpPresenter;
import com.netikras.studies.studentbuddy.api.client.android.ui.sys.view.AdminMvpView;

import java.util.Arrays;
import java.util.Collection;

import javax.inject.Inject;

/**
 * Created by netikras on 17.11.11.
 */

public class AdminActivity extends BaseActivity implements AdminMvpView {

    private ViewFields fields;

    @Inject
    AdminMvpPresenter<AdminMvpView> presenter;

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
        addMenu(R.id.btn_admin_main_menu);
    }

    class ViewFields extends BaseViewFields {

        @Override
        protected Collection<TextView> getAllFields() {
            return Arrays.asList();
        }
    }
}
