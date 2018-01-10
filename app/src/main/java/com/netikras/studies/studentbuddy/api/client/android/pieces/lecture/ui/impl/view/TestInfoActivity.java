package com.netikras.studies.studentbuddy.api.client.android.pieces.lecture.ui.impl.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
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
        handleIntent();
        executeTask();
    }

    private void handleIntent() {
        Intent intent = getIntent();
        Log.d("TestAct", "Intent: " + intent);
        if (intent == null) {
            return;
        }

        String cachedId = intent.getStringExtra("cached_id");
        Log.d("", "Fetching test by ID: " + cachedId);
        if (!isNullOrEmpty(cachedId)) {
            showLoading();
            presenter.getById(new ErrorsAwareSubscriber<DisciplineTestDto>() {
                @Override
                public void onCacheHit(DisciplineTestDto response) {
                    setFetchRequired(false);
                    executeOnSuccess(response);
                }

                @Override
                public void onSuccess(DisciplineTestDto response) {
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
    public void show(DisciplineTestDto dto) {
        show(dto, false);
    }

    @Override
    public void show(DisciplineTestDto dto, boolean createNew) {
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

        if (createNew) {
            getFields().setId(null);
            getFields().enableEdit(true);
        } else {
            getFields().setCommentsCount("" + getCommentsCount("TEST", dto.getId()));
        }

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


    @OnClick(R.id.btn_test_comments)
    public void showComments() {
        showComments("TEST", getFields().getId());
    }

    @OnClick(R.id.btn_test_comments_add)
    public void addComment() {
        createComment("TEST", getFields().getId());
    }

    @Override
    protected void menuOnClickCreate() {
        DisciplineTestDto dto = collect();
        showLoading();
        presenter.create(new ErrorsAwareSubscriber<DisciplineTestDto>() {
            @Override
            public void onSuccess(DisciplineTestDto response) {
                runOnUiThread(() -> show(response));
            }
        }, dto);
    }

    @Override
    protected void menuOnClickSave() {
        DisciplineTestDto dto = collect();
        showLoading();
        presenter.update(new ErrorsAwareSubscriber<DisciplineTestDto>() {
            @Override
            public void onSuccess(DisciplineTestDto response) {
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
        presenter.getById(new ErrorsAwareSubscriber<DisciplineTestDto>() {
            @Override
            public void onSuccess(DisciplineTestDto response) {
                if (response != null) {
                    runOnUiThread(() -> show(response));
                }
            }
        }, getFields().getId());
    }


    public class ViewFields extends BaseViewFields {
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
        @BindView(R.id.btn_test_comments)
        Button comments;
        @BindView(R.id.btn_test_comments_add)
        ImageButton addComment;

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

        public String getCommentsCount() {
            return getString(comments);
        }

        public void setCommentsCount(String count) {
            setString(comments, count);
        }

        @Override
        protected Collection<TextView> getAllFields() {
            return Arrays.asList(id, description, startDate, startTime, lecture, comments);
        }

        @Override
        protected Map<TextView, Integer> getEditableFields() {
            Map<TextView, Integer> types = super.getEditableFields();

            types.put(description, InputType.TYPE_TEXT_FLAG_MULTI_LINE);
            types.put(startDate, InputType.TYPE_DATETIME_VARIATION_DATE);
            types.put(startTime, InputType.TYPE_DATETIME_VARIATION_TIME);

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

            if (!hasUserPermission("test", "comment_create", getId())) {
                addComment.setVisibility(View.INVISIBLE);
            }

            if (!hasUserPermission("test", "comment_get", getId())) {
                comments.setEnabled(false);
            }
        }
    }

}
