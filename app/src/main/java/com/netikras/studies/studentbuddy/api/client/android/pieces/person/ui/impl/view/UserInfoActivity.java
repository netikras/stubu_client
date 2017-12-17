package com.netikras.studies.studentbuddy.api.client.android.pieces.person.ui.impl.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
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
import com.netikras.studies.studentbuddy.api.client.android.pieces.person.ui.presenter.UserMvpPresenter;
import com.netikras.studies.studentbuddy.api.client.android.pieces.person.ui.view.UserMvpView;
import com.netikras.studies.studentbuddy.api.client.android.service.ServiceRequest.Subscriber;
import com.netikras.studies.studentbuddy.core.data.api.dto.PersonDto;
import com.netikras.studies.studentbuddy.core.data.api.dto.meta.RoleDto;
import com.netikras.studies.studentbuddy.core.data.api.dto.meta.RolePermissionDto;
import com.netikras.studies.studentbuddy.core.data.api.dto.meta.UserDto;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

import static com.netikras.tools.common.security.IntegrityUtils.coalesce;
import static com.netikras.tools.common.security.IntegrityUtils.isNullOrEmpty;

public class UserInfoActivity extends BaseActivity implements UserMvpView {

    private static final String TAG = "UserInfoActivity";

    @Inject
    UserMvpPresenter<UserMvpView> presenter;

    private ViewFields fields;
    private static UserDto lastEntry = null;

    public static void start(Context context) {
        Intent intent = new Intent(context, UserInfoActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected List<Integer> excludeMenuItems() {
        return Arrays.asList(R.id.main_menu_create, R.id.main_menu_delete, R.id.main_menu_user);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);
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
        fields = initFields(new ViewFields());
        onAttach(this);
        getFields().postInit();
        presenter.onAttach(this);
        addMenu();
        if (lastEntry != null) {
            showUser(lastEntry);
        }
        executeTask();
    }

    public ViewFields getFields() {
        return fields;
    }

    @Override
    protected void menuOnClickDelete() {
        getFields().enableEdit(false);
        showLoading();
        presenter.delete(new ErrorsAwareSubscriber<Boolean>() {
            @Override
            public void onSuccess(Boolean response) {
                showUser(null);
            }
        }, fields.getId());
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
        UserDto userDto = collect();
        getFields().setPassword("");

        showLoading();
        Subscriber<UserDto> subscriber = new ErrorsAwareSubscriber<UserDto>() {
            @Override
            public void onSuccess(UserDto response) {
                showUser(response);
            }
        };

        if (isNullOrEmpty(getFields().getId())) {
            presenter.create(subscriber, userDto);
        } else {
            if (!isNullOrEmpty(userDto.getPassword())) {
                presenter.changePassword(new ErrorsAwareSubscriber<Boolean>() {
                    @Override
                    public void onSuccess(Boolean response) {
                        super.onSuccess(response);
                    }
                }, userDto);
            } else {
                presenter.update(subscriber, userDto);
            }
        }
    }

    @Override
    public void showUser(UserDto userDto) {
        getFields().reset();

        if (userDto == null) {
            return;
        }

        getFields().setPerson(userDto.getPerson());

        getFields().setUsername(userDto.getName());
        getFields().setId(userDto.getId());
        getFields().setRoles(userDto.getRoles());

        if (lastEntry != null) {
            lastEntry = null;
        } else {
            prepare(userDto);
        }
    }

    @Override
    protected boolean isPartial() {
        UserDto dto = collect();
        if (dto == null) {
            return true;
        }
        Object item = coalesce(dto.getPerson(), dto.getRoles());
        return item == null;
    }

    private void prepare(UserDto entity) {
        if (hasTriedToFetch()) {
            setTriedToFetch(false);
            return;
        }
        if (entity == null || isNullOrEmpty(entity.getId())) {
            return;
        }
        if (isPartial()) {
            showLoading();
            setTriedToFetch(true);
            presenter.getById(new ErrorsAwareSubscriber<UserDto>() {
                @Override
                public void onCacheHit(UserDto response) {
                    if (response != null && !isNullOrEmpty(response.getName())) {
                        setFetchRequired(false);
                        executeOnSuccess(response);
                    }
                }

                @Override
                public void onSuccess(UserDto response) {
                    runOnUiThread(() -> showUser(response));
                }
            }, entity.getId());
        }
    }


    @OnClick(R.id.btn_user_person)
    void showPerson() {
        UserDto userDto = collect();
//
//        if (userDto == null || userDto.getPerson() == null) {
//            onError(R.string.err_no_person);
//            return;
//        }
        showLoading();
        presenter.fetchPerson(new ErrorsAwareSubscriber<UserDto>() {

            @Override
            public void onCacheHit(UserDto response) {
                setFetchRequired(false); // let's trust cached data, shall we
                executeOnSuccess(response);
            }

            @Override
            public void onSuccess(UserDto response) {
                startView(PersonInfoActivity.class, new ViewTask<PersonInfoActivity>() {
                    @Override
                    public void execute() {
                        getActivity().showPerson(response.getPerson());
                    }
                });
            }
        }, userDto);
    }

    private UserDto collect() {
        UserDto user = new UserDto();
        user.setId(getFields().getId());
        user.setName(getFields().getUsername());
        user.setPassword(getFields().getPassword());
        user.setPerson(getFields().getPerson());
        user.setRoles(getFields().getRoles());
        return user;
    }

    @Override
    protected void menuOnClickRefresh() {
        showLoading();
        presenter.getById(new ErrorsAwareSubscriber<UserDto>() {
            @Override
            public void onSuccess(UserDto response) {
                if (response != null) {
                    runOnUiThread(() -> showUser(response));
                }
            }
        }, getFields().getId());
    }

    @Override
    protected void menuOnClickEdit() {
        getFields().enableEdit(true);
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

        @BindView(R.id.list_user_roles)
        ListView roles;

        @BindView(R.id.txt_lbl_user_id)
        TextView labelId;


        private ListHandler<String> listHandler = new ListHandler<String>() {
            @Override
            public List<String> getListData() {
                return getRoles();
            }

            @Override
            public void onRowClick(String item) {
                showLoading();
                presenter.getRoleByName(new ErrorsAwareSubscriber<RoleDto>() {
                    @Override
                    public void onSuccess(RoleDto role) {
                        if (role == null) {
                            return;
                        }

                        if (isNullOrEmpty(role.getPermissions())) {
                            role.setPermissions(new ArrayList<>());
                        } else {
                            role.getPermissions().sort(Comparator
                                    .comparing(RolePermissionDto::getResource)
                                    .thenComparing(RolePermissionDto::getAction)
                                    .thenComparing(RolePermissionDto::isStrict)
                            );
                        }

                        showList(UserInfoActivity.this, new ListHandler<RolePermissionDto>() {
                            @Override
                            public ListRow getNewRow(View convertView) {
                                return new RolesPermissionsListRow(convertView);
                            }

                            @Override
                            public List<RolePermissionDto> getListData() {
                                return role.getPermissions();
                            }

                            @Override
                            public String getToolbarText() {
                                return role.getName();
                            }

                            class RolesPermissionsListRow extends ListRow<RolePermissionDto> {
                                TextView text;

                                public RolesPermissionsListRow(View rowView) {
                                    super(null);
                                    text = getDefaultListTextView(rowView);
                                    rowView.setTag(this);
                                }

                                @Override
                                public void assign(RolePermissionDto item) {
                                    if (item == null) {
                                        text.setText("<???>");
                                        return;
                                    }

                                    StringBuilder builder = new StringBuilder();
                                    builder.append(item.getResource()).append(" : ").append(item.getAction());
                                    if (item.isStrict()) {
                                        builder.append(" <!>");
                                    }

                                    text.setText(builder.toString());
                                }
                            }
                        });

                    }
                }, item);
            }
        };

        @Override
        public void postInit() {
            roles.setTag(new ArrayList<String>());
            CustomListAdapter adapter = new CustomListAdapter();
            adapter.bind(UserInfoActivity.this, roles, listHandler);
        }

        @Override
        protected Collection<TextView> getAllFields() {
            return Arrays.asList(id, personName, identification, username, password);
        }

        @Override
        protected Map<TextView, Integer> getEditableFields() {
            Map<TextView, Integer> types = super.getEditableFields();

            types.put(identification, InputType.TYPE_CLASS_TEXT);
            types.put(username, InputType.TYPE_CLASS_TEXT);
            types.put(password, InputType.TYPE_TEXT_VARIATION_PASSWORD);

            return types;
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

        public void setPerson(PersonDto person) {
            setTag(this.personName, person);
            if (person != null) {
                setPersonName(person.getFirstName() + " " + person.getLastName());
                setIdentification(person.getIdentification());
            }
        }

        public PersonDto getPerson() {
            return (PersonDto) getTag(personName);
        }

        public void setRoles(List<String> roles) {
            getRoles().clear();
            if (roles != null) {
                getRoles().addAll(roles);
            }
            listHandler.onDataSetChanged();
        }

        public List<String> getRoles() {
            return (List<String>) getTag(roles);
        }

        @Override
        public void enableEdit(boolean enable) {
            super.enableEdit(enable);
            if (enable) {
                setVisible(labelId, true);
                setVisible(id, true);
            } else {
//                labelId.setVisibility(View.INVISIBLE);
                setVisible(labelId, false);
                setVisible(id, null);
            }
        }

    }

}
