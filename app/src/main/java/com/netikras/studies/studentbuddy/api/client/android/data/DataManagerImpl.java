package com.netikras.studies.studentbuddy.api.client.android.data;

import com.netikras.studies.studentbuddy.api.client.android.conf.di.AllDatastores;
import com.netikras.studies.studentbuddy.api.client.android.data.stores.BaseDataStore;
import com.netikras.studies.studentbuddy.api.client.android.data.stores.person.PersonDataStore;
import com.netikras.studies.studentbuddy.api.client.android.data.stores.user.UserDataStore;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

/**
 * Created by netikras on 17.11.1.
 */

public class DataManagerImpl implements DataManager {

    @Inject
    @AllDatastores
    Map<Class<? extends BaseDataStore>, BaseDataStore> stores;


//    public DataManagerImpl(Map<Class<? extends BaseDataStore>, BaseDataStore> stores) {
//        this.stores = stores;
//    }

    @Inject
    public DataManagerImpl() {

    }
//
//    public void setStores(Map<Class<? extends BaseDataStore>, BaseDataStore> stores) {
//        this.stores = stores;
//    }

    @Override
    public <S extends BaseDataStore> S getStore(Class<S> storeClass) {
        prepare();
        return (S) stores.get(storeClass);
    }

    private void prepare() {
        if (stores == null) {
            stores = new HashMap<>();
        }

        if (stores.isEmpty()) {
            fillStores();
        }
    }

    private void fillStores() {
        stores.put(UserDataStore.class, userDataStore);
        stores.put(PersonDataStore.class, personDataStore);
    }


    @Inject
    UserDataStore userDataStore;
    @Inject
    PersonDataStore personDataStore;




}
