package com.netikras.studies.studentbuddy.api.client.android.ui.person.presenter;

import com.netikras.studies.studentbuddy.api.client.android.ui.base.MvpPresenter;
import com.netikras.studies.studentbuddy.api.client.android.ui.person.view.PersonMvpView;
import com.netikras.studies.studentbuddy.core.data.api.dto.PersonDto;

/**
 * Created by netikras on 17.10.31.
 */

public interface PersonMvpPresenter<V extends PersonMvpView> extends MvpPresenter <V> {


    void showPerson(String id);

    void updatePerson(PersonDto personDto);

    void createPerson(PersonDto personDto);

    void showPersonByIdentifier(String identification);
}
