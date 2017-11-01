package com.netikras.studies.studentbuddy.api.client.android;

import android.app.Application;
import android.content.Context;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;

import com.netikras.studies.studentbuddy.api.client.android.conf.di.DepInjector;
import com.netikras.studies.studentbuddy.api.client.android.conf.di.component.ApplicationComponent;
import com.netikras.studies.studentbuddy.api.client.android.ui.login.view.LoginActivity;
import com.netikras.studies.studentbuddy.api.client.android.ui.person.UserService;
import com.netikras.studies.studentbuddy.api.client.android.util.Settings;
import com.netikras.studies.studentbuddy.core.data.api.dto.meta.UserDto;
import com.netikras.tools.common.remote.RemoteEndpointServer;
import com.netikras.tools.common.remote.http.GenericRestConsumer;
import com.netikras.tools.common.remote.http.SessionContext;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Created by netikras on 17.10.17.
 */

public class App extends Application {

    private UserDto currentUser;
    private SessionContext sessionContext = new SessionContext();

    private Map<String, RemoteEndpointServer> servers = new HashMap<>();

    private Map<String, Object> cache = new HashMap<>();


    public static App getInstance(Context context) {
        return (App) context.getApplicationContext();
    }

    private ApplicationComponent mApplicationComponent;

    public ApplicationComponent getComponent() {
        return mApplicationComponent;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mApplicationComponent = DepInjector.inject(this);
    }

    public UserDto getCurrentUserOrLogin() {
        if (currentUser == null) {
            LoginActivity.start(this);
        }
        return currentUser;
    }

    public UserDto refreshUser() {
        if (getCurrentUser() != null) {
            UserService.startGetUser(this, getCurrentUser().getId());
        }
        return null;
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

    public RemoteEndpointServer getServer(String name) {
        if (name == null) {
            name = "default";
        }
        return servers.get(name);
    }

    public PopupMenu getMainMenu(Context context, View view) {
        PopupMenu menu = new PopupMenu(context, view);
        MenuInflater inflater = menu.getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu.getMenu());

        removeUnavailableItems(menu.getMenu());
//        menu.show();
        return menu;
    }

    private void removeUnavailableItems(Menu menu) {

        if (getCurrentUser() == null) {
            setVisible(menu.findItem(R.id.main_menu_user), false);
            setVisible(menu.findItem(R.id.main_menu_login), true);
        } else {
            setVisible(menu.findItem(R.id.main_menu_user), true);
            setVisible(menu.findItem(R.id.main_menu_login), false);
        }
    }




    private void setVisible(MenuItem item, boolean show) {
        if (item == null) {
            return;
        }

        item.setVisible(show);
    }


    public void attachConsumer(GenericRestConsumer consumer) {
        for (Map.Entry<String, RemoteEndpointServer> entry : servers.entrySet()) {
            consumer.addServer(entry.getKey(), entry.getValue());
        }
        consumer.setSessionContext(Settings.getSessionContext(this));
    }

    public Object getFromCache(String key) {
        return cache.get(key);
    }

    public String addToCache(Object object) {
        String key = UUID.randomUUID().toString();
        cache.put(key, object);
        return key;
    }

    public Object popFromCache(String key) {
        return cache.remove(key);
    }


}
