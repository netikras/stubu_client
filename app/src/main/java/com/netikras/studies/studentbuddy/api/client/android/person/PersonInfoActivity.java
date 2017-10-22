package com.netikras.studies.studentbuddy.api.client.android.person;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;

import com.netikras.studies.studentbuddy.api.client.android.App;
import com.netikras.studies.studentbuddy.api.client.android.R;
import com.netikras.studies.studentbuddy.api.client.android.Settings;
import com.netikras.studies.studentbuddy.core.data.api.dto.PersonDto;

import static com.netikras.studies.studentbuddy.api.client.android.Settings.getExtraName;

public class PersonInfoActivity extends AppCompatActivity {

    enum Action {
        GET_BY_ID,
        SHOW_CACHED
    }

    private App app;

    private static final String X_PERSON_ID = getExtraName("person_id");
    private static final String X_PERSON_KEY = getExtraName("person_key");

    public static void start(Context context) {
        Intent intent = new Intent(context, PersonInfoActivity.class);
        context.startActivity(intent);
    }

    public static void start(Context context, String personId) {
        Intent intent = new Intent(context, PersonInfoActivity.class);
        intent.setAction(Action.GET_BY_ID.name());
        intent.putExtra(X_PERSON_ID, personId);
        context.startActivity(intent);
    }

    public static void start(Context context, PersonDto person) {
        Intent intent = new Intent(context, PersonInfoActivity.class);
        intent.setAction(Action.SHOW_CACHED.name());
        intent.putExtra(X_PERSON_KEY, App.getInstance(context).addToCache(person));
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_info);
        app = App.getInstance(this);

        showPerson(null);

        handleIntent();
    }

    private void handleIntent() {
        Intent intent = getIntent();
        Action action = Action.valueOf(intent.getAction());

        String id = intent.getStringExtra(X_PERSON_ID);
        String personKey = intent.getStringExtra(X_PERSON_KEY);
        PersonDto personDto;

        switch (action) {
            case GET_BY_ID:
                PersonService.startGetById(this, id);
                break;
            case SHOW_CACHED:
                personDto = (PersonDto) app.popFromCache(personKey);
                showPerson(personDto);
                break;
            default:
                break;
        }

    }



    private void showPerson(PersonDto personDto) {
        if (personDto == null) {
            personDto = new PersonDto(); // display empty fields. Replace this with 'return;' to display default dummy values
        }
        ((EditText)findViewById(R.id.txt_edit_person_id)).setText(personDto.getId());
        ((EditText)findViewById(R.id.txt_edit_person_identificator)).setText(personDto.getIdentification());
        ((EditText)findViewById(R.id.txt_edit_person_name)).setText(personDto.getFirstName());
        ((EditText)findViewById(R.id.txt_edit_person_surname)).setText(personDto.getLastName());
        ((EditText)findViewById(R.id.txt_edit_person_email)).setText(personDto.getEmail());
        ((EditText)findViewById(R.id.txt_edit_person_phone_number)).setText(personDto.getPhoneNo());
        ((EditText)findViewById(R.id.txt_edit_person_date_created)).setText(personDto.getCreatedOn() == null ? "" : personDto.getCreatedOn().toString()); // TODO format date
    }
}
