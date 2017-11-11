package com.netikras.studies.studentbuddy.api.client.android.data;

import com.netikras.studies.studentbuddy.api.client.android.conf.di.AllDatastores;
import com.netikras.studies.studentbuddy.api.client.android.data.stores.BaseDataStore;
import com.netikras.studies.studentbuddy.api.client.android.pieces.discipline.data.DisciplineDataStore;
import com.netikras.studies.studentbuddy.api.client.android.pieces.person.data.PersonDataStore;
import com.netikras.studies.studentbuddy.api.client.android.pieces.person.data.UserDataStore;

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

    @Inject
    public DataManagerImpl() {

    }

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
        stores.put(DisciplineDataStore.class, disciplineDataStore);
    }


    @Inject
    UserDataStore userDataStore;
    @Inject
    PersonDataStore personDataStore;
    @Inject
    DisciplineDataStore disciplineDataStore;




}
