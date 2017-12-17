package com.netikras.studies.studentbuddy.api.client.android.pieces.discipline.ui.impl.view;

import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.netikras.studies.studentbuddy.api.client.android.R;
import com.netikras.studies.studentbuddy.api.client.android.conf.di.DepInjector;
import com.netikras.studies.studentbuddy.api.client.android.pieces.base.BaseActivity;
import com.netikras.studies.studentbuddy.api.client.android.pieces.base.BaseViewFields;
import com.netikras.studies.studentbuddy.api.client.android.pieces.base.list.ListHandler;
import com.netikras.studies.studentbuddy.api.client.android.pieces.base.list.ListRow;
import com.netikras.studies.studentbuddy.api.client.android.pieces.discipline.ui.presenter.DisciplineMvpPresenter;
import com.netikras.studies.studentbuddy.api.client.android.pieces.discipline.ui.view.DisciplineMvpView;
import com.netikras.studies.studentbuddy.api.client.android.pieces.lecturer.ui.impl.view.LecturerInfoActivity;
import com.netikras.studies.studentbuddy.api.client.android.pieces.school.ui.impl.view.SchoolActivity;
import com.netikras.studies.studentbuddy.core.data.api.dto.school.DisciplineDto;
import com.netikras.studies.studentbuddy.core.data.api.dto.school.LecturerDto;
import com.netikras.studies.studentbuddy.core.data.api.dto.school.SchoolDto;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

import static com.netikras.tools.common.security.IntegrityUtils.coalesce;
import static com.netikras.tools.common.security.IntegrityUtils.isNullOrEmpty;

public class DisciplineInfoActivity extends BaseActivity implements DisciplineMvpView {


    @Inject
    DisciplineMvpPresenter<DisciplineMvpView> presenter;

    private ViewFields fields;

    private static DisciplineDto lastEntry = null;

    @Override
    protected List<Integer> excludeMenuItems() {
        return Arrays.asList(R.id.main_menu_create, R.id.main_menu_delete);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discipline_info);
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

    @Override
    public ViewFields getFields() {
        return fields;
    }

    @Override
    public void show(DisciplineDto disciplineDto) {
        getFields().reset();

        if (disciplineDto == null) {
            return;
        }

        getFields().setId(disciplineDto.getId());
        getFields().setDescription(disciplineDto.getDescription());
        getFields().setName(disciplineDto.getTitle());
        getFields().setSchool(disciplineDto.getSchool());
//        getFields().setLecturers(disciplineDto.getCourses());
        // FIXME fix discipline dto to return all courses, not just one

        if (lastEntry != null) {
            lastEntry = null;
        } else {
            prepare(disciplineDto);
        }
    }

    public DisciplineDto collect() {
        DisciplineDto dto = new DisciplineDto();

        dto.setId(getFields().getId());
        dto.setDescription(getFields().getDescription());
        dto.setSchool(getFields().getSchool());
        dto.setTitle(getFields().getName());

        return dto;
    }

    @Override
    protected boolean isPartial() {
        DisciplineDto dto = collect();
        if (dto == null) {
            return true;
        }
        Object item = coalesce(dto.getCourses(), dto.getSchool());
        return item == null;
    }

    private void prepare(DisciplineDto entity) {
        if (entity == null || isNullOrEmpty(entity.getId())) {
            return;
        }
        if (isPartial()) {
            showLoading();
            presenter.getById(new ErrorsAwareSubscriber<DisciplineDto>() {
                @Override
                public void onSuccess(DisciplineDto response) {
                    runOnUiThread(() -> show(response));
                }
            }, entity.getId());
        }
    }

    @OnClick(R.id.btn_discipline_school)
    public void showSchool() {

        SchoolDto schoolDto = getFields().getSchool();
        if (schoolDto == null) {
            onError(R.string.err_no_schools);
            return;
        }

        startView(SchoolActivity.class, new ViewTask<SchoolActivity>() {
            @Override
            public void execute() {
                getActivity().show(getFields().getSchool());
            }
        });
    }

    @OnClick(R.id.btn_discipline_lecturers)
    public void showLecturers() {
        showList(this, new ListHandler<LecturerDto>() {
            @Override
            public ListRow getNewRow(View convertView) {
                return new LecturerRow(convertView);
            }

            @Override
            public List<LecturerDto> getListData() {
                return getFields().getLecturers();
            }

            @Override
            public void onRowClick(LecturerDto item) {
                startView(LecturerInfoActivity.class, new ViewTask<LecturerInfoActivity>() {
                    @Override
                    public void execute() {
                        getActivity().show(item);
                    }
                });
            }

            @Override
            public String getToolbarText() {
                return getString(R.string.title_lecturers);
            }

            class LecturerRow extends ListRow<LecturerDto> {

                TextView text;

                public LecturerRow(View rowView) {
                    super(null);
                    text = getDefaultListTextView(rowView);
                    rowView.setTag(this);
                }

                @Override
                public void assign(LecturerDto item) {
                    super.assign(item);
                }
            }
        });
    }

    @Override
    protected void menuOnClickSave() {
        DisciplineDto dto = collect();
        showLoading();
        presenter.update(new ErrorsAwareSubscriber<DisciplineDto>() {
            @Override
            public void onSuccess(DisciplineDto response) {
                if (response != null) {
                    runOnUiThread(() -> show(response));
                }
            }
        }, dto);
    }

    @Override
    protected void menuOnClickRefresh() {
        showLoading();
        presenter.getById(new ErrorsAwareSubscriber<DisciplineDto>() {
            @Override
            public void onSuccess(DisciplineDto response) {
                if (response != null) {
                    runOnUiThread(() -> show(response));
                }
            }
        }, getFields().getId());
    }

    public class ViewFields extends BaseViewFields {

        @BindView(R.id.txt_edit_discipline_id)
        EditText id;

        @BindView(R.id.txt_edit_discipline_name)
        EditText name;

        @BindView(R.id.txt_edit_discipline_description)
        EditText description;

        @BindView(R.id.btn_discipline_school)
        Button school;

        @BindView(R.id.btn_discipline_lecturers)
        Button lecturers;

        @BindView(R.id.txt_lbl_discipline_id)
        TextView lblId;


        public String getId() {
            return getString(id);
        }

        public void setId(String id) {
            setString(this.id, id);
        }

        public String getName() {
            return getString(name);
        }

        public void setName(String name) {
            setString(this.name, name);
        }

        public String getDescription() {
            return getString(description);
        }

        public void setDescription(String description) {
            setString(this.description, description);
        }

        public String getSchoolName() {
            return getString(school);
        }

        public void setSchoolName(String school) {
            setString(this.school, school);
        }

        public SchoolDto getSchool() {
            return (SchoolDto) getTag(school);
        }

        public void setSchool(SchoolDto school) {
            setTag(this.school, school);
            if (school != null) {
                setSchoolName(school.getTitle());
            }
        }

        public String getLecturersCount() {
            return getString(lecturers);
        }

        public void setLecturersCount(String lecturersCount) {
            setString(this.lecturers, lecturersCount);
        }

        public List<LecturerDto> getLecturers() {
            return (List<LecturerDto>) getTag(lecturers);
        }

        public void setLectures(List<LecturerDto> lecturers) {
            setTag(this.lecturers, lecturers);
            if (lecturers != null) {
                setLecturersCount("" + lecturers.size());
            }
        }

        @Override
        protected Collection<TextView> getAllFields() {
            return Arrays.asList(id, name, description, school, lecturers);
        }

        @Override
        protected Map<TextView, Integer> getEditableFields() {
            Map<TextView, Integer> types = super.getEditableFields();

            types.put(name, InputType.TYPE_TEXT_FLAG_MULTI_LINE);
            types.put(description, InputType.TYPE_TEXT_FLAG_MULTI_LINE);
            return types;
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
