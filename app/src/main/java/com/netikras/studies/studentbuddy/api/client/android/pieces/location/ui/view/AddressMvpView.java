package com.netikras.studies.studentbuddy.api.client.android.pieces.location.ui.view;

import com.netikras.studies.studentbuddy.api.client.android.pieces.base.MvpView;
import com.netikras.studies.studentbuddy.core.data.api.dto.location.AddressDto;

/**
 * Created by netikras on 17.11.10.
 */

public interface AddressMvpView extends MvpView {
    void show(AddressDto address);
}
