package com.netikras.studies.studentbuddy.api.client.android.ui.base;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.netikras.studies.studentbuddy.api.client.android.App;
import com.netikras.studies.studentbuddy.api.client.android.MainMenuDefaultListener;
import com.netikras.studies.studentbuddy.api.client.android.R;
import com.netikras.studies.studentbuddy.api.client.android.conf.di.DepInjector;
import com.netikras.studies.studentbuddy.api.client.android.conf.di.component.ActivityComponent;
import com.netikras.studies.studentbuddy.api.client.android.ui.login.impl.view.LoginActivity;
import com.netikras.studies.studentbuddy.api.client.android.util.CommonUtils;
import com.netikras.studies.studentbuddy.api.client.android.util.NetworkUtils;
import com.netikras.studies.studentbuddy.api.client.android.util.misc.YesNoDialog;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by netikras on 17.10.30.
 */

public abstract class BaseActivity extends AppCompatActivity
        implements MvpView, BaseFragment.Callback {

    private ProgressDialog mProgressDialog;

    private Unbinder mUnBinder;

    private MvpView view = null;

    private BaseViewFields fields;

    @Inject
    App app;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DepInjector.inject(this);

    }

    public ActivityComponent getActivityComponent() {
        return DepInjector.getActivityComponent();
    }

    @Override
    protected void attachBaseContext(Context newBase) {
//        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
        super.attachBaseContext(newBase);
    }

    @TargetApi(Build.VERSION_CODES.M)
    public void requestPermissionsSafely(String[] permissions, int requestCode) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(permissions, requestCode);
        }
    }

    @TargetApi(Build.VERSION_CODES.M)
    public boolean hasPermission(String permission) {
        return Build.VERSION.SDK_INT < Build.VERSION_CODES.M ||
                checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED;
    }

    @Override
    public void showLoading() {
        hideLoading();
        mProgressDialog = CommonUtils.showLoadingDialog(this);
    }

    @Override
    public void hideLoading() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.cancel();
        }
    }

    private void showSnackBar(String message) {
        Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content),
                message, Snackbar.LENGTH_SHORT);
        View sbView = snackbar.getView();
        TextView textView = sbView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(ContextCompat.getColor(this, R.color.white));
        snackbar.show();
    }

    @Override
    public void onError(String message) {
        if (message != null) {
            showSnackBar(message);
        } else {
            showSnackBar(getString(R.string.some_error));
        }
    }

    @Override
    public void onError(@StringRes int resId) {
        onError(getString(resId));
    }

    @Override
    public void showMessage(String message) {
        if (message != null) {
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, getString(R.string.some_error), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void showMessage(@StringRes int resId) {
        showMessage(getString(resId));
    }

    @Override
    public boolean isNetworkConnected() {
        return NetworkUtils.isNetworkConnected(getApplicationContext());
    }

    @Override
    public void onFragmentAttached() {

    }

    @Override
    public void onFragmentDetached(String tag) {

    }

    public void hideKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)
                    getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    @Override
    public void openActivityOnTokenExpire() {
        LoginActivity.start(this);
        finish();
    }

    public void setUnBinder(Unbinder unBinder) {
        mUnBinder = unBinder;
    }


    protected void onAttach(MvpView view) {
        this.view = view;
        if (Activity.class.isAssignableFrom(view.getClass())) {
            setUnBinder(ButterKnife.bind((Activity) view));
//            DepInjector.inject(view);
        }
    }

    protected void addMenu(int menu) {
        Button menuButton = findViewById(menu);
        menuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu menu = app.getMainMenu(BaseActivity.this, v);

                menu.setOnMenuItemClickListener(new MainMenuDefaultListener() {

                    @Override
                    protected Context getContext() {
                        return BaseActivity.this;
                    }

                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        if (super.onMenuItemClick(item)) {
                            return true;
                        }
                        int itemId = item.getItemId();
                        switch (itemId) {
                            case R.id.main_menu_edit:
                                menuOnClickEdit();
                                return true;
                            case R.id.main_menu_create:
                                menuOnClickCreate();
                                return true;
                            case R.id.main_menu_save:
                                menuOnClickSave();
                                return true;
                            case R.id.main_menu_delete:
                                new YesNoDialog()
                                        .text("Ar tikrai norite pašalinti įrašą?")
                                        .yes("Taip", new YesNoDialog.OnClick(){
                                            @Override
                                            protected void onClick(DialogInterface dialog) {
                                                menuOnClickDelete();
                                                super.onClick(dialog);
                                            }
                                        })
                                        .no("Ne", null)
                                        .show(BaseActivity.this);
                                return true;
                        }
                        return false;
                    }
                });
                menu.show();
            }
        });
    }


    protected void menuOnClickEdit() {
        fields.enableEdit(true);
    }

    protected void menuOnClickCreate() {
        fields.enableEdit(false);
    }

    protected void menuOnClickSave() {
        fields.enableEdit(false);
    }

    protected void menuOnClickDelete() {

    }


    protected <F extends BaseViewFields> F initFields(F fields) {
        ButterKnife.bind(fields, this);
        fields.enableEdit(false);
        this.fields = fields;
        return fields;
    }

    @Override
    protected void onDestroy() {

        if (mUnBinder != null) {
            mUnBinder.unbind();
        }
        super.onDestroy();
    }

    protected abstract void setUp();
}
