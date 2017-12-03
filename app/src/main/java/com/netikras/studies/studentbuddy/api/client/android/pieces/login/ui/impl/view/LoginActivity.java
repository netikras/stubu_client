package com.netikras.studies.studentbuddy.api.client.android.pieces.login.ui.impl.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

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

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends BaseActivity implements LoginMvpView {


    @Inject
    LoginMvpPresenter<LoginMvpView> presenter;

    private ViewFields fields;

    public static void start(Context context) {
        Intent intent = new Intent(context, LoginActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void setUp() {
        DepInjector.inject(this);
        onAttach(this);
        presenter.onAttach(this);
        fields = initFields(new ViewFields());
        fields.enableEdit(true);
        fields.setUsername(presenter.getLastLoginUsername());
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setUp();
    }

    @OnClick(R.id.btn_login_proceed)
    public void proceed() {
        presenter.setLastLoginUsername(fields.getUsername());
        presenter.proceedLogin(fields.getUsername(), fields.getPassword(), new ErrorsAwareSubscriber<UserDto>(){
            @Override
            public void onSuccess(UserDto response) {
                startView(MainActivity.class, new ViewTask<MainActivity>() {
                    @Override
                    public void execute() {
                        // nothing is required so far
                    }
                });
            }
        });
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

