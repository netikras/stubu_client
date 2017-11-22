package com.netikras.studies.studentbuddy.api.client.android.pieces.lecturer.ui.impl.view;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.netikras.studies.studentbuddy.api.client.android.R;
import com.netikras.studies.studentbuddy.api.client.android.conf.di.DepInjector;
import com.netikras.studies.studentbuddy.api.client.android.pieces.base.BaseActivity;
import com.netikras.studies.studentbuddy.api.client.android.pieces.base.BaseViewFields;
import com.netikras.studies.studentbuddy.api.client.android.pieces.lecturer.ui.presenter.LecturerMvpPresenter;
import com.netikras.studies.studentbuddy.api.client.android.pieces.lecturer.ui.view.LecturerMvpView;
import com.netikras.studies.studentbuddy.core.data.api.dto.PersonDto;
import com.netikras.studies.studentbuddy.core.data.api.dto.school.LecturerDto;

import java.util.Collection;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

public class LecturerInfoActivity extends BaseActivity implements LecturerMvpView {


    private ViewFields fields;

    @Inject
    LecturerMvpPresenter<LecturerMvpView> presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lecturer_info);
    }

    @Override
    protected void setUp() {
        DepInjector.inject(this);
        onAttach(this);
        presenter.onAttach(this);
        fields = initFields(new ViewFields());
        addMenu(R.id.btn_lecturer_main_menu);
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
//        getFields().setDisciplines(); // TODO maybe include courses?
    }

    @OnClick(R.id.btn_lecturer_name)
    public void showPerson() {
        presenter.showPerson(this, getFields().getPerson());
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

        public String getId() {
            return getString( id);
        }

        public void setId(String id) {
            setString(this.id, id);
        }

        public String getDegree() {
            return getString( degree);
        }

        public void setDegree(String degree) {
            setString(this.degree, degree);
        }

        public String getDisciplines() {
            return getString( disciplines);
        }

        public void setDisciplines(String disciplines) {
            setString(this.disciplines, disciplines);
        }

        public String getName() {
            return getString( name);
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


        @Override
        protected Collection<TextView> getAllFields() {
            return null;
        }
    }
}
