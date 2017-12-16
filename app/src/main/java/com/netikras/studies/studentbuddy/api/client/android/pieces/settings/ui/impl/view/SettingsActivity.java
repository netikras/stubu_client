package com.netikras.studies.studentbuddy.api.client.android.pieces.settings.ui.impl.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import com.netikras.studies.studentbuddy.api.client.android.R;
import com.netikras.studies.studentbuddy.api.client.android.conf.di.DepInjector;
import com.netikras.studies.studentbuddy.api.client.android.pieces.base.BaseActivity;
import com.netikras.studies.studentbuddy.api.client.android.pieces.base.BaseViewFields;
import com.netikras.studies.studentbuddy.api.client.android.pieces.settings.ui.presenter.SettingsMvpPresenter;
import com.netikras.studies.studentbuddy.api.client.android.pieces.settings.ui.view.SettingsMvpView;
import com.netikras.tools.common.remote.RemoteEndpointServer;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import butterknife.BindView;

import static com.netikras.tools.common.security.IntegrityUtils.isNullOrEmpty;

public class SettingsActivity extends BaseActivity implements SettingsMvpView {


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

    public ViewFields getFields() {
        return fields;
    }

    public static void start(Context context) {
        Intent intent = new Intent(context, SettingsActivity.class);
        context.startActivity(intent);
    }


    private void showSettings() {
        getFields().setUrl(presenter.getApiUrl());
        getFields().setNotificationsEnabled(presenter.isNotificationsEnabled());
        getFields().setCommentNotificationsEnabled(presenter.isCommentNotificationsEnabled());
        getFields().setLecturesAheadPeriod(presenter.getLecturesAheadPeriod());
        getFields().setNotifyEventBeforePeriod(presenter.getNotifyEventBeforePeriod());
        getFields().setUpdatePeriod(presenter.getUpdatePeriod());
        getFields().setAutostartEnabled(presenter.getAutostartEnabled());
    }

    @Override
    protected void menuOnClickSave() {
        presenter.saveApiUrl(getFields().getUrl());
        presenter.saveNotificationsSettings(getFields().getNotificationsEnabled());
        presenter.saveCommentNotificationsSettings(getFields().getCommentNotificationsEnabled());
        presenter.saveLecturesAheadPeriod(getFields().getLecturesAheadPeriod());
        presenter.saveNotifyEventBeforePeriod(getFields().getNotifyEventsBeforePeriod());
        presenter.saveUpdatePeriod(getFields().getUpdatePeriod());
        presenter.saveAutostartEnabled(getFields().getAutostartEnabled());
        getFields().enableEdit(false);
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
        @BindView(R.id.switch_settings_notify_comments_enable)
        Switch commentNotificationsEnabled;
        @BindView(R.id.switch_settings_autostart_enable)
        Switch autostartEnabled;
        @BindView(R.id.txt_edit_settings_lectures_ahead_period)
        EditText lecturesAheadPeriod; // hours
        @BindView(R.id.txt_edit_settings_update_period)
        EditText updatePeriod;
        @BindView(R.id.txt_edit_settings_notify_event_before)
        EditText notifyEventBefore;


        @Override
        protected Collection<TextView> getAllFields() {
            return Arrays.asList(url, lecturesAheadPeriod, updatePeriod, notifyEventBefore);
        }

        @Override
        protected Map<TextView, Integer> getEditableFields() {
            Map<TextView, Integer> types = super.getEditableFields();

            types.put(url, InputType.TYPE_TEXT_VARIATION_URI);
            types.put(lecturesAheadPeriod, InputType.TYPE_CLASS_NUMBER);
            types.put(updatePeriod, InputType.TYPE_CLASS_NUMBER);
            types.put(notifyEventBefore, InputType.TYPE_CLASS_NUMBER);

            return types;
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

        public boolean getCommentNotificationsEnabled() {
            return commentNotificationsEnabled.isChecked();
        }

        public void setCommentNotificationsEnabled(boolean enabled) {
            commentNotificationsEnabled.setChecked(enabled);
        }

        public boolean getAutostartEnabled() {
            return autostartEnabled.isChecked();
        }

        public void setAutostartEnabled(boolean enabled) {
            autostartEnabled.setChecked(enabled);
        }

        public long getLecturesAheadPeriod() {
            return parseLong(getString(lecturesAheadPeriod));
        }

        public void setLecturesAheadPeriod(long hours) {
            setString(lecturesAheadPeriod, "" + hours);
        }

        public long getUpdatePeriod() {
            return parseLong(getString(updatePeriod));
        }

        public void setUpdatePeriod(long minutes) {
            setString(updatePeriod, "" + minutes);
        }

        public long getNotifyEventsBeforePeriod() {
            return parseLong(getString(notifyEventBefore));
        }

        public void setNotifyEventBeforePeriod(long minutes) {
            setString(notifyEventBefore, "" + minutes);
        }

        private long parseLong(String text) {
            if (!isNullOrEmpty(text)) {
                try {
                    return Long.parseLong(text);
                } catch (Exception e) {

                }
            }
            return -1;
        }
    }


}
