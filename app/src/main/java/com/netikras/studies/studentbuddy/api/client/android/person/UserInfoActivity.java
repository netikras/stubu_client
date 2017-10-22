package com.netikras.studies.studentbuddy.api.client.android.person;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.netikras.studies.studentbuddy.api.client.android.App;
import com.netikras.studies.studentbuddy.api.client.android.MainActivity;
import com.netikras.studies.studentbuddy.api.client.android.MainMenuDefaultListener;
import com.netikras.studies.studentbuddy.api.client.android.R;
import com.netikras.studies.studentbuddy.core.data.api.dto.PersonDto;
import com.netikras.studies.studentbuddy.core.data.api.dto.meta.UserDto;

import static com.netikras.tools.common.security.IntegrityUtils.isNullOrEmpty;

public class UserInfoActivity extends AppCompatActivity {

    private static final String TAG = "UserInfoActivity";

    private App app;

    public static void start(Context context) {
        Intent intent = new Intent(context, UserInfoActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);

        app = App.getInstance(this);

        UserDto userDto = app.getCurrentUser();

        showUser(userDto);

        Button menuButton = findViewById(R.id.btn_user_main_menu);
        menuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu menu = app.getMainMenu(UserInfoActivity.this, v);

                menu.setOnMenuItemClickListener(new MainMenuDefaultListener() {

                    @Override
                    protected Context getContext() {
                        return UserInfoActivity.this;
                    }

                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        if (super.onMenuItemClick(item)) {
                            return true;
                        }
                        int itemId = item.getItemId();
                        switch (itemId) {
                            case R.id.main_menu_edit:
                                Log.i(TAG, "EDIT");
                                return true;
                            case R.id.main_menu_create:
                                Log.i(TAG, "CREATE");
                                return true;
                            case R.id.main_menu_save:
                                Log.i(TAG, "SAVE");
                                return true;
                            case R.id.main_menu_delete:
                                Log.i(TAG, "DELETE");
                                return true;
                        }
                        return false;
                    }
                });
                menu.show();
            }
        });
    }

    private void showUser(UserDto userDto) {
        PersonDto personDto;

        if (userDto == null) {
            userDto = new UserDto();
        }

        personDto = userDto.getPerson();

        Button btnPerson = findViewById(R.id.btn_user_person);

        if (personDto != null) {
            btnPerson.setText(personDto.getFirstName() + " " + personDto.getLastName());
            ((EditText) findViewById(R.id.txt_edit_user_identification)).setText(personDto.getIdentification());

        } else {
            ((Button) findViewById(R.id.btn_user_person)).setText(R.string.dash);
            ((EditText) findViewById(R.id.txt_edit_user_identification)).setText(R.string.dash);
        }

        btnPerson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PersonInfoActivity.start(UserInfoActivity.this, personDto);
            }
        });


        ((EditText) findViewById(R.id.txt_edit_user_username)).setText(userDto.getName());
        ((EditText) findViewById(R.id.txt_edit_user_id)).setText(userDto.getId());
    }


    private void markEditable(boolean editable) {
        showId(editable);

        EditText username = findViewById(R.id.txt_edit_user_username);
        EditText password = findViewById(R.id.txt_edit_user_password);
        EditText userIdentificator = findViewById(R.id.txt_edit_user_identification);

        username.setFocusable(editable);
        password.setFocusable(editable);
        userIdentificator.setFocusable(editable);
    }

    private void createNew() {
        clearAll();
        markEditable(true);
    }

    private void clearAll() {
        EditText username = findViewById(R.id.txt_edit_user_username);
        EditText password = findViewById(R.id.txt_edit_user_password);
        EditText userIdentificator = findViewById(R.id.txt_edit_user_identification);
        EditText id = findViewById(R.id.txt_edit_user_id);

        username.getText().clear();
        password.getText().clear();
        userIdentificator.getText().clear();
        id.getText().clear();
    }

    private void save() {
        EditText username = findViewById(R.id.txt_edit_user_username);
        EditText password = findViewById(R.id.txt_edit_user_password);
        EditText userIdentificator = findViewById(R.id.txt_edit_user_identification);
        EditText id = findViewById(R.id.txt_edit_user_id);

        if (!validate()) {
            return;
        }

        UserDto userDto = new UserDto();

        userDto.setName(username.getText().toString());
        userDto.setPassword(password.getText().toString());
        UserService.startCreateUserById2(this, userDto, userIdentificator.getText().toString());

    }

    private boolean validate() {
        boolean hasErrors = false;
        EditText username = findViewById(R.id.txt_edit_user_username);
        EditText password = findViewById(R.id.txt_edit_user_password);
        EditText userIdentificator = findViewById(R.id.txt_edit_user_identification);

        String text;

        if (isNullOrEmpty(username.getText().toString())) {
            hasErrors = true;
            username.setError(getString(R.string.error_field_required));
        }

        if (isNullOrEmpty(password.getText().toString())) {
            hasErrors = true;
            password.setError(getString(R.string.error_field_required));
        }

        if (isNullOrEmpty(userIdentificator.getText().toString())) {
            hasErrors = true;
            userIdentificator.setError(getString(R.string.error_field_required));
        }

        return hasErrors;
    }

    private void showId(boolean show) {
        TextView idLabel = findViewById(R.id.txt_lbl_user_id);
        TextView idText = findViewById(R.id.txt_edit_user_id);

        if (show) {
            idLabel.setVisibility(View.INVISIBLE);
            idText.setVisibility(View.GONE);
        } else {
            idLabel.setVisibility(View.VISIBLE);
            idLabel.setVisibility(View.VISIBLE);
        }
    }


}
