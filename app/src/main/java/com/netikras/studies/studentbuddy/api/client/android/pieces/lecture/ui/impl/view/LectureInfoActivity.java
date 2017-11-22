package com.netikras.studies.studentbuddy.api.client.android.pieces.lecture.ui.impl.view;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.netikras.studies.studentbuddy.api.client.android.R;
import com.netikras.studies.studentbuddy.api.client.android.conf.di.DepInjector;
import com.netikras.studies.studentbuddy.api.client.android.pieces.base.BaseActivity;
import com.netikras.studies.studentbuddy.api.client.android.pieces.base.BaseViewFields;
import com.netikras.studies.studentbuddy.api.client.android.pieces.lecture.ui.presenter.LectureMvpPresenter;
import com.netikras.studies.studentbuddy.api.client.android.pieces.lecture.ui.view.LectureMvpView;
import com.netikras.studies.studentbuddy.core.data.api.dto.meta.CommentDto;
import com.netikras.studies.studentbuddy.core.data.api.dto.school.AssignmentDto;
import com.netikras.studies.studentbuddy.core.data.api.dto.school.DisciplineTestDto;
import com.netikras.studies.studentbuddy.core.data.api.dto.school.LectureDto;
import com.netikras.studies.studentbuddy.core.data.api.dto.school.LectureGuestDto;
import com.netikras.studies.studentbuddy.core.data.api.dto.school.LecturerDto;
import com.netikras.studies.studentbuddy.core.data.api.dto.school.StudentsGroupDto;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

import static com.netikras.studies.studentbuddy.api.client.android.util.AppConstants.DAY_IN_MS;
import static com.netikras.studies.studentbuddy.api.client.android.util.AppConstants.HOUR_IN_MS;
import static com.netikras.studies.studentbuddy.api.client.android.util.AppConstants.MINUTE_IN_MS;
import static com.netikras.studies.studentbuddy.api.client.android.util.CommonUtils.datetimeToDate;
import static com.netikras.studies.studentbuddy.api.client.android.util.CommonUtils.datetimeToTime;
import static com.netikras.tools.common.security.IntegrityUtils.isNullOrEmpty;
import static java.lang.String.valueOf;

public class LectureInfoActivity extends BaseActivity implements LectureMvpView {

    private static LectureDto current;

    private ViewFields fields;

    @Inject
    LectureMvpPresenter<LectureMvpView> presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lecture_info);
        setUp();
    }

    @Override
    protected void setUp() {
        DepInjector.inject(this);
        onAttach(this);
        presenter.onAttach(this);
        fields = initFields(new ViewFields());
        addMenu(R.id.btn_lecture_main_menu);
    }

    @Override
    public void show(LectureDto lectureDto) {
        if (lectureDto == null) {
            fields.clean();
            return;
        }

        fields.setId(lectureDto.getId());
        fields.setAssignments(lectureDto.getAssignments());
        fields.setTests(lectureDto.getTests());
        fields.setStudentsGroup(lectureDto.getStudentsGroup());
        fields.setLecturer(lectureDto.getLecturer());
        fields.setComments(lectureDto.getComments());
        fields.setStartDatetime(lectureDto.getStartsOn());

        if (lectureDto.getDiscipline() != null) {
            fields.setNameName(lectureDto.getDiscipline().getTitle());
        }

    }


    class ViewFields extends BaseViewFields {

        @BindView(R.id.txt_edit_lecture_id)
        EditText id;

        @BindView(R.id.txt_edit_lecture_start_date)
        EditText startDate;

        @BindView(R.id.txt_edit_lecture_start_time)
        EditText startTime;

        @BindView(R.id.txt_edit_lecture_time_remaining)
        EditText timeRemaining;

        @BindView(R.id.btn_lecture_assignments)
        Button assignments;

        @BindView(R.id.btn_lecture_comments)
        Button comments;

        @BindView(R.id.btn_lecture_guests)
        Button guests;

        @BindView(R.id.btn_lecture_lecturer)
        Button lecturer;

        @BindView(R.id.btn_lecture_location)
        Button location;

        @BindView(R.id.btn_lecture_name)
        Button name;

        @BindView(R.id.btn_lecture_students_group)
        Button studentsGroup;

        @BindView(R.id.btn_lecture_tests)
        Button tests;

        public String getId() {
            return getString(id);
        }

        public void setId(String id) {
            setString(this.id, id);
        }

        public String getStartDate() {
            return getString(startDate);
        }

        public void setStartDate(String startDate) {
            setString(this.startDate, startDate);
            calcRemainingTime();
        }

        public String getStartTime() {
            return getString(startTime);
        }

        public void setStartTime(String startTime) {
            setString(this.startTime, startTime);
            calcRemainingTime();
        }

        public void setStartDatetime(Date datetime) {
            setStartDate(datetimeToDate(datetime));
            setStartTime(datetimeToTime(datetime));
        }

        public Date getStartDatetime() {
            return getDatetime(getStartDate(), getStartTime());
        }

        public String getTimeRemaining() {
            return getString(timeRemaining);
        }

        public void setTimeRemaining(String timeRemaining) {
            setString(this.timeRemaining, timeRemaining);
        }

        // buttons below
        public String getAssignmentsCount() {
            return getString(assignments);
        }

        public void setAssignmentsCount(String assignments) {
            setString(this.assignments, assignments);
        }

        public List<AssignmentDto> getAssignments() {
            return (List<AssignmentDto>) getTag(assignments);
        }

        public void setAssignments(List<AssignmentDto> assignments) {
            setTag(this.assignments, assignments);
            if (assignments != null) {
                setAssignmentsCount(valueOf(assignments.size()));
            }
        }

        public String getCommentsCount() {
            return getString(comments);
        }

        public void setCommentsCount(String comments) {
            setString(this.comments, comments);
        }

        public List<CommentDto> getComments() {
            return (List<CommentDto>) getTag(comments);
        }

        public void setComments(List<CommentDto> data) {
            setTag(comments, data);
            if (data != null) {
                setCommentsCount(valueOf(data.size()));
            }
        }

        public String getGuestsCount() {
            return getString(guests);
        }

        public void setGuestsCount(String guests) {
            setString(this.guests, guests);
        }

        public List<LectureGuestDto> getGuests() {
            return (List<LectureGuestDto>) getTag(guests);
        }

        public void setGuests(List<LectureGuestDto> data) {
            setTag(guests, data);
            if (data != null) {
                setGuestsCount(valueOf(data.size()));
            }
        }

        public String getLecturerName() {
            return getString(lecturer);
        }

        public void setLecturerName(String lecturer) {
            setString(this.lecturer, lecturer);
        }

        public LecturerDto getLecturer() {
            return (LecturerDto) getTag(lecturer);
        }

        public void setLecturer(LecturerDto data) {
            setTag(lecturer, data);
            if (data != null && data.getPerson() != null) {
                setLecturerName(data.getPerson().getFirstName() + " " + data.getPerson().getLastName());
            }
        }

        public String getLocationName() {
            return getString(location);
        }

        public void setLocationName(String location) {
            setString(this.location, location);
        }


        public String getNameName() {
            return getString(name);
        }

        public void setNameName(String name) {
            setString(this.name, name);
        }

        public String getStudentsGroupName() {
            return getString(studentsGroup);
        }

        public void setStudentsGroupName(String studentsGroup) {
            setString(this.studentsGroup, studentsGroup);
        }

        public StudentsGroupDto getStudentsGroup() {
            return (StudentsGroupDto) getTag(studentsGroup);
        }

        public void setStudentsGroup(StudentsGroupDto data) {
            setTag(studentsGroup, data);
            if (data != null) {
                setStudentsGroupName(data.getTitle());
            }
        }

        public String getTestsCount() {
            return getString(tests);
        }

        public void setTestsCount(String tests) {
            setString(this.tests, tests);
        }

        public List<DisciplineTestDto> getTests() {
            return (List<DisciplineTestDto>) getTag(tests);
        }

        public void setTests(List<DisciplineTestDto> data) {
            setTag(tests, data);
            if (data != null) {
                setTestsCount(valueOf(data.size()));
            }
        }


        private void calcRemainingTime() {
            StringBuilder builder = new StringBuilder();
            if (!isNullOrEmpty(getStartDate())) {
                if (!isNullOrEmpty(getStartTime())) {
                    long remaining = getStartDatetime().getTime() - new Date().getTime();

                    if (remaining < 0) {
                        builder.append("-");
                    }

                    if (remaining > DAY_IN_MS) {
                        long days = remaining / (DAY_IN_MS);
                        builder.append(days).append("d. ");
                        remaining %= (DAY_IN_MS);
                    }

                    builder.append(remaining / HOUR_IN_MS).append(":");
                    remaining %= HOUR_IN_MS;
                    builder.append(remaining / MINUTE_IN_MS);

                }
            }
            if (builder.length() > 0) {
                setTimeRemaining(builder.toString());
            }
        }

        @Override
        protected Collection<TextView> getAllFields() {
            return null;
        }
    }

}
