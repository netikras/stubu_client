package com.netikras.studies.studentbuddy.api.client.android.pieces.discipline.ui.view;

import com.netikras.studies.studentbuddy.api.client.android.pieces.base.MvpView;
import com.netikras.studies.studentbuddy.api.client.android.pieces.discipline.ui.impl.view.DisciplineInfoActivity;

/**
 * Created by netikras on 17.11.9.
 */

public interface DisciplineMvpView extends MvpView {
    DisciplineInfoActivity.ViewFields getFields();
}
