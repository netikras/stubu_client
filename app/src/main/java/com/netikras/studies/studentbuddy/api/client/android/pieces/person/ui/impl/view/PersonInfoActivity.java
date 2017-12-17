package com.netikras.studies.studentbuddy.api.client.android.pieces.person.ui.impl.view;

import android.content.Context;
import android.os.Bundle;
import android.text.InputType;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.netikras.studies.studentbuddy.api.client.android.R;
import com.netikras.studies.studentbuddy.api.client.android.conf.di.DepInjector;
import com.netikras.studies.studentbuddy.api.client.android.pieces.base.BaseActivity;
import com.netikras.studies.studentbuddy.api.client.android.pieces.base.BaseViewFields;
import com.netikras.studies.studentbuddy.api.client.android.pieces.base.list.CustomListAdapter;
import com.netikras.studies.studentbuddy.api.client.android.pieces.base.list.ListHandler;
import com.netikras.studies.studentbuddy.api.client.android.pieces.base.list.ListRow;
import com.netikras.studies.studentbuddy.api.client.android.pieces.person.ui.presenter.PersonMvpPresenter;
import com.netikras.studies.studentbuddy.api.client.android.pieces.person.ui.view.PersonMvpView;
import com.netikras.studies.studentbuddy.api.client.android.service.ServiceRequest.Subscriber;
import com.netikras.studies.studentbuddy.core.data.api.dto.PersonDto;
import com.netikras.studies.studentbuddy.core.data.api.dto.meta.RoleDto;

import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import butterknife.BindView;

import static com.netikras.studies.studentbuddy.api.client.android.util.CommonUtils.dateToDatetime;
import static com.netikras.studies.studentbuddy.api.client.android.util.CommonUtils.datetimeToDate;
import static com.netikras.tools.common.security.IntegrityUtils.isNullOrEmpty;

public class PersonInfoActivity extends BaseActivity implements PersonMvpView {

    @Inject
    PersonMvpPresenter<PersonMvpView> presenter;

    private ViewFields fields;

    private static PersonDto lastEntry = null;

    @Override
    protected List<Integer> excludeMenuItems() {
        return Arrays.asList(R.id.main_menu_create, R.id.main_menu_delete);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_info);
        setUp();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        lastEntry = collect();
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
        addMenu();
        if (lastEntry != null) {
            showPerson(lastEntry);
        }
        executeTask();
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
        getFields().setId(personDto.getId());
        getFields().setIdentificator(personDto.getIdentification());
        getFields().setName(personDto.getFirstName());
        getFields().setSurname(personDto.getLastName());
        getFields().setEmail(personDto.getEmail());
        getFields().setPhoneNo(personDto.getPhoneNo());
        getFields().setDateCreated(personDto.getCreatedOn());

        if (lastEntry != null) {
            lastEntry = null;
        }
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

        PersonDto dto = collect();
        showLoading();
        presenter.updatePerson(new ErrorsAwareSubscriber<PersonDto>() {
            @Override
            public void onSuccess(PersonDto response) {
                if (response != null) {
                    runOnUiThread(() -> showPerson(response));
                }
            }
        }, dto);
    }

    @Override
    protected void menuOnClickRefresh() {
        showLoading();
        presenter.getById(new ErrorsAwareSubscriber<PersonDto>() {
            @Override
            public void onSuccess(PersonDto response) {
                if (response != null) {
                    runOnUiThread(() -> showPerson(response));
                }
            }
        }, getFields().getId());
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

        @BindView(R.id.list_person_roles)
        ListView roles;

        @BindView(R.id.txt_lbl_person_id)
        TextView labelId;

        @Override
        protected Collection<TextView> getAllFields() {
            return Arrays.asList(id, identificator, name, surname, email, phoneNo, dateCreated);
        }

        @Override
        protected Map<TextView, Integer> getEditableFields() {
            Map<TextView, Integer> types = super.getEditableFields();

            types.put(identificator, InputType.TYPE_CLASS_TEXT);
            types.put(name, InputType.TYPE_TEXT_VARIATION_PERSON_NAME);
            types.put(surname, InputType.TYPE_TEXT_VARIATION_PERSON_NAME);
            types.put(email, InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
            types.put(phoneNo, InputType.TYPE_CLASS_PHONE);
            types.put(dateCreated, InputType.TYPE_DATETIME_VARIATION_DATE);


            return types;
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
            return getString(phoneNo);
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

        public void setRoles(List<String> roles) {

        }

        public List<String> getRoles() {
            return null;
        }

        @Override
        public void enableEdit(boolean enable) {
            super.enableEdit(enable);
            if (enable) {
                setVisible(labelId, true);
                setVisible(id, true);
            } else {
                setVisible(labelId, false);
                setVisible(id, null);
            }
        }
    }


    private ListHandler<String> rolesListHandler = new ListHandler<String>() {
        @Override
        public List<String> getData() {
            return super.getData();
        }

        @Override
        public void onRowClick(String item) {
            super.onRowClick(item);
        }
    };

}
