package com.netikras.studies.studentbuddy.api.client.android.pieces.main.ui.impl.presenter;

import com.netikras.studies.studentbuddy.api.client.android.App;
import com.netikras.studies.studentbuddy.api.client.android.data.DataManager;
import com.netikras.studies.studentbuddy.api.client.android.data.prefs.PreferencesHelper;
import com.netikras.studies.studentbuddy.api.client.android.pieces.base.BasePresenter;
import com.netikras.studies.studentbuddy.api.client.android.pieces.lecture.data.LectureDataStore;
import com.netikras.studies.studentbuddy.api.client.android.pieces.lecturer.data.LecturerDataStore;
import com.netikras.studies.studentbuddy.api.client.android.pieces.main.ui.presenter.MainMvpPresenter;
import com.netikras.studies.studentbuddy.api.client.android.pieces.main.ui.view.MainMvpView;
import com.netikras.studies.studentbuddy.api.client.android.pieces.settings.data.SettingsDataStore;
import com.netikras.studies.studentbuddy.api.client.android.pieces.student.data.GuestDataStore;
import com.netikras.studies.studentbuddy.api.client.android.pieces.student.data.StudentDataStore;
import com.netikras.studies.studentbuddy.api.client.android.service.ServiceRequest.Result;
import com.netikras.studies.studentbuddy.api.client.android.service.ServiceRequest.Subscriber;
import com.netikras.studies.studentbuddy.api.misc.TimeUnit;
import com.netikras.studies.studentbuddy.core.data.api.dto.PersonDto;
import com.netikras.studies.studentbuddy.core.data.api.dto.school.DisciplineDto;
import com.netikras.studies.studentbuddy.core.data.api.dto.school.LectureDto;
import com.netikras.studies.studentbuddy.core.data.api.dto.school.LectureGuestDto;
import com.netikras.studies.studentbuddy.core.data.api.dto.school.LecturerDto;
import com.netikras.studies.studentbuddy.core.data.api.dto.school.StudentDto;
import com.netikras.studies.studentbuddy.core.data.api.dto.school.StudentsGroupDto;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import static com.netikras.tools.common.security.IntegrityUtils.isNullOrEmpty;

/**
 * Created by netikras on 17.11.1.
 */

public class MainPresenter<V extends MainMvpView> extends BasePresenter<V> implements MainMvpPresenter<V> {

    @Inject
    App app;
    @Inject
    PreferencesHelper preferencesHelper;


    @Inject
    public MainPresenter(DataManager dataManager) {
        super(dataManager);
    }

    private LectureDataStore getLectureDataStore() {
        return getDataManager().getStore(LectureDataStore.class);
    }

    private StudentDataStore getStudentDataStore() {
        return getDataManager().getStore(StudentDataStore.class);
    }

    private LecturerDataStore getLecturerDataStore() {
        return getDataManager().getStore(LecturerDataStore.class);
    }

    private GuestDataStore getGuestDataStore() {
        return getDataManager().getStore(GuestDataStore.class);
    }

    private long getHours() {
        return preferencesHelper.getLecturesListDuration();
    }


    private List<StudentDto> getStudents() {
        return app.getRoles().getRoleStudents();
    }

    private List<LectureGuestDto> getGuests() {
        return app.getRoles().getRoleGuests();
    }

    private List<LecturerDto> getLecturers() {
        return app.getRoles().getRoleLecturers();
    }


    private Result<Boolean> studentsFetched = new Result<>(Boolean.FALSE);
    private Result<Boolean> guestsFetched = new Result<>(Boolean.FALSE);
    private Result<Boolean> lecturersFetched = new Result<>(Boolean.FALSE);


    private Subscriber<Collection<LectureDto>> wrapLecturesSubscriber(Subscriber<Collection<LectureDto>> subscriber) {

        Subscriber<Collection<LectureDto>> lecturesSubscriber = new Subscriber<Collection<LectureDto>>() {
            @Override
            public void onSuccess(Collection<LectureDto> response) {
                if (subscriber != null) {
                    subscriber.executeOnSuccess(response);
                }
            }
        };
        return lecturesSubscriber;
    }

    private List<LectureDto> getTestLectures() {
        List<LectureDto> data = new ArrayList<>();

        DisciplineDto d1 = new DisciplineDto();
        d1.setTitle("Matematika");
        LectureDto l1 = new LectureDto();
        l1.setDiscipline(d1);
        l1.setAssignments(Arrays.asList(null, null));
        l1.setTests(Arrays.asList(null, null));
        l1.setStartsOn(new Date(System.currentTimeMillis() + 100000));

        DisciplineDto d2 = new DisciplineDto();
        d2.setTitle("Anglų k.");
        LectureDto l2 = new LectureDto();
        l2.setDiscipline(d2);
        l2.setAssignments(Arrays.asList(null, null));
        l2.setTests(Arrays.asList(null, null));
        l2.setStartsOn(new Date(System.currentTimeMillis() + 332000));

        DisciplineDto d3= new DisciplineDto();
        d3.setTitle("Vokiečių k.");
        LectureDto l3 = new LectureDto();
        l3.setDiscipline(d3);
        l3.setAssignments(Arrays.asList(null, null));
        l3.setTests(Arrays.asList(null, null));
        l3.setStartsOn(new Date(System.currentTimeMillis() + 452000));


        data.add(l1);
        data.add(l2);
        data.add(l3);

        return data;
    }

    @Override
    public void fetchLecturesForStudent(Subscriber<Collection<LectureDto>> subscriber, PersonDto personDto) {

//        subscriber.executeOnSuccess(getTestLectures());
//
//        if (getTestLectures() != null) {
//            return;
//        }

        if (!studentsFetched.getValue()) {
            getStudentDataStore().getAllByPerson(personDto.getId(), new Subscriber<Collection<StudentDto>>() {
                @Override
                public void onSuccess(Collection<StudentDto> response) {
                    studentsFetched.setValue(true);
                    if (!isNullOrEmpty(response)) {
                        getStudents().clear();
                        getStudents().addAll(response);
                        doGetLecturesForStudents(response, getHours(), subscriber);
                    }
                }
            });
            getStudentDataStore().processOrders(getContext());
        } else {
            doGetLecturesForStudents(getStudents(), getHours(), subscriber);
        }
    }

    private void doGetLecturesForStudents(Collection<StudentDto> students, long hours, Subscriber<Collection<LectureDto>> subscriber) {
        if (!isNullOrEmpty(students)) {
            for (StudentDto studentDto : students) {
                getLectureDataStore().getAllByStudent(studentDto.getId(), TimeUnit.HOURS, hours, wrapLecturesSubscriber(subscriber));
            }
            getLectureDataStore().processOrders(getContext());
        }
    }


    @Override
    public void fetchLecturesForLecturer(Subscriber<Collection<LectureDto>> subscriber, PersonDto personDto) {

        if (!lecturersFetched.getValue()) {
            getLecturerDataStore().getAllByPerson(personDto.getId(), new Subscriber<Collection<LecturerDto>>() {
                @Override
                public void onSuccess(Collection<LecturerDto> response) {
                    lecturersFetched.setValue(true);
                    if (!isNullOrEmpty(response)) {
                        getLecturers().clear();
                        getLecturers().addAll(response);
                        doGetLecturesForLecturers(response, getHours(), subscriber);
                    }
                }
            });
            getLecturerDataStore().processOrders(getContext());
        } else {
            doGetLecturesForLecturers(getLecturers(), getHours(), subscriber);
        }
    }

    private void doGetLecturesForLecturers(Collection<LecturerDto> lecturers, long hours, Subscriber<Collection<LectureDto>> subscriber) {
        if (!isNullOrEmpty(lecturers)) {
            for (LecturerDto lecturerDto : lecturers) {
                getLectureDataStore().getAllByLecturer(lecturerDto.getId(), TimeUnit.HOURS, hours, wrapLecturesSubscriber(subscriber));
            }
            getLectureDataStore().processOrders(getContext());
        }

    }

    @Override
    public void fetchLecturesForGuest(Subscriber<Collection<LectureDto>> subscriber, PersonDto personDto) {

        if (!guestsFetched.getValue()) {
            getGuestDataStore().getAllByPerson(personDto.getId(), new Subscriber<Collection<LectureGuestDto>>() {
                @Override
                public void onSuccess(Collection<LectureGuestDto> response) {
                    guestsFetched.setValue(true);
                    if (!isNullOrEmpty(response)) {
                        getGuests().clear();
                        getGuests().addAll(response);
                        doGetLecturesForGuests(response, getHours(), subscriber);
                    }
                }
            });
            getGuestDataStore().processOrders(getContext());
        } else {
            doGetLecturesForGuests(getGuests(), getHours(), subscriber);
        }
    }

    private void doGetLecturesForGuests(Collection<LectureGuestDto> guests, long hours, Subscriber<Collection<LectureDto>> subscriber) {
        if (!isNullOrEmpty(guests)) {
            for (LectureGuestDto guestDto : guests) {
                getLectureDataStore().getAllByGuest(guestDto.getId(), TimeUnit.HOURS, hours, wrapLecturesSubscriber(subscriber));
            }
            getLectureDataStore().processOrders(getContext());
        }
    }
}
