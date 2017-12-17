package com.netikras.studies.studentbuddy.api.client.android.pieces.lecture.ui.impl.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.InputType;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.netikras.studies.studentbuddy.api.client.android.R;
import com.netikras.studies.studentbuddy.api.client.android.conf.di.DepInjector;
import com.netikras.studies.studentbuddy.api.client.android.pieces.base.BaseActivity;
import com.netikras.studies.studentbuddy.api.client.android.pieces.base.BaseViewFields;
import com.netikras.studies.studentbuddy.api.client.android.pieces.lecture.ui.presenter.AssignmentMvpPresenter;
import com.netikras.studies.studentbuddy.api.client.android.pieces.lecture.ui.view.AssignmentMvpView;
import com.netikras.studies.studentbuddy.core.data.api.dto.school.AssignmentDto;
import com.netikras.studies.studentbuddy.core.data.api.dto.school.DisciplineDto;
import com.netikras.studies.studentbuddy.core.data.api.dto.school.DisciplineTestDto;
import com.netikras.studies.studentbuddy.core.data.api.dto.school.LectureDto;

import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

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
        Log.d("AssignmentAct", "Intent: " + intent);
        if (intent == null) {
            return;
        }

        String cachedId = intent.getStringExtra("cached_id");
        Log.d("AssignmentAct", "Fetching assignment by ID: " + cachedId);
        if (!isNullOrEmpty(cachedId)) {
            Log.d("AssignmentAct", "Fetching assignment by ID: " + cachedId);
            showLoading();
            presenter.getById(new ErrorsAwareSubscriber<AssignmentDto>() {
                @Override
                public void onCacheHit(AssignmentDto response) {
                    setFetchRequired(false);
                    executeOnSuccess(response);
                }

                @Override
                public void onSuccess(AssignmentDto response) {
                    runOnUiThread(() -> show(response));
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
        getFields().setLecture(dto.getLecture());
        getFields().setDiscipline(dto.getDiscipline());

        if (createNew) {
            getFields().setId(null);
            getFields().enableEdit(true);
        } else {
            getFields().setCommentsCount("" + getCommentsCount("ASSIGNMENT", dto.getId()));
        }

        if (lastEntry != null) {
            lastEntry = null;
        }
    }

    private AssignmentDto collect() {
        AssignmentDto dto = new AssignmentDto();

        dto.setId(getFields().getId());
        dto.setLecture(getFields().getLecture());
        dto.setCreatedOn(getFields().getAnnouncedDatetime());
        dto.setDiscipline(getFields().getDiscipline());
        dto.setDue(getFields().getDueDatetime());
        dto.setDescription(getFields().getDescription());

        return dto;
    }

    @OnClick(R.id.btn_assignment_comments)
    public void showComments() {
        showComments("ASSIGNMENT", getFields().getId());
    }

    @OnClick(R.id.btn_assignment_comments_add)
    public void addComment() {
        createComment("ASSIGNMENT", getFields().getId());
    }

    @Override
    protected void menuOnClickCreate() {
        AssignmentDto assignmentDto = collect();
        showLoading();
        presenter.create(new ErrorsAwareSubscriber<AssignmentDto>() {
            @Override
            public void onSuccess(AssignmentDto response) {
                if (response != null) {
                    runOnUiThread(() -> show(response));
                }
            }
        }, assignmentDto);
    }

    @Override
    protected void menuOnClickSave() {
        AssignmentDto dto = collect();
        showLoading();
        presenter.update(new ErrorsAwareSubscriber<AssignmentDto>() {
            @Override
            public void onSuccess(AssignmentDto response) {
                if (response != null) {
                    runOnUiThread(() -> show(response));
                }
            }
        }, dto);
    }

    @Override
    protected void menuOnClickDelete() {
        showLoading();
        presenter.delete(new ErrorsAwareSubscriber<Boolean>() {
            @Override
            public void onSuccess(Boolean su) {
                runOnUiThread(() -> finish());
            }
        }, getFields().getId());
    }

    @Override
    protected void menuOnClickRefresh() {
        showLoading();
        presenter.getById(new ErrorsAwareSubscriber<AssignmentDto>() {
            @Override
            public void onSuccess(AssignmentDto response) {
                if (response != null) {
                    runOnUiThread(() -> show(response));
                }
            }
        }, getFields().getId());
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
        @BindView(R.id.btn_assignment_comments)
        Button comments;
        @BindView(R.id.btn_assignment_comments_add)
        ImageButton addComment;

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


        public String getCommentsCount() {
            return getString(comments);
        }

        public void setCommentsCount(String count) {
            setString(comments, count);
        }


        @Override
        protected Collection<TextView> getAllFields() {
            return Arrays.asList(id, title, description, announcedDate, announcedTime, dueDate, dueTime, announcedDate, announcedTime, comments);
        }

        @Override
        protected Map<TextView, Integer> getEditableFields() {
            Map<TextView, Integer> types = super.getEditableFields();

            types.put(title, InputType.TYPE_TEXT_FLAG_MULTI_LINE);
            types.put(description, InputType.TYPE_TEXT_FLAG_MULTI_LINE);
            types.put(announcedDate, InputType.TYPE_DATETIME_VARIATION_DATE);
            types.put(announcedTime, InputType.TYPE_DATETIME_VARIATION_TIME);
            types.put(dueDate, InputType.TYPE_DATETIME_VARIATION_DATE);
            types.put(dueTime, InputType.TYPE_DATETIME_VARIATION_TIME);

            return types;
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
