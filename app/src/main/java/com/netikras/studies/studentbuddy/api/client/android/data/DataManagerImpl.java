package com.netikras.studies.studentbuddy.api.client.android.data;

import com.netikras.studies.studentbuddy.api.client.android.conf.di.AllDatastores;
import com.netikras.studies.studentbuddy.api.client.android.data.cache.CacheManager;
import com.netikras.studies.studentbuddy.api.client.android.data.stores.BaseDataStore;
import com.netikras.studies.studentbuddy.api.client.android.pieces.comments.data.CommentsDataStore;
import com.netikras.studies.studentbuddy.api.client.android.pieces.discipline.data.CourseDataStore;
import com.netikras.studies.studentbuddy.api.client.android.pieces.discipline.data.DisciplineDataStore;
import com.netikras.studies.studentbuddy.api.client.android.pieces.lecture.data.AssignmentDataStore;
import com.netikras.studies.studentbuddy.api.client.android.pieces.lecture.data.LectureDataStore;
import com.netikras.studies.studentbuddy.api.client.android.pieces.lecture.data.TestDataStore;
import com.netikras.studies.studentbuddy.api.client.android.pieces.lecturer.data.LecturerDataStore;
import com.netikras.studies.studentbuddy.api.client.android.pieces.location.data.FloorDataStore;
import com.netikras.studies.studentbuddy.api.client.android.pieces.location.data.RoomDataStore;
import com.netikras.studies.studentbuddy.api.client.android.pieces.person.data.PersonDataStore;
import com.netikras.studies.studentbuddy.api.client.android.pieces.person.data.UserDataStore;
import com.netikras.studies.studentbuddy.api.client.android.pieces.school.data.SchoolDataStore;
import com.netikras.studies.studentbuddy.api.client.android.pieces.school.data.SchoolDepartmentDataStore;
import com.netikras.studies.studentbuddy.api.client.android.pieces.student.data.GuestDataStore;
import com.netikras.studies.studentbuddy.api.client.android.pieces.student.data.StudentDataStore;
import com.netikras.studies.studentbuddy.api.client.android.pieces.student.data.StudentsGroupDataStore;

import org.apache.commons.lang3.ClassUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import static com.netikras.tools.common.security.IntegrityUtils.isNullOrEmpty;

/**
 * Created by netikras on 17.11.1.
 */

public class DataManagerImpl implements DataManager {

    @Inject
    @AllDatastores
    Map<Class<? extends BaseDataStore>, BaseDataStore> stores;

    @Inject
    CacheManager cacheManager;

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

    private <S extends BaseDataStore> void addStore(S store) {
        if (store != null) {
            List<Class<?>> types = ClassUtils.getAllInterfaces(store.getClass());

            if (!isNullOrEmpty(types)) {
                for (Class<?> type : types) {
                    if (BaseDataStore.class.isAssignableFrom(type)) {
                        stores.put((Class<? extends BaseDataStore>) type, store);
                    }
                }
            }

        }
    }

    private void fillStores() {

        addStore(userDataStore);
        addStore(personDataStore);
        addStore(disciplineDataStore);
        addStore(courseDataStore);
        addStore(guestDataStore);
        addStore(studentDataStore);
        addStore(lectureDataStore);
        addStore(lecturerDataStore);
        addStore(studentsGroupDataStore);
        addStore(schoolDataStore);
        addStore(schoolDepartmentDataStore);
        addStore(roomDataStore);
        addStore(floorDataStore);
        addStore(assignmentDataStore);
        addStore(testDataStore);
        addStore(commentsDataStore);
    }


    @Inject
    UserDataStore userDataStore;
    @Inject
    PersonDataStore personDataStore;
    @Inject
    DisciplineDataStore disciplineDataStore;
    @Inject
    CourseDataStore courseDataStore;
    @Inject
    GuestDataStore guestDataStore;
    @Inject
    StudentDataStore studentDataStore;
    @Inject
    LecturerDataStore lecturerDataStore;
    @Inject
    LectureDataStore lectureDataStore;
    @Inject
    StudentsGroupDataStore studentsGroupDataStore;
    @Inject
    SchoolDataStore schoolDataStore;
    @Inject
    SchoolDepartmentDataStore schoolDepartmentDataStore;
    @Inject
    RoomDataStore roomDataStore;
    @Inject
    FloorDataStore floorDataStore;
    @Inject
    AssignmentDataStore assignmentDataStore;
    @Inject
    TestDataStore testDataStore;
    @Inject
    CommentsDataStore commentsDataStore;

}
