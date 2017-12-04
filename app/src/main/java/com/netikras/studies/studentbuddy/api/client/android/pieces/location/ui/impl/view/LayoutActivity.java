package com.netikras.studies.studentbuddy.api.client.android.pieces.location.ui.impl.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.netikras.studies.studentbuddy.api.client.android.R;
import com.netikras.studies.studentbuddy.api.client.android.conf.di.DepInjector;
import com.netikras.studies.studentbuddy.api.client.android.pieces.base.BaseActivity;
import com.netikras.studies.studentbuddy.api.client.android.pieces.base.BaseViewFields;
import com.netikras.studies.studentbuddy.api.client.android.pieces.location.ui.presenter.LayoutMvpPresenter;
import com.netikras.studies.studentbuddy.api.client.android.pieces.location.ui.view.LayoutMvpView;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by netikras on 17.11.10.
 */

public class LayoutActivity extends BaseActivity implements LayoutMvpView {


    private ViewFields fields;

    @Inject
    LayoutMvpPresenter<LayoutMvpView> presenter;

    @Override
    protected List<Integer> excludeMenuItems() {
        return Arrays.asList(R.id.main_menu_create, R.id.main_menu_delete);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_floor_layout);
        setUp();
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

    class ViewFields extends BaseViewFields {

        @Override
        protected Collection<TextView> getAllFields() {
            return Arrays.asList();
        }
    }


}
