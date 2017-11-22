package com.netikras.studies.studentbuddy.api.client.android.pieces.location.ui.impl.view;

import android.support.annotation.Nullable;
import android.os.Bundle;
import android.widget.TextView;

import com.netikras.studies.studentbuddy.api.client.android.conf.di.DepInjector;
import com.netikras.studies.studentbuddy.api.client.android.pieces.base.BaseActivity;
import com.netikras.studies.studentbuddy.api.client.android.pieces.base.BaseViewFields;
import com.netikras.studies.studentbuddy.api.client.android.pieces.location.ui.presenter.FloorMvpPresenter;
import com.netikras.studies.studentbuddy.api.client.android.pieces.location.ui.view.FloorMvpView;
import com.netikras.studies.studentbuddy.core.data.api.dto.location.BuildingFloorDto;

import java.util.Arrays;
import java.util.Collection;

import javax.inject.Inject;

public class FloorActivity extends BaseActivity implements FloorMvpView {

    private ViewFields fields;

    @Inject
    FloorMvpPresenter<FloorMvpView> presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.);
        setUp();
    }

    @Override
    protected void setUp() {
        DepInjector.inject(this);
        onAttach(this);
        presenter.onAttach(this);
        fields = initFields(new ViewFields());
//        addMenu(R.id.btn_layout_main_menu);
    }

    @Override
    public void show(BuildingFloorDto floor) {
    }

    class ViewFields extends BaseViewFields {

        @Override
        protected Collection<TextView> getAllFields() {
            return Arrays.asList();
        }
    }
}
