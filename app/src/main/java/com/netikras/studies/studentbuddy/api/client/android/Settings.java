package com.netikras.studies.studentbuddy.api.client.android;

import android.content.Context;
import android.content.SharedPreferences;

import com.netikras.tools.common.remote.http.SessionContext;

import static com.netikras.tools.common.security.IntegrityUtils.isNullOrEmpty;

/**
 * Created by netikras on 17.10.14.
 */

public class Settings {


    private static SessionContext sessionContext;

    public static SharedPreferences getSettings(Context context) {
        return context.getSharedPreferences("connSettings", Context.MODE_PRIVATE);
    }


    public static String getExtraName(String name) {
        return getExtraName(Thread.currentThread().getStackTrace()[0].getClassName(), name);
    }

    public static String getExtraName(Class clazz, String name) {
        if (clazz == null) {
            return getExtraName("", name);
        }

        return getExtraName(clazz.getName(), name);
    }

    public static String getExtraName(String prefix, String name) {
        if (isNullOrEmpty(prefix)) {
            return "" + name;
        }

        return prefix + ".extra." + name;
    }



    public static String getActionName(String name) {
        return getActionName(Thread.currentThread().getStackTrace()[0].getClassName(), name);
    }

    public static String getActionName(Class clazz, String name) {
        if (clazz == null) {
            return getActionName("", name);
        }

        return getActionName(clazz.getName(), name);
    }

    public static String getActionName(String prefix, String name) {
        if (isNullOrEmpty(prefix)) {
            return "" + name;
        }

        return prefix + ".action." + name;
    }


    public static SessionContext getSessionContext(Context context) {
        // TODO persist
        if (sessionContext == null) {
            sessionContext = new SessionContext();
        }
        return sessionContext;
    }

}
