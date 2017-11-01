package com.netikras.studies.studentbuddy.api.client.android.conf.di;

import java.lang.annotation.Retention;

import javax.inject.Qualifier;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Created by netikras on 17.10.30.
 */

@Qualifier
@Retention(RUNTIME)
public @interface ApplicationCtx {
}
