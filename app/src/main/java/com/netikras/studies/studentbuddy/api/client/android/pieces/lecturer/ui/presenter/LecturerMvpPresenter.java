package com.netikras.studies.studentbuddy.api.client.android.pieces.lecturer.ui.presenter;

import android.content.Context;

import com.netikras.studies.studentbuddy.api.client.android.pieces.base.MvpPresenter;
import com.netikras.studies.studentbuddy.api.client.android.pieces.lecturer.ui.view.LecturerMvpView;
import com.netikras.studies.studentbuddy.core.data.api.dto.PersonDto;
import com.netikras.studies.studentbuddy.core.data.api.dto.school.LecturerDto;

/**
 * Created by netikras on 17.11.10.
 */

public interface LecturerMvpPresenter<V extends LecturerMvpView> extends MvpPresenter<V> {
    void show(Context context, LecturerDto lecturer);

    void showPerson(Context context, PersonDto person);
}
