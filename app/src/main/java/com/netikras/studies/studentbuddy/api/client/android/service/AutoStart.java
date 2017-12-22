package com.netikras.studies.studentbuddy.api.client.android.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.netikras.studies.studentbuddy.api.client.android.conf.di.DepInjector;
import com.netikras.tools.common.security.IntegrityUtils;

import static android.os.Debug.waitForDebugger;
import static com.netikras.tools.common.io.IoUtils.sleep;

/**
 * Created by netikras on 17.12.14.
 */

public class AutoStart extends BroadcastReceiver {

    private static final String TAG = "AutoStart.class";

    @Override
    public void onReceive(Context context, Intent intent) {
//        waitForDebugger();
        Log.d(TAG, "Intent: " + intent);
//        if ("android.intent.action.BOOT_COMPLETED".equals(intent.getAction())) {
//            Intent initIntent = new Intent(context, ScheduledUpdateService.class);
//            initIntent.putExtra("autostart", true);
//            context.startService(initIntent);
//        }
        ScheduledUpdateService.reschedule(1000 * 10, context);
    }

}