package com.netikras.studies.studentbuddy.api.client.android.pieces.school.ui.view;

import com.netikras.studies.studentbuddy.api.client.android.pieces.base.MvpView;
import com.netikras.studies.studentbuddy.api.client.android.pieces.school.ui.impl.view.SchoolActivity;

/**
 * Created by netikras on 17.11.9.
 */

public interface SchoolMvpView extends MvpView {
    SchoolActivity.ViewFields getFields();
}
