package com.netikras.studies.studentbuddy.api.client.android.pieces.student.ui.impl.view;

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
import com.netikras.studies.studentbuddy.api.client.android.pieces.school.ui.impl.view.SchoolActivity;
import com.netikras.studies.studentbuddy.api.client.android.pieces.student.ui.presenter.StudentsGroupMvpPresenter;
import com.netikras.studies.studentbuddy.api.client.android.pieces.student.ui.view.StudentsGroupMvpView;
import com.netikras.studies.studentbuddy.core.data.api.dto.school.SchoolDto;
import com.netikras.studies.studentbuddy.core.data.api.dto.school.StudentDto;
import com.netikras.studies.studentbuddy.core.data.api.dto.school.StudentsGroupDto;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

import static com.netikras.studies.studentbuddy.api.client.android.util.CommonUtils.datetimeToDate;

public class StudentsGroupInfoActivity extends BaseActivity implements StudentsGroupMvpView {


    private ViewFields fields;

    @Inject
    StudentsGroupMvpPresenter<StudentsGroupMvpView> presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_students_group_info);
    }

    @Override
    protected void setUp() {
        DepInjector.inject(this);
        onAttach(this);
        presenter.onAttach(this);
        fields = initFields(new ViewFields());
        addMenu(R.id.btn_user_main_menu);
    }

    public ViewFields getFields() {
        return fields;
    }

    public void show(StudentsGroupDto dto) {
        getFields().reset();

        if (dto == null) {
            return;
        }

        getFields().setId(dto.getId());
        getFields().setName(dto.getTitle());
        getFields().setEmail(dto.getEmail());
        getFields().setSchool(dto.getSchool());
        getFields().setMembers(dto.getMembers());
        getFields().setCreatedDatetime(dto.getCreatedOn());
    }

    public StudentsGroupDto collect() {
        StudentsGroupDto dto = new StudentsGroupDto();

        dto.setId(getFields().getId());
        dto.setTitle(getFields().getName());
        dto.setEmail(getFields().getEmail());
        dto.setMembers(getFields().getMembers());
        dto.setSchool(getFields().getSchool());

        return dto;
    }

    @OnClick(R.id.btn_students_group_school)
    public void showSchool() {
        startView(SchoolActivity.class, new ViewTask<SchoolActivity>() {
            @Override
            public void execute() {
                getActivity().show(getFields().getSchool());
            }
        });
    }

    @OnClick(R.id.btn_students_group_members)
    public void showGroupMembers() {
        showList(this, new ListHandler<StudentDto>(){

            @Override
            public ListRow getNewRow(View convertView) {
                return new StudentRow(convertView);
            }

            @Override
            public List<StudentDto> getListData() {
                return getFields().getMembers();
            }

            @Override
            public void onRowClick(StudentDto item) {
                startView(StudentInfoActivity.class, new ViewTask<StudentInfoActivity>() {
                    @Override
                    public void execute() {
                        getActivity().show(item);
                    }
                });
            }

            @Override
            public String getToolbarText() {
                return getString(R.string.title_students);
            }

            class StudentRow extends ListRow<StudentDto> {

                TextView text;

                public StudentRow(View rowView) {
                    super(null);
                    text = getDefaultListTextView(rowView);
                    rowView.setTag(this);
                }

                @Override
                public void assign(StudentDto item) {
                    StringBuilder title = new StringBuilder();
                    if (item != null && item.getPerson() != null) {
                        title.append("(").append(item.getPerson().getIdentification()).append(") ");
                        title.append(item.getPerson().getFirstName()).append(" ");
                        title.append(item.getPerson().getLastName()).append(" ");
                    }
                    text.setText(title.toString());
                }
            }
        });
    }

    class ViewFields extends BaseViewFields {
        @BindView(R.id.txt_edit_students_group_id)
        EditText id;
        @BindView(R.id.txt_edit_students_group_name)
        EditText name;
        @BindView(R.id.txt_edit_students_group_email)
        EditText email;
        @BindView(R.id.txt_edit_students_group_date_created)
        EditText created;

        @BindView(R.id.btn_students_group_school)
        Button school;
        @BindView(R.id.btn_students_group_members)
        Button members;

        public String getId() {
            return getString( id);
        }

        public void setId(String id) {
            setString(this.id, id);
        }

        public String getName() {
            return getString( name);
        }

        public void setName(String name) {
            setString(this.name, name);
        }

        public String getEmail() {
            return getString( email);
        }

        public void setEmail(String email) {
            setString(this.email, email);
        }

        public String getCreated() {
            return getString( created);
        }

        public void setCreated(String created) {
            setString(this.created, created);
        }

        public void setCreatedDatetime(Date created) {
            setString(this.created, datetimeToDate(created));
        }

        public String getSchoolName() {
            return getString( school);
        }

        public void setSchoolName(String school) {
            setString(this.school, school);
        }

        public String getMembersCount() {
            return getString( members);
        }

        public void setMembersCount(String members) {
            setString(this.members, members);
        }

        public SchoolDto getSchool() {
            return (SchoolDto) getTag(school);
        }

        public void setSchool(SchoolDto dto) {
            setTag(this.school, dto);
            if (school != null) {
                setSchoolName(dto.getTitle());
            }
        }

        public List<StudentDto> getMembers() {
            return (List<StudentDto>) getTag(members);
        }

        public void setMembers(List<StudentDto> dtos) {
            setTag(members, dtos);
            if (dtos != null) {
                setMembersCount("" + dtos.size());
            }
        }

        @Override
        protected Collection<TextView> getAllFields() {
            return null;
        }
    }

}
