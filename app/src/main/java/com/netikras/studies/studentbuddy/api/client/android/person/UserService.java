package com.netikras.studies.studentbuddy.api.client.android.person;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;
import android.util.Log;

import com.netikras.studies.studentbuddy.api.client.android.App;
import com.netikras.studies.studentbuddy.api.client.android.Settings;
import com.netikras.studies.studentbuddy.api.client.android.student.ExtrasPair;
import com.netikras.studies.studentbuddy.api.user.generated.PersonApiConsumer;
import com.netikras.studies.studentbuddy.api.user.generated.UserApiConsumer;
import com.netikras.studies.studentbuddy.core.data.api.dto.PersonDto;
import com.netikras.studies.studentbuddy.core.data.api.dto.meta.UserDto;

import static com.netikras.studies.studentbuddy.api.client.android.Settings.getExtraName;
import static com.netikras.tools.common.security.IntegrityUtils.isNullOrEmpty;


public class UserService extends IntentService {


    public enum Actions {
        ACT_LOGIN,
        ACT_LOGOUT,
        ACT_CHNGPSWD,
        ACT_GET_USER,
        ACT_CREATE_W_ID2
    }

    public static final String X_USERNAME = getExtraName("username");
    public static final String X_PASSWORD = getExtraName("password");
    public static final String X_USER_ID = getExtraName("user_id");
    public static final String X_PERSON_ID2 = getExtraName("person_id2");
    public static final String X_USER_KEY = getExtraName("user_key");

    private UserApiConsumer userApiConsumer;
    private App app;


    public UserService() {
        super("UserService");
    }

    @Override
    public void onStart(@Nullable Intent intent, int startId) {
        super.onStart(intent, startId);
        app = App.getInstance(this);
        createConsumer();
    }

    private void createConsumer() {
        SharedPreferences settings = Settings.getSettings(this);

        if (userApiConsumer == null || settings.getBoolean("reset_server", false)) {
            userApiConsumer = new UserApiConsumer();
        }

        app.attachConsumer(userApiConsumer);
    }


    /**
     * Starts this service to perform action Foo with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    public static void startLogin(Context context, String username, String password) {
        doStartForAction(context, Actions.ACT_LOGIN,
                new ExtrasPair(X_USERNAME, username),
                new ExtrasPair(X_PASSWORD, password)
        );
    }

    public static void startLogout(Context context) {
        doStartForAction(context, Actions.ACT_LOGOUT);
    }

    public static void startGetUser(Context context, String userId) {
        doStartForAction(context, Actions.ACT_GET_USER,
                new ExtrasPair(X_USER_ID, userId)
        );
    }

    public static void startChangePassword(Context context, String newPassword) {
        doStartForAction(context, Actions.ACT_CHNGPSWD,
                new ExtrasPair(X_PASSWORD, newPassword)
        );
    }


    public static void startCreateUserById2(Context context, UserDto userDto, String id2) {
        doStartForAction(context, Actions.ACT_CREATE_W_ID2,
                new ExtrasPair(X_USER_KEY, App.getInstance(context).addToCache(userDto)),
                new ExtrasPair(X_PERSON_ID2, id2)
        );
    }


    private static void doStartForAction(Context context, Actions action, ExtrasPair... extras) {
        Intent intent = new Intent(context, UserService.class);
        intent.setAction(action.name());
        if (extras != null) {
            for (ExtrasPair extra : extras) {
                intent.putExtra(extra.getKey(), extra.getValue());
            }
        }
        context.startService(intent);
    }


    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {

            String username;
            String password;
            String userId;
            String id2;
            UserDto userDto;
            String key;

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
                    break;
                case ACT_GET_USER:
                    userId = intent.getStringExtra(X_USER_ID);
                    handleGetUser(userId);
                    break;
                case ACT_CREATE_W_ID2:
                    id2 = intent.getStringExtra(X_PERSON_ID2);
                    key = intent.getStringExtra(X_USER_KEY);
                    userDto = (UserDto) App.getInstance(this).popFromCache(key);
                    handleCreateUserForId2(userDto, id2);
                default:
                    break;
            }
        }
    }


    private void handleCreateUserForId2(UserDto userDto, String id2) {
        createConsumer();
        PersonApiConsumer personApiConsumer = new PersonApiConsumer();
        app.attachConsumer(personApiConsumer);
        PersonDto personDto = personApiConsumer.getPersonDtoByIdentifier(id2);
        if (personDto == null) {
            return;
        }

        userDto.setPerson(personDto);
        UserDto created = userApiConsumer.createUserDto(userDto);

        if (created == null) {
            return;
        }

        // FIXME drop DTO somewhere

    }

    private void handleGetUser(String userId) {
        createConsumer();
        UserDto userDto = userApiConsumer.retrieveUserDto(userId);
        // FIXME drop DTO somewhere...
    }

    private void handleLogin(String username, String password) {
        createConsumer();
        UserDto userDto = userApiConsumer.loginUserDto(username, password, null);

        if (userDto != null && !isNullOrEmpty(userDto.getId())) {
            App app = App.getInstance(this);
            app.setCurrentUser(userDto);
        }

        Log.i("Received user: ", "" + userDto);
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
