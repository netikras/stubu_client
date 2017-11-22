package com.netikras.studies.studentbuddy.api.client.android.conf.di.module;

import com.netikras.studies.studentbuddy.api.client.android.App;
import com.netikras.studies.studentbuddy.api.client.android.conf.di.AllDatastores;
import com.netikras.studies.studentbuddy.api.client.android.conf.di.PreferenceInfo;
import com.netikras.studies.studentbuddy.api.client.android.data.DataManager;
import com.netikras.studies.studentbuddy.api.client.android.data.DataManagerImpl;
import com.netikras.studies.studentbuddy.api.client.android.data.prefs.AppPreferencesHelper;
import com.netikras.studies.studentbuddy.api.client.android.data.prefs.PreferencesHelper;
import com.netikras.studies.studentbuddy.api.client.android.data.stores.BaseDataStore;
import com.netikras.studies.studentbuddy.api.client.android.pieces.discipline.data.CourseDataStore;
import com.netikras.studies.studentbuddy.api.client.android.pieces.discipline.data.DisciplineDataStore;
import com.netikras.studies.studentbuddy.api.client.android.pieces.discipline.data.impl.CourseDataStoreApiImpl;
import com.netikras.studies.studentbuddy.api.client.android.pieces.discipline.data.impl.DisciplineDataStoreApiImpl;
import com.netikras.studies.studentbuddy.api.client.android.pieces.lecture.data.AssignmentDataStore;
import com.netikras.studies.studentbuddy.api.client.android.pieces.lecture.data.LectureDataStore;
import com.netikras.studies.studentbuddy.api.client.android.pieces.lecture.data.TestDataStore;
import com.netikras.studies.studentbuddy.api.client.android.pieces.lecture.data.impl.AssignmentDataStoreApiImpl;
import com.netikras.studies.studentbuddy.api.client.android.pieces.lecture.data.impl.LectureDataStoreApiImpl;
import com.netikras.studies.studentbuddy.api.client.android.pieces.lecture.data.impl.TestDataStoreApiImpl;
import com.netikras.studies.studentbuddy.api.client.android.pieces.lecturer.data.LecturerDataStore;
import com.netikras.studies.studentbuddy.api.client.android.pieces.lecturer.data.impl.LecturerDataStoreApiImpl;
import com.netikras.studies.studentbuddy.api.client.android.pieces.location.data.AddressDataStore;
import com.netikras.studies.studentbuddy.api.client.android.pieces.location.data.BuildingDataStore;
import com.netikras.studies.studentbuddy.api.client.android.pieces.location.data.BuildingSectionDataStore;
import com.netikras.studies.studentbuddy.api.client.android.pieces.location.data.FloorDataStore;
import com.netikras.studies.studentbuddy.api.client.android.pieces.location.data.LayoutDataStore;
import com.netikras.studies.studentbuddy.api.client.android.pieces.location.data.RoomDataStore;
import com.netikras.studies.studentbuddy.api.client.android.pieces.location.data.impl.AddressDataStoreApiImpl;
import com.netikras.studies.studentbuddy.api.client.android.pieces.location.data.impl.BuildingDataStoreApiImpl;
import com.netikras.studies.studentbuddy.api.client.android.pieces.location.data.impl.BuildingSectionDataStoreApiImpl;
import com.netikras.studies.studentbuddy.api.client.android.pieces.location.data.impl.FloorDataStoreApiImpl;
import com.netikras.studies.studentbuddy.api.client.android.pieces.location.data.impl.LayoutDataStoreApiImpl;
import com.netikras.studies.studentbuddy.api.client.android.pieces.location.data.impl.RoomDataStoreApiImpl;
import com.netikras.studies.studentbuddy.api.client.android.pieces.person.data.PersonDataStore;
import com.netikras.studies.studentbuddy.api.client.android.pieces.person.data.impl.PersonDataStoreApiImpl;
import com.netikras.studies.studentbuddy.api.client.android.pieces.person.data.UserDataStore;
import com.netikras.studies.studentbuddy.api.client.android.pieces.person.data.impl.UserDataStoreApiImpl;
import com.netikras.studies.studentbuddy.api.client.android.pieces.school.data.PersonnelDataStore;
import com.netikras.studies.studentbuddy.api.client.android.pieces.school.data.SchoolDataStore;
import com.netikras.studies.studentbuddy.api.client.android.pieces.school.data.SchoolDepartmentDataStore;
import com.netikras.studies.studentbuddy.api.client.android.pieces.school.data.impl.PersonnelDataStoreApiImpl;
import com.netikras.studies.studentbuddy.api.client.android.pieces.school.data.impl.SchoolDataStoreApiImpl;
import com.netikras.studies.studentbuddy.api.client.android.pieces.school.data.impl.SchoolDepartmentDataStoreApiImpl;
import com.netikras.studies.studentbuddy.api.client.android.pieces.student.data.GuestDataStore;
import com.netikras.studies.studentbuddy.api.client.android.pieces.student.data.StudentDataStore;
import com.netikras.studies.studentbuddy.api.client.android.pieces.student.data.StudentsGroupDataStore;
import com.netikras.studies.studentbuddy.api.client.android.pieces.student.data.impl.GuestDataStoreApiImpl;
import com.netikras.studies.studentbuddy.api.client.android.pieces.student.data.impl.StudentDataStoreApiImpl;
import com.netikras.studies.studentbuddy.api.client.android.pieces.student.data.impl.StudentsGroupDataStoreApiImpl;
import com.netikras.studies.studentbuddy.api.client.android.pieces.sys.data.PasswordReqDataStore;
import com.netikras.studies.studentbuddy.api.client.android.pieces.sys.data.StatusDataStore;
import com.netikras.studies.studentbuddy.api.client.android.pieces.sys.data.SystemSettingsDataStore;
import com.netikras.studies.studentbuddy.api.client.android.pieces.sys.data.impl.PasswordReqDataStoreApiImpl;
import com.netikras.studies.studentbuddy.api.client.android.pieces.sys.data.impl.StatusDataStoreApiImpl;
import com.netikras.studies.studentbuddy.api.client.android.pieces.sys.data.impl.SystemSettingsDataStoreApiImpl;

import java.util.HashMap;
import java.util.Map;

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


    @Provides
    DisciplineDataStore disciplineDataStore(DisciplineDataStoreApiImpl store) {
        return store;
    }

    @Provides
    CourseDataStore courseDataStore(CourseDataStoreApiImpl store) {
        return store;
    }

    @Provides
    AssignmentDataStore assingmentDataStore(AssignmentDataStoreApiImpl store) {
        return store;
    }

    @Provides
    LectureDataStore lectureDataStore(LectureDataStoreApiImpl store) {
        return store;
    }

    @Provides
    TestDataStore testDataStore(TestDataStoreApiImpl store) {
        return store;
    }

    @Provides
    LecturerDataStore lecturerDataStore(LecturerDataStoreApiImpl store) {
        return store;
    }

    @Provides
    AddressDataStore addressDataStore(AddressDataStoreApiImpl store) {
        return store;
    }

    @Provides
    BuildingDataStore buildingDataStore(BuildingDataStoreApiImpl store) {
        return store;
    }

    @Provides
    BuildingSectionDataStore buildingSectionDataStore(BuildingSectionDataStoreApiImpl store) {
        return store;
    }

    @Provides
    FloorDataStore floorDataStore(FloorDataStoreApiImpl store) {
        return store;
    }

    @Provides
    LayoutDataStore layoutDataStore(LayoutDataStoreApiImpl store) {
        return store;
    }

    @Provides
    RoomDataStore roomDataStore(RoomDataStoreApiImpl store) {
        return store;
    }

    @Provides
    PersonnelDataStore personnelDataStore(PersonnelDataStoreApiImpl store) {
        return store;
    }

    @Provides
    SchoolDataStore schoolDataStore(SchoolDataStoreApiImpl store) {
        return store;
    }

    @Provides
    SchoolDepartmentDataStore schoolDepartmentDataStore(SchoolDepartmentDataStoreApiImpl store) {
        return store;
    }

    @Provides
    GuestDataStore guestDataStore(GuestDataStoreApiImpl store) {
        return store;
    }

    @Provides
    StudentDataStore studentDataStore(StudentDataStoreApiImpl store) {
        return store;
    }

    @Provides
    StudentsGroupDataStore studentsGroupDataStore(StudentsGroupDataStoreApiImpl store) {
        return store;
    }

    @Provides
    PasswordReqDataStore passwordReqDataStore(PasswordReqDataStoreApiImpl store) {
        return store;
    }

    @Provides
    SystemSettingsDataStore systemSettingsDataStore(SystemSettingsDataStoreApiImpl store) {
        return store;
    }

    @Provides
    StatusDataStore statusDataStore(StatusDataStoreApiImpl store) {
        return store;
    }

}
