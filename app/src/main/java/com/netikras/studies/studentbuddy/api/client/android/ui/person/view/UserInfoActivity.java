package com.netikras.studies.studentbuddy.api.client.android.ui.person.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.netikras.studies.studentbuddy.api.client.android.App;
import com.netikras.studies.studentbuddy.api.client.android.MainMenuDefaultListener;
import com.netikras.studies.studentbuddy.api.client.android.R;
import com.netikras.studies.studentbuddy.api.client.android.ui.base.BaseActivity;
import com.netikras.studies.studentbuddy.api.client.android.ui.base.BaseViewFields;
import com.netikras.studies.studentbuddy.api.client.android.ui.person.UserService;
import com.netikras.studies.studentbuddy.api.client.android.ui.person.presenter.UserMvpPresenter;
import com.netikras.studies.studentbuddy.core.data.api.dto.PersonDto;
import com.netikras.studies.studentbuddy.core.data.api.dto.meta.UserDto;

import java.util.Arrays;
import java.util.Collection;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

import static com.netikras.tools.common.security.IntegrityUtils.isNullOrEmpty;

public class UserInfoActivity extends BaseActivity implements UserMvpView {

    private static final String TAG = "UserInfoActivity";

    @Inject
    App app;
    @Inject
    UserMvpPresenter<? extends UserMvpView> presenter;

    private ViewFields fields;

    public static void start(Context context) {
        Intent intent = new Intent(context, UserInfoActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);

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

    @Override
    protected void setUp() {

    }

    @Override
    public void showUser(UserDto userDto) {
        if (userDto == null) {
            userDto = new UserDto();
        }
        PersonDto personDto = userDto.getPerson();

        if (personDto != null) {
            fields.setPersonName(personDto.getFirstName() + " " + personDto.getLastName());
            fields.setIdentification(personDto.getIdentification());
        } else {
            fields.setPersonName(getString(R.string.dash));
            fields.setIdentification(getString(R.string.dash));
        }

        fields.setUsername(userDto.getName());
        fields.setId(userDto.getId());
    }

    @OnClick(R.id.btn_user_person)
    void showPerson() {
        UserDto userDto = collect();
        presenter.showPersonForUser(userDto);
    }

    private UserDto collect() {
        UserDto user = new UserDto();
        user.setId(fields.getId());
        user.setName(fields.getUsername());
        user.setPassword(fields.getPassword());
        user.setPerson(new PersonDto());
        user.getPerson().setIdentification(fields.getIdentification());
        return user;
    }

    public class ViewFields extends BaseViewFields {

        @BindView(R.id.txt_edit_user_id)
        EditText id;
        @BindView(R.id.btn_user_person)
        Button personName;
        @BindView(R.id.txt_edit_user_identification)
        EditText identification;
        @BindView(R.id.txt_edit_user_username)
        EditText username;
        @BindView(R.id.txt_edit_user_password)
        EditText password;

        @BindView(R.id.txt_lbl_user_id)
        TextView labelId;

        @Override
        protected Collection<TextView> getAllFields() {
            return Arrays.asList(id, personName, identification, username, password);
        }

        @Override
        protected Collection<TextView> getEditableFields() {
            Collection<TextView> all = getAllFields();
            all.remove(id);
            all.remove(personName);
            return all;
        }

        public String getId() {
            return getString(id);
        }

        public void setId(String id) {
            setString(this.id, id);
        }

        public String getIdentification() {
            return getString(identification);
        }

        public void setIdentification(String identification) {
            setString(this.identification, identification);
        }

        public String getUsername() {
            return getString(username);
        }

        public void setUsername(String username) {
            setString(this.username, username);
        }

        public String getPassword() {
            return getString(password);
        }

        public void setPassword(String password) {
            setString(this.password, password);
        }

        public String getPersonName() {
            return getString(personName);
        }

        public void setPersonName(String personName) {
            setString(this.personName, personName);
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
