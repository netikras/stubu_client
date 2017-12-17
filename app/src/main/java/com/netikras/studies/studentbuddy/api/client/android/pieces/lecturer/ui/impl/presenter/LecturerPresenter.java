package com.netikras.studies.studentbuddy.api.client.android.pieces.lecturer.ui.impl.presenter;

import com.netikras.studies.studentbuddy.api.client.android.data.DataManager;
import com.netikras.studies.studentbuddy.api.client.android.pieces.base.BasePresenter;
import com.netikras.studies.studentbuddy.api.client.android.pieces.discipline.ui.presenter.DisciplineMvpPresenter;
import com.netikras.studies.studentbuddy.api.client.android.pieces.discipline.ui.view.DisciplineMvpView;
import com.netikras.studies.studentbuddy.api.client.android.pieces.lecture.data.LectureDataStore;
import com.netikras.studies.studentbuddy.api.client.android.pieces.lecture.ui.presenter.LectureMvpPresenter;
import com.netikras.studies.studentbuddy.api.client.android.pieces.lecturer.data.LecturerDataStore;
import com.netikras.studies.studentbuddy.api.client.android.pieces.lecturer.ui.presenter.LecturerMvpPresenter;
import com.netikras.studies.studentbuddy.api.client.android.pieces.lecturer.ui.view.LecturerMvpView;
import com.netikras.studies.studentbuddy.api.client.android.pieces.person.ui.presenter.PersonMvpPresenter;
import com.netikras.studies.studentbuddy.api.client.android.pieces.person.ui.view.PersonMvpView;
import com.netikras.studies.studentbuddy.api.client.android.service.ServiceRequest;
import com.netikras.studies.studentbuddy.api.client.android.service.ServiceRequest.Subscriber;
import com.netikras.studies.studentbuddy.api.misc.TimeUnit;
import com.netikras.studies.studentbuddy.core.data.api.dto.school.LectureDto;
import com.netikras.studies.studentbuddy.core.data.api.dto.school.LecturerDto;

import java.util.Collection;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by netikras on 17.11.10.
 */

public class LecturerPresenter<V extends LecturerMvpView> extends BasePresenter<V> implements LecturerMvpPresenter<V> {

    @Inject
    DisciplineMvpPresenter<DisciplineMvpView> disciplinePresenter;

    @Inject
    PersonMvpPresenter<PersonMvpView> personPresenter;

    @Inject
    public LecturerPresenter(DataManager dataManager) {
        super(dataManager);
    }

    private LecturerDataStore getDataStore() {
        return getDataManager().getStore(LecturerDataStore.class);
    }

    @Override
    public void getById(Subscriber<LecturerDto> subscriber, String id) {
        getDataStore().getById(id, subscriber);
        getDataStore().processOrders(getContext());
    }

    @Override
    public void update(Subscriber<LecturerDto> subscriber, LecturerDto dto) {
        getDataStore().create(dto, subscriber);
        getDataStore().processOrders(getContext());
    }

    @Override
    public void getUpcomingLectures(Subscriber<Collection<LectureDto>> subscriber, String id) {
        getDataManager().getStore(LectureDataStore.class).getAllByLecturer(id, TimeUnit.HOURS, 24L, subscriber);
        getDataManager().getStore(LectureDataStore.class).processOrders(getContext());
    }
}
