package com.netikras.studies.studentbuddy.api.client.android.pieces.lecture.ui.presenter;

import android.content.Context;

import com.netikras.studies.studentbuddy.api.client.android.pieces.base.MvpPresenter;
import com.netikras.studies.studentbuddy.api.client.android.pieces.base.list.ListHandler;
import com.netikras.studies.studentbuddy.api.client.android.pieces.lecture.ui.impl.view.LectureInfoActivity;
import com.netikras.studies.studentbuddy.api.client.android.pieces.lecture.ui.view.LectureMvpView;
import com.netikras.studies.studentbuddy.core.data.api.dto.location.LectureRoomDto;
import com.netikras.studies.studentbuddy.core.data.api.dto.school.AssignmentDto;
import com.netikras.studies.studentbuddy.core.data.api.dto.school.DisciplineDto;
import com.netikras.studies.studentbuddy.core.data.api.dto.school.DisciplineTestDto;
import com.netikras.studies.studentbuddy.core.data.api.dto.school.LectureDto;
import com.netikras.studies.studentbuddy.core.data.api.dto.school.LectureGuestDto;
import com.netikras.studies.studentbuddy.core.data.api.dto.school.LecturerDto;
import com.netikras.studies.studentbuddy.core.data.api.dto.school.StudentsGroupDto;

/**
 * Created by netikras on 17.11.10.
 */

public interface LectureMvpPresenter<V extends LectureMvpView> extends MvpPresenter<V> {
    void showLecture(LectureDto lecture, Context fromContext);

    void showLocation(Context context, LectureRoomDto location);

    void showDiscipline(Context context, DisciplineDto discipline);

    void showLecturer(Context context, LecturerDto lecturer);

    void showGroup(Context context, StudentsGroupDto studentsGroup);

    void showGuest(Context context, LectureGuestDto item);

    void showTest(Context context, DisciplineTestDto item);

    void showAssignment(Context context, AssignmentDto item);
}
