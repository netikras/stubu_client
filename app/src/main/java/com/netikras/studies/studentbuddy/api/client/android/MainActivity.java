package com.netikras.studies.studentbuddy.api.client.android;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.PopupMenu;

public class MainActivity extends AppCompatActivity {

    private App app;

    private static final String TAG = "MAIN_ACT";

    private App getApp() {
        if (app == null) {
            app = App.getInstance(this);
        }
        return app;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getApp();
        setContentView(R.layout.activity_main);

        if (getApp().getCurrentUser() == null) {
            getApp().getCurrentUserOrLogin();
//            recreate();
        }

        Button menuButton = findViewById(R.id.btn_main_main_menu);
        menuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu menu = getApp().getMainMenu(MainActivity.this, v);
                menu.getMenu().findItem(R.id.main_menu_edit).setVisible(false);
                menu.getMenu().findItem(R.id.main_menu_create).setVisible(false);
                menu.getMenu().findItem(R.id.main_menu_save).setVisible(false);
                menu.getMenu().findItem(R.id.main_menu_delete).setVisible(false);
                menu.setOnMenuItemClickListener(new MainMenuDefaultListener() {

                    @Override
                    protected Context getContext() {
                        return MainActivity.this;
                    }

                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        if (super.onMenuItemClick(item)) {
                            return true;
                        }
                        int itemId = item.getItemId();
                        switch (itemId) {
                            case R.id.main_menu_edit:
                                Log.i(TAG, "EDIT");
                                return true;
                            case R.id.main_menu_create:
                                Log.i(TAG, "CREATE");
                                return true;
                            case R.id.main_menu_save:
                                Log.i(TAG, "SAVE");
                                return true;
                            case R.id.main_menu_delete:
                                Log.i(TAG, "DELETE");
                                return true;
                        }
                        return false;
                    }
                });
                menu.show();
            }
        });


    }

    private void toggleVisibility(View view) {
        view.setVisibility(view.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);
    }


    @Override
    public boolean onMenuOpened(int featureId, Menu menu) {
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
//        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

//        switch (item.getItemId()) {
//            case R.id.main_menu_settings:
//                SettingsRootActivity.start(this);
//                break;
//            case R.id.main_menu_login:
//                LoginActivity.start(this);
//                break;
//        }
        return true;
    }
}
