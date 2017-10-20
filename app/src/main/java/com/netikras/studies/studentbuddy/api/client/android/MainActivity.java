package com.netikras.studies.studentbuddy.api.client.android;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    private App app;

    private App getApp() {
        if (app == null) {
            app = App.getInstance(this);
        }
        return app;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        showCurrentUser();

    }


    /*private void showCurrentUser() {
        UserDto currentUser = getApp().getCurrentUserOrLogin();

        TextView nameView = findViewById(R.id.txt_current_user_name);
        TextView idView = findViewById(R.id.txt_current_user_id);

        String shownId = "" + idView.getText();

        String currentName = "-";
        String currentId = "-";

        if (currentUser != null) {
            currentName = currentUser.getName();
            currentId = currentUser.getId();
        }

        nameView.setText(currentName);
        idView.setText(currentId);

        if (!shownId.equals(currentId)) {
            idView.invalidate();
            nameView.invalidate();
            toggleVisibility(idView);
            toggleVisibility(idView);

            toggleVisibility(nameView);
            toggleVisibility(nameView);
            // FIXME need to redraw fields after updating their values
            recreate();
        }

    }*/

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
