package com.netikras.studies.studentbuddy.api.client.android.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.netikras.studies.studentbuddy.api.client.android.conf.di.DepInjector;

/**
 * Created by netikras on 17.12.14.
 */

public class AutoStart extends BroadcastReceiver {


    @Override
    public void onReceive(Context context, Intent intent) {
        Intent initIntent = new Intent(context, ScheduledUpdateService.class);
        initIntent.putExtra("autostart", true);
        context.startService(initIntent);

    }

}