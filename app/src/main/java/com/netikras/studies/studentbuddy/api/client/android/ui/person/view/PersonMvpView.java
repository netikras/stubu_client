package com.netikras.studies.studentbuddy.api.client.android.ui.person.view;

import com.netikras.studies.studentbuddy.api.client.android.ui.base.BaseViewFields;
import com.netikras.studies.studentbuddy.api.client.android.ui.base.MvpView;
import com.netikras.studies.studentbuddy.core.data.api.dto.PersonDto;

/**
 * Created by netikras on 17.10.31.
 */

public interface PersonMvpView extends MvpView {

    void showPerson(PersonDto personDto);

    BaseViewFields getFields();

}
