package com.netikras.studies.studentbuddy.api.client.android.pieces.settings.ui.impl.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import com.netikras.studies.studentbuddy.api.client.android.App;
import com.netikras.studies.studentbuddy.api.client.android.R;
import com.netikras.studies.studentbuddy.api.client.android.conf.di.DepInjector;
import com.netikras.studies.studentbuddy.api.client.android.data.prefs.PreferencesHelper;
import com.netikras.studies.studentbuddy.api.client.android.pieces.base.BaseActivity;
import com.netikras.studies.studentbuddy.api.client.android.pieces.base.BaseViewFields;
import com.netikras.studies.studentbuddy.api.client.android.pieces.settings.ui.presenter.SettingsMvpPresenter;
import com.netikras.studies.studentbuddy.api.client.android.pieces.settings.ui.view.SettingsMvpView;
import com.netikras.tools.common.remote.RemoteEndpointServer;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

public class SettingsActivity extends BaseActivity implements SettingsMvpView {

    @Inject
    App app;
    @Inject
    PreferencesHelper preferencesHelper;
    @Inject
    SettingsMvpPresenter<SettingsMvpView> presenter;

    private ViewFields fields;

    @Override
    protected List<Integer> excludeMenuItems() {
        return Arrays.asList(R.id.main_menu_create, R.id.main_menu_delete, R.id.main_menu_settings, R.id.main_menu_search, R.id.main_menu_login);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        setUp();

//        ConstraintLayout layout = findViewById(R.id.lay_settings);
//        layout.addView(findViewById(R.id.btn_main_main_menu));
//        ConstraintSet set = new ConstraintSet();
//        set.clone(layout);
//        set.connect(R.id.btn_main_main_menu, ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP, 16);
//        set.connect(R.id.btn_main_main_menu, ConstraintSet.RIGHT, ConstraintSet.PARENT_ID, ConstraintSet.RIGHT, 16);
//        set.applyTo(layout);

        showSettings();
    }

    @Override
    protected void setUp() {
        DepInjector.inject(this);
        onAttach(this);
        presenter.onAttach(this);
        fields = initFields(new SettingsActivity.ViewFields());
        addMenu();
        executeTask();
    }

    public static void start(Context context) {
        Intent intent = new Intent(context, SettingsActivity.class);
        context.startActivity(intent);
    }


    private void showSettings() {
        fields.setUrl(presenter.getApiUrl());
        fields.setNotificationsEnabled(presenter.isNotificationsEnabled());
    }

    @Override
    protected void menuOnClickSave() {
        presenter.saveApiUrl(fields.getUrl());
        presenter.saveNotificationsSettings(fields.getNotificationsEnabled());
    }

    private String toString(RemoteEndpointServer server) {
        if (server == null) {
            server = new RemoteEndpointServer();
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(server.getProtocol().name().toLowerCase()).append("://");
        stringBuilder.append(server.getAddress());
        if (server.getPort() > 0) {
            stringBuilder.append(":").append(server.getPort());
        }
        stringBuilder.append(server.getRootUrl());

        return stringBuilder.toString();
    }

    class ViewFields extends BaseViewFields {

        @BindView(R.id.txt_edit_settings_url)
        EditText url;

        @BindView(R.id.switch_settings_notifications_enable)
        Switch notificationsEnabled;

        @BindView(R.id.spinner_settings_notifications_time_before_event)
        Spinner timeDelta;


        @Override
        protected Collection<TextView> getAllFields() {
            return Arrays.asList(url);
        }


        public String getUrl() {
            return getString(url);
        }

        public void setUrl(String url) {
            setString(this.url, url);
        }

        public boolean getNotificationsEnabled() {
            return notificationsEnabled.isChecked();
        }

        public void setNotificationsEnabled(boolean notificationsEnabled) {
            this.notificationsEnabled.setChecked(notificationsEnabled);
        }

        public int getTimeDelta() {
            return (Integer) timeDelta.getSelectedItem();
        }

        public void setTimeDelta(int timeDelta) {
            for (int i = 0; i < this.timeDelta.getCount(); i++) {
                if ((Integer) this.timeDelta.getItemAtPosition(i) == timeDelta) {
                    this.timeDelta.setSelection(i, true);
                    break;
                }
            }
        }
    }


}
