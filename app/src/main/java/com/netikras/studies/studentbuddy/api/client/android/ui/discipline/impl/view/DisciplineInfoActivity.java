package com.netikras.studies.studentbuddy.api.client.android.ui.discipline.impl.view;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.netikras.studies.studentbuddy.api.client.android.R;
import com.netikras.studies.studentbuddy.api.client.android.conf.di.DepInjector;
import com.netikras.studies.studentbuddy.api.client.android.ui.base.BaseActivity;
import com.netikras.studies.studentbuddy.api.client.android.ui.base.BaseViewFields;
import com.netikras.studies.studentbuddy.api.client.android.ui.discipline.presenter.DisciplineMvpPresenter;
import com.netikras.studies.studentbuddy.api.client.android.ui.discipline.view.DisciplineMvpView;
import com.netikras.studies.studentbuddy.core.data.api.dto.school.DisciplineDto;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

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

    public void show(DisciplineDto disciplineDto) {

    }

    public DisciplineDto collect() {
        DisciplineDto dto = new DisciplineDto();

        dto.setId(fields.getId());

        return dto;
    }

    class ViewFields extends BaseViewFields {

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

        public String getSchool() {
            return getString(school);
        }

        public void setSchool(String school) {
            setString(this.school, school);
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
