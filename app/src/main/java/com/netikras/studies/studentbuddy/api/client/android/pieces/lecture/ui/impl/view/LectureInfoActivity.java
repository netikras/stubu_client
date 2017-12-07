package com.netikras.studies.studentbuddy.api.client.android.pieces.lecture.ui.impl.view;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.netikras.studies.studentbuddy.api.client.android.R;
import com.netikras.studies.studentbuddy.api.client.android.conf.di.DepInjector;
import com.netikras.studies.studentbuddy.api.client.android.pieces.base.BaseActivity;
import com.netikras.studies.studentbuddy.api.client.android.pieces.base.BaseViewFields;
import com.netikras.studies.studentbuddy.api.client.android.pieces.base.list.ListHandler;
import com.netikras.studies.studentbuddy.api.client.android.pieces.discipline.ui.impl.view.DisciplineInfoActivity;
import com.netikras.studies.studentbuddy.api.client.android.pieces.lecture.ui.presenter.LectureMvpPresenter;
import com.netikras.studies.studentbuddy.api.client.android.pieces.lecture.ui.view.LectureMvpView;
import com.netikras.studies.studentbuddy.api.client.android.pieces.lecturer.ui.impl.view.LecturerInfoActivity;
import com.netikras.studies.studentbuddy.api.client.android.pieces.location.ui.impl.view.LocationInfoActivity;
import com.netikras.studies.studentbuddy.api.client.android.pieces.student.ui.impl.view.GuestActivity;
import com.netikras.studies.studentbuddy.api.client.android.pieces.student.ui.impl.view.StudentsGroupInfoActivity;
import com.netikras.studies.studentbuddy.api.client.android.service.ServiceRequest;
import com.netikras.studies.studentbuddy.core.data.api.dto.location.LectureRoomDto;
import com.netikras.studies.studentbuddy.core.data.api.dto.meta.CommentDto;
import com.netikras.studies.studentbuddy.core.data.api.dto.school.AssignmentDto;
import com.netikras.studies.studentbuddy.core.data.api.dto.school.DisciplineDto;
import com.netikras.studies.studentbuddy.core.data.api.dto.school.DisciplineTestDto;
import com.netikras.studies.studentbuddy.core.data.api.dto.school.LectureDto;
import com.netikras.studies.studentbuddy.core.data.api.dto.school.LectureGuestDto;
import com.netikras.studies.studentbuddy.core.data.api.dto.school.LecturerDto;
import com.netikras.studies.studentbuddy.core.data.api.dto.school.StudentsGroupDto;

import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

import static com.netikras.studies.studentbuddy.api.client.android.util.AppConstants.DAY_IN_MS;
import static com.netikras.studies.studentbuddy.api.client.android.util.AppConstants.HOUR_IN_MS;
import static com.netikras.studies.studentbuddy.api.client.android.util.AppConstants.MINUTE_IN_MS;
import static com.netikras.studies.studentbuddy.api.client.android.util.CommonUtils.datetimeToDate;
import static com.netikras.studies.studentbuddy.api.client.android.util.CommonUtils.datetimeToTime;
import static com.netikras.tools.common.security.IntegrityUtils.coalesce;
import static com.netikras.tools.common.security.IntegrityUtils.isNullOrEmpty;
import static java.lang.String.valueOf;

public class LectureInfoActivity extends BaseActivity implements LectureMvpView {

    private ViewFields fields;

    @Inject
    LectureMvpPresenter<LectureMvpView> presenter;

    @Override
    protected List<Integer> excludeMenuItems() {
        return Arrays.asList(R.id.main_menu_create, R.id.main_menu_delete);
    }

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
        addMenu();
        executeTask();
    }

    @Override
    public ViewFields getFields() {
        return fields;
    }

    @Override
    public void show(LectureDto lectureDto) {
        getFields().reset();

        if (lectureDto == null) {
            fields.clean();
            return;
        }

        getFields().setId(lectureDto.getId());
        getFields().setAssignments(lectureDto.getAssignments());
        getFields().setTests(lectureDto.getTests());
        getFields().setStudentsGroup(lectureDto.getStudentsGroup());
        getFields().setLecturer(lectureDto.getLecturer());
        getFields().setComments(lectureDto.getComments());
        getFields().setStartDatetime(lectureDto.getStartsOn());
        getFields().setLocation(lectureDto.getRoom());
        getFields().setDiscipline(lectureDto.getDiscipline());

        prepare(lectureDto);

    }

    public LectureDto collect() {
        LectureDto dto = new LectureDto();

        dto.setId(getFields().getId());
        dto.setDiscipline(getFields().getDiscipline());
        dto.setTests(getFields().getTests());
        dto.setAssignments(getFields().getAssignments());
//        dto.setCourse(getFields().getCourse()); // FIXME
        dto.setLecturer(getFields().getLecturer());
        dto.setRoom(getFields().getLocation());
        dto.setStudentsGroup(getFields().getStudentsGroup());
//        dto.setVisitors(getFields().getVisitors()); // FIXME

        return dto;
    }

    @Override
    protected boolean isPartial() {
        LectureDto dto = collect();
        if (dto == null) {
            return true;
        }
        Object item = coalesce(dto.getDiscipline(), dto.getLecturer(), dto.getRoom());
        return item == null;
    }

    private ServiceRequest.Result<LectureDto> fetch(LectureDto dto) {
        ServiceRequest.Result<LectureDto> result = new ServiceRequest.Result<>();
        showLoading();
        presenter.getById(new ErrorsAwareSubscriber<LectureDto>() {
            @Override
            public void onSuccess(LectureDto response) {
                result.setValue(response);
            }
        }, dto.getId());

        return result;
    }

    private void prepare(LectureDto entity) {
        if (entity == null || isNullOrEmpty(entity.getId())) {
            return;
        }
        if (isPartial()) {
            ServiceRequest.Result<LectureDto> result = fetch(entity);
            LectureDto dto = result.get(5, TimeUnit.SECONDS);
            if (!result.isTimedOut()) {
                show(dto);
            }
        }
    }

    @OnClick(R.id.btn_lecture_assignments)
    public void showAssignments() {
        List<AssignmentDto> assignments = getFields().getAssignments();
        if (assignments == null) {
            return;
        }

        showList(this, new ListHandler<AssignmentDto>() {
            @Override
            public List<AssignmentDto> getListData() {
                return assignments;
            }

            @Override
            public void onRowClick(AssignmentDto item) {
                startView(AssignmentActivity.class, new ViewTask<AssignmentActivity>() {
                    @Override
                    public void execute() {
                        getActivity().show(item);
                    }
                });
            }

            @Override
            public String getToolbarText() {
                return getString(R.string.title_assignments);
            }
        });
    }

    @OnClick(R.id.btn_lecture_tests)
    public void showTests() {
        List<DisciplineTestDto> tests = getFields().getTests();
        if (tests == null) {
            return;
        }

        showList(this, new ListHandler<DisciplineTestDto>() {
            @Override
            public List<DisciplineTestDto> getListData() {
                return tests;
            }

            @Override
            public void onRowClick(DisciplineTestDto item) {
                startView(TestInfoActivity.class, new ViewTask<TestInfoActivity>() {
                    @Override
                    public void execute() {
                        getActivity().show(item);
                    }
                });
            }

            @Override
            public String getToolbarText() {
                return getString(R.string.title_tests);
            }
        });
    }

    @OnClick(R.id.btn_lecture_students_group)
    public void showGroup() {
        startView(StudentsGroupInfoActivity.class, new ViewTask<StudentsGroupInfoActivity>() {
            @Override
            public void execute() {
                getActivity().show(getFields().getStudentsGroup());
            }
        });
    }

    @OnClick(R.id.btn_lecture_location)
    public void showLocation() {
        startView(LocationInfoActivity.class, new ViewTask<LocationInfoActivity>() {
            @Override
            public void execute() {
                getActivity().showClean(null, null, null, null, getFields().getLocation());
            }
        });
    }

    @OnClick(R.id.btn_lecture_lecturer)
    public void showLecturer() {
        startView(LecturerInfoActivity.class, new ViewTask<LecturerInfoActivity>() {
            @Override
            public void execute() {
                getActivity().show(getFields().getLecturer());
            }
        });
    }

    @OnClick(R.id.btn_lecture_guests)
    public void showGuests() {
        List<LectureGuestDto> guests = getFields().getGuests();
        if (guests == null) {
            return;
        }

        showList(this, new ListHandler<LectureGuestDto>() {
            @Override
            public List<LectureGuestDto> getListData() {
                return guests;
            }

            @Override
            public void onRowClick(LectureGuestDto item) {
                startView(GuestActivity.class, new ViewTask<GuestActivity>() {
                    @Override
                    public void execute() {
                        getActivity().show(item);
                    }
                });
            }

            @Override
            public String getToolbarText() {
                return getString(R.string.title_guests);
            }
        });
    }

    @OnClick(R.id.btn_lecture_name)
    public void showDiscipline() {
        startView(DisciplineInfoActivity.class, new ViewTask<DisciplineInfoActivity>() {
            @Override
            public void execute() {
                getActivity().show(getFields().getDiscipline());
            }
        });
    }

    public class ViewFields extends BaseViewFields {

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

        @BindView(R.id.txt_lbl_lecture_id)
        TextView lblId;


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

        public void setLocation(LectureRoomDto room) {
            setTag(location, room);
            if (room != null) {
                StringBuilder stringBuilder = new StringBuilder();
                if (room.getFloor() != null) {
                    if (room.getFloor().getBuilding() != null) {
                        stringBuilder.append(room.getFloor().getBuilding().getTitle()).append(", ");
                    }
                    if (room.getFloor().getBuildingSection() != null) {
                        stringBuilder.append(room.getFloor().getBuildingSection().getTitle()).append(", ");
                    }
//                    stringBuilder.append(room.getFloor().getNumber()).append(" aukš., ");
                }
                stringBuilder.append(room.getNumber());
                if (!isNullOrEmpty(room.getTitle())) {
                    stringBuilder.append(" (").append(room.getTitle()).append(")");
                }
            }
        }

        public LectureRoomDto getLocation() {
            return (LectureRoomDto) getTag(location);
        }

        public String getNameName() {
            return getString(name);
        }

        public void setNameName(String name) {
            setString(this.name, name);
        }

        public void setDiscipline(DisciplineDto disciplineDto) {
            setTag(name, disciplineDto);
            if (disciplineDto != null) {
                setNameName(disciplineDto.getTitle());
            }
        }

        public DisciplineDto getDiscipline() {
            return (DisciplineDto) getTag(name);
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
            return Arrays.asList(id, name, startDate, startTime, lecturer, studentsGroup, startTime, timeRemaining, startDate, assignments, tests, comments, guests);
        }

        @Override
        protected Collection<TextView> getEditableFields() {
            return Arrays.asList(name, startDate, startTime);
        }

        @Override
        public void enableEdit(boolean enable) {
            super.enableEdit(enable);
            if (enable) {
                setVisible(lblId, true);
                setVisible(id, true);
            } else {
                setVisible(lblId, false);
                setVisible(id, null);
            }
        }
    }

}
