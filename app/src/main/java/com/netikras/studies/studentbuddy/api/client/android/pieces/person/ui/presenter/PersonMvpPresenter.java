package com.netikras.studies.studentbuddy.api.client.android.pieces.person.ui.presenter;

import com.netikras.studies.studentbuddy.api.client.android.pieces.base.MvpPresenter;
import com.netikras.studies.studentbuddy.api.client.android.pieces.person.ui.view.PersonMvpView;
import com.netikras.studies.studentbuddy.api.client.android.service.ServiceRequest.Subscriber;
import com.netikras.studies.studentbuddy.core.data.api.dto.PersonDto;

/**
 * Created by netikras on 17.10.31.
 */

public interface PersonMvpPresenter<V extends PersonMvpView> extends MvpPresenter<V> {

    void updatePerson(Subscriber<PersonDto> subscriber, PersonDto personDto);

    void createPerson(Subscriber<PersonDto> subscriber, PersonDto personDto);

    void getById(Subscriber<PersonDto> subscriber, String id);
}
