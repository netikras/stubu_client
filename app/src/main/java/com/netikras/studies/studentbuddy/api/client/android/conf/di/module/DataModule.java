package com.netikras.studies.studentbuddy.api.client.android.conf.di.module;

import com.netikras.studies.studentbuddy.api.client.android.data.DataManager;
import com.netikras.studies.studentbuddy.api.client.android.data.DataManagerImpl;
import com.netikras.studies.studentbuddy.api.client.android.data.stores.BaseDataStore;
import com.netikras.studies.studentbuddy.api.client.android.data.stores.person.PersonDataStore;
import com.netikras.studies.studentbuddy.api.client.android.data.stores.person.impl.PersonDataStoreApiImpl;
import com.netikras.studies.studentbuddy.api.client.android.data.stores.user.UserDataStore;
import com.netikras.studies.studentbuddy.api.client.android.data.stores.user.UserDataStoreApiImpl;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by netikras on 17.11.1.
 */

@Module
public class DataModule {

    private Map<Class<? extends BaseDataStore>, BaseDataStore> stores = new HashMap<>();

    @Singleton
    @Provides
    DataManager dataManager() {
        DataManager manager = new DataManagerImpl(stores);
        return manager;
    }


    @Singleton
    @Provides
    PersonDataStore personDataStore() {
        PersonDataStore personDataStore = new PersonDataStoreApiImpl();
        stores.put(PersonDataStore.class, personDataStore);
        return personDataStore;
    }

    @Singleton
    @Provides
    UserDataStore userDataStore() {
        UserDataStore dataStore = new UserDataStoreApiImpl();
        stores.put(UserDataStore.class, dataStore);
        return dataStore;
    }

}
