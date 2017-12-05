package com.netikras.studies.studentbuddy.api.client.android.pieces.base;

import android.annotation.TargetApi;
import android.app.Activity;
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
import android.text.InputType;
import android.util.AttributeSet;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.netikras.studies.studentbuddy.api.client.android.App;
import com.netikras.studies.studentbuddy.api.client.android.R;
import com.netikras.studies.studentbuddy.api.client.android.conf.di.DepInjector;
import com.netikras.studies.studentbuddy.api.client.android.conf.di.component.ActivityComponent;
import com.netikras.studies.studentbuddy.api.client.android.pieces.SearchActivity;
import com.netikras.studies.studentbuddy.api.client.android.pieces.base.list.ListHandler;
import com.netikras.studies.studentbuddy.api.client.android.pieces.base.list.SimpleListActivity;
import com.netikras.studies.studentbuddy.api.client.android.pieces.login.ui.impl.view.LoginActivity;
import com.netikras.studies.studentbuddy.api.client.android.pieces.person.ui.impl.view.UserInfoActivity;
import com.netikras.studies.studentbuddy.api.client.android.pieces.settings.ui.impl.view.SettingsActivity;
import com.netikras.studies.studentbuddy.api.client.android.service.ServiceRequest;
import com.netikras.studies.studentbuddy.api.client.android.util.CommonUtils;
import com.netikras.studies.studentbuddy.api.client.android.util.Exchange;
import com.netikras.studies.studentbuddy.api.client.android.util.NetworkUtils;
import com.netikras.studies.studentbuddy.api.client.android.util.misc.YesNoDialog;
import com.netikras.studies.studentbuddy.core.data.api.dto.meta.UserDto;
import com.netikras.tools.common.exception.ErrorBody;
import com.netikras.tools.common.exception.ErrorsCollection;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.Unbinder;

import static com.netikras.tools.common.security.IntegrityUtils.isNullOrEmpty;

/**
 * Created by netikras on 17.10.30.
 */

public abstract class BaseActivity extends AppCompatActivity
        implements MvpView, BaseFragment.Callback {

//    private ProgressDialog mProgressDialog;

    private Unbinder mUnBinder;

    private MvpView view = null;

    private BaseViewFields fields;

    @Inject
    App app;
    @Inject
    Exchange exchange;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DepInjector.inject(this);
    }

    protected UserDto getCurrentUser() {
        return app.getCurrentUser();
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

    /**
     * @param viewClass activity class
     * @param task      something to do when view is started. <br/>
     *                  NOTE: before calling execute() view will call setActivity(). Utilize that
     */
    protected <VIEW extends MvpView> void startView(Class<VIEW> viewClass, ViewTask<VIEW> task) {
        Intent intent = new Intent(this, viewClass);
        intent.putExtra("task", exchange.put(task));
        startActivity(intent);
    }

    @Override
    public void showLoading() {
//        hideLoading();
//        mProgressDialog = CommonUtils.showLoadingDialog(this);
        CommonUtils.showSpinner(this);
    }

    @Override
    public void hideLoading() {
//        if (mProgressDialog != null && mProgressDialog.isShowing()) {
//            mProgressDialog.cancel();
//        }
        CommonUtils.hideSpinner(this);
    }

    private void showSnackBar(Context context, String message) {
        Activity activity = this;
        if (context != null && Activity.class.isAssignableFrom(context.getClass())) {
            activity = (Activity) context;
        }
        Snackbar snackbar = Snackbar.make(activity.findViewById(android.R.id.content),
                message, Snackbar.LENGTH_SHORT);
        View sbView = snackbar.getView();
        TextView textView = sbView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(ContextCompat.getColor(activity, R.color.white));
        snackbar.show();
    }

    @Override
    public void onError(String message) {
        onError(this, message);
    }

    @Override
    public void onError(Context context, String message) {
        if (message != null) {
            showSnackBar(context, message);
        } else {
            showSnackBar(context, getString(R.string.some_error));
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

    protected <TV extends TextView> TV makeMultiline(TV textView) {
        if (textView != null) {
            textView.setElegantTextHeight(true);
            textView.setSingleLine(false);
            textView.setInputType(InputType.TYPE_TEXT_FLAG_MULTI_LINE);
        }

        return textView;
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
//        executeTask();
    }

    protected List<Integer> excludeMenuItems() {
        return Arrays.asList();
    }

    protected void addMenu() {
        int btnId = R.id.btn_main_menu;
        Button menuButton = findViewById(btnId);
        menuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu menu = getMainMenu(BaseActivity.this, v);

                menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {

                    protected Context getContext() {
                        return BaseActivity.this;
                    }

                    @Override
                    public boolean onMenuItemClick(MenuItem item) {

                        int itemId = item.getItemId();
                        switch (itemId) {
                            case R.id.main_menu_user:
                                startActivity(getContext(), UserInfoActivity.class, new ViewTask<UserInfoActivity>() {
                                    @Override
                                    public void execute() {
                                        getActivity().showUser(getCurrentUser());
                                    }
                                });
                                return true;
                            case R.id.main_menu_login:
                                startActivity(getContext(), LoginActivity.class, null);
                                return true;
                            case R.id.main_menu_search:
                                startActivity(getContext(), SearchActivity.class, null);
                                return true;
                            case R.id.main_menu_settings:
                                startActivity(getContext(), SettingsActivity.class, null);
                                return true;
                            case R.id.main_menu_edit:
                                menuOnClickEdit();
                                return true;
                            case R.id.main_menu_create:
                                menuOnClickCreate();
                                return true;
                            case R.id.main_menu_save:
                                menuOnClickSave();
                                return true;
                            case R.id.main_menu_refresh:
                                menuOnClickRefresh();
                                return true;
                            case R.id.main_menu_delete:
                                new YesNoDialog()
                                        .text(getString(R.string.yesno_question_delete))
                                        .yes(getString(R.string.yesno_choice_yes), new YesNoDialog.OnClick() {
                                            @Override
                                            protected void onClick(DialogInterface dialog) {
                                                menuOnClickDelete();
                                                super.onClick(dialog);
                                            }
                                        })
                                        .no(getString(R.string.yesno_choice_no), null)
                                        .show(getContext());
                                return true;
                            case R.id.main_menu_logout:
                                new YesNoDialog()
                                        .text(getString(R.string.yesno_question_logout))
                                        .yes(getString(R.string.yesno_choice_yes), new YesNoDialog.OnClick() {
                                            @Override
                                            protected void onClick(DialogInterface dialog) {
                                                super.onClick(dialog);
                                                startView(LoginActivity.class, new ViewTask<LoginActivity>() {
                                                    @Override
                                                    public void execute() {
                                                        getActivity().proceedLogout();
                                                    }
                                                });
                                            }
                                        })
                                        .no(getString(R.string.yesno_choice_no), null)
                                        .show(getContext());
                                return true;
                        }
                        return false;
                    }
                });
                menu.show();
            }
        });
    }

    public PopupMenu getMainMenu(Context context, View view) {
        PopupMenu menu = new PopupMenu(context, view);
        MenuInflater inflater = menu.getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu.getMenu());

        removeUnavailableItems(menu.getMenu());
//        menu.show();
        return menu;
    }

    private void removeUnavailableItems(Menu menu) {

        Set<Integer> excluded = new HashSet<>(excludeMenuItems());

        if (getCurrentUser() == null) {
            excluded.add(R.id.main_menu_user);
            excluded.add(R.id.main_menu_logout);
            excluded.add(R.id.main_menu_create);
            excluded.add(R.id.main_menu_delete);
            excluded.add(R.id.main_menu_edit);
            excluded.add(R.id.main_menu_save);
            excluded.add(R.id.main_menu_search);
            setVisible(menu.findItem(R.id.main_menu_login), true);
        } else {
            setVisible(menu.findItem(R.id.main_menu_user), true);
            excluded.add(R.id.main_menu_login);
            setVisible(menu.findItem(R.id.main_menu_logout), true);
        }

        if (!isNullOrEmpty(excluded)) {
            for (int item : excluded) {
                setVisible(menu.findItem(item), false);
            }
        }
    }




    private void setVisible(MenuItem item, boolean show) {
        if (item == null) {
            return;
        }

        item.setVisible(show);
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

    protected void menuOnClickRefresh() {

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

    @Override
    public View onCreateView(View parent, String name, Context context, AttributeSet attrs) {
        return super.onCreateView(parent, name, context, attrs);
    }

    public <T> void showList(Context context, ListHandler<T> handler) {
        Intent intent = new Intent(context, SimpleListActivity.class);
        intent.putExtra("handler", exchange.put(handler));
        startActivity(intent);
    }

    protected abstract void setUp();

    public void startActivity(Context fromContext, Class<? extends MvpView> activity, ViewTask task) {
        Intent intent = new Intent(fromContext, activity);
        intent.putExtra("task", exchange.put(task));
        fromContext.startActivity(intent);
    }


    public abstract static class ViewTask<A extends MvpView> {

        private A act;

        public abstract void execute();

        protected void setActivity(A activity) {
            act = activity;
        }

        protected A getActivity() {
            return act;
        }
    }

    protected class ErrorsAwareSubscriber<T> extends ServiceRequest.Subscriber<T> {

        @Override
        public void executeOnError(ErrorsCollection errors) {
            hideLoading();
            super.executeOnError(errors);
        }

        @Override
        public void executeOnSuccess(T response) {
            hideLoading();
            super.executeOnSuccess(response);
        }

        @Override
        public void onError(ErrorsCollection errors) {
            if (isNullOrEmpty(errors)) {
                return;
            }
            for (ErrorBody error : errors) {
                BaseActivity.this.onError(error.getMessage1() + "\n" + error.getMessage2());
            }
        }

    }


    protected void executeTask() {
        Intent intent = getIntent();
        if (intent == null) {
            return;
        }

        String taskKey = intent.getStringExtra("task");
        if (isNullOrEmpty(taskKey)) {
            return;
        }

        ViewTask task = (ViewTask) exchange.get(taskKey);
        if (task != null) {
            task.setActivity(view);
            task.execute();
        }
    }
}
