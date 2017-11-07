package com.netikras.studies.studentbuddy.api.client.android.conf.di.module;

import com.netikras.studies.studentbuddy.api.client.android.App;
import com.netikras.studies.studentbuddy.api.client.android.conf.di.AllDatastores;
import com.netikras.studies.studentbuddy.api.client.android.conf.di.PreferenceInfo;
import com.netikras.studies.studentbuddy.api.client.android.data.DataManager;
import com.netikras.studies.studentbuddy.api.client.android.data.DataManagerImpl;
import com.netikras.studies.studentbuddy.api.client.android.data.prefs.AppPreferencesHelper;
import com.netikras.studies.studentbuddy.api.client.android.data.prefs.PreferencesHelper;
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

//    @Singleton
    @Provides
    DataManager dataManager(DataManagerImpl manager) {
//        DataManager manager = new DataManagerImpl(stores);
//        manager.setStores(st);
        return manager;
    }


    @Provides
    PreferencesHelper preferencesHelper(App app, @PreferenceInfo String prefsFileName) {
        return new AppPreferencesHelper(app, prefsFileName);
    }

//    @Singleton
    @AllDatastores
    @Provides
    Map<Class<? extends BaseDataStore>, BaseDataStore> allStores() {

        return stores;
    }

//    @Singleton
    @Provides
    PersonDataStore personDataStore(PersonDataStoreApiImpl store) {
//        stores.put(PersonDataStore.class, store);
        return store;
    }

//    @Singleton
    @Provides
    UserDataStore userDataStore(UserDataStoreApiImpl store) {
//        stores.put(UserDataStore.class, store);
        return store;
    }

    @Provides
    @PreferenceInfo
    String prefsFileName() {
        return "stubu_prefs";
    }

}
