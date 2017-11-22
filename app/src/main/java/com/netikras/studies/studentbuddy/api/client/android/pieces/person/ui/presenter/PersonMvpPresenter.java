package com.netikras.studies.studentbuddy.api.client.android.pieces.person.ui.presenter;

import android.content.Context;

import com.netikras.studies.studentbuddy.api.client.android.pieces.base.MvpPresenter;
import com.netikras.studies.studentbuddy.api.client.android.pieces.person.ui.view.PersonMvpView;
import com.netikras.studies.studentbuddy.core.data.api.dto.PersonDto;

/**
 * Created by netikras on 17.10.31.
 */

public interface PersonMvpPresenter<V extends PersonMvpView> extends MvpPresenter <V> {


    void showPerson(Context context, PersonDto personDto);

    void showPerson(Context context, String id);

    void updatePerson(Context context, PersonDto personDto);

    void createPerson(Context context, PersonDto personDto);

    void showPersonByIdentifier(Context context, String identification);
}
