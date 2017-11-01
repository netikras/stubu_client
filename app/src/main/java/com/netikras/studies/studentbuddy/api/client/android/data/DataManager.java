package com.netikras.studies.studentbuddy.api.client.android.data;

import com.netikras.studies.studentbuddy.api.client.android.data.stores.BaseDataStore;

/**
 * Created by netikras on 17.10.31.
 */

public interface DataManager {

    <S extends BaseDataStore> S getStore(Class<S> storeClass);
}
