package com.netikras.studies.studentbuddy.api.client.android.pieces.lecture.ui.impl.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.EditText;
import android.widget.TextView;

import com.netikras.studies.studentbuddy.api.client.android.R;
import com.netikras.studies.studentbuddy.api.client.android.conf.di.DepInjector;
import com.netikras.studies.studentbuddy.api.client.android.pieces.base.BaseActivity;
import com.netikras.studies.studentbuddy.api.client.android.pieces.base.BaseViewFields;
import com.netikras.studies.studentbuddy.api.client.android.pieces.lecture.ui.presenter.AssignmentMvpPresenter;
import com.netikras.studies.studentbuddy.api.client.android.pieces.lecture.ui.view.AssignmentMvpView;
import com.netikras.studies.studentbuddy.core.data.api.dto.school.AssignmentDto;
import com.netikras.studies.studentbuddy.core.data.api.dto.school.DisciplineDto;
import com.netikras.studies.studentbuddy.core.data.api.dto.school.LectureDto;

import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

import static com.netikras.studies.studentbuddy.api.client.android.util.CommonUtils.datetimeToDate;
import static com.netikras.studies.studentbuddy.api.client.android.util.CommonUtils.datetimeToTime;
import static com.netikras.tools.common.security.IntegrityUtils.isNullOrEmpty;

/**
 * Created by netikras on 17.11.10.
 */

public class AssignmentActivity extends BaseActivity implements AssignmentMvpView {

    private ViewFields fields;

    @Inject
    AssignmentMvpPresenter<AssignmentMvpView> presenter;

    private static AssignmentDto lastEntry = null;

    @Override
    protected List<Integer> excludeMenuItems() {
        return Arrays.asList();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assignment);
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
        handleIntent();
        executeTask();
    }

    private void handleIntent() {
        Intent intent = getIntent();
        if (intent == null) {
            return;
        }

        String cachedId = intent.getStringExtra("cached_id");
        if (!isNullOrEmpty(cachedId)) {
            presenter.getById(new ErrorsAwareSubscriber<AssignmentDto>() {
                @Override
                public void onSuccess(AssignmentDto response) {
                    show(response);
                }
            }, cachedId);
        }
    }

    @Override
    public ViewFields getFields() {
        return fields;
    }


    @Override
    public void show(AssignmentDto dto) {
        show(dto, false);
    }

    @Override
    public void show(AssignmentDto dto, boolean createNew) {
        getFields().reset();
        if (dto == null) {
            return;
        }

        getFields().setDueDate(dto.getDue());
        getFields().setAnnouncedDatetime(dto.getCreatedOn());
        getFields().setId(dto.getId());
        getFields().setDescription(dto.getDescription());
        getFields().setLecture(dto.getLectureDto());
        getFields().setDiscipline(dto.getDiscipline());

        if (createNew) {
            getFields().setId(null);
            getFields().enableEdit(true);
        }

        if (lastEntry != null) {
            lastEntry = null;
        }
    }

    private AssignmentDto collect() {
        AssignmentDto dto = new AssignmentDto();

        dto.setId(getFields().getId());
        dto.setLectureDto(getFields().getLecture());
        dto.setCreatedOn(getFields().getAnnouncedDatetime());
        dto.setDiscipline(getFields().getDiscipline());
        dto.setDue(getFields().getDueDatetime());
        dto.setDescription(getFields().getDescription());

        return dto;
    }

    @Override
    protected void menuOnClickCreate() {
        AssignmentDto assignmentDto = collect();
        presenter.create(new ErrorsAwareSubscriber<AssignmentDto>() {
            @Override
            public void onSuccess(AssignmentDto response) {
                show(response);
            }
        }, assignmentDto);
    }

    @Override
    protected void menuOnClickSave() {
        AssignmentDto dto = collect();
        presenter.update(new ErrorsAwareSubscriber<AssignmentDto>() {
            @Override
            public void onSuccess(AssignmentDto response) {
                show(response);
            }
        }, dto);
    }

    public class ViewFields extends BaseViewFields {

        @BindView(R.id.txt_edit_assignment_id)
        EditText id;
        @BindView(R.id.txt_edit_assignment_title)
        EditText title;
        @BindView(R.id.txt_edit_assignment_description)
        EditText description;
        @BindView(R.id.txt_edit_assignment_announced_date)
        EditText announcedDate;
        @BindView(R.id.txt_edit_assignment_announced_time)
        EditText announcedTime;
        @BindView(R.id.txt_edit_assignment_due_date)
        EditText dueDate;
        @BindView(R.id.txt_edit_assignment_due_time)
        EditText dueTime;

        LectureDto lecture;
        DisciplineDto discipline;

        @BindView(R.id.txt_lbl_assignment_id)
        TextView lblId;

        public String getId() {
            return getString(id);
        }

        public void setId(String id) {
            setString(this.id, id);
        }

        public String getTitle() {
            return getString(title);
        }

        public void setTitle(String title) {
            setString(this.title, title);
        }

        public String getDescription() {
            return getString(description);
        }

        public void setDescription(String description) {
            setString(this.description, description);
        }

        public String getAnnouncedDate() {
            return getString(announcedDate);
        }

        public void setAnnouncedDate(String announcedDate) {
            setString(this.announcedDate, announcedDate);
        }

        public String getAnnouncedTime() {
            return getString(announcedTime);
        }

        public void setAnnouncedTime(String announcedTime) {
            setString(this.announcedTime, announcedTime);
        }

        public String getDueDate() {
            return getString(dueDate);
        }

        public void setDueDate(String dueDate) {
            setString(this.dueDate, dueDate);
        }

        public String getDueTime() {
            return getString(dueTime);
        }

        public void setDueTime(String dueTime) {
            setString(this.dueTime, dueTime);
        }


        public Date getAnnouncedDatetime() {
            return getDatetime(getAnnouncedDate(), getAnnouncedTime());
        }

        public void setAnnouncedDatetime(Date announcedDate) {
            setAnnouncedDate(datetimeToDate(announcedDate));
            setAnnouncedTime(datetimeToTime(announcedDate));
        }

        public Date getDueDatetime() {
            return getDatetime(getDueDate(), getDueTime());
        }

        public void setDueDate(Date dueDate) {
            setDueDate(datetimeToDate(dueDate));
            setDueTime(datetimeToTime(dueDate));
        }

        public LectureDto getLecture() {
            return lecture;
        }

        public void setLecture(LectureDto lecture) {
            this.lecture = lecture;
        }

        @Override
        protected Collection<TextView> getAllFields() {
            return Arrays.asList(id, title, description, announcedDate, announcedTime, dueDate, dueTime, announcedDate, announcedTime);
        }

        @Override
        protected Collection<TextView> getEditableFields() {
            return Arrays.asList(title, description, announcedDate, announcedTime, dueDate, dueTime);
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

        public DisciplineDto getDiscipline() {
            return discipline;
        }

        public void setDiscipline(DisciplineDto discipline) {
            this.discipline = discipline;
        }
    }
}
