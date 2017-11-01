package com.netikras.studies.studentbuddy.api.client.android.ui.login.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.netikras.studies.studentbuddy.api.client.android.R;
import com.netikras.studies.studentbuddy.api.client.android.ui.base.BaseActivity;
import com.netikras.studies.studentbuddy.api.client.android.ui.base.BaseViewFields;
import com.netikras.studies.studentbuddy.api.client.android.ui.login.presenter.LoginMvpPresenter;

import java.util.Arrays;
import java.util.Collection;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends BaseActivity implements LoginMvpView {


    @Inject
    LoginMvpPresenter<? extends LoginMvpView> presenter;

    private ViewFields fields;

    public static void start(Context context) {
        Intent intent = new Intent(context, LoginActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void setUp() {

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    @Override
    public View onCreateView(View parent, String name, Context context, AttributeSet attrs) {
        View view = super.onCreateView(parent, name, context, attrs);
        ButterKnife.bind(view);
        return view;
    }

    @OnClick(R.id.btn_login_proceed)
    public void proceed() {
        presenter.proceedLogin(fields.getUsername(), fields.getPassword());
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

