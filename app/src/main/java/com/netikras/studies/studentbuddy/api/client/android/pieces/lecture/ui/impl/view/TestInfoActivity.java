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
import com.netikras.studies.studentbuddy.core.data.api.dto.school.DisciplineTestDto;
import com.netikras.studies.studentbuddy.core.data.api.dto.school.LectureDto;

import java.util.Arrays;
import java.util.Collection;
import java.util.Date;

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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        setUp();
    }

    @Override
    protected void setUp() {
        DepInjector.inject(this);
        onAttach(this);
        presenter.onAttach(this);
        fields = initFields(new ViewFields());
        addMenu(R.id.btn_test_main_menu);
    }


    public void show(DisciplineTestDto dto) {
        if (dto == null) {
            fields.clean();
            return;
        }

        fields.setId(dto.getId());
        fields.setStart(dto.getStartsOn());
        fields.setDescription(dto.getDescription());
        fields.setLecture(dto.getLecture());
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

        @Override
        protected Collection<TextView> getAllFields() {
            return Arrays.asList(id, description, startDate, startTime, lecture);
        }
    }

}
