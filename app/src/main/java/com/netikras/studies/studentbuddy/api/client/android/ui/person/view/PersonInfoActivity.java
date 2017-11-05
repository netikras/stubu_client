package com.netikras.studies.studentbuddy.api.client.android.ui.person.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.netikras.studies.studentbuddy.api.client.android.App;
import com.netikras.studies.studentbuddy.api.client.android.R;
import com.netikras.studies.studentbuddy.api.client.android.ui.base.BaseActivity;
import com.netikras.studies.studentbuddy.api.client.android.ui.base.BaseViewFields;
import com.netikras.studies.studentbuddy.api.client.android.ui.person.PersonService;
import com.netikras.studies.studentbuddy.api.client.android.ui.person.presenter.PersonMvpPresenter;
import com.netikras.studies.studentbuddy.api.client.android.util.AppConstants;
import com.netikras.studies.studentbuddy.core.data.api.dto.PersonDto;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.netikras.studies.studentbuddy.api.client.android.util.Settings.getExtraName;

public class PersonInfoActivity extends BaseActivity implements PersonMvpView {

    enum Action {
        GET_BY_ID,
        SHOW_CACHED
    }

    @Inject
    App app;

    @Inject
    PersonMvpPresenter<PersonMvpView> presenter;

    private ViewFields fields;

    private static final String X_PERSON_ID = getExtraName("person_id");
    private static final String X_PERSON_KEY = getExtraName("person_key");

//    public static void start(Context context) {
//        Intent intent = new Intent(context, PersonInfoActivity.class);
//        context.startActivity(intent);
//    }
//
//    public static void start(Context context, String personId) {
//        Intent intent = new Intent(context, PersonInfoActivity.class);
//        intent.setAction(Action.GET_BY_ID.name());
//        intent.putExtra(X_PERSON_ID, personId);
//        context.startActivity(intent);
//    }
//
//    public static void start(Context context, PersonDto person) {
//        Intent intent = new Intent(context, PersonInfoActivity.class);
//        intent.setAction(Action.SHOW_CACHED.name());
//        intent.putExtra(X_PERSON_KEY, App.getInstance(context).addToCache(person));
//        context.startActivity(intent);
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_info);

        showPerson(null);
        onAttach(this);

//        handleIntent();
    }

    @Override
    public View onCreateView(View parent, String name, Context context, AttributeSet attrs) {
        View view = super.onCreateView(parent, name, context, attrs);
        fields = new ViewFields();
        ButterKnife.bind(fields, view);
        return view;
    }

    @Override
    protected void setUp() {

    }


    @Override
    public ViewFields getFields() {
        return fields;
    }

    @Override
    public void showPerson(PersonDto personDto) {
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

//
//    private void handleIntent() {
//        Intent intent = getIntent();
//        Action action = Action.valueOf(intent.getAction());
//
//        String id = intent.getStringExtra(X_PERSON_ID);
//        String personKey = intent.getStringExtra(X_PERSON_KEY);
//        PersonDto personDto;
//
//        switch (action) {
//            case GET_BY_ID:
//                PersonService.startGetById(this, id);
//                break;
//            case SHOW_CACHED:
//                personDto = (PersonDto) app.popFromCache(personKey);
//                showPerson(personDto);
//                break;
//            default:
//                break;
//        }
//
//    }

    public class ViewFields extends BaseViewFields {

        private SimpleDateFormat dateFormat = new SimpleDateFormat(AppConstants.DATE_FORMAT);

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
            try {
                return dateFormat.parse(getDateCreated());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return null;
        }

        public void setDateCreated(String dateCreated) {
            setString(this.dateCreated, dateCreated);
        }

        public void setDateCreated(Date dateCreated) {
            setString(this.dateCreated, dateFormat.format(dateCreated));
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
