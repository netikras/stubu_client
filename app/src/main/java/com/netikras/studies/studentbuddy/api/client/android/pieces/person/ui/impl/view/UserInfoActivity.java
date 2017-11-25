package com.netikras.studies.studentbuddy.api.client.android.pieces.person.ui.impl.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.netikras.studies.studentbuddy.api.client.android.App;
import com.netikras.studies.studentbuddy.api.client.android.R;
import com.netikras.studies.studentbuddy.api.client.android.conf.di.DepInjector;
import com.netikras.studies.studentbuddy.api.client.android.pieces.base.BaseActivity;
import com.netikras.studies.studentbuddy.api.client.android.pieces.base.BaseViewFields;
import com.netikras.studies.studentbuddy.api.client.android.pieces.base.list.ListHandler;
import com.netikras.studies.studentbuddy.api.client.android.pieces.base.list.ListRow;
import com.netikras.studies.studentbuddy.api.client.android.pieces.person.ui.presenter.UserMvpPresenter;
import com.netikras.studies.studentbuddy.api.client.android.pieces.person.ui.view.UserMvpView;
import com.netikras.studies.studentbuddy.core.data.api.dto.PersonDto;
import com.netikras.studies.studentbuddy.core.data.api.dto.meta.UserDto;
import com.netikras.studies.studentbuddy.core.data.api.dto.school.LectureDto;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

import static com.netikras.tools.common.security.IntegrityUtils.isNullOrEmpty;

public class UserInfoActivity extends BaseActivity implements UserMvpView {

    private static final String TAG = "UserInfoActivity";

    @Inject
    App app;
    @Inject
    UserMvpPresenter<UserMvpView> presenter;

    private ViewFields fields;

    public static void start(Context context) {
        Intent intent = new Intent(context, UserInfoActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);
        setUp();

//        UserDto userDto = app.getCurrentUser();
//        showUser(userDto);
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

    @Override
    protected void menuOnClickDelete() {
        getFields().enableEdit(false);
        presenter.delete(this, fields.getId());
    }

    @Override
    protected void menuOnClickCreate() {
        getFields().reset();
        getFields().setId("");
        getFields().enableEdit(true);
    }

    @Override
    protected void menuOnClickSave() {
        getFields().enableEdit(false);
        if (isNullOrEmpty(getFields().getId())) {
            presenter.create(this, collect());
        } else {
            presenter.update(this, collect());
        }
    }

    @Override
    public void showUser(UserDto userDto) {
        fields.reset();

        if (userDto == null) {
            return;
        }
        PersonDto personDto = userDto.getPerson();

        if (personDto != null) {
            fields.setPersonName(personDto.getFirstName() + " " + personDto.getLastName());
            fields.setIdentification(personDto.getIdentification());
        } else {
//            fields.setPersonName(getString(R.string.dash));
//            fields.setIdentification(getString(R.string.dash));
        }

        fields.setUsername(userDto.getName());
        fields.setId(userDto.getId());
    }


    @OnClick(R.id.btn_user_person)
    void showPerson() {
//        UserDto userDto = collect();
//        presenter.showPersonForUser(this, userDto);
        showList(this, new ListHandler<LectureDto>() {
            @Override
            public ListRow getNewRow(View convertView) {
                return new LectureRow(convertView);
            }

            @Override
            public ListRow extractExistingRow(View convertView) {
                if (convertView == null) {
                    return null;
                }
                return (ListRow) convertView.getTag();
            }

            @Override
            public int getActivityLayout() {
                return super.getActivityLayout();
            }

            @Override
            public int getRowLayout() {
                return super.getRowLayout();
            }

            @Override
            public List<LectureDto> getListData() {
                List<LectureDto> lectures = new ArrayList<>();
                LectureDto dto;

                dto = new LectureDto();
                dto.setId("aaa");
                lectures.add(dto);

                dto = new LectureDto();
                dto.setId("bbb");
                lectures.add(dto);

                dto = new LectureDto();
                dto.setId("ccc");
                lectures.add(dto);

                return lectures;
            }

            @Override
            public void onRowClick(LectureDto item) {
                onError(getListContext(), "Lecture selected: " + item.getId());
                System.out.println("Lecture selected: " + item.getId());
            }

            @Override
            public String getToolbarText() {
                return "Lectures";
            }

            class LectureRow extends ListRow<LectureDto> {

                TextView text;

                public LectureRow(View rowView) {
                    super(null);
                    if (rowView == null) {
                        return;
                    }
                    text = rowView.findViewById(android.R.id.text1);
                    makeMultiline(text);
                    rowView.setTag(this);
                }

                @Override
                public void assign(LectureDto item) {
                    text.setText(item.getId());
                }
            }
        });
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
            return Arrays.asList(identification, username, password);
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
