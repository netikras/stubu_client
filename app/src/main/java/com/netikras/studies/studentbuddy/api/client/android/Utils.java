package com.netikras.studies.studentbuddy.api.client.android;

import android.content.Context;
import android.content.SharedPreferences;

import com.netikras.tools.common.io.IoUtils;

import static com.netikras.tools.common.io.IoUtils.toObject;
import static com.netikras.tools.common.security.IntegrityUtils.fromBase64;
import static com.netikras.tools.common.security.IntegrityUtils.toBase64String;

/**
 * Created by netikras on 17.10.16.
 */

public class Utils {

    public static void persistObject(Context context, String prefsName, String objectName, Object object) {
        SharedPreferences preferences = context.getSharedPreferences(prefsName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(objectName, toBase64String(IoUtils.objectToByteArray(object)));
        editor.apply();
    }

    public static <T> T retrieveObject(Context context, String prefsName, String objectName, Class<T> type) {
        T result = null;
        SharedPreferences preferences = context.getSharedPreferences(prefsName, Context.MODE_PRIVATE);
        String resultBase64 = preferences.getString(objectName, "");

        result = (T) toObject(fromBase64(resultBase64));

        return result;
    }

}
