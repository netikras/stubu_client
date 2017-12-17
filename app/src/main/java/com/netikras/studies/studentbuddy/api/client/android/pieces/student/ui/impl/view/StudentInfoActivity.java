package com.netikras.studies.studentbuddy.api.client.android.pieces.student.ui.impl.view;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.netikras.studies.studentbuddy.api.client.android.R;
import com.netikras.studies.studentbuddy.api.client.android.conf.di.DepInjector;
import com.netikras.studies.studentbuddy.api.client.android.pieces.base.BaseActivity;
import com.netikras.studies.studentbuddy.api.client.android.pieces.base.BaseViewFields;
import com.netikras.studies.studentbuddy.api.client.android.pieces.person.ui.impl.view.PersonInfoActivity;
import com.netikras.studies.studentbuddy.api.client.android.pieces.student.ui.presenter.StudentMvpPresenter;
import com.netikras.studies.studentbuddy.api.client.android.pieces.student.ui.view.StudentMvpView;
import com.netikras.studies.studentbuddy.core.data.api.dto.PersonDto;
import com.netikras.studies.studentbuddy.core.data.api.dto.school.SchoolDepartmentDto;
import com.netikras.studies.studentbuddy.core.data.api.dto.school.SchoolDto;
import com.netikras.studies.studentbuddy.core.data.api.dto.school.StudentDto;
import com.netikras.studies.studentbuddy.core.data.api.dto.school.StudentsGroupDto;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

import static com.netikras.studies.studentbuddy.api.client.android.util.CommonUtils.datetimeToDate;
import static com.netikras.tools.common.security.IntegrityUtils.coalesce;
import static com.netikras.tools.common.security.IntegrityUtils.isNullOrEmpty;

public class StudentInfoActivity extends BaseActivity implements StudentMvpView {

    private ViewFields fields;

    @Inject
    StudentMvpPresenter<StudentMvpView> presenter;
    private static StudentDto lastEntry = null;


    @Override
    protected List<Integer> excludeMenuItems() {
        return Arrays.asList(R.id.main_menu_create, R.id.main_menu_edit, R.id.main_menu_save, R.id.main_menu_delete);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_info);
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
    public void show(StudentDto studentDto) {
        getFields().reset();

        if (studentDto == null) {
            return;
        }

        getFields().setId(studentDto.getId());
        getFields().setGroup(studentDto.getGroup());
        getFields().setSchool(studentDto.getSchool());
        getFields().setDepartment(studentDto.getDepartment());
        getFields().setCreated(datetimeToDate(studentDto.getCreatedOn()));
        getFields().setPerson(studentDto.getPerson());

        if (lastEntry != null) {
            lastEntry = null;
        } else {
            prepare(studentDto);
        }
    }

    public StudentDto collect() {
        StudentDto dto = new StudentDto();

        dto.setId(getFields().getId());
        dto.setDepartment(getFields().getDepartment());
        dto.setSchool(getFields().getSchool());
        dto.setPerson(getFields().getPerson());
        dto.setGroup(getFields().getGroup());

        return dto;
    }

    @Override
    protected boolean isPartial() {
        StudentDto dto = collect();
        if (dto == null) {
            return true;
        }
        Object item = coalesce(dto.getGroup(), dto.getSchool());
        return item == null || dto.getPerson() == null;
    }

    private void prepare(StudentDto studentDto) {
        if (hasTriedToFetch()) {
            setTriedToFetch(false);
            return;
        }
        if (studentDto == null || isNullOrEmpty(studentDto.getId())) {
            return;
        }
        if (isPartial()) {
            showLoading();
            setTriedToFetch(true);
            presenter.getById(new ErrorsAwareSubscriber<StudentDto>() {
                @Override
                public void onCacheHit(StudentDto response) {
                    if (response != null && response.getPerson() != null && !isNullOrEmpty(response.getPerson().getFirstName())) {
                        setFetchRequired(false);
                        executeOnSuccess(response);
                    }
                }

                @Override
                public void onSuccess(StudentDto response) {
                    runOnUiThread(() -> show(response));
//                    show(response);
                }
            }, studentDto.getId());
        }
    }

    @OnClick(value = {R.id.btn_student_name})
    public void showPerson() {

        PersonDto personDto = getFields().getPerson();
        if (personDto == null) {
            onError(R.string.err_no_person);
            return;
        }

        startView(PersonInfoActivity.class, new ViewTask<PersonInfoActivity>() {
            @Override
            public void execute() {
                getActivity().showPerson(getFields().getPerson());
            }
        });
    }

    @Override
    protected void menuOnClickRefresh() {
        showLoading();
        presenter.getById(new ErrorsAwareSubscriber<StudentDto>(){
            @Override
            public void onSuccess(StudentDto response) {
                if (response != null) {
                    runOnUiThread(() -> show(response));
                }
            }
        }, getFields().getId());
    }

    public class ViewFields extends BaseViewFields {

        @BindView(R.id.txt_edit_student_id)
        EditText id;
        @BindView(R.id.txt_edit_student_identificator)
        EditText identificator;
        @BindView(R.id.txt_edit_student_date_created)
        EditText created;

        @BindView(R.id.btn_student_name)
        Button name;
        @BindView(R.id.btn_student_school)
        Button school;
        @BindView(R.id.btn_student_school_department)
        Button department;
        @BindView(R.id.btn_student_group)
        Button group;

        @BindView(R.id.txt_lbl_student_id)
        TextView lblId;

        public String getId() {
            return getString(id);
        }

        public void setId(String id) {
            setString(this.id, id);
        }

        public PersonDto getPerson() {
            return (PersonDto) getTag(name);
        }

        public void setPerson(PersonDto dto) {
            setTag(name, dto);
            if (dto != null) {
                setName(dto.getFirstName() + " " + dto.getLastName());
                setIdentificator(dto.getIdentification());
            }
        }

        public String getIdentificator() {
            return getString(identificator);
        }

        public void setIdentificator(String identificator) {
            setString(this.identificator, identificator);
        }

        public String getCreated() {
            return getString(created);
        }

        public void setCreated(String created) {
            setString(this.created, created);
        }

        public String getName() {
            return getString(name);
        }

        public void setName(String name) {
            setString(this.name, name);
        }

        public String getSchoolName() {
            return getString(school);
        }

        public void setSchoolName(String school) {
            setString(this.school, school);
        }

        public String getDepartmentName() {
            return getString(department);
        }

        public void setDepartmentName(String department) {
            setString(this.department, department);
        }

        public String getGroupName() {
            return getString(group);
        }

        public void setGroupName(String group) {
            setString(this.group, group);
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

        public SchoolDepartmentDto getDepartment() {
            return (SchoolDepartmentDto) getTag(department);
        }

        public void setDepartment(SchoolDepartmentDto department) {
            setTag(this.department, department);
            if (department != null) {
                setDepartmentName(department.getTitle());
            }
        }

        public StudentsGroupDto getGroup() {
            return (StudentsGroupDto) getTag(group);
        }

        public void setGroup(StudentsGroupDto group) {
            setTag(this.group, group);
            if (group != null) {
                setGroupName(group.getTitle());
            }
        }

        @Override
        protected Collection<TextView> getAllFields() {
            return Arrays.asList(id, identificator, created, name, school, department, group);
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
