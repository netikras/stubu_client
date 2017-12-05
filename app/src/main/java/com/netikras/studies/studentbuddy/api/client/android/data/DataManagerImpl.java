package com.netikras.studies.studentbuddy.api.client.android.data;

import com.netikras.studies.studentbuddy.api.client.android.conf.di.AllDatastores;
import com.netikras.studies.studentbuddy.api.client.android.data.stores.BaseDataStore;
import com.netikras.studies.studentbuddy.api.client.android.pieces.discipline.data.DisciplineDataStore;
import com.netikras.studies.studentbuddy.api.client.android.pieces.lecture.data.LectureDataStore;
import com.netikras.studies.studentbuddy.api.client.android.pieces.lecturer.data.LecturerDataStore;
import com.netikras.studies.studentbuddy.api.client.android.pieces.person.data.PersonDataStore;
import com.netikras.studies.studentbuddy.api.client.android.pieces.person.data.UserDataStore;
import com.netikras.studies.studentbuddy.api.client.android.pieces.student.data.GuestDataStore;
import com.netikras.studies.studentbuddy.api.client.android.pieces.student.data.StudentDataStore;

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
        stores.put(GuestDataStore.class, guestDataStore);
        stores.put(StudentDataStore.class, studentDataStore);
        stores.put(LecturerDataStore.class, lecturerDataStore);
        stores.put(LectureDataStore.class, lectureDataStore);

    }


    @Inject
    UserDataStore userDataStore;
    @Inject
    PersonDataStore personDataStore;
    @Inject
    DisciplineDataStore disciplineDataStore;
    @Inject
    GuestDataStore guestDataStore;
    @Inject
    StudentDataStore studentDataStore;
    @Inject
    LecturerDataStore lecturerDataStore;
    @Inject
    LectureDataStore lectureDataStore;



}
