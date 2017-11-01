package com.netikras.studies.studentbuddy.api.client.android;

import android.content.Context;
import android.view.MenuItem;
import android.widget.PopupMenu;

import com.netikras.studies.studentbuddy.api.client.android.ui.login.view.LoginActivity;
import com.netikras.studies.studentbuddy.api.client.android.ui.SearchActivity;
import com.netikras.studies.studentbuddy.api.client.android.ui.person.view.SettingsActivity;
import com.netikras.studies.studentbuddy.api.client.android.ui.person.view.UserInfoActivity;

public abstract class MainMenuDefaultListener implements PopupMenu.OnMenuItemClickListener {

    protected abstract Context getContext();

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        int itemId = item.getItemId();
        switch (itemId) {
            case R.id.main_menu_user:
                UserInfoActivity.start(getContext());
                return true;
            case R.id.main_menu_login:
                LoginActivity.start(getContext());
                return true;
            case R.id.main_menu_search:
                SearchActivity.start(getContext());
                return true;
            case R.id.main_menu_settings:
                SettingsActivity.start(getContext());
                return true;
        }
        return false;
    }
}