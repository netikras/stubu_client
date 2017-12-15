package com.netikras.studies.studentbuddy.api.client.android.pieces.lecture.ui.view;

import com.netikras.studies.studentbuddy.api.client.android.pieces.base.MvpView;
import com.netikras.studies.studentbuddy.api.client.android.pieces.lecture.ui.impl.view.TestInfoActivity;
import com.netikras.studies.studentbuddy.core.data.api.dto.school.DisciplineTestDto;

/**
 * Created by netikras on 17.11.10.
 */

public interface TestMvpView extends MvpView {
    TestInfoActivity.ViewFields getFields();

    void show(DisciplineTestDto dto);

    void show(DisciplineTestDto dto, boolean createNew);
}
