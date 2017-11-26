package com.netikras.studies.studentbuddy.api.client.android.pieces.lecture.ui.presenter;

import android.content.Context;

import com.netikras.studies.studentbuddy.api.client.android.pieces.base.MvpPresenter;
import com.netikras.studies.studentbuddy.api.client.android.pieces.lecture.ui.view.AssignmentMvpView;
import com.netikras.studies.studentbuddy.core.data.api.dto.school.AssignmentDto;

/**
 * Created by netikras on 17.11.10.
 */

public interface AssignmentMvpPresenter<V extends AssignmentMvpView> extends MvpPresenter<V> {

    void show(Context context, AssignmentDto item);
}
