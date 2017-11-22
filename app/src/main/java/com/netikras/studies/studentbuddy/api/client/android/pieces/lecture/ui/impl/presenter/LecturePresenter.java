package com.netikras.studies.studentbuddy.api.client.android.pieces.lecture.ui.impl.presenter;

import android.content.Context;

import com.netikras.studies.studentbuddy.api.client.android.data.DataManager;
import com.netikras.studies.studentbuddy.api.client.android.pieces.base.BaseActivity;
import com.netikras.studies.studentbuddy.api.client.android.pieces.base.BaseActivity.ViewTask;
import com.netikras.studies.studentbuddy.api.client.android.pieces.base.BasePresenter;
import com.netikras.studies.studentbuddy.api.client.android.pieces.discipline.ui.presenter.DisciplineMvpPresenter;
import com.netikras.studies.studentbuddy.api.client.android.pieces.discipline.ui.view.DisciplineMvpView;
import com.netikras.studies.studentbuddy.api.client.android.pieces.lecture.data.LectureDataStore;
import com.netikras.studies.studentbuddy.api.client.android.pieces.lecture.ui.impl.view.LectureInfoActivity;
import com.netikras.studies.studentbuddy.api.client.android.pieces.lecture.ui.presenter.LectureMvpPresenter;
import com.netikras.studies.studentbuddy.api.client.android.pieces.lecture.ui.view.LectureMvpView;
import com.netikras.studies.studentbuddy.api.client.android.pieces.lecturer.ui.presenter.LecturerMvpPresenter;
import com.netikras.studies.studentbuddy.api.client.android.pieces.lecturer.ui.view.LecturerMvpView;
import com.netikras.studies.studentbuddy.api.client.android.pieces.location.ui.presenter.LocationMvpPresenter;
import com.netikras.studies.studentbuddy.api.client.android.pieces.location.ui.view.LocationMvpView;
import com.netikras.studies.studentbuddy.api.client.android.pieces.student.ui.presenter.StudentsGroupMvpPresenter;
import com.netikras.studies.studentbuddy.api.client.android.pieces.student.ui.view.StudentsGroupMvpView;
import com.netikras.studies.studentbuddy.api.client.android.util.CommonUtils;
import com.netikras.studies.studentbuddy.core.data.api.dto.location.LectureRoomDto;
import com.netikras.studies.studentbuddy.core.data.api.dto.school.DisciplineDto;
import com.netikras.studies.studentbuddy.core.data.api.dto.school.LectureDto;
import com.netikras.studies.studentbuddy.core.data.api.dto.school.LecturerDto;
import com.netikras.studies.studentbuddy.core.data.api.dto.school.StudentsGroupDto;

import javax.inject.Inject;

/**
 * Created by netikras on 17.11.10.
 */

public class LecturePresenter<V extends LectureMvpView> extends BasePresenter<V> implements LectureMvpPresenter<V> {

    @Inject
    LocationMvpPresenter<LocationMvpView> locationPresenter;
    @Inject
    DisciplineMvpPresenter<DisciplineMvpView> disciplinePresenter;
    @Inject
    StudentsGroupMvpPresenter<StudentsGroupMvpView> groupPresenter;
    @Inject
    LecturerMvpPresenter<LecturerMvpView> lecturerPresenter;

    @Inject
    public LecturePresenter(DataManager dataManager) {
        super(dataManager);
    }

    private LectureDataStore getDataStore() {
        return getDataManager().getStore(LectureDataStore.class);
    }

    @Override
    public void startView(Context fromContext) {
        startView(fromContext, LectureInfoActivity.class);
    }

    @Override
    public void showLecture(LectureDto lecture, Context fromContext) {
        startView(fromContext, LectureInfoActivity.class, new ViewTask<LectureInfoActivity>() {
            @Override
            public void execute() {
                getActivity().show(lecture);
            }
        });
    }

    @Override
    public void showLocation(Context context, LectureRoomDto room) {
        if (room == null) {
            return;
        }
        locationPresenter.show(context, null, null, null, null, room);
    }

    @Override
    public void showDiscipline(Context context, DisciplineDto discipline) {
        disciplinePresenter.show(context, discipline);
    }

    @Override
    public void showLecturer(Context context, LecturerDto lecturer) {
        lecturerPresenter.show(context, lecturer);
    }

    @Override
    public void showGroup(Context context, StudentsGroupDto studentsGroup) {
        groupPresenter.show(context, studentsGroup);
    }
}
