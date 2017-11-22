package com.netikras.studies.studentbuddy.api.client.android.util;

/**
 * Created by amitshekhar on 08/01/17.
 */

public final class AppConstants {

    public static final String STATUS_CODE_SUCCESS = "success";
    public static final String STATUS_CODE_FAILED = "failed";

    public static final int API_STATUS_CODE_LOCAL_ERROR = 0;

    public static final String DB_NAME = "mindorks_mvp.db";
    public static final String PREF_NAME = "mindorks_pref";

    public static final long NULL_INDEX = -1L;
    public static final String NULL_ID = "nil";

    public static final String SEED_DATABASE_OPTIONS = "seed/options.json";
    public static final String SEED_DATABASE_QUESTIONS = "seed/questions.json";

    public static final String TIMESTAMP_FORMAT = "yyyyMMdd_HHmmss";
    public static final String DATE_FORMAT = "yyyyMMdd";
    public static final String TIME_FORMAT = "HH:mm";


    public static final long SECOND_IN_MS = 1000;
    public static final long MINUTE_IN_MS = SECOND_IN_MS * 60;
    public static final long HOUR_IN_MS = MINUTE_IN_MS * 60;
    public static final long DAY_IN_MS = HOUR_IN_MS * 24;


    private AppConstants() {
        // This utility class is not publicly instantiable
    }
}
