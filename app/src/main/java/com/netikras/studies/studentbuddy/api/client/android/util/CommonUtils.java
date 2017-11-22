/*
 * Copyright (C) 2017 MINDORKS NEXTGEN PRIVATE LIMITED
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://mindorks.com/license/apache-v2
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License
 */

package com.netikras.studies.studentbuddy.api.client.android.util;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.provider.Settings;

import com.netikras.studies.studentbuddy.api.client.android.R;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by janisharali on 27/01/17.
 */

public final class CommonUtils {

    private static final String TAG = "CommonUtils";

    private CommonUtils() {
        // This utility class is not publicly instantiable
    }

    public static ProgressDialog showLoadingDialog(Context context) {
        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.show();
        if (progressDialog.getWindow() != null) {
            progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
        progressDialog.setContentView(R.layout.progress_dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        return progressDialog;
    }

    @SuppressLint("all")
    public static String getDeviceId(Context context) {
        return Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
    }

    public static boolean isEmailValid(String email) {
        Pattern pattern;
        Matcher matcher;
        final String EMAIL_PATTERN =
                "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                        + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        pattern = Pattern.compile(EMAIL_PATTERN);
        matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public static String loadJSONFromAsset(Context context, String jsonFileName)
            throws IOException {

        AssetManager manager = context.getAssets();
        InputStream is = manager.open(jsonFileName);

        int size = is.available();
        byte[] buffer = new byte[size];
        is.read(buffer);
        is.close();

        return new String(buffer, "UTF-8");
    }

    public static String getTimeStamp() {
        return datetimeToTimestamp(new Date());
    }

    public static String formatDatetime(String pattern, Date datetime) {
        if (pattern == null || datetime == null) {
            return "";
        }
        return new SimpleDateFormat(pattern, Locale.ENGLISH).format(datetime);
    }

    public static Date parseDatetime(String pattern, String text) {
        if (pattern == null || text == null) {
            return null;
        }
        try {
            return new SimpleDateFormat(pattern, Locale.ENGLISH).parse(text);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String datetimeToTimestamp(Date date) {
        return formatDatetime(AppConstants.TIMESTAMP_FORMAT, date);
    }
    public static Date timestampTodatetime(String timestamp) {
        return parseDatetime(AppConstants.TIMESTAMP_FORMAT, timestamp);
    }



    public static String datetimeToDate(Date date) {
        return formatDatetime(AppConstants.DATE_FORMAT, date);
    }

    public static String datetimeToTime(Date date) {
        return formatDatetime(AppConstants.TIME_FORMAT, date);
    }

    public static Date dateToDatetime(String date) {
        return parseDatetime(AppConstants.DATE_FORMAT, date);
    }

    public static Date timeToDatetime(String time) {
        return parseDatetime(AppConstants.TIME_FORMAT, time);
    }



}
