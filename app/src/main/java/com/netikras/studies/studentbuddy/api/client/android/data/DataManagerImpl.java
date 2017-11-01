package com.netikras.studies.studentbuddy.api.client.android.data;

import com.netikras.studies.studentbuddy.api.client.android.data.stores.BaseDataStore;

import java.util.Map;

/**
 * Created by netikras on 17.11.1.
 */

public class DataManagerImpl implements DataManager {

    private Map<Class<? extends BaseDataStore>, BaseDataStore> stores;


    public DataManagerImpl(Map<Class<? extends BaseDataStore>, BaseDataStore> stores) {
        this.stores = stores;
    }

    @Override
    public <S extends BaseDataStore> S getStore(Class<S> storeClass) {
        return null;
    }

}
