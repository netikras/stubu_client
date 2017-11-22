package com.netikras.studies.studentbuddy.api.client.android.pieces.person.ui.impl.view;

import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.netikras.studies.studentbuddy.api.client.android.App;
import com.netikras.studies.studentbuddy.api.client.android.R;
import com.netikras.studies.studentbuddy.api.client.android.conf.di.DepInjector;
import com.netikras.studies.studentbuddy.api.client.android.pieces.base.BaseActivity;
import com.netikras.studies.studentbuddy.api.client.android.pieces.base.BaseViewFields;
import com.netikras.studies.studentbuddy.api.client.android.pieces.person.ui.presenter.PersonMvpPresenter;
import com.netikras.studies.studentbuddy.api.client.android.pieces.person.ui.view.PersonMvpView;
import com.netikras.studies.studentbuddy.api.client.android.util.AppConstants;
import com.netikras.studies.studentbuddy.api.client.android.util.CommonUtils;
import com.netikras.studies.studentbuddy.core.data.api.dto.PersonDto;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.netikras.studies.studentbuddy.api.client.android.util.CommonUtils.dateToDatetime;
import static com.netikras.studies.studentbuddy.api.client.android.util.CommonUtils.datetimeToDate;
import static com.netikras.tools.common.security.IntegrityUtils.isNullOrEmpty;

public class PersonInfoActivity extends BaseActivity implements PersonMvpView {

    @Inject
    App app;

    @Inject
    PersonMvpPresenter<PersonMvpView> presenter;

    private ViewFields fields;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_info);
        setUp();
    }

    @Override
    public View onCreateView(View parent, String name, Context context, AttributeSet attrs) {
        View view = super.onCreateView(parent, name, context, attrs);
//        fields = new ViewFields();
//        ButterKnife.bind(fields, view);
        return view;
    }

    @Override
    protected void setUp() {
        DepInjector.inject(this);
        onAttach(this);
        presenter.onAttach(this);
        fields = initFields(new ViewFields());
        addMenu(R.id.btn_person_main_menu);
    }


    @Override
    public ViewFields getFields() {
        return fields;
    }

    @Override
    public void showPerson(PersonDto personDto) {
        getFields().reset();

        if (personDto == null) {
            personDto = new PersonDto(); // display empty fields. Replace this with 'return;' to display default dummy values
        }
        fields.setId(personDto.getId());
        fields.setIdentificator(personDto.getIdentification());
        fields.setName(personDto.getFirstName());
        fields.setSurname(personDto.getLastName());
        fields.setEmail(personDto.getEmail());
        fields.setPhoneNo(personDto.getPhoneNo());
        fields.setDateCreated(personDto.getCreatedOn());
    }

    @Override
    public PersonDto collect() {
        PersonDto personDto = new PersonDto();

        personDto.setId(getFields().getId());
        personDto.setIdentification(getFields().getIdentificator());
        personDto.setEmail(getFields().getEmail());
        personDto.setFirstName(getFields().getName());
        personDto.setLastName(getFields().getSurname());
//        personDto.setPersonalCode(getFields().get);
        personDto.setPhoneNo(getFields().getPhoneNo());

        return personDto;
    }

    @Override
    protected void menuOnClickSave() {
        getFields().enableEdit(false);
        if (isNullOrEmpty(getFields().getId())) {
            presenter.createPerson(this, collect());
        } else {
            presenter.updatePerson(this, collect());
        }
    }

    @Override
    protected void menuOnClickCreate() {
        getFields().reset();
        getFields().setId("");
        getFields().enableEdit(true);
    }

    public class ViewFields extends BaseViewFields {

        @BindView(R.id.txt_edit_person_id)
        EditText id;
        @BindView(R.id.txt_edit_person_identificator)
        EditText identificator;
        @BindView(R.id.txt_edit_person_name)
        EditText name;
        @BindView(R.id.txt_edit_person_surname)
        EditText surname;
        @BindView(R.id.txt_edit_person_email)
        EditText email;
        @BindView(R.id.txt_edit_person_phone_number)
        EditText phoneNo;
        @BindView(R.id.txt_edit_person_date_created)
        EditText dateCreated;

        @BindView(R.id.txt_lbl_person_id)
        TextView labelId;

        @Override
        protected Collection<TextView> getAllFields() {
            return Arrays.asList(id, identificator, name, surname, email, phoneNo, dateCreated);
        }

        @Override
        protected Collection<TextView> getEditableFields() {
            return Arrays.asList(identificator, name, surname, email, phoneNo, dateCreated);
        }

        public String getId() {
            return getString(id);
        }

        public void setId(String id) {
            this.setString(this.id, id);
        }

        public String getIdentificator() {
            return getString(identificator);
        }

        public void setIdentificator(String identificator) {
            setString(this.identificator, identificator);
        }

        public String getName() {
            return getString(name);
        }

        public void setName(String name) {
            setString(this.name, name);
        }

        public String getSurname() {
            return getString(surname);
        }

        public void setSurname(String surname) {
            setString(this.surname, surname);
        }

        public String getEmail() {
            return getString(email);
        }

        public void setEmail(String email) {
            setString(this.email, email);
        }

        public String getPhoneNo() {
            return getString(email);
        }

        public void setPhoneNo(String phoneNo) {
            setString(this.phoneNo, phoneNo);
        }

        public String getDateCreated() {
            return getString(dateCreated);
        }

        public Date getDateCreatedAsDate() {
            return dateToDatetime(getDateCreated());
        }

        public void setDateCreated(String dateCreated) {
            setString(this.dateCreated, dateCreated);
        }

        public void setDateCreated(Date dateCreated) {
            setString(this.dateCreated, datetimeToDate(dateCreated));
        }

        @Override
        public void enableEdit(boolean enable) {
            super.enableEdit(enable);
            if (enable) {
                hideField(labelId, false);
                hideField(id, false);
            } else {
                labelId.setVisibility(View.INVISIBLE);
                hideField(id, true);
            }
        }
    }
}
