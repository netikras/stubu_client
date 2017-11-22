package com.netikras.studies.studentbuddy.api.client.android.pieces.location.ui.impl.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.netikras.studies.studentbuddy.api.client.android.conf.di.DepInjector;
import com.netikras.studies.studentbuddy.api.client.android.pieces.base.BaseActivity;
import com.netikras.studies.studentbuddy.api.client.android.pieces.base.BaseViewFields;
import com.netikras.studies.studentbuddy.api.client.android.pieces.location.ui.presenter.BuildingMvpPresenter;
import com.netikras.studies.studentbuddy.api.client.android.pieces.location.ui.view.BuildingMvpView;
import com.netikras.studies.studentbuddy.core.data.api.dto.location.BuildingDto;

import java.util.Arrays;
import java.util.Collection;

import javax.inject.Inject;

/**
 * Created by netikras on 17.11.10.
 */

public class BuildingActivity extends BaseActivity implements BuildingMvpView {


    private ViewFields fields;

    @Inject
    BuildingMvpPresenter<BuildingMvpView> presenter;

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
//        addMenu(R.id.btn_building_main_menu);
    }

    @Override
    public void show(BuildingDto building) {
    }

    class ViewFields extends BaseViewFields {

        @Override
        protected Collection<TextView> getAllFields() {
            return Arrays.asList();
        }
    }

}
