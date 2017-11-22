package com.netikras.studies.studentbuddy.api.client.android.pieces.discipline.ui.presenter;

import android.content.Context;

import com.netikras.studies.studentbuddy.api.client.android.pieces.base.MvpPresenter;
import com.netikras.studies.studentbuddy.api.client.android.pieces.discipline.ui.impl.view.DisciplineInfoActivity;
import com.netikras.studies.studentbuddy.api.client.android.pieces.discipline.ui.view.DisciplineMvpView;
import com.netikras.studies.studentbuddy.core.data.api.dto.school.DisciplineDto;
import com.netikras.studies.studentbuddy.core.data.api.dto.school.SchoolDto;

/**
 * Created by netikras on 17.11.9.
 */

public interface DisciplineMvpPresenter<V extends DisciplineMvpView> extends MvpPresenter<V> {

    void show(Context context, DisciplineDto discipline);

    void showSchool(Context context, SchoolDto school);
}
