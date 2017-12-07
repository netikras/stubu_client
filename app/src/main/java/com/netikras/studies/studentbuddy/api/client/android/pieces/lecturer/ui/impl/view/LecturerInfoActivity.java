package com.netikras.studies.studentbuddy.api.client.android.pieces.lecturer.ui.impl.view;

import android.os.Bundle;
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
import com.netikras.studies.studentbuddy.api.client.android.pieces.discipline.ui.impl.view.DisciplineInfoActivity;
import com.netikras.studies.studentbuddy.api.client.android.pieces.lecturer.ui.presenter.LecturerMvpPresenter;
import com.netikras.studies.studentbuddy.api.client.android.pieces.lecturer.ui.view.LecturerMvpView;
import com.netikras.studies.studentbuddy.api.client.android.pieces.person.ui.impl.view.PersonInfoActivity;
import com.netikras.studies.studentbuddy.api.client.android.service.ServiceRequest;
import com.netikras.studies.studentbuddy.core.data.api.dto.PersonDto;
import com.netikras.studies.studentbuddy.core.data.api.dto.school.DisciplineDto;
import com.netikras.studies.studentbuddy.core.data.api.dto.school.LecturerDto;
import com.netikras.studies.studentbuddy.core.data.api.dto.school.SchoolDto;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

import static com.netikras.tools.common.security.IntegrityUtils.coalesce;
import static com.netikras.tools.common.security.IntegrityUtils.isNullOrEmpty;

public class LecturerInfoActivity extends BaseActivity implements LecturerMvpView {


    private ViewFields fields;

    @Inject
    LecturerMvpPresenter<LecturerMvpView> presenter;

    @Override
    protected List<Integer> excludeMenuItems() {
        return Arrays.asList(R.id.main_menu_create, R.id.main_menu_delete);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lecturer_info);
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
    public void show(LecturerDto lecturer) {
        getFields().reset();
        if (lecturer == null) {
            return;
        }

        getFields().setId(lecturer.getId());
        getFields().setDegree(lecturer.getDegree());
        getFields().setPerson(lecturer.getPerson());
        getFields().setDisciplines(lecturer.getDisciplines());
        getFields().setSchool(lecturer.getSchool());

        prepare(lecturer);
    }

    public LecturerDto collect() {
        LecturerDto dto = new LecturerDto();
        dto.setId(getFields().getId());
        dto.setDegree(getFields().getDegree());
        dto.setPerson(getFields().getPerson());
        dto.setSchool(getFields().getSchool());
        return dto;
    }

    @Override
    protected boolean isPartial() {
        LecturerDto dto = collect();
        if (dto == null) {
            return true;
        }
        Object item = coalesce(dto.getPerson(), dto.getSchool());
        return item == null;
    }

    private ServiceRequest.Result<LecturerDto> fetch(LecturerDto dto) {
        ServiceRequest.Result<LecturerDto> result = new ServiceRequest.Result<>();
        showLoading();
        presenter.getById(new ErrorsAwareSubscriber<LecturerDto>() {
            @Override
            public void onSuccess(LecturerDto response) {
                result.setValue(response);
            }
        }, dto.getId());

        return result;
    }

    private void prepare(LecturerDto entity) {
        if (entity == null || isNullOrEmpty(entity.getId())) {
            return;
        }
        if (isPartial()) {
            ServiceRequest.Result<LecturerDto> result = fetch(entity);
            LecturerDto dto = result.get(5, TimeUnit.SECONDS);
            if (!result.isTimedOut()) {
                show(dto);
            }
        }
    }

    @OnClick(R.id.btn_lecturer_name)
    public void showPerson() {
        startView(PersonInfoActivity.class, new ViewTask<PersonInfoActivity>() {
            @Override
            public void execute() {
                getActivity().showPerson(getFields().getPerson());
            }
        });
    }

    @OnClick(R.id.btn_lecturer_disciplines)
    public void showDisciplinesList() {
        List<DisciplineDto> disciplines = getFields().getDisciplines();
        if (isNullOrEmpty(disciplines)) {
            onError(R.string.err_no_disciplines);
            return;
        }

        showList(this, new ListHandler<DisciplineDto>() {
            @Override
            public ListRow<DisciplineDto> getNewRow(View convertView) {
                return new DisciplineRow(convertView);
            }

            @Override
            public List<DisciplineDto> getListData() {
                return disciplines;
            }

            @Override
            public void onRowClick(DisciplineDto item) {
                onError(getListContext(), "Discipline selected: " + item.getTitle());
                System.out.println("Discipline selected: " + item.getId());
                startView(DisciplineInfoActivity.class, new ViewTask<DisciplineInfoActivity>() {
                    @Override
                    public void execute() {
                        getActivity().show(item);
                    }
                });
            }

            @Override
            public String getToolbarText() {
                return getString(R.string.title_disciplines);
            }

            class DisciplineRow extends ListRow<DisciplineDto> {

                TextView text;

                public DisciplineRow(View rowView) {
                    super(null);
                    text = getDefaultListTextView(rowView);
                    rowView.setTag(this);
                }

                @Override
                public void assign(DisciplineDto item) {
                    text.setText(item.getTitle());
                }
            }
        });
    }

    public class ViewFields extends BaseViewFields {
        @BindView(R.id.txt_edit_lecturer_id)
        EditText id;
        @BindView(R.id.txt_edit_lecturer_degree)
        EditText degree;
        @BindView(R.id.btn_lecturer_disciplines)
        Button disciplines;
        @BindView(R.id.btn_lecturer_name)
        Button name;

        @BindView(R.id.txt_lbl_lecturer_id)
        TextView lblId;

        SchoolDto school;

        public String getId() {
            return getString(id);
        }

        public void setId(String id) {
            setString(this.id, id);
        }

        public String getDegree() {
            return getString(degree);
        }

        public void setDegree(String degree) {
            setString(this.degree, degree);
        }

        public String getDisciplinesCount() {
            return getString(disciplines);
        }

        public void setDisciplinesCount(String disciplines) {
            setString(this.disciplines, disciplines);
        }

        public List<DisciplineDto> getDisciplines() {
            return (List<DisciplineDto>) getTag(disciplines);
        }

        public void setDisciplines(List<DisciplineDto> disciplines) {
            setTag(this.disciplines, disciplines);
            if (disciplines != null) {
                setDisciplinesCount("" + disciplines.size());
            }
        }

        public String getName() {
            return getString(name);
        }

        public void setName(String name) {
            setString(this.name, name);
        }

        public PersonDto getPerson() {
            return (PersonDto) getTag(name);
        }

        public void setPerson(PersonDto person) {
            setTag(name, person);
            if (person != null) {
                setName(person.getFirstName() + " " + person.getLastName());
            }
        }

        public SchoolDto getSchool() {
            return school;
        }

        public void setSchool(SchoolDto school) {
            this.school = school;
        }



        @Override
        protected Collection<TextView> getAllFields() {
            return Arrays.asList(id, degree, disciplines, name);
        }

        @Override
        protected Collection<TextView> getEditableFields() {
            return Arrays.asList(degree);
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
