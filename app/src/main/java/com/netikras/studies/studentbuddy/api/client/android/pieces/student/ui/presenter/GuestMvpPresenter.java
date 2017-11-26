package com.netikras.studies.studentbuddy.api.client.android.pieces.student.ui.presenter;

import android.content.Context;

import com.netikras.studies.studentbuddy.api.client.android.pieces.base.MvpPresenter;
import com.netikras.studies.studentbuddy.api.client.android.pieces.student.ui.view.GuestMvpView;
import com.netikras.studies.studentbuddy.core.data.api.dto.school.LectureGuestDto;

/**
 * Created by netikras on 17.11.11.
 */

public interface GuestMvpPresenter<V extends GuestMvpView> extends MvpPresenter<V> {
    void show(Context context, LectureGuestDto item);
}
