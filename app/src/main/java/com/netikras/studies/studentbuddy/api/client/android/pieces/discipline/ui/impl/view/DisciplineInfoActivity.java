package com.netikras.studies.studentbuddy.api.client.android.pieces.discipline.ui.impl.view;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.netikras.studies.studentbuddy.api.client.android.R;
import com.netikras.studies.studentbuddy.api.client.android.conf.di.DepInjector;
import com.netikras.studies.studentbuddy.api.client.android.pieces.base.BaseActivity;
import com.netikras.studies.studentbuddy.api.client.android.pieces.base.BaseViewFields;
import com.netikras.studies.studentbuddy.api.client.android.pieces.discipline.ui.presenter.DisciplineMvpPresenter;
import com.netikras.studies.studentbuddy.api.client.android.pieces.discipline.ui.view.DisciplineMvpView;
import com.netikras.studies.studentbuddy.core.data.api.dto.school.DisciplineDto;
import com.netikras.studies.studentbuddy.core.data.api.dto.school.SchoolDto;

import java.util.Arrays;
import java.util.Collection;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

public class DisciplineInfoActivity extends BaseActivity implements DisciplineMvpView {


    @Inject
    DisciplineMvpPresenter<DisciplineMvpView> presenter;

    private ViewFields fields;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discipline_info);
    }

    @Override
    protected void setUp() {
        DepInjector.inject(this);
        onAttach(this);
        presenter.onAttach(this);
        fields = initFields(new ViewFields());
        addMenu(R.id.btn_discipline_main_menu);
    }

    @Override
    public ViewFields getFields() {
        return fields;
    }

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
    }

    public DisciplineDto collect() {
        DisciplineDto dto = new DisciplineDto();

        dto.setId(fields.getId());
        dto.setDescription(getFields().getDescription());
        dto.setSchool(getFields().getSchool());
        dto.setTitle(getFields().getName());

        return dto;
    }

    @OnClick(R.id.btn_discipline_school)
    public void showSchool() {
        presenter.showSchool(this, getFields().getSchool());
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

        public String getLecturers() {
            return getString(lecturers);
        }

        public void setLecturers(String lecturersCount) {
            setString(this.lecturers, lecturersCount);
        }

        @Override
        protected Collection<TextView> getAllFields() {
            return Arrays.asList(id, name, description, school, lecturers);
        }

        @Override
        protected Collection<TextView> getEditableFields() {
            return Arrays.asList(name, description, school, lecturers);
        }
    }
}
