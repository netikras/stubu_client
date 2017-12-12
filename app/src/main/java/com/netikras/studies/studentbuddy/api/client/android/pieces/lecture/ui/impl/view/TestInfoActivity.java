package com.netikras.studies.studentbuddy.api.client.android.pieces.lecture.ui.impl.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.netikras.studies.studentbuddy.api.client.android.R;
import com.netikras.studies.studentbuddy.api.client.android.conf.di.DepInjector;
import com.netikras.studies.studentbuddy.api.client.android.pieces.base.BaseActivity;
import com.netikras.studies.studentbuddy.api.client.android.pieces.base.BaseViewFields;
import com.netikras.studies.studentbuddy.api.client.android.pieces.lecture.ui.presenter.TestMvpPresenter;
import com.netikras.studies.studentbuddy.api.client.android.pieces.lecture.ui.view.TestMvpView;
import com.netikras.studies.studentbuddy.core.data.api.dto.school.DisciplineDto;
import com.netikras.studies.studentbuddy.core.data.api.dto.school.DisciplineTestDto;
import com.netikras.studies.studentbuddy.core.data.api.dto.school.LectureDto;

import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

import static com.netikras.studies.studentbuddy.api.client.android.util.CommonUtils.datetimeToDate;
import static com.netikras.studies.studentbuddy.api.client.android.util.CommonUtils.datetimeToTime;

/**
 * Created by netikras on 17.11.10.
 */

public class TestInfoActivity extends BaseActivity implements TestMvpView {


    private ViewFields fields;

    @Inject
    TestMvpPresenter<TestMvpView> presenter;

    private static DisciplineTestDto lastEntry = null;


    @Override
    protected List<Integer> excludeMenuItems() {
        return Arrays.asList();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        setUp();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        lastEntry = collect();
    }

    @Override
    protected void setUp() {
        DepInjector.inject(this);
        onAttach(this);
        presenter.onAttach(this);
        fields = initFields(new ViewFields());
        addMenu();

        if (lastEntry != null) {
            show(lastEntry);
        }

        executeTask();
    }


    public ViewFields getFields() {
        return fields;
    }

    public void show(DisciplineTestDto dto) {
        getFields().reset();
        if (dto == null) {
            return;
        }

        getFields().setId(dto.getId());
        getFields().setStart(dto.getStartsOn());
        getFields().setDescription(dto.getDescription());
        getFields().setLecture(dto.getLecture());
        getFields().setExam(dto.isExam());
        getFields().setDiscipline(dto.getDiscipline());

        if (lastEntry != null) {
            lastEntry = null;
        }

    }

    private DisciplineTestDto collect() {
        DisciplineTestDto dto = new DisciplineTestDto();

        dto.setId(getFields().getId());
        dto.setDiscipline(getFields().getDiscipline());
        dto.setLecture(getFields().getLecture());
        dto.setStartsOn(getFields().getStart());
        dto.setExam(getFields().isExam());
        dto.setDescription(getFields().getDescription());

        return dto;
    }

    @OnClick(R.id.btn_test_lecture)
    void onLectureClick() {
        startView(LectureInfoActivity.class, new ViewTask<LectureInfoActivity>() {
            @Override
            public void execute() {
                getActivity().show(fields.getLecture());
            }
        });
    }


    class ViewFields extends BaseViewFields {
        @BindView(R.id.txt_edit_test_id)
        EditText id;
        @BindView(R.id.txt_edit_test_description)
        EditText description;
        @BindView(R.id.txt_edit_test_start_date)
        EditText startDate;
        @BindView(R.id.txt_edit_test_start_time)
        EditText startTime;
        @BindView(R.id.btn_test_lecture)
        Button lecture;

        DisciplineDto discipline;
        boolean exam;

        @BindView(R.id.txt_lbl_test_id)
        TextView lblId;

        public String getId() {
            return getString(id);
        }

        public void setId(String id) {
            setString(this.id, id);
        }

        public String getDescription() {
            return getString(description);
        }

        public void setDescription(String description) {
            setString(this.description, description);
        }

        public String getStartDate() {
            return getString(startDate);
        }

        public void setStartDate(String startDate) {
            setString(this.startDate, startDate);
        }

        public String getStartTime() {
            return getString(startTime);
        }

        public void setStartTime(String startTime) {
            setString(this.startTime, startTime);
        }

        public void setStart(Date date) {
            setStartDate(datetimeToDate(date));
            setStartTime(datetimeToTime(date));
        }

        public Date getStart() {
            return getDatetime(getStartDate(), getStartTime());
        }

        public String getLectureName() {
            return getString(lecture);
        }

        public void setLectureName(String lecture) {
            setString(this.lecture, lecture);
        }

        public LectureDto getLecture() {
            return (LectureDto) lecture.getTag();
        }

        public void setLecture(LectureDto lectureDto) {
            lecture.setTag(lectureDto);
            if (lectureDto != null && lectureDto.getDiscipline() != null) {
                setLectureName(lectureDto.getDiscipline().getTitle());
            }
        }

        public DisciplineDto getDiscipline() {
            return discipline;
        }

        public void setDiscipline(DisciplineDto discipline) {
            this.discipline = discipline;
        }

        public boolean isExam() {
            return exam;
        }

        public void setExam(boolean exam) {
            this.exam = exam;
        }

        @Override
        protected Collection<TextView> getAllFields() {
            return Arrays.asList(id, description, startDate, startTime, lecture);
        }

        @Override
        protected Collection<TextView> getEditableFields() {
            return Arrays.asList(description, startDate, startTime);
        }

        @Override
        public void enableEdit(boolean enable) {
            super.enableEdit(enable);
            if (enable) {
                setVisible(id, true);
                setVisible(lblId, true);
            } else {
                setVisible(id, null);
                setVisible(lblId, false);
            }
        }
    }

}
