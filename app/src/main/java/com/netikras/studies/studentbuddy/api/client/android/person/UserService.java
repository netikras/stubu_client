package com.netikras.studies.studentbuddy.api.client.android.person;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;
import android.util.Log;

import com.netikras.studies.studentbuddy.api.client.android.App;
import com.netikras.studies.studentbuddy.api.client.android.Settings;
import com.netikras.studies.studentbuddy.api.user.generated.UserApiConsumer;
import com.netikras.studies.studentbuddy.core.data.api.dto.meta.UserDto;
import com.netikras.tools.common.remote.RemoteEndpointServer;
import com.netikras.tools.common.remote.http.HttpRequest;

import static com.netikras.studies.studentbuddy.api.client.android.Settings.getActionName;
import static com.netikras.studies.studentbuddy.api.client.android.Settings.getExtraName;
import static com.netikras.tools.common.security.IntegrityUtils.isNullOrEmpty;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class UserService extends IntentService {
    // TODO: Rename actions, choose action names that describe tasks that this
    // IntentService can perform, e.g. ACTION_FETCH_NEW_ITEMS
    private static final String ACTION_FOO = "com.netikras.studies.studentbuddy.api.client.android.action.FOO";
    private static final String ACTION_BAZ = "com.netikras.studies.studentbuddy.api.client.android.action.BAZ";

    // TODO: Rename parameters
    private static final String EXTRA_PARAM1 = "com.netikras.studies.studentbuddy.api.client.android.extra.PARAM1";
    private static final String EXTRA_PARAM2 = "com.netikras.studies.studentbuddy.api.client.android.extra.PARAM2";


    public enum Actions {
        ACT_LOGIN,
        ACT_LOGOUT,
        ACT_CHNGPSWD
    }

    public static final String X_USERNAME = getExtraName("username");
    public static final String X_PASSWORD = getExtraName("password");

    private UserApiConsumer userApiConsumer;


    public UserService() {
        super("UserService");
    }

    @Override
    public void onStart(@Nullable Intent intent, int startId) {
        super.onStart(intent, startId);
        createConsumer();
    }

    private void createConsumer() {
        SharedPreferences settings = Settings.getSettings(this);

        if (userApiConsumer != null && !settings.getBoolean("reset_server", false)) {
            return;
        }

        String address = settings.getString("address", "http://192.168.1.6");
        int port = settings.getInt("port", 8080);
        String rootContext = settings.getString("context", "/stubu");

        RemoteEndpointServer server = RemoteEndpointServer.parse(address);
        server.setPort(port);
        server.setRootUrl(rootContext);
        server.setItemId("1");
        server.setProtocol(HttpRequest.Protocol.HTTP);

        userApiConsumer = new UserApiConsumer();
        userApiConsumer.addServer("default", server);
        userApiConsumer.setSessionContext(Settings.getSessionContext(this));
    }




    /**
     * Starts this service to perform action Foo with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    public static void startLogin(Context context, String username, String password) {
        doStartForAction(context, Actions.ACT_LOGIN,
                new String[]{X_USERNAME, username},
                new String[]{X_PASSWORD, password}
        );
    }

    public static void startLogout(Context context) {
        doStartForAction(context, Actions.ACT_LOGOUT);
    }

    public static void startChangePassword(Context context, String newPassword) {
        doStartForAction(context, Actions.ACT_CHNGPSWD,
                new String[]{X_PASSWORD, newPassword}
                );
    }





    private static void doStartForAction(Context context, Actions action, String[]... extras) {
        Intent intent = new Intent(context, UserService.class);
        intent.setAction(action.name());
        if (extras != null) {
            for (String[] extra : extras) {
                if (extra == null || extra.length != 2) {
                    Log.e("UserService", "Ignoring a set of extras");
                    continue;
                }
                intent.putExtra(extra[0], extra[1]);
            }
        }
        context.startService(intent);
    }


    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {

            String username;
            String password;

            Actions action = Actions.valueOf(intent.getAction());
            switch (action) {
                case ACT_LOGIN:
                    username = intent.getStringExtra(X_USERNAME);
                    password = intent.getStringExtra(X_PASSWORD);
                    handleLogin(username, password);
                    break;
                case ACT_LOGOUT:
                    handleLogout();
                    break;
                case ACT_CHNGPSWD:
                    password = intent.getStringExtra(X_PASSWORD);
                    handleChangePassword(password);
                default:
                    break;
            }
        }
    }

    private void handleLogin(String username, String password) {
        createConsumer();
        UserDto userDto = userApiConsumer.loginUserDto(username, password, null);

        if (userDto != null && !isNullOrEmpty(userDto.getId())) {
            App app = App.getInstance(this);
            app.setCurrentUser(userDto);
        }

        Log.i("Received user: ", ""+userDto);
    }

    private void handleLogout() {
        createConsumer();
        App app = App.getInstance(this);
        UserDto currentUser = app.getCurrentUser();
        userApiConsumer.logoutUserDto();

        if (currentUser != null) {
            app.setCurrentUser(userApiConsumer.retrieveUserDto(currentUser.getId()));
        }
    }

    private void handleChangePassword(String newPassword) {
        createConsumer();
        App app = App.getInstance(this);
        UserDto currentUser = app.getCurrentUser();
        if (currentUser == null) {
            return;
        }

        userApiConsumer.changeUserDtoPassword(currentUser.getId(), newPassword);

    }

}
