package com.netikras.studies.studentbuddy.api.client.android.person;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;

import com.netikras.studies.studentbuddy.api.client.android.App;
import com.netikras.studies.studentbuddy.api.client.android.R;
import com.netikras.tools.common.remote.RemoteEndpointServer;

public class SettingsActivity extends AppCompatActivity {

    private App app;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        app = App.getInstance(this);

        showSettings();
    }

    public static void start(Context context) {
        Intent intent = new Intent(context, SettingsActivity.class);
        context.startActivity(intent);
    }


    private void showSettings() {
        RemoteEndpointServer server = app.getServer(null);

        ((EditText)findViewById(R.id.txt_edit_settings_url)).setText(toString(server));
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



}
