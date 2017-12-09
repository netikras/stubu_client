package com.netikras.studies.studentbuddy.api.client.android.pieces.login.ui.impl.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.EditText;
import android.widget.TextView;

import com.netikras.studies.studentbuddy.api.client.android.App;
import com.netikras.studies.studentbuddy.api.client.android.R;
import com.netikras.studies.studentbuddy.api.client.android.conf.di.DepInjector;
import com.netikras.studies.studentbuddy.api.client.android.pieces.base.BaseActivity;
import com.netikras.studies.studentbuddy.api.client.android.pieces.base.BaseViewFields;
import com.netikras.studies.studentbuddy.api.client.android.pieces.login.ui.presenter.LoginMvpPresenter;
import com.netikras.studies.studentbuddy.api.client.android.pieces.login.ui.view.LoginMvpView;
import com.netikras.studies.studentbuddy.api.client.android.pieces.main.ui.impl.view.MainActivity;
import com.netikras.studies.studentbuddy.core.data.api.dto.meta.UserDto;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends BaseActivity implements LoginMvpView {


    @Inject
    App app;
    @Inject
    LoginMvpPresenter<LoginMvpView> presenter;

    private ViewFields fields;

    public static void start(Context context) {
        Intent intent = new Intent(context, LoginActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected List<Integer> excludeMenuItems() {
        return Arrays.asList(R.id.main_menu_create, R.id.main_menu_save, R.id.main_menu_refresh, R.id.main_menu_login, R.id.main_menu_delete, R.id.main_menu_edit);
    }

    @Override
    protected void setUp() {
        DepInjector.inject(this);
        onAttach(this);
        presenter.onAttach(this);
        fields = initFields(new ViewFields());
        fields.enableEdit(true);
        fields.setUsername(presenter.getLastLoginUsername());
        addMenu();
        executeTask();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setUp();
    }

    @Override
    @OnClick(R.id.btn_login_proceed)
    public void proceedLogin() {
        presenter.setLastLoginUsername(fields.getUsername());
        hideKeyboard();
        showLoading();
        presenter.proceedLogin(fields.getUsername(), fields.getPassword(), new ErrorsAwareSubscriber<UserDto>(){
            @Override
            public void onSuccess(UserDto response) {
                finish();
                startView(MainActivity.class, new ViewTask<MainActivity>() {
                    @Override
                    public void execute() {
                        // nothing is required so far
                    }
                });
            }
        });
    }

    @Override
    public void proceedLogout() {
        showLoading();
        presenter.logout(new ErrorsAwareSubscriber<Boolean>() {
            @Override
            public void onSuccess(Boolean response) {
                app.setCurrentUser(null);
            }
        }, getCurrentUser());
    }

    public class ViewFields extends BaseViewFields {

        @BindView(R.id.txt_edit_login_username)
        EditText username;
        @BindView(R.id.txt_edit_login_password)
        EditText password;

        @Override
        protected Collection<TextView> getAllFields() {
            return Arrays.asList(username, password);
        }

        @Override
        public void enableEdit(boolean enable) {

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
    }
}

