package com.netikras.studies.studentbuddy.api.client.android.pieces.discipline.ui.view;

import com.netikras.studies.studentbuddy.api.client.android.pieces.base.MvpView;
import com.netikras.studies.studentbuddy.api.client.android.pieces.discipline.ui.impl.view.DisciplineInfoActivity;
import com.netikras.studies.studentbuddy.core.data.api.dto.school.DisciplineDto;

/**
 * Created by netikras on 17.11.9.
 */

public interface DisciplineMvpView extends MvpView {
    DisciplineInfoActivity.ViewFields getFields();

    void show(DisciplineDto disciplineDto);
}
