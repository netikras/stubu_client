package com.netikras.studies.studentbuddy.api.client.android.pieces.main.ui.impl.presenter;

import com.netikras.studies.studentbuddy.api.client.android.App;
import com.netikras.studies.studentbuddy.api.client.android.data.DataManager;
import com.netikras.studies.studentbuddy.api.client.android.data.cache.CacheManager;
import com.netikras.studies.studentbuddy.api.client.android.pieces.lecturer.data.cahe.LecturerDao;
import com.netikras.studies.studentbuddy.api.client.android.data.prefs.PreferencesHelper;
import com.netikras.studies.studentbuddy.api.client.android.pieces.base.BasePresenter;
import com.netikras.studies.studentbuddy.api.client.android.pieces.lecture.data.LectureDataStore;
import com.netikras.studies.studentbuddy.api.client.android.pieces.lecturer.data.LecturerDataStore;
import com.netikras.studies.studentbuddy.api.client.android.pieces.main.ui.presenter.MainMvpPresenter;
import com.netikras.studies.studentbuddy.api.client.android.pieces.main.ui.view.MainMvpView;
import com.netikras.studies.studentbuddy.api.client.android.pieces.person.data.PersonDataStore;
import com.netikras.studies.studentbuddy.api.client.android.pieces.person.data.UserDataStore;
import com.netikras.studies.studentbuddy.api.client.android.pieces.student.data.GuestDataStore;
import com.netikras.studies.studentbuddy.api.client.android.pieces.student.data.StudentDataStore;
import com.netikras.studies.studentbuddy.api.client.android.service.ServiceRequest.Result;
import com.netikras.studies.studentbuddy.api.client.android.service.ServiceRequest.Subscriber;
import com.netikras.studies.studentbuddy.api.misc.TimeUnit;
import com.netikras.studies.studentbuddy.core.data.api.dto.PersonDto;
import com.netikras.studies.studentbuddy.core.data.api.dto.meta.UserDto;
import com.netikras.studies.studentbuddy.core.data.api.dto.school.DisciplineDto;
import com.netikras.studies.studentbuddy.core.data.api.dto.school.LectureDto;
import com.netikras.studies.studentbuddy.core.data.api.dto.school.LectureGuestDto;
import com.netikras.studies.studentbuddy.core.data.api.dto.school.LecturerDto;
import com.netikras.studies.studentbuddy.core.data.api.dto.school.StudentDto;
import com.netikras.tools.common.exception.ErrorsCollection;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    private LecturerDao lecturerCache;

    @Inject
    public MainPresenter(DataManager dataManager, CacheManager cacheManager) {
        super(dataManager);
        lecturerCache = cacheManager.getDao(LecturerDao.class);
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

    private PersonDataStore getPersonDataStore() {
        return getDataManager().getStore(PersonDataStore.class);
    }

    private long getHours() {
        return preferencesHelper.getFetchLecturesAheadHours();
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

        DisciplineDto d3 = new DisciplineDto();
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


    private void appendPerson(Collection<LectureDto> lectureDtos) {
        if (!isNullOrEmpty(lectureDtos)) {
            for (LectureDto lectureDto : lectureDtos) {
                if (lectureDto == null) {
                    continue;
                }

                if (lectureDto.getLecturer() == null) {
                    continue;
                }

                PersonDto personDto = lectureDto.getLecturer().getPerson();

                if (personDto == null) {
                    LecturerDto cachedLecturer = lecturerCache.fill(lecturerCache.get(lectureDto.getLecturer().getId()));
                    if (cachedLecturer != null) {
                        lectureDto.getLecturer().setPerson(cachedLecturer.getPerson());
                    }
                }
            }
        }
    }

    @Override
    public void fetchLecturesForStudent(Subscriber<Collection<LectureDto>> subscriber, PersonDto personDto) {
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

            final Set<StudentDto> pendingStudents = Collections.synchronizedSet(new HashSet<>(students));
            final List<LectureDto> lectureDtos = Collections.synchronizedList(new ArrayList<>());

            for (StudentDto studentDto : students) {
                getLectureDataStore().getAllByStudent(studentDto.getId(), TimeUnit.HOURS, hours, new Subscriber<Collection<LectureDto>>() {
                    @Override
                    public void onError(ErrorsCollection errors) {
                        subscriber.onError(errors);
                    }

                    @Override
                    public void onCacheHit(Collection<LectureDto> response) {
                        subscriber.onCacheHit(response);
                    }

                    @Override
                    public synchronized void onSuccess(Collection<LectureDto> response) {
                        pendingStudents.remove(studentDto);
                        if (response != null) {
                            lectureDtos.addAll(response);
                        }
                        if (pendingStudents.isEmpty()) {
                            appendPerson(lectureDtos);
                            subscriber.onSuccess(lectureDtos);
                        }
                    }
                });
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

    /**
     * Concatenating all results and delegating to subscriber only when all results are fetched.
     */
    private void doGetLecturesForLecturers(Collection<LecturerDto> lecturers, long hours, Subscriber<Collection<LectureDto>> subscriber) {
        if (!isNullOrEmpty(lecturers)) {

            final Set<LecturerDto> pendingLecturers = Collections.synchronizedSet(new HashSet<>(lecturers));
            final List<LectureDto> lectureDtos = Collections.synchronizedList(new ArrayList<>());

            for (LecturerDto lecturerDto : lecturers) {
                getLectureDataStore().getAllByLecturer(lecturerDto.getId(), TimeUnit.HOURS, hours, new Subscriber<Collection<LectureDto>>() {
                    @Override
                    public void onError(ErrorsCollection errors) {
                        subscriber.onError(errors);
                    }

                    @Override
                    public void onCacheHit(Collection<LectureDto> response) {
                        subscriber.onCacheHit(response);
                    }

                    @Override
                    public synchronized void onSuccess(Collection<LectureDto> response) {
                        pendingLecturers.remove(lecturerDto);
                        if (response != null) {
                            lectureDtos.addAll(response);
                        }
                        if (pendingLecturers.isEmpty()) {
                            appendPerson(lectureDtos);
                            subscriber.onSuccess(lectureDtos);
                        }
                    }
                });
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

    @Override
    public void getCurrentUser(Subscriber<UserDto> subscriber) {
        getDataManager().getStore(UserDataStore.class).getCurrentUser(subscriber);
        getDataManager().getStore(UserDataStore.class).processOrders(getContext());
    }

    private void doGetLecturesForGuests(Collection<LectureGuestDto> guests, long hours, Subscriber<Collection<LectureDto>> subscriber) {
        if (!isNullOrEmpty(guests)) {

            final Set<LectureGuestDto> pendingGuests = Collections.synchronizedSet(new HashSet<>(guests));
            final List<LectureDto> lectureDtos = Collections.synchronizedList(new ArrayList<>());

            for (LectureGuestDto guestDto : guests) {
                getLectureDataStore().getAllByGuest(guestDto.getId(), TimeUnit.HOURS, hours, new Subscriber<Collection<LectureDto>>() {
                    @Override
                    public void onError(ErrorsCollection errors) {
                        subscriber.onError(errors);
                    }

                    @Override
                    public void onCacheHit(Collection<LectureDto> response) {
                        subscriber.onCacheHit(response);
                    }

                    @Override
                    public synchronized void onSuccess(Collection<LectureDto> response) {
                        pendingGuests.remove(guestDto);
                        if (response != null) {
                            lectureDtos.addAll(response);
                        }
                        if (pendingGuests.isEmpty()) {
                            appendPerson(lectureDtos);
                            subscriber.onSuccess(lectureDtos);
                        }
                    }
                });
            }

            getLectureDataStore().processOrders(getContext());
        }
    }
}
