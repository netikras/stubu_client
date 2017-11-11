package com.netikras.studies.studentbuddy.api.client.android.pieces.main.ui.impl.view;

import android.os.Bundle;
import android.widget.TextView;

import com.netikras.studies.studentbuddy.api.client.android.R;
import com.netikras.studies.studentbuddy.api.client.android.conf.di.DepInjector;
import com.netikras.studies.studentbuddy.api.client.android.pieces.base.BaseActivity;
import com.netikras.studies.studentbuddy.api.client.android.pieces.base.BaseViewFields;
import com.netikras.studies.studentbuddy.api.client.android.pieces.main.ui.presenter.MainMvpPresenter;
import com.netikras.studies.studentbuddy.api.client.android.pieces.main.ui.view.MainMvpView;

import java.util.Collection;

import javax.inject.Inject;

public class MainActivity extends BaseActivity implements MainMvpView {


    private ViewFields fields;

    @Inject
    MainMvpPresenter<MainMvpView> presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }

    @Override
    protected void setUp() {
        DepInjector.inject(this);
        onAttach(this);
        presenter.onAttach(this);
        fields = initFields(new ViewFields());
        addMenu(R.id.btn_main_main_menu);
    }


    class ViewFields extends BaseViewFields {

        @Override
        protected Collection<TextView> getAllFields() {
            return null;
        }
    }
}
