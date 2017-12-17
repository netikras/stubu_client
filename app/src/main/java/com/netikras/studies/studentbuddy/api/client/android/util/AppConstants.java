package com.netikras.studies.studentbuddy.api.client.android.util;

/**
 * Created by amitshekhar on 08/01/17.
 */

public final class AppConstants {

    public static final String NULL_ID = "nil";

    public static final String TIMESTAMP_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final String TIMESTAMP_SHORT_FORMAT = "MM-dd HH:mm:ss";
    public static final String TIMESTAMPNSEC_FORMAT = "yyyy-MM-dd HH:mm";
    public static final String TIMESTAMPNSEC_SHORT_FORMAT = "MM-dd HH:mm";
    public static final String DATE_FORMAT = "yyyy-MM-dd";
    public static final String TIME_FORMAT = "HH:mm";


    public static final long SECOND_IN_MS = 1000;
    public static final long MINUTE_IN_MS = SECOND_IN_MS * 60;
    public static final long HOUR_IN_MS = MINUTE_IN_MS * 60;
    public static final long DAY_IN_MS = HOUR_IN_MS * 24;


    private AppConstants() {
        // This utility class is not publicly instantiable
    }
}
