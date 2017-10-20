package com.netikras.studies.studentbuddy.api.client.android;

import android.app.Application;
import android.content.Context;

import com.netikras.studies.studentbuddy.core.data.api.dto.meta.UserDto;
import com.netikras.tools.common.remote.http.SessionContext;

/**
 * Created by netikras on 17.10.17.
 */

public class App extends Application {

    private UserDto currentUser;
    private SessionContext sessionContext;


    public static App getInstance(Context context) {
        return (App) context.getApplicationContext();
    }

    public UserDto getCurrentUserOrLogin() {
        if (currentUser == null) {
            LoginActivity.start(this);
        }
        return currentUser;
    }

    public UserDto getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(UserDto currentUser) {
        this.currentUser = currentUser;
    }

    public SessionContext getSessionContext() {
        return sessionContext;
    }
}
